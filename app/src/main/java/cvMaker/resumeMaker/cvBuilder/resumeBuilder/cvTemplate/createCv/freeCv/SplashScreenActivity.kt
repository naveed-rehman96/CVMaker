package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*
import kotlin.collections.ArrayList

class SplashScreenActivity : AppCompatActivity() {
    private var interstitialAdSplash: InterstitialAd? = null
    lateinit var tiny: TinyDB
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(in_app_layout())

        // Initialize the Mobile Ads SDK.
        tiny = TinyDB(this)

        if (!tiny.getBoolean("inApp")) {
            loadAd()
        }

        handler = Handler()
        handler.postDelayed(mSplashHandler , 6000)


    }

    internal var mSplashHandler: Runnable = Runnable {
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
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
    }



    protected fun in_app_layout(): Int {
        return R.layout.activity_splash__screen__acitivity
    }



    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.splash_interstitial_ad_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    this@SplashScreenActivity.interstitialAdSplash = interstitialAd
                    Log.i(
                        "Tag",
                        "onAdLoaded"
                    )

                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(
                        "Tag",
                        loadAdError.message
                    )
                    interstitialAdSplash = null


                }
            })
    }


}