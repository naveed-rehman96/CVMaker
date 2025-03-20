package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.showMessage
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.databinding.FragmentPersonelInfoBinding
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.PathUtil
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.PersonalInfoDataClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.File
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class PersonalInfoFragment : Fragment() {

    private val binding : FragmentPersonelInfoBinding by lazy {
        FragmentPersonelInfoBinding.inflate(layoutInflater)
    }
    val cvDatabase by inject<AppDatabase>()

    lateinit var ID: String
    var imagePath: String = ""
    var gender: String = ""
    var maritalStatus1: String = ""

    lateinit var tinyDB: TinyDB

    private val TAG: String = "AppDebug"

    private val GALLERY_REQUEST_CODE = 1234

    val cvViewModel: CvViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tinyDB =
            TinyDB(
                requireContext()
            )
        binding.ccp.setCountryForPhoneCode(92)

        binding.phoneEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!binding.ccp.isValid) {
                    binding.phoneEdt.error = "Invalid"
                }
            }
        })
        binding.emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isValidEmail(s)) {
                    binding.emailEdt.error = "Invalid"
                }
            }
        })

        binding.profileImageSkipped.setOnCheckedChangeListener { _, _ ->
            if (binding.profileImageSkipped.isChecked) {
                binding.profileImage.visibility = View.GONE
                binding.textImageStatus.visibility = View.GONE
            } else {
                binding.profileImage.visibility = View.VISIBLE
                binding.textImageStatus.visibility = View.VISIBLE
            }
        }

        binding.ccp.registerPhoneNumberTextView(binding.phoneEdt)

        binding.profileImage.setOnClickListener {
            pickFromGallery()
        }
        binding.dateOfBirthEdt.setOnClickListener {
            selectDateDialog()
        }
        binding.btnSavePI.setOnClickListener {
            // validatePersonalInfoDataIndividual()
            CreateCVActivity.mViewPager2.currentItem =
                1
        }

        if (tinyDB.getBoolean("Boolean")) {
            lifecycleScope.launch {
                cvViewModel.getCvById(tinyDB.getString("UID")).collect() {
                    cvMainModel.personInfo = it
                }
            }.invokeOnCompletion {
                binding.cnicEdt.setText(cvMainModel.personInfo.cnic)
                imagePath = cvMainModel.personInfo.imagePath
                if (imagePath == "") {
                    binding.profileImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_add_circle_24
                        )
                    )
                } else {
                    activity?.let {
                        Glide.with(it).load(File(imagePath)).into(
                            binding.profileImage
                        )
                    }
                }
                binding.nationalityEdt.setText(cvMainModel.personInfo.nationality)
                binding.fatherNameEdt.setText(cvMainModel.personInfo.fatherName)
                binding.phoneEdt.setText(cvMainModel.personInfo.phoneNum)
                binding.emailEdt.setText(cvMainModel.personInfo.emailID)
                binding.dateOfBirthEdt.text = cvMainModel.personInfo.dateOfBirth
                binding.nameEdt.setText(cvMainModel.personInfo.fullName)
                binding.addressEdt.setText(cvMainModel.personInfo.address)
                ID = cvMainModel.personInfo.id.toString()
                when (cvMainModel.personInfo.gender) {
                    "Male" -> {
                        binding.genderRadioGroup.check(R.id.rb_Male)
                        gender = "Male"
                    }
                    "Female" -> {
                        binding.genderRadioGroup.check(R.id.rb_female)
                        gender = "Female"
                    }
                    "Other" -> {
                        binding.genderRadioGroup.check(R.id.rb_other)
                        gender = "Other"
                    }
                }

                val martialStatussss = cvMainModel.personInfo.maritalStatus
                Log.e("statusMarital", "CheckAboveIf: $martialStatussss")

                if (cvMainModel.personInfo.maritalStatus == "Single") {
                    binding.maritalGroup.check(R.id.rb_Single)
                    maritalStatus1 = "Single"
                } else if (cvMainModel.personInfo.maritalStatus == "Married") {
                    binding.maritalGroup.check(R.id.rb_Married)
                    maritalStatus1 = "Married"
                }

                if (cvMainModel.personInfo.countryCode != "") {
                    binding.ccp.setCountryForPhoneCode(cvMainModel.personInfo.countryCode.toInt())
                }
            }
        }

        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(binding.btnSavePI.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(binding.btnCancelPI.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                MyDrawableCompat.setColorFilter(binding.btnSavePI.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(binding.btnCancelPI.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                MyDrawableCompat.setColorFilter(binding.btnSavePI.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(binding.btnCancelPI.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(binding.btnSavePI.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(binding.btnCancelPI.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(binding.btnSavePI.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(binding.btnCancelPI.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(binding.btnSavePI.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(binding.btnCancelPI.background, Color.parseColor("#6A6A6A"))
            }
        }

        binding.genderRadioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { _, checkedId ->

                when (checkedId) {
                    R.id.rb_Male -> {
                        gender = "Male"
                    }
                    R.id.rb_female -> {
                        gender = "Female"
                    }
                    R.id.rb_other -> {
                        gender = "Other"
                    }
                }
            }
        )
        binding.maritalGroup.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.rb_Married -> {
                    maritalStatus1 = "Married"
                }
                R.id.rb_Single -> {
                    maritalStatus1 = "Single"
                }
            }
        }

        return binding.root
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun validatePersonalInfoDataIndividual() {
        val name = binding.nameEdt.text.toString()
        val fathername = binding.fatherNameEdt.text.toString()
        val phoneNumber = binding.phoneEdt.text.toString()
        val dateofbirth = binding.dateOfBirthEdt.text.toString()
        val nationality = binding.nationalityEdt.text.toString()
        val cnic = binding.cnicEdt.text.toString()
        val emailid = binding.emailEdt.text.toString()
        val address = binding.addressEdt.text.toString()

        val model = PersonalInfoDataClass()

        if (TextUtils.isEmpty(name)) {
            binding.nameEdt.error = "Required"
        } else {
            model.fullName = name
            cvMainModel.personInfo.fullName = name
        }

        if (!TextUtils.isEmpty(imagePath)) {
            model.imagePath = imagePath
            cvMainModel.personInfo.imagePath = imagePath
        }
        if (!TextUtils.isEmpty(gender)) {
            model.gender = gender
            cvMainModel.personInfo.gender = gender
        }

        if (!TextUtils.isEmpty(maritalStatus1)) {
            model.maritalStatus = maritalStatus1
            cvMainModel.personInfo.maritalStatus = maritalStatus1
        }
        if (TextUtils.isEmpty(fathername)) {
            binding.fatherNameEdt.error = "Required"
        } else {
            model.fatherName = fathername
            cvMainModel.personInfo.fatherName = fathername
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            binding.phoneEdt.error = "Required"
        } else {
            if (binding.ccp.isValid) {
                model.fullPhoneNum = binding.ccp.fullNumber
                model.CountryCode = binding.ccp.selectedCountryCode
                model.phoneNumber = phoneNumber
                cvMainModel.personInfo.fullNumber = binding.ccp.fullNumber
            } else {
                activity?.showMessage(
                    "You have entered and Invalid Number..."
                )
                binding.phoneEdt.error = "Invalid"
            }
        }

        if (TextUtils.isEmpty(dateofbirth)) {
            binding.dateOfBirthEdt.error = "Required"
        } else {
            model.dateOfBirth = dateofbirth
            cvMainModel.personInfo.dateOfBirth = dateofbirth
        }

        if (TextUtils.isEmpty(nationality)) {
            binding.nationalityEdt.error = "Required"
        } else {
            model.nationality = nationality
            cvMainModel.personInfo.nationality = nationality
        }

        if (TextUtils.isEmpty(cnic)) {
            binding.cnicEdt.error = "Required"
        } else {
            model.cnic = cnic
            cvMainModel.personInfo.cnic = cnic
        }

        if (TextUtils.isEmpty(emailid)) {
            binding.emailEdt.error = "Required"
        } else {
            if (!isValidEmail(emailid)) {
                activity?.showMessage(
                    "Email Not Saved .. Please Type Correct Email ..."
                )
                binding.emailEdt.error = "Invalid"
            } else {
                model.emailId = emailid
                cvMainModel.personInfo.emailID = emailid
            }
        }

        if (TextUtils.isEmpty(address)) {
            binding.addressEdt.error = "Required"
        } else {
            model.address = address
            cvMainModel.personInfo.address = address
        }

        AppDatabase.databaseWriteExecutor.execute {
            cvViewModel.updateImagePath(model.imagePath, tinyDB.getString("UID"))
            cvViewModel.updateName(model.fullName, tinyDB.getString("UID"))
            cvViewModel.updateFatherName(model.fatherName, tinyDB.getString("UID"))
            cvViewModel.updateGender(model.gender, tinyDB.getString("UID"))
            cvViewModel.updateMaritalStatus(model.maritalStatus, tinyDB.getString("UID"))
            cvViewModel.updatePhone(model.phoneNumber, tinyDB.getString("UID"))
            cvViewModel.updateEmailID(model.emailId, tinyDB.getString("UID"))
            cvViewModel.updateFullNumber(model.fullPhoneNum, tinyDB.getString("UID"))
            cvViewModel.updateCountryCode(model.CountryCode, tinyDB.getString("UID"))
            cvViewModel.updateDateOfBirth(model.dateOfBirth, tinyDB.getString("UID"))
            cvViewModel.updateCnic(model.cnic, tinyDB.getString("UID"))
            cvViewModel.updateNationality(model.nationality, tinyDB.getString("UID"))
            cvViewModel.updateAddress(model.address, tinyDB.getString("UID"))
        }
    }

    private fun selectDateDialog() {
        val dialogfragment: DialogFragment = DatePickerDialogTheme4(binding)

        dialogfragment.show(childFragmentManager, "Theme 4")
    }

     class DatePickerDialogTheme4(var binding : FragmentPersonelInfoBinding) : DialogFragment(), DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            return DatePickerDialog(
                requireContext(),
                AlertDialog.THEME_HOLO_LIGHT,
                this,
                year,
                month,
                day
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            binding.dateOfBirthEdt.text = "" + view.dayOfMonth + " - " + (month + 1) + " - " + year
            binding.dateOfBirthEdt.error = null
        }
    }

    private fun pickFromGallery() {
        launchImageCrop()
    }

    private fun launchImageCrop() {
        startCameraWithoutUri(true,true)
    }
    private fun startCameraWithoutUri(includeCamera: Boolean, includeGallery: Boolean) {
        customCropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = includeCamera,
                    imageSourceIncludeGallery = includeGallery,
                ),
            ),
        )
    }
    private val customCropImage = registerForActivityResult(CropImageContract()) {
        if (it !is CropImage.CancelledResult) {
            handleCropImageResult(it.uriContent!!)
        }
    }
    private fun handleCropImageResult(uri: Uri) {
        imagePath = PathUtil.getPath(requireContext(), uri)
        setImage(uri)
    }

    private fun setImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.profileImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop()
                        Log.e("newUri", "onActivityResult: $uri")
                        imagePath = PathUtil.getRealPathFromURI(requireContext(), uri)
                        Log.e("IMAGE_URI", "onActivityResult: $uri")
                        binding.textImageStatus.text = "Image Added"
                    }
                } else {
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory.")
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (imagePath == "") {
            binding.textImageStatus.text = "Upload Image"
        } else {
            binding.textImageStatus.text = "Image Added"
            binding.profileImageSkipped.visibility = View.GONE
        }
    }



    override fun onPause() {
        validatePersonalInfoDataIndividual()
        super.onPause()
    }
}
