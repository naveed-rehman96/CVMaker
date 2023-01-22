package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.ModelCoverLetter

class CoverLetterAdapterClassFrag(
    val context: Context, private val arrayList: ArrayList<ModelCoverLetter>,
    var onClickItem: CoverLetterClickListener
) :
    RecyclerView.Adapter<CoverLetterAdapterClassFrag.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.cover_letter_profile_new, parent, false)
        return MyViewHolder(itemView, onClickItem)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val model = arrayList[position]


        holder.fileName.text = model.cfilename

        val tinyDB = TinyDB(context)
        holder.checkBox.isChecked = model.cid == tinyDB.getString("CL_ID")


    }


    override fun getItemCount(): Int {
        return arrayList.size
    }


    inner class MyViewHolder constructor(itemView: View,
                                         private var onClickItem: CoverLetterClickListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        var fileName: TextView = itemView.findViewById(R.id.nameNavProfile)
        var personImage: ImageView = itemView.findViewById(R.id.imageNavProfile)
        var checkBox: RadioButton = itemView.findViewById(R.id.selectedProfileRadioButton)

        val tinyDB =
            TinyDB(
                context
            )

        init {

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            checkBox.setOnClickListener(this)


        }


        override fun onClick(v: View?) {

            arrayList[position].isSelected = true

            for (i in 0 until arrayList.size) {
                val product: ModelCoverLetter = arrayList[i]
                product.isSelected = i == position
            }

            onClickItem.onViewCVClick(adapterPosition)
            notifyDataSetChanged()
        }

        override fun onLongClick(v: View?): Boolean {
            onClickItem.onLongViewCLClick(adapterPosition)
            return true
        }

    }

    interface CoverLetterClickListener {
        fun onViewCVClick(position: Int)
        fun onLongViewCLClick(position: Int)
    }
}