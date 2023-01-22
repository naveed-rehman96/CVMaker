package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ProjectsEntity;import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProjectsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(ProjectsEntity projectsEntity);

    @Delete
    void deleteProject(ProjectsEntity projectsEntity);

    @Query("SELECT * FROM projectTable WHERE userId = :uid")
    List<ProjectsEntity> getAllProject(String uid);

    @Query("DELETE FROM projectTable where userId = :uid")
    int deleteAllProject(String uid);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void Update(ArrayList<ProjectsEntity> projectsEntity);

}
