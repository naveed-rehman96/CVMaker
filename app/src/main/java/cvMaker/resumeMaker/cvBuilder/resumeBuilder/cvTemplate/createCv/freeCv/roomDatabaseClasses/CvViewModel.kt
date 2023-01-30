package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.states.CvStates
import kotlinx.coroutines.flow.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.launch

class CvViewModel(val cvRepository: CvRepository) : ViewModel() {

    fun insert(data: CVModelEntity?): LongArray {
        val rowID = LongArray(1)
        AppDatabase.databaseWriteExecutor.execute { rowID[0] = cvRepository.insert(data) }
        return rowID
    }

    private val _allCvListStateFlow = MutableStateFlow<CvStates>(CvStates.Empty)
    val allCvListStateFlow: StateFlow<CvStates> = _allCvListStateFlow

    fun getAllCvList() = viewModelScope.launch {
        cvRepository.getAllCVList().onStart {
            _allCvListStateFlow.value = CvStates.Loading
        }.catch {
            _allCvListStateFlow.value = CvStates.Failure(it)
        }.onEach {
            _allCvListStateFlow.value = CvStates.Empty
        }.collect {
            _allCvListStateFlow.value = CvStates.Success(it)
        }
    }

    fun getCvById(id: String): Flow<CVModelEntity> {
        return cvRepository.getCvById(id)
    }
    fun getExperienceById(id: String): Flow<List<ExperienceEntity>> {
        return cvRepository.getExperienceById(id)
    }
    fun getQualification(id: String): Flow<List<QualificationEntity>> {
        return cvRepository.getQualificationById(id)
    }
    fun getProjects(id: String): Flow<List<ProjectsEntity>> {
        return cvRepository.getProjectsById(id)
    }
    fun getSkills(id: String): Flow<List<SkillsEntity>> {
        return cvRepository.getSkillsById(id)
    }
}
