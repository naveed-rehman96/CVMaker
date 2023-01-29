package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity
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
}
