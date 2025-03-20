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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewDarkBlue_whiteCreamActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewDark_GrayBlueActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewDark_GrayWhiteActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.PreviewlightWhiteActivity


class ProfessionalTemplateFragment : Fragment() , TemplateAdapter.OnTemplateSelect{


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

        val object6 = CvTemplateModelClass()
        object6.setName(getString(R.string.str_lightwhite))
        object6.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_light_white))
        templateList.add(object6)


        val object5 = CvTemplateModelClass()
        object5.setName(getString(R.string.str_graywhite))
        object5.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_graywhite))
        templateList.add(object5)

        val object3 = CvTemplateModelClass()
        object3.setName(getString(R.string.str_darkbluewhite))
        object3.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_dark_blue_white_cream))
        templateList.add(object3)

        val object1 = CvTemplateModelClass()
        object1.setName(getString(R.string.str_dark_light_gray))
        object1.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.temp_darkgray))
        templateList.add(object1)




    }
    override fun onTemplateClick(position: Int) {

        if (tinyDB.getString("UID").equals(""))
        {
            Toast.makeText(requireContext() , "No Profile Created Yet." , Toast.LENGTH_LONG).show()
        }
        else {
            when {
                templateList[position].getName().equals("Dark Light Gray") -> {
                    val intent = Intent(requireContext(), PreviewDark_GrayBlueActivity::class.java)
                    startActivity(intent)
                }

                templateList[position].getName().equals("Dark Blue White") -> {
                    val intent = Intent(requireContext(), PreviewDarkBlue_whiteCreamActivity::class.java)
                    startActivity(intent)
                }

                templateList[position].getName().equals("Gray White") -> {
                    val intent = Intent(requireContext(), PreviewDark_GrayWhiteActivity::class.java)
                    startActivity(intent)
                }
                templateList[position].getName().equals("Light White") -> {
                    val intent = Intent(requireContext() , PreviewlightWhiteActivity::class.java)
                    startActivity(intent)
                }

            }
        }


    }


}