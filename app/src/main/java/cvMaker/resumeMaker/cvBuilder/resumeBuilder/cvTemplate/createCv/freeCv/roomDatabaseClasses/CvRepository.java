package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses;

import android.app.Application;

import androidx.lifecycle.LiveData;

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.CvDao;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ExperienceEntity;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.ProjectsEntity;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.QualificationEntity;
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.SkillsEntity;

import java.util.List;

public class CvRepository {

    private CvDao cvDao;
    private LiveData<List<CVModelEntity>> allCVs;
    private LiveData<List<ProjectsEntity>> allProjects;
    private LiveData<List<ExperienceEntity>> allExperiences;
    private List<QualificationEntity> allQualification;
    private LiveData<List<SkillsEntity>> allSkills;
    private LiveData<CVModelEntity> currentCV;

    TinyDB tinyDB;

    public CvRepository(Application application) {

        AppDatabase appDatabase = AppDatabase.getInstance(application);


        tinyDB = new TinyDB(application);

        cvDao = appDatabase.cvDao();

    }

    public CvDao getCvDao() {
        return cvDao;
    }

    public LiveData<List<CVModelEntity>> getAllCVs() {
        return allCVs;
    }

    public List<QualificationEntity> getAllQualifications() {
        return allQualification;
    }

    public LiveData<List<SkillsEntity>> getAllSkills() {
        return allSkills;
    }

    public LiveData<CVModelEntity> getCurrentCV() {
        return currentCV;
    }


    public LiveData<List<ProjectsEntity>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<ExperienceEntity>> getAllExperiences() {
        return allExperiences;
    }

    public List<QualificationEntity> getAllQualification() {
        return allQualification;
    }

    public long insert(CVModelEntity data) {
        return cvDao.insertCvData(data);
    }

}
