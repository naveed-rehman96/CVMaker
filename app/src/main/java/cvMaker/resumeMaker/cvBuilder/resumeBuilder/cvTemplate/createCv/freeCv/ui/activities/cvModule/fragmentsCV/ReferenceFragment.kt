package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.fragmentsCV

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.SelectTemplateActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import com.kaopiz.kprogresshud.KProgressHUD
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ExperienceEntity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ProjectsEntity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.QualificationEntity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.SkillsEntity
import org.koin.android.ext.android.inject


class ReferenceFragment : Fragment() {
    lateinit var reference: EditText

    lateinit var ID: String
    lateinit var btnChoseTemplateNow: Button
    lateinit var btnSkipReference: CheckBox
    lateinit var linearReferenceLayout: LinearLayout

    val cvDatabase by inject<AppDatabase>()

    lateinit var tinyDB: TinyDB

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_reference, parent, false)

        tinyDB =
            TinyDB(
                requireContext()
            )


        reference = v.findViewById(R.id.ReferenceEdt) as EditText

        btnSkipReference = v.findViewById(R.id.btnSkipReference)
        linearReferenceLayout = v.findViewById(R.id.linearReferenceLayout)
        btnChoseTemplateNow = v.findViewById(R.id.btnChoseTemplateNow)


        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#6A6A6A"))
            }
        }

        btnChoseTemplateNow.setOnClickListener {
            moveToPreview()
            val intent = Intent(requireContext(), SelectTemplateActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
            startActivity(intent)

        }
        val textCountObj = v.findViewById(R.id.textCountObj) as TextView
        reference.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    btnChoseTemplateNow.visibility = View.VISIBLE
                } else {
                    btnChoseTemplateNow.visibility = View.GONE
                }

                textCountObj.text = s.length.toString() + "/75"
                if (s.length == 75) {
                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                } else {

                    textCountObj.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )
                    )
                }
            }
        })



        if (tinyDB.getBoolean("Boolean")) {
            reference.setText(cvMainModel.personInfo.reference)
            ID = cvMainModel.personInfo.id
        }


        //First check weather user checked skip qualification or not
        btnSkipReference.isChecked = tinyDB.getBoolean(Constants.skipRef)

        if (btnSkipReference.isChecked) {
            linearReferenceLayout.visibility = View.GONE
        } else {
            linearReferenceLayout.visibility = View.VISIBLE
        }


        Log.e("btnSkipExperience", "Qualification Skip: "+ tinyDB.getBoolean(Constants.skipRef))

        btnSkipReference.setOnClickListener {

            if (tinyDB.getBoolean(Constants.skipRef)) {
                btnSkipReference.isChecked = false
                tinyDB.putBoolean(Constants.skipRef, false)
                linearReferenceLayout.visibility = View.VISIBLE
            } else {
                btnSkipReference.isChecked = true
                linearReferenceLayout.visibility = View.GONE
                tinyDB.putBoolean(Constants.skipRef, true)
            }
        }




//        when {
//            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
//                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#0078FF"))
//                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#0078FF"))
//                MyDrawableCompat.setColorFilter(
//                    btnChoseTemplateNow.background,
//                    Color.parseColor("#0078FF")
//                )
//            }
//            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
//
//                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#ED851A"))
//                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#ED851A"))
//                MyDrawableCompat.setColorFilter(
//                    btnChoseTemplateNow.background,
//                    Color.parseColor("#ED851A")
//                )
//            }
//            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
//
//                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#950806"))
//                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#950806"))
//                MyDrawableCompat.setColorFilter(
//                    btnChoseTemplateNow.background,
//                    Color.parseColor("#950806")
//                )
//            }
//            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
//                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#C8BA00"))
//                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#C8BA00"))
//                MyDrawableCompat.setColorFilter(
//                    btnChoseTemplateNow.background,
//                    Color.parseColor("#C8BA00")
//                )
//            }
//            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
//                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#296E01"))
//                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#296E01"))
//                MyDrawableCompat.setColorFilter(
//                    btnChoseTemplateNow.background,
//                    Color.parseColor("#296E01")
//                )
//            }
//            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
//                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#6A6A6A"))
//                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6A6A6A"))
//                MyDrawableCompat.setColorFilter(
//                    btnChoseTemplateNow.background,
//                    Color.parseColor("#6A6A6A")
//                )
//            }
//        }

        return v
    }


    fun moveToPreview() {

        GetUserDetails().execute()


    }
    @SuppressLint("StaticFieldLeak")
    inner class GetUserDetails() : AsyncTask<String, String, String>() {

        val progressBar = KProgressHUD(requireContext())
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

            }, 100)

        }

        override fun doInBackground(vararg params: String?): String {
            //getDetails(tinyDB12.getString("UID"))

            cvMainModel.personInfo = cvDatabase.cvDao().getCVbyId(tinyDB.getString("UID"))
            cvMainModel.experienceInfo = cvDatabase.experienceDAO().getAllExperience(tinyDB.getString("UID")) as ArrayList<ExperienceEntity>
            cvMainModel.qualificationEntity = cvDatabase.qualificationDAO().getAllQualification(tinyDB.getString("UID")) as ArrayList<QualificationEntity>
            cvMainModel.skillsEntity = cvDatabase.skillsDao().getAllSkills(tinyDB.getString("UID")) as ArrayList<SkillsEntity>
            cvMainModel.projectsEntity = cvDatabase.projectsDao().getAllProject(tinyDB.getString("UID")) as ArrayList<ProjectsEntity>


            return ""
        }

    }


    override fun onPause() {
        super.onPause()
        val referenceTxt = reference.text.toString()
        val db = Room.databaseBuilder(requireContext().applicationContext ,
            AppDatabase::class.java , AppDatabase.DATABASE_NAME).build()

        if (TextUtils.isEmpty(referenceTxt)) {
            reference.error = "Required"
        } else {
            AppDatabase.databaseWriteExecutor.execute{
                db.cvDao().updateReference(referenceTxt,tinyDB.getString("UID"))
                cvMainModel.personInfo.reference = referenceTxt
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate: ")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "onAttach: ")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        btnChoseTemplateNow.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shake_left_to_right
            )
        )
//        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
//            reference.setText(tinyDB.getString("Reference"))
//        }
//        if (tinyDB.getBoolean("RESUME_CV"))
//        {
//            reference.setText(tinyDB.getString("Reference1"))
//        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
            tinyDB.putString("Reference", reference.text.toString())
            tinyDB.putString("Reference1", reference.text.toString())
        }

    }


}