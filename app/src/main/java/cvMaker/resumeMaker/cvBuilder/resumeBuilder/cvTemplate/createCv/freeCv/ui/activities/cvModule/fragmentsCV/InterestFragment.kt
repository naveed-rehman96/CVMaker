package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.fragmentsCV

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import org.koin.android.ext.android.inject

class InterestFragment : Fragment() {
    lateinit var intrest: EditText

    lateinit var btnNext: Button
    lateinit var btnBack: Button
    lateinit var ID: String
    lateinit var btnSkipInterest: CheckBox

    lateinit var tinyDB: TinyDB
    
    val cvDatabase by inject<AppDatabase>()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_intrest, parent, false)

        tinyDB =
            TinyDB(
                requireContext()
            )
        intrest = v.findViewById(R.id.intrestEdt) as EditText
        btnSkipInterest = v.findViewById(R.id.btnSkipInterest)
        btnNext = v.findViewById(R.id.btn_save_int) as Button
        btnBack = v.findViewById(R.id.btn_back) as Button


        val textCountObj = v.findViewById(R.id.textCountObj) as TextView
        intrest.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textCountObj.text = s!!.length.toString() +"/75"
                if(s.length == 75)
                {
                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                }
                else{

                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                }
            }
        })


        btnNext.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(8, false)
        }
        btnBack.setOnClickListener{
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(6,false)
        }


        if ( tinyDB.getBoolean("Boolean")) {
            intrest.setText(cvMainModel.personInfo.interest)
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
        //First check weather user checked skip qualification or not
        btnSkipInterest.isChecked = tinyDB.getBoolean(Constants.skipInterest)

        if (btnSkipInterest.isChecked) {
            intrest.visibility = View.GONE
        } else {
            intrest.visibility = View.VISIBLE
        }


        Log.e("btnSkipExperience", "Qualification Skip: "+ tinyDB.getBoolean(Constants.skipInterest))

        btnSkipInterest.setOnClickListener {

            if (tinyDB.getBoolean(Constants.skipInterest)) {
                btnSkipInterest.isChecked = false
                tinyDB.putBoolean(Constants.skipInterest, false)
                intrest.visibility = View.VISIBLE
            } else {
                btnSkipInterest.isChecked = true
                intrest.visibility = View.GONE
                tinyDB.putBoolean(Constants.skipInterest, true)
            }
        }
        return v
    }


    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause: Interest", )


     
        val interestTxt = intrest.text.toString()

        if (TextUtils.isEmpty(interestTxt)) {
            intrest.error = "Required"
        } else {

            AppDatabase.databaseWriteExecutor.execute{
                cvDatabase.cvDao().updateInterest(interestTxt,tinyDB.getString("UID"))
                cvMainModel.personInfo.interest = interestTxt

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
    }
    override fun onDestroy() {
        super.onDestroy()
        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
            tinyDB.putString("Interest", intrest.text.toString())
            tinyDB.putString("Interest1", intrest.text.toString())
        }
    }

}