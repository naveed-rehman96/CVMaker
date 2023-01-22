package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentPreviewCv

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.ModelNewMain
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCV.PreviewYellowGrayActivity
import java.io.File

class YellowGrayCvFragment : Fragment() {

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_preview_all_cv, container, false)
        val btnFullScreen: ImageButton = view.findViewById(R.id.btnFullScreenPreview)
        btnFullScreen.setOnClickListener {
            val intent = Intent(requireContext(), PreviewYellowGrayActivity::class.java)
            startActivity(intent)
        }
        val btnNext = view.findViewById(R.id.btnNextTemplate) as Button
        val btnBack = view.findViewById(R.id.btnBackTemplate) as Button

        btnNext.setOnClickListener {

            val intent = Intent(requireContext(), PreviewYellowGrayActivity::class.java)
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
        webView.settings.allowFileAccess = true;
        webView.settings.javaScriptEnabled = true;
        webView.settings.builtInZoomControls = false;
        webView.settings.displayZoomControls = false;
        webView.settings.builtInZoomControls = true;
        webView.setInitialScale(100)
        val htmlContent = StringBuilder()
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
                        ".c26{border-right-style: solid;            border-bottom-color: #ffffff;            border-top-width: 0pt;            border-right-width: 0pt;            border-left-color: #ffffff;            vertical-align: top;            border-right-color: #ffffff;            border-left-width: 0pt;            background: #ffffff;            border-top-style: solid;            border-left-style: solid;            border-bottom-width: 0pt;            width: 200pt;            border-top-color: #ffffff;            background-image: linear-gradient(to right, #FFA500, #FFBA50);            border-bottom-style: solid}" +
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
                        ".c9{padding-top: 0pt;            padding-bottom: 0pt;            align-items: center;            font-weight: 900;            font-size: 18pt;            text-align: center}" +
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
                        " .yourButton1:after { content: \"\";position: absolute; left: 100%%; top: 0px; width: 0px; height: 0px; border-top: 0px solid transparent; border-left: 30px solid black; border-bottom: 70px solid transparent;}" +

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
                        "            <tbody>\n"
            )
        )

        htmlContent.append(
            String.format(
                "<tr class=\"c21\">\n" +
                        "                    <th class=\"c26\" style=\"margin-top: 50px;\" colspan=\"1\" rowspan=\"1\">\n"
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
                                "      <div style=\"height: 250px;margin-left: 20pt;margin-top: 20pt;margin-right: 20pt;align-items: center;\">" +
                                "                            <img src=\"%s\" style=\"width: 150pt; height: 200px;border-radius: 100%%\">" +
                                "                        </div>" +
                                "",
                        male
                    )
                )

            } else if (modelMain.personInfo.gender.equals("Female")) {
                htmlContent.append(
                    String.format(
                        "" +
                                "      <div style=\"height: 250px;margin-left: 20pt;margin-top: 20pt;margin-right: 20pt;align-items: center;\">" +
                                "                            <img src=\"file://%s\" style=\"width: 150pt; height: 200px;border-radius: 100%%\">" +
                                "                        </div>" +
                                "",
                        female
                    )
                )
            } else {
                htmlContent.append(
                    String.format(
                        "" +
                                "      <div style=\"height: 250px;margin-left: 20pt;margin-top: 20pt;margin-right: 20pt;align-items: center;\">" +
                                "                            <img src=\"%s\" style=\"width: 150pt; height: 200px;border-radius: 100%%\">" +
                                "                        </div>" +
                                "",
                        male
                    )
                )
            }
        } else {
            htmlContent.append(
                String.format(
                    "" +
                            "      <div style=\"height: 250px;margin-left: 20pt;margin-top: 20pt;margin-right: 20pt;align-items: center;\">" +
                            "                            <img src=\"file://%s\" style=\"width: 150pt; height: 200px;border-radius: 100%%\">" +
                            "                        </div>" +
                            "",
                    file.absolutePath
                )
            )


        }



        htmlContent.append(
            String.format(
                "" +
                        "                        <h1 class=\"c9\">" +
                        "                            <span>CONTACT DETIALS</span>" +
                        "                        </h1>" +
                        "                        <div class=\"aParent\">" +
                        "                            <div>" +
                        "                                <div class=\"yourButton divClass\">" +
                        "                                    <i class=\"fa fa-envelope fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                </div>" +
                        "                                <div>" +
                        "                                    <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%s</span>" +
                        "                                </div>" +
                        "                            </div>" +
                        "                            <div class=\"aParent\">" +
                        "                                <div>" +
                        "                                    <div class=\"yourButton divClass\">" +
                        "                                        <i class=\"fa fa-phone fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                    </div>" +
                        "                                    <div>" +
                        "                                        <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%s</span>" +
                        "                                    </div>" +
                        "                                </div>" +
                        "                                   <div class=\"aParent\">" +
                        "                                            <div>" +
                        "                                                <div class=\"yourButton divClass\">" +
                        "                                                    <i class=\"fa fa-location-arrow fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                                </div>" +
                        "                                                <div> <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%S</span> </div>" +
                        "                                            </div>" +
                        "                                   <div class=\"aParent\">" +
                        "                                            <div>" +
                        "                                                <div class=\"yourButton divClass\">" +
                        "                                                    <i class=\"fa fa-male fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                                </div>" +
                        "                                                <div> <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%S</span> </div>" +
                        "                                            </div>" +
                        "                                <div class=\"aParent\">" +
                        "                                    <div>" +
                        "                                        <div class=\"yourButton divClass\">" +
                        "                                            <i class=\"fa fa-address-card fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                        </div>" +
                        "                                        <div>" +
                        "                                            <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%s</span>" +
                        "                                        </div>" +
                        "                                    </div>" +
                        "                                    <div class=\"aParent\">" +
                        "                                        <div>" +
                        "                                            <div class=\"yourButton divClass\">" +
                        "                                                <i class=\"fa fa-calendar fa-xs\" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                            </div>" +
                        "                                            <div><span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%s</span></div>" +
                        "                                        </div>" +
                        "                                        <div class=\"aParent\">" +
                        "                                            <div>" +
                        "                                                <div class=\"yourButton divClass\">" +
                        "                                                    <i class=\"fa fa-globe fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                                </div>" +
                        "                                                <div> <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%S</span> </div>" +
                        "                                            </div>" +
                        "                                          <div class=\"aParent\">" +
                        "                                            <div>" +
                        "                                                <div class=\"yourButton divClass\">" +
                        "                                                    <i class=\"fa fa-globe fa-xs \" aria-hidden=\"true\" style=\"margin-right: 10px;color: #ffffff;\"></i>" +
                        "                                                </div>" +
                        "                                                <div> <span class=\"c251\" style=\"color: #313133;margin-left: 20pt;\">%S</span> </div>" +
                        "                                            </div>" +
                        "                                            <p class=\"c68\"></p>",
                modelMain.personInfo.emailID,
                tinyDB.getString("FullPhoneNumberUpdate"),
                modelMain.personInfo.address,
                modelMain.personInfo.gender,
                modelMain.personInfo.cnic,
                modelMain.personInfo.dateOfBirth,
                modelMain.personInfo.nationality, "Status : " + modelMain.personInfo.maritalStatus

            )
        )

        if (!tinyDB.getBoolean(Constants.skipTech)) {
            if (modelMain.skillsEntity != null) {
                htmlContent.append(
                    java.lang.String.format(
                        "" +
                                "<p class=\"c68\"></p>" +
                                "  <h1 class=\"c9\">" +
                                "      <span>TECH SKILLS</span>" +
                                "  </h1>"
                    )
                )
            }
            if (modelMain.skillsEntity != null) {
                for (model in modelMain.skillsEntity!!) {
                    htmlContent.append(
                        java.lang.String.format(
                            "  <p class=\"c6\">" +
                                    "      <span class=\"c251\" style=\"color: #313133;\">%s</span>" +
                                    "  </p>",
                            model.skillTitle
                        )
                    )
                }
            }
        }


        if (!tinyDB.getBoolean(Constants.skipLang)) {

            htmlContent.append(
                String.format(
                    "  <p class=\"c68\"></p>" +
                            "  <h1 class=\"c9\">" +
                            "      <span>LANGUAGES</span>" +
                            "  </h1>" +
                            "  <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "      <span class=\"c251\">%s</span>" +
                            "  </p>",
                    modelMain.personInfo.language
                )
            )
        }
        if (!tinyDB.getBoolean(Constants.skipInterest)) {

            htmlContent.append(
                String.format(
                    "<p class=\"c68\"></p>" +
                            "  <h1 class=\"c9\">" +
                            "      <span>INTEREST</span>" +
                            "  </h1>" +
                            "  <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "      <span class=\"c251\">%s</span>" +
                            "  </p>",
                    modelMain.personInfo.interest
                )
            )
        }
        if (!tinyDB.getBoolean(Constants.skipRef)) {

            htmlContent.append(
                String.format(

                    "  <p class=\"c68\"></p>" +
                            "  <h1 class=\"c9\">" +
                            "      <span>REFERENCES</span>" +
                            "  </h1>" +
                            "  <p class=\"c6\" style=\"margin-bottom: 15px;\">" +
                            "      <span class=\"c251\">%s</span>" +
                            "  </p>" +
                            "  <p class=\"c68\"></p>" +
                            "  <p class=\"68\"></p>",
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
                "" +
                        "" +
                        "                        <p class=\"c68\"></p>" +
                        "                        <div class=\"yourButton1\" style=\"border-bottom-color: #FFA500;border-bottom-width: 5px;border-bottom-style: solid; margin-top: 30pt; padding: 5pt;width: 220pt\">" +
                        "                            <p class=\"c6 c12 title\"><span>%s</span></p>" +
                        "                            <p class=\"c33 subtitle\"><span class=\"c20\" style=\"margin-bottom: 5pt\">%s</span></p>" +
                        "                        </div>",
                modelMain.personInfo.fullName, modelMain.personInfo.fatherName

            )
        )


        htmlContent.append(
            String.format(
                "" +
                        "    <h1 class=\"c91\" style=\" \">" +
                        "                            <span class=\"\" style=\"text-align: center;\"> Objective </span>" +
                        "                        </h1>" +
                        "                        <p class=\"c22\" style=\"margin-bottom: 15px; margin-left: 20pt;margin-right: 30pt;\">" +
                        "                            <span class=\"c251\">%s</span>" +
                        "                        </p>" +
                        "" +
                        "",
                modelMain.personInfo.objective

            )
        )
        if (!tinyDB.getBoolean(Constants.skipQua)) {
            if (modelMain.qualificationEntity != null) {
                htmlContent.append(
                    String.format(
                        "" +
                                " <h1 class=\"c91\" style=\"margin-top: 10pt;\">" +
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
                                    "<h2 class=\"c3\" style=\"font-size: 12px; margin-top: 2px;font-style: bold;margin-bottom: 5px;margin-left: 20pt\">" +
                                    "                            <span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 12pt;color: #FF1A3A;\">( %s )</p>" +
                                    "                            </span>" +
                                    "                        </h2>" +
                                    "                        <p class=\"c22\">" +
                                    "                            <span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 20pt;font-weight:bold; \">%s</p>" +
                                    "                            </span>" +
                                    "                        </p>" +
                                    "                        <p class=\"c22\">" +
                                    "                            <span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 10pt;margin-top: 2px;margin-left: 20pt\">%s</p>" +
                                    "                            </span>" +
                                    "                        </p>" +
                                    "                        <p class=\"c22\">" +
                                    "                            <span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 10pt;margin-top: 2px;margin-left: 20pt\">%s</p>" +
                                    "                            </span>" +
                                    "                        </p>" +
                                    "" +
                                    "" +
                                    "" +
                                    "",
                            model.getEndDate(),
                            model.getSchoolName(),
                             model.subject,
                            model.getMarks()

                        )
                    )
                }
            }
        }
        if (!tinyDB.getBoolean(Constants.skipExp)) {
            if (modelMain.experienceInfo  != null) {
                htmlContent.append(
                    String.format(
                        "" +
                                " <h1 class=\"c91\" style=\"margin-top: 10pt;\">" +
                                "                            <span> Work Experience </span>" +
                                "                        </h1>" +
                                ""
                    )
                )
            }

            if (modelMain.experienceInfo  != null) {


                for (model in modelMain.experienceInfo !!) {
                    htmlContent.append(
                        String.format(
                            "" +
                                    "<h2 class=\"c3\" style=\"font-size: 12px; margin-top: 2px;font-style: bold;margin-bottom: 5px;margin-left: 20pt\">" +
                                    "                            <span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 12pt; color: #FF1A3A;\">( FROM %s TO %s )</p>" +
                                    "                            </span>" +
                                    "                        </h2>" +
                                    "                        <p class=\"c22\">" +
                                    "                            <span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 10pt;color: #2F3A40;margin-top: :2px;margin-bottom: 0px;margin-left: 20pt;font-weight:bold;\">%s</p>" +
                                    "                            </span>" +
                                    "                        </p>" +
                                    "                        <p class=\"c22\"><span>" +
                                    "                                <p class=\"c251\" style=\"font-size: 9pt;margin-top: 2px;margin-left: 20pt\">%s</p>" +
                                    "                        </p>",
                            model.startDate,
                            model.endDate,
                            modelMain.personInfo.objective,
                           model.designation
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
                            "                        <p class=\"c22\" style=\"margin-bottom: 2px; margin-left: 20pt; margin-right:30pt;\">" +
                                    "                            <span class=\"c251\">%s</span>" +
                                    "                        </p>",
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

        if (!tinyDB.getBoolean(Constants.skipAwards)) {

            htmlContent.append(
                String.format(
                    "" +
                            "" +
                            "<h1 class=\"c91\" style=\"margin-top: 10pt;\">" +
                            "                            <span > Achievements </span>" +
                            "                        </h1>" +
                            "                        <p class=\"c22\" style=\"margin-bottom: 15px; margin-left: 20pt; margin-right:30pt;\">" +
                            "                            <span class=\"c251\">%s</span>" +
                            "                        </p>",
                     modelMain.personInfo.awards

                )
            )
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
            override fun onPageFinished(view: WebView, url: String) {
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        }
        webView.loadDataWithBaseURL(null, htmlContent.toString(), "text/html", "utf-8", null)

    }

}