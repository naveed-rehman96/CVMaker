package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.MyApplication
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.FragmentCvTemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetterTemplates.FragmentBlueLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetterTemplates.FragmentBrownLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetterTemplates.FragmentGreenLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetterTemplates.FragmentWhiteLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.coverletter.CoverLetterFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelCoverLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvTemplateModelClass

class LetterTemplateActivity : AppCompatActivity(), FragmentCvTemplateAdapter.OnTemplateSelect {

    lateinit var recyclerView: RecyclerView
    lateinit var templateList: ArrayList<CvTemplateModelClass>
    lateinit var adapter: FragmentCvTemplateAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var tinyDB: TinyDB

    lateinit var fragmentManager: FragmentManager
    lateinit var transaction: FragmentTransaction

    lateinit var frameLayout: FrameLayout

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

    override fun onStart() {
        super.onStart()
        this.overridePendingTransition(
            R.anim.anim_slide_in_top,
            R.anim.anim_slide_out_top
        )
    }

    @SuppressLint("Range")
    private fun getDetails() {
        val datebase = DataBaseHandler(this)
        val cursor = datebase.fetchLetterRecord(tinyDB.getString("CL_ID"))

        CoverLetterFragment.modelObjectCLFrag = ModelCoverLetter()
        if (cursor != null) {
            val id = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_CID)))
            val filename = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_CLNAME)))
            val sName = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_NAME)))
            val sDesignation = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_DESIG)))
            val sAddress = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_ADDRESS)))
            val sPhone = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_PHONE)))
            val sEmail = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_EMAIL)))
            val rName = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_REC_NAME)))
            val rDesignation = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_REC_DESIG)))
            val rAddress = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_REC_ADDRESS)))
            val date = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_DATE)))
            val description = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_DESCRIPTION)))
            val subject = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_SUBJECT)))
            val company = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_COMPANY)))

            CoverLetterFragment.modelObjectCLFrag.setCid(id)
            CoverLetterFragment.modelObjectCLFrag.setCFileName(filename)
            CoverLetterFragment.modelObjectCLFrag.setSenderName(sName)
            CoverLetterFragment.modelObjectCLFrag.setSenderDesignation(sDesignation)
            CoverLetterFragment.modelObjectCLFrag.setSenderAddress(sAddress)
            CoverLetterFragment.modelObjectCLFrag.setSenderPhone(sPhone)
            CoverLetterFragment.modelObjectCLFrag.setSenderEmail(sEmail)
            CoverLetterFragment.modelObjectCLFrag.setReceiverName(rName)
            CoverLetterFragment.modelObjectCLFrag.setReceiverDesignation(rDesignation)
            CoverLetterFragment.modelObjectCLFrag.setReceiverAddress(rAddress)
            CoverLetterFragment.modelObjectCLFrag.setDate(date)
            CoverLetterFragment.modelObjectCLFrag.setDescription(description)
            CoverLetterFragment.modelObjectCLFrag.setSenderSubject(subject)
            CoverLetterFragment.modelObjectCLFrag.setSenderCompany(company)

            Log.e("Reciever", "onTemplateClick: $rName")
            Log.e("Reciever", "onTemplateClick: $rAddress")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()
        setContentView(R.layout.activity_select_letterview_template)

        getDetails()

        val toolbar = findViewById<Toolbar>(R.id.toolbarSelectTemplate)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Cover Letter Templates"
        val backBtn = findViewById<ImageButton>(R.id.selectTemplateBackButton)
        backBtn.setOnClickListener {
            onBackPressed()
        }
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

        templateList = ArrayList()
        addDataToArray()

        recyclerView = findViewById(R.id.templateRcv)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = FragmentCvTemplateAdapter(this, templateList, this)
        recyclerView.adapter = adapter

        frameLayout = findViewById(R.id.FragmentPreviewCvFrameLayout)
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.FragmentPreviewCvFrameLayout, FragmentBlueLetter())
        transaction.commit()

        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
                MyDrawableCompat.setColorFilter(recyclerView.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
                MyDrawableCompat.setColorFilter(recyclerView.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
                MyDrawableCompat.setColorFilter(recyclerView.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
                MyDrawableCompat.setColorFilter(recyclerView.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
                MyDrawableCompat.setColorFilter(recyclerView.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
                MyDrawableCompat.setColorFilter(recyclerView.background, Color.parseColor("#6A6A6A"))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        frameLayout = findViewById(R.id.FragmentPreviewCvFrameLayout)
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.FragmentPreviewCvFrameLayout, FragmentBlueLetter())
        transaction.commit()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {
        val object1 = CvTemplateModelClass()
        object1.setName("Blue Bar")
        object1.setTemplate(ContextCompat.getDrawable(this, R.drawable.bluebar))
        templateList.add(object1)

        val object2 = CvTemplateModelClass()
        object2.setName("Green Bar")
        object2.setTemplate(ContextCompat.getDrawable(this, R.drawable.greenbar))
        templateList.add(object2)

        val object3 = CvTemplateModelClass()
        object3.setName("White BG")
        object3.setTemplate(ContextCompat.getDrawable(this, R.drawable.white))
        templateList.add(object3)

        val object4 = CvTemplateModelClass()
        object4.setName("Brown Bar")
        object4.setTemplate(ContextCompat.getDrawable(this, R.drawable.brown))
        templateList.add(object4)
    }

    override fun onTemplateClick(position: Int) {
        MyApplication.firbaseAnalaytics(this, "1", "onTemplateClickLetter")
        if (templateList[position].getName().equals("Blue Bar")) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentPreviewCvFrameLayout, FragmentBlueLetter()).commitAllowingStateLoss()
        } else if (templateList[position].getName().equals("Green Bar")) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentPreviewCvFrameLayout, FragmentGreenLetter()).commitAllowingStateLoss()
        } else if (templateList[position].getName().equals("White BG")) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentPreviewCvFrameLayout, FragmentWhiteLetter()).commitAllowingStateLoss()
        } else if (templateList[position].getName().equals("Brown Bar")) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentPreviewCvFrameLayout, FragmentBrownLetter()).commitAllowingStateLoss()
        }
    }
}
