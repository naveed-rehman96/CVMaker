package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "projectTable")
public class ProjectsEntity {

    @PrimaryKey(autoGenerate = true)
    int projectID;

    String projectTitle = "";

    String userId = "";

    @Ignore
    public ProjectsEntity() {
    }

    @Ignore
    public ProjectsEntity(String projectTitle, String userId) {
        this.projectTitle = projectTitle;
        this.userId = userId;
    }

    public ProjectsEntity(int projectID, String projectTitle, String userId) {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.userId = userId;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
