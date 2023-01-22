package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCV

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import androidx.core.content.ContextCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.Saved_Cv_Pdf_Activity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.ModelNewMain

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.io.File
import java.io.FileOutputStream
import java.lang.String
import java.util.*


class PreviewGrayPinkActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var tinyDB: TinyDB

    lateinit var dialog: ProgressDialog


    companion object {
        lateinit var modelMain: CvMainModel
    }

    lateinit var xModel: ModelNewMain
    private fun checkTheme() {
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                super.setTheme(R.style.AppTheme)
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                super.setTheme(R.style.AppThemeOrange)

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                super.setTheme(R.style.AppThemeRed)
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {

                super.setTheme(R.style.AppThemeYellow)

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                super.setTheme(R.style.AppThemeGreen)

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                super.setTheme(R.style.AppThemeGray)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()
        setContentView(R.layout.activity_preview_cv)
        dialog = ProgressDialog(this)
        dialog.setTitle("Loading CV")
        dialog.setCancelable(true)
        dialog.setMessage("Please Wait While we are preparing your CV")

        dialog.show()

        modelMain = CvMainModel()
        modelMain = cvMainModel

        val toolbar: Toolbar =
            findViewById(R.id.toolbarPreviewCV)
        setSupportActionBar(toolbar)
        val txtToolbar: TextView = toolbar.findViewById(R.id.textPreviewCV)
        txtToolbar.text = modelMain.personInfo.fileName!!.capitalize()
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
        return File(mediaStorageDir.path + File.separator + modelMain.personInfo.fileName + "_PINKGRA.jpg")
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
            fos = FileOutputStream(getOPFile())
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            showFilesDialogImages()
        } catch (e: Exception) {
            println("-----error--$e")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun previewCV() {
        webView = findViewById(R.id.webView)
        webView.settings.allowFileAccess = true;
        webView.settings.javaScriptEnabled = true;
        webView.settings.builtInZoomControls = true;
        webView.setInitialScale(100)
        val htmlContent = StringBuilder()
        htmlContent.append(
            String.format(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta name=\"viewport\" content='width=device-width, initial-scale=1.0,text/html,charset=utf-8' >" +
                        "<title>Resume</title>\n" +
                        "<meta charset=UTF-8>\n" +
                        "<link rel=\"shortcut icon\" href=https://ssl.gstatic.com/docs/documents/images/kix-favicon6.ico>\n" +
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
                        ".c26{border-right-style: solid; border-bottom-color: #2F3A40; border-top-width: 0pt; border-right-width: 0pt; border-left-color: #2F3A40; vertical-align: top; border-right-color: #2F3A40; border-left-width: 0pt; background: #2F3A40; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; width: 176.3pt; border-top-color: #2F3A40; border-bottom-style: solid}" +
                        ".c4{border-right-style: solid; border-bottom-color: #ffffff; border-top-width: 0pt; border-right-width: 0pt; margin-top: 20pt; border-left-color: #ffffff; vertical-align: top; border-right-color: #ffffff; border-left-width: 0pt; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; background: #ffffff; width: 327.7pt; border-top-color: #ffffff; border-bottom-style: solid}" +
                        ".c16{color: #ffffff;font-weight: 700;text-decoration: none;vertical-align: baseline;font-size: 12pt;font-family: \"Raleway\";font-style: normal}" +
                        ".c7{color: #ffffff;    font-size: 10pt;    font-family: \"Lato\";    text-align: center;}" +
                        ".c13{ color: #000000;    font-weight: 700;    text-decoration: none;    vertical-align: baseline;    font-size: 10pt;    font-family: \"Lato\";    font-style: normal;}" +
                        ".c1{color: #666666;    font-family: \"Lato\";}" +
                        ".c19{color: #000000;    font-weight: 400;    text-decoration: none;    vertical-align: baseline;    font-size: 6pt;    font-family: \"Lato\";   font-style: normal}" +
                        ".c20{color: #ffffff; font-weight: 700; text-decoration: none; vertical-align: baseline; font-size: 16pt; text-align: center; font-family: \"Raleway\"; font-style: normal}" +
                        ".c6{padding-bottom: 0pt;    line-height: 1.0;    margin-left: 10pt;    text-align: left;}" +
                        ".c32{padding-top: 5pt;    padding-bottom: 0pt;    line-height: 1.15;    text-align: left}" +
                        ".c0{padding-top: 10pt;    line-height: 1.0;   margin-left: 10pt;    text-align: left;}" +
                        ".c22{padding-bottom: 0pt;    color: #2F3A40;    text-align: left;}" +
                        ".c10{color: #d44500;    text-decoration: none;    vertical-align: baseline;    font-style: normal}" +
                        ".c2{text-align: left}" +
                        ".c33{padding-top: 3pt;    padding-bottom: 0pt;    line-height: 1.0;    text-align: left}" +
                        ".c9{padding-top: 6pt; padding-bottom: 6pt; background-image: linear-gradient(to right, #D97CA3, #447888); margin-right: 20pt; border-top-right-radius: 5pt; border-bottom-right-radius: 5pt; margin-left: -10pt; color: #ffffff text-align: center}" +
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
                        ".c27{height:auto}" +
                        ".c211 {    height: 40pt}" +
                        ".c34{height:auto}" +
                        ".c29{height:auto}" +
                        ".c25{font-size:10pt}" +
                        ".c12{page-break-after:avoid}" +
                        ".c17{height:265pt}" +
                        ".c41 {border-right-style: solid; border-bottom-color: #ffffff; border-top-width: 0pt; border-right-width: 0pt; margin-top: 20pt; border-left-color: #ffffff; vertical-align: top; border-right-color: #ff; border-left-width: 0pt; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; background: #ffffff; width: 327.7pt; border-top-color: #ffffff; border-bottom-style: solid}" +
                        ".c91{line-height: 1.15;font-style: bold; color: #ffffff; padding: 10px; margin-right: 50pt; border-radius: 10pt; background-image: linear-gradient(to right, #D97CA3, #447888);}" +
                        ".c161{color: #ffffff;font-style: bold; font-weight: 100; font-size: 12pt; font-family: \"Raleway\"}" +
                        ".c68{    padding-bottom: 10pt;    line-height: 1.0;    text-align: left;}" +

                        ".title{padding-top: 6pt; color: #ffffff; font-weight: 700; font-size: 30pt; padding-bottom: 0pt; font-family: \"Raleway\"; line-height: 1.0; page-break-after: avoid; orphans: 2; widows: 2; text-align: left}" +
                        ".subtitle{padding-top: 3pt; color: #ffffff; font-weight: 700; font-size: 16pt; padding-bottom: 0pt; font-family: \"Raleway\"; line-height: 1.0; page-break-after: avoid; orphans: 2; widows: 2; text-align: center}" +
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
                        "           <td class=\"c41\" style=\"width: 30pt;\">" +
                        "                        <div style=\"width: 30pt;height: 190px;background: #D97CA3;\"> </div>" +
                        "           </td>" +
                        "           <th class=\"c26\" colspan=\"1\" rowspan=\"1\" style=\"margin-left: -5pt;\">\n"
            )
        )


        val file: File = File(modelMain.personInfo.imagePath)
        val name = file.name
        val female = "file:///android_res/drawable/female.png"
        val male = "file:///android_res/drawable/male.png"


        if (modelMain.personInfo.imagePath.equals("")) {
            if (modelMain.personInfo.gender.equals("Male")) {

                htmlContent.append(
                    String.format(
                        "" +
                                "<img src=\"%s\" style=\"margin-top: 20pt;width: 150px; height: 150px; margin-left: 10px;border-radius: 100px; margin-right: 10px;\">\n",
                        male
                    )
                )
            } else if (modelMain.personInfo.gender.equals("Female")) {

                htmlContent.append(
                    String.format(
                        "" +
                                "<img src=\"%s\" style=\"margin-top: 20pt;width: 150px; height: 150px; margin-left: 10px;border-radius: 100px; margin-right: 10px;\">\n",
                        female
                    )
                )
            } else {
                htmlContent.append(
                    String.format(
                        "" +
                                "<img src=\"%s\" style=\"margin-top: 20pt;width: 150px; height: 150px; margin-left: 10px;border-radius: 100px; margin-right: 10px;\">\n",
                        male
                    )
                )
            }
        } else {
            htmlContent.append(
                String.format(
                    "" +
                            "<img src=\"file://%s\" style=\"margin-top: 20pt;width: 150px; height: 150px; margin-left: 10px;border-radius: 100px; margin-right: 10px;\">\n",
                    file.absolutePath


                )
            )

        }


        htmlContent.append(
            String.format(


                "                        <p class=\"c68\"></p>" +
                        "                        <p class=\"c68\"></p>" +
                        "                        <p class=\"c68\"></p>" +
                        "                        <h1 class=\"c9\" style=\"text-align: center;\">" +
                        "                            <span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Contact Details</span>" +
                        "                        </h1>" +
                        "                        <p class=\"c68\"></p>"
            )
        )

        htmlContent.append(
            String.format(
                "                        <p class=\"c6\"><span class=\"c7\">%s</span></p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 3pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 4pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 3pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 3pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 3pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 3pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c6\" style=\"margin-top: 3pt;\">\n" +
                        "                            <span class=\"c25\" style=\"color: #ffffff;\">%s</span>\n" +
                        "                        </p>\n" +
                        "                        <p class=\"c68\"></p>\n",
                modelMain.personInfo.emailID, tinyDB.getString("FullPhoneNumberUpdate"),
                modelMain.personInfo.address,
                modelMain.personInfo.gender,
                modelMain.personInfo.dateOfBirth,
                modelMain.personInfo.cnic,
                modelMain.personInfo.nationality,
                "Status : " + modelMain.personInfo.maritalStatus

            )
        )


        if (!tinyDB.getBoolean(Constants.skipTech)) {
            if (modelMain.skillsEntity != null) {
                htmlContent.append(
                    kotlin.String.format(
                        " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                                "<span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Skills</span>\n" +
                                "                        </h1>\n" +
                                "\n"
                    )
                )
            }
            for (model in modelMain.skillsEntity!!) {

                htmlContent.append(
                    kotlin.String.format(
                        " <p class=\"c6\" style=\"margin-bottom: 2px;\">" +
                                "<span class=\"c7\">%s</span>" +
                                "</p>",


                        model.skillTitle

                    )
                )
            }
        }


        htmlContent.append(
            String.format(

                "        <p class=\"68\"></p>",


                )
        )

        if (!tinyDB.getBoolean(Constants.skipLang)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                            "<span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Languages</span>\n" +
                            "                        </h1>\n" +
                            "\n"
                )
            )

            htmlContent.append(
                String.format(
                    " <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "<span class=\"c7\">%s</span>" +
                            "</p>" +
                            "        <p class=\"68\"></p>",
                    modelMain.personInfo.language


                )
            )
        }

        if (!tinyDB.getBoolean(Constants.skipProj)) {

            if (modelMain.projectsEntity != null) {
                htmlContent.append(
                    kotlin.String.format(
                        " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                                "<span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Projects</span>\n" +
                                "                        </h1>\n" +
                                "\n"
                    )
                )
            }
            if (modelMain.projectsEntity != null) {
                for (model in modelMain.projectsEntity!!) {
                    htmlContent.append(
                        kotlin.String.format(
                            " <p class=\"c6\" style=\"margin-bottom: 2px;\">" +
                                    "<span class=\"c7\">%s</span>" +
                                    "</p>",
                            model.projectTitle

                        )
                    )
                }
            }
        }
        htmlContent.append(
            java.lang.String.format(

                "        <p class=\"68\"></p>"


            )
        )

        if (!tinyDB.getBoolean(Constants.skipInterest)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                            "<span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Interest</span>\n" +
                            "                        </h1>\n" +
                            "\n"
                )
            )

            htmlContent.append(
                String.format(
                    " <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "<span class=\"c7\">%s</span>" +
                            "</p>" +
                            "        <p class=\"68\"></p>",
                    modelMain.personInfo.interest


                )
            )
        }
        if (!tinyDB.getBoolean(Constants.skipAwards)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                            "<span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Achievements</span>\n" +
                            "                        </h1>\n" +
                            "\n"
                )
            )

            htmlContent.append(
                String.format(
                    " <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "<span class=\"c7\">%s</span>" +
                            "</p>" +
                            "        <p class=\"68\"></p>",
                     modelMain.personInfo.awards


                )
            )
        }
        if (!tinyDB.getBoolean(Constants.skipRef)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                            "<span class=\"c16\" style=\"color: #ffffff; margin-bottom: 10px;text-align: center;\">Reference</span>\n" +
                            "                        </h1>\n" +
                            "\n"
                )
            )

            htmlContent.append(
                String.format(
                    " <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "<span class=\"c7\">%s</span>" +
                            "</p>" +
                            "        <p class=\"68\"></p>",
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

        htmlContent.append(
            String.format(
                " <div style=\"height: 190px;border-bottom-right-radius: 75px; align-items: center; background-image: linear-gradient(to right, #D97CA3, #447888);\">" +
                        "                            <p class=\"c68\"></p>" +
                        "                            <p class=\"c68\"></p>" +
                        "                            <p class=\"c68\"></p>" +
                        "                            <p class=\"c68\"></p>" +
                        "                            <p class=\"c6 c12 title\" style=\"text-align: center;\"><span>%s</span></p>" +
                        "                            <p class=\"c33 subtitle\"><span class=\"c20\">%s</span></p>" +
                        "                        </div>" +


                        "                        <p class=\"c68\"></p>" +
                        "                        <p class=\"c68\"></p>" +
                        "\n",
                modelMain.personInfo.fullName,
                modelMain.personInfo.fatherName

            )
        )
        if (modelMain.experienceInfo  != null) {
            htmlContent.append(
                String.format(
                    "                        <h1 class=\"c91\" style=\"margin-left: 20px;\">\n" +
                            "                            <span class=\"c16\" style=\"font-style: bold; border-radius: 5pt;padding-left: 15pt;padding-right: 15pt;padding-top: 5pt;padding-bottom: 5pt;\">Objective</span>\n" +
                            "                        </h1>\n"
                )
            )
        }


        htmlContent.append(
            String.format(

                "                        <p class=\"c22\">\n" +
                        "                            <span>\n" +
                        "                                <p style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 28pt;font-weight:bold;\">%s</p>\n" +
                        "                            </span>\n" +
                        "                        </p>\n" +
                        "\n",
                modelMain.personInfo.objective
            )
        )



        if (!tinyDB.getBoolean(Constants.skipExp)) {
            if (modelMain.experienceInfo  != null) {
                htmlContent.append(
                    String.format(
                        "                        <h1 class=\"c91\" style=\"margin-left: 20px;\">\n" +
                                "                            <span class=\"c16\" style=\"font-style: bold; border-radius: 5pt;padding-left: 15pt;padding-right: 15pt;padding-top: 5pt;padding-bottom: 5pt;\">Experience</span>\n" +
                                "                        </h1>\n"
                    )
                )
            }


            if (modelMain.experienceInfo  != null) {
                for (model in modelMain.experienceInfo !!) {
                    htmlContent.append(
                        String.format(
                            "<h2 class=\"c3\" style=\"font-size: 12px; margin-top: 2px;font-style: bold;margin-bottom: 5px;margin-left: 28pt\">\n" +
                                    "                            <span>\n" +
                                    "                                <p style=\"font-size: 12pt;\">%s</p>\n" +
                                    "                            </span>\n" +
                                    "                        </h2>\n" +
                                    "                        <p class=\"c22\">\n" +
                                    "                            <span>\n" +
                                    "                                <p style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 28pt;font-weight:bold;\">%s</p>\n" +
                                    "                            </span>\n" +
                                    "                        </p>\n" +
                                    "                        <p class=\"c22\"><span>\n" +
                                    "                                <p style=\"font-size: 9pt;margin-top: 2px;margin-left: 28pt\">%s</p>\n" +
                                    "                        </p>\n" +
                                    "                        <hr>\n" +
                                    "\n",
                            model.companyName,
                            model.designation,
                            "From :" + model.startDate + "  To : " + model.endDate
                        )
                    )
                }
            }
        }
        if (!tinyDB.getBoolean(Constants.skipQua)) {
            if (modelMain.qualificationEntity != null) {
                htmlContent.append(
                    String.format(
                        "  <p class=\"c68\"></p>\n" +
                                "                        <h1 class=\"c91\" style=\"margin-left: 20px;\">\n" +
                                "                            <span class=\"c16\" style=\"font-style: bold;border-radius: 5pt;padding-left: 15pt;padding-right: 15pt;padding-top: 5pt;padding-bottom: 5pt;\">Qualification</span>\n" +
                                "                        </h1>\n" +
                                "\n"
                    )
                )
            }

            if (modelMain.qualificationEntity != null) {
                for (model in modelMain.qualificationEntity!!) {
                    htmlContent.append(
                        String.format(
                            "<h2 class=\"c3\" style=\"font-size: 12px; margin-top: 2px;font-style: bold;margin-bottom: 5px;margin-left: 28pt\">\n" +
                                    "                            <span>\n" +
                                    "                                <p style=\"font-size: 12pt;\">%s</p>\n" +
                                    "                            </span>\n" +
                                    "                        </h2>\n" +
                                    "                        <p class=\"c22\">\n" +
                                    "                            <span>\n" +
                                    "                                <p style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 28pt;font-weight:bold;\">%s</p>\n" +
                                    "                            </span>\n" +
                                    "                        </p>\n" +
                                    "                        <p class=\"c22\"><span>\n" +
                                    "                                <p style=\"font-size: 9pt;margin-top: 2px;margin-left: 28pt\">%s</p>\n" +
                                    "                        </p>\n" +
                                    "                        <p class=\"c22\"><span>\n" +
                                    "                                <p style=\"font-size: 9pt;margin-top: 2px;margin-left: 28pt\">%s</p>\n" +
                                    "                        </p>\n" +
                                    "                        <hr>\n" +
                                    "\n",
                            model.getSchoolName(),
                            model.subject,
                            model.getMarks(),
                            model.getEndDate()
                        )
                    )
                }
            }
        }

        htmlContent.append(
            String.format(
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
        webView.settings.javaScriptEnabled = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: kotlin.String) {
                if (dialog.isShowing()) {
                    dialog.dismiss()
                }
            }
        }
        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null)
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

    private fun createWebPrintJob(webView: WebView) {

        // Get a PrintManager instance
        val printManager = this
            .getSystemService(PRINT_SERVICE) as PrintManager

        // Get a print adapter instance
        val printAdapter = webView.createPrintDocumentAdapter()

        // Create a print job with name and adapter instance
        val jobName = modelMain.personInfo.fileName + "_Resume"
        val printJob = printManager.print(
            jobName, printAdapter,
            PrintAttributes.Builder().build()
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
            val fileName = modelMain.personInfo.fileName + "_" + modelMain.personInfo.id + "_DLG.pdf"
            val printAdapter = webView.createPrintDocumentAdapter(fileName)
            val printAttributes = PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()
            var file: File? = null
            file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File( getExternalFilesDir(null).toString() + "/CVMaker/Resume/Pdf/")
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
}