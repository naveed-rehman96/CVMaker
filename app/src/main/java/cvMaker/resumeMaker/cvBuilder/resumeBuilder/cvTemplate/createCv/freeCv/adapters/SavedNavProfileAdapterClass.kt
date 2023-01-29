package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelMain
import java.io.File

class SavedNavProfileAdapterClass(
    val context: Context,
    private val arrayList: ArrayList<ModelMain>,
    var onClickItem: SavedProfileClick
):
    RecyclerView.Adapter<SavedNavProfileAdapterClass.MyViewHolder>()  {

    var positionSel = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.nav_profile_item_layout, parent, false)
        return MyViewHolder(itemView, onClickItem)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tinyDB =
            TinyDB(
                context
            )
        val model = arrayList[position]
        holder.fileName.text = model.fullName
        holder.checkBox.isChecked = model.id == tinyDB.getString("UID")



        if (model.imageUri == "")
        {
            holder.personImage.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_add_circle_24
                )
            )
        }
        else
        {
            Glide.with(context).load(File(model.imageUri)).into(holder.personImage)
        }




    }

    override fun getItemCount(): Int {
        return arrayList.size
    }



    inner class MyViewHolder constructor(itemView: View, private var onClickItem: SavedProfileClick)
        : RecyclerView.ViewHolder(itemView) , View.OnClickListener , View.OnLongClickListener{

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
                val product: ModelMain = arrayList[i]
                product.isSelected = i == position
            }

            onClickItem.onViewCVClick(adapterPosition)
            notifyDataSetChanged()
        }

        override fun onLongClick(v: View?): Boolean {
            onClickItem.onCVLongClickListener(adapterPosition)
            return true
        }

    }

    interface SavedProfileClick{
        fun onViewCVClick(position: Int)
        fun onCVLongClickListener(position: Int)
    }
}