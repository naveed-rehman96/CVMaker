package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses

import android.os.Parcel
import android.os.Parcelable

class ModelCoverLetter() : Parcelable {

    var cid: String = ""
    var cfilename:  String= ""
    var senderName:  String= ""
    var senderDesignation:  String= ""
    var senderAddress: String= ""
    var senderPhone:  String= ""
    var senderEmail:  String= ""
    var receiverName:  String= ""
    var receiverDesignation:  String= ""
    var receiverAddress: String= ""
    var date:  String= ""
    var description:  String= ""
    var senderCompany:  String= ""
    var senderSubject:  String= ""
    var isSelected: Boolean = false

    constructor(parcel: Parcel) : this() {
        cid = parcel.readString().toString()
        cfilename = parcel.readString().toString()
        senderName = parcel.readString().toString()
        senderDesignation = parcel.readString().toString()
        senderAddress = parcel.readString().toString()
        senderPhone = parcel.readString().toString()
        senderEmail = parcel.readString().toString()
        receiverName = parcel.readString().toString()
        receiverDesignation = parcel.readString().toString()
        receiverAddress = parcel.readString().toString()
        date = parcel.readString().toString()
        description = parcel.readString().toString()
        senderCompany = parcel.readString().toString()
        senderSubject = parcel.readString().toString()
        isSelected = parcel.readByte() != 0.toByte()
    }


    constructor(
        cid: String,
        cfilename: String,
        senderName: String,
        senderDesignation: String,
        senderAddress: String,
        senderPhone: String,
        senderEmail: String,
        receiverName: String,
        receiverDesignation: String,
        receiverAddress: String,
        date: String,
        description: String,
        senderCompany: String,
        senderSubject: String

    ) : this() {
        this.cid = cid
        this.cfilename = cfilename
        this.senderName = senderName
        this.senderDesignation = senderDesignation
        this.senderAddress = senderAddress
        this.senderPhone = senderPhone
        this.senderEmail = senderEmail
        this.receiverName = receiverName
        this.receiverDesignation = receiverDesignation
        this.receiverAddress = receiverAddress
        this.date = date
        this.description = description
        this.senderCompany = senderCompany
        this.senderSubject = senderSubject
    }

    @JvmName("isSelected1")
    fun isSelected(): Boolean{
        return isSelected
    }

    @JvmName("setSelected1")
    fun setSelected(selected: Boolean){
         this.isSelected = selected
    }


    @JvmName("getLanguage1")
    fun getCid(): String {
        return cid
    }
    @JvmName("setLanguage1")
    fun setCid(cid: String){
        this.cid = cid
    }

    @JvmName("senderCompany")
    fun getSenderCompany(): String {
        return senderCompany
    }
    @JvmName("senderCompany")
    fun setSenderCompany(senderCompany: String){
        this.senderCompany = senderCompany
    }
    @JvmName("senderSubject")
    fun getSenderSubject(): String {
        return senderSubject
    }
    @JvmName("senderSubject")
    fun setSenderSubject(senderSubject: String){
        this.senderSubject = senderSubject
    }



    @JvmName("getImageUri1")
    fun getCFileName(): String {
        return cfilename
    }
    @JvmName("setImageUri1")
    fun setCFileName(cfilename: String?) {
        if (cfilename != null) {
            this.cfilename = cfilename
        }
    }
    @JvmName("getFullName1")
    fun getSenderName(): String{
        return senderName
    }
    @JvmName("setFullName1")
    fun setSenderName(senderName: String) {
        this.senderName = senderName
    }
    @JvmName("getFatherName1")
    fun getSenderDesignation(): String {
        return senderDesignation
    }
    @JvmName("setFatherName1")
    fun setSenderDesignation(senderDesignation: String) {
        this.senderDesignation = senderDesignation
    }
    @JvmName("getPhoneNum1")
    fun getSenderAddress(): String {
        return senderAddress
    }
    @JvmName("setPhoneNum1")
    fun setSenderAddress(senderAddress: String) {
        this.senderAddress = senderAddress
    }
    @JvmName("getEmailId1")
    fun getSenderPhone(): String {
        return senderPhone
    }
    @JvmName("setEmailId1")
    fun setSenderPhone(senderPhone: String) {
        this.senderPhone = senderPhone
    }
    @JvmName("getDateOFBirth1")
    fun getSenderEmail(): String {
        return senderEmail
    }
    @JvmName("setDateOFBirth1")
    fun setSenderEmail(senderEmail: String) {
        this.senderEmail = senderEmail
    }
    @JvmName("getCNIC1")
    fun getReceiverName(): String {
        return receiverName
    }
    @JvmName("setCNIC1")
    fun setReceiverName(receiverName: String) {
        this.receiverName = receiverName
    }
    @JvmName("getNationality1")
    fun getReceiverDesignation(): String {
        return receiverDesignation
    }
    @JvmName("setNationality1")
    fun setReceiverDesignation(receiverDesignation: String) {
        this.receiverDesignation = receiverDesignation
    }
    @JvmName("ReceiverAddress1")
    fun getReceiverAddress(): String {
        return receiverAddress
    }
    @JvmName("ReceiverAddress1")
    fun setReceiverAddress(receiverAddress: String) {
        this.receiverAddress = receiverAddress
    }
    @JvmName("getFileName1")
    fun getDate(): String {
        return date
    }
    @JvmName("setFileName1")
    fun setDate(date: String) {
        this.date = date
    }
    @JvmName("getObjective1")
    fun getDescription(): String {
        return description
    }
    @JvmName("setObjective1")
    fun setDescription(description: String) {
        this.description = description
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cid)
        parcel.writeString(cfilename)
        parcel.writeString(senderName)
        parcel.writeString(senderDesignation)
        parcel.writeString(senderAddress)
        parcel.writeString(senderPhone)
        parcel.writeString(senderEmail)
        parcel.writeString(receiverName)
        parcel.writeString(receiverDesignation)
        parcel.writeString(receiverAddress)
        parcel.writeString(date)
        parcel.writeString(description)
        parcel.writeString(senderCompany)
        parcel.writeString(senderSubject)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelCoverLetter> {
        override fun createFromParcel(parcel: Parcel): ModelCoverLetter {
            return ModelCoverLetter(parcel)
        }

        override fun newArray(size: Int): Array<ModelCoverLetter?> {
            return arrayOfNulls(size)
        }
    }


}