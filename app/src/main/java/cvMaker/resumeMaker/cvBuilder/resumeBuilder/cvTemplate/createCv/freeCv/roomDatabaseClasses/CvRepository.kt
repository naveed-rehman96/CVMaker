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


    fun updateImagePath(value : String , id: String){
        cvDao.updateImagePath(value,id)
    }
    fun updateName(value : String , id: String){
        cvDao.updateName(value,id)
    }
    fun updateFatherName(value : String , id: String){
        cvDao.updateFatherName(value,id)
    }
    fun updateGender(value : String , id: String){
        cvDao.updateGender(value,id)
    }
    fun updateMaritalStatus(value : String , id: String){
        cvDao.updateMaritalStatus(value,id)
    }
    fun updatePhone(value : String , id: String){
        cvDao.updatePhone(value,id)
    }
    fun updateEmailID(value : String , id: String){
        cvDao.updateEmailID(value,id)
    }
    fun updateFullNumber(value : String , id: String){
        cvDao.updateFullNumber(value,id)
    }
    fun updateCountryCode(value : String , id: String){
        cvDao.updateCountryCode(value,id)
    }
    fun updateDateOfBirth(value : String , id: String){
        cvDao.updateDateOfBirth(value,id)
    }
    fun updateCnic(value : String , id: String){
        cvDao.updateCnic(value,id)
    }
    fun updateNationality(value : String , id: String){
        cvDao.updateNationality(value,id)
    }
    fun updateAddress(value : String , id: String){
        cvDao.updateAddress(value,id)
    }

    fun insertObjective(objectiveTxt: String, ID: String?) {
        cvDao.updateObjective(objectiveTxt,ID)
    }

}
