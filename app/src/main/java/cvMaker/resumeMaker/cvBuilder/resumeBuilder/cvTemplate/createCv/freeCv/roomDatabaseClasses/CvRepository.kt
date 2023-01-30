package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.CvDao
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.ExperienceDAO
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.ProjectsDao
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.QualificationDAO
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.SkillsDao
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CvRepository(
    var cvDao: CvDao,
    var qualificationDAO: QualificationDAO,
    var experienceDAO: ExperienceDAO,
    var projectsDao: ProjectsDao,
    var skillsDao: SkillsDao
) {

    fun getAllCVList(): Flow<MutableList<CVModelEntity>> = flow {
        emit(cvDao.fetchAllRecords())
    }.flowOn(Dispatchers.Default)

    fun insert(data: CVModelEntity?): Long {
        return cvDao.insertCvData(data)
    }

    fun getCvById(id: String): Flow<CVModelEntity> = flow {
        emit(cvDao.getCVbyId(id))
    }.flowOn(Dispatchers.IO)

    fun getExperienceById(id: String): Flow<List<ExperienceEntity>> = flow {
        emit(experienceDAO.getAllExperience(id))
    }.flowOn(Dispatchers.IO)

    fun getQualificationById(id: String): Flow<List<QualificationEntity>> = flow {
        emit(qualificationDAO.getAllQualification(id))
    }.flowOn(Dispatchers.IO)

    fun getSkillsById(id: String): Flow<List<SkillsEntity>> = flow {
        emit(skillsDao.getAllSkills(id))
    }.flowOn(Dispatchers.IO)

    fun getProjectsById(id: String): Flow<List<ProjectsEntity>> = flow {
        emit(projectsDao.getAllProject(id))
    }.flowOn(Dispatchers.IO)
}
