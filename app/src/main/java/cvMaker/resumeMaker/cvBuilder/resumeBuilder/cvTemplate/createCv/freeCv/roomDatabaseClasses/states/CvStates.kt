package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.states

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity

sealed class CvStates {

    object Empty : CvStates()
    object Loading : CvStates()
    class Failure(val error: Throwable) : CvStates()
    class Success(var response: MutableList<CVModelEntity>) :CvStates()
}
