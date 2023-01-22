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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.MyApplication
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentAdapter.QualificationAdapterClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass.QualificationModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.QualificationEntity
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

class QualificationFragment : Fragment() , QualificationAdapterClass.onQualificationClickedListener {

    lateinit var schoolName: EditText
    lateinit var degreeTitle: EditText
    lateinit var score: EditText
    companion object {
        lateinit var endDate1: TextView
    }
    lateinit var textViewCount: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: QualificationAdapterClass
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var confirm: Button
    lateinit var btnSkipQualififcation : CheckBox
    lateinit var btn_add_exp: Button
    lateinit var btnUpdateExpLayout: Button
    lateinit var btnAddNewQualification: Button
    lateinit var btn_cancel_exp: Button
    lateinit var ID: String
    lateinit var addQualificationLayout: LinearLayout
    lateinit var addExpLayout: LinearLayout
    lateinit var expID: String
    val cvDatabase by inject<AppDatabase>()

    lateinit var tinyDB: TinyDB
    var position: Int = 0
    var check: Boolean = false

    lateinit var btnNext : Button
    lateinit var btnBack : Button
    lateinit var linearBottom :RelativeLayout
    lateinit var qualificationModelArray: ArrayList<QualificationEntity>

    lateinit var db : AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_education, parent, false)

        intializeViews(v)
        db = Room.databaseBuilder(requireActivity().applicationContext , AppDatabase::class.java , AppDatabase.DATABASE_NAME).build()

        tinyDB =
            TinyDB(
                requireContext()
            )
        textViewCount.visibility = VISIBLE

        btn_add_exp.setOnClickListener {
            val modelObj = QualificationEntity()
            val exp1Txt = schoolName.text.toString()
            val exp2Txt = degreeTitle.text.toString()
            val exp3Txt = score.text.toString()
            val exp4Txt = endDate1.text.toString()

            if (TextUtils.isEmpty(exp1Txt)) {
                schoolName.error = "Required"
            } else if (TextUtils.isEmpty(exp2Txt)) {
                degreeTitle.error = "Required"
            } else if (TextUtils.isEmpty(exp3Txt)) {
                score.error = "Required"
            } else if (TextUtils.isEmpty(exp4Txt)) {
                endDate1.error = "Required"
            } else {



                recyclerView.visibility = VISIBLE
                linearBottom.visibility = VISIBLE
                addQualificationLayout.visibility = GONE
                modelObj.schoolName = exp1Txt
                modelObj.subject = (exp2Txt)
                modelObj.marks = (exp3Txt)
                modelObj.endDate = (exp4Txt)
                modelObj.userID = tinyDB.getString("UID")

                cvDatabase.qualificationDAO().insertQua(modelObj)

                schoolName.setText("")
                degreeTitle.setText("")
                score.setText("")
                endDate1.setText("")
                qualificationModelArray.add(modelObj)

                val count = qualificationModelArray.size
                textViewCount.text = "$count / 4"

                Log.e("SizeArray", "onCreateView: Add Button ${qualificationModelArray.size} ", )
                if (qualificationModelArray.size > 3)
                {
                    btnAddNewQualification.visibility = GONE
                }
                else
                {
                    btnAddNewQualification.visibility = VISIBLE
                }
                adapter = QualificationAdapterClass(requireContext(), qualificationModelArray,this)
                recyclerView.adapter = adapter
                linearBottom.visibility = VISIBLE
                btnUpdateExpLayout.visibility = GONE
                recyclerView.visibility = VISIBLE
                addExpLayout.visibility = VISIBLE
                addQualificationLayout.visibility = GONE
                adapter.notifyDataSetChanged()
            }

        }
        btnUpdateExpLayout.setOnClickListener {
            val exp1Txt = schoolName.text.toString()
            val exp2Txt = degreeTitle.text.toString()
            val exp3Txt = score.text.toString()
            val exp4Txt = endDate1.text.toString()

            if (TextUtils.isEmpty(exp1Txt)) {
                schoolName.error = "Required"
            } else if (TextUtils.isEmpty(exp2Txt)) {
                degreeTitle.error = "Required"
            } else if (TextUtils.isEmpty(exp3Txt)) {
                score.error = "Required"
            } else if (TextUtils.isEmpty(exp4Txt)) {
                endDate1.error = "Required"
            } else {
                qualificationModelArray[position].subject = (exp2Txt)
                qualificationModelArray[position].schoolName = (exp1Txt)
                qualificationModelArray[position].marks = (exp3Txt)
                qualificationModelArray[position].endDate = exp4Txt
                qualificationModelArray[position].quaID = expID.toInt()
                qualificationModelArray[position].userID = tinyDB.getString("UID")



                schoolName.setText("")
                degreeTitle.setText("")
                score.setText("")
                endDate1.text = ""

                if (qualificationModelArray.size > 3)
                {
                    btnAddNewQualification.visibility = GONE
                }
                else
                {
                    btnAddNewQualification.visibility = VISIBLE
                }


                cvDatabase.qualificationDAO().UpdateQualification(qualificationModelArray)
                linearBottom.visibility = VISIBLE
                btnUpdateExpLayout.visibility = GONE
                recyclerView.visibility = VISIBLE
                addExpLayout.visibility = VISIBLE
                addQualificationLayout.visibility = GONE
                adapter.notifyDataSetChanged()

            }
        }
        btnNext.setOnClickListener {
            CreateCVActivity.mViewPager2.setCurrentItem(4, false)
        }
        btnBack.setOnClickListener{
            CreateCVActivity.mViewPager2.setCurrentItem(2,false)
        }

        linearLayoutManager = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = linearLayoutManager
        adapter = QualificationAdapterClass(requireContext(), qualificationModelArray,this)
        recyclerView.adapter = adapter


        if ( tinyDB.getBoolean("Boolean")) {

            GetQualification().execute()
            ID = cvMainModel.personInfo.id.toString()

        }

        if(qualificationModelArray.isEmpty())
        {
            addQualificationLayout.visibility = VISIBLE
        }



        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnUpdateExpLayout.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btn_add_exp.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btn_cancel_exp.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnUpdateExpLayout.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btn_add_exp.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btn_cancel_exp.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnUpdateExpLayout.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btn_add_exp.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btn_cancel_exp.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnUpdateExpLayout.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btn_add_exp.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btn_cancel_exp.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnUpdateExpLayout.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btn_add_exp.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btn_cancel_exp.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnUpdateExpLayout.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btn_add_exp.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btn_cancel_exp.background, Color.parseColor("#6A6A6A"))
            }
        }


        btnAddNewQualification.setOnClickListener{
            linearBottom.visibility = GONE
            btnAddNewQualification.visibility = GONE
            addQualificationLayout.visibility = VISIBLE
        }

        endDate1.setOnClickListener{
            selectDateDialog1()
            endDate1.error = null
        }



        //First check weather user checked skip qualification or not
        btnSkipQualififcation.isChecked = tinyDB.getBoolean(Constants.skipQua)

        if (btnSkipQualififcation.isChecked) {
            recyclerView.visibility = GONE
        } else {
            recyclerView.visibility = VISIBLE
        }


        Log.e("btnSkipExperience", "Qualification Skip: "+ tinyDB.getBoolean(Constants.skipQua))

        btnSkipQualififcation.setOnClickListener {

            if (tinyDB.getBoolean(Constants.skipQua)) {
                btnSkipQualififcation.isChecked = false
                tinyDB.putBoolean(Constants.skipQua, false)
                recyclerView.visibility = VISIBLE
            } else {
                btnSkipQualififcation.isChecked = true
                recyclerView.visibility = GONE
                tinyDB.putBoolean(Constants.skipQua, true)
            }
        }

        btn_cancel_exp.setOnClickListener{
            btnAddNewQualification.visibility = VISIBLE
            linearBottom.visibility = VISIBLE
            addQualificationLayout.visibility = GONE
        }


        return v
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
            endDate1.text =""+ view.dayOfMonth + " - " + (month + 1) + " - " + year
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause: Qualification" )

        qualificationModelArray = cvDatabase.qualificationDAO().getAllQualification(tinyDB.getString("UID")) as ArrayList<QualificationEntity>
        cvMainModel.qualificationEntity = qualificationModelArray
    }


    private fun intializeViews(v: View?) {
        score = v?.findViewById(R.id.scoreEdt) as EditText
        schoolName = v.findViewById(R.id.schoolNameEDT) as EditText
        endDate1 = v.findViewById(R.id.completedOnEdt) as TextView
        degreeTitle = v.findViewById(R.id.degreeNameEdt) as EditText
        recyclerView = v.findViewById(R.id.qualificationRCV)
        qualificationModelArray = ArrayList()

        addQualificationLayout = v.findViewById(R.id.AddQualificationLayout) as LinearLayout
        addExpLayout = v.findViewById(R.id.addExpLayout) as LinearLayout

        btnUpdateExpLayout = v.findViewById(R.id.btn_update_explayout)
        btn_add_exp = v.findViewById(R.id.btn_add_exp)
        textViewCount = v.findViewById(R.id.txtCountQualification)
        btnSkipQualififcation = v.findViewById(R.id.btnSkipExperience)

        btnAddNewQualification = v.findViewById(R.id.btnAddNewQualification)
        btn_cancel_exp = v.findViewById(R.id.btn_cancel_exp)
        linearBottom = v.findViewById(R.id.linearBottom)
        btnNext = v.findViewById(R.id.btn_next_exp)
        btnBack = v.findViewById(R.id.btn_back)

    }

    private fun saveQualification(experienceModelClass: ArrayList<QualificationModelClass>) {

        //CreateCVActivity.cvModelmain.setQualification(qualificationModelArray)
        val count = qualificationModelArray.size
        textViewCount.text = "$count / 4"


        CreateCVActivity.mViewPager2.currentItem

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

    override fun onEditClickListener(position: Int) {
        this.position = position
        val db: DataBaseHandler? = activity?.let { DataBaseHandler(it) }
        btnUpdateExpLayout.visibility = VISIBLE
        recyclerView.visibility = GONE
        addExpLayout.visibility = GONE
        addQualificationLayout.visibility = VISIBLE

        schoolName.setText(qualificationModelArray[position].schoolName)
        degreeTitle.setText(qualificationModelArray[position].subject)
        score.setText(qualificationModelArray[position].marks)
        endDate1.text = qualificationModelArray[position].endDate


        expID = qualificationModelArray[position].quaID.toString()
        Log.e("onEditClickListener", "onEditClickListener: $expID" )


        qualificationModelArray[position].schoolName = "";
        qualificationModelArray[position].endDate = ""
        qualificationModelArray[position].subject = ("")
        qualificationModelArray[position].marks = ""
    }

    override fun onResume() {
        super.onResume()
        qualificationModelArray = cvDatabase.qualificationDAO().getAllQualification(tinyDB.getString("UID")) as ArrayList<QualificationEntity>
        cvMainModel.qualificationEntity = qualificationModelArray
        adapter.notifyDataSetChanged()
        val count = qualificationModelArray.size
        textViewCount.text = "$count / 4"
    }

    override fun onDeleteClickListener(position: Int) {

        Toast.makeText(requireContext() , "Deleted Successfully", Toast.LENGTH_LONG ).show()

        cvDatabase.qualificationDAO().deleteQua(qualificationModelArray[position])
        qualificationModelArray.removeAt(position)


        if (qualificationModelArray.size >= 4) {
            btnAddNewQualification.visibility = View.GONE
            addQualificationLayout.visibility = View.GONE
        } else {
            btnAddNewQualification.visibility = View.VISIBLE
        }
        recyclerView.adapter = QualificationAdapterClass(requireContext() , qualificationModelArray , this)
        adapter.notifyDataSetChanged()
        val count = qualificationModelArray.size
        textViewCount.text = "$count / 4"
        textViewCount.visibility = View.VISIBLE
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetQualification() : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            qualificationModelArray.clear()
        }

        override fun doInBackground(vararg params: String?): String {
            qualificationModelArray = db.qualificationDAO().getAllQualification(tinyDB.getString("UID")) as ArrayList<QualificationEntity>
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            addAdapter()
            if (qualificationModelArray.size > 3) {
                btnAddNewQualification.visibility = GONE
                addQualificationLayout.visibility = GONE
            } else {
                btnAddNewQualification.visibility = VISIBLE
            }


            val count = qualificationModelArray.size
            Log.e("SizeArray", "onViewCreated: $count", )
            textViewCount.text = "$count / 4"
        }
    }

    fun addAdapter()
    {
        adapter = QualificationAdapterClass(requireContext(), qualificationModelArray , this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}