package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models

import android.os.Parcel
import android.os.Parcelable


class ModelNewMain() : Parcelable {

    var imageUri: String = ""
    var fullName:  String= ""
    var fatherName:  String= ""
    var phoneNum:  String= ""
    var emailId: String= ""
    var dateOFBirth:  String= ""
    var CNIC:  String= ""
    var nationality:  String= ""
    var address:  String= ""
    var gender:  String= ""
    var maritalStatus:  String = ""
    var id:  String= ""
    var fileName: String= ""
    var objective:  String= ""
    var interest:  String= ""
    var language:  String= ""
    var reference:  String= ""
    var qualification: ArrayList<QualificationModelClass>? = null
    var exp: ArrayList<ExperienceModelClass>? = null
    var skills: ArrayList<TechnicalSkillModelClass>? = null
    var awards: String= ""
    var projects: ArrayList<ProjectModelClass>? = null
    var fullNumber:String = ""
     var countryCode:String = ""

    constructor(parcel: Parcel) : this() {
        imageUri = parcel.readString().toString()
        fullName = parcel.readString().toString()
        fatherName = parcel.readString().toString()
        phoneNum = parcel.readString().toString()
        emailId = parcel.readString().toString()
        dateOFBirth = parcel.readString().toString()
        CNIC = parcel.readString().toString()
        nationality = parcel.readString().toString()
        address = parcel.readString().toString()
        gender = parcel.readString().toString()
        maritalStatus = parcel.readString().toString()
        id = parcel.readString().toString()
        fileName = parcel.readString().toString()
        objective = parcel.readString().toString()
        interest = parcel.readString().toString()
        language = parcel.readString().toString()
        reference = parcel.readString().toString()
        awards = parcel.readString().toString()
        fullNumber = parcel.readString().toString()
        countryCode = parcel.readString().toString()
    }


    constructor(
        imageUri: String,
        fullName: String,
        fatherName: String,
        phoneNum: String,
        emailId: String,
        dateOFBirth: String,
        CNIC: String,
        nationality: String,
        address: String,
        id: String,
        fileName: String,
        objective: String,
        interest: String,
        language: String,
        reference: String,
        qualification: ArrayList<QualificationModelClass>? = null,
        exp: ArrayList<ExperienceModelClass>? = null,
        skills: ArrayList<TechnicalSkillModelClass>? = null,
        awards: String,
        projects: ArrayList<ProjectModelClass>? = null

    ) : this() {
        this.imageUri = imageUri
        this.fullName = fullName
        this.fatherName = fatherName
        this.phoneNum = phoneNum
        this.emailId = emailId
        this.dateOFBirth = dateOFBirth
        this.CNIC = CNIC
        this.nationality = nationality
        this.address = address
        this.id = id
        this.fileName = fileName
        this.objective = objective
        this.exp = exp
        this.qualification = qualification
        this.skills = skills
        this.awards = awards
        this.projects = projects
        this.interest = interest
        this.language = language
        this.reference = reference
        this.address = address
    }

    constructor(
        imageUri: String,
        fullName: String,
        fatherName: String,
        phoneNum: String,
        emailId: String,
        dateOFBirth: String,
        CNIC: String,
        nationality: String,
        id: String,
        fileName: String,
        objective: String,
        interest: String,
        language: String,
        reference: String,
        awards: String,
        fullNumber:String,
        countryCode:String,
        address: String,
        gender: String,
        maritalStatus: String

    ) : this() {
        this.imageUri = imageUri
        this.fullName = fullName
        this.fatherName = fatherName
        this.phoneNum = phoneNum
        this.emailId = emailId
        this.dateOFBirth = dateOFBirth
        this.CNIC = CNIC
        this.nationality = nationality
        this.id = id
        this.fileName = fileName
        this.objective = objective
        this.awards = awards
        this.interest = interest
        this.language = language
        this.reference = reference
        this.fullNumber = fullNumber
        this.countryCode = countryCode
        this.address = address
        this.gender = gender
        this.maritalStatus = maritalStatus
    }
    @JvmName("getGender1")
    fun getGender(): String
    {
        return gender
    }
    @JvmName("setGender1")
    fun setGender(gender: String){
        this.gender = gender
    }

    @JvmName("getmaritalStatus1")
    fun getMaritalStatus(): String
    {
        return maritalStatus
    }
    @JvmName("setmaritalStatus1")
    fun setMaritalStatus(maritalStatus: String){
        this.maritalStatus = maritalStatus
    }



    @JvmName("getFullNumber1")
    fun getFullNumber(): String
    {
        return fullNumber
    }
    @JvmName("setFullNumber1")
    fun setFullNumber(fullNumber: String){
        this.fullNumber = fullNumber
    }

    @JvmName("getCountryCode1")
    fun getCountryCode(): String
    {
        return countryCode
    }
    @JvmName("setCountryCode1")
    fun setCountryCode(countryCode: String){
        this.countryCode = countryCode
    }

    @JvmName("getAddressCode1")
    fun getAddress(): String
    {
        return address
    }
    @JvmName("setAddressCode1")
    fun setAddress(address: String){
        this.address = address
    }

    @JvmName("getLanguage1")
    fun getLanguage(): String
    {
        return language
    }
    @JvmName("setLanguage1")
    fun setLanguage(language: String){
        this.language = language
    }

    @JvmName("getImageUri1")
    fun getImageUri(): String {
        return imageUri
    }

    @JvmName("setImageUri1")
    fun setImageUri(imageUri: String?) {
        if (imageUri != null) {
            this.imageUri = imageUri
        }
    }

    @JvmName("getFullName1")
    fun getFullName(): String {
        return fullName
    }

    @JvmName("setFullName1")
    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

    @JvmName("getFatherName1")
    fun getFatherName(): String {
        return fatherName
    }

    @JvmName("setFatherName1")
    fun setFatherName(fatherName: String) {
        this.fatherName = fatherName
    }

    @JvmName("getPhoneNum1")
    fun getPhoneNum(): String {
        return phoneNum
    }

    @JvmName("setPhoneNum1")
    fun setPhoneNum(phoneNum: String) {
        this.phoneNum = phoneNum
    }

    @JvmName("getEmailId1")
    fun getEmailId(): String {
        return emailId
    }

    @JvmName("setEmailId1")
    fun setEmailId(emailId: String) {
        this.emailId = emailId
    }

    @JvmName("getDateOFBirth1")
    fun getDateOFBirth(): String {
        return dateOFBirth
    }

    @JvmName("setDateOFBirth1")
    fun setDateOFBirth(dateOFBirth: String) {
        this.dateOFBirth = dateOFBirth
    }

    @JvmName("getCNIC1")
    fun getCNIC(): String {
        return CNIC
    }

    @JvmName("setCNIC1")
    fun setCNIC(CNIC: String) {
        this.CNIC = CNIC
    }

    @JvmName("getNationality1")
    fun getNationality(): String {
        return nationality
    }

    @JvmName("setNationality1")
    fun setNationality(nationality: String) {
        this.nationality = nationality
    }

    @JvmName("getId1")
    fun getId(): String {
        return id
    }

    @JvmName("setId1")
    fun setId(id: String) {
        this.id = id
    }

    @JvmName("getFileName1")
    fun getFileName(): String {
        return fileName
    }

    @JvmName("setFileName1")
    fun setFileName(fileName: String) {
        this.fileName = fileName
    }

    @JvmName("getObjective1")
    fun getObjective(): String {
        return objective
    }

    @JvmName("setObjective1")
    fun setObjective(objective: String) {
        this.objective = objective
    }

    @JvmName("getExp1")
    fun getExp(): ArrayList<ExperienceModelClass>? {
        return exp
    }

    @JvmName("setExp1")
    fun setExp(exp: ArrayList<ExperienceModelClass>) {
        this.exp = exp
    }

    @JvmName("getQualification1")
    fun getQualification(): ArrayList<QualificationModelClass>? {
        return qualification
    }

    @JvmName("setQualification1")
    fun setQualification(qualification: ArrayList<QualificationModelClass>) {
        this.qualification = qualification
    }

    @JvmName("getSkills1")
    fun getSkills(): ArrayList<TechnicalSkillModelClass>? {
        return skills
    }

    @JvmName("setSkills1")
    fun setSkills(skills: ArrayList<TechnicalSkillModelClass>) {
        this.skills = skills
    }

    @JvmName("getAwards1")
    fun getAwards(): String {
        return awards
    }

    @JvmName("setAwards1")
    fun setAwards(awards: String) {
        this.awards = awards
    }

    @JvmName("getProjects1")
    fun getProjects(): ArrayList<ProjectModelClass>? {
        return projects
    }

    @JvmName("setProjects1")
    fun setProjects(projects: ArrayList<ProjectModelClass>) {
        this.projects = projects
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUri)
        parcel.writeString(fullName)
        parcel.writeString(fatherName)
        parcel.writeString(phoneNum)
        parcel.writeString(emailId)
        parcel.writeString(dateOFBirth)
        parcel.writeString(CNIC)
        parcel.writeString(nationality)
        parcel.writeString(address)
        parcel.writeString(gender)
        parcel.writeString(id)
        parcel.writeString(fileName)
        parcel.writeString(objective)
        parcel.writeString(interest)
        parcel.writeString(language)
        parcel.writeString(reference)
        parcel.writeString(awards)
        parcel.writeString(fullNumber)
        parcel.writeString(countryCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ModelNewMain(imageUri='$imageUri', fullName='$fullName', fatherName='$fatherName', phoneNum='$phoneNum', emailId='$emailId', dateOFBirth='$dateOFBirth', CNIC='$CNIC', nationality='$nationality', address='$address', gender='$gender', maritalStatus='$maritalStatus', id='$id', fileName='$fileName', objective='$objective', interest='$interest', language='$language', reference='$reference', qualification=$qualification, exp=$exp, skills='$skills', awards='$awards', projects='$projects', fullNumber='$fullNumber', countryCode='$countryCode')"
    }

    companion object CREATOR : Parcelable.Creator<ModelNewMain> {
        override fun createFromParcel(parcel: Parcel): ModelNewMain {
            return ModelNewMain(parcel)
        }

        override fun newArray(size: Int): Array<ModelNewMain?> {
            return arrayOfNulls(size)
        }
    }


}