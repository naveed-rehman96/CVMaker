package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses



data class ModelMain (
    val fileName: String,
    val fullName: String,
    val imageUri: String,
    val emailId: String,
    val id: String,
    var isSelected : Boolean = false
 )