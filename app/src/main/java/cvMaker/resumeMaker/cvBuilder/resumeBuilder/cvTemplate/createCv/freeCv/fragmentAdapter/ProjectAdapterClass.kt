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
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ProjectsEntity

class ProjectAdapterClass(
    val context1: Context,
    private val arrayList: ArrayList<ProjectsEntity>,
    var onClicked: onExperienceClickedListener
) :
    RecyclerView.Adapter<ProjectAdapterClass.MyViewHolder>() {


    inner class MyViewHolder(itemView: View, onClick: onExperienceClickedListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

//        var CName: TextView = itemView.findViewById(R.id.itemCName)
//        var designation: TextView = itemView.findViewById(R.id.itemDesignation)
//        var todate: TextView = itemView.findViewById(R.id.itemToDate)
//        var fromdate: TextView = itemView.findViewById(R.id.itemFromDate)
//
//
        var CName: TextView = itemView.findViewById(R.id.schoolNameEDT_rcv)
        var itemNumber: TextView = itemView.findViewById(R.id.textView)



        var btnEditExp: ImageView = itemView.findViewById(R.id.btnEditExp)
        var btnDeleteExp: ImageView = itemView.findViewById(R.id.btnDeleteExp)
        var onExperienceClickedListener: onExperienceClickedListener = onClick

        init {

            btnEditExp.setOnClickListener(this)
            btnDeleteExp.setOnClickListener(this)

            var tinyDB =
                TinyDB(
                    context1
                )
            when {
                tinyDB.getString("APP_THEME") == context1.getString(R.string.theme_blue) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context1, R.color.Blue_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context1, R.color.Blue_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context1.getString(R.string.theme_orange) -> {

                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context1, R.color.Orange_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context1, R.color.Orange_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context1.getString(R.string.theme_red) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context1, R.color.Red_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context1, R.color.Red_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context1.getString(R.string.theme_yellow) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context1, R.color.Yellow_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context1, R.color.Yellow_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context1.getString(R.string.theme_green) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context1, R.color.Green_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context1, R.color.Green_Theme)
                    );
                }
                tinyDB.getString("APP_THEME") == context1.getString(R.string.theme_gray) -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnEditExp.drawable),
                        ContextCompat.getColor(context1, R.color.Gray_Theme)
                    );
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(btnDeleteExp.drawable),
                        ContextCompat.getColor(context1, R.color.Gray_Theme)
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
        val itemView: View = LayoutInflater.from(context1)
            .inflate(R.layout.technical_skill_item_layout_editable, parent, false)
        return MyViewHolder(itemView, onClicked)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val modelClass = arrayList[position]

        holder.CName.text = modelClass.projectTitle
        val number = position +1
        holder.itemNumber.text = "Project $number"


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    public interface onExperienceClickedListener {
        fun onEditClickListener(position: Int)
        fun onDeleteClickListener(position: Int)
    }
}