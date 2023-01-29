package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv

import android.app.Application
import android.content.Context
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { }
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        firebaseCrashlytics = FirebaseCrashlytics.getInstance()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MyApplication)
            // Load modules
            modules(appModule)
        }
    }

    companion object {

        var firebaseAnalytics: FirebaseAnalytics? = null
        var firebaseCrashlytics: FirebaseCrashlytics? = null
        internal lateinit var bundle: Bundle
        internal lateinit var bundle1: Bundle

        fun firbaseAnalaytics(context: Context, Item_id: String, Item_name: String) {
            bundle = Bundle()
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Item_id)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Item_name)
            firebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
    }
}
