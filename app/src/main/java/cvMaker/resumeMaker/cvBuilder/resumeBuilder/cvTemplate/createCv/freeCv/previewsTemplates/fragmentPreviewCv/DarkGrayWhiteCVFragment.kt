package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.fragmentPreviewCv

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelNewMain
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewDark_GrayWhiteActivity
import java.io.File
import java.lang.String

class DarkGrayWhiteCVFragment : Fragment() {

    lateinit var dialog: ProgressDialog
    lateinit var tinyDB: TinyDB
    lateinit var webView: WebView

    companion object {
        lateinit var modelMain: CvMainModel
    }

    lateinit var xModel: ModelNewMain
    val htmlContent = StringBuilder()

    override fun onPause() {
        super.onPause()
        webView.destroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preview_all_cv, container, false)
        val btnFullScreen: ImageButton = view.findViewById(R.id.btnFullScreenPreview)
        btnFullScreen.setOnClickListener {
            val intent = Intent(requireContext(), PreviewDark_GrayWhiteActivity::class.java)
            startActivity(intent)
        }
        val btnNext = view.findViewById(R.id.btnNextTemplate) as Button
        val btnBack = view.findViewById(R.id.btnBackTemplate) as Button

        btnNext.setOnClickListener {
            val intent = Intent(requireContext(), PreviewDark_GrayWhiteActivity::class.java)
            startActivity(intent)
        }
        btnBack.setOnClickListener {
            activity?.finish()
        }
        tinyDB =
            TinyDB(
                requireContext()
            )
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6A6A6A"))
            }
        }
        dialog = ProgressDialog(context)
        dialog.setTitle("Loading")
        dialog.setCancelable(true)
        dialog.setMessage("Please Wait While we load your data in CV")
        dialog.show()
        modelMain = CvMainModel()
        modelMain = cvMainModel

        if (isAdded) {
            previewCV(view)
        }

        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun previewCV(view: View) {
        webView = view.findViewById(R.id.WebView)
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.builtInZoomControls = false
        webView.settings.displayZoomControls = false
        webView.setInitialScale(100)
        val htmlContent = StringBuilder()
        htmlContent.append(
            String.format(
                "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
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
                    ".c26{border-right-style: solid; border-bottom-color: #454545; border-top-width: 0pt; border-right-width: 0pt; border-left-color: #454545; vertical-align: top; border-right-color: #454545; border-left-width: 0pt; background: #454545; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; width: 176.3pt; border-top-color: #454545; border-bottom-style: solid}" +
                    ".c4{border-right-style: solid; border-bottom-color: #ffffff; border-top-width: 0pt; border-right-width: 0pt; margin-top: 10pt; border-left-color: #ffffff; vertical-align: top; border-right-color: #ffffff; border-left-width: 0pt; border-top-style: solid; border-left-style: solid; border-bottom-width: 0pt; background: #ffffff; width: 327.7pt; border-top-color: #D7D7D7; border-bottom-style: solid}" +
                    ".c16{color: #000000;font-weight: 700;text-decoration: none;vertical-align: baseline;font-size: 12pt;font-family: \"Raleway\";font-style: normal}" +
                    ".c7{color: #ffffff;    font-size: 10pt;    font-family: \"Lato\";    text-align: center;}" +
                    ".c13{ color: #000000;    font-weight: 700;    text-decoration: none;    vertical-align: baseline;    font-size: 10pt;    font-family: \"Lato\";    font-style: normal;}" +
                    ".c1{color: #666666;    font-family: \"Lato\";}" +
                    ".c19{color: #000000;    font-weight: 400;    text-decoration: none;    vertical-align: baseline;    font-size: 6pt;    font-family: \"Lato\";   font-style: normal}" +
                    ".c20{color: #000000; font-weight: 700; text-decoration: none; vertical-align: baseline; font-size: 16pt; font-family: \"Raleway\"; font-style: normal}" +
                    ".c6{padding-bottom: 0pt;            line-height: 1.0;            margin-left: 15pt;            margin-right: 15pt;            text-align: left}" +
                    ".c32{padding-top: 5pt;    padding-bottom: 0pt;    line-height: 1.15;    text-align: left}" +
                    ".c0{padding-top: 10pt;    line-height: 1.0;   margin-left: 10pt;    text-align: left;}" +
                    ".c22{padding-bottom: 0pt;    color: #2F3A40;    text-align: left;}" +
                    ".c10{color: #d44500;    text-decoration: none;    vertical-align: baseline;    font-style: normal}" +
                    ".c2{text-align: left}" +
                    ".c33{padding-top: 3pt;    padding-bottom: 0pt;    line-height: 1.0;    text-align: left}" +
                    ".c9{padding-top: 5pt; padding-bottom: 5pt; background: #454545; margin: 0pt; color: #0067DE; text-align: center}" +
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
                    ".c91{line-height: 1.15;            color: #ffffff;             padding:10px;}" +
                    ".c161{color: #0067DE;            font-weight: bolder;            font-size: 18pt;            background: #ffffff;            font-family: \"Raleway\"}" +
                    ".c68{    padding-bottom: 10pt;    line-height: 1.0;    text-align: left;}" +

                    ".title{padding-top: 6pt; color: #0067DE; font-weight: 700; font-size: 30pt; padding-bottom: 0pt; font-family: \"Raleway\"; line-height: 1.0; page-break-after: avoid; orphans: 2; margin-left: 20pt; widows: 2; text-align: left}" +
                    ".subtitle{padding-top: 3pt; color: #000000; font-weight: 700; font-size: 16pt; padding-bottom: 0pt; font-family: \"Raleway\"; line-height: 1.0; page-break-after: avoid; orphans: 2; widows: 2; margin-left: 20pt}" +
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

        val file = File(modelMain.personInfo.imagePath)
        val female = "file:///android_res/drawable/female.png"
        val male = "file:///android_res/drawable/male.png"

        if (modelMain.personInfo.imagePath.equals("")) {
            if (modelMain.personInfo.gender.equals("Male")) {
                htmlContent.append(
                    String.format(
                        "<img src=\"%s\" style=\"width: 150px; height: 150px; margin-left: 10px; margin-top: 30px; border-radius: 100px; margin-right: 10px;\">\n",
                        male
                    )
                )
            } else if (modelMain.personInfo.gender.equals("Female")) {
                htmlContent.append(
                    String.format(
                        "<img src=\"%s\" style=\"width: 150px; height: 150px; margin-left: 10px; margin-top: 30px; border-radius: 100px; margin-right: 10px;\">\n",
                        female
                    )
                )
            } else {
                htmlContent.append(
                    String.format(
                        "<img src=\"%s\" style=\"width: 150px; height: 150px; margin-left: 10px; margin-top: 30px; border-radius: 100px; margin-right: 10px;\">\n",
                        male
                    )
                )
            }
        } else {
            htmlContent.append(
                String.format(
                    "<img src=\"file://%s\" style=\"width: 150px; height: 150px; margin-left: 10px; margin-top: 30px; border-radius: 100px; margin-right: 10px;\">\n",
                    file.absolutePath
                )
            )
        }

        htmlContent.append(
            String.format(

                "                        <p class=\"c68\"></p>" +
                    "                        <h1 class=\"c9\" style=\"text-align: center;\">" +
                    "                            <span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Contact Details</span>" +
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
                modelMain.personInfo.emailID,
                tinyDB.getString("FullPhoneNumberUpdate"),
                modelMain.personInfo.address,
                modelMain.personInfo.gender,
                modelMain.personInfo.dateOfBirth,
                modelMain.personInfo.cnic,
                modelMain.personInfo.nationality, "Status : " + modelMain.personInfo.maritalStatus

            )
        )

        htmlContent.append(
            String.format(
                " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                    "<span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Objective</span>\n" +
                    "                        </h1>\n" +
                    "\n"
            )
        )

        htmlContent.append(
            String.format(
                " <p class=\"c6\" style=\"margin-bottom: 15px;\"><span class=\"c7\">%s</span></p>" +
                    "                        <p class=\"68\"></p>",
                modelMain.personInfo.objective

            )
        )

        if (!tinyDB.getBoolean(Constants.skipTech)) {
            if (modelMain.skillsEntity != null) {
                htmlContent.append(
                    java.lang.String.format(
                        " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                            "<span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Skills</span>\n" +
                            "                        </h1>\n" +
                            "\n"
                    )
                )
            }
            if (modelMain.skillsEntity != null) {
                for (model in modelMain.skillsEntity!!) {
                    htmlContent.append(
                        java.lang.String.format(
                            " <p class=\"c6\" style=\"margin-bottom: 2px;\">" +
                                "<span class=\"c7\">%s</span>" +
                                "</p>",
                            model.skillTitle
                        )
                    )
                }
            }
        }

        htmlContent.append(
            String.format(

                "        <p class=\"68\"></p>",
                modelMain.skillsEntity

            )
        )

        if (!tinyDB.getBoolean(Constants.skipLang)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                        "<span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Languages</span>\n" +
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

        htmlContent.append(
            String.format(

                "        <p class=\"68\"></p>"

            )
        )

        if (!tinyDB.getBoolean(Constants.skipInterest)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                        "<span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Interest</span>\n" +
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
                        "<span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Achievements</span>\n" +
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

        if (tinyDB.getBoolean(Constants.skipRef)) {
            htmlContent.append(
                String.format(
                    " <h1 class=\"c9\" style=\"text-align: center; margin-bottom: 15px;\">" +
                        "<span class=\"c16\" style=\"color: #0067DE; margin-bottom: 10px;text-align: center;\">Reference</span>\n" +
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
                "<p class=\"c68\"></p>" +
                    "                        <p class=\"c68\"></p>" +
                    "                        <p class=\"c68\"></p>" +
                    "                        <p class=\"c6 c12 title\" ><span>%s</span></p>" +
                    "                        <p class=\"subtitle\"><span>%s</span></p>" +
                    "                        <p class=\"c68\"></p>" +
                    "                        <p class=\"c68\"></p>" +
                    "\n",
                modelMain.personInfo.fullName,
                modelMain.personInfo.fatherName

            )
        )
        if (!tinyDB.getBoolean(Constants.skipExp)) {
            htmlContent.append(
                String.format(

                    "                        <h1 class=\"c91\" style=\"margin-left: 20px;\">\n" +
                        "                            <span class=\"c161\" style=\"border-radius: 5pt;padding-left: 15pt;padding-right: 15pt;padding-top: 5pt;padding-bottom: 5pt;\">Experience</span>\n" +
                        "                        </h1>\n"
                )
            )

            if (modelMain.experienceInfo != null) {
                for (model in modelMain.experienceInfo!!) {
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
                            modelMain.personInfo.objective,
                            model.designation,
                            "From :" + model.startDate + "  To : " + model.endDate
                        )
                    )
                }
            }
        }
        if (!tinyDB.getBoolean(Constants.skipQua)) {
            htmlContent.append(
                String.format(
                    "                        <h1 class=\"c91\" style=\"margin-left: 20px;\">\n" +
                        "                            <span class=\"c161\" style=\"border-radius: 5pt;padding-left: 15pt;padding-right: 15pt;padding-top: 5pt;padding-bottom: 5pt;\">Qualification</span>\n" +
                        "                        </h1>\n" +
                        "\n"
                )
            )

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
                            model.schoolName,
                            model.subject,
                            model.marks,
                            model.endDate
                        )
                    )
                }
            }
        }
        if (!tinyDB.getBoolean(Constants.skipProj)) {
            htmlContent.append(
                String.format(
                    "                        <h1 class=\"c91\" style=\"margin-left: 20px;\">\n" +
                        "                            <span class=\"c161\" style=\"border-radius: 5pt;padding-left: 15pt;padding-right: 15pt;padding-top: 5pt;padding-bottom: 5pt;\">Projects</span>\n" +
                        "                        </h1>\n" +
                        "\n"
                )
            )

            if (modelMain.projectsEntity != null) {
                for (model in modelMain.projectsEntity!!) {
                    htmlContent.append(
                        String.format(

                            "                        <p class=\"c22\">\n" +
                                "                            <span>\n" +
                                "                                <p style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 28pt;font-weight:bold;\">%s</p>\n" +
                                "                            </span>\n" +
                                "                        </p>\n",
                            model.projectTitle
                        )
                    )
                }
            }
        }
        htmlContent.append(
            String.format(
                "  <p class=\"c68\"></p>\n" +
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
                if (dialog.isShowing()) {
                    dialog.dismiss()
                }
            }
        }
        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null)
    }
}
