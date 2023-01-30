package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter.DescriptionFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter.SentDateFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter.SentFromFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter.SentToFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.coverletter.CoverLetterFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelCoverLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewBlueLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewBrownLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewGreenLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewWhiteLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel
import org.koin.android.ext.android.inject

class CreateCoverLetterActivity : AppCompatActivity() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var tinyDB: TinyDB

    lateinit var scrollView: HorizontalScrollView
    lateinit var txtPosition: TextView
    lateinit var btn_backCreateLetterActivity: ImageView

    val cvViewModel: CvViewModel by inject()


    lateinit var layoutSentFrom: LinearLayout
    lateinit var layoutSentDate: LinearLayout
    lateinit var layoutDescription: LinearLayout
    lateinit var ScrollLinearLayout1: LinearLayout
    lateinit var layoutSentTo: LinearLayout
    companion object {
        lateinit var modelMainLetter: ModelCoverLetter
        lateinit var mViewPager2: ViewPager2
    }
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
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()

        setContentView(R.layout.activity_create_cover_letter)
        modelMainLetter = ModelCoverLetter()
        this.tinyDB.putBoolean("VIEWLetter", false)
        scrollView = findViewById(R.id.scrollViewButton)
        btn_backCreateLetterActivity = findViewById(R.id.btn_backCreateLetterActivity)
        txtPosition = findViewById(R.id.ScreenPosition)

        layoutSentFrom = findViewById(R.id.layoutSentFrom)
        layoutSentDate = findViewById(R.id.layoutSentDate)
        ScrollLinearLayout1 = findViewById(R.id.ScrollLinearLayout1)
        layoutDescription = findViewById(R.id.layoutDescription)
        layoutSentTo = findViewById(R.id.layoutSentTo)

        toolbar = findViewById(R.id.toolbarCreateCoverLetter)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cover Letter"
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

        btn_backCreateLetterActivity.setOnClickListener {
            onBackPressed()
        }

        if (intent != null && intent!!.extras != null) {
            val letterID = intent?.getStringExtra("CID").toString()

            val datebase = DataBaseHandler(this)
            val cursor = datebase.fetchLetterRecord(letterID)

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

                modelMainLetter.setCid(id)
                modelMainLetter.setCFileName(filename)
                modelMainLetter.setSenderName(sName)
                modelMainLetter.setSenderDesignation(sDesignation)
                modelMainLetter.setSenderAddress(sAddress)
                modelMainLetter.setSenderPhone(sPhone)
                modelMainLetter.setSenderEmail(sEmail)
                modelMainLetter.setReceiverName(rName)
                modelMainLetter.setReceiverDesignation(rDesignation)
                modelMainLetter.setReceiverAddress(rAddress)
                modelMainLetter.setDate(date)
                modelMainLetter.setDescription(description)
                modelMainLetter.setSenderSubject(subject)
                modelMainLetter.setSenderCompany(company)

                Log.e("Reciever", "onTemplateClick: $rName")
                Log.e("Reciever", "onTemplateClick: $rAddress")

                CoverLetterFragment.modelObjectCLFrag = modelMainLetter
            }
        }

        layoutSentFrom.setBackgroundResource(R.drawable.bg_buttonf)
        layoutSentDate.setBackgroundResource((R.drawable.bg_button_white))
        layoutDescription.setBackgroundResource((R.drawable.bg_button_white))
        layoutSentTo.setBackgroundResource((R.drawable.bg_button_white))

        mViewPager2 = findViewById(R.id.viewpager123)
        val adapter = ViewPagerAdapterCover(this)
        mViewPager2.adapter = adapter
        mViewPager2.offscreenPageLimit = 1

        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (position == 0) {
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout1.left, 0) }
                    txtPosition.text = "Position : 1/4"
                    layoutSentFrom.setBackgroundResource(R.drawable.bg_buttonf)
                    layoutSentDate.setBackgroundResource((R.drawable.bg_button_white))
                    layoutDescription.setBackgroundResource((R.drawable.bg_button_white))
                    layoutSentTo.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 1) {
                    txtPosition.text = "Position : 2/4"
                    layoutSentFrom.setBackgroundResource(R.drawable.bg_button_white)
                    layoutSentDate.setBackgroundResource((R.drawable.bg_button_white))
                    layoutDescription.setBackgroundResource((R.drawable.bg_button_white))
                    layoutSentTo.setBackgroundResource((R.drawable.bg_buttonf))
                }
                if (position == 2) {
                    txtPosition.text = "Position : 3/4"
                    layoutSentFrom.setBackgroundResource(R.drawable.bg_button_white)
                    layoutSentDate.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutDescription.setBackgroundResource((R.drawable.bg_button_white))
                    layoutSentTo.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 3) {
                    txtPosition.text = "Position : 4/4"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout1.right, 0) }
                    layoutSentFrom.setBackgroundResource(R.drawable.bg_button_white)
                    layoutSentDate.setBackgroundResource((R.drawable.bg_button_white))
                    layoutDescription.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutSentTo.setBackgroundResource((R.drawable.bg_button_white))
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        layoutSentFrom.setOnClickListener {
            txtPosition.text = "Position : 1/4"
            mViewPager2.setCurrentItem(0, false)
        }
        layoutSentTo.setOnClickListener {
            txtPosition.text = "Position : 2/4"
            mViewPager2.setCurrentItem(1, false)
        }
        layoutSentDate.setOnClickListener {
            txtPosition.text = "Position : 3/4"
            mViewPager2.setCurrentItem(2, false)
        }
        layoutDescription.setOnClickListener {
            txtPosition.text = "Position : 4/4"
            mViewPager2.setCurrentItem(3, false)
        }

        if (tinyDB.getBoolean("VIEWLetter")) {
            val intent = Intent(this, LetterTemplateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val dialogBuilder1 = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_exit_cv, null)
        dialogBuilder1.setView(dialogView)
        val cancel: Button = dialogView.findViewById(R.id.btnCancelExitDialog)
        val Ok: Button = dialogView.findViewById(R.id.btnOkExitDialog)
        val alertDialog = dialogBuilder1.create()
        alertDialog.setCancelable(false)
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#6A6A6A"))
            }
        }

        cancel.setOnClickListener {
            alertDialog.dismiss()
        }

        Ok.setOnClickListener {
            super.onBackPressed()
            alertDialog.dismiss()
            CoverLetterFragment.modelObjectCLFrag = modelMainLetter
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.create_new_cv_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.viewCv -> {
                getDetails()
                val intent = Intent(this, LetterTemplateActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getDetails() {
        val datebase = DataBaseHandler(this)
        val cursor = datebase.fetchLetterRecord(tinyDB.getString("CL_ID"))

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

            modelMainLetter.setCid(id)
            modelMainLetter.setCFileName(filename)
            modelMainLetter.setSenderName(sName)
            modelMainLetter.setSenderDesignation(sDesignation)
            modelMainLetter.setSenderAddress(sAddress)
            modelMainLetter.setSenderPhone(sPhone)
            modelMainLetter.setSenderEmail(sEmail)
            modelMainLetter.setReceiverName(rName)
            modelMainLetter.setReceiverDesignation(rDesignation)
            modelMainLetter.setReceiverAddress(rAddress)
            modelMainLetter.setDate(date)
            modelMainLetter.setDescription(description)
            modelMainLetter.setSenderSubject(subject)
            modelMainLetter.setSenderCompany(company)

            Log.e("Reciever", "onTemplateClick: $rName")
            Log.e("Reciever", "onTemplateClick: $rAddress")

            CoverLetterFragment.modelObjectCLFrag = modelMainLetter
        }
    }

    private fun selectTemplate(templateName: String?) {
        if (templateName.equals("Blue Bar")) {
            val intent = Intent(this, PreviewBlueLetterActivity::class.java)
            startActivity(intent)
        } else if (templateName.equals("Green Bar")) {
            val intent = Intent(this, PreviewGreenLetterActivity::class.java)
            startActivity(intent)
        } else if (templateName.equals("White BG")) {
            val intent = Intent(this, PreviewWhiteLetterActivity::class.java)
            startActivity(intent)
        } else if (templateName.equals("Brown Bar")) {
            val intent = Intent(this, PreviewBrownLetterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        tinyDB.putString("DESCRIPTION", "")
        tinyDB.putString("cdate", "")
        tinyDB.putString("s_name", "")
        tinyDB.putString("s_desig", "")
        tinyDB.putString("s_address", "")
        tinyDB.putString("s_subject", "")
        tinyDB.putString("s_company", "")
        tinyDB.putString("s_email", "")
        tinyDB.putString("s_phone", "")
        tinyDB.putString("r_name", "")
        tinyDB.putString("r_desig", "")
        tinyDB.putString("r_address", "")
    }

    internal class ViewPagerAdapterCover(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
        private val mFragments = arrayOf(
            // Initialize fragments views
            // Fragment views are initialized like any other fragment (Extending Fragment)
            SentFromFragment(),
            SentToFragment(),
            SentDateFragment(),
            DescriptionFragment()
        )

        override fun getItemCount(): Int {
            return mFragments.size // Number of fragments displayed
        }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

        override fun createFragment(position: Int): Fragment {
            return mFragments[position]
        }
    }
}
