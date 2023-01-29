package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputLayout
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.MyApplication
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.TemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvTemplateModelClass

class TemplatesCVActivity : AppCompatActivity(), TemplateAdapter.OnTemplateSelect {
    lateinit var recyclerView: RecyclerView
    lateinit var templateList: ArrayList<CvTemplateModelClass>
    lateinit var adapter: TemplateAdapter
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var context: Context
    lateinit var tinyDB: TinyDB

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB =
            TinyDB(
                this
            )
        checkTheme()

        setContentView(R.layout.activity_templates_c_v)

        val toolbar = findViewById<Toolbar>(R.id.toolbarSelectTemplate)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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

        context = this
        tinyDB =
            TinyDB(
                context
            )
        templateList = ArrayList()
        addDataToArray()

        recyclerView = findViewById(R.id.templateRcv)
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        adapter = TemplateAdapter(this, templateList, this)
        recyclerView.adapter = adapter
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {
        val object4 = CvTemplateModelClass()
        object4.setName(getString(R.string.str_darkbluecream))
        object4.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_blue_dark_cream))
        templateList.add(object4)
        val object7 = CvTemplateModelClass()
        object7.setName(getString(R.string.str_darkredwhite))
        object7.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_dark_redwhite))
        templateList.add(object7)

        val object10 = CvTemplateModelClass()
        object10.setName(getString(R.string.str_yellowgray))
        object10.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_yellowgray))
        templateList.add(object10)

        val object1 = CvTemplateModelClass()
        object1.setName(getString(R.string.str_dark_light_gray))
        object1.setTemplate(ContextCompat.getDrawable(this, R.drawable.temp_darkgray))
        templateList.add(object1)

        val object2 = CvTemplateModelClass()
        object2.setName(getString(R.string.str_darkgraywhite))
        object2.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_lighgray))
        templateList.add(object2)

        val object3 = CvTemplateModelClass()
        object3.setName(getString(R.string.str_darkbluewhite))
        object3.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_dark_blue_white_cream))
        templateList.add(object3)

        val object5 = CvTemplateModelClass()
        object5.setName(getString(R.string.str_graywhite))
        object5.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_graywhite))
        templateList.add(object5)

        val object6 = CvTemplateModelClass()
        object6.setName(getString(R.string.str_lightwhite))
        object6.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_light_white))
        templateList.add(object6)

        val object8 = CvTemplateModelClass()
        object8.setName(getString(R.string.darklightwhite))
        object8.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_lightgraywhite))
        templateList.add(object8)

        val object9 = CvTemplateModelClass()
        object9.setName(getString(R.string.str_whitebox))
        object9.setTemplate(ContextCompat.getDrawable(this, R.drawable.templ_whitebox))
        templateList.add(object9)
    }

    override fun onTemplateClick(position: Int) {
        MyApplication.firbaseAnalaytics(this, "1", "onTemplateClick")
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cv_name, null)
        dialogBuilder.setView(dialogView)
        var cvName: TextInputLayout
        cvName = dialogView.findViewById(R.id.CV_name_input)
        var createBt: Button = dialogView.findViewById(R.id.btnCreateCV)
        var cancelBt: Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
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
                tinyDB.putBoolean("Boolean", false)
                tinyDB.putBoolean("IS_SELECTED", false)
                tinyDB.putString("CV_SELECTED_TEMPLATE_NAME", templateList[position].getName())
                val intent = Intent(this, cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
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
}
