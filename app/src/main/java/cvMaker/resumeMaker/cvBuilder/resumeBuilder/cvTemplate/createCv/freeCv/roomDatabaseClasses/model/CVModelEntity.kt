package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cv_table")
class CVModelEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var auto_id = 0
    var id = ""
    var imagePath = ""
    var fullName = ""
    var fatherName = ""
    var phoneNum = ""
    var emailID = ""
    var dateOfBirth = ""
    var cnic = ""
    var nationality = ""
    var address = ""
    var gender = ""
    var maritalStatus = ""
    var fileName = ""
    var objective = ""
    var interest = ""
    var language = ""
    var reference = ""
    var awards = ""
    var fullNumber = ""
    var countryCode = ""
    var isSelected = false

    @Ignore
    constructor() {
    }

    constructor(
        id: String,
        imagePath: String,
        fullName: String,
        fatherName: String,
        phoneNum: String,
        emailID: String,
        dateOfBirth: String,
        cnic: String,
        nationality: String,
        address: String,
        gender: String,
        maritalStatus: String,
        fileName: String,
        objective: String,
        interest: String,
        language: String,
        reference: String,
        awards: String,
        fullNumber: String,
        countryCode: String
    ) {
        this.id = id
        this.imagePath = imagePath
        this.fullName = fullName
        this.fatherName = fatherName
        this.phoneNum = phoneNum
        this.emailID = emailID
        this.dateOfBirth = dateOfBirth
        this.cnic = cnic
        this.nationality = nationality
        this.address = address
        this.gender = gender
        this.maritalStatus = maritalStatus
        this.fileName = fileName
        this.objective = objective
        this.interest = interest
        this.language = language
        this.reference = reference
        this.awards = awards
        this.fullNumber = fullNumber
        this.countryCode = countryCode
    }
}
