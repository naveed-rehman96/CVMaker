package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass.ExperienceModelClass;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass.ProjectModelClass;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass.QualificationModelClass;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass.TechnicalSkillModelClass;

import java.util.ArrayList;

@Entity(tableName = "cv_table")
public class CVModelEntity {

    @PrimaryKey(autoGenerate = true)
    int auto_id;
    String id = "";
    String imagePath = "";
    String fullName ="";
    String fatherName ="";
    String phoneNum = "";
    String emailID ="";
    String dateOfBirth= "";
    String cnic= "";
    String nationality= "";
    String address= "";
    String gender= "";
    String maritalStatus= "";
    String fileName= "";
    String objective= "";
    String interest= "";
    String language= "";
    String reference= "";
    String awards= "";
    String fullNumber= "";
    String countryCode= "";

    @Ignore
    public CVModelEntity() {
    }

    public CVModelEntity(String id, String imagePath, String fullName, String fatherName, String phoneNum, String emailID, String dateOfBirth, String cnic, String nationality, String address, String gender, String maritalStatus, String fileName, String objective, String interest, String language, String reference, String awards, String fullNumber, String countryCode) {
        this.id = id;
        this.imagePath = imagePath;
        this.fullName = fullName;
        this.fatherName = fatherName;
        this.phoneNum = phoneNum;
        this.emailID = emailID;
        this.dateOfBirth = dateOfBirth;
        this.cnic = cnic;
        this.nationality = nationality;
        this.address = address;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.fileName = fileName;
        this.objective = objective;
        this.interest = interest;
        this.language = language;
        this.reference = reference;
        this.awards = awards;
        this.fullNumber = fullNumber;
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public void setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}