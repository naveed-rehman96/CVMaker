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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewDark_light_GrayActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewDarkerLighterGrayActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewGreenishActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewYellowGrayActivity


class NewTemplateFragment : Fragment() , TemplateAdapter.OnTemplateSelect{

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
        object10.setName(getString(R.string.str_yellowgray))
        object10.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_yellowgray))
        templateList.add(object10)

        val object2 = CvTemplateModelClass()
        object2.setName(getString(R.string.str_darkgraywhite))
        object2.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_lighgray))
        templateList.add(object2)


        val object3 = CvTemplateModelClass()
        object3.setName(getString(R.string.str_darkLight_gray))
        object3.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_darklight_gray))
        templateList.add(object3)

    val object4 = CvTemplateModelClass()
        object4.setName(getString(R.string.str_greenish))
        object4.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_greenish))
        templateList.add(object4)







    }
    override fun onTemplateClick(position: Int) {

        if (tinyDB.getString("UID").equals(""))
        {
            Toast.makeText(requireContext() , "No Profile Created Yet." , Toast.LENGTH_LONG).show()
        }
        else {

            when {
                templateList[position].getName().equals("Yellow Gray") -> {
                    val intent = Intent(requireContext(), PreviewYellowGrayActivity::class.java)
                    startActivity(intent)
                }

                templateList[position].getName().equals("Dark Gray White") -> {
                    val intent = Intent(requireContext() , PreviewDark_light_GrayActivity::class.java)
                    startActivity(intent)

                }
                templateList[position].getName().equals("DarkerLighterGray") -> {
                    val intent = Intent(requireContext() , PreviewDarkerLighterGrayActivity::class.java)
                    startActivity(intent)

                }
                templateList[position].getName().equals("Lighter Green") -> {
                    val intent = Intent(requireContext() , PreviewGreenishActivity::class.java)
                    startActivity(intent)

                }

            }
        }


    }


}