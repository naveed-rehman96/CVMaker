package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.fragmentsCV

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import org.koin.android.ext.android.inject


class ObjectiveFragment : Fragment() {

    lateinit var objective: EditText

    lateinit var btnNext: Button
    lateinit var btnBack: Button
    lateinit var textCountObj: TextView
    lateinit var ID: String
    lateinit var tinyDB: TinyDB

    val cvDatabase by inject<AppDatabase>()


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_objective, parent, false)

        tinyDB =
            TinyDB(
                requireContext()
            )
        objective = v.findViewById(R.id.objectiveEdt) as EditText
        btnNext = v.findViewById(R.id.btn_save_ob) as Button
        btnBack = v.findViewById(R.id.btn_back) as Button


        val textCountObj = v.findViewById(R.id.textCountObj) as TextView
        objective.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textCountObj.text = s!!.length.toString() +"/300"
                if(s.length == 300)
                {
                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                }
                else{

                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                }
            }
        })


        btnNext.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(2, true)
        }
        btnBack.setOnClickListener{
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(0, true)
        }


        if (tinyDB.getBoolean("Boolean")) {
            objective.setText(cvMainModel.personInfo.objective)
            ID = cvMainModel.personInfo.id.toString()
        }





        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnNext.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnBack.background, Color.parseColor("#6A6A6A"))
            }
        }







        return v
    }

    override fun onPause() {
        super.onPause()
        val objectiveTxt = objective.text.toString()

        if (TextUtils.isEmpty(objectiveTxt)) {
            objective.error = "Required"
        } else {
            AppDatabase.databaseWriteExecutor.execute{
                cvDatabase.cvDao().updateObjective(objectiveTxt,tinyDB.getString("UID"))
                cvMainModel.personInfo.objective = objectiveTxt
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
            tinyDB.putString("Objective", objective.text.toString())
            tinyDB.putString("Objective1", objective.text.toString())
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


}