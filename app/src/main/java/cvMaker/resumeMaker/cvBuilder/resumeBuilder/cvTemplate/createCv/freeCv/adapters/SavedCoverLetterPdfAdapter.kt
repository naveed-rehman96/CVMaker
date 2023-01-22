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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.SavedCoverLetterAcitivity.Companion.isMultiSelectionCLPDF
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.FileModelClass
import java.io.File
import java.text.DateFormat
import java.util.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SavedCoverLetterPdfAdapter(
    val context: Context,
    private val arrayList: ArrayList<FileModelClass>,
    var onClickItem: SavedCVClickListener

) :
    RecyclerView.Adapter<SavedCoverLetterPdfAdapter.MyViewHolder>() {

    var count = 0;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.pdf_rcv_template, parent, false)
        return MyViewHolder(itemView, onClickItem)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val model = arrayList[position]

        val file = File(model.location)
        holder.fileName.text = "" + model.name
        holder.fileSize.visibility = View.GONE
        holder.fileDate.text = formatDate(file.lastModified())
        holder.filePath.visibility = View.GONE


        if (arrayList[position].selected) {
            holder.itemViewCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    context, android.R.color.darker_gray
                )
            )
        } else {
            holder.itemViewCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    context, R.color.white
                )
            )
        }

        holder.itemView.setOnLongClickListener {
            isMultiSelectionCLPDF = true
            onClickItem.selectionTrue(isMultiSelectionCLPDF)
            if (arrayList[position].selected) {

                arrayList[position].selected = false
                holder.itemViewCard.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context, R.color.white
                    )
                )
                count--
            } else {
                arrayList[position].selected = true
                holder.itemViewCard.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context, android.R.color.darker_gray
                    )
                )
                count++
            }
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }



        holder.itemView.setOnClickListener {


            if (isMultiSelectionCLPDF) {
                onClickItem.selectionTrue(isMultiSelectionCLPDF)
                if (arrayList[position].selected) {
                    arrayList[position].selected = false
                    holder.itemViewCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context, R.color.white
                        )
                    )
                    count--
                } else {
                    arrayList[position].selected = true
                    holder.itemViewCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context, android.R.color.darker_gray
                        )
                    )
                    count++
                }
                notifyDataSetChanged()
            } else {

                onClickItem.selectionTrue(isMultiSelectionCLPDF)
                val file: File = File(model.location)
                val target = Intent(Intent.ACTION_VIEW)
                val uri = FileProvider.getUriForFile(
                    context,
                    "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.FileProvider",
                    file
                )

                target.setDataAndType(uri, "application/pdf")
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

        holder.menuBtn.setOnClickListener {
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
                            deleteFile.delete()
                            arrayList.removeAt(position)
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
                        val oldFile = File(model.location)
                        showRenameDialog(context, model)

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
    private fun showRenameDialog(context: Context, model: FileModelClass) {


        val oldFile = File(model.location)
        val dialogBuilder = AlertDialog.Builder(context, R.style.DialogStyle)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_rename, null)
        dialogBuilder.setView(dialogView)
        var cvName: TextInputLayout
        cvName = dialogView.findViewById(R.id.CV_name_input)
        var createBt: Button = dialogView.findViewById(R.id.btnCreateCV)
        var fileName: TextView = dialogView.findViewById(R.id.fileNameRenameDialog)
        fileName.text = "Old Name : " + oldFile.name
        var cancelBt: Button = dialogView.findViewById(R.id.btnCancelDialog)
        val alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(false)

        createBt.setOnClickListener {
            val name = cvName.editText?.text.toString()
            if (!TextUtils.isEmpty(name)) {
                val newFile = File(oldFile.parentFile.path + "/" + name + ".pdf")
                oldFile.renameTo(newFile)
                alertDialog.dismiss()
                onClickItem.renamedPdf()
                notifyDataSetChanged()

            } else {
                cvName.editText?.error = "Required"
            }
        }

        cancelBt.setOnClickListener {
            alertDialog.dismiss()
        }


        alertDialog.show()

    }

    private fun rename(from: File, to: File): Boolean {
        return from.parentFile.exists() && from.exists() && from.renameTo(to)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun formatDate(dateInMillis: Long): String? {
        val date = Date(dateInMillis)
        return DateFormat.getDateInstance().format(date)
    }


    inner class MyViewHolder constructor(
        itemView: View,
        private var onClickItem: SavedCVClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var fileName: TextView = itemView.findViewById(R.id.fileName)
        var fileSize: TextView = itemView.findViewById(R.id.fileSize)
        var fileDate: TextView = itemView.findViewById(R.id.fileDate)
        var itemViewCard: CardView = itemView.findViewById(R.id.itemView)
        var filePath: TextView = itemView.findViewById(R.id.filePath)
        var menuBtn: ImageButton = itemView.findViewById(R.id.btnOptionMenu)
        val tinyDB =
            TinyDB(
                context
            )


        override fun onClick(v: View?) {


        }

    }

    interface SavedCVClickListener {
        fun renamedPdf()
        fun selectionTrue(b: Boolean)
    }
}