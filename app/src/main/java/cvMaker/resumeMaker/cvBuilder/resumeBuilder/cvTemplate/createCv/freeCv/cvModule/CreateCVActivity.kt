package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.* // ktlint-disable no-wildcard-imports
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.customViews.KProgressHUD
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.databinding.ActivityCreateCVAcitivityBinding
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV.* // ktlint-disable no-wildcard-imports
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.* // ktlint-disable no-wildcard-imports
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CreateCVActivity : AppCompatActivity() {

    private val binding: ActivityCreateCVAcitivityBinding by lazy {
        ActivityCreateCVAcitivityBinding.inflate(layoutInflater)
    }

    val cvDatabase by inject<AppDatabase>()

    lateinit var tinyDB12: TinyDB

    companion object {
        lateinit var mViewPager2: ViewPager2
    }

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
        setSupportActionBar(binding.toolbarCreateCV)

        when {
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                binding.toolbarCreateCV.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                binding.toolbarCreateCV.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_red) -> {
                binding.toolbarCreateCV.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                binding.toolbarCreateCV.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_green) -> {
                binding.toolbarCreateCV.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                binding.toolbarCreateCV.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
            }
        }

        mViewPager2 = findViewById(R.id.viewpager2) // Get ViewPager2 view
        val adapter =
            ViewPagerAdapter(
                this
            )
        mViewPager2.adapter = adapter
        mViewPager2.offscreenPageLimit = 5
        // Attach the adapter with our ViewPagerAdapter passing the host activity

        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (position == 0) {
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.left, 0) }
                    scroll1()
                    binding.ScreenPosition.text = "Position : 1/10"
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_buttonf)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 1) {
                    binding.ScreenPosition.text = "Position : 2/10"
                    scroll2()
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.left, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 2) {
                    scroll3()
                    binding.ScreenPosition.text = "Position : 3/10"
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.left, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 3) {
                    scroll4()
                    binding.ScreenPosition.text = "Position : 4/10"
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.right / 8, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 4) {
                    scroll5()
                    binding.ScreenPosition.text = "Position : 5/10"
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.right / 7, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 5) {
                    scroll6()
                    binding.ScreenPosition.text = "Position : 6/10"
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.right / 3, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 6) {
                    scroll7()
                    binding.ScreenPosition.text = "Position : 7/10"
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 7) {
                    scroll8()
                    binding.ScreenPosition.text = "Position : 8/10"
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.right / 2, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 8) {
                    scroll9()
                    binding.ScreenPosition.text = "Position : 9/10"
                    binding.scrollViewButton.post { binding.scrollViewButton.scrollTo(binding.ScrollLinearLayout.right, 0) }
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_buttonf))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))
                }
                if (position == 9) {
                    scroll10()
                    binding.ScreenPosition.text = "Position : 10/10"
                    binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_button_white)
                    binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
                    binding.layoutReference.setBackgroundResource((R.drawable.bg_buttonf))
                }
            }
        })

        binding.layoutPersonalInfo.setBackgroundResource(R.drawable.bg_buttonf)
        binding.layoutObjective.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutExperience.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutQualification.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutTechnicalSkills.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutAchievements.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutProjects.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutInterest.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutLanguage.setBackgroundResource((R.drawable.bg_button_white))
        binding.layoutReference.setBackgroundResource((R.drawable.bg_button_white))

        binding.layoutPersonalInfo.setOnClickListener {
            binding.ScreenPosition.text = "Position : 1/10"
            scroll1()
            mViewPager2.setCurrentItem(0, false)
        }
        binding.layoutObjective.setOnClickListener {
            scroll2()
            binding.ScreenPosition.text = "Position : 2/10"
            mViewPager2.setCurrentItem(1, false)
        }
        binding.layoutExperience.setOnClickListener {
            binding.ScreenPosition.text = "Position : 3/10"
            scroll3()
            mViewPager2.setCurrentItem(2, false)
        }
        binding.layoutQualification.setOnClickListener {
            binding.ScreenPosition.text = "Position : 4/10"
            scroll4()
            mViewPager2.setCurrentItem(3, false)
        }
        binding.layoutTechnicalSkills.setOnClickListener {
            binding.ScreenPosition.text = "Position : 5/10"
            scroll5()
            mViewPager2.setCurrentItem(4, false)
        }
        binding.layoutAchievements.setOnClickListener {
            binding.ScreenPosition.text = "Position : 6/10"
            scroll6()
            mViewPager2.setCurrentItem(5, false)
        }
        binding.layoutProjects.setOnClickListener {
            binding.ScreenPosition.text = "Position : 7/10"
            scroll7()
            mViewPager2.setCurrentItem(6, false)
        }
        binding.layoutInterest.setOnClickListener {
            binding.ScreenPosition.text = "Position : 8/10"
            scroll8()
            mViewPager2.setCurrentItem(7, false)
        }
        binding.layoutLanguage.setOnClickListener {
            binding.ScreenPosition.text = "Position : 9/10"
            scroll9()
            mViewPager2.setCurrentItem(8, false)
        }
        binding.layoutReference.setOnClickListener {
            scroll10()
            binding.ScreenPosition.text = "Position : 10/10"
            mViewPager2.setCurrentItem(9, false)
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
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll2() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll3() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll4() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll5() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll6() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll7() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll8() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll9() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#0078FF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#FFFFFF"))
        // MyDrawableCompat.setColorFilter(scroll11.background, Color.parseColor("#FFFFFF"))
    }

    private fun scroll10() {
        MyDrawableCompat.setColorFilter(binding.scroll1.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll2.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll3.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll4.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll5.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll6.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll7.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll8.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll9.background, Color.parseColor("#FFFFFF"))
        MyDrawableCompat.setColorFilter(binding.scroll10.background, Color.parseColor("#0078FF"))
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

    var alertDialog: AlertDialog ? = null

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

        alertDialog = dialogBuilder1.create()

        alertDialog?.setCancelable(false)
        alertDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        cancel.setOnClickListener {
            alertDialog?.dismiss()
        }

        Ok.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        alertDialog?.show()
    }

    override fun onDestroy() {
        if (alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
        }
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
                    val intent = Intent(this, SelectTemplateActivity::class.java)
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
        )

        override fun getItemCount(): Int {
            return mFragments.size // Number of fragments displayed
        }

        override fun createFragment(position: Int): Fragment {
            return mFragments[position]
        }
    }
}
