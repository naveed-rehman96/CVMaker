package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.SkillsEntity;import java.util.ArrayList;
import java.util.List;

@Dao
public interface SkillsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSkill(SkillsEntity skillsEntity);

    @Delete
    void deleteSkill(SkillsEntity skillsEntity);

    @Query("SELECT * FROM SKILLTABLE WHERE uid = :uid")
    List<SkillsEntity> getAllSkills(String uid);

    @Query("DELETE FROM SKILLTABLE where uid = :uid")
    int deleteAllSkills(String uid);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void Update(ArrayList<SkillsEntity> projectsEntity);

}
