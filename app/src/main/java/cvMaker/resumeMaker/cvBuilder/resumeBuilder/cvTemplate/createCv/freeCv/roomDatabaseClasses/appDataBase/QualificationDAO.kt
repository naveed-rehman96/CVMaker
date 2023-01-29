package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase

import androidx.room.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.QualificationEntity
import java.util.*

@Dao
interface QualificationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQua(qualificationEntity: QualificationEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllQua(qualificationEntity: ArrayList<QualificationEntity>)

    @Delete
    fun deleteQua(qualificationEntity: QualificationEntity?)

    @Query("Select * From qualificationTable where userId = :uid")
    fun deleteAllQualification(uid: String): Int

    @Query("SELECT * FROM qualificationTable WHERE userId = :uid")
    fun getAllQualification(uid: String): List<QualificationEntity>


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun UpdateQualification(qualificationEntity: ArrayList<QualificationEntity>)

}