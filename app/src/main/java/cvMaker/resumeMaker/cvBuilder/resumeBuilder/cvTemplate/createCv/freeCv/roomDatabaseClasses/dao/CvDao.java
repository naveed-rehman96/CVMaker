package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity;

import java.util.List;

@Dao
public interface CvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCvData(CVModelEntity cvModelEntity);

    @Delete
    void deleteCvData(CVModelEntity cvModelEntity);

    @Query("SELECT * FROM CV_TABLE")
    List<CVModelEntity> getAllRecords();

    @Query("SELECT * FROM cv_table WHERE id = :id")
    CVModelEntity getCVbyId(String id);


    @Query("DELETE FROM cv_table WHERE id = :id")
    void deleteById(String id);


    //************************** Updating Value One by One *****************************

    @Query("UPDATE cv_table SET fullName = :fullName WHERE id =:id")
    void updateName(String fullName, String id);

    @Query("UPDATE cv_table SET fatherName = :fatherName WHERE id =:id")
    void updateFatherName(String fatherName, String id);

    @Query("UPDATE cv_table SET gender = :gender WHERE id =:id")
    void updateGender(String gender, String id);

    @Query("UPDATE cv_table SET imagePath = :imagePath WHERE id =:id")
    void updateImagePath(String imagePath, String id);

    @Query("UPDATE cv_table SET maritalStatus = :maritalStatus WHERE id =:id")
    void updateMaritalStatus(String maritalStatus, String id);

    @Query("UPDATE cv_table SET fullNumber = :fullNumber WHERE id =:id")
    void updateFullNumber(String fullNumber, String id);

    @Query("UPDATE cv_table SET emailID = :emailID WHERE id =:id")
    void updateEmailID(String emailID, String id);

    @Query("UPDATE cv_table SET dateOfBirth = :dateOfBirth WHERE id =:id")
    void updateDateOfBirth(String dateOfBirth, String id);

    @Query("UPDATE cv_table SET cnic = :cnic WHERE id =:id")
    void updateCnic(String cnic, String id);

    @Query("UPDATE cv_table SET nationality = :nationality WHERE id =:id")
    void updateNationality(String nationality, String id);

    @Query("UPDATE cv_table SET address = :address WHERE id =:id")
    void updateAddress(String address, String id);

    @Query("UPDATE cv_table SET objective = :objective WHERE id =:id")
    void updateObjective(String objective, String id);

    @Query("UPDATE cv_table SET awards = :awards WHERE id =:id")
    void updateAchievements(String awards, String id);

    @Query("UPDATE cv_table SET interest = :interest WHERE id =:id")
    void updateInterest(String interest, String id);

    @Query("UPDATE cv_table SET language = :language WHERE id =:id")
    void updateLanguage(String language, String id);

    @Query("UPDATE cv_table SET reference = :reference WHERE id =:id")
    void updateReference(String reference, String id);

    @Query("UPDATE cv_table SET countryCode = :countryCode WHERE id =:id")
    void updateCountryCode(String countryCode, String id);

    @Query("UPDATE cv_table SET phoneNum = :phoneNum WHERE id =:id")
    void updatePhone(String phoneNum, String id);



}
