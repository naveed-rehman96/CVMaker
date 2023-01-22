package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.TemplateModelClass

class FragmentCvTemplateAdapter
    (val context: Context, private val arrayList: ArrayList<TemplateModelClass>, var onTemplateClick: OnTemplateSelect)
    : RecyclerView.Adapter<FragmentCvTemplateAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  ,View.OnClickListener {

        var templateImage: ImageView = itemView.findViewById(R.id.templateImage)
        var templateName: TextView = itemView.findViewById(R.id.templateName)


        init {
            templateImage.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onTemplateClick.onTemplateClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.template_rcv_item_hrz, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(arrayList[position].getTemplate()).into(holder.templateImage)
        holder.templateName.text = arrayList[position].getName()
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface OnTemplateSelect{
        fun onTemplateClick(position: Int)
    }
}