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



class SentToFragment : Fragment() {


    lateinit var btnNext : Button
    lateinit var btnBack : Button
    lateinit var tinyDB: TinyDB

    lateinit var nameEdt: EditText
    lateinit var designationEdt: EditText
    lateinit var addressEdt: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_sent_to, container, false)


        nameEdt = v.findViewById(R.id.cReceiverNameEdt)
        designationEdt = v.findViewById(R.id.cReceiverDesignEdt)
        addressEdt = v.findViewById(R.id.cReceiverAddressEdt)


        btnNext = v.findViewById(R.id.btn_Next)
        btnBack = v.findViewById(R.id.btn_back)
        tinyDB=
            TinyDB(
                requireContext()
            )

        if (!tinyDB.getBoolean("NEW_LETTER"))
        {
            nameEdt.setText(CreateCoverLetterActivity.modelMainLetter.getReceiverName())
            designationEdt.setText(CreateCoverLetterActivity.modelMainLetter.getReceiverDesignation())
            addressEdt.setText(CreateCoverLetterActivity.modelMainLetter.getReceiverAddress())


        }





        btnNext.setOnClickListener{
            CreateCoverLetterActivity.mViewPager2.setCurrentItem(2,false)
        }

        btnBack.setOnClickListener{
            CreateCoverLetterActivity.mViewPager2.setCurrentItem(0,false)


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





        if (nameEdt.text.toString().equals(""))
        {
            nameEdt.error = "Required"
        }
        else if (designationEdt.text.toString().equals(""))
        {
            designationEdt.error = "Required"
        }
        else if (addressEdt.text.toString().equals(""))
        {
            addressEdt.error = "Required"
        }
        else
        {
            saveSentToInfo(nameEdt.text.toString(), designationEdt.text.toString() ,addressEdt.text.toString())
        }

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun saveSentToInfo(name: String, designation: String, address: String) {
        val db = DataBaseHandler(requireContext())
        db.addSentToInfo(tinyDB.getString("CL_ID") , name , designation , address)

        CreateCoverLetterActivity.modelMainLetter.setReceiverName(name)
        CreateCoverLetterActivity.modelMainLetter.setReceiverAddress(address)
        CreateCoverLetterActivity.modelMainLetter.setReceiverDesignation(designation)



    }


}