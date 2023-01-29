package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB

class SplashScreenActivity : AppCompatActivity() {
    private var interstitialAdSplash: InterstitialAd? = null
    lateinit var tiny: TinyDB
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(in_app_layout())

        // Initialize the Mobile Ads SDK.
        tiny = TinyDB(this)

        handler = Handler()
        handler.postDelayed(mSplashHandler, 6000)
    }

    private var mSplashHandler: Runnable = Runnable {
        if (interstitialAdSplash != null) {
            interstitialAdSplash!!.show(this)
            interstitialAdSplash!!.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        this@SplashScreenActivity.interstitialAdSplash = null
                        startActivity(
                            Intent(
                                this@SplashScreenActivity,
                                WelcomeActivity::class.java
                            )
                        )
                        finish()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        this@SplashScreenActivity.interstitialAdSplash = null
                    }

                    override fun onAdShowedFullScreenContent() {
                    }
                }
        } else {
            if (!TinyDB(this).getBoolean("ShowWelcomeScreen")) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }

    protected fun in_app_layout(): Int {
        return R.layout.activity_splash__screen__acitivity
    }
}
