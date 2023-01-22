package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule

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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SavedCoverLetterPdfAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SavedLetterImagesAdapter
import com.google.android.material.textfield.TextInputLayout
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.FileModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.MyApplication
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class SavedCoverLetterAcitivity : AppCompatActivity(), SavedCoverLetterPdfAdapter.SavedCVClickListener,
    SavedLetterImagesAdapter.SavedCVClickListener {
    lateinit var pDFArrayList: ArrayList<FileModelClass>
    lateinit var imagesArrayList: ArrayList<FileModelClass>
    lateinit var recyclerViewPDF: RecyclerView
    lateinit var recyclerViewImages: RecyclerView
    lateinit var adapterPDf: SavedCoverLetterPdfAdapter
    lateinit var adapterImages: SavedLetterImagesAdapter
    lateinit var tinyDB: TinyDB
    lateinit var bottomSavedLetterLayout: LinearLayout

    lateinit var imageBtn: Button
    lateinit var pdfBtn: Button
    lateinit var menuMore: ImageButton

    lateinit var  lottie :LottieAnimationView
    lateinit var  titleSavedResume :TextView

    lateinit var bottomCreateCVLayout: RelativeLayout
    lateinit var bottomSavedProfile: LinearLayout
    lateinit var btnCreateLetter: CircleImageView

    companion object {
        var isMultiSelectionCLPDF = false
        var isMultiSelectionClImage = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_cover_letter)

        val toolbar = findViewById<Toolbar>(R.id.toolbarSavedLetter)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnCreateLetter = findViewById(R.id.btnCreateLetterSavedProfile)
        bottomCreateCVLayout = findViewById(R.id.bottomCreateCVLayout)
        bottomSavedProfile = findViewById(R.id.bottomSavedProfile)
        titleSavedResume = findViewById(R.id.titleSavedResume)

        imageBtn = findViewById(R.id.imageRCVbtn)
        menuMore = findViewById(R.id.menuMore)
        pdfBtn = findViewById(R.id.pdfRcvbtn)
        bottomSavedLetterLayout = findViewById(R.id.bottomSavedLetterLayout)


        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
                MyDrawableCompat.setColorFilter(
                    bottomSavedLetterLayout.background,
                    Color.parseColor("#6C48EF")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#6C48EF"))
                btnCreateLetter.setImageDrawable(
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
                    bottomSavedLetterLayout.background,
                    Color.parseColor("#ED851A")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#ED851A"))
                btnCreateLetter.setImageDrawable(
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
                    bottomSavedLetterLayout.background,
                    Color.parseColor("#950806")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#950806"))
                btnCreateLetter.setImageDrawable(
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
                    bottomSavedLetterLayout.background,
                    Color.parseColor("#C8BA00")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#C8BA00"))
                btnCreateLetter.setImageDrawable(
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
                    bottomSavedLetterLayout.background,
                    Color.parseColor("#296E01")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#296E01"))
                btnCreateLetter.setImageDrawable(
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
                    bottomSavedLetterLayout.background,
                    Color.parseColor("#6A6A6A")
                )
                MyDrawableCompat.setColorFilter(pdfBtn.background, Color.parseColor("#6A6A6A"))
                btnCreateLetter.setImageDrawable(
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
            pdfBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_blue))
            imageBtn.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.border_orange
                )
            )
            imageBtn.setTextColor(Color.BLACK)
            pdfBtn.setTextColor(Color.WHITE)
            recyclerViewPDF.visibility = View.VISIBLE
            recyclerViewImages.visibility = View.GONE
            tinyDB.putBoolean("Cpdf", false)
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
            imageBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_blue))
            pdfBtn.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_orange))
            imageBtn.setTextColor(Color.WHITE)
            pdfBtn.setTextColor(Color.BLACK)
            recyclerViewImages.visibility = View.VISIBLE
            recyclerViewPDF.visibility = View.GONE
            tinyDB.putBoolean("Cpdf", true)
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



        btnCreateLetter.setOnClickListener {
            dialogCOVERLETTERName()
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
                isMultiSelectionClImage = false
                isMultiSelectionCLPDF = false
                if (tinyDB.getBoolean("Cpdf")) {
                    for (file in imagesArrayList) {
                        file.selected = true
                    }
                    adapterImages.notifyDataSetChanged()
                } else {
                    for (file in pDFArrayList) {
                        file.selected = true
                    }
                    adapterPDf.notifyDataSetChanged()
                }
                true
            }
            R.id.cancelMenu -> {
                if (tinyDB.getBoolean("Cpdf")) {
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
                lottie.visibility = View.VISIBLE
                titleSavedResume.visibility = View.VISIBLE

                true
            }
            R.id.actionDeleteSelected -> {
                var count = 0
                if (tinyDB.getBoolean("Cpdf")) {
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
                    DeleteSelectedFiles().execute()
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
                val uriList: ArrayList<Uri> = ArrayList()
                if (tinyDB.getBoolean("Cpdf")) {
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

                val intent = Intent()
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.action = Intent.ACTION_SEND_MULTIPLE
                intent.type = "*/*"
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
                startActivity(Intent.createChooser(intent, "Share using"))

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



    fun dialogCOVERLETTERName() {

        MyApplication.firbaseAnalaytics(this, "1", "onTemplateClick_CreateLetter")
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_coverletter_name, null)
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
                val myfinalstring: String = name.replace("'", "''")
                tinyDB.putString("LETTER", myfinalstring)
                tinyDB.putString("CL_ID", "" + System.currentTimeMillis())
                tinyDB.putBoolean("NEW_LETTER", true)
                tinyDB.putBoolean("VIEWLetter", false)
                val db = DataBaseHandler(this)
                db.addCoverLetterRecord(tinyDB.getString("LETTER"), tinyDB.getString("CL_ID"))
                val intent = Intent(this, CreateCoverLetterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
                startActivity(intent)
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
        btnCreateLetter.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))

    }

    inner class GetFiles() : AsyncTask<String, String, String>() {

        lateinit var progressBar: ProgressDialog
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.dismiss()
            if (pDFArrayList.isEmpty() && imagesArrayList.isEmpty()) {
                bottomCreateCVLayout.visibility = View.VISIBLE
                bottomSavedLetterLayout.visibility = View.GONE
            } else {

                bottomCreateCVLayout.visibility = View.GONE

                if (tinyDB.getBoolean("Cpdf")) {
                    pdfBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@SavedCoverLetterAcitivity,
                            R.drawable.border_orange
                        )
                    )
                    imageBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@SavedCoverLetterAcitivity,
                            R.drawable.border_blue
                        )
                    )
                    imageBtn.setTextColor(Color.WHITE)
                    pdfBtn.setTextColor(Color.BLACK)
                    recyclerViewPDF.visibility = View.GONE
                    recyclerViewImages.visibility = View.VISIBLE
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
                    recyclerViewImages.visibility = View.GONE
                    recyclerViewPDF.visibility = View.VISIBLE
                    imageBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@SavedCoverLetterAcitivity,
                            R.drawable.border_orange
                        )
                    )
                    pdfBtn.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this@SavedCoverLetterAcitivity,
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

                pDFArrayList.reverse()
                imagesArrayList.reverse()
                adapterPDf = SavedCoverLetterPdfAdapter(
                    this@SavedCoverLetterAcitivity,
                    pDFArrayList,
                    this@SavedCoverLetterAcitivity
                )
                adapterImages = SavedLetterImagesAdapter(
                    this@SavedCoverLetterAcitivity,
                    imagesArrayList,
                    this@SavedCoverLetterAcitivity
                )
                recyclerViewPDF.adapter = adapterPDf
                recyclerViewImages.adapter = adapterImages
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar = ProgressDialog(this@SavedCoverLetterAcitivity)
            progressBar.setTitle("Fetching Files")
            progressBar.setMessage("Please Wait while we get your resumes")
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            val file: File =
                File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/CoverLetter/Pdf/")
            val file1: File =
                File(Environment.getExternalStorageDirectory().absolutePath + "/CVMaker/CoverLetter/Images/")
            gettingAllPdfFromStorage(file)
            gettingAllImagesFromStorage(file1)
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
                        val objec = FileModelClass()
                        objec.name = listFile[i].name.toString()
                        objec.location = listFile[i].absolutePath
                        objec.itemIcon = ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_file
                        )
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
                        val objec = FileModelClass()
                        objec.name = listFile[i].name.toString()
                        objec.location = listFile[i].absolutePath
                        imagesArrayList.add(objec)
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun renamedPdf() {
        GetFiles().execute()
        adapterPDf.notifyDataSetChanged()
    }

    var mState: Boolean = false
    override fun selectionTrue(b: Boolean) {
        var count = 1

        mState = b
        invalidateOptionsMenu()
    }

    override fun renamedImage() {
        GetFiles()
        adapterImages.notifyDataSetChanged()
    }

    override fun selectionImage(b: Boolean) {
        var count = 1
        mState = b
        invalidateOptionsMenu()
    }


    private fun isNetworkAvailable(): Boolean {

        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected()
    }

    @SuppressLint("StaticFieldLeak")
    inner class DeleteAllFiles() : AsyncTask<String, String, String>() {

        lateinit var progressBar: ProgressDialog
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.dismiss()

            GetFiles().execute()

            Toast.makeText(
                this@SavedCoverLetterAcitivity,
                "Deleted Successfully",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar = ProgressDialog(this@SavedCoverLetterAcitivity)
            progressBar.setTitle("Deleting All Files")
            progressBar.setMessage("Please Wait for a while")
            progressBar.show()

        }

        override fun doInBackground(vararg params: String?): String {

            if (tinyDB.getBoolean("Cpdf")) {
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

            Toast.makeText(
                this@SavedCoverLetterAcitivity,
                "Deleted Successfully",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar = ProgressDialog(this@SavedCoverLetterAcitivity)
            progressBar.setTitle("Deleting All Files")
            progressBar.setMessage("Please Wait for a while")
            progressBar.show()

        }

        override fun doInBackground(vararg params: String?): String {

            if (tinyDB.getBoolean("Cpdf")) {
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

}