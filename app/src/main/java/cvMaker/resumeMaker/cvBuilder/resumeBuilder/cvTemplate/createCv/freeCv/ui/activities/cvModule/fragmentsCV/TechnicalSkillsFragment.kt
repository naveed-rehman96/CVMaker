package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.fragmentsCV

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentAdapter.TechnicalSkillsAdapterClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.TechnicalSkillModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.SkillsEntity
import org.koin.android.ext.android.inject
import kotlin.collections.ArrayList

class TechnicalSkillsFragment : Fragment(), TechnicalSkillsAdapterClass.onExperienceClickedListener {

    lateinit var cName: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TechnicalSkillsAdapterClass
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var btnNext: Button
    lateinit var textView: TextView
    lateinit var btnExp: Button
    private lateinit var btnUpdateExpLayout: Button
    private lateinit var btnAddNewExperience: Button
    private lateinit var btnCancelExp: Button
    lateinit var ID: String
    lateinit var addTechSkillayout: LinearLayout
    lateinit var linearBottomlayout: RelativeLayout
    lateinit var addExpLayout: LinearLayout
    lateinit var expID: String
    var position: Int = 0
    var check: Boolean = false
    lateinit var tinyDB: TinyDB

    lateinit var btnBack: Button

    lateinit var technicalModelArray: ArrayList<SkillsEntity>

    lateinit var btnSkipExperience: CheckBox
    lateinit var currentlyWorkingHere: CheckBox


    val cvDatabase by inject<AppDatabase>()




    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_technical_skills, parent, false)

        technicalModelArray = ArrayList()


        tinyDB = TinyDB(requireContext())
        intializeViews(v)
        btnExp.setOnClickListener {
            val modelObj =
                SkillsEntity()
            val skill = cName.text.toString()
            if (TextUtils.isEmpty(skill)) {
                cName.error = "Required"
            }  else {

                recyclerView.visibility = View.VISIBLE
                modelObj.skillTitle = skill
                modelObj.id = tinyDB.getString("UID")
                technicalModelArray.add(modelObj)
                technicalModelArray.reverse()
                adapter.notifyDataSetChanged()



                addTechSkillayout.visibility = View.GONE
                linearBottomlayout.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE


                val count = technicalModelArray.size
                textView.text = "$count / 10"
                cName.setText("")

                cvDatabase.skillsDao().insertSkill(modelObj)

                if (technicalModelArray.size > 9) {
                    btnAddNewExperience.visibility = View.GONE
                } else {
                    btnAddNewExperience.visibility = View.VISIBLE
                }

            }


        }
        btnUpdateExpLayout.setOnClickListener {

            val modelObj = TechnicalSkillModelClass()
            val skill = cName.text.toString()

            if (TextUtils.isEmpty(skill)) {
                cName.error = "Required"
            } else {

                technicalModelArray[position].skillTitle = skill

                if (technicalModelArray.size >= 10) {
                    addTechSkillayout.visibility = View.GONE
                } else {
                    addTechSkillayout.visibility = View.GONE
                }

                cvDatabase.skillsDao().Update(technicalModelArray)

                val count = technicalModelArray.size
                textView.text = "$count / 10"
                cName.setText("")

                btnUpdateExpLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                linearBottomlayout.visibility = View.VISIBLE
                addExpLayout.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()

            }
        }
        btnNext.setOnClickListener {

            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(5, false)
        }
        btnBack.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(3, false)
        }

        linearLayoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = linearLayoutManager
        adapter = TechnicalSkillsAdapterClass(requireContext(), technicalModelArray, this)
        recyclerView.adapter = adapter
        if (tinyDB.getBoolean("Boolean")) {

            cvMainModel.skillsEntity = cvDatabase.skillsDao().getAllSkills(tinyDB.getString("UID")) as ArrayList<SkillsEntity>

            technicalModelArray = cvMainModel.skillsEntity!!
            technicalModelArray.reverse()
            adapter = TechnicalSkillsAdapterClass(requireContext(), technicalModelArray, this)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            ID = cvMainModel.personInfo.id
        }

        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateExpLayout.background,
                    Color.parseColor("#6C48EF")
                )
                MyDrawableCompat.setColorFilter(btnExp.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(
                    btnCancelExp.background,
                    Color.parseColor("#6C48EF")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateExpLayout.background,
                    Color.parseColor("#ED851A")
                )
                MyDrawableCompat.setColorFilter(btnExp.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(
                    btnCancelExp.background,
                    Color.parseColor("#ED851A")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateExpLayout.background,
                    Color.parseColor("#950806")
                )
                MyDrawableCompat.setColorFilter(btnExp.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(
                    btnCancelExp.background,
                    Color.parseColor("#950806")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateExpLayout.background,
                    Color.parseColor("#C8BA00")
                )
                MyDrawableCompat.setColorFilter(btnExp.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(
                    btnCancelExp.background,
                    Color.parseColor("#C8BA00")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateExpLayout.background,
                    Color.parseColor("#296E01")
                )
                MyDrawableCompat.setColorFilter(btnExp.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(
                    btnCancelExp.background,
                    Color.parseColor("#296E01")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateExpLayout.background,
                    Color.parseColor("#6A6A6A")
                )
                MyDrawableCompat.setColorFilter(btnExp.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(
                    btnCancelExp.background,
                    Color.parseColor("#6A6A6A")
                )
            }
        }

        btnAddNewExperience.setOnClickListener {
            addTechSkillayout.visibility = View.VISIBLE
            btnAddNewExperience.visibility = View.GONE
            linearBottomlayout.visibility = View.GONE
        }


        val count = technicalModelArray.size
        textView.text = "$count / 10"

        if (technicalModelArray.size >= 10) {
            addTechSkillayout.visibility = View.GONE
            btnAddNewExperience.visibility = View.GONE
        } else {
            addTechSkillayout.visibility = View.VISIBLE
            btnAddNewExperience.visibility = View.VISIBLE
        }
        if (technicalModelArray.isEmpty()) {
            addTechSkillayout.visibility = View.VISIBLE
            btnAddNewExperience.visibility = View.GONE
        }




        btnSkipExperience.isChecked = tinyDB.getBoolean(Constants.skipTech)
        if (btnSkipExperience.isChecked) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
        }


        Log.e("btnSkipExperience", "Experience Skip: "+ tinyDB.getBoolean(Constants.skipTech))

        btnSkipExperience.setOnClickListener {

            if (tinyDB.getBoolean(Constants.skipTech)) {
                btnSkipExperience.isChecked = false
                tinyDB.putBoolean(Constants.skipTech, false)
                recyclerView.visibility = View.VISIBLE
            } else {
                btnSkipExperience.isChecked = true
                recyclerView.visibility = View.GONE
                tinyDB.putBoolean(Constants.skipTech, true)
            }
        }



        btnCancelExp.setOnClickListener {
            addTechSkillayout.visibility = View.GONE
            btnAddNewExperience.visibility = View.VISIBLE
            linearBottomlayout.visibility = View.VISIBLE
        }



        return v
    }
    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause: TechSkills")

        cvMainModel.skillsEntity = cvDatabase.skillsDao().getAllSkills(tinyDB.getString("UID")) as ArrayList<SkillsEntity>

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        cvMainModel.skillsEntity =
            cvDatabase.skillsDao().getAllSkills(tinyDB.getString("UID")) as ArrayList<SkillsEntity>

        val count = technicalModelArray.size
        textView.text = "$count / 10"
    }

    private fun intializeViews(v: View?) {
        cName = v?.findViewById(R.id.companyEDT) as EditText
        recyclerView = v.findViewById(R.id.skillsRCV)
        technicalModelArray = ArrayList()

        addTechSkillayout = v.findViewById(R.id.AddExperienceLayout) as LinearLayout
        btnBack = v.findViewById(R.id.btn_back)
        addExpLayout = v.findViewById(R.id.addExpLayout) as LinearLayout

        btnNext = v.findViewById(R.id.btn_save_exp) as Button
        btnUpdateExpLayout = v.findViewById(R.id.btn_update_explayout)
        btnExp = v.findViewById(R.id.btn_add_exp)
        textView = v.findViewById(R.id.txtCountExperience)


        btnCancelExp = v.findViewById(R.id.btn_cancel_exp)
        btnSkipExperience = v.findViewById(R.id.btnSkipExperience)
        btnAddNewExperience = v.findViewById(R.id.btnAddNewExperience)
        linearBottomlayout = v.findViewById(R.id.linearBottomlayout)


    }


    @SuppressLint("SetTextI18n")
    private fun saveExperience(experienceModelClass: ArrayList<TechnicalSkillModelClass>) {
        val db = activity?.let { DataBaseHandler(it) }
        db?.saveTechnicalSkill(experienceModelClass, tinyDB.getString("UID"))

        val count = experienceModelClass.size
        textView.text = "$count / 10"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "onAttach: ")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "onStart: ")
    }

    override fun onEditClickListener(position: Int) {
        this.position = position

        addTechSkillayout.visibility = View.VISIBLE
        btnUpdateExpLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        addExpLayout.visibility = View.GONE

        cName.setText(technicalModelArray[position].skillTitle)
        expID = technicalModelArray[position].skillId.toString()
        Log.e("onEditClickListener", "onEditClickListener: $expID" )


        technicalModelArray[position].skillTitle= ""
    }

    @SuppressLint("SetTextI18n")
    override fun onDeleteClickListener(position: Int) {
        technicalModelArray[position].skillId


        cvDatabase.skillsDao().deleteSkill(technicalModelArray[position])


        technicalModelArray.removeAt(position)
        adapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_LONG).show()
        if (technicalModelArray.size >= 10) {
            btnAddNewExperience.visibility = View.GONE
        } else {
            btnAddNewExperience.visibility = View.VISIBLE
        }
        if (technicalModelArray.isEmpty()) {
            addTechSkillayout.visibility = View.VISIBLE
            btnAddNewExperience.visibility = View.GONE
        }
        val count = technicalModelArray.size
        textView.text = "$count / 10"
        textView.visibility = View.VISIBLE
    }


}