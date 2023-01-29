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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.bumptech.glide.Glide
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.PersonalInfoDataClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.PathUtil
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.theartofdev.edmodo.cropper.CropImage
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule.UserObject.cvMainModel
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.android.ext.android.inject
import java.io.File
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class PersonalInfoFragment : Fragment() {

    private val GalleryPick = 1
    lateinit var profileImage: CircleImageView
    private var ImageUri: Uri? = null
    private lateinit var cCPicker: CountryCodePicker

    val cvDatabase by inject<AppDatabase>()


    lateinit var nameEdt: EditText

    companion object {

        lateinit var dateOfBirthEdt123: TextView
    }

    private var cvViewModel: CvViewModel? = null
    lateinit var emailID: EditText
    lateinit var phoneNumEdt: EditText
    lateinit var fatherNameEdt: EditText
    lateinit var cnicEdt: EditText
    lateinit var nationalityEdt: EditText
    lateinit var addressEdt: EditText
    lateinit var genderRadioGroup: RadioGroup
    lateinit var maritalGroup: RadioGroup
    lateinit var profileImageSkipped: CheckBox
    lateinit var btnBack: Button
    lateinit var dateOfBirthEdt: TextView

    lateinit var textImageStatus: TextView

    lateinit var confirm: Button
    lateinit var cancel: Button
    lateinit var db: DataBaseHandler
    lateinit var ID: String
    var imagePath: String = ""
    var gender: String = ""
    var maritalStatus1: String = ""

    lateinit var tinyDB: TinyDB

    private val TAG: String = "AppDebug"

    private val GALLERY_REQUEST_CODE = 1234


    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val v = inflater.inflate(R.layout.fragment_personel_info, parent, false)
        cvViewModel = ViewModelProvider(this).get(CvViewModel::class.java)


        tinyDB =
            TinyDB(
                requireContext()
            )

        initializeViews(v)

        phoneNumEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!cCPicker.isValid) {
                    phoneNumEdt.error = "Invalid"
                }
            }
        })
        emailID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isValidEmail(s)) {
                    emailID.error = "Invalid"
                }
            }
        })

        profileImageSkipped.setOnCheckedChangeListener { _, isChecked ->
            if (profileImageSkipped.isChecked) {
                profileImage.visibility = View.GONE
                textImageStatus.visibility = View.GONE
            } else {
                profileImage.visibility = View.VISIBLE
                textImageStatus.visibility = View.VISIBLE
            }

        }



        cCPicker.registerPhoneNumberTextView(phoneNumEdt);


        profileImage.setOnClickListener {
            pickFromGallery()

        }
        dateOfBirthEdt123.setOnClickListener {
            selectDateDialog()
        }
        confirm.setOnClickListener {
            //ValidatePersonalInfoDataIndividual()
            cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.mViewPager2.currentItem = 1
        }



        if (tinyDB.getBoolean("Boolean")) {

            cvMainModel.personInfo = cvDatabase.cvDao().getCVbyId(tinyDB.getString("UID"))

            cnicEdt.setText(cvMainModel.personInfo.cnic)
            imagePath = cvMainModel.personInfo.imagePath
            if (imagePath == "") {
                profileImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_add_circle_24
                    )
                )
            } else {
                activity?.let {
                    Glide.with(it).load(File(imagePath)).into(
                        profileImage
                    )
                };
            }
            nationalityEdt.setText(cvMainModel.personInfo.nationality)
            fatherNameEdt.setText(cvMainModel.personInfo.fatherName)
            phoneNumEdt.setText(cvMainModel.personInfo.phoneNum)
            emailID.setText(cvMainModel.personInfo.emailID)
            dateOfBirthEdt123.text = cvMainModel.personInfo.dateOfBirth
            nameEdt.setText(cvMainModel.personInfo.fullName)
            addressEdt.setText(cvMainModel.personInfo.address)
            ID = cvMainModel.personInfo.id.toString()
            if (cvMainModel.personInfo.gender.equals("Male")) {
                genderRadioGroup.check(R.id.rb_Male)
                gender = "Male"
            } else if (cvMainModel.personInfo.gender.equals("Female")) {
                genderRadioGroup.check(R.id.rb_female)
                gender = "Female"
            } else if (cvMainModel.personInfo.gender.equals("Other")) {
                genderRadioGroup.check(R.id.rb_other)
                gender = "Other"
            }

            val martialStatussss = cvMainModel.personInfo.maritalStatus
            Log.e("statusMarital", "CheckAboveIf: $martialStatussss")

            if (cvMainModel.personInfo.maritalStatus.equals("Single")) {

                maritalGroup.check(R.id.rb_Single)
                maritalStatus1 = "Single"

            } else if (cvMainModel.personInfo.maritalStatus.equals("Married")) {

                maritalGroup.check(R.id.rb_Married)
                maritalStatus1 = "Married"

            }

            if(!cvMainModel.personInfo.countryCode.equals(""))
            {
                cCPicker.setCountryForPhoneCode(cvMainModel.personInfo.countryCode.toInt())
            }

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


        genderRadioGroup.setOnCheckedChangeListener(
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
            })
        maritalGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { _, checkedId ->

                when (checkedId) {
                    R.id.rb_Married -> {
                        maritalStatus1 = "Married"
                    }
                    R.id.rb_Single -> {
                        maritalStatus1 = "Single"

                    }

                }
            })


        return v
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun initializeViews(view: View?) {

        db = activity?.let { DataBaseHandler(it) }!!
        cnicEdt = view?.findViewById(R.id.cnicEdt) as EditText
        nationalityEdt = view.findViewById(R.id.nationalityEdt) as EditText
        fatherNameEdt = view.findViewById(R.id.fatherNameEdt) as EditText
        phoneNumEdt = view.findViewById(R.id.phoneEdt) as EditText
        emailID = view.findViewById(R.id.emailEdt) as EditText
        dateOfBirthEdt123 = view.findViewById(R.id.dateOfBirthEdt) as TextView
        nameEdt = view.findViewById(R.id.nameEdt) as EditText
        confirm = view.findViewById(R.id.btn_save_PI) as Button
        cancel = view.findViewById(R.id.btn_cancel_PI) as Button
        profileImage = view.findViewById(R.id.profile_image) as CircleImageView
        cCPicker = view.findViewById(R.id.ccp)
        cCPicker.setCountryForPhoneCode(92)
        textImageStatus = view.findViewById(R.id.textImageStatus)
        addressEdt = view.findViewById(R.id.addressEdt)
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup)
        maritalGroup = view.findViewById(R.id.maritalGroup)
        profileImageSkipped = view.findViewById(R.id.profileImageSkipped)


    }


    private fun ValidatePersonalInfoDataIndividual() {

        val name = nameEdt.text.toString()
        val fathername = fatherNameEdt.text.toString()
        val phoneNumber = phoneNumEdt.text.toString()
        val dateofbirth = dateOfBirthEdt123.text.toString()
        val nationality = nationalityEdt.text.toString()
        val cnic = cnicEdt.text.toString()
        val emailid = emailID.text.toString()
        val address = addressEdt.text.toString()

//        val modelPersonalInfo =PersonalInfoDataClass(imagePath , name ,fathername ,gender ,maritalStatus1 ,
//                                                    cCPicker.number , cCPicker.fullNumber , cCPicker.selectedCountryCode
//                                                    ,emailid ,dateofbirth , cnic , nationality , address)
//

        val model = PersonalInfoDataClass()

        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()

        if (TextUtils.isEmpty(name)) {
            nameEdt.error = "Required"
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
            fatherNameEdt.error = "Required"
        } else {
            model.fatherName = fathername
            cvMainModel.personInfo.fatherName = fathername
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumEdt.error = "Required"
        } else {
            if (cCPicker.isValid) {
                model.fullPhoneNum = cCPicker.fullNumber
                model.CountryCode = cCPicker.selectedCountryCode
                model.phoneNumber = phoneNumber
                cvMainModel.personInfo.fullNumber = cCPicker.fullNumber
            } else {
                cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.showMessage("You have entered and Invalid Number...")
                phoneNumEdt.error = "Invalid"
            }
        }

        if (TextUtils.isEmpty(dateofbirth)) {
            dateOfBirthEdt123.error = "Required"
        } else {
            model.dateOfBirth = dateofbirth
            cvMainModel.personInfo.dateOfBirth = dateofbirth
        }

        if (TextUtils.isEmpty(nationality)) {
            nationalityEdt.error = "Required"
        } else {
            model.nationality = nationality
            cvMainModel.personInfo.nationality = nationality
        }

        if (TextUtils.isEmpty(cnic)) {
            cnicEdt.error = "Required"
        } else {
            model.cnic = cnic
            cvMainModel.personInfo.cnic = cnic

        }

        if (TextUtils.isEmpty(emailid)) {
            emailID.error = "Required"
        } else {
            if (!isValidEmail(emailid)) {
                cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity.showMessage("Email Not Saved .. Please Type Correct Email ...")
                emailID.error = "Invalid"
            } else {
                model.emailId = emailid
                cvMainModel.personInfo.emailID = emailid
            }
        }

        if (TextUtils.isEmpty(address)) {
            addressEdt.error = "Required"
        } else {
            model.address = address
            cvMainModel.personInfo.address = address
        }


        AppDatabase.databaseWriteExecutor.execute{

            db.cvDao().updateImagePath(model.imagePath , tinyDB.getString("UID"))
            db.cvDao().updateName(model.fullName , tinyDB.getString("UID"))
            db.cvDao().updateFatherName(model.fatherName , tinyDB.getString("UID"))
            db.cvDao().updateGender(model.gender , tinyDB.getString("UID"))
            db.cvDao().updateMaritalStatus(model.maritalStatus , tinyDB.getString("UID"))
            db.cvDao().updatePhone(model.phoneNumber , tinyDB.getString("UID"))
            db.cvDao().updateEmailID(model.emailId , tinyDB.getString("UID"))
            db.cvDao().updateFullNumber(model.fullPhoneNum , tinyDB.getString("UID"))
            db.cvDao().updateCountryCode(model.CountryCode , tinyDB.getString("UID"))
            db.cvDao().updateDateOfBirth(model.dateOfBirth , tinyDB.getString("UID"))
            db.cvDao().updateCnic(model.cnic , tinyDB.getString("UID"))
            db.cvDao().updateNationality(model.nationality , tinyDB.getString("UID"))
            db.cvDao().updateAddress(model.address , tinyDB.getString("UID"))

        }


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
            dateOfBirthEdt123.text = "" + view.dayOfMonth + " - " + (month + 1) + " - " + year
            dateOfBirthEdt123.error = null
        }
    }

    private fun pickFromGallery() {
        launchImageCrop()
    }

    private fun launchImageCrop() {

        val intent = context?.let {
            CropImage.activity()
                .setAspectRatio(1, 1)
                .getIntent(it)
        }
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)


    }

    private fun setImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(profileImage)
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
                        textImageStatus.text = "Image Added"
                    }
                } else {
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory.")
                }
            }


            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    setImage(result.uri)
                    Log.e("IMAGE_URI", "imageUri: " + result.uri.toString())
                    imagePath = PathUtil.getPath(requireContext(), result.uri)
                    Log.e("IMAGE_URI", "imagePath: $imagePath")
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e(TAG, "Crop error: ${result.getError()}")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (imagePath == "") {
            textImageStatus.text = "Upload Image"
        } else {
            textImageStatus.text = "Image Added"
            profileImageSkipped.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

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

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "onStop");
    }

    override fun onPause() {
        ValidatePersonalInfoDataIndividual()
        super.onPause()


    }


}