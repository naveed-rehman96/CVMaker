package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentAdapter.ExperienceAdapterClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ExperienceEntity
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

class ExperienceFragment : Fragment(), ExperienceAdapterClass.onExperienceClickedListener {

    lateinit var cName: EditText
    lateinit var designation: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ExperienceAdapterClass
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var btnNext: Button
    lateinit var textView: TextView
    lateinit var btnExp: Button
    lateinit var btnUpdateExpLayout: Button
    lateinit var btnAddMoreExperience: Button
    lateinit var btn_cancel_exp: Button
    lateinit var ID: String
    lateinit var buttonLayout: RelativeLayout
    lateinit var linearBottomlayout: RelativeLayout
    lateinit var addExpLayout: LinearLayout
    lateinit var addExperienceLayout: LinearLayout
    lateinit var expID: String
    var position: Int = 0
    var check: Boolean = false
    lateinit var tinyDB: TinyDB

    lateinit var btnBack: Button

    lateinit var experienceModelArray: ArrayList<ExperienceEntity>

    lateinit var btnSkipExperience: CheckBox
    lateinit var currentlyWorkingHere: CheckBox
    lateinit var db: AppDatabase

    val cvDatabase by inject<AppDatabase>()

    companion object {

        lateinit var toDateEdt: TextView
        lateinit var fromDateEdt: TextView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        experienceModelArray = ArrayList()

        db = Room.databaseBuilder(
            requireActivity().applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

        tinyDB = TinyDB(requireContext())
        intializeViews(view)
        btnExp.setOnClickListener {
            val modelObj = ExperienceEntity()
            val exp1Txt = cName.text.toString()
            val exp2Txt = designation.text.toString()
            val exp3Txt = toDateEdt.text.toString()
            val exp4Txt = fromDateEdt.text.toString()

            if (TextUtils.isEmpty(exp1Txt)) {
                cName.error = "Required"
            } else if (TextUtils.isEmpty(exp2Txt)) {
                designation.error = "Required"
            } else if (TextUtils.isEmpty(exp3Txt)) {
                toDateEdt.error = "Required"
            } else if (TextUtils.isEmpty(exp4Txt)) {
                fromDateEdt.error = "Required"
            } else {

                recyclerView.visibility = VISIBLE

                modelObj.companyName = exp1Txt
                modelObj.designation = exp2Txt
                modelObj.startDate = (exp4Txt)
                modelObj.endDate = (exp3Txt)
                modelObj.userId = tinyDB.getString("UID")

                experienceModelArray.add(modelObj)
                experienceModelArray.reverse()
                adapter.notifyDataSetChanged()

                if (experienceModelArray.size >= 4) {
                    btnAddMoreExperience.visibility = GONE
                } else {
                    btnAddMoreExperience.visibility = VISIBLE
                }

                linearBottomlayout.visibility = VISIBLE
                addExperienceLayout.visibility = GONE
                recyclerView.visibility = VISIBLE


                cvDatabase.experienceDAO()?.insertExp(modelObj)

                adapter.notifyDataSetChanged()

                val count = experienceModelArray.size
                if (experienceModelArray.size > 3) {
                    btnAddMoreExperience.visibility = GONE
                    addExperienceLayout.visibility = GONE
                } else {
                    btnAddMoreExperience.visibility = VISIBLE
                }

                textView.text = "$count / 4"
                cName.setText("")
                designation.setText("")
                toDateEdt.text = ""
                fromDateEdt.text = ""

            }


        }
        btnUpdateExpLayout.setOnClickListener {
            val exp1Txt = cName.text.toString()
            val exp2Txt = designation.text.toString()
            val exp3Txt = toDateEdt.text.toString()
            val exp4Txt = fromDateEdt.text.toString()

            if (TextUtils.isEmpty(exp1Txt)) {
                cName.error = "Required"
            } else if (TextUtils.isEmpty(exp2Txt)) {
                designation.error = "Required"
            } else if (TextUtils.isEmpty(exp3Txt)) {
                toDateEdt.error = "Required"
            } else if (TextUtils.isEmpty(exp4Txt)) {
                fromDateEdt.error = "Required"
            } else {
                experienceModelArray[position].designation = exp2Txt
                experienceModelArray[position].companyName = exp1Txt
                experienceModelArray[position].startDate = (exp4Txt)
                experienceModelArray[position].endDate = (exp3Txt)
                experienceModelArray[position].expId = expID.toInt()
                experienceModelArray[position].userId = tinyDB.getString("UID")


                if (experienceModelArray.size > 3) {
                    btnAddMoreExperience.visibility = GONE
                    addExperienceLayout.visibility = GONE
                } else {
                    btnAddMoreExperience.visibility = VISIBLE
                }
                cvDatabase.experienceDAO()?.UpdateExp(experienceModelArray)

                val count = experienceModelArray.size
                textView.text = "$count / 4"
                cName.setText("")
                designation.setText("")
                toDateEdt.text = ""
                fromDateEdt.text = ""

                btnUpdateExpLayout.visibility = GONE
                recyclerView.visibility = VISIBLE
                buttonLayout.visibility = GONE
                addExpLayout.visibility = VISIBLE
                adapter.notifyDataSetChanged()

            }
        }
        btnNext.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(3, false)
        }
        btnBack.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(1, false)
        }

        linearLayoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = linearLayoutManager
        adapter = ExperienceAdapterClass(requireContext(), experienceModelArray, this)
         recyclerView.adapter = adapter

        if (tinyDB.getBoolean("Boolean")) {


            GetFiles().execute()
            ID = cvMainModel.personInfo.id
        }


        if(experienceModelArray.isEmpty())
        {
            addExperienceLayout.visibility = VISIBLE
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
                    btn_cancel_exp.background,
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
                    btn_cancel_exp.background,
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
                    btn_cancel_exp.background,
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
                    btn_cancel_exp.background,
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
                    btn_cancel_exp.background,
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
                    btn_cancel_exp.background,
                    Color.parseColor("#6A6A6A")
                )
            }
        }

        btnAddMoreExperience.setOnClickListener {
            btnAddMoreExperience.visibility = GONE
            linearBottomlayout.visibility = GONE
            addExperienceLayout.visibility = VISIBLE
        }


        toDateEdt.setOnClickListener {
            selectDateDialog()
            toDateEdt.error = null
        }
        fromDateEdt.setOnClickListener {
            selectDateDialog1()
            fromDateEdt.error = null
            currentlyWorkingHere.isChecked = false
        }


        btnSkipExperience.isChecked = tinyDB.getBoolean(Constants.skipExp)
        if (btnSkipExperience.isChecked) {
            recyclerView.visibility = GONE
        } else {
            recyclerView.visibility = VISIBLE
        }


        Log.e("btnSkipExperience", "Experience Skip: " + tinyDB.getBoolean(Constants.skipExp))

        btnSkipExperience.setOnClickListener {

            if (tinyDB.getBoolean(Constants.skipExp)) {
                btnSkipExperience.isChecked = false
                tinyDB.putBoolean(Constants.skipExp, false)
                recyclerView.visibility = VISIBLE
            } else {
                btnSkipExperience.isChecked = true
                recyclerView.visibility = GONE
                tinyDB.putBoolean(Constants.skipExp, true)
            }
        }


        currentlyWorkingHere.setOnCheckedChangeListener { _, _ ->
            if (currentlyWorkingHere.isChecked) {
                toDateEdt.error = null
                tinyDB.putBoolean(Constants.currentlyWorking, true)
                toDateEdt.text = "Present"
            } else {
                toDateEdt.error = null
                tinyDB.putBoolean(Constants.currentlyWorking, false)
                toDateEdt.text = ""

            }
        }


        btn_cancel_exp.setOnClickListener {
            addExperienceLayout.visibility = GONE
            btnAddMoreExperience.visibility = VISIBLE
            linearBottomlayout.visibility = VISIBLE
        }


    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_experience, parent, false)


        return view
    }

    override fun onPause() {
        super.onPause()
        cvMainModel.experienceInfo =
            cvDatabase.experienceDAO()?.getAllExperience(tinyDB.getString("UID")) as ArrayList<ExperienceEntity>
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        cvMainModel.experienceInfo =
            cvDatabase.experienceDAO()?.getAllExperience(tinyDB.getString("UID")) as ArrayList<ExperienceEntity>
        val count = experienceModelArray.size
        textView.text = "$count / 4"
    }

    private fun intializeViews(v: View?) {
        cName = v?.findViewById(R.id.companyEDT) as EditText
        designation = v.findViewById(R.id.designationEdt) as EditText
        recyclerView = v.findViewById(R.id.experienceRCV)
        experienceModelArray = ArrayList()

      addExperienceLayout = v.findViewById(R.id.AddExperienceLayout) as LinearLayout

        buttonLayout = v.findViewById(R.id.linearBottomlayout)
        btnBack = v.findViewById(R.id.btn_back)
        addExpLayout = v.findViewById(R.id.addExpLayout) as LinearLayout

        btnNext = v.findViewById(R.id.btn_save_exp) as Button
        btnUpdateExpLayout = v.findViewById(R.id.btn_update_explayout)
        btnExp = v.findViewById(R.id.btn_add_exp)
        textView = v.findViewById(R.id.txtCountExperience)


        btn_cancel_exp = v.findViewById(R.id.btn_cancel_exp)
        toDateEdt = v.findViewById(R.id.toEdt)
        fromDateEdt = v.findViewById(R.id.fromEdt)
        btnSkipExperience = v.findViewById(R.id.btnSkipExperience)
        btnAddMoreExperience = v.findViewById(R.id.btnAddMoreExperience)
        linearBottomlayout = v.findViewById(R.id.linearBottomlayout)
        currentlyWorkingHere = v.findViewById(R.id.currentlyWorkingHere)


    }


    private fun selectDateDialog() {


        val dialogfragment: DialogFragment = DatePickerDialogTheme4()

        dialogfragment.show(requireFragmentManager(), "Theme 4")


    }

    class DatePickerDialogTheme4 : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            return DatePickerDialog(
                requireContext(),
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            toDateEdt.text = "" + view.dayOfMonth + " - " + (month + 1) + " - " + year
        }
    }

    private fun selectDateDialog1() {


        val dialogfragment: DialogFragment = DatePickerDialogTheme41()

        dialogfragment.show(requireFragmentManager(), "Theme 4")


    }

    class DatePickerDialogTheme41 : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            return DatePickerDialog(
                requireContext(),
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            fromDateEdt.text = "" + view.dayOfMonth + " - " + (month + 1) + " - " + year
        }
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

        btnUpdateExpLayout.visibility = VISIBLE
        recyclerView.visibility = GONE
        buttonLayout.visibility = GONE
        addExpLayout.visibility = GONE
        addExperienceLayout.visibility = VISIBLE

        cName.setText(experienceModelArray[position].companyName)
        designation.setText(experienceModelArray[position].designation)
        toDateEdt.text = experienceModelArray[position].endDate
        fromDateEdt.text = experienceModelArray[position].startDate
        expID = experienceModelArray[position].expId.toString()
        Log.e("onEditClickListener", "onEditClickListener: $expID")


        experienceModelArray[position].companyName = ""
        experienceModelArray[position].startDate = ""
        experienceModelArray[position].endDate = ""
        experienceModelArray[position].designation = ""
    }

    override fun onDeleteClickListener(position: Int) {


//        AppDatabase.databaseWriteExecutor.execute {
//            cvDatabase.experienceDAO().deleteExperience(experienceModelArray[position].expId)
//            experienceModelArray.removeAt(position)
//        }

        cvDatabase.experienceDAO()?.deleteExp(experienceModelArray[position])
        experienceModelArray.removeAt(position)



        adapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_LONG).show()

        if (experienceModelArray.size > 3) {
            btnAddMoreExperience.visibility = GONE
            addExperienceLayout.visibility = GONE
        } else {
            btnAddMoreExperience.visibility = VISIBLE
        }
        val count = experienceModelArray.size
        textView.text = "$count / 4"
        textView.visibility = VISIBLE
    }


    fun addAdapter() {
        adapter = ExperienceAdapterClass(requireContext(), experienceModelArray, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        Log.e("ExpSize", "onCreateView: $experienceModelArray")
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetFiles() : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            experienceModelArray = db.experienceDAO()
                .getAllExperience(tinyDB.getString("UID")) as ArrayList<ExperienceEntity>
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            addAdapter()
            if (experienceModelArray.size > 3) {
                btnAddMoreExperience.visibility = GONE
                addExperienceLayout.visibility = GONE
            } else {
                btnAddMoreExperience.visibility = VISIBLE
            }

            val count = experienceModelArray.size
            Log.e("SizeArray", "onViewCreated: $count", )
            textView.text = "$count / 4"

        }
    }



}