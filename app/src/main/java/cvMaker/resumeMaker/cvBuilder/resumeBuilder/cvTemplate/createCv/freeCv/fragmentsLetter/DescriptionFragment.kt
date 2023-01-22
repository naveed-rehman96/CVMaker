package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.CreateCoverLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.LetterTemplateActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB


class DescriptionFragment : Fragment() {

    lateinit var btnCancel : Button
    lateinit var btnSave : Button
    lateinit var btnChoseTemplateNow : Button
    lateinit var description : TextView
    lateinit var tinyDB: TinyDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnSave = view.findViewById(R.id.btn_save)
        description = view.findViewById(R.id.cDescriptionEdt)
        val textCountObj = view.findViewById(R.id.countWords) as TextView
        btnChoseTemplateNow = view.findViewById(R.id.btnChoseTemplateNow)
        tinyDB =
            TinyDB(
                requireContext()
            )
        btnCancel.setOnClickListener{
            description.text = ""
        }

        description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty())
                {
                    btnChoseTemplateNow.visibility = View.VISIBLE
                }
                else
                {
                    btnChoseTemplateNow.visibility = View.GONE
                }
                textCountObj.text = s.length.toString() +"/1000"
                if(s.length == 1000)
                {
                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                }
                else{

                    textCountObj.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                }
            }
        })




        if (!tinyDB.getBoolean("NEW_LETTER"))
        {
            description.text = CreateCoverLetterActivity.modelMainLetter.getDescription()
            btnSave.text = "Update"
        }


        btnChoseTemplateNow.setOnClickListener{
            getDetails()
            val intent = Intent(requireContext(), LetterTemplateActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
            startActivity(intent)
        }


        btnSave.setOnClickListener{

        }
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#6C48EF"))
                MyDrawableCompat.setColorFilter(btnCancel.background, Color.parseColor("#6C48EF"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#ED851A"))
                MyDrawableCompat.setColorFilter(btnCancel.background, Color.parseColor("#ED851A"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#950806"))
                MyDrawableCompat.setColorFilter(btnCancel.background, Color.parseColor("#950806"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#C8BA00"))
                MyDrawableCompat.setColorFilter(btnCancel.background, Color.parseColor("#C8BA00"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#296E01"))
                MyDrawableCompat.setColorFilter(btnCancel.background, Color.parseColor("#296E01"))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                MyDrawableCompat.setColorFilter(btnChoseTemplateNow.background, Color.parseColor("#6A6A6A"))
                MyDrawableCompat.setColorFilter(btnCancel.background, Color.parseColor("#6A6A6A"))
            }
        }



        return view
    }

    override fun onPause() {
        super.onPause()
        if (description.text.toString().equals(""))
        {
            description.error = "Required"
        }
        else
        {
            saveDescription(description.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        btnChoseTemplateNow.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.shake_left_to_right
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun saveDescription(description: String) {
        val db = DataBaseHandler(requireContext())
        db.addLetterDescription(tinyDB.getString("CL_ID"),description)
        CreateCoverLetterActivity.modelMainLetter.setDescription(description)
        btnSave.text = "Update"

    }
    private fun getDetails()
    {
        val datebase = DataBaseHandler(requireContext())
        val cursor = datebase.fetchLetterRecord(tinyDB.getString("CL_ID"))

        if (cursor != null) {
            val id = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_CID)))
            val filename = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_CLNAME)))
            val sName = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_NAME)))
            val sDesignation = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_DESIG)))
            val sAddress = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_ADDRESS)))
            val sPhone = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_PHONE)))
            val sEmail = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_EMAIL)))
            val rName = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_REC_NAME)))
            val rDesignation = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_REC_DESIG)))
            val rAddress = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_REC_ADDRESS)))
            val date = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_DATE)))
            val description = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_DESCRIPTION)))
            val subject = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_SUBJECT)))
            val company = (cursor.getString(cursor.getColumnIndex(DataBaseHandler.COLUMN_SEND_COMPANY)))

            CreateCoverLetterActivity.modelMainLetter.setCid(id)
            CreateCoverLetterActivity.modelMainLetter.setCFileName(filename)
            CreateCoverLetterActivity.modelMainLetter.setSenderName(sName)
            CreateCoverLetterActivity.modelMainLetter.setSenderDesignation(sDesignation)
            CreateCoverLetterActivity.modelMainLetter.setSenderAddress(sAddress)
            CreateCoverLetterActivity.modelMainLetter.setSenderPhone(sPhone)
            CreateCoverLetterActivity.modelMainLetter.setSenderEmail(sEmail)
            CreateCoverLetterActivity.modelMainLetter.setReceiverName(rName)
            CreateCoverLetterActivity.modelMainLetter.setReceiverDesignation(rDesignation)
            CreateCoverLetterActivity.modelMainLetter.setReceiverAddress(rAddress)
            CreateCoverLetterActivity.modelMainLetter.setDate(date)
            CreateCoverLetterActivity.modelMainLetter.setDescription(description)
            CreateCoverLetterActivity.modelMainLetter.setSenderSubject(subject)
            CreateCoverLetterActivity.modelMainLetter.setSenderCompany(company)

            Log.e("Reciever", "onTemplateClick: $rName")
            Log.e("Reciever", "onTemplateClick: $rAddress")
        }
    }



}