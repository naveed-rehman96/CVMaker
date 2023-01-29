package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "projectTable")
class ProjectsEntity {
    @PrimaryKey(autoGenerate = true)
    var projectID = 0
    var projectTitle = ""
    var userId = ""

    @Ignore
    constructor() {
    }

    @Ignore
    constructor(projectTitle: String, userId: String) {
        this.projectTitle = projectTitle
        this.userId = userId
    }

    constructor(projectID: Int, projectTitle: String, userId: String) {
        this.projectID = projectID
        this.projectTitle = projectTitle
        this.userId = userId
    }
}