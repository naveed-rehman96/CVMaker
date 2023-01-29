package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.home.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.TemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvTemplateModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCvTemplateActivities.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewDarkLightBrownActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewGrayPinkActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewOtherActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewSeaBlueActivity


class ModernTemplateFragment : Fragment() , TemplateAdapter.OnTemplateSelect{

    lateinit var recyclerView: RecyclerView
    lateinit var templateList: ArrayList<CvTemplateModelClass>
    lateinit var adapter: TemplateAdapter
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var tinyDB: TinyDB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tinyDB =
            TinyDB(
                context
            )
        templateList = ArrayList()
        addDataToArray()

        recyclerView = view.findViewById(R.id.templateRcv)
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
        adapter = TemplateAdapter(requireContext(), templateList, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_latest_template, container, false)
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {


        val object10 = CvTemplateModelClass()
        object10.setName(getString(R.string.str_seaColor))
        object10.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.template_sea_color))
        templateList.add(object10)


        val object11 = CvTemplateModelClass()
        object11.setName(getString(R.string.str_darkLight_brown))
        object11.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.template_dark_light_brown))
        templateList.add(object11)


        val object12 = CvTemplateModelClass()
        object12.setName(getString(R.string.str_gray_pink))
        object12.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.template_gray_pink))
        templateList.add(object12)



        val object13 = CvTemplateModelClass()
        object13.setName(getString(R.string.str_other))
        object13.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.template_other))
        templateList.add(object13)









    }
    override fun onTemplateClick(position: Int) {

        if (tinyDB.getString("UID").equals(""))
        {
            Toast.makeText(requireContext() , "No Profile Created Yet." , Toast.LENGTH_LONG).show()
        }
        else {

            when {
                templateList[position].getName().equals("Sea Color") -> {
                    val intent = Intent(requireContext(), PreviewSeaBlueActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Dark Light Brown") -> {
                    val intent = Intent(requireContext(), PreviewDarkLightBrownActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Gray Pink") -> {
                    val intent = Intent(requireContext(), PreviewGrayPinkActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Other") -> {
                    val intent = Intent(requireContext(), PreviewOtherActivity::class.java)
                    startActivity(intent)
                }


            }
        }


    }


}