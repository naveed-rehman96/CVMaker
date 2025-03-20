package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB

class PermissionActivity : AppCompatActivity() {

    var allPermissionsGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val tinyDB : TinyDB =
            TinyDB(
                this
            )
        if (tinyDB.getBoolean("PermissionGranted"))
        {
            val i = Intent(this@PermissionActivity, WelcomeCreateCVActivity::class.java)
            startActivity(i)
            finish()
        }
        val btnPermissionActivity : Button = findViewById(R.id.btnPermissionActivity)
        btnPermissionActivity.setOnClickListener{
            if (allPermissionsGranted)
            {
                val i = Intent(this@PermissionActivity, WelcomeCreateCVActivity::class.java)
                startActivity(i)
                tinyDB.putBoolean("PermissionGranted" , true)
                finish()
            }
            else
            {
                btnPermissionActivity.text = "Start Now"
                verifyPermissions()
            }

        }
    }

    override fun onResume() {
        super.onResume()

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
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>, grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (permissions.isEmpty()) {
//            return
//        }
//        allPermissionsGranted = true
//
////        if (grantResults.isNotEmpty()) {
////            for (grantResult in grantResults) {
////                if (grantResult != PackageManager.PERMISSION_GRANTED) {
////                    allPermissionsGranted = false
////                    break
////                }
////            }
////        }
//        if (grantResults.isNotEmpty())
//        {
//            for (grantResult in grantResults) {
//                if (grantResult == PackageManager.PERMISSION_GRANTED) {
//                    allPermissionsGranted = true
//                    break
//                }
//                else
//                {
//                    allPermissionsGranted = false
//                }
//            }
//        }
//        if (!allPermissionsGranted) {
//            var somePermissionsForeverDenied = false
//            for (permission in permissions) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission!!)) {
//                    //denied
//                    Log.e("denied", permission)
//
//                } else {
//                    if (ActivityCompat.checkSelfPermission(
//                            this,
//                            permission
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        //allowed
//                        Log.e("allowed", permission)
//                    } else {
//                        //set to never ask again
//                        Log.e("set to never ask again", permission)
//                        somePermissionsForeverDenied = true
//                    }
//                }
//            }
//            if (somePermissionsForeverDenied) {
//
//
//                val alertDialogBuilder = AlertDialog.Builder(this)
//                alertDialogBuilder.setTitle("Permissions Required")
//                    .setMessage(
//                        "You have forcefully denied some of the required permissions " +
//                                "for this action. Please open settings, go to permissions and allow them."
//                    )
//                    .setPositiveButton(
//                        "Settings"
//                    ) { _ , _ ->
//                        val intent = Intent(
//                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                            Uri.fromParts("package", packageName, null)
//                        )
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        startActivity(intent)
//                    }
//                    .setNegativeButton(
//                        "Cancel"
//                    ) { _ , _ -> }
//                    .setCancelable(false)
//                    .create()
//                    .show()
//            }
//        }
//    }
}