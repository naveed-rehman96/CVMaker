package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.QualificationEntity

class QualificationAdapterClass(
    val context: Context,
    private val arrayList: ArrayList<QualificationEntity>,
    var onClicked: onQualificationClickedListener
) :
    RecyclerView.Adapter<QualificationAdapterClass.MyViewHolder>() {


    inner class MyViewHolder(itemView: View, onClick: onQualificationClickedListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


        //
        var CName: TextView = itemView.findViewById(R.id.schoolNameEDT_rcv)
        var designation: TextView = itemView.findViewById(R.id.degreeNameEdt_rcv)
        var todate: TextView = itemView.findViewById(R.id.scoreEdt_rcv)
        var fromdate: TextView = itemView.findViewById(R.id.completedOnEdt_rcv)
        var itemNumber: TextView = itemView.findViewById(R.id.textView)

        var btnEditExp: ImageView = itemView.findViewById(R.id.btnEditExp)
        var btnDeleteExp: ImageView = itemView.findViewById(R.id.btnDeleteExp)
        var onExperienceClickedListener: onQualificationClickedListener = onClick

        init {


            btnEditExp.setOnClickListener(this)
            btnDeleteExp.setOnClickListener(this)
            var tinyDB =
                TinyDB(
                    context
                )
            when {
                tinyDB.getString("APP_THEME") == context.getString(R.string.theme_blue) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context, R.color.Blue_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context, R.color.Blue_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context.getString(R.string.theme_orange) -> {

                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context, R.color.Orange_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context, R.color.Orange_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context.getString(R.string.theme_red) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context, R.color.Red_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context, R.color.Red_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context.getString(R.string.theme_yellow) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context, R.color.Yellow_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context, R.color.Yellow_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context.getString(R.string.theme_green) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context, R.color.Green_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context, R.color.Green_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context.getString(R.string.theme_gray) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context, R.color.Gray_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context, R.color.Gray_Theme)
                    );
                }
            }

        }

        override fun onClick(v: View?) {

            if (v != null) {
                if (v.id == R.id.btnEditExp)
                {
                    onExperienceClickedListener.onEditClickListener(adapterPosition)
                }
                if (v.id == R.id.btnDeleteExp)
                {
                    onExperienceClickedListener.onDeleteClickListener(adapterPosition)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.experience_item_layout_editable, parent, false)
        return MyViewHolder(itemView, onClicked)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val modelClass = arrayList[position]

        holder.CName.text = "" + modelClass.schoolName
        holder.designation.text = "Title : " + modelClass.subject
        holder.todate.text = "Marks : " + modelClass.marks
        holder.fromdate.text = "Completed In : " + modelClass.endDate
        val num = position+1
        holder.itemNumber.text = "Qualification $num"

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    public interface onQualificationClickedListener {
        fun onEditClickListener(position: Int)
        fun onDeleteClickListener(position: Int)
    }
}