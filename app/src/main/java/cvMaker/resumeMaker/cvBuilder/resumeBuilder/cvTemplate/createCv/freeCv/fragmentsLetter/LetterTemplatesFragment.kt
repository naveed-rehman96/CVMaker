package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentsLetter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters.SelectTemplateAdapter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper.DataBaseHandler
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.ModelCoverLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.TemplateModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetter.PreviewBlueLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetter.PreviewBrownLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetter.PreviewGreenLetterActivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.previewCoverLetter.PreviewWhiteLetterActivity


class LetterTemplatesFragment : Fragment() , SelectTemplateAdapter.OnTemplateClick {

    companion object
    {
        lateinit var modelLetterMain : ModelCoverLetter
    }
    lateinit var tinyDB: TinyDB
    lateinit var database: DataBaseHandler
    lateinit var recyclerView: RecyclerView
    lateinit var templateList: ArrayList<TemplateModelClass>
    lateinit var adapter: SelectTemplateAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val v = inflater.inflate(R.layout.fragment_letter_templates, container, false)
        database = DataBaseHandler(requireContext())
        tinyDB =
            TinyDB(
                requireContext()
            )
        modelLetterMain = ModelCoverLetter()
        templateList = ArrayList()

        addDataToArray()
        recyclerView = v.findViewById(R.id.lettertemplateRcv)
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager
        adapter = SelectTemplateAdapter(requireContext(), templateList, this)
        recyclerView.adapter = adapter

        return v
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun addDataToArray() {
        val object1 = TemplateModelClass()
        object1.setName("Blue Bar")
        object1.setTemplate(ContextCompat.getDrawable(requireContext() , R.drawable.bluebar))
        templateList.add(object1)

        val object2 = TemplateModelClass()
        object2.setName("Green Bar")
        object2.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.greenbar))
        templateList.add(object2)

        val object3 = TemplateModelClass()
        object3.setName("White BG")
        object3.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.white))
        templateList.add(object3)

        val object4 = TemplateModelClass()
        object4.setName("Brown Bar")
        object4.setTemplate(ContextCompat.getDrawable(requireContext(), R.drawable.brown))
        templateList.add(object4)

    }

    override fun onTemplateClick(position: Int) {
        if (templateList.get(position).getName().equals("Blue Bar")) {
            val intent = Intent(requireContext() , PreviewBlueLetterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
            startActivity(intent)
        } else if (templateList.get(position).getName().equals("Green Bar")) {
            val intent = Intent(requireContext() , PreviewGreenLetterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
            startActivity(intent)
        } else if (templateList.get(position).getName().equals("White BG")) {
            val intent = Intent(requireContext() , PreviewWhiteLetterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
            startActivity(intent)
        } else if (templateList.get(position).getName().equals("Brown Bar")) {
            val intent = Intent(requireContext() , PreviewBrownLetterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
            startActivity(intent)
        }
    }



}