package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel

import com.google.android.material.textfield.TextInputLayout

class WelcomeCreateCVActivity : AppCompatActivity() {

    private lateinit var btnSkip : Button
    lateinit var btnCreateProfile : Button
    lateinit var tinyDB: TinyDB
    private var cvViewModel: CvViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_create_cv)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        tinyDB = TinyDB(this)
        cvViewModel = ViewModelProvider(this).get(CvViewModel::class.java)

        if(tinyDB.getBoolean("SkipScreen"))
        {
            startActivity(Intent(this , HomeActivity::class.java))
            finish()
        }



        btnSkip = findViewById(R.id.btnSkip)
        btnCreateProfile = findViewById(R.id.btnCreateProfile)


        btnSkip.setOnClickListener{
            tinyDB.putBoolean("SkipScreen" , true)
            startActivity(Intent(this , HomeActivity::class.java))
            finish()

        }

        btnCreateProfile.setOnClickListener{

            cvMainModel = CvMainModel()
            dialogCVNameNAV()



        }


    }

    private fun dialogCVNameNAV() {
        val dialogBuilder = AlertDialog.Builder(this, R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cv_name, null)
        dialogBuilder.setView(dialogView)
        val cvName: TextInputLayout = dialogView.findViewById(R.id.CV_name_input)
        val createBt: Button = dialogView.findViewById(R.id.btnCreateCV)
        val cancelBt: Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)


        createBt.setOnClickListener {
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
                createCV(name)
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

    fun createCV(name: String) {
        tinyDB.putBoolean("SkipScreen" , true)
        val myfinalstring: String = name.replace("'", "''")
        tinyDB.putString("FILE", myfinalstring)
        tinyDB.putString("UID", "" + System.currentTimeMillis())
        Log.d("TAG", "dialogCVName: " + tinyDB.getString("FILE"))


        val model =
            CVModelEntity(
                tinyDB.getString("UID"),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                myfinalstring,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )


        val rowId = cvViewModel?.insert(model)
        Log.e("Inserted", "Row ID: $rowId")


        tinyDB.putBoolean("FirstCreated" , true)

        tinyDB.putBoolean("PERSONEL_INFO_CHECK", false)
        tinyDB.putBoolean("CHECK_ACTIVITY", true)
        tinyDB.putBoolean("VIEW", false)
        tinyDB.putBoolean("RESUME_CV", false)
        tinyDB.putBoolean("IS_SELECTED", false)
        tinyDB.putBoolean(Constants.skipExp, false)
        tinyDB.putBoolean(Constants.skipQua, false)
        tinyDB.putBoolean(Constants.skipInterest, false)
        tinyDB.putBoolean(Constants.skipRef, false)
        tinyDB.putBoolean(Constants.skipTech, false)
        tinyDB.putBoolean(Constants.skipRef, false)
        tinyDB.putBoolean(Constants.skipAwards, false)
        val intent = Intent(this, CreateCVActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
        startActivity(intent)
        finish()

    }

}