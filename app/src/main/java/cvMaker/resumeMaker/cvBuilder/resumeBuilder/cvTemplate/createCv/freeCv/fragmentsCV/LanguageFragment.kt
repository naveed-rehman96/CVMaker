package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV

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
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import org.koin.android.ext.android.inject

class LanguageFragment : Fragment() {
    lateinit var language: EditText

    lateinit var btnNext: Button
    lateinit var btnBack: Button
    lateinit var ID: String

    lateinit var tinyDB: TinyDB

    lateinit var btnSkipLanguage: CheckBox

    val cvDatabase by inject<AppDatabase>()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_language, parent, false)

        tinyDB =
            TinyDB(
                requireContext()
            )
        language = v.findViewById(R.id.LanguageEdt) as EditText
        btnNext = v.findViewById(R.id.btn_save_lng) as Button
        btnBack = v.findViewById(R.id.btn_back) as Button
        btnSkipLanguage = v.findViewById(R.id.btnSkipLanguage)

        val textCountObj = v.findViewById(R.id.textCountObj) as TextView
        language.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textCountObj.text = s!!.length.toString() + "/50"
                if (s.length == 50) {
                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                } else {
                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        })
        btnNext.setOnClickListener {
            CreateCVActivity.mViewPager2.setCurrentItem(9, true)
        }
        btnBack.setOnClickListener {
            CreateCVActivity.mViewPager2.setCurrentItem(7, true)
        }

        if (tinyDB.getBoolean("Boolean")) {
            language.setText(cvMainModel.personInfo.language)
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

        btnSkipLanguage.isChecked = tinyDB.getBoolean(Constants.skipLang)

        if (btnSkipLanguage.isChecked) {
            language.visibility = View.GONE
        } else {
            language.visibility = View.VISIBLE
        }

        Log.e("btnSkipExperience", "Qualification Skip: " + tinyDB.getBoolean(Constants.skipLang))

        btnSkipLanguage.setOnClickListener {
            if (tinyDB.getBoolean(Constants.skipLang)) {
                btnSkipLanguage.isChecked = false
                tinyDB.putBoolean(Constants.skipLang, false)
                language.visibility = View.VISIBLE
            } else {
                btnSkipLanguage.isChecked = true
                language.visibility = View.GONE
                tinyDB.putBoolean(Constants.skipLang, true)
            }
        }

        return v
    }

    override fun onPause() {
        super.onPause()

        val languageTxt = language.text.toString()

        if (TextUtils.isEmpty(languageTxt)) {
            language.error = "Required"
        } else {
            AppDatabase.databaseWriteExecutor.execute {
                cvDatabase.cvDao().updateLanguage(languageTxt, tinyDB.getString("UID"))
                cvMainModel.personInfo.language = languageTxt
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

    override fun onDestroy() {
        super.onDestroy()
        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
            tinyDB.putString("language", language.text.toString())
            tinyDB.putString("language1", language.text.toString())
        }
    }
}
