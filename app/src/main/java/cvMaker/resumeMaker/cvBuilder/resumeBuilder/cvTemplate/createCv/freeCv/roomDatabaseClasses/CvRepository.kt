package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses

import androidx.lifecycle.LiveData
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.CvDao
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CvRepository(var cvDao: CvDao) {
    val allCVs: LiveData<List<CVModelEntity>>? = null
    val allProjects: LiveData<List<ProjectsEntity>>? = null
    val allExperiences: LiveData<List<ExperienceEntity>>? = null
    val allQualifications: List<QualificationEntity>? = null
    val allSkills: LiveData<List<SkillsEntity>>? = null
    val currentCV: LiveData<CVModelEntity>? = null

    fun getAllCVList(): Flow<MutableList<CVModelEntity>> = flow {
        emit(cvDao.fetchAllRecords())
    }.flowOn(Dispatchers.Default)

    fun insert(data: CVModelEntity?): Long {
        return cvDao.insertCvData(data)
    }
}
