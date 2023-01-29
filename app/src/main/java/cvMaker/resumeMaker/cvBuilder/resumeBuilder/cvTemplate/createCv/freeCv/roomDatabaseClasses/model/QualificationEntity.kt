package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "qualificationTable")
class QualificationEntity {
    @PrimaryKey(autoGenerate = true)
    var quaID = 0
    var userID: String? = null
    var schoolName: String? = null
    var subject: String? = null
    var marks: String? = null
    var endDate: String? = null

    @Ignore
    constructor() {
    }

    @Ignore
    constructor(schoolName: String?, subject: String?, endDate: String?) {
        this.schoolName = schoolName
        this.subject = subject
        this.endDate = endDate
    }

    constructor(
        quaID: Int,
        userID: String?,
        schoolName: String?,
        subject: String?,
        marks: String?,
        endDate: String?
    ) {
        this.quaID = quaID
        this.userID = userID
        this.schoolName = schoolName
        this.subject = subject
        this.marks = marks
        this.endDate = endDate
    }
}