package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.appDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.* // ktlint-disable no-wildcard-imports
import java.util.concurrent.Executors

@Database(
    entities = [SkillsEntity::class, QualificationEntity::class, CVModelEntity::class, ExperienceEntity::class, ProjectsEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cvDao(): CvDao
    abstract fun skillsDao(): SkillsDao
    abstract fun projectsDao(): ProjectsDao
    abstract fun experienceDAO(): ExperienceDAO
    abstract fun qualificationDAO(): QualificationDAO

    companion object {

        fun provideCvDao(database: AppDatabase): CvDao {
            return database.cvDao()
        }

        const val DATABASE_NAME = "ResumeBuilder.db"

        @Volatile
        var instance: AppDatabase? = null
        val lock = Any()
        val databaseWriteExecutor = Executors.newFixedThreadPool(4)

        @JvmStatic
        @Synchronized
        fun getDatabaseInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build()
            }
            return instance
        }

        private val roomCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}
