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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewBlueCreamCreamActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewDarkRedWhiteActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewlightGrayWhiteActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.previewCvTemplateActivities.PreviewlightWhiteboxActivity

class latestTemplateFragment : Fragment(), TemplateAdapter.OnTemplateSelect{


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
        val object4 = CvTemplateModelClass()
        object4.setName(getString(R.string.str_darkbluecream))
        object4.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_blue_dark_cream))
        templateList.add(object4)

        val object9 = CvTemplateModelClass()
        object9.setName(getString(R.string.str_whitebox))
        object9.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_whitebox))
        templateList.add(object9)

        val object7 = CvTemplateModelClass()
        object7.setName(getString(R.string.str_darkredwhite))
        object7.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_dark_redwhite))
        templateList.add(object7)

        val object8 = CvTemplateModelClass()
        object8.setName(getString(R.string.darklightwhite))
        object8.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_lightgraywhite))
        templateList.add(object8)







    }
    override fun onTemplateClick(position: Int) {

        if (tinyDB.getString("UID").equals(""))
        {
            Toast.makeText(requireContext() , "No Profile Created Yet." , Toast.LENGTH_LONG).show()
        }
        else {

            when {
                templateList[position].getName().equals("Dark Blue Cream") -> {
                    val intent = Intent(requireContext(), PreviewBlueCreamCreamActivity::class.java)
                    startActivity(intent)
                }

                templateList[position].getName().equals("Dark Red White") -> {
                    val intent = Intent(requireContext() , PreviewDarkRedWhiteActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Light Gray White") -> {
                    val intent = Intent(requireContext(), PreviewlightGrayWhiteActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("White Box") -> {
                    val intent = Intent(requireContext() , PreviewlightWhiteboxActivity::class.java)
                    startActivity(intent)

                }

            }
        }


    }




}