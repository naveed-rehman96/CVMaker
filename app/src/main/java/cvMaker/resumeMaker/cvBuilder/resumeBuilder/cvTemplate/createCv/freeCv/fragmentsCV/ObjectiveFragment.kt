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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.room.Room
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.databinding.FragmentObjectiveBinding
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.databinding.FragmentPersonelInfoBinding
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import org.koin.android.ext.android.inject


class ObjectiveFragment : Fragment() {
    lateinit var ID: String
    lateinit var tinyDB: TinyDB

    val cvDatabase by inject<AppDatabase>()

    private val binding : FragmentObjectiveBinding by lazy {
        FragmentObjectiveBinding.inflate(layoutInflater)
    }

    val viewModel : CvViewModel by inject()


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        tinyDB =
            TinyDB(
                requireContext()
            )

        binding.objectiveEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textCountObj.text = s!!.length.toString() +"/300"
                if(s.length == 300)
                {
                    binding.textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                }
                else{

                    binding.textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                }
            }
        })


        binding.btnSaveOb.setOnClickListener {
            CreateCVActivity.mViewPager2.setCurrentItem(2, true)
        }
        binding.btnBack.setOnClickListener{
            CreateCVActivity.mViewPager2.setCurrentItem(0, true)
        }


        if (tinyDB.getBoolean("Boolean")) {
            binding.objectiveEdt.setText(cvMainModel.personInfo.objective)
            ID = cvMainModel.personInfo.id
        }





        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(binding.btnSaveOb.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(binding.btnBack.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                MyDrawableCompat.setColorFilter(binding.btnSaveOb.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(binding.btnBack.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                MyDrawableCompat.setColorFilter(binding.btnSaveOb.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(binding.btnBack.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(binding.btnSaveOb.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(binding.btnBack.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(binding.btnSaveOb.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(binding.btnBack.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(binding.btnSaveOb.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(binding.btnBack.background, Color.parseColor("#6A6A6A"))
            }
        }







        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val objectiveTxt = binding.objectiveEdt.text.toString()

        if (TextUtils.isEmpty(objectiveTxt)) {
            binding.objectiveEdt.error = "Required"
        } else {
            viewModel.insertObjective(objectiveTxt,tinyDB.getString("UID"))
            cvMainModel.personInfo.objective = objectiveTxt
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tinyDB.getBoolean("CHECK_ACTIVITY")) {
            tinyDB.putString("Objective", binding.objectiveEdt.text.toString())
            tinyDB.putString("Objective1", binding.objectiveEdt.text.toString())
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