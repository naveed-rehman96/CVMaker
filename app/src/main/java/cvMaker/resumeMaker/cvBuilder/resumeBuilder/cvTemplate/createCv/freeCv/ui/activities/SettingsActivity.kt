package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB

class SettingsActivity : AppCompatActivity() {


    private lateinit var navRadioGroup: RadioGroup
    private lateinit var navShareIcon: ImageView
    private lateinit var navShare: LinearLayout
    private lateinit var navRateUs: LinearLayout
    private lateinit var navRateUsIcon: ImageView
    private lateinit var tinyDB: TinyDB
    private lateinit var rateUsLottie: LottieAnimationView
    private lateinit var settingsToolbar: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tinyDB = TinyDB(this)
        checkTheme()
        setContentView(R.layout.activity_settings)

        navRadioGroup = findViewById(R.id.nav_RadioGroup)
        settingsToolbar = findViewById(R.id.settingsToolbar)
        navShare = findViewById(R.id.nav_share_us)
        navShareIcon = findViewById(R.id.navShareIcon)
        navRateUs = findViewById(R.id.nav_rateUs)
        navRateUsIcon = findViewById(R.id.navRateUsIcon)
        rateUsLottie = findViewById(R.id.rateUsLottie)

        navShare.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "https://play.google.com/store/apps/details?id=$packageName"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share Via"))
        }
        navRateUs.setOnClickListener {
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
        rateUsLottie.setOnClickListener {
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

        navRadioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { _, checkedId ->

                when (checkedId) {
                    R.id.blue_theme_rb -> {
                        tinyDB.putString("APP_THEME", getString(R.string.theme_blue))
                        settingsToolbar.setBackgroundColor(ContextCompat.getColor(this ,
                            R.color.Blue_Theme
                        ))
                        window.statusBarColor = ContextCompat.getColor(this, R.color.Blue_Theme)

                    }
                    R.id.red_theme_rb -> {
                        tinyDB.putString("APP_THEME", getString(R.string.theme_red))
                        settingsToolbar.setBackgroundColor(ContextCompat.getColor(this ,
                            R.color.Red_Theme
                        ))
                        window.statusBarColor = ContextCompat.getColor(this, R.color.Red_Theme)


                    }
                    R.id.yellow_theme_rb -> {
                        tinyDB.putString("APP_THEME", getString(R.string.theme_yellow))
                        settingsToolbar.setBackgroundColor(ContextCompat.getColor(this ,
                            R.color.Yellow_Theme
                        ))
                        window.statusBarColor = ContextCompat.getColor(this, R.color.Yellow_Theme)


                    }
                    R.id.orange_theme_rb -> {
                        tinyDB.putString("APP_THEME", getString(R.string.theme_orange))
                        settingsToolbar.setBackgroundColor(ContextCompat.getColor(this ,
                            R.color.Orange_Theme
                        ))
                        window.statusBarColor = ContextCompat.getColor(this, R.color.Orange_Theme)

                    }
                    R.id.green_theme_rb -> {
                        tinyDB.putString("APP_THEME", getString(R.string.theme_green))
                        settingsToolbar.setBackgroundColor(ContextCompat.getColor(this ,
                            R.color.Green_Theme
                        ))
                        window.statusBarColor = ContextCompat.getColor(this, R.color.Green_Theme)

                    }
                    R.id.gray_theme_rb -> {
                        tinyDB.putString("APP_THEME", getString(R.string.theme_gray))
                        settingsToolbar.setBackgroundColor(ContextCompat.getColor(this ,
                            R.color.Gray_Theme
                        ))
                        window.statusBarColor = ContextCompat.getColor(this, R.color.Gray_Theme)

                    }
                }

            })

        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {

                navRadioGroup.check(R.id.blue_theme_rb)
                settingsToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {


                settingsToolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Orange_Theme
                    )
                )
                navRadioGroup.check(R.id.orange_theme_rb)
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                settingsToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
                navRadioGroup.check(R.id.red_theme_rb)
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {

                settingsToolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Yellow_Theme
                    )
                )
                navRadioGroup.check(R.id.yellow_theme_rb)

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {

                settingsToolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.Green_Theme
                    )
                )
                navRadioGroup.check(R.id.green_theme_rb)

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {

                settingsToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
                navRadioGroup.check(R.id.gray_theme_rb)
            }
            else -> {
                navRadioGroup.check(R.id.blue_theme_rb)
                settingsToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
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
}