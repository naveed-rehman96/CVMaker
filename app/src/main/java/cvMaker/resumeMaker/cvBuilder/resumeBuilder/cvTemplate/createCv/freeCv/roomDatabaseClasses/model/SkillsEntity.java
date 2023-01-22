package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "skillTable")
public class SkillsEntity {

    @PrimaryKey(autoGenerate = true)
    int skillId;
    String uid ="";
    String skillTitle = "";


    public SkillsEntity() {
    }

    @Ignore
    public SkillsEntity(String Uid, String skillTitle) {
        this.uid = Uid;
        this.skillTitle = skillTitle;
    }

    public SkillsEntity(String Uid, int skillId, String skillTitle) {
        this.uid = Uid;
        this.skillId = skillId;
        this.skillTitle = skillTitle;
    }

    public String getId() {
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }
}
