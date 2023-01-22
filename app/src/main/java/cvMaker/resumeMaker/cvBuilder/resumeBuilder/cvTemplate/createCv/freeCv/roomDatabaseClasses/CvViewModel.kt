package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.CVModelEntity

class CvViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val cvRepository: CvRepository
    var db: AppDatabase

    fun insert(data: CVModelEntity?): LongArray {
        val rowID = LongArray(1)
        AppDatabase.databaseWriteExecutor.execute { rowID[0] = cvRepository.insert(data) }
        return rowID
    }

    init {
        db = Room.databaseBuilder(application!!, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
        cvRepository = CvRepository(application)
    }
}