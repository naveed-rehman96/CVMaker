package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.CreateCoverLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.MyDrawableCompat
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import java.util.*

class SentDateFragment : Fragment() {

    lateinit var btnNext : Button
    lateinit var btnBack : Button
    lateinit var tinyDB: TinyDB

    companion object
    {

        lateinit var dateTxt : TextView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sent_date, container, false)
        btnNext = view.findViewById(R.id.btn_Next)
        btnBack = view.findViewById(R.id.btn_back)
        dateTxt = view.findViewById(R.id.cSentDateEdt)
        tinyDB =
            TinyDB(
                requireContext()
            )
        dateTxt.setOnClickListener{
            selectDateDialog1()
        }

        btnBack.setOnClickListener{
            CreateCoverLetterActivity.mViewPager2.setCurrentItem(1,false)
        }
        btnNext.setOnClickListener{
            CreateCoverLetterActivity.mViewPager2.setCurrentItem(3,false)
        }
        if (!tinyDB.getBoolean("NEW_LETTER"))
        {
            dateTxt.text = CreateCoverLetterActivity.modelMainLetter.getDate()
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
        return view
    }
    private fun selectDateDialog1() {


        val dialogfragment: DialogFragment = DatePickerDialogTheme41()

        dialogfragment.show(requireFragmentManager(), "Theme 4")


    }
    class DatePickerDialogTheme41 : DialogFragment(), DatePickerDialog.OnDateSetListener {
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
            dateTxt.text =""+ view.dayOfMonth + " - " + (month + 1) + " - " + year
        }

    }
    override fun onPause() {
        super.onPause()
        if (dateTxt.text.toString() == "")
        {
            dateTxt.error = "Tap to Select Date"
        }
        else
        {
            saveSentDate(dateTxt.text.toString())
        }
    }


    private fun saveSentDate(date: String) {
        val db = DataBaseHandler(requireContext())
        db.addLetterDate(tinyDB.getString("CL_ID") , date)
        CreateCoverLetterActivity.modelMainLetter.setDate(date)
    }


}