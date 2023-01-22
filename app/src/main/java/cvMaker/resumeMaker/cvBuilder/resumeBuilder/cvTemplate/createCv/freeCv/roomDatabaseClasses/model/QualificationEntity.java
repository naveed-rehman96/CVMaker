package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "qualificationTable")
public class QualificationEntity {

    @PrimaryKey(autoGenerate = true)
    int quaID;
    String userID;
    String schoolName;
    String subject;
    String marks;
    String endDate;

    @Ignore
    public QualificationEntity() {
    }

    @Ignore
    public QualificationEntity(String schoolName, String subject, String endDate) {
        this.schoolName = schoolName;
        this.subject = subject;
        this.endDate = endDate;
    }

    public QualificationEntity(int quaID, String userID, String schoolName, String subject, String marks, String endDate) {
        this.quaID = quaID;
        this.userID = userID;
        this.schoolName = schoolName;
        this.subject = subject;
        this.marks = marks;
        this.endDate = endDate;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public int getQuaID() {
        return quaID;
    }

    public void setQuaID(int quaID) {
        this.quaID = quaID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
