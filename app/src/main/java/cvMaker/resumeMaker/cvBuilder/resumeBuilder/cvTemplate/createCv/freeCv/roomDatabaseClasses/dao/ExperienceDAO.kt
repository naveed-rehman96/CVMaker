package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.dao

import androidx.room.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ExperienceEntity
import kotlin.collections.ArrayList

@Dao
interface ExperienceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExp(experienceEntity: ExperienceEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExp(experienceEntity: ArrayList<ExperienceEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun UpdateExp(experienceEntity: ArrayList<ExperienceEntity>)

    @Delete
    fun deleteExp(experienceEntity: ExperienceEntity?)

    @Query("DELETE From experienceTable where userId = :uid")
    fun deleteExperience(uid: Array<out Int?>): Int


    @Query("Select * From experienceTable where userId = :uid")
    fun deleteAllExperience(uid: String): Int

    @Query("SELECT * FROM experienceTable WHERE userId = :uid")
    fun getAllExperience(uid: String): List<ExperienceEntity>
}