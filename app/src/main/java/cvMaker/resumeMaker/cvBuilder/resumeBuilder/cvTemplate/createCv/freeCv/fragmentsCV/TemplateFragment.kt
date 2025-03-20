package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsCV

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.FragmentCvTemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvTemplateModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewsTemplates.previewCvTemplateActivities.* // ktlint-disable no-wildcard-imports

class TemplateFragment : Fragment(), FragmentCvTemplateAdapter.OnTemplateSelect {

    lateinit var recyclerView: RecyclerView
    lateinit var templateList: ArrayList<CvTemplateModelClass>
    lateinit var adapter: FragmentCvTemplateAdapter
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var tinyDB: TinyDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_template, container, false)

        tinyDB =
            TinyDB(
                context
            )
        templateList = ArrayList()
        addDataToArray()

        recyclerView = view.findViewById(R.id.fragmenttemplateRcv)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = FragmentCvTemplateAdapter(requireContext(), templateList, this)
        recyclerView.adapter = adapter

        return view
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {
        val object4 = CvTemplateModelClass()
        object4.setName(getString(R.string.str_darkbluecream))
        object4.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_blue_dark_cream))
        templateList.add(object4)
        val object7 = CvTemplateModelClass()
        object7.setName(getString(R.string.str_darkredwhite))
        object7.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_dark_redwhite))
        templateList.add(object7)

        val object10 = CvTemplateModelClass()
        object10.setName(getString(R.string.str_yellowgray))
        object10.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_yellowgray))
        templateList.add(object10)

        val object1 = CvTemplateModelClass()
        object1.setName(getString(R.string.str_dark_light_gray))
        object1.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.temp_darkgray))
        templateList.add(object1)

        val object2 = CvTemplateModelClass()
        object2.setName(getString(R.string.str_darkgraywhite))
        object2.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_lighgray))
        templateList.add(object2)

        val object3 = CvTemplateModelClass()
        object3.setName(getString(R.string.str_darkbluewhite))
        object3.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_dark_blue_white_cream))
        templateList.add(object3)

        val object5 = CvTemplateModelClass()
        object5.setName(getString(R.string.str_graywhite))
        object5.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_graywhite))
        templateList.add(object5)

        val object6 = CvTemplateModelClass()
        object6.setName(getString(R.string.str_lightwhite))
        object6.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_light_white))
        templateList.add(object6)

        val object8 = CvTemplateModelClass()
        object8.setName(getString(R.string.darklightwhite))
        object8.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_lightgraywhite))
        templateList.add(object8)

        val object9 = CvTemplateModelClass()
        object9.setName(getString(R.string.str_whitebox))
        object9.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.templ_whitebox))
        templateList.add(object9)
    }

    override fun onTemplateClick(position: Int) {
        if (templateList[position].getName().equals("Dark Light Gray")) {
            val intent = Intent(requireContext(), PreviewDark_GrayBlueActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Dark Gray White")) {
            val intent = Intent(requireContext(), PreviewDark_light_GrayActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Dark Blue White")) {
            val intent = Intent(requireContext(), PreviewDarkBlue_whiteCreamActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Dark Blue Cream")) {
            val intent = Intent(requireContext(), PreviewBlueCreamCreamActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Gray White")) {
            val intent = Intent(requireContext(), PreviewDark_GrayWhiteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Light White")) {
            val intent = Intent(requireContext(), PreviewlightWhiteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Dark Red White")) {
            val intent = Intent(requireContext(), PreviewDarkRedWhiteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Light Gray White")) {
            val intent = Intent(requireContext(), PreviewlightGrayWhiteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("White Box")) {
            val intent = Intent(requireContext(), PreviewlightWhiteboxActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (templateList[position].getName().equals("Yellow Gray")) {
            val intent = Intent(requireContext(), PreviewYellowGrayActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}
