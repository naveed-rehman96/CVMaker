package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.kaopiz.kprogresshud.KProgressHUD
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelNewMain
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCvTemplateActivities.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CreateCVActivity : AppCompatActivity() {
    private lateinit var btnPersonalInfo: ImageButton
    private lateinit var btnObjective: ImageButton
    private lateinit var btnExperience: ImageButton
    private lateinit var btnEducation: ImageButton
    private lateinit var btnTechnicalSkills: ImageButton
    private lateinit var btnAchievementsAwards: ImageButton
    private lateinit var btnTemplates: ImageButton
    private lateinit var btnProjects: ImageButton
    private lateinit var btnInterest: ImageButton
    private lateinit var btnLanguage: ImageButton
    private lateinit var btnReference: ImageButton

    lateinit var layoutPersonalInfo: LinearLayout
    lateinit var layoutObjective: LinearLayout
    lateinit var layoutExperience: LinearLayout
    lateinit var layoutQualification: LinearLayout
    lateinit var layoutTechnicalSkills: LinearLayout
    lateinit var layoutAchievements: LinearLayout
    lateinit var layoutProjects: LinearLayout
    lateinit var layoutInterest: LinearLayout
    lateinit var layoutLanguage: LinearLayout
    lateinit var layoutReference: LinearLayout
    lateinit var ScrollLinearLayout: LinearLayout

    lateinit var scroll1: View
    lateinit var scroll2: View
    lateinit var scroll3: View
    lateinit var scroll4: View
    lateinit var scroll5: View
    lateinit var scroll6: View
    lateinit var scroll7: View
    lateinit var scroll8: View
    lateinit var scroll9: View
    lateinit var scroll10: View

    val cvDatabase by inject<AppDatabase>()

    lateinit var txtPosition: TextView

    private lateinit var scrollView: HorizontalScrollView
    lateinit var tinyDB12: TinyDB

    companion object {
        lateinit var cvModelmain: CvMainModel

        lateinit var cvModelmain_c: ModelNewMain
        var xModel: ModelNewMain = ModelNewMain()
        lateinit var context: Context
        var mToast: Toast? = null
        lateinit var mViewPager2: ViewPager2

        fun showMessage(message: String) {
            if (cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mToast != null) {
                cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mToast!!.cancel()
            }
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mToast = Toast.makeText(
                cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.context,
                message,
                Toast.LENGTH_SHORT
            )
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mToast!!.show()
        }
    }

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tinyDB12 =
            TinyDB(
                this
            )
        tinyDB12.putBoolean("FirstCreated", false)
        checkTheme()
        setContentView(R.layout.activity_create_c_v_acitivity)
        initializeViews()

        when {
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_red) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_green) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
            }
        }

        cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2 = findViewById(R.id.viewpager2) // Get ViewPager2 view
        val adapter =
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.ViewPagerAdapter(
                this
            )
        cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.adapter = adapter
        cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.offscreenPageLimit = 5
        // Attach the adapter with our ViewPagerAdapter passing the host activity

        cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (position == 0) {
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.left, 0) }
                    scroll1()
                    txtPosition.text = "Position : 1/10"
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_buttonf)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 1) {
                    txtPosition.text = "Position : 2/10"
                    scroll2()
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.left, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 2) {
                    scroll3()
                    txtPosition.text = "Position : 3/10"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.left, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 3) {
                    scroll4()
                    txtPosition.text = "Position : 4/10"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.right / 8, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 4) {
                    scroll5()
                    txtPosition.text = "Position : 5/10"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.right / 7, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 5) {
                    scroll6()
                    txtPosition.text = "Position : 6/10"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.right / 3, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 6) {
                    scroll7()
                    txtPosition.text = "Position : 7/10"
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 7) {
                    scroll8()
                    txtPosition.text = "Position : 8/10"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.right / 2, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 8) {
                    scroll9()
                    txtPosition.text = "Position : 9/10"
                    scrollView.post { scrollView.scrollTo(ScrollLinearLayout.right, 0) }
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_buttonf))
                    layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 9) {
                    scroll10()
                    txtPosition.text = "Position : 10/10"
                    layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    layoutReference.setBackgroundResource((R.drawable.bg_buttonf))
                }
            }
        })

        layoutPersonalInfo.setBackgroundResource(R.drawable.bg_buttonf)
        layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
        layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
        layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
        layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
        layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
        layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
        layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
        layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
        layoutReference.setBackgroundResource((R.drawable.bg_button_white))

        layoutPersonalInfo.setOnClickListener {
            txtPosition.text = "Position : 1/10"
            scroll1()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(0, false)
        }
        layoutObjective.setOnClickListener {
            scroll2()
            txtPosition.text = "Position : 2/10"
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(1, false)
        }
        layoutExperience.setOnClickListener {
            txtPosition.text = "Position : 3/10"
            scroll3()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(2, false)
        }
        layoutQualification.setOnClickListener {
            txtPosition.text = "Position : 4/10"
            scroll4()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(3, false)
        }
        layoutTechnicalSkills.setOnClickListener {
            txtPosition.text = "Position : 5/10"
            scroll5()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(4, false)
        }
        layoutAchievements.setOnClickListener {
            txtPosition.text = "Position : 6/10"
            scroll6()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(5, false)
        }
        layoutProjects.setOnClickListener {
            txtPosition.text = "Position : 7/10"
            scroll7()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(6, false)
        }
        layoutInterest.setOnClickListener {
            txtPosition.text = "Position : 8/10"
            scroll8()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(7, false)
        }
        layoutLanguage.setOnClickListener {
            txtPosition.text = "Position : 9/10"
            scroll9()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(8, false)
        }
        layoutReference.setOnClickListener {
            scroll10()
            txtPosition.text = "Position : 10/10"
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.mViewPager2.setCurrentItem(9, false)
        }
    }

    override fun onResume() {
        super.onResume()
        val tinyDB = TinyDB(this)
        if (!tinyDB.getString("UID").equals("")) {
            getUserDetails()
        }
    }

    private fun scroll1() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll2() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll3() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll4() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll5() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll6() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll7() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll8() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll9() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll10() {
        MyDrawableCompat.setColorFilter(scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(scroll10.background, Color.parseColor("#0078FF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun checkTheme() {
        if (tinyDB12.getString("APP_THEME") == getString(R.string.theme_blue)) {
            super.setTheme(R.style.AppTheme)
        } else if (tinyDB12.getString("APP_THEME") == getString(R.string.theme_orange)) {
            super.setTheme(R.style.AppThemeOrange)
        } else if (tinyDB12.getString("APP_THEME") == getString(R.string.theme_red)) {
            super.setTheme(R.style.AppThemeRed)
        } else if (tinyDB12.getString("APP_THEME") == getString(R.string.theme_yellow)) {
            super.setTheme(R.style.AppThemeYellow)
        } else if (tinyDB12.getString("APP_THEME") == getString(R.string.theme_green)) {
            super.setTheme(R.style.AppThemeGreen)
        } else if (tinyDB12.getString("APP_THEME") == getString(R.string.theme_gray)) {
            super.setTheme(R.style.AppThemeGray)
        }
    }

    private fun initializeViews() {
        cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.Companion.context = applicationContext
        ScrollLinearLayout = findViewById(R.id.ScrollLinearLayout)

        scroll1 = findViewById(R.id.scroll1)
        scroll2 = findViewById(R.id.scroll2)
        scroll3 = findViewById(R.id.scroll3)
        scroll4 = findViewById(R.id.scroll4)
        scroll5 = findViewById(R.id.scroll5)
        scroll6 = findViewById(R.id.scroll6)
        scroll7 = findViewById(R.id.scroll7)
        scroll8 = findViewById(R.id.scroll8)
        scroll9 = findViewById(R.id.scroll9)
        scroll10 = findViewById(R.id.scroll10)

        scrollView = findViewById(R.id.scrollViewButton)
        btnPersonalInfo = findViewById(R.id.btnPersonalInfo)
        btnReference = findViewById(R.id.btnReference)
        btnLanguage = findViewById(R.id.btnLanguage)
        btnInterest = findViewById(R.id.btnInterest)
        btnProjects = findViewById(R.id.btnProjects)
        // btnTemplates = findViewById<ImageButton>(R.id.btnTemplates)
        btnAchievementsAwards = findViewById(R.id.btnAchievementsAwards)
        btnTechnicalSkills = findViewById(R.id.btnTechnicalSkills)
        btnEducation = findViewById(R.id.btnEducation)
        btnExperience = findViewById(R.id.btnExperince)
        btnObjective = findViewById(R.id.btnObjective)
        txtPosition = findViewById(R.id.ScreenPosition)

        layoutPersonalInfo = findViewById(R.id.layoutPersonalInfo)
        layoutObjective = findViewById(R.id.layoutObjective)
        layoutExperience = findViewById(R.id.layoutExperience)
        layoutQualification = findViewById(R.id.layoutQualification)
        layoutTechnicalSkills = findViewById(R.id.layoutTechnicalSkills)
        layoutAchievements = findViewById(R.id.layoutAchievements)
        layoutProjects = findViewById(R.id.layoutProjects)
        layoutInterest = findViewById(R.id.layoutInterest)
        layoutLanguage = findViewById(R.id.layoutLanguage)
        layoutReference = findViewById(R.id.layoutReference)
        // layoutTemplates = findViewById(R.id.layoutTemplates)

        toolbar = findViewById(R.id.toolbarCreateCV)
        setSupportActionBar(toolbar)
    }

    private fun moveToPreview() {
        getUserDetails()
    }

    private fun getUserDetails() {
        val progressBar = KProgressHUD(this@CreateCVActivity)
        lifecycleScope.launch {
            progressBar.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            progressBar.setLabel("Loading Profile")
            progressBar.setCancellable(false)
            progressBar.setAnimationSpeed(2)
            progressBar.setDimAmount(0.5f)

            cvMainModel.personInfo =
                cvDatabase.cvDao().getCVbyId(tinyDB12.getString("UID")) as CVModelEntity
            cvMainModel.experienceInfo = cvDatabase.experienceDAO()
                .getAllExperience(tinyDB12.getString("UID")) as ArrayList<ExperienceEntity>
            cvMainModel.qualificationEntity = cvDatabase.qualificationDAO()
                .getAllQualification(tinyDB12.getString("UID")) as ArrayList<QualificationEntity>
            cvMainModel.skillsEntity = cvDatabase.skillsDao()
                .getAllSkills(tinyDB12.getString("UID")) as ArrayList<SkillsEntity>
            cvMainModel.projectsEntity = cvDatabase.projectsDao()
                .getAllProject(tinyDB12.getString("UID")) as ArrayList<ProjectsEntity>
        }.invokeOnCompletion {
            progressBar.dismiss()
            Log.e(
                "ImageUri",
                "onPostExecute: ${cvMainModel.personInfo.imagePath}"
            )
        }
    }
    override fun onBackPressed() {
        val dialogBuilder1 = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_exit_cv, null)

        dialogBuilder1.setView(dialogView)

        val cancel: Button = dialogView.findViewById(R.id.btnCancelExitDialog)
        val Ok: Button = dialogView.findViewById(R.id.btnOkExitDialog)

        when {
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#6C48EF"))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#ED851A"))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_red) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#950806"))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#C8BA00"))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#296E01"))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(Ok.background, Color.parseColor("#6A6A6A"))
            }
        }

        val alertDialog = dialogBuilder1.create()
        alertDialog.setCancelable(false)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        cancel.setOnClickListener {
            alertDialog.dismiss()
        }

        Ok.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.create_new_cv_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.viewCv -> {
                moveToPreview()
                if (tinyDB12.getBoolean("IS_SELECTED")) {
                    openTemplate(tinyDB12.getString("TEMPL_Name"))
                } else {
                    val intent = Intent(this, cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.SelectTemplateActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openTemplate(template: String) {
        when (template) {
            "Dark Light Gray" -> {
                val intent = Intent(this, PreviewDark_GrayBlueActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Dark Gray White" -> {
                val intent = Intent(this, PreviewDark_light_GrayActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Dark Blue White" -> {
                val intent = Intent(this, PreviewDarkBlue_whiteCreamActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Dark Blue Cream" -> {
                val intent = Intent(this, PreviewBlueCreamCreamActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Gray White" -> {
                val intent = Intent(this, PreviewDark_GrayWhiteActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Light White" -> {
                val intent = Intent(this, PreviewlightWhiteActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Dark Red White" -> {
                val intent = Intent(this, PreviewDarkRedWhiteActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Light Gray White" -> {
                val intent = Intent(this, PreviewlightGrayWhiteActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "White Box" -> {
                val intent = Intent(this, PreviewlightWhiteboxActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            "Yellow Gray" -> {
                val intent = Intent(this, PreviewYellowGrayActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
    }

    internal class ViewPagerAdapter(fa: FragmentActivity?) : FragmentStateAdapter(fa!!) {
        private val mFragments = arrayOf(
            // Initialize fragments views
            // Fragment views are initialized like any other fragment (Extending Fragment)
            PersonalInfoFragment(),
            ObjectiveFragment(),
            ExperienceFragment(),
            QualificationFragment(),
            TechnicalSkillsFragment(),
            AchievementsFragment(),
            ProjectsFragment(),
            InterestFragment(),
            LanguageFragment(),
            ReferenceFragment()
//            ,
//            TemplateFragment()
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
