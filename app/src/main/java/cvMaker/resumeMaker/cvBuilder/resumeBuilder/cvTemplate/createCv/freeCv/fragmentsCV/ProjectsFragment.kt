package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV

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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentAdapter.ProjectAdapterClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ProjectModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.TechnicalSkillModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ProjectsEntity
import org.koin.android.ext.android.inject
import kotlin.collections.ArrayList

class ProjectsFragment : Fragment(), ProjectAdapterClass.onExperienceClickedListener {

    lateinit var cName: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProjectAdapterClass
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var btnNext: Button
    lateinit var textView: TextView
    lateinit var btnSaveProject: Button
    private lateinit var btnUpdateProject: Button
    private lateinit var btnAddNewProject: Button
    private lateinit var btnCancel: Button
    lateinit var ID: String
    lateinit var AddTechSkillayout: LinearLayout
    lateinit var linearBottomlayout: RelativeLayout
    lateinit var addExpLayout: LinearLayout
    lateinit var expID: String
    var position: Int = 0
    var check: Boolean = false
    lateinit var tinyDB: TinyDB
    val cvDatabase by inject<AppDatabase>()

    lateinit var btnBack: Button

    lateinit var projectModelArray: ArrayList<ProjectsEntity>

    lateinit var btnSkipProject: CheckBox




    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_projects, parent, false)

        projectModelArray = ArrayList()


        tinyDB = TinyDB(requireContext())
        intializeViews(v)
        btnSaveProject.setOnClickListener {
            val modelObj =
                ProjectsEntity()
            val skill = cName.text.toString()
            if (TextUtils.isEmpty(skill)) {
                cName.error = "Required"
            }  else {

                modelObj.projectTitle = skill
                modelObj.userId = tinyDB.getString("UID")


                recyclerView.visibility = View.VISIBLE

                projectModelArray.add(modelObj)
                projectModelArray.reverse()
                adapter.notifyDataSetChanged()

                if (projectModelArray.size > 9) {
                    btnAddNewProject.visibility = View.GONE
                } else {
                    btnAddNewProject.visibility = View.VISIBLE
                }

                cvDatabase.projectsDao().insertProject(modelObj)



                AddTechSkillayout.visibility = View.GONE
                linearBottomlayout.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE


                val count = projectModelArray.size
                textView.text = "$count / 10"
                cName.setText("")

            }


        }
        btnUpdateProject.setOnClickListener {

            val modelObj = TechnicalSkillModelClass()
            val skill = cName.text.toString()

            if (TextUtils.isEmpty(skill)) {
                cName.error = "Required"
            } else {

                projectModelArray[position].projectTitle = skill

                val db: DataBaseHandler? = activity?.let { it1 -> DataBaseHandler(it1) }
                modelObj.techSkills = skill
                db?.updateProjects(expID, skill)
                if (projectModelArray.size >= 10) {
                    AddTechSkillayout.visibility = View.GONE
                } else {
                    AddTechSkillayout.visibility = View.GONE
                }

                val count = projectModelArray.size
                textView.text = "$count / 10"
                cName.setText("")

                cvDatabase.projectsDao().Update(projectModelArray)


                btnUpdateProject.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                linearBottomlayout.visibility = View.VISIBLE
                addExpLayout.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()

            }
        }
        btnNext.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(7, false)
        }
        btnBack.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(5, false)
        }

        linearLayoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = linearLayoutManager
        adapter = ProjectAdapterClass(requireContext(), projectModelArray, this)
        recyclerView.adapter = adapter
        if (tinyDB.getBoolean("Boolean")) {
            cvMainModel.projectsEntity = cvDatabase.projectsDao().getAllProject(tinyDB.getString("UID")) as ArrayList<ProjectsEntity>

            projectModelArray = cvMainModel.projectsEntity!!
            projectModelArray.reverse()
            adapter = ProjectAdapterClass(requireContext(), projectModelArray, this)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            ID = cvMainModel.personInfo.id
        }

        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateProject.background,
                    Color.parseColor("#6C48EF")
                )
                MyDrawableCompat.setColorFilter(btnSaveProject.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(
                    btnCancel.background,
                    Color.parseColor("#6C48EF")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateProject.background,
                    Color.parseColor("#ED851A")
                )
                MyDrawableCompat.setColorFilter(btnSaveProject.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(
                    btnCancel.background,
                    Color.parseColor("#ED851A")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateProject.background,
                    Color.parseColor("#950806")
                )
                MyDrawableCompat.setColorFilter(btnSaveProject.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(
                    btnCancel.background,
                    Color.parseColor("#950806")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateProject.background,
                    Color.parseColor("#C8BA00")
                )
                MyDrawableCompat.setColorFilter(btnSaveProject.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(
                    btnCancel.background,
                    Color.parseColor("#C8BA00")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateProject.background,
                    Color.parseColor("#296E01")
                )
                MyDrawableCompat.setColorFilter(btnSaveProject.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(
                    btnCancel.background,
                    Color.parseColor("#296E01")
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(
                    btnUpdateProject.background,
                    Color.parseColor("#6A6A6A")
                )
                MyDrawableCompat.setColorFilter(btnSaveProject.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(
                    btnCancel.background,
                    Color.parseColor("#6A6A6A")
                )
            }
        }

        btnAddNewProject.setOnClickListener {
            AddTechSkillayout.visibility = View.VISIBLE
            btnAddNewProject.visibility = View.GONE
            linearBottomlayout.visibility = View.GONE
        }


        val count = projectModelArray.size
        textView.text = "$count / 10"

        if (projectModelArray.size >= 10) {
            AddTechSkillayout.visibility = View.GONE
            btnAddNewProject.visibility = View.GONE
        } else {
            AddTechSkillayout.visibility = View.VISIBLE
            btnAddNewProject.visibility = View.VISIBLE
        }
        if (projectModelArray.isEmpty()) {
            AddTechSkillayout.visibility = View.VISIBLE
            btnAddNewProject.visibility = View.GONE
        }




        btnSkipProject.isChecked = tinyDB.getBoolean(Constants.skipProj)
        if (btnSkipProject.isChecked) {
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
        }


        Log.e("btnSkipExperience", "Experience Skip: "+ tinyDB.getBoolean(Constants.skipProj))

        btnSkipProject.setOnClickListener {

            if (tinyDB.getBoolean(Constants.skipProj)) {
                btnSkipProject.isChecked = false
                tinyDB.putBoolean(Constants.skipProj, false)
                recyclerView.visibility = View.VISIBLE
            } else {
                btnSkipProject.isChecked = true
                recyclerView.visibility = View.GONE
                tinyDB.putBoolean(Constants.skipProj, true)
            }
        }



        btnCancel.setOnClickListener {
            AddTechSkillayout.visibility = View.GONE
            btnAddNewProject.visibility = View.VISIBLE
            linearBottomlayout.visibility = View.VISIBLE
        }



        return v
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause: TechSkills")

        cvMainModel.projectsEntity =
            cvDatabase.projectsDao().getAllProject(tinyDB.getString("UID")) as ArrayList<ProjectsEntity>


    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        cvMainModel.projectsEntity =
            cvDatabase.projectsDao().getAllProject(tinyDB.getString("UID")) as ArrayList<ProjectsEntity>
        adapter.notifyDataSetChanged()
        val count = projectModelArray.size
        textView.text = "$count / 10"

    }

    private fun intializeViews(v: View?) {
        cName = v?.findViewById(R.id.companyEDT) as EditText
        recyclerView = v.findViewById(R.id.skillsRCV)
        projectModelArray = ArrayList()

        AddTechSkillayout = v.findViewById(R.id.AddExperienceLayout) as LinearLayout
        btnBack = v.findViewById(R.id.btn_back)
        addExpLayout = v.findViewById(R.id.addExpLayout) as LinearLayout

        btnNext = v.findViewById(R.id.btn_save_exp) as Button
        btnUpdateProject = v.findViewById(R.id.btn_update_explayout)
        btnSaveProject = v.findViewById(R.id.btn_add_exp)
        textView = v.findViewById(R.id.txtCountExperience)


        btnCancel = v.findViewById(R.id.btn_cancel_exp)
        btnSkipProject = v.findViewById(R.id.btnSkipExperience)
        btnAddNewProject = v.findViewById(R.id.btnAddNewExperience)
        linearBottomlayout = v.findViewById(R.id.linearBottomlayout)


    }


    @SuppressLint("SetTextI18n")
    private fun saveExperience(projectModelClass:  ArrayList<ProjectModelClass>) {
        val db = activity?.let { DataBaseHandler(it) }
        db?.saveProject(projectModelClass, tinyDB.getString("UID"))

        val count = projectModelClass.size
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

        AddTechSkillayout.visibility = View.VISIBLE
        btnUpdateProject.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        addExpLayout.visibility = View.GONE


        cName.setText(projectModelArray[position].projectTitle)
        expID = projectModelArray[position].projectID.toString()
        Log.e("ProjectFragment", "onEditClickListener: $expID" )


        projectModelArray[position].projectTitle= ""
    }

    @SuppressLint("SetTextI18n")
    override fun onDeleteClickListener(position: Int) {
        projectModelArray[position].projectID
        Log.e("ProjectFragment", "onDeleteClickListener: ${projectModelArray[position].projectID}")

        cvDatabase.projectsDao().deleteProject(projectModelArray[position])
        projectModelArray.removeAt(position)

        adapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_LONG).show()
        if (projectModelArray.size >= 10) {
            btnAddNewProject.visibility = View.GONE
        } else {
            btnAddNewProject.visibility = View.VISIBLE
        }
        if (projectModelArray.isEmpty()) {
            AddTechSkillayout.visibility = View.VISIBLE
            btnAddNewProject.visibility = View.GONE
        }
        val count = projectModelArray.size
        textView.text = "$count / 10"
        textView.visibility = View.VISIBLE
    }


}