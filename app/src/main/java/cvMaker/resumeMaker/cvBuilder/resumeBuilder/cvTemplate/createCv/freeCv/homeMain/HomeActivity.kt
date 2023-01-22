package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.billingclient.api.*
import com.bumptech.glide.Glide
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SavedNavProfileAdapterClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.inAppPurchase.InAppBaseClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.ModelMain
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.kaopiz.kprogresshud.KProgressHUD
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.*
import org.koin.android.ext.android.inject
import java.io.File


class HomeActivity : InAppBaseClass(), SavedNavProfileAdapterClass.SavedProfileClick,
    PurchasesUpdatedListener {


    private lateinit var btnMenu: ImageView
    private lateinit var navProfileImage1: ImageView
    private lateinit var selectedProfName: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnSettings: LottieAnimationView
    lateinit var toolbarBottomNav: RelativeLayout
    private lateinit var nav_header_main: LinearLayout

    private lateinit var modelMainArray: ArrayList<ModelMain>
    private lateinit var arrayListCVs: ArrayList<CVModelEntity>


    private val updateRequestCode = 999 // set globally
    lateinit var reviewManager: ReviewManager
    private var appUpdateManager: AppUpdateManager? = null

    val cvDatabase : AppDatabase by inject<AppDatabase>()


    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var adapter: SavedNavProfileAdapterClass

        @SuppressLint("StaticFieldLeak")
        lateinit var adContainerView: FrameLayout
        lateinit var remove_ads: LottieAnimationView
        fun hideAd() {
            adContainerView.visibility = View.GONE
            remove_ads.visibility = View.GONE
        }

    }

    lateinit var recyclerViewNavProfiles: RecyclerView
    lateinit var db: DataBaseHandler
    lateinit var tinyDB12: TinyDB
    private fun checkTheme() {
        when {
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                super.setTheme(R.style.AppTheme)
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                super.setTheme(R.style.AppThemeOrange)

            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_red) -> {
                super.setTheme(R.style.AppThemeRed)
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_yellow) -> {

                super.setTheme(R.style.AppThemeYellow)

            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_green) -> {
                super.setTheme(R.style.AppThemeGreen)

            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                super.setTheme(R.style.AppThemeGray)
            }
        }

    }

    private fun checkAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager!!.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { result ->
            if (result!!.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager!!.startUpdateFlowForResult(
                        result,
                        AppUpdateType.IMMEDIATE,
                        this@HomeActivity,
                        updateRequestCode
                    )
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        tinyDB12 = TinyDB(this)

        if (!tinyDB12.getBoolean("FirstCreated")) {
            cvMainModel = CvMainModel()
        }


        checkTheme()
        setContentView(R.layout.activity_home_2)
        checkAppUpdate()

        reviewManager = ReviewManagerFactory.create(this);
        showRateApp()


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        btnSettings = findViewById(R.id.settingsLottie)
        remove_ads = findViewById(R.id.remove_ads)
        nav_header_main = findViewById(R.id.nav_header_main)
        toolbarBottomNav = findViewById(R.id.toolbarBottomNav)
        navProfileImage1 = findViewById(R.id.navProfileImage)
        selectedProfName = findViewById(R.id.selectedProfName)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navView, navController)

        verifyPermissions()

        arrayListCVs = ArrayList()
        modelMainArray = ArrayList()
        GetCvProfiles().execute()


        if (isPremium) {
            remove_ads.visibility = View.GONE
        }


        remove_ads.setOnClickListener {

            purchase(this, getString(R.string.APP_IN_PURCHASE))

        }

        if (!tinyDB12.getString("UID").equals("")) {
            GetUserDetails().execute()
        } else {
            selectedProfName.text = "No Profile Created Yet"
            Glide.with(this)
                .load(ContextCompat.getDrawable(this, R.drawable.ic_profile_icon))
                .into(navProfileImage1)
        }





        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }


        drawerLayout = findViewById(R.id.drawer_layout)
        btnMenu = findViewById(R.id.btnNavMenu)

        recyclerViewNavProfiles = findViewById(R.id.profileNavRecyclerView)

        btnMenu.setOnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(Gravity.START)
            } else {
                drawerLayout.closeDrawer(Gravity.END)
            }
        }
        when {
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                blueTheme()
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                orangeTheme()

            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_red) -> {
                redTheme()
            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_yellow) -> {

                yellowTheme()

            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_green) -> {
                greenTheme()

            }
            tinyDB12.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                grayTheme()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        appUpdateManager!!.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an in-app update is already running, resume the update.
                try {
                    appUpdateManager!!.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this@HomeActivity,
                        updateRequestCode
                    )
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == updateRequestCode) {
            if (resultCode != RESULT_OK) {
                val parentLayout = findViewById<View>(android.R.id.content)
                val snackbar = Snackbar
                    .make(parentLayout, "Installation Failed!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }

    private fun verifyPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )
    }

    var allPermissionsGranted: Boolean = true

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions.isEmpty()) {
            return
        }
        allPermissionsGranted = true

        if (grantResults.isNotEmpty()) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    tinyDB12.putBoolean("PermissionGranted", allPermissionsGranted)
                    break
                }
            }
        }
        if (!allPermissionsGranted) {
            var somePermissionsForeverDenied = false
            for (permission in permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission!!)) {
                    //denied
                    Log.e("denied", permission)
                    val intent = Intent(this, PermissionActivity::class.java)
                    startActivity(intent)
                } else {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //allowed
                        Log.e("allowed", permission)
                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission)
                        somePermissionsForeverDenied = true
                    }
                }
            }
            if (somePermissionsForeverDenied) {
                val intent = Intent(this, PermissionActivity::class.java)
                startActivity(intent)

            }
        } else {

        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetCvProfiles() : AsyncTask<String, String, String>() {

        val progressBar = KProgressHUD(this@HomeActivity)

        override fun onPreExecute() {
            super.onPreExecute()

            progressBar.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            progressBar.setLabel("Loading Profile")
            progressBar.setDetailsLabel("Please Wait")
            progressBar.setCancellable(false)
            progressBar.setAnimationSpeed(2)
            progressBar.setDimAmount(0.5f)

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Log.e("Size", "onPostExecute: " + modelMainArray.size)

            adapter = SavedNavProfileAdapterClass(
                this@HomeActivity,
                modelMainArray,
                this@HomeActivity
            )
            recyclerViewNavProfiles.adapter = adapter

            progressBar.dismiss()

        }

        override fun doInBackground(vararg params: String?): String {

            arrayListCVs = cvDatabase.cvDao()?.allRecords as ArrayList<CVModelEntity>
            modelMainArray.clear()
            for (model in arrayListCVs) {
                val obj = ModelMain(
                    model.fileName,
                    model.fullName,
                    model.imagePath,
                    model.emailID,
                    model.id
                )
                modelMainArray.add(obj)
            }
            return ""
        }

    }


    @SuppressLint("StaticFieldLeak")
    inner class GetUserDetails() : AsyncTask<String, String, String>() {

        val progressBar = KProgressHUD(this@HomeActivity)
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            progressBar.setLabel("Loading Profile")
            progressBar.setCancellable(false)
            progressBar.setAnimationSpeed(2)
            progressBar.setDimAmount(0.5f)

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Handler(Looper.myLooper()!!).postDelayed({
                progressBar.dismiss()

                Log.e("ImageUri", "onPostExecute: ${cvMainModel.personInfo.imagePath}")
                if (!cvMainModel.personInfo.imagePath.equals("")) {
                    Glide.with(this@HomeActivity).load(File(cvMainModel.personInfo.imagePath))
                        .into(navProfileImage1)
                } else {
                    navProfileImage1.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@HomeActivity,
                            R.drawable.ic_profile_icon
                        )
                    )
                }
                selectedProfName.text = cvMainModel.personInfo.fullName

            }, 100)

        }

        override fun doInBackground(vararg params: String?): String {
            //getDetails(tinyDB12.getString("UID"))

            cvMainModel.personInfo =
                cvDatabase.cvDao()?.getCVbyId(tinyDB12.getString("UID"))!!
            cvMainModel.experienceInfo = cvDatabase.experienceDAO()
                ?.getAllExperience(tinyDB12.getString("UID")) as ArrayList<ExperienceEntity>
            cvMainModel.qualificationEntity = cvDatabase.qualificationDAO()
                ?.getAllQualification(tinyDB12.getString("UID")) as ArrayList<QualificationEntity>
            cvMainModel.skillsEntity = cvDatabase.skillsDao()
                ?.getAllSkills(tinyDB12.getString("UID")) as ArrayList<SkillsEntity>
            cvMainModel.projectsEntity = cvDatabase.projectsDao()
                ?.getAllProject(tinyDB12.getString("UID")) as ArrayList<ProjectsEntity>


            return ""
        }

    }


    override fun onViewCVClick(position: Int) {
        tinyDB12.putString("UID", modelMainArray[position].id)
        tinyDB12.putBoolean(Constants.skipExp, false)
        tinyDB12.putBoolean(Constants.skipQua, false)
        tinyDB12.putBoolean(Constants.skipInterest, false)
        tinyDB12.putBoolean(Constants.skipRef, false)
        tinyDB12.putBoolean(Constants.skipTech, false)
        tinyDB12.putBoolean(Constants.skipRef, false)
        tinyDB12.putBoolean(Constants.skipAwards, false)
        GetUserDetails().execute()


        selectedProfName.text = cvMainModel.personInfo.fullName
    }

    override fun onCVLongClickListener(position: Int) {

        val db = DataBaseHandler(this)
        val sourceString =
            "Are you sure you want delete it <b><i>" + modelMainArray[position].fileName + "</i></b> " + " CV ?"
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        dialogBuilder.setMessage(Html.fromHtml(sourceString))
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, _ ->


                val ids = modelMainArray[position].id
                cvDatabase.cvDao()?.deleteById(modelMainArray[position].id)
                cvDatabase.skillsDao()?.deleteAllSkills(ids)
                cvDatabase.projectsDao()?.deleteAllProject(ids)
                cvDatabase.qualificationDAO()?.deleteAllQualification(ids)
                cvDatabase.experienceDAO()?.deleteAllExperience(ids)


                modelMainArray.removeAt(position)
                adapter.notifyDataSetChanged()
                if (tinyDB12.getString("UID") == ids) {
                    tinyDB12.putString("UID", "")
                    navProfileImage1.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_profile_icon
                        )
                    )
                    selectedProfName.text = "No Profile Selected"
                }
                GetCvProfiles().execute()

                dialog.dismiss()

            }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val alert = dialogBuilder.create()
        alert.setIcon(android.R.drawable.ic_menu_delete)
        alert.show()


    }

    private fun grayTheme() {

        // MyDrawableCompat.setColorFilter(view.background, Color.parseColor("#6A6A6A"))
        toolbarBottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.Gray_Theme);
        nav_header_main.setBackgroundColor(ContextCompat.getColor(this, R.color.Gray_Theme))
    }

    private fun greenTheme() {

        // MyDrawableCompat.setColorFilter(btnFeedBack.background, Color.parseColor("#296E01"))
        toolbarBottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))
        nav_header_main.setBackgroundColor(ContextCompat.getColor(this, R.color.Green_Theme))

        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.Green_Theme);
    }

    private fun yellowTheme() {

        // MyDrawableCompat.setColorFilter(view.background, Color.parseColor("#C8BA00"))
        toolbarBottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
        nav_header_main.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow_Theme))
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.Yellow_Theme);


    }

    private fun orangeTheme() {

        // MyDrawableCompat.setColorFilter(view.background, Color.parseColor("#ED851A"))
        toolbarBottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
        nav_header_main.setBackgroundColor(ContextCompat.getColor(this, R.color.Orange_Theme))
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.Orange_Theme);


    }

    private fun redTheme() {

        //MyDrawableCompat.setColorFilter(view.background, Color.parseColor("#950806"))
        toolbarBottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
        nav_header_main.setBackgroundColor(ContextCompat.getColor(this, R.color.Red_Theme))
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.Red_Theme);

    }

    private fun blueTheme() {

        // MyDrawableCompat.setColorFilter(view.background, Color.parseColor("#0078FF"))
        toolbarBottomNav.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
        nav_header_main.setBackgroundColor(ContextCompat.getColor(this, R.color.Blue_Theme))
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.Blue_Theme);
    }


    override fun onBackPressed() {
        val dialogBuilder1 = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_exit, null)

        dialogBuilder1.setView(dialogView)

        val cancel: Button = dialogView.findViewById(R.id.btnCancelExitDialog)
        val Ok: Button = dialogView.findViewById(R.id.btnOkExitDialog)


        val alertDialog = dialogBuilder1.create()
        alertDialog.setCancelable(true)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        cancel.setOnClickListener {
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

        Ok.setOnClickListener {
            alertDialog.dismiss()
            finishAffinity()
            finish()
        }
        alertDialog.show()


    }

    fun showRateApp() {
        val request: Task<ReviewInfo?> = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo?> ->
            if (task.isSuccessful) {
                // We can get the ReviewInfo object
                val reviewInfo = task.result
                val flow: Task<Void?> =
                    reviewManager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { task1: Task<Void?>? -> }
            }
        }
    }


    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        if (p0.responseCode == BillingClient.BillingResponseCode.OK) {
            adContainerView.visibility = View.GONE
        }
    }


}