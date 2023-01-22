package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.Constants
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.CreateCVActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.home.fragments.ModernTemplateFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.home.fragments.NewTemplateFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.home.fragments.ProfessionalTemplateFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.home.fragments.latestTemplateFragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel

import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout

class HomeFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var btnCreateProfile: CardView
    lateinit var btnResumeCV: CardView
    lateinit var tinyDB: TinyDB
    lateinit var buttonFragmentLinear: LinearLayout


    private var cvViewModel: CvViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        tinyDB =
            TinyDB(
                requireContext()
            )

        if(tinyDB.getBoolean("FirstCreated"))
        {
            resumeBtnFunc()
        }



        cvViewModel = ViewModelProvider(this).get(CvViewModel::class.java)


        if (!tinyDB.getBoolean("inApp")) {


        }
        viewPager = view.findViewById(R.id.viewPagerTemplates)
        tabLayout = view.findViewById(R.id.tablayoutTemplates)
        btnCreateProfile = view.findViewById(R.id.btnCreateProfile)
        buttonFragmentLinear = view.findViewById(R.id.buttonFragmentLinear)
        btnResumeCV = view.findViewById(R.id.btnResumeCV)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = MyPagerAdapter(childFragmentManager)
        viewPager.offscreenPageLimit = 1


        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {

                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Blue_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {

                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Orange_Theme
                    )
                )

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {

                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Red_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {


                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Yellow_Theme
                    )
                )

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {

                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Green_Theme
                    )
                )

            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {

                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Gray_Theme
                    )
                )
            }
        }


        btnCreateProfile.setOnClickListener {
            tinyDB.putBoolean("Boolean", false)
            dialogCVNameNAV()

            HomeActivity.adapter.notifyDataSetChanged()


        }

        btnResumeCV.setOnClickListener {
            resumeBtnFunc()
        }


        return view
    }


    fun resumeBtnFunc() {
        if (tinyDB.getString("UID").equals("")) {
            dialogCVNameNAV()
        } else {

            val intent = Intent(requireContext(), CreateCVActivity::class.java)
            intent.putExtra("ID", tinyDB.getString("UID"))
            tinyDB.putString("UID", tinyDB.getString("UID"))
            tinyDB.putBoolean("CHECK_ACTIVITY", false)
            tinyDB.putBoolean("VIEW", false)
            tinyDB.putBoolean("Boolean", true)
            tinyDB.putString("CV_SELECTED_TEMPLATE_NAME", "Other")
            tinyDB.putBoolean("RESUME_CV", false)
            tinyDB.putBoolean("IS_SELECTED", false)
            requireActivity().startActivity(intent)
            requireActivity().finish()

        }
    }



    private class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {


        override fun getCount(): Int {
            return 4;
        }

        override fun getItem(pos: Int): Fragment {
            return return when (pos) {
                1 -> NewTemplateFragment()
                2 -> latestTemplateFragment()
                3 -> ProfessionalTemplateFragment()
                else -> ModernTemplateFragment()

            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            var title: String? = null
            if (position == 0) {
                title = "Modern"
            } else if (position == 1) {
                title = "New"
            } else if (position == 2) {
                title = "Latest"
            } else if (position == 3) {
                title = "Professional"
            }
            return title
        }

    }

    private fun dialogCVNameNAV() {
        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.DialogStyle)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_cv_name, null)
        dialogBuilder.setView(dialogView)
        val cvName: TextInputLayout = dialogView.findViewById(R.id.CV_name_input)
        val createBt: Button = dialogView.findViewById(R.id.btnCreateCV)
        val cancelBt: Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)


        createBt.setOnClickListener {
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
                createCV(name)
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


    fun createCV(name: String) {
        val tinyDB =
            TinyDB(
                requireContext()
            )
        val myfinalstring: String = name.replace("'", "''")
        tinyDB.putString("FILE", myfinalstring)
        tinyDB.putString("UID", "" + System.currentTimeMillis())
        Log.d("TAG", "dialogCVName: " + tinyDB.getString("FILE"))


        val model =
            CVModelEntity(
                tinyDB.getString("UID"),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                myfinalstring,
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )



        val rowId = cvViewModel?.insert(model)
        Log.e("Inserted", "Row ID: $rowId")



        tinyDB.putBoolean("PERSONEL_INFO_CHECK", false)
        tinyDB.putBoolean("CHECK_ACTIVITY", true)
        tinyDB.putBoolean("VIEW", false)
        tinyDB.putBoolean("RESUME_CV", false)
        tinyDB.putBoolean("IS_SELECTED", false)
        tinyDB.putBoolean(Constants.skipExp, false)
        tinyDB.putBoolean(Constants.skipQua, false)
        tinyDB.putBoolean(Constants.skipInterest, false)
        tinyDB.putBoolean(Constants.skipRef, false)
        tinyDB.putBoolean(Constants.skipTech, false)
        tinyDB.putBoolean(Constants.skipRef, false)
        tinyDB.putBoolean(Constants.skipAwards, false)
        val intent = Intent(requireContext(), CreateCVActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
        startActivity(intent)
        requireActivity().finish()

    }

}