package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.airbnb.lottie.LottieAnimationView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.Saved_Cv_Pdf_Activity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import java.io.File

class PdfPreviewActivity : AppCompatActivity() {
    // lateinit var pdfView: PDFView
    var pageNumber = 0
    lateinit var pdfFileName: String
    lateinit var pdfFilePath: String
    lateinit var share: ImageView
    lateinit var Delete: ImageView
    lateinit var tinyDB: TinyDB
    lateinit var buttonLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()
        setContentView(R.layout.activity_pdf_preview)

        val toolbar = findViewById<Toolbar>(R.id.toolbarPreviewPdf)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonLayout = findViewById(R.id.buttonItemViewPDf)
        val lottie = findViewById<LottieAnimationView>(R.id.rateUsLottie)
        lottie.setOnClickListener {
            try {
                val marketUri = Uri.parse("market://details?id=$packageName")
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                startActivity(marketIntent)
            } catch (e: ActivityNotFoundException) {
                val marketUri =
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
                startActivity(marketIntent)
            }
        }
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
                buttonLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
                buttonLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
                buttonLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
                buttonLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
                buttonLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
                buttonLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
            }
        }

        val intent = intent
        if (intent != null) {
            pdfFileName = intent.getStringExtra("Name").toString()
            pdfFilePath = intent.getStringExtra("Path").toString()
        }
        // pdfView = findViewById<View>(R.id.pdfView) as PDFView
        share = findViewById(R.id.sharepdf)
        Delete = findViewById(R.id.deletePDf)
        val u = "file:///$pdfFilePath"
        Log.d("filenew", "onCreate: $u")
        displayFromAsset(Uri.parse(u))
        share.setOnClickListener(
            View.OnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "application/pdf"
                val fileUri = FileProvider.getUriForFile(
                    applicationContext,
                    "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.FileProvider",
                    File(pdfFilePath)
                )
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
                startActivity(Intent.createChooser(shareIntent, "Share it"))
            }
        )
        Delete.setOnClickListener {
            val dialogBuilder1 = AlertDialog.Builder(this, R.style.DialogStyle)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_deletefile, null)
            dialogBuilder1.setView(dialogView)
            val cancel: Button = dialogView.findViewById(R.id.btnCancelExitDialog)
            val Ok: Button = dialogView.findViewById(R.id.btnOkExitDialog)
            val alertDialog = dialogBuilder1.create()
            alertDialog.setCancelable(false)

            cancel.setOnClickListener {
                alertDialog.dismiss()
            }

            Ok.setOnClickListener {
                val deleteFile = File(pdfFilePath)
                deleteFile.delete()
                val intent = Intent(this, Saved_Cv_Pdf_Activity::class.java)
                startActivity(intent)
                finish()
            }
            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Saved_Cv_Pdf_Activity::class.java)
        startActivity(intent)
        finish()
    }

    private fun displayFromAsset(assetFileName: Uri) {
//        pdfView!!.fromUri(assetFileName)
//            .defaultPage(pageNumber)
//            .enableSwipe(true)
//            .swipeHorizontal(false)
//            .onPageChange(this)
//            .enableAnnotationRendering(true)
//            .onLoad(this)
//            .scrollHandle(DefaultScrollHandle(this))
//            .load()
    }

//    override fun onPageChanged(page: Int, pageCount: Int) {
//        pageNumber = page
//        title = String.format("%s %s / %s", pdfFileName, page + 1, pageCount)
//    }
//
//    override fun loadComplete(nbPages: Int) {
//        val meta = pdfView!!.documentMeta
//        printBookmarksTree(pdfView!!.tableOfContents, "-")
//    }
//
//    fun printBookmarksTree(tree: List<Bookmark>, sep: String) {
//        for (b in tree) {
//            Log.e("TAG", String.format("%s %s, p %d", sep, b.title, b.pageIdx))
//            if (b.hasChildren()) {
//                printBookmarksTree(b.children, "$sep-")
//            }
//        }
//    }

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
}
