package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models

import android.graphics.drawable.Drawable

class CvTemplateModelClass {

    private var template: Drawable? = null
    var Name: String? = null

    fun TemplateModelClass(template: Drawable?, name: String?) {
        this.template = template
        Name = name
    }

    fun getTemplate(): Drawable? {
        return template
    }

    fun setTemplate(template: Drawable?) {
        this.template = template
    }

    @JvmName("getName1")
    fun getName(): String? {
        return Name
    }

    @JvmName("setName1")
    fun setName(name: String?) {
        Name = name
    }
}
