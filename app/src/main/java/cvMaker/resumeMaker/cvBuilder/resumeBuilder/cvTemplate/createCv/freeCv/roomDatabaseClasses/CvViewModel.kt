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

    fun updateImagePath(value : String , id: String){
        cvRepository.updateImagePath(value,id)
    }
    fun updateName(value : String , id: String){
        cvRepository.updateName(value,id)

    }
    fun updateFatherName(value : String , id: String){
        cvRepository.updateFatherName(value,id)

    }
    fun updateGender(value : String , id: String){
        cvRepository.updateGender(value,id)

    }
    fun updateMaritalStatus(value : String , id: String){
        cvRepository.updateMaritalStatus(value,id)

    }
    fun updatePhone(value : String , id: String){
        cvRepository.updatePhone(value,id)

    }
    fun updateEmailID(value : String , id: String){
        cvRepository.updateEmailID(value,id)

    }
    fun updateFullNumber(value : String , id: String){
        cvRepository.updateFullNumber(value,id)

    }
    fun updateCountryCode(value : String , id: String){
        cvRepository.updateCountryCode(value,id)

    }
    fun updateDateOfBirth(value : String , id: String){
        cvRepository.updateDateOfBirth(value,id)

    }
    fun updateCnic(value : String , id: String){
        cvRepository.updateCnic(value,id)

    }
    fun updateNationality(value : String , id: String){
        cvRepository.updateNationality(value,id)

    }
    fun updateAddress(value : String , id: String){
        cvRepository.updateAddress(value,id)

    }

    fun insertObjective(objectiveTxt: String, string: String?) {
        cvRepository.insertObjective(objectiveTxt,string)
    }
}
