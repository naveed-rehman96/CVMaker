package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.coverletter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.CoverLetterAdapterClassFrag
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SelectTemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.CreateCoverLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelCoverLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvTemplateModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewBlueLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewBrownLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewGreenLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetterActivities.PreviewWhiteLetterActivity
import com.google.android.material.textfield.TextInputLayout
import com.kaopiz.kprogresshud.KProgressHUD

class CoverLetterFragment : Fragment(), CoverLetterAdapterClassFrag.CoverLetterClickListener ,SelectTemplateAdapter.OnTemplateClick  {


    lateinit var btnEditCoverLetter: CardView
    lateinit var btnCreateCoverLetter: CardView
    lateinit var recyclerviewCoverLetterProfile: RecyclerView
    lateinit var recyclerviewCoverLetterTemplates: RecyclerView
    lateinit var tinyDB: TinyDB
    lateinit var modelLetter: ArrayList<ModelCoverLetter>
    lateinit var dataBaseHandler: DataBaseHandler
    lateinit var adapter: CoverLetterAdapterClassFrag
    lateinit var objectAsync : GetLetterProfiles

    lateinit var buttonFragmentLinear : LinearLayout
    lateinit var templateList: ArrayList<CvTemplateModelClass>
    lateinit var adapterTemplates: SelectTemplateAdapter
    companion object
    {
        lateinit var modelObjectCLFrag : ModelCoverLetter
        lateinit var mViewPager2: ViewPager2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cover_letters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tinyDB = TinyDB(requireContext())
        objectAsync = GetLetterProfiles()
        modelLetter  = ArrayList()
        templateList = ArrayList()
        btnCreateCoverLetter = view.findViewById(R.id.btnCreateCoverLetter)
        btnEditCoverLetter = view.findViewById(R.id.btnEditCoverLetter)
        recyclerviewCoverLetterProfile = view.findViewById(R.id.recyclerviewCoverLetterProfile)
        recyclerviewCoverLetterTemplates = view.findViewById(R.id.recyclerviewCoverLetterTemplates)
        dataBaseHandler = DataBaseHandler(requireContext())
        btnCreateCoverLetter.setOnClickListener {
            dialogCOVERLETTERName()

        }
        btnEditCoverLetter.setOnClickListener {

            if (!tinyDB.getString("CL_ID").equals("")) {
                val intent = Intent(requireContext(), CreateCoverLetterActivity::class.java)
                intent.putExtra("CID", tinyDB.getString("CL_ID"))
                tinyDB.putString("CL_ID", tinyDB.getString("CL_ID"))
                tinyDB.putBoolean("NEW_LETTER", false)
                this.tinyDB.putBoolean("VIEWLetter", false)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Please Select or Create Cover Letter First ", Toast.LENGTH_LONG)
                    .show()
            }
        }
        buttonFragmentLinear = view.findViewById(R.id.btnTops)
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {

                buttonFragmentLinear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Blue_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                buttonFragmentLinear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Orange_Theme))

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                buttonFragmentLinear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Red_Theme))
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {


                buttonFragmentLinear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Yellow_Theme))

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {

                buttonFragmentLinear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Green_Theme))

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {

                buttonFragmentLinear.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Gray_Theme))
            }
        }


        if (isAdded)
        {
            GetLetterProfiles().execute()
            addDataToArray()
            adapterTemplates = SelectTemplateAdapter(requireContext(), templateList, this)
            recyclerviewCoverLetterTemplates.adapter = adapterTemplates
        }

    }

    override fun onResume() {
        super.onResume()
        if (objectAsync.status == AsyncTask.Status.FINISHED)
        {
            objectAsync.execute()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {
        val object1 = CvTemplateModelClass()
        object1.setName("Blue Bar")
        object1.setTemplate(ContextCompat.getDrawable(requireContext() , R.drawable.bluebar))
        templateList.add(object1)

        val object2 = CvTemplateModelClass()
        object2.setName("Green Bar")
        object2.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.greenbar))
        templateList.add(object2)

        val object3 = CvTemplateModelClass()
        object3.setName("White BG")
        object3.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.white))
        templateList.add(object3)

        val object4 = CvTemplateModelClass()
        object4.setName("Brown Bar")
        object4.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.brown))
        templateList.add(object4)

    }

    override fun onPause() {
        super.onPause()
        if (objectAsync.status == AsyncTask.Status.FINISHED)
        {
            objectAsync.execute()
        }
    }

    inner class GetLetterProfiles() : AsyncTask<String, String, String>() {

        val progressBar = KProgressHUD(requireContext())
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            progressBar.setLabel("Loading Cover Letters")
            progressBar.setCancellable(false)
            progressBar.setAnimationSpeed(2)
            progressBar.setDimAmount(0.5f)
            progressBar.show()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Handler(Looper.myLooper()!!).postDelayed({
                progressBar.dismiss()
                adapter = CoverLetterAdapterClassFrag(requireContext(), modelLetter, this@CoverLetterFragment)
                recyclerviewCoverLetterProfile.adapter = adapter

            },1000)


        }


        override fun doInBackground(vararg params: String?): String {
            modelLetter.clear()
            modelLetter = dataBaseHandler.fetchCoverLetterData()

            if(!tinyDB.getString("CL_ID").equals(""))
            {
                getDetailsCoverLetter(tinyDB.getString("CL_ID"))
            }
            return ""
        }

    }


    fun dialogCOVERLETTERName() {

        //MyApplication.firbaseAnalaytics(requireContext(), "1", "onTemplateClick_CreateLetter")
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_coverletter_name, null)
        dialogBuilder.setView(dialogView)
        val cvName: TextInputLayout = dialogView.findViewById(R.id.CV_name_input)
        val createBt: Button = dialogView.findViewById(R.id.btnCreateCV)
        val cancelBt: Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
                val myfinalstring: String = name.replace("'", "''")
                tinyDB.putString("LETTER", myfinalstring)
                tinyDB.putString("CL_ID", "" + System.currentTimeMillis())
                tinyDB.putBoolean("NEW_LETTER", true)
                tinyDB.putBoolean("VIEWLetter", false)
                val db = DataBaseHandler(requireContext())
                db.addCoverLetterRecord(tinyDB.getString("LETTER"), tinyDB.getString("CL_ID"))
                val intent = Intent(requireContext(), CreateCoverLetterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
                startActivity(intent)
                alertDialog.dismiss()
            } else {
                cvName.editText?.error = "Required"
            }
        }
        cancelBt.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onViewCVClick(position: Int) {

        tinyDB.putString("CL_ID",modelLetter[position].cid)

        getUserDetails().execute()
        adapter.notifyDataSetChanged()
    }

    override fun onLongViewCLClick(position: Int) {
        val db = DataBaseHandler(requireContext())

        val dialogBuilder = AlertDialog.Builder(requireContext() , R.style.DialogStyle)
        dialogBuilder.setMessage("Are you sure you want delete "+ modelLetter[position].getCFileName()+"?")
            // if the dialog is cancelable
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id ->
                if(db.deleteLetterRecord(modelLetter[position].cid))
                {
                    tinyDB.putString("CL_ID","")
                    modelLetter.removeAt(position)
                    adapter.notifyDataSetChanged()

                }
                dialog.dismiss()

            }).setNegativeButton("Cancel", DialogInterface.OnClickListener{
                    dialog, id ->
                dialog.dismiss()
            })
        val alert = dialogBuilder.create()
        alert.setIcon(android.R.drawable.ic_menu_delete)
        alert.show()
    }


    @SuppressLint("StaticFieldLeak")
    inner class getUserDetails() : AsyncTask<String, String, String>() {

        val progressBar = KProgressHUD(requireContext())
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            progressBar.setLabel("Loading Details")
            progressBar.setCancellable(false)
            progressBar.setAnimationSpeed(2)
            progressBar.setDimAmount(0.5f)
            progressBar.show()

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Handler(Looper.myLooper()!!).postDelayed({
                progressBar.dismiss()
            }, 1000)

        }

        override fun doInBackground(vararg params: String?): String {
            getDetailsCoverLetter(tinyDB.getString("CL_ID"))
            return ""
        }

    }




    fun getDetailsCoverLetter(id:String)
    {
        val datebase = DataBaseHandler(requireContext())
        val cursor = datebase.fetchLetterRecord(id)

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

            modelObjectCLFrag = ModelCoverLetter()
            modelObjectCLFrag.setCid(id)
            modelObjectCLFrag.setCFileName(filename)
            modelObjectCLFrag.setSenderName(sName)
            modelObjectCLFrag.setSenderDesignation(sDesignation)
            modelObjectCLFrag.setSenderAddress(sAddress)
            modelObjectCLFrag.setSenderPhone(sPhone)
            modelObjectCLFrag.setSenderEmail(sEmail)
            modelObjectCLFrag.setReceiverName(rName)
            modelObjectCLFrag.setReceiverDesignation(rDesignation)
            modelObjectCLFrag.setReceiverAddress(rAddress)
            modelObjectCLFrag.setDate(date)
            modelObjectCLFrag.setDescription(description)
            modelObjectCLFrag.setSenderSubject(subject)
            modelObjectCLFrag.setSenderCompany(company)


            Log.e("Reciever", "onTemplateClick: $rName")
            Log.e("Reciever", "onTemplateClick: $rAddress")
        }


    }


    override fun onTemplateClick(position: Int) {

        if (!tinyDB.getString("CL_ID").equals("")) {

            when {
                templateList[position].getName().equals("Blue Bar") -> {
                    val intent = Intent(requireContext(), PreviewBlueLetterActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Green Bar") -> {
                    val intent = Intent(requireContext(), PreviewGreenLetterActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("White BG") -> {
                    val intent = Intent(requireContext(), PreviewWhiteLetterActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Brown Bar") -> {
                    val intent = Intent(requireContext(), PreviewBrownLetterActivity::class.java)
                    startActivity(intent)
                }
            }
        }else
        {
            Toast.makeText(requireContext() , "Please Create Cover letter First" ,Toast.LENGTH_LONG).show()
        }

    }
}