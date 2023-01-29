package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCoverLetterActivities

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.print.PdfPrint
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.coverletter.CoverLetterFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.SavedCoverLetterAcitivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelCoverLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelNewMain

import java.io.File
import java.io.FileOutputStream
import java.lang.String
import java.util.*


class PreviewGreenLetterActivity : AppCompatActivity() {


    lateinit var dialog: ProgressDialog
    lateinit var tinyDB: TinyDB
    lateinit var webView: WebView
    companion object {
        lateinit var modelMain: ModelCoverLetter
    }

    lateinit var xModel: ModelNewMain
    val htmlContent = StringBuilder()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_cv)

        dialog = ProgressDialog(this)
        dialog.setTitle("Loading CV")
        dialog.setMessage("Please Wait While we are preparing your CV")
        dialog.setCancelable(false)

        tinyDB =
            TinyDB(
                applicationContext
            )
        modelMain = CoverLetterFragment.modelObjectCLFrag
        Log.e("letter", "onCreate: modelMain" + modelMain.getReceiverName())
        Log.e("letter", "onCreate: modelMain" + modelMain.getReceiverAddress())

        val toolbar: Toolbar =
            findViewById(R.id.toolbarPreviewCV)
        setSupportActionBar(toolbar)
        val txtToolbar: TextView = toolbar.findViewById(R.id.textPreviewCV)
        txtToolbar.text = modelMain.getCFileName().capitalize()

        dialog.show()

        Thread {
            previewCV()
            Handler(Looper.getMainLooper()).post(object : Runnable {
                override fun run() {
                    webView = findViewById(R.id.webView)
                    webView.settings.allowFileAccess = true;
                    webView.settings.javaScriptEnabled = true;
                    webView.setInitialScale(100)
                    webView.settings.builtInZoomControls = true;
                    webView.loadDataWithBaseURL(
                        null,
                        htmlContent.toString(),
                        "text/html",
                        "utf-8",
                        null
                    )
                    webView.webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: kotlin.String) {
                            if (dialog.isShowing()) {
                                dialog.dismiss()
                            }
                        }
                    }
                }

            })
        }.start()





        btnExport = toolbar.findViewById(R.id.ExportCv)
        btnExport.setOnClickListener {

                btnExportClicked()

        }

    }
    private lateinit var btnExport: Button


    fun btnExportClicked()
    {
        val popup = PopupMenu(this, btnExport)
        popup.inflate(R.menu.menu_export)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.actionExportPdf -> {
                    savePdf()
                }
                R.id.actionExportPng -> {

                    webView.setInitialScale(100)
                    savePng()
                }
            }
            false
        }
        popup.show()
    }

    private val PERMISSION_REQUEST = 0
    private var allowSave = true
    private fun savePdf() {
        if (!allowSave) return
        allowSave = false
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val fileName = modelMain.cfilename+"_"+ modelMain.getCid()+"_GL.pdf"
            val printAdapter = webView.createPrintDocumentAdapter(fileName)
            val printAttributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()
            val file = File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/CoverLetter/Pdf/")
            PdfPrint(printAttributes).print(
                printAdapter,
                file,
                fileName,
                object : PdfPrint.CallbackPrint {
                    override fun success(path: kotlin.String?) {
                        allowSave = true
                        Toast.makeText(
                            applicationContext,
                            kotlin.String.format("Your file is saved in %s", path),
                            Toast.LENGTH_LONG
                        ).show()
                        showFilesDialogPDf()
                    }

                    override fun onFailure(errorMsg: kotlin.String?) {
                        allowSave = true
                        Toast.makeText(
                            applicationContext,
                            kotlin.String.format(
                                "Failed",
                            ),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<kotlin.String?>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults[Arrays.asList(*permissions)
                    .indexOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)] == PackageManager.PERMISSION_GRANTED
            ) {
                savePdf()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getOPFile(): File? {
        val mediaStorageDir = File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/CoverLetter/Images/")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        return File(mediaStorageDir.path + File.separator + modelMain.cfilename+"_GL.jpg")
    }

    private fun savePng() {
        val picture: Picture = webView.capturePicture()
        val b = Bitmap.createBitmap(
            picture.width, picture.height, Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        picture.draw(c)

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream( getOPFile())
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            showFilesDialogImages()

        } catch (e: Exception) {
            println("-----error--$e")
        }
    }

    private fun showFilesDialogPDf() {

        webView.setInitialScale(100)
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_file_converted, null)
        dialogBuilder.setView(dialogView)
        val createBt: Button = dialogView.findViewById(R.id.btnViewNow)
        val cancelBt: Button = dialogView.findViewById(R.id.btnlater)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {
            tinyDB.putBoolean("Cpdf", false)
            val intent = Intent(this, SavedCoverLetterAcitivity::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()

        }

        cancelBt.setOnClickListener {
            alertDialog.dismiss()

        }


        alertDialog.show()
    }  private fun showFilesDialogImages() {

        webView.setInitialScale(100)
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_file_converted, null)
        dialogBuilder.setView(dialogView)
        val createBt: Button = dialogView.findViewById(R.id.btnViewNow)
        val cancelBt: Button = dialogView.findViewById(R.id.btnlater)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {

            tinyDB.putBoolean("Cpdf", true)
            val intent = Intent(this, SavedCoverLetterAcitivity::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()

        }

        cancelBt.setOnClickListener {
            alertDialog.dismiss()

        }


        alertDialog.show()
    }

    override fun onStop() {
        super.onStop()
        webView.destroy()
    }




    @SuppressLint("SetJavaScriptEnabled")
    fun previewCV() {
        htmlContent.append(
            String.format(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<title>Resume</title>\n" +

                        "<meta charset=UTF-8>\n" +
                        "" +
                        "    <link href=\"http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css\" rel=\"stylesheet\">" +
                        "    <link rel=\"shortcut icon\" href=\"https://ssl.gstatic.com/docs/documents/images/kix-favicon6.ico\">" +
                        "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">" +
                        "<style " +
                        "type=text/css>body{font-family:arial,sans,sans-serif;margin:0}" +
                        "iframe{border:0;frameborder:0;height:100%%;width:100%%}" +
                        "#header,#footer{background:#f0f0f0;padding:10px 10px}" +
                        "#header{border-bottom:1px #ccc solid}" +
                        "#footer{border-top:1px #ccc solid;border-bottom:1px #ccc solid;font-size:13}" +
                        "#contents{margin:6px}.dash{padding:0 6px}</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div id=contents>\n" +
                        "<style " +
                        "type=text/css>@import " +
                        "url('https://themes.googleusercontent.com/fonts/css?kit=xTOoZr6X-i3kNg7pYrzMsnEzyYBuwf3lO_Sc3Mw9RUVbV0WvE1cEyAoIq5yYZlSc');" +
                        "ol{margin:0;padding:0}table td,table th{padding:0}" +
                        ".c4{border-right-style: solid; border-bottom-color: #D4D5D6; border-top-width: 0pt; border-right-width: 0pt; margin-top: 20pt; border-left-color: #D4D5D6; vertical-align: top; border-right-color: #D4D5D6; border-left-width: 0pt; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; background: #D4D5D6; width: 290pt; border-top-color: #D4D5D6; border-bottom-style: solid}" +
                        ".c16{ color: #313133;            text-decoration: none;            vertical-align: baseline;           align-items: center;            text-align: center;            font-family: \"Raleway\";}" +
                        ".c7{color: #313133;            font-size: 10pt;            font-family: \"Lato\";            text-align: center;}" +
                        ".c13{ color: #000000;    font-weight: 700;    text-decoration: none;    vertical-align: baseline;    font-size: 10pt;    font-family: \"Lato\";    font-style: normal;}" +
                        ".c1{color: #666666;    font-family: \"Lato\";}" +
                        ".c19{color: #000000;    font-weight: 400;    text-decoration: none;    vertical-align: baseline;    font-size: 6pt;    font-family: \"Lato\";   font-style: normal}" +
                        ".c20{color: #FFBA50; text-decoration: none; vertical-align: baseline; font-size: 11pt; text-align: center; font-family: \"Raleway\"; font-style: normal}" +
                        ".c6{padding-bottom: 0pt;            line-height: 1.3;            margin-left: 30pt;            margin-right: 20pt;            text-align: left}" +
                        ".c32{padding-top: 5pt;    padding-bottom: 0pt;    line-height: 1.15;    text-align: left}" +
                        ".c0{padding-top: 10pt;    line-height: 1.0;   margin-left: 10pt;    text-align: left;}" +
                        ".c22{padding-bottom: 0pt;            color: #2F3A40;            text-align: left}" +
                        ".c221 {padding-bottom: 0pt;  color: #2F3A40;     background: #0084FF;     text-align: center;           color: #ffffff}" +
                        ".c10{color: #d44500;    text-decoration: none;    vertical-align: baseline;    font-style: normal}" +
                        ".c2{text-align: left}" +
                        ".c33{padding-top: 3pt;    padding-bottom: 0pt;    line-height: 1.0;    text-align: left}" +
                        ".c99 { padding-bottom: 0pt;            line-height: 1.5;            margin-left: 20pt;            margin-right: 20pt;            text-align: left}" +
                        ".c23{border-spacing: 0;    border-collapse: collapse;    margin: 0 auto;    background: #d9dae6}" +
                        ".c30{color: #000000;    text-decoration: none;    vertical-align: baseline;    font-style: normal}" +
                        ".c3{padding-bottom: 0pt;    text-align: left;}" +
                        ".c14{padding-top: 16pt;    padding-bottom: 0pt;    line-height: 1.15;    text-align: left}" +
                        ".c28{padding-top: 6pt;    padding-bottom: 0pt;    line-height: 1.0;    text-align: left}" +
                        ".c18{font-size: 9pt;    font-family: \"Lato\";    font-weight: 400}" +
                        ".c24{font-size: 14pt;    font-family: \"Lato\";    font-weight: 700}" +
                        ".c8{font-size: 10pt;    font-family: \"Lato\";    font-weight: 400}" +
                        ".c5{font-size: 11pt;    font-family: \"Lato\";    font-weight: 400}" +
                        ".c31{background-color: #d9dae6;    max-width: 504pt;    padding: 36pt 54pt 36pt 54pt}" +
                        ".c35{font-weight: 700;    font-size: 24pt;    font-family: \"Raleway\"}" +
                        ".c11{orphans: 2;    widows: 2;    height: 11pt}" +
                        ".c21{height:auto}" +
                        ".c15{height:auto}" +
                        ".yourButton1 {            position: relative;            background-color: black;        }" +
                        ".yourButton:after {  left: 100%% ;content: \"\"; position: absolute; top: 0px; width: 0px; height: 0px; border-top: 0px solid transparent; border-left: 20px solid black; border-bottom: 30px solid transparent;        }" +
                        " .yourButton1:after { content: \"\";position: absolute; left: 100%%; top: 0px; width: 0px; height: 0px; border-top: 0px solid transparent; border-left: 30px solid black; border-bottom: 70px solid transparent;}"+
                        "" +
                        ".aParent div {            float: left;            clear: none;            margin-top: 2pt        }" +
                        ".yourButton {            position: relative;            background-color: black;        }" +
                        ".divClass {            padding-left: 10pt;            margin-left: 0pt;            height: 26px;            width: 30pt;            padding-top: 2pt        }" +
                        ".c27{height:auto}" +
                        ".c211 {    height: 40pt}" +
                        ".c34{height:auto}" +
                        ".c29{height:auto}" +
                        ".c25 {            font-size: 11pt;            font-style italic}" +
                        ".c251{font-size: 10pt}" +
                        ".c12{page-break-after:avoid}" +
                        ".c17{height:265pt}" +


                        ".c9 {   color: #2F6565;            text-align: left;}" +
                        ".c16 { color: #2F6565; text-decoration: none; vertical-align: baseline; text-align: left; font-family: \"Raleway\"; font-style: normal }" +
                        ".c26 { border-right-style: solid; border-bottom-color: #F1F1F1; border-top-width: 0pt; border-right-width: 0pt border-left-color: #F1F1F1; vertical-align: top; border-right-color: #F1F1F1; border-left-width: 0pt; background: #F1F1F1; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; width: 750pt;border-top-color: #F1F1F1; border-bottom-style: solid}\n" +
                        ".header {  background: #014C4C;            height: 40pt;            color: #ffffff;            margin-top: -3pt     }" +
                        ".footer { background: #014C4C;            height: 40pt;            color: #ffffff;            margin-bottom: -3pt;            vertical-align: bottom }" +



                        ".c91{ line-height: 1.15;            color: #313133;            brder-radius: 10pt;            font-family: \"helvetica\";            background: #D4D5D6;            margin-right: 60pt;            margin-left: 20pt;            font-size: 18pt}" +
                        ".c161{ color: #313133;            font-weight: 100;            font-size: 12pt;            font-family: \"Raleway\"}" +
                        "hr.new6 {            border: 1px solid #717171;            border-radius: 1px;        }" +
                        ".c68{    padding-bottom: 10pt;    line-height: 1.0;    text-align: left;}" +

                        ".title{padding-top: 6pt;            color: #ffffff;            font-weight: 700;            font-size: 18pt;            padding-bottom: 0pt;            font-family: \"Raleway\";            line-height: 1.0;            page-break-after: avoid;            orphans: 2;            widows: 2;            text-align: left}" +
                        ".subtitle{ padding-top: 3pt;            color: #FFBA50;            font-size: 16pt;            padding-bottom: 0pt;            font-family: \"Raleway\";            line-height: 1.0;            page-break-after: avoid;            orphans: 2;            margin-left: 30pt;            widows: 2;            text-align: left}" +
                        "li{color:#000;font-size:11pt;font-family:\"Lato\"}" +
                        "p{margin: 0;    color: #000000;    font-size: 11pt;    font-family: \"Lato\"}" +
                        "h1{padding-top: 4pt;    color: #000000;    font-weight: 700;    font-size: 12pt;    padding-bottom: 0pt;    font-family: \"Raleway\";    line-height: 1.15;    page-break-after: avoid;    orphans: 2;    widows: 2;    text-align: left}" +
                        "h2{padding-top: 6pt;    color: #000000;    font-weight: 700;    font-size: 11pt;    padding-bottom: 0pt;    font-family: \"Lato\";    line-height: 1.15;    page-break-after: avoid;    orphans: 2;    widows: 2;    text-align: left}" +
                        "h3{padding-top: 6pt;    color: #666666;    font-size: 9pt;    padding-bottom: 0pt;    font-family: \"Lato\";    line-height: 1.15;    page-break-after: avoid;    orphans: 2;    widows: 2;    text-align: left}" +
                        "h4{padding-top:8pt;-webkit-text-decoration-skip:none;color:#666;text-decoration:underline;font-size:11pt;padding-bottom:0;line-height:1.15;page-break-after:avoid;text-decoration-skip-ink:none;font-family:\"Trebuchet MS\";orphans:2;widows:2;text-align:left}" +
                        "h5{padding-top:8pt;color:#666;font-size:11pt;padding-bottom:0;font-family:\"Trebuchet MS\";line-height:1.15;page-break-after:avoid;orphans:2;widows:2;text-align:left}" +
                        "h6{padding-top:8pt;color:#666;font-size:11pt;padding-bottom:0;font-family:\"Trebuchet MS\";line-height:1.15;page-break-after:avoid;font-style:italic;orphans:2;widows:2;text-align:left}" +
                        "</style>\n" +
                        "<p class=\"c2 c29\"><span class=c19></span></p>\n" +
                        "<a id=t.b7144mALLJdNZgTMP7QwRgUp272mSxnuhMR8051e></a>\n" +
                        "<a id=t.0></a>\n"

            )
        )
        htmlContent.append(
            String.format(
                "        <table class=\"c23\" style=\"background: #2F3A40;\">\n" +
                        "            <tbody>\n"+
                        "                 <tr class=\"c21\">\n" +
                        "                    <th class=\"c26\" style=\"margin-top: 50px;\" colspan=\"1\" rowspan=\"1\">\n"

            )
        )

        htmlContent.append(String.format(
            "" +
                    "<td class=\"c26\"  style=\"margin-left: -5pt;\">" +
                    " <h1 class=\"header\">" +
                    "                        </h1>" +
                    "                        <h1 class=\"c9\" style=\" text-align: center;margin-top: 15pt;\">" +
                    "                            <span class=\"c16\" style=\"color: #2F6565;font-size: 30pt; margin-bottom: 10px;text-align: right;\">%s</span>" +
                    "                        </h1>" ,

            modelMain.getSenderCompany()

        ))

        htmlContent.append(String.format(
           "" +
                   " <h1 class=\"c9\" style=\"text-align: center;margin-top: 40pt; margin-bottom: 15px;\">" +
                   "                            <span class=\"c16\" style=\"font-size: 18pt;color: #2F6565;text-align: left;\">%s</span>" +
                   "                        </h1>"       ,
            modelMain.getSenderSubject()


        ))


        htmlContent.append(String.format(
            "" +
                    " <h1 class=\"c9\" style=\"height: 415pt;width:500pt; text-align: left; margin-right: 25pt;margin-left: 30pt\">" +
                    "                            <span class=\"c16\" style=\" line-height: 2.0;color: #2F6565; margin: 10pt;text-align: left;padding-right: 20pt\">" +
                    "                                %s</span>" +
                    "                        </h1>" +
                    "",
            modelMain.getDescription()
        ))

        htmlContent.append(String.format(
            "" +
                    "                          <h1 style=\" text-align: right;\">" +
                    "                            <span style=\"font-size: 12pt;color: #2F6565; margin-bottom: 10px;text-align: right; margin-right: 30pt\">Yours Sincerely</span>" +
                    "                        </h1>" +"                       " +
                    "                        <h1 style=\" text-align: right;\">" +
                    "                            <span style=\"font-size: 12pt;color: #2F6565; margin-bottom: 10px;text-align: right; margin-right: 30pt\">%s</span>" +
                    "                        </h1>" +
                    "                        <h1 style=\" text-align: right;\">" +
                    "                            <span style=\"font-size: 12pt;color: #2F6565; margin-bottom: 10px;text-align: right; margin-right: 30pt\">%s</span>" +
                    "                        </h1>" +
                    "                        <h1 style=\" text-align: right;\">" +
                    "                            <span style=\"font-size: 12pt;color: #2F6565; margin-bottom: 10px;text-align: right; margin-right: 30pt\">%s</span>" +
                    "                        </h1>" +
                    "                        </h1>" +
                    "                        <h3 style=\" text-align: left;margin-left:30pt;\">" +
                    "                            <span style=\" text-decoration: overline;font-size: 12pt;color: #2F6565; margin-bottom: 10px;text-align: left; margin-right: 15pt\">" +
                    "                                ( Signature )" +
                    "                            </span>" +
                    "                        </h3>" +
                    "                        <h3 style=\" text-align: left;margin-top: -8pt;margin-left:30pt;\">" +
                    "                            <span style=\"font-size: 12pt;color: #2F6565; margin-bottom: 10px;text-align: left; margin-right: 15pt\">" +
                    "                                Date: %s" +
                    "                            </span>" +
                    "                        </h3>" +
                    "                        <h1 class=\"footer\">" +
                    "                        </h1>" +
                    "                    </td>" +
                    "" +
                    "" ,
            modelMain.getSenderName(),
            modelMain.getSenderAddress(),
            modelMain.getSenderDesignation(),
            modelMain.getDate()
        ))



        htmlContent.append(
            String.format(
                "                    </th>\n"+
                        "                </tr>\n" +
                        "            </tbody>\n" +
                        "        </table>\n" +
                        "        <p class=\"c2 c11\"><span class=\"c30 c5\"></span></p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>\n"
            )
        )


    }

    private fun createWebPrintJob(webView: WebView) {



        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = modelMain.getCFileName() + "_Resume"
        val printAdapter = webView.createPrintDocumentAdapter()
        val builder = PrintAttributes.Builder()

        val custom = PrintAttributes.MediaSize.ISO_A4
        builder.setMediaSize(custom)
        val printJob = printManager.print(
            jobName, printAdapter,
            builder.build()
        )
    }

}