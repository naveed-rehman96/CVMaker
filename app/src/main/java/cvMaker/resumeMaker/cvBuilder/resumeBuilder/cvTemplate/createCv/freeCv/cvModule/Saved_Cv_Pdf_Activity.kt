package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SavedResumeAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SavedResumeImagesAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.MyApplication
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvFileModelClass
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class Saved_Cv_Pdf_Activity : AppCompatActivity(), SavedResumeAdapter.SavedCVClickListener,
    SavedResumeImagesAdapter.SavedCVClickListener {

    lateinit var pDFArrayList: ArrayList<CvFileModelClass>
    lateinit var selectedPdfList: ArrayList<CvFileModelClass>
    lateinit var imagesArrayList: ArrayList<CvFileModelClass>

    lateinit var recyclerViewPDF: RecyclerView
    lateinit var recyclerViewImages: RecyclerView
    lateinit var adapterPDf: SavedResumeAdapter
    lateinit var adapterImages: SavedResumeImagesAdapter
    lateinit var tinyDB: TinyDB
    lateinit var lottie: LottieAnimationView
    lateinit var titleSavedResume: TextView
    lateinit var bottomSavedResumeLayout: LinearLayout

    lateinit var bottomCreateCVLayout: RelativeLayout
    lateinit var bottomSavedProfile: LinearLayout

    var mState: Boolean = false
    var mAllSelectedPdf: Boolean = false
    var mAllSelectedImage: Boolean = false


    lateinit var imageBtn: Button
    lateinit var menuMore: ImageButton
    lateinit var pdfBtn: Button
    lateinit var btnCreateCVSavedProfile: CircleImageView

    companion object {
        var isMultiSelectionOnPDF = false
        var isMultiSelectionOnImage = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        tinyDB =
            TinyDB(
                this
            )
        checkTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved__cv__pdf_)

        val toolbar = findViewById<Toolbar>(R.id.toolbarSavedCV2)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        imageBtn = findViewById(R.id.imageRCVbtn)
        titleSavedResume = findViewById(R.id.titleSavedResume)
        menuMore = findViewById(R.id.menuMore)
        btnCreateCVSavedProfile = findViewById(R.id.btnCreateCVSavedProfile)
        pdfBtn = findViewById(R.id.pdfRcvbtn)
        bottomCreateCVLayout = findViewById(R.id.bottomCreateCVLayout)
        bottomSavedProfile = findViewById(R.id.bottomSavedProfile)
        bottomSavedResumeLayout = findViewById(R.id.bottomSavedResumeLayout)


        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedResumeLayout.background,
                    Color.parseColor("#6C48EF")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#6C48EF"))
                btnCreateCVSavedProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_add_main_cv_blue
                    )
                )
                bottomSavedProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Blue_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedResumeLayout.background,
                    Color.parseColor("#ED851A")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#ED851A"))
                btnCreateCVSavedProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_add_main_cv_orange
                    )
                )
                bottomSavedProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Orange_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedResumeLayout.background,
                    Color.parseColor("#950806")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#950806"))
                btnCreateCVSavedProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_add_main_cv_red
                    )
                )
                bottomSavedProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Red_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedResumeLayout.background,
                    Color.parseColor("#C8BA00")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#C8BA00"))
                btnCreateCVSavedProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_add_main_cv_yellow
                    )
                )
                bottomSavedProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Yellow_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedResumeLayout.background,
                    Color.parseColor("#296E01")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#296E01"))
                btnCreateCVSavedProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_add_main_cv_green
                    )
                )
                bottomSavedProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Green_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedResumeLayout.background,
                    Color.parseColor("#6A6A6A")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#6A6A6A"))
                btnCreateCVSavedProfile.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_add_main_cv_gray
                    )
                )
                bottomSavedProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Gray_Theme
                    )
                )
            }
        }

        lottie = findViewById<LottieAnimationView>(R.id.rateUsLottie)
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
        recyclerViewPDF = findViewById(R.id.savedPdfResumeRcv)
        recyclerViewImages = findViewById(R.id.savedImagesResumeRcv)
        recyclerViewPDF.layoutManager = LinearLayoutManager(this)
        recyclerViewImages.layoutManager = LinearLayoutManager(this)
        GetFiles().execute()


        pdfBtn.setOnClickListener {
            imageBtn.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.border_orange
                )
            )
            pdfBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_blue))
            imageBtn.setTextColor(Color.BLACK)
            pdfBtn.setTextColor(Color.WHITE)
            recyclerViewPDF.visibility = VISIBLE
            recyclerViewImages.visibility = GONE
            tinyDB.putBoolean("pdf", false)

            when {
                tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                    MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#6C48EF"))
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                    MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#ED851A"))
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                    MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#950806"))
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                    MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#C8BA00"))
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                    MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#296E01"))
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                    MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#6A6A6A"))
                }
            }
        }
        imageBtn.setOnClickListener {
            pdfBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_orange))
            imageBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_blue))
            imageBtn.setTextColor(Color.WHITE)
            pdfBtn.setTextColor(Color.BLACK)
            recyclerViewImages.visibility = VISIBLE
            recyclerViewPDF.visibility = GONE

            tinyDB.putBoolean("pdf", true)
            when {
                tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                    MyDrawableCompat.setColorFilter(
                        imageBtn.background,
                        Color.parseColor("#6C48EF")
                    )
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                    MyDrawableCompat.setColorFilter(
                        imageBtn.background,
                        Color.parseColor("#ED851A")
                    )
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                    MyDrawableCompat.setColorFilter(
                        imageBtn.background,
                        Color.parseColor("#950806")
                    )
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                    MyDrawableCompat.setColorFilter(
                        imageBtn.background,
                        Color.parseColor("#C8BA00")
                    )
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                    MyDrawableCompat.setColorFilter(
                        imageBtn.background,
                        Color.parseColor("#296E01")
                    )
                }
                tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                    MyDrawableCompat.setColorFilter(
                        imageBtn.background,
                        Color.parseColor("#6A6A6A")
                    )
                }
            }
        }


        btnCreateCVSavedProfile.setOnClickListener {
            dialogCVName()
            MyApplication.firbaseAnalaytics(this@Saved_Cv_Pdf_Activity, "1", "btn_CreateCV")
            tinyDB.putBoolean("Boolean", false)

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.saved_file_menu, menu)
        if (mState) {
            for (i in 0 until menu.size()) {

                menu.getItem(i).isVisible = true
            }
        } else {
            for (i in 0 until menu.size()) {
                menu.getItem(i).isVisible = menu.getItem(i).itemId ==
                        R.id.actionDescendingOrder || menu.getItem(i).itemId == R.id.actionAscendingOrder

            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.selectAll -> {
                if (tinyDB.getBoolean("pdf")) {
                    if (mAllSelectedImage) {
                        mAllSelectedImage = false
                        for (file in imagesArrayList) {
                            file.selected = false
                        }
                    } else {
                        mAllSelectedImage = true

                        for (file in imagesArrayList) {
                            file.selected = true

                        }
                    }

                    adapterImages.notifyDataSetChanged()
                } else {
                    if (mAllSelectedPdf) {
                        mAllSelectedPdf = false
                        for (file in pDFArrayList) {
                            file.selected = false

                        }
                    } else {
                        mAllSelectedPdf = true
                        for (file in pDFArrayList) {
                            file.selected = true

                        }
                    }
                    adapterPDf.notifyDataSetChanged()
                }
                true
            }
            R.id.cancelMenu -> {
                isMultiSelectionOnPDF = false
                isMultiSelectionOnImage = false
                if (tinyDB.getBoolean("pdf")) {
                    for (file in imagesArrayList) {
                        file.selected = false
                    }
                    adapterImages.notifyDataSetChanged()
                } else {
                    for (file in pDFArrayList) {
                        file.selected = false
                    }
                    adapterPDf.notifyDataSetChanged()
                }
                mState = false
                invalidateOptionsMenu()
                lottie.visibility = VISIBLE
                titleSavedResume.visibility = VISIBLE

                true
            }
            R.id.actionDeleteSelected -> {
                var count = 0
                if (tinyDB.getBoolean("pdf")) {
                    for (file in imagesArrayList) {
                        if (file.selected) {
                            count++
                        }
                    }

                } else {
                    for (file in pDFArrayList) {
                        if (file.selected) {
                            count++
                        }
                    }

                }

                if (count > 0) {

                    val dialogBuilder1 = AlertDialog.Builder(this, R.style.DialogStyle)
                    val inflater = LayoutInflater.from(this)
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
                        DeleteSelectedFiles().execute()
                        alertDialog.dismiss()

                    }
                    alertDialog.show()
                } else {
                    Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.actionAscendingOrder -> {


                if (tinyDB.getBoolean("pdf")) {
                    imagesArrayList.sortWith(Comparator { video1, video2 ->
                        File(video1.location).length().compareTo(File(video2.location).length())
                    })
                    adapterImages.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "actionAscendingOrder", Toast.LENGTH_SHORT).show()

                    pDFArrayList.sortWith(Comparator { video1, video2 ->
                        File(video1.location).length().compareTo(File(video2.location).length())
                    })
                    adapterPDf.notifyDataSetChanged()
                }

                true
            }
            R.id.actionShareSelected -> {
                val count = 0
                val uriList: ArrayList<Uri> = ArrayList()
                if (tinyDB.getBoolean("pdf")) {
                    for (file in imagesArrayList) {
                        if (file.selected) {
                            val fileShare = File(file.location)
                            uriList.add(
                                FileProvider.getUriForFile(
                                    this,
                                    "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.FileProvider",
                                    fileShare
                                )
                            )
                        }
                    }
                } else {
                    for (file in pDFArrayList) {
                        if (file.selected) {
                            val fileShare = File(file.location)
                            uriList.add(
                                FileProvider.getUriForFile(
                                    this,
                                    "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.FileProvider",
                                    fileShare
                                )
                            )
                        }
                    }
                }

                if (uriList.isNotEmpty()) {
                    val intent = Intent()
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.action = Intent.ACTION_SEND_MULTIPLE
                    intent.type = "*/*"
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
                    startActivity(Intent.createChooser(intent, "Share using"))
                } else {
                    Toast.makeText(this, "No Files Selected", Toast.LENGTH_SHORT).show()
                }
                true
            }

            R.id.actionDescendingOrder -> {

                if (!tinyDB.getBoolean("pdf")) {
                    Toast.makeText(this, "actionDescendingOrder", Toast.LENGTH_SHORT).show()
                    pDFArrayList.sortWith(Comparator { video1, video2 ->
                        File(video2.location).length().compareTo(File(video1.location).length())
                    })
                    adapterPDf.notifyDataSetChanged()
                } else {
                    imagesArrayList.sortWith(Comparator { video1, video2 ->
                        File(video2.location).length().compareTo(File(video1.location).length())
                    })
                    adapterImages.notifyDataSetChanged()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun dialogCVName() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cv_name, null)
        dialogBuilder.setView(dialogView)
        val cvName: TextInputLayout
        cvName = dialogView.findViewById(R.id.CV_name_input)
        val createBt: Button = dialogView.findViewById(R.id.btnCreateCV)
        val cancelBt: Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)


        createBt.setOnClickListener {
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
                createCV(name)
                alertDialog.dismiss()
            } else {
                cvName.editText?.error = "Required"
            }
        }

        cancelBt.setOnClickListener {
            alertDialog.dismiss()
        }


        alertDialog.show()
    }


    fun createCV(name: String) {
        bottomCreateCVLayout.visibility = GONE
        GetFiles().execute()
        val myfinalstring: String = name.replace("'", "''")
        tinyDB.putString("FILE", myfinalstring)
        tinyDB.putString("UID", "" + System.currentTimeMillis())
        Log.d("TAG", "dialogCVName: " + tinyDB.getString("FILE"))

        val db = DataBaseHandler(this)
        db.addFileName(tinyDB.getString("FILE").toLowerCase(), tinyDB.getString("UID"))
        tinyDB.putBoolean(" ", false)
        tinyDB.putBoolean("CHECK_ACTIVITY", true)
        tinyDB.putBoolean("VIEW", false)
        tinyDB.putBoolean("RESUME_CV", false)
        tinyDB.putBoolean("IS_SELECTED", false)
        val intent = Intent(this, cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
        startActivity(intent)
        finish()


    }


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

    override fun onResume() {
        super.onResume()
        btnCreateCVSavedProfile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
    }


    @SuppressLint("StaticFieldLeak")
    inner class GetFiles() : AsyncTask<String, String, String>() {

        lateinit var progressBar: ProgressDialog
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.dismiss()
            if (pDFArrayList.isEmpty() && imagesArrayList.isEmpty()) {

                bottomCreateCVLayout.visibility = VISIBLE
                recyclerViewImages.visibility = GONE
                recyclerViewPDF.visibility = GONE
                bottomSavedResumeLayout.visibility = GONE

            } else {
                bottomCreateCVLayout.visibility = GONE
                bottomSavedResumeLayout.visibility = VISIBLE

                progressBar.dismiss()

                if (tinyDB.getBoolean("pdf")) {
                    pdfBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@Saved_Cv_Pdf_Activity,
                            R.drawable.border_orange
                        )
                    )
                    imageBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@Saved_Cv_Pdf_Activity,
                            R.drawable.border_blue
                        )
                    )
                    imageBtn.setTextColor(Color.WHITE)
                    pdfBtn.setTextColor(Color.BLACK)
                    recyclerViewPDF.visibility = GONE
                    recyclerViewImages.visibility = VISIBLE
                    when {
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                            MyDrawableCompat.setColorFilter(
                                imageBtn.background,
                                Color.parseColor("#6C48EF")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                            MyDrawableCompat.setColorFilter(
                                imageBtn.background,
                                Color.parseColor("#ED851A")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                            MyDrawableCompat.setColorFilter(
                                imageBtn.background,
                                Color.parseColor("#950806")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                            MyDrawableCompat.setColorFilter(
                                imageBtn.background,
                                Color.parseColor("#C8BA00")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                            MyDrawableCompat.setColorFilter(
                                imageBtn.background,
                                Color.parseColor("#296E01")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                            MyDrawableCompat.setColorFilter(
                                imageBtn.background,
                                Color.parseColor("#6A6A6A")
                            )
                        }
                    }
                } else {
                    recyclerViewImages.visibility = GONE
                    recyclerViewPDF.visibility = VISIBLE
                    imageBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@Saved_Cv_Pdf_Activity,
                            R.drawable.border_orange
                        )
                    )
                    pdfBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@Saved_Cv_Pdf_Activity,
                            R.drawable.border_blue
                        )
                    )
                    imageBtn.setTextColor(Color.BLACK)
                    pdfBtn.setTextColor(Color.WHITE)

                    when {
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                            MyDrawableCompat.setColorFilter(
                                pdfBtn.background,
                                Color.parseColor("#6C48EF")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                            MyDrawableCompat.setColorFilter(
                                pdfBtn.background,
                                Color.parseColor("#ED851A")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                            MyDrawableCompat.setColorFilter(
                                pdfBtn.background,
                                Color.parseColor("#950806")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                            MyDrawableCompat.setColorFilter(
                                pdfBtn.background,
                                Color.parseColor("#C8BA00")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                            MyDrawableCompat.setColorFilter(
                                pdfBtn.background,
                                Color.parseColor("#296E01")
                            )
                        }
                        tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                            MyDrawableCompat.setColorFilter(
                                pdfBtn.background,
                                Color.parseColor("#6A6A6A")
                            )
                        }
                    }
                }

                adapterPDf = SavedResumeAdapter(
                    this@Saved_Cv_Pdf_Activity,
                    pDFArrayList,
                    this@Saved_Cv_Pdf_Activity
                )
                adapterImages = SavedResumeImagesAdapter(
                    this@Saved_Cv_Pdf_Activity,
                    imagesArrayList,
                    this@Saved_Cv_Pdf_Activity
                )
                recyclerViewPDF.adapter = adapterPDf
                recyclerViewImages.adapter = adapterImages
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar = ProgressDialog(this@Saved_Cv_Pdf_Activity)
            progressBar.setTitle("Fetching Files")
            progressBar.setMessage("Please Wait while we get your resumes")
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            var file: File
            var file1: File

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                file = File(getExternalFilesDir(null).toString() + "/CVMaker/Resume/Pdf/")
                file1 = File(getExternalFilesDir(null).toString() + "/CVMaker/Resume/Images/")
            } else {
                file =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/Resume/Pdf/")
                file1 =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/Resume/Images/")

            }





            gettingAllPdfFromStorage(file)
            gettingAllImagesFromStorage(file1)


            pDFArrayList.sortWith(Comparator { o1, o2 ->
                val k = File(o1.location).lastModified() - File(o2.location).lastModified()
                when {
                    k > 0 -> {
                        1
                    }
                    k == 0L -> {
                        0
                    }
                    else -> {
                        -1
                    }
                }
            })
            imagesArrayList.sortWith(Comparator { o1, o2 ->
                val k = File(o1.location).lastModified() - File(o2.location).lastModified()
                when {
                    k > 0 -> {
                        1
                    }
                    k == 0L -> {
                        0
                    }
                    else -> {
                        -1
                    }
                }
            })

            return ""
        }


    }

    @SuppressLint("StaticFieldLeak")
    inner class DeleteAllFiles() : AsyncTask<String, String, String>() {

        lateinit var progressBar: ProgressDialog
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.dismiss()

            GetFiles().execute()

            Toast.makeText(this@Saved_Cv_Pdf_Activity, "Deleted Successfully", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar = ProgressDialog(this@Saved_Cv_Pdf_Activity)
            progressBar.setTitle("Deleting All Files")
            progressBar.setMessage("Please Wait for a while")
            progressBar.show()

        }

        override fun doInBackground(vararg params: String?): String {

            if (tinyDB.getBoolean("pdf")) {
                for (file in imagesArrayList) {
                    File(file.location).delete()
                }
            } else {
                for (file in pDFArrayList) {
                    File(file.location).delete()
                }

            }


            return ""
        }


    }

    inner class DeleteSelectedFiles() : AsyncTask<String, String, String>() {

        lateinit var progressBar: ProgressDialog
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.dismiss()

            GetFiles().execute()

            Toast.makeText(this@Saved_Cv_Pdf_Activity, "Deleted Successfully", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar = ProgressDialog(this@Saved_Cv_Pdf_Activity)
            progressBar.setTitle("Deleting All Files")
            progressBar.setMessage("Please Wait for a while")
            progressBar.show()

        }

        override fun doInBackground(vararg params: String?): String {

            if (tinyDB.getBoolean("pdf")) {
                for (file in imagesArrayList) {
                    if (file.selected) {
                        File(file.location).delete()
                    }
                }
            } else {
                for (file in pDFArrayList) {
                    if (file.selected) {
                        val fileDel = File(file.location)
                        fileDel.delete()
                    }
                }

            }


            return ""
        }


    }

    fun gettingAllPdfFromStorage(dir: File) {
        pDFArrayList = ArrayList()
        val pdfPattern = ".pdf"
        val listFile = dir.listFiles()
        if (listFile != null) {
            for (i in listFile.indices) {
                if (listFile[i].isDirectory) {
                    gettingAllPdfFromStorage(listFile[i])
                } else {
                    if (listFile[i].name.endsWith(pdfPattern)) {
                        //Do what ever u want
                        val objec =
                            CvFileModelClass()
                        objec.name = listFile[i].name.toString()
                        objec.location = listFile[i].absolutePath
                        objec.itemIcon = ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_file
                        )
                        objec.selected = false
                        pDFArrayList.add(objec)
                    }
                }
            }
        }
    }

    fun gettingAllImagesFromStorage(dir: File) {
        imagesArrayList = ArrayList()
        val pdfPattern = ".jpg"
        val listFile = dir.listFiles()
        if (listFile != null) {
            for (i in listFile.indices) {
                if (listFile[i].isDirectory) {
                    gettingAllImagesFromStorage(listFile[i])
                } else {
                    if (listFile[i].name.endsWith(pdfPattern)) {
                        //Do what ever u want
                        val objec =
                            CvFileModelClass()
                        objec.name = listFile[i].name.toString()
                        objec.location = listFile[i].absolutePath
                        objec.selected = false
                        imagesArrayList.add(objec)
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun renamedPdf() {
        GetFiles().execute()
        adapterPDf.notifyDataSetChanged()
    }

    override fun selectionTrue(b: Boolean) {

        mState = b
        invalidateOptionsMenu()
        if (b) {
            lottie.visibility = GONE
            titleSavedResume.visibility = GONE
        } else {

            lottie.visibility = VISIBLE
            titleSavedResume.visibility = VISIBLE
        }

    }


    override fun renamedImage() {
        GetFiles().execute()
        adapterImages.notifyDataSetChanged()
    }

    override fun selectionImage(b: Boolean) {
        var count = 1

        if (b) {
            lottie.visibility = GONE
            titleSavedResume.visibility = GONE
        } else {

            lottie.visibility = VISIBLE
            titleSavedResume.visibility = VISIBLE
        }

        mState = b
        invalidateOptionsMenu()
    }

    private fun isNetworkAvailable(): Boolean {

        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected()
    }

}