package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule // ktlint-disable filename

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvRepository
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.CvViewModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase.AppDatabase.Companion.getDatabaseInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { getDatabaseInstance(get()) }
    single { AppDatabase.provideCvDao(database = getDatabaseInstance(get())!!) }
    single { AppDatabase.provideSkillsDao(database = getDatabaseInstance(get())!!) }
    single { AppDatabase.provideExperienceDAO(database = getDatabaseInstance(get())!!) }
    single { AppDatabase.provideProjectsDao(database = getDatabaseInstance(get())!!) }
    single { AppDatabase.provideQualificationDAO(database = getDatabaseInstance(get())!!) }
    single { CvRepository(cvDao = get(), skillsDao = get(), projectsDao = get(), experienceDAO = get(), qualificationDAO = get()) }

    viewModel { CvViewModel(cvRepository = get()) }
}
