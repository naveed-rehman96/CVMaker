package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule

import android.content.Context
import android.widget.Toast


var mToast: Toast? = null

fun Context.showMessage(message: String) {
    if (mToast != null) {
        mToast!!.cancel()
    }
    mToast = Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    )
    mToast!!.show()
}