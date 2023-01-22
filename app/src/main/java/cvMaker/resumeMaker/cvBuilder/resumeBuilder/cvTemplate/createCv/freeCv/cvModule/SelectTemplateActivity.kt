package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.FragmentCvTemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentPreviewCv.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.TemplateModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.MyApplication
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.ModelNewMain
import com.kaopiz.kprogresshud.KProgressHUD

class SelectTemplateActivity : AppCompatActivity(), FragmentCvTemplateAdapter.OnTemplateSelect {


    lateinit var recyclerView: RecyclerView
    lateinit var templateList: ArrayList<TemplateModelClass>
    lateinit var adapter: FragmentCvTemplateAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var tinyDB: TinyDB

    lateinit var fragmentManager: FragmentManager
    lateinit var transaction: FragmentTransaction

    lateinit var frameLayout: FrameLayout

    companion object {
        lateinit var selectTemplateModelObj: ModelNewMain
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

    override fun onStart() {
        super.onStart()
        this.overridePendingTransition(
            R.anim.anim_slide_in_top,
            R.anim.anim_slide_out_top
        );
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()
        setContentView(R.layout.activity_select_template)


        val toolbar = findViewById<Toolbar>(R.id.toolbarSelectTemplate)
        setSupportActionBar(toolbar)

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


        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
                MyDrawableCompat.setColorFilter(
                    recyclerView.background,
                    Color.parseColor("#6C48EF")
                )

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
                MyDrawableCompat.setColorFilter(
                    recyclerView.background,
                    Color.parseColor("#ED851A")
                )

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
                MyDrawableCompat.setColorFilter(
                    recyclerView.background,
                    Color.parseColor("#950806")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
                MyDrawableCompat.setColorFilter(
                    recyclerView.background,
                    Color.parseColor("#C8BA00")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
                MyDrawableCompat.setColorFilter(
                    recyclerView.background,
                    Color.parseColor("#296E01")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
                MyDrawableCompat.setColorFilter(
                    recyclerView.background,
                    Color.parseColor("#6A6A6A")
                )
            }
        }

        getUserDetails().execute()

    }

    override fun onResume() {
        super.onResume()
        frameLayout = findViewById(R.id.FragmentPreviewCvFrameLayout)

        when {
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Dark Light Gray" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkGrayBlueFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Dark Gray White" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkLightGrayCvFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Dark Blue White" -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkBlueWhiteCreamCVFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Dark Blue Cream" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, BlueCreamCvFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Gray White" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkGrayWhiteCVFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Light White" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, LightWhiteCVFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Dark Red White" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkRedWhiteCvFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Light Gray White" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, LightGrayWhiteCvFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "White Box" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, LightWhiteBoxCvFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Yellow Gray" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, YellowGrayCvFragment())
                    .commitAllowingStateLoss()
            }
            tinyDB.getString("CV_SELECTED_TEMPLATE_NAME") == "Other" -> {
                fragmentManager = supportFragmentManager
                transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.FragmentPreviewCvFrameLayout, BlueCreamCvFragment())
                transaction.commit()
            }
        }

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {
        val object4 = TemplateModelClass()
        object4.setName(getString(R.string.str_darkbluecream))
        object4.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_blue_dark_cream))
        templateList.add(object4)

        val object9 = TemplateModelClass()
        object9.setName(getString(R.string.str_whitebox))
        object9.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_whitebox))
        templateList.add(object9)

        val object7 = TemplateModelClass()
        object7.setName(getString(R.string.str_darkredwhite))
        object7.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_dark_redwhite))
        templateList.add(object7)

        val object8 = TemplateModelClass()
        object8.setName(getString(R.string.darklightwhite))
        object8.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_lightgraywhite))
        templateList.add(object8)


        val object10 = TemplateModelClass()
        object10.setName(getString(R.string.str_yellowgray))
        object10.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_yellowgray))
        templateList.add(object10)

        val object2 = TemplateModelClass()
        object2.setName(getString(R.string.str_darkgraywhite))
        object2.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_lighgray))
        templateList.add(object2)

        val object6 = TemplateModelClass()
        object6.setName(getString(R.string.str_lightwhite))
        object6.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_light_white))
        templateList.add(object6)


        val object5 = TemplateModelClass()
        object5.setName(getString(R.string.str_graywhite))
        object5.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_graywhite))
        templateList.add(object5)

        val object3 = TemplateModelClass()
        object3.setName(getString(R.string.str_darkbluewhite))
        object3.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_dark_blue_white_cream))
        templateList.add(object3)

        val object1 = TemplateModelClass()
        object1.setName(getString(R.string.str_dark_light_gray))
        object1.setTemplate(ContextCompat.getDrawable(this, R.drawable.temp_darkgray))
        templateList.add(object1)


    }

    override fun onTemplateClick(position: Int) {
        MyApplication.firbaseAnalaytics(this, "1", "onTemplateClick")

        when {
            templateList[position].getName().equals("Dark Light Gray") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkGrayBlueFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Dark Gray White") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkLightGrayCvFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Dark Blue White") -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkBlueWhiteCreamCVFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Dark Blue Cream") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, BlueCreamCvFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Gray White") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkGrayWhiteCVFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Light White") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, LightWhiteCVFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Dark Red White") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, DarkRedWhiteCvFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Light Gray White") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, LightGrayWhiteCvFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("White Box") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, LightWhiteBoxCvFragment())
                    .commitAllowingStateLoss()
            }
            templateList[position].getName().equals("Yellow Gray") -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentPreviewCvFrameLayout, YellowGrayCvFragment())
                    .commitAllowingStateLoss()


            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class getUserDetails() : AsyncTask<String, String, String>() {

        val progressBar = KProgressHUD(this@SelectTemplateActivity)
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            progressBar.setLabel("Loading Profile")
            progressBar.setCancellable(false)
            progressBar.setAnimationSpeed(2)
            progressBar.setDimAmount(0.5f)
            progressBar.show()

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Handler(Looper.myLooper()!!).postDelayed({
                progressBar.dismiss()
                supportFragmentManager.beginTransaction()
                    .add(R.id.FragmentPreviewCvFrameLayout, BlueCreamCvFragment())
                    .commitAllowingStateLoss()
            }, 1000)

        }

        override fun doInBackground(vararg params: String?): String {
            //getDetails(tinyDB.getString("UID"))
            return ""
        }

    }

//    fun getDetails(id: String) {
//        tinyDB.putBoolean("Boolean", true)
//        val db = DataBaseHandler(this)
//        val cursor: Cursor? = db.getRecord(id)
//        if (cursor != null) {
//            val cnic = cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Cnic))
//            val dob = cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_DOB))
//            val filename =
//                cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_FILE_NAME))
//            val name =
//                cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_PersonName))
//            val fatherName =
//                cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_FatherName))
//            val phone = cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Phone))
//            val nationality =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Nationality)))
//            val imagePath =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_imageURI)))
//            val email =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_emailID)))
//            val objective =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_objective)))
//            val achieve =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Achievements)))
//            val interest =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Interests)))
//            val language =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Language)))
//            val reference =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_Reference)))
//
//            //changes
//            val countryCode =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_CountryCode)))
//            val fullnumber =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_FullNumber)))
//            val address =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_ADDRESS)))
//            val gender =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COULMN_GENDER)))
//            val maritalStatus =
//                (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COULMN_MARTIALSTATUS)))
//
//            selectTemplateModelObj = ModelNewMain(
//                imagePath,
//                name,
//                fatherName,
//                phone,
//                email,
//                dob,
//                cnic,
//                nationality,
//                id,
//                filename,
//                objective,
//                interest,
//                language,
//                reference,
//                achieve,
//                fullnumber,
//                countryCode,
//                address,
//                gender,
//                maritalStatus
//            )
//
//            if (countryCode != "") {
//                tinyDB.putInt("countryCodeUpdate", countryCode.toInt())
//            }
//            tinyDB.putString("FullPhoneNumberUpdate", fullnumber)
//            tinyDB.putString("add", address)
//            db.getAllExperience(id).let { selectTemplateModelObj.setExp(it) }
//            db.getAllQualification(id).let { selectTemplateModelObj.setQualification(it) }
//            db.getAllTechSkills(id).let { selectTemplateModelObj.setSkills(it) }
//            db.getAllProjects(id).let { selectTemplateModelObj.setProjects(it) }
//
//
//            HomeActivity.cvModelHomeObj = selectTemplateModelObj
//            CreateCVActivity.cvModelmain = selectTemplateModelObj
//
//        }
//
//
//    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}