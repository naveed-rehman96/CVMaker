package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.print.PdfPrint
import android.print.PrintAttributes
import android.print.PrintManager
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
import androidx.core.content.ContextCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.Saved_Cv_Pdf_Activity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvMainModel
import java.io.File
import java.io.FileOutputStream
import java.lang.String
import java.util.*

class PreviewDarkerLighterGrayActivity : AppCompatActivity() {

    lateinit var dialog: ProgressDialog
    lateinit var tinyDB: TinyDB
    lateinit var webView: WebView

    companion object {
        lateinit var modelMain: CvMainModel
    }
    val htmlContent = StringBuilder()
    private fun checkTheme() {
        if (tinyDB.getString("APP_THEME") == getString(R.string.theme_blue)) {
            super.setTheme(R.style.AppTheme)
        } else if (tinyDB.getString("APP_THEME") == getString(R.string.theme_orange)) {
            super.setTheme(R.style.AppThemeOrange)
        } else if (tinyDB.getString("APP_THEME") == getString(R.string.theme_red)) {
            super.setTheme(R.style.AppThemeRed)
        } else if (tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow)) {
            super.setTheme(R.style.AppThemeYellow)
        } else if (tinyDB.getString("APP_THEME") == getString(R.string.theme_green)) {
            super.setTheme(R.style.AppThemeGreen)
        } else if (tinyDB.getString("APP_THEME") == getString(R.string.theme_gray)) {
            super.setTheme(R.style.AppThemeGray)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                applicationContext
            )
        checkTheme()
        setContentView(R.layout.activity_preview_cv)

        dialog = ProgressDialog(this)
        dialog.setTitle("Loading CV")
        dialog.setCancelable(true)
        dialog.setMessage("Please Wait While we are preparing your CV")

        modelMain = CvMainModel()
        modelMain = cvMainModel

        val toolbar: Toolbar =
            findViewById(R.id.toolbarPreviewCV)
        setSupportActionBar(toolbar)
        val txtToolbar: TextView = toolbar.findViewById(R.id.textPreviewCV)
        txtToolbar.text = modelMain.personInfo.fileName!!.capitalize()

        dialog.show()

        previewCV()
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
            }
        }

        btnExport = toolbar.findViewById(R.id.ExportCv)
        btnExport.setOnClickListener {
            btnExportClicked()
        }
    }

    private lateinit var btnExport: Button

    fun btnExportClicked() {
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

    private fun getOPFile(): File? {
        val mediaStorageDir =
            File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/Resume/Images/")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        return File(mediaStorageDir.path + File.separator + modelMain.personInfo.fileName + "_DRLTC.jpg")
    }

    private fun savePng() {
        val picture: Picture = webView.capturePicture()
        val b = Bitmap.createBitmap(
            picture.width,
            picture.height,
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        picture.draw(c)

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(getOPFile())
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            showFilesDialogImages()
        } catch (e: Exception) {
            println("-----error--$e")
        }
    }

    private fun showFilesDialogPdf() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_file_converted, null)
        dialogBuilder.setView(dialogView)
        val createBt: Button = dialogView.findViewById(R.id.btnViewNow)
        val cancelBt: Button = dialogView.findViewById(R.id.btnlater)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {
            tinyDB.putBoolean("pdf", false)
            val intent = Intent(this, Saved_Cv_Pdf_Activity::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()
        }

        cancelBt.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun showFilesDialogImages() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_file_converted, null)
        dialogBuilder.setView(dialogView)
        val createBt: Button = dialogView.findViewById(R.id.btnViewNow)
        val cancelBt: Button = dialogView.findViewById(R.id.btnlater)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {
            tinyDB.putBoolean("pdf", true)
            val intent = Intent(this, Saved_Cv_Pdf_Activity::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()
        }

        cancelBt.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun previewCV() {
        webView = findViewById(R.id.webView)
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        val htmlContent = StringBuilder()
        webView.setInitialScale(100)
        htmlContent.append(
            String.format(
                "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta name=\"viewport\" content='width=device-width, initial-scale=1.0,text/html,charset=utf-8' >" +
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
                    ".c26{border-right-style: solid;border-bottom-color: #3C3F41;border-top-width: 0pt;border-right-width: 0pt;border-left-color: #3C3F41;vertical-align: top;border-right-color: #3C3F41;border-left-width: 0pt;background: #3C3F41;border-top-style: solid;border-left-style: solid;border-bottom-width: 0pt;width: 196.3pt;border-top-color: #3C3F41;background: #3C3F41;border-bottom-style: solid}" +
                    ".c4{ border-right-style: solid;            border-bottom-color: #C7CBD1;           border-top-width: 0pt;border-right-width: 0pt;margin-top: 20pt;            border-left-color: #C7CBD1;            vertical-align: top;            margin-left: 15pt;padding-left: 35pt;border-right-color: #C7CBD1;            border-left-width: 0pt;  border-top-style: solid;            border-left-style: solid;            border-bottom-width: 0pt;            background: #C7CBD1;            width: 307.7pt;          border-top-color: #C7CBD1;            border-bottom-style: solid}" +
                    ".c16{ color: #FFFFFF;            font-weight: 700;            text-decoration: none;            vertical-align: baseline;            font-size: 12pt;            align-items: center;            text-align: center;            font-family: \"Raleway\";            font-style: normal}" +
                    ".c7{color: #313133;            font-size: 10pt;            font-family: \"Lato\";            text-align: center}" +
                    ".c13{ color: #000000;    font-weight: 700;    text-decoration: none;    vertical-align: baseline;    font-size: 10pt;    font-family: \"Lato\";    font-style: normal;}" +
                    ".c1{color: #666666;    font-family: \"Lato\";}" +
                    ".c19{color: #000000;    font-weight: 400;    text-decoration: none;    vertical-align: baseline;    font-size: 6pt;    font-family: \"Lato\";   font-style: normal}" +
                    ".c20{color: #FFFFFF; text-decoration: none; vertical-align: baseline; font-size: 11pt; text-align: center; font-family: \"Raleway\"; font-style: normal}" +
                    ".c6{padding-bottom: 0pt;            line-height: 1.3;            margin-left: 30pt;            margin-right: 20pt;            text-align: left}" +
                    ".c32{padding-top: 5pt;    padding-bottom: 0pt;    line-height: 1.15;    text-align: left}" +
                    ".c0{padding-top: 10pt;    line-height: 1.0;   margin-left: 10pt;    text-align: left;}" +
                    ".c22{padding-bottom: 0pt;            color: #2F3A40;            text-align: left}" +
                    ".c221 {padding-bottom: 0pt;  color: #2F3A40;     background: #0084FF;     text-align: center;           color: #ffffff}" +
                    ".c10{color: #d44500;    text-decoration: none;    vertical-align: baseline;    font-style: normal}" +
                    ".c2{text-align: left}" +
                    ".c33{padding-top: 3pt;    padding-bottom: 0pt;    line-height: 1.0;    text-align: left}" +
                    ".c9{padding-top: 0pt;            padding-bottom: 0pt;            align-items: center;            text-align: center;            border: 1px solid #ffffff; border-collapse: collapse; padding-top: 10px;padding-bottom: 10px; margin-left: 30pt;margin-right: 30pt}" +
                    ".c99 {  padding-bottom: 0pt;            line-height: 1.5;            margin-left: 20pt;            margin-right: 20pt;            text-align: left}" +
                    ".c23{border-spacing: 0;    border-collapse: collapse;    margin: 0 auto;    background: #d9dae6}" +
                    ".c30{color: #000000;    text-decoration: none;    vertical-align: baseline;    font-style: normal}" +
                    ".c3{padding-bottom: 0pt;    text-align: left;}" +
                    ".c14{padding-top: 16pt;    padding-bottom: 0pt;    line-height: 1.15;    text-align: left}" +

                    ".c28{padding-top: 6pt;    padding-bottom: 0pt;    line-height: 1.0;    text-align: left}" +
                    ".subtitle2 {            padding-top: 3pt;            color: #000000;            font-size: 16pt;            padding-bottom: 0pt;            font-family: \"Raleway\";          line-height: 1.0;            page-break-after: avoid;            orphans: 2;            margin-left: 20pt;            widows: 2;            text-align: left        }" +
                    ".c71 {            color: #000000;            font-size: 10pt;            font-family: \"Lato\";            text-align: center;        }" +

                    ".c18{font-size: 9pt;    font-family: \"Lato\";    font-weight: 400}" +

                    ".c24{font-size: 14pt;    font-family: \"Lato\";    font-weight: 700}" +

                    ".c8{font-size: 10pt;    font-family: \"Lato\";    font-weight: 400}" +

                    ".c5{font-size: 11pt;    font-family: \"Lato\";    font-weight: 400}" +

                    ".c31{background-color: #d9dae6;    max-width: 504pt;    padding: 36pt 54pt 36pt 54pt}" +

                    ".c35{font-weight: 700;    font-size: 24pt;    font-family: \"Raleway\"}" +

                    ".c11{orphans: 2;    widows: 2;    height: 11pt}" +
                    ".c21{height:auto}" +

                    ".c15{height:auto}" +
                    ".c27{height:auto}" +
                    ".c211 {    height: 40pt}" +
                    ".c34{height:auto}" +
                    ".c29{height:auto}" +
                    ".c25{  font-size: 11pt;            font-style italic}" +
                    ".c251{font-size: 9pt;            font-style:italic}" +
                    ".c12{page-break-after:avoid}" +
                    ".c17{height:265pt}" +
                    ".c91{  line-height: 1.15; color: #313133; brder-radius: 10pt; font-family: \"helvetica\"; background: #C7CBD1; margin-right: 60pt; margin-left: 20pt; text-align: center; margin-top: 10pt; border: 0.7px solid black; border-collapse: collapse; padding-top: 10px; padding-bottom: 10px}" +
                    ".c161{ color: #313133;            font-weight: 100;            font-size: 12pt;            font-family: \"Raleway\"}" +
                    "hr.new6 {            border: 1px solid #717171;            border-radius: 1px;        }" +
                    ".c68{    padding-bottom: 10pt;    line-height: 1.0;    text-align: left;}" +

                    ".title{ padding-top: 6pt; color: #FFFFFF; font-weight: 700; font-size: 18pt; padding-bottom: 0pt; font-family: \"Raleway\"; line-height: 1.0; page-break-after: avoid; orphans: 2; widows: 2; text-align: center}" +
                    ".subtitle{padding-top: 3pt; color: #FFFFFF; font-size: 16pt; padding-bottom: 0pt; font-family: \"Raleway\"; line-height: 1.0; page-break-after: avoid; orphans: 2; widows: 2; text-align: center}" +
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
                    "            <tbody>\n"
            )
        )

        htmlContent.append(
            String.format(
                "<tr class=\"c21\">\n" +
                    "                    <th class=\"c26\" style=\"margin-top: 50px;\" colspan=\"1\" rowspan=\"1\">\n"
            )
        )

        val file: File = File(modelMain.personInfo.imagePath.toString())
        val name = file.name
        val female = "file:///android_res/drawable/female.png"
        val male = "file:///android_res/drawable/male.png"

        if (modelMain.personInfo.imagePath.equals("")) {
            if (modelMain.personInfo.gender.equals("Male")) {
                htmlContent.append(
                    String.format(
                        "<p class=\"c68\"></p>" +
                            "<p class=\"c68\"></p>" +
                            "                        <p class=\"c6 c12 title\"><span>%s</span></p>" +
                            "                        <p class=\"c33 subtitle\"><span class=\"c20\">%s</span></p>" +
                            "<div style=\"margin-top: 10pt;height: 250px;margin-left: 20pt;margin-right: 20pt; background: #3C3F41;align-items: center;\">" +
                            "                            <img src=\"%s\" style=\"width: 166pt; height: 220px;border-radius: 100pt\">" +
                            "                        </div>",
                        modelMain.personInfo.fullName,
                        modelMain.personInfo.fatherName,
                        male

                    )
                )
            } else if (modelMain.personInfo.gender.equals("Female")) {
                htmlContent.append(
                    String.format(
                        "<p class=\"c68\"></p>" +
                            "<p class=\"c68\"></p>" +
                            "                        <p class=\"c6 c12 title\"><span>%s</span></p>" +
                            "                        <p class=\"c33 subtitle\"><span class=\"c20\">%s</span></p>" +
                            "<div style=\"margin-top: 10pt;height: 250px;margin-left: 20pt;margin-right: 20pt; background: #3C3F41;align-items: center;\">" +
                            "                            <img src=\"%s\" style=\"width: 166pt; height: 220px;border-radius: 100pt\">" +
                            "                        </div>",
                        modelMain.personInfo.fullName,
                        modelMain.personInfo.fatherName,
                        female

                    )
                )
            } else {
                htmlContent.append(
                    String.format(
                        "<p class=\"c68\"></p>" +
                            "<p class=\"c68\"></p>" +
                            "                        <p class=\"c6 c12 title\"><span>%s</span></p>" +
                            "                        <p class=\"c33 subtitle\"><span class=\"c20\">%s</span></p>" +
                            "<div style=\"margin-top: 10pt;height: 250px;margin-left: 20pt;margin-right: 20pt; background: #3C3F41;align-items: center;\">" +
                            "                            <img src=\"%s\" style=\"width: 166pt; height: 220px;border-radius: 100pt\">" +
                            "                        </div>",
                        modelMain.personInfo.fullName,
                        modelMain.personInfo.fatherName,
                        male

                    )
                )
            }
        } else {
            htmlContent.append(
                String.format(
                    "<p class=\"c68\"></p>" +
                        "<p class=\"c68\"></p>" +
                        "                        <p class=\"c6 c12 title\"><span>%s</span></p>" +
                        "                        <p class=\"c33 subtitle\"><span class=\"c20\">%s</span></p>" +
                        "<div style=\"margin-top: 10pt;height: 250px;margin-left: 20pt;margin-right: 20pt; background: #3C3F41;align-items: center;\">" +
                        "                            <img src=\"file://%s\" style=\"width: 166pt; height: 220px;border-radius: 100pt\">" +
                        "                        </div>",
                    modelMain.personInfo.fullName,
                    modelMain.personInfo.fatherName,
                    file.absolutePath

                )
            )
        }

        htmlContent.append(
            String.format(
                "" +
                    "                        <h1 class=\"c9\">" +
                    "                            <span class=\"c16\" style=\"padding-top: 8px;padding-bottom: 8px;\">Objective</span>" +
                    "                        </h1>" +
                    "                        <p class=\"c6\">" +
                    "                            <span>" +
                    "                                <span class=\"c251\" style=\"font-size: 9pt;color: #ffffff;margin-top: 2px;margin-bottom: 0px;margin-left: 5pt;\">asdadadasdadadasdasd%s</span>" +
                    "                            </span>" +
                    "                        </p>" +
                    "",
                modelMain.personInfo.objective
            )
        )

        htmlContent.append(
            String.format(
                "" +
                    " <p class=\"c68\"></p>" +
                    "                        <h1 class=\"c9\">" +
                    "                            <span class=\"c16\">Contact Details</span>" +
                    "                        </h1>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-envelope \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Email</span> <br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-phone \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Phone</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-location-arrow\" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Address</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-male\" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Gender</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-id-badge \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">National ID</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-calendar \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Date Of Birth</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-calendar \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Date Of Birth</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>" +
                    "                        <p class=\"c6\" style=\"margin-top: 3pt;\">" +
                    "                            <i class=\"fa fa-globe \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\">" +
                    "                                <span class=\"c25\" style=\"color: #ffffff;margin-left: 5pt;font-style: italic;\">Nationality</span><br><span class=\"c251\" style=\"color: #ffffff;margin-left: 19pt;\">%s</span></i>" +
                    "                        </p>",
                modelMain.personInfo.emailID,
                modelMain.personInfo.fullNumber,
                modelMain.personInfo.address,
                modelMain.personInfo.gender,
                modelMain.personInfo.cnic,
                modelMain.personInfo.dateOfBirth,
                modelMain.personInfo.nationality,
                "Status : " + modelMain.personInfo.maritalStatus

            )
        )

        if (!tinyDB.getBoolean(Constants.skipLang)) {
            htmlContent.append(
                String.format(
                    "" +
                        "" +
                        "                        <p class=\"c68\"></p>" +
                        "                        <h1 class=\"c9\">" +
                        "                            <span style=\"color: #ffffff;\">Languages</span>" +
                        "                        </h1>" +
                        "                        <p class=\"c6\">" +
                        "                            <span class=\"c251\" style=\"color: #ffffff;\">%s</span>" +
                        "                        </p>" +
                        "                        <p class=\"c68\"></p>" +
                        "",
                    modelMain.personInfo.language
                )
            )
        }
        if (!tinyDB.getBoolean(Constants.skipAwards)) {
            htmlContent.append(
                String.format(
                    "" +
                        "" +
                        "                        <p class=\"c68\"></p>" +
                        "                        <h1 class=\"c9\">" +
                        "                            <span style=\"color: #ffffff;\">Achievements</span>" +
                        "                        </h1>" +
                        "                        <p class=\"c6\">" +
                        "                            <span class=\"c251\" style=\"color: #ffffff;\">%s</span>" +
                        "                        </p>" +
                        "                        <p class=\"c68\"></p>" +
                        "",
                    modelMain.personInfo.awards
                )
            )
        }

        if (!tinyDB.getBoolean(Constants.skipRef)) {
            htmlContent.append(
                String.format(

                    "                        <h1 class=\"c9\">" +
                        "                            <span style=\"color: #ffffff;\">Reference</span>" +
                        "                        </h1>" +
                        "                        <p class=\"c6\">" +
                        "                            <span class=\"c251\" style=\"color: #ffffff;\">%s</span>" +
                        "                        </p>" +
                        "                        <p class=\"c68\"></p>" +
                        "",
                    modelMain.personInfo.reference
                )
            )
        }

        htmlContent.append(
            String.format(
                "                    </th>\n" +
                    "                    <td class=\"c4\" colspan=\"1\" rowspan=\"1\">\n"
            )
        )

        if (!tinyDB.getBoolean(Constants.skipExp)) {
            if (modelMain.experienceInfo != null) {
                htmlContent.append(
                    String.format(
                        "<h1 class=\"c91 subtitle2\" style=\"margin-top: 40pt;\">" +
                            "                            <span> Work Experience </span>" +
                            "                        </h1>" +
                            ""

                    )
                )
            }

            if (modelMain.experienceInfo != null) {
                for (model in modelMain.experienceInfo!!) {
                    htmlContent.append(
                        String.format(
                            "" +
                                "<h2 class=\"c3\" style=\"font-size: 12px; margin-top: 2px;font-style: bold;margin-bottom: 5px;margin-left: 20pt\">" +
                                "                            <span>" +
                                "                                <p class=\"c251\" style=\"font-size: 12pt; color: #FF1A3A;\">( %s )</p>" +
                                "                            </span>" +
                                "                        </h2>" +
                                "                        <p class=\"c22\">" +
                                "                            <span>" +
                                "                                <p class= \"c251\" style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 20pt;font-weight:bold;\">%s</p>" +
                                "                            </span>" +
                                "                        </p>" +
                                "                        <p class=\"c22\"><span>" +
                                "                                <p class= \"c251\" style=\"font-size: 9pt;margin-top: 2px;margin-left: 20pt\">%s</p>" +
                                "                        </p>",
                            "From " + model.startDate + " to " + model.endDate,
                            model.companyName,
                            model.designation

                        )
                    )
                }
            }
        }
        if (!tinyDB.getBoolean(Constants.skipQua)) {
            if (modelMain.qualificationEntity != null) {
                htmlContent.append(
                    String.format(
                        "<h1 class=\"c91\" style=\"margin-top: 10pt;\">" +
                            "                            <span> Qualification </span>" +
                            "                        </h1>" +
                            ""

                    )
                )
            }
            if (modelMain.qualificationEntity != null) {
                for (model in modelMain.qualificationEntity!!) {
                    htmlContent.append(
                        String.format(
                            "" +
                                "" +
                                "                        <h2 class=\"c3\" style=\"font-size: 12px; margin-top: 2px;font-style: bold;margin-bottom: 5px;margin-left: 20pt;\">" +
                                "                            <span>" +
                                "                                <p class= \"c251\" style=\"font-size: 12pt;color: #FF1A3A;\">( %s )</p>" +
                                "                            </span>" +
                                "                        </h2>" +
                                "                        <p class=\"c22\">" +
                                "                            <span>" +
                                "                                <p class= \"c251\"  style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 20pt;font-weight:bold; \">%s</p>" +
                                "                            </span>" +
                                "                        </p>" +
                                "                        <p class=\"c22\">" +
                                "                            <span>" +
                                "                                <p class= \"c251\"  style=\"font-size: 10pt;margin-top: 2px;margin-left: 20pt\">%s</p>" +
                                "                            </span>" +
                                "                        </p>" +
                                "                        <p class=\"c22\">" +
                                "                            <span>" +
                                "                                <p class= \"c251\" style=\"font-size: 10pt;margin-top: 2px;margin-left: 20pt\">%s</p>" +
                                "                            </span>" +
                                "                        </p>" +
                                "" +
                                "",
                            model.endDate,
                            model.schoolName,
                            model.subject,
                            model.marks
                        )
                    )
                }
            }
        }
        if (!tinyDB.getBoolean(Constants.skipProj)) {
            if (modelMain.projectsEntity != null) {
                htmlContent.append(
                    kotlin.String.format(
                        "" +
                            "" +
                            "<h1 class=\"c91\" style=\"margin-top: 10pt;\">" +
                            "                            <span > Projects </span>" +
                            "                        </h1>"
                    )
                )
            }
            if (modelMain.projectsEntity != null) {
                for (model in modelMain.projectsEntity!!) {
                    htmlContent.append(
                        kotlin.String.format(
                            "                        <p class=\"c22\" style=\"margin-bottom: 1px; margin-left: 20pt; margin-right:30pt;\">" +
                                "                            <span class=\"c251\">%s</span>" +
                                "                        </p>",
                            model.projectTitle

                        )
                    )
                }
            }
        }

        if (!tinyDB.getBoolean(Constants.skipTech)) {
            if (modelMain.skillsEntity != null) {
                htmlContent.append(
                    kotlin.String.format(
                        "" +
                            "<h1 class=\"c91\" style=\"margin-top: 10pt;\">" +
                            "                            <span > Skills </span>" +
                            "                        </h1>"
                    )
                )
            }
            for (model in modelMain.skillsEntity!!) {
                htmlContent.append(
                    kotlin.String.format(
                        "" +

                            "                        <p class=\"c22\" style=\"margin-bottom: 1px; margin-left: 20pt; margin-right:30pt;\">" +
                            "                            <span class=\"c251\">%s</span>" +
                            "                        </p>",
                        model.skillTitle

                    )
                )
            }
        }

        htmlContent.append(
            String.format(
                "                        <p class=\"c68\"></p>" +
                    "</td>\n" +
                    "                </tr>\n" +
                    "            </tbody>\n" +
                    "        </table>\n" +
                    "        <p class=\"c2 c11\"><span class=\"c30 c5\"></span></p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n"
            )
        )

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: kotlin.String) {
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        }

        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null)
    }

    private fun createWebPrintJob(webView: WebView) {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = modelMain.personInfo.fileName + "_Resume"
        val printAdapter = webView.createPrintDocumentAdapter()
        val builder = PrintAttributes.Builder()

        val custom = PrintAttributes.MediaSize.ISO_A4
        builder.setMediaSize(custom)
        val printJob = printManager.print(
            jobName,
            printAdapter,
            builder.build()
        )
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
            val fileName = modelMain.personInfo.fileName + "_" + modelMain.personInfo.id + "_LWB.pdf"
            val printAdapter = webView.createPrintDocumentAdapter(fileName)
            val printAttributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()
            var file: File? = null
            file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(getExternalFilesDir(null).toString() + "/CVMaker/Resume/Pdf/")
            } else {
                File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/Resume/Pdf/")
            }

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
                        showFilesDialogPdf()
                    }

                    override fun onFailure(errorMsg: kotlin.String?) {
                        allowSave = true
                        Toast.makeText(
                            applicationContext,
                            kotlin.String.format(
                                "Failed"
                            ),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST
            )
        }
    }

}
