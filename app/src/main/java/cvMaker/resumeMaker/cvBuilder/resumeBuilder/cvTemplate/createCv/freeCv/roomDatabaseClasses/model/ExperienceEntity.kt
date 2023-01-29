package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experienceTable")
data class ExperienceEntity(
    var companyName: String? = null,
    var designation: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var userId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var expId = 0

    override fun toString(): String {
        return "ExperienceEntity{" +
            "companyName='" + companyName + '\'' +
            ", designation='" + designation + '\'' +
            ", startDate='" + startDate + '\'' +
            ", endDate='" + endDate + '\'' +
            ", userId='" + userId + '\'' +
            ", expId=" + expId +
            '}'
    }
}
