package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models

class ExperienceModelClass {

    var companyName: String?= null
    var designation: String?= null
    var to_date: String?= null
    var from_date: String?= null
    var expId: String?= null
    var workingHere: String?= null


    constructor(companyName: String?,expId: String?, designation: String, to_date: String , from_date: String) {
        this.companyName = companyName
        this.designation = designation
        this.to_date = to_date
        this.from_date = from_date
        this.expId = expId
    }
    constructor(companyName: String?,expId: String?, designation: String, to_date: String , from_date: String ,currentlyWorkingHere: String ) {
        this.companyName = companyName
        this.designation = designation
        this.to_date = to_date
        this.from_date = from_date
        this.expId = expId
        this.workingHere = currentlyWorkingHere
    }

    constructor()
    @JvmName("setWorkingHere1")
    fun setWorkingHere(value : String)
    {
        this.workingHere = value
    }

    @JvmName("IsWorkingHere")
    fun getWorkingHere(): String? {
        return workingHere
    }



    @JvmName("getExpId1")
    fun getExpId(): String?{
        return expId
    }
    @JvmName("getExpId1")
    fun setExpId(expId: String?) {
        this.expId = expId
    }

    @JvmName("getCompanyName1")
    fun getCompanyName(): String? {
        return companyName
    }
    @JvmName("getCompanyName1")
    fun setCompanyName(degree: String?) {
        this.companyName = degree
    }
    @JvmName("getDesignation1")
    fun getDesignation(): String? {
        return designation
    }
    @JvmName("getDesignationName1")
    fun setDesignation(schoolName: String) {
        this.designation = schoolName
    }
    @JvmName("getToDate1")
    fun getToDate(): String? {
        return to_date
    }
    @JvmName("getToDate1")
    fun setToDate(marks: String) {
        this.to_date = marks
    }
    @JvmName("getFromDate1")
    fun getFromDate(): String? {
        return from_date
    }
    @JvmName("getFromDate1")
    fun setFromDate(marks: String) {
        this.from_date = marks
    }


}