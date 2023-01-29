package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.adapters

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.Saved_Cv_Pdf_Activity.Companion.isMultiSelectionOnImage
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.CvFileModelClass
import java.io.File
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*


class SavedResumeImagesAdapter(
    val context: Context,
    private val arrayList: ArrayList<CvFileModelClass>,
    var onClickItem: SavedCVClickListener
):
    RecyclerView.Adapter<SavedResumeImagesAdapter.MyViewHolder>()  {

    var count : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.pdf_rcv_template, parent, false)
        return MyViewHolder(itemView)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val model = arrayList[position]

        val file = File(model.location)
        holder.fileName.text = ""+model.name
        holder.fileSize.text = getStringSizeLengthFile(file.length())
        holder.fileDate.text = formatDate(file.lastModified())
        holder.filePath.text = file.absolutePath.substring(20)
        Glide.with(context).load(File(model.location)).into(holder.itemImage)


        if (arrayList[position].selected)
        {
            holder.itemViewCard.setCardBackgroundColor(
                ContextCompat.getColor(context
                    ,android.R.color.darker_gray))
        }
        else
        {
            holder.itemViewCard.setCardBackgroundColor(
                ContextCompat.getColor(context
                    ,R.color.white
                ))
        }


        holder.itemView.setOnLongClickListener {


            isMultiSelectionOnImage = true
            onClickItem.selectionImage(isMultiSelectionOnImage)
            if (arrayList[position].selected)
            {
                arrayList[position].selected = false
                holder.itemViewCard.setCardBackgroundColor(
                    ContextCompat.getColor(context
                    ,R.color.white))
                count--
            }
            else
            {
                arrayList[position].selected = true
                holder.itemViewCard.setCardBackgroundColor(
                    ContextCompat.getColor(context
                    , android.R.color.darker_gray
                ))
                count++
            }
            return@setOnLongClickListener true
        }



        holder.itemView.setOnClickListener{

            if (isMultiSelectionOnImage)
            {
                onClickItem.selectionImage(isMultiSelectionOnImage)
                if (arrayList[position].selected)
                {
                    arrayList[position].selected = false
                    holder.itemViewCard.setCardBackgroundColor(
                        ContextCompat.getColor(context
                        ,R.color.white))
                    count--
                }
                else
                {
                    arrayList[position].selected = true
                    holder.itemViewCard.setCardBackgroundColor(
                        ContextCompat.getColor(context
                        , android.R.color.darker_gray
                    ))
                    count++
                }
            }
            else {

                onClickItem.selectionImage(isMultiSelectionOnImage)
                val file: File = File(model.location)
                val target = Intent(Intent.ACTION_VIEW)
                val uri = FileProvider.getUriForFile(
                    context,
                    "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.FileProvider",
                    file
                )

                target.setDataAndType(uri, "image/*")
                target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                target.putExtra(Intent.EXTRA_STREAM, uri)
                val intent = Intent.createChooser(target, "Open File")
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                }
            }
        }

        holder.menuBtn.setOnClickListener{
            val popup = PopupMenu(context, holder.menuBtn)
            popup.inflate(R.menu.menu_option)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.actionDelete -> {
                        val dialogBuilder1 = AlertDialog.Builder(context, R.style.DialogStyle)
                        val inflater = LayoutInflater.from(context)
                        val dialogView = inflater.inflate(R.layout.dialog_deletefile, null)
                        dialogBuilder1.setView(dialogView)
                        val cancel: Button = dialogView.findViewById(R.id.btnCancelExitDialog)
                        val Ok: Button = dialogView.findViewById(R.id.btnOkExitDialog)
                        val alertDialog = dialogBuilder1.create()
                        alertDialog.setCancelable(false)


                        cancel.setOnClickListener {
                            alertDialog.dismiss()
                        }

                        Ok.setOnClickListener {
                            val deleteFile = File(model.location)
                            arrayList.removeAt(position)
                            deleteFile.delete()
                            notifyDataSetChanged()
                            alertDialog.dismiss()

                        }
                        alertDialog.show()

                    }
                    R.id.actionShare -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "application/pdf"
                        val fileUri = FileProvider.getUriForFile(
                            context,
                            "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.FileProvider",
                            File(model.location)
                        )
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
                        context.startActivity(Intent.createChooser(shareIntent, "Share it"))
                    }
                    R.id.actionRename -> {
                        showRenameDialog(context , model)
                    }
                }
                false
            }
            //displaying the popup
            //displaying the popup
            popup.show()
        }

    }
    @SuppressLint("SetTextI18n")
    private fun showRenameDialog(context: Context, model: CvFileModelClass) {


        val oldFile = File(model.location)
        val dialogBuilder = AlertDialog.Builder(context, R.style.DialogStyle)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_rename, null)
        dialogBuilder.setView(dialogView)
        var cvName : TextInputLayout
        cvName = dialogView.findViewById(R.id.CV_name_input)
        var createBt : Button = dialogView.findViewById(R.id.btnCreateCV)
        var fileName : TextView = dialogView.findViewById(R.id.fileNameRenameDialog)
        fileName.text = "Old Name : "+ oldFile.name
        var cancelBt : Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder .create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener{
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
                val newFile = File(oldFile.parentFile.path+"/"+ name +".jpg")
                oldFile.renameTo(newFile)
                alertDialog.dismiss()
                onClickItem.renamedImage()
                notifyDataSetChanged()

            }
            else
            {
                cvName.editText?.error = "Required"
            }
        }

        cancelBt.setOnClickListener{
            alertDialog.dismiss()
        }


        alertDialog.show()

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun formatDate(dateInMillis: Long): String? {
        val date = Date(dateInMillis)
        return DateFormat.getDateInstance().format(date)
    }
    fun getStringSizeLengthFile(size: Long): String {
        val df = DecimalFormat("0.00")
        val sizeKb = 1024.0f
        val sizeMb = sizeKb * sizeKb
        val sizeGb = sizeMb * sizeKb
        val sizeTerra = sizeGb * sizeKb
        if (size < sizeMb)
            return df.format((size / sizeKb).toDouble()) + " Kb"
        else if (size < sizeGb)
            return df.format((size / sizeMb).toDouble()) + " Mb"
        else if (size < sizeTerra)
            return df.format((size / sizeGb).toDouble()) + " Gb"
        return ""
    }

    inner class MyViewHolder constructor(itemView: View)
        : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        var fileName: TextView = itemView.findViewById(R.id.fileName)
        var fileSize: TextView = itemView.findViewById(R.id.fileSize)
        var fileDate: TextView = itemView.findViewById(R.id.fileDate)
        var itemViewCard: CardView = itemView.findViewById(R.id.itemView)
        var itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        var filePath: TextView = itemView.findViewById(R.id.filePath)
        var menuBtn : ImageButton = itemView.findViewById(R.id.btnOptionMenu)
        val tinyDB =
            TinyDB(
                context
            )



        override fun onClick(v: View?) {


        }

    }
    interface SavedCVClickListener{
        fun renamedImage()
        fun selectionImage(b: Boolean)
    }

}