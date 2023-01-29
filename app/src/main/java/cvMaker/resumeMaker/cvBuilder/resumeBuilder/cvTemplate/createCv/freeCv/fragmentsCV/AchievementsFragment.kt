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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import org.koin.android.ext.android.inject

class AchievementsFragment : Fragment() {

    private lateinit var achievements: EditText

    lateinit var confirm: Button
    lateinit var cancel: Button
    lateinit var ID: String
    lateinit var tinyDB: TinyDB
    lateinit var textCount: TextView
    lateinit var btnSkipAchievements: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_achievements, parent, false)

        achievements = v.findViewById(R.id.awardsEdt)
        confirm = v.findViewById(R.id.btn_next_ach)
        cancel = v.findViewById(R.id.btn_back)
        btnSkipAchievements = v.findViewById(R.id.btnSkipAchievements)

        textCount = v.findViewById(R.id.textCount)
        tinyDB =
            TinyDB(
                requireContext()
            )

        achievements.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textCount.text = s!!.length.toString() + "/250"
                if (s.length == 250) {
                    textCount.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                } else {
                    textCount.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                }
            }
        })

        confirm.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(6, false)
        }

        cancel.setOnClickListener {
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.setCurrentItem(4, false)
        }

        if (tinyDB.getBoolean("Boolean")) {
            achievements.setText(cvMainModel.personInfo.awards)
            ID = cvMainModel.personInfo.id.toString()
        }

        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(confirm.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(cancel.background, Color.parseColor("#6A6A6A"))
            }
        }

        btnSkipAchievements.isChecked = tinyDB.getBoolean(Constants.skipAwards)

        if (btnSkipAchievements.isChecked) {
            achievements.visibility = View.GONE
        } else {
            achievements.visibility = View.VISIBLE
        }

        Log.e("btnSkipExperience", "Qualification Skip: " + tinyDB.getBoolean(Constants.skipAwards))

        btnSkipAchievements.setOnClickListener {
            if (tinyDB.getBoolean(Constants.skipAwards)) {
                btnSkipAchievements.isChecked = false
                tinyDB.putBoolean(Constants.skipAwards, false)
                achievements.visibility = View.VISIBLE
            } else {
                btnSkipAchievements.isChecked = true
                achievements.visibility = View.GONE
                tinyDB.putBoolean(Constants.skipAwards, true)
            }
        }

        return v
    }

    val cvDatabase by inject<AppDatabase>()

    override fun onPause() {
        val awardsTxt = achievements.text.toString()

        Log.e("TAG", "onPause: Achievements")
        if (TextUtils.isEmpty(awardsTxt)) {
            achievements.error = "Required"
        } else {
            AppDatabase.databaseWriteExecutor.execute {
                cvDatabase.cvDao().updateAchievements(awardsTxt, tinyDB.getString("UID"))
                cvMainModel.personInfo.awards
            }
        }
        super.onPause()
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            achievements.focusable = View.FOCUSABLE
            achievements.isCursorVisible = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG-Stop", "onDestroy: ")

        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
            tinyDB.putString("Achievement", achievements.text.toString())
            tinyDB.putString("Achievement1", achievements.text.toString())
        }
    }
}
