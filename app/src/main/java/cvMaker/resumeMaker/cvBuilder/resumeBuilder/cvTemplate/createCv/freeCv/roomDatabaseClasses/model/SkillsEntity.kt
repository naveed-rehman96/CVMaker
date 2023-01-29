package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "skillTable")
class SkillsEntity {
    @PrimaryKey(autoGenerate = true)
    var skillId = 0
    var id = ""
    var skillTitle = ""

    constructor() {}

    @Ignore
    constructor(Uid: String, skillTitle: String) {
        id = Uid
        this.skillTitle = skillTitle
    }

    constructor(Uid: String, skillId: Int, skillTitle: String) {
        id = Uid
        this.skillId = skillId
        this.skillTitle = skillTitle
    }
}
