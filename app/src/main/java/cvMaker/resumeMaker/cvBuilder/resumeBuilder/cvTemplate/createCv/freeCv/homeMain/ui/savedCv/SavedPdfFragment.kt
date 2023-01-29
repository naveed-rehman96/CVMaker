package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.ui.savedCv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.coverLetterModule.SavedCoverLetterAcitivity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.cvModule.Saved_Cv_Pdf_Activity
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import java.util.*

class SavedPdfFragment : Fragment() {

    lateinit var btnSavedCV: CardView
    lateinit var btnSavedCoverLetter: CardView

    lateinit var tinyDB: TinyDB
    lateinit var buttonFragmentLinear: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tinyDB = TinyDB(requireContext())
        btnSavedCV = view.findViewById(R.id.btnSavedCV)
        btnSavedCoverLetter = view.findViewById(R.id.btnSavedCoverLetter)
        buttonFragmentLinear = view.findViewById(R.id.btnLinearLayout)

        buttonFragmentLinear = view.findViewById(R.id.btnLinearLayout)
        when {
            tinyDB.getString("APP_THEME") == getString(R.string.theme_blue) -> {
                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Blue_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_orange) -> {
                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Orange_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_red) -> {
                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Red_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_yellow) -> {
                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Yellow_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_green) -> {
                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Green_Theme
                    )
                )
            }
            tinyDB.getString("APP_THEME") == getString(R.string.theme_gray) -> {
                buttonFragmentLinear.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.Gray_Theme
                    )
                )
            }
        }

        tinyDB = TinyDB(requireContext())

        btnSavedCV.setOnClickListener {
            startActivity(Intent(requireContext(), Saved_Cv_Pdf_Activity::class.java))
        }

        btnSavedCoverLetter.setOnClickListener {
            startActivity(Intent(requireContext(), SavedCoverLetterAcitivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_pdf_, container, false)
    }
}
