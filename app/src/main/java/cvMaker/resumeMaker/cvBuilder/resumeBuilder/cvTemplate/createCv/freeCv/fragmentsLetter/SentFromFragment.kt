package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.CreateCoverLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB


class SentFromFragment : Fragment() {


    lateinit var btnNext :Button
    lateinit var btnBack :Button
    lateinit var tinydb : TinyDB

    lateinit var nameEdt: EditText
    lateinit var designationEdt: EditText
    lateinit var addressEdt: EditText
    lateinit var phoneedt : EditText
    lateinit var emailEdt: EditText
    lateinit var companyEdt: EditText
    lateinit var subjectEdt: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val v = inflater.inflate(R.layout.fragment_sent_from, parent, false)

        val tinyDB =
            TinyDB(
                requireContext()
            )
        btnNext = v.findViewById(R.id.btn_Next)
        btnBack = v.findViewById(R.id.btn_back)
        tinydb =
            TinyDB(
                requireContext()
            )

        nameEdt = v.findViewById(R.id.cnameEdt)
        subjectEdt = v.findViewById(R.id.cSubjectEdt)
        addressEdt = v.findViewById(R.id.cAddressEdt)
        companyEdt = v.findViewById(R.id.cCompanyEdt)
        phoneedt = v.findViewById(R.id.cPhoneEdt)
        emailEdt = v.findViewById(R.id.cEmailEdt)
        designationEdt = v.findViewById(R.id.cDesignEdt)


        btnBack.setOnClickListener{
        }
        if (!tinydb.getBoolean("NEW_LETTER"))
        {
            nameEdt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderName())
            designationEdt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderDesignation())
            addressEdt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderAddress())
            phoneedt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderPhone())
            emailEdt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderEmail())
            companyEdt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderCompany())
            subjectEdt.setText(CreateCoverLetterActivity.modelMainLetter.getSenderSubject())

        }



        btnNext.setOnClickListener{

            CreateCoverLetterActivity.mViewPager2.setCurrentItem(1,false)

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

        when {
            nameEdt.text.toString() == "" -> {
                nameEdt.error = "Required"
            }
            designationEdt.text.toString() == "" -> {
                designationEdt.error = "Required"
            }
            addressEdt.text.toString() == "" -> {
                addressEdt.error = "Required"
            }
            phoneedt.text.toString() == "" -> {
                phoneedt.error = "Required"
            }
            emailEdt.text.toString() == "" -> {
                emailEdt.error = "Required"
            }
            companyEdt.text.toString() == "" -> {
                companyEdt.error = "Required"
            }
            subjectEdt.text.toString() == "" -> {
                subjectEdt.error = "Required"
            }
            else -> {
                saveSentFromInfo( companyEdt.text.toString(),subjectEdt.text.toString(),nameEdt.text.toString(),
                    emailEdt.text.toString() ,phoneedt.text.toString(),
                    designationEdt.text.toString() , addressEdt.text.toString())
            }
        }


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun saveSentFromInfo(company:String,subject:String, name: String, email: String, phone: String, designation: String, address: String) {
        val db : DataBaseHandler = DataBaseHandler(requireContext())
        db.addSentInfo(tinydb.getString("CL_ID"),company,subject ,name , email , phone , designation , address)

        CreateCoverLetterActivity.modelMainLetter.setSenderCompany(company)
        CreateCoverLetterActivity.modelMainLetter.setSenderSubject(subject)
        CreateCoverLetterActivity.modelMainLetter.setSenderName(name)
        CreateCoverLetterActivity.modelMainLetter.setSenderEmail(email)
        CreateCoverLetterActivity.modelMainLetter.setSenderPhone(phone)
        CreateCoverLetterActivity.modelMainLetter.setSenderDesignation(designation)
        CreateCoverLetterActivity.modelMainLetter.setSenderAddress(address)

    }


}