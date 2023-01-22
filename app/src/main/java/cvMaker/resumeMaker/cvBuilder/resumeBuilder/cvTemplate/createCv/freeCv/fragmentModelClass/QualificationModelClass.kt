package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass

class QualificationModelClass {

    var degree: String?= null
    var schoolName: String?= null
    var marks: String?= null
    var endDate: String?= null
    var quaId: String?= null

    @JvmName("getQuaId1")
    fun getQuaId(): String?{
        return quaId
    }
    @JvmName("getQuaId1")
    fun setQuaId(quaId: String?) {
        this.quaId = quaId
    }
    @JvmName("getEndDate1")
    fun getEndDate(): String?{
        return endDate
    }

    @JvmName("setEndDate1")
    fun setEndDate(endDate: String)
    {
        this.endDate = endDate
    }

    @JvmName("getDegree1")
    fun getDegree(): String? {
        return degree
    }
    @JvmName("getDegree1")
    fun setDegree(degree: String?) {
        this.degree = degree
    }
    @JvmName("getSchoolName1")
    fun getSchoolName(): String? {
        return schoolName
    }
    @JvmName("getSchoolName1")
    fun setSchoolName(schoolName: String) {
        this.schoolName = schoolName
    }
    @JvmName("getmarks1")
    fun getMarks(): String? {
        return marks
    }
    @JvmName("getMarks1")
    fun setMarks(marks: String) {
        this.marks = marks
    }


}