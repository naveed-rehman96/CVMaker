package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ExperienceModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelCoverLetter
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ModelMain
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.ProjectModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.QualificationModelClass
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models.TechnicalSkillModelClass

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CV_TABLE = (
            "CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER," +
                COLUMN_FILE_NAME + " TEXT ," +
                COLUMN_objective + " TEXT," +
                COLUMN_Nationality + " TEXT," +
                COLUMN_Cnic + " TEXT," +
                COLUMN_DOB + " TEXT," +
                COLUMN_emailID + " TEXT," +
                COLUMN_Phone + " TEXT," +
                COLUMN_FatherName + " TEXT," +
                COLUMN_PersonName + " TEXT," +
                COLUMN_imageURI + " TEXT," +
                COLUMN_Achievements + " TEXT," +
                COLUMN_Interests + " TEXT," +
                COLUMN_Language + " TEXT," +
                COLUMN_Reference + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COULMN_GENDER + " TEXT," +
                COULMN_MARTIALSTATUS + " TEXT," +
                COLUMN_CountryCode + " TEXT," +
                COLUMN_FullNumber + " TEXT" +
                ")"
            )

        val CREATE_QUALIFICATION_TABLE = (
            "CREATE TABLE " +
                TABLE_QUALIFICATION + "(" +
                COLUMN_ID + " TEXT," +
                COLUMN_QUA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DEGREE + " TEXT ," +
                COLUMN_SCHOOL + " TEXT," +
                COLUMN_ENDDATE + " TEXT," +
                COLUMN_MARKS + " TEXT" +
                ")"
            )

        val CREATE_EXPERIENCE_TABLE = (
            "CREATE TABLE " +
                TABLE_EXPERIENCE + "(" +
                COLUMN_EXP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ID + " TEXT," +
                COLUMN_CNAME + " TEXT ," +
                COLUMN_DESIG + " TEXT," +
                COLUMN_TO + " TEXT," +
                COLUMN_FROM + " TEXT" +
                ")"
            )

        val CREATE_TECHSKILL_TABLE = (
            "CREATE TABLE " +
                TABLE_TECHSKILL + "(" +
                COLUMN_SKILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ID + " TEXT," +
                COLUMN_SKILL + " TEXT" +
                ")"
            )

        val CREATE_PROJECT_TABLE = (
            "CREATE TABLE " +
                TABLE_PROJECT + "(" +
                COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ID + " TEXT," +
                COLUMN_PROJECT + " TEXT" +
                ")"
            )

        val CREATE_COVER_LETTER_TABLE = (
            "CREATE TABLE " +
                TABLE_COVER_LETTER + "(" +
                COLUMN_CID + " INTEGER," +
                COLUMN_CLNAME + " TEXT," +
                COLUMN_SEND_NAME + " TEXT," +
                COLUMN_SEND_COMPANY + " TEXT," +
                COLUMN_SEND_SUBJECT + " TEXT," +
                COLUMN_SEND_ADDRESS + " TEXT," +
                COLUMN_SEND_PHONE + " TEXT," +
                COLUMN_SEND_EMAIL + " TEXT," +
                COLUMN_SEND_DESIG + " TEXT," +
                COLUMN_REC_NAME + " TEXT," +
                COLUMN_REC_DESIG + " TEXT," +
                COLUMN_REC_ADDRESS + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT" +
                ")"
            )

        db.execSQL(CREATE_QUALIFICATION_TABLE)
        db.execSQL(CREATE_EXPERIENCE_TABLE)
        db.execSQL(CREATE_CV_TABLE)
        db.execSQL(CREATE_COVER_LETTER_TABLE)
        db.execSQL(CREATE_TECHSKILL_TABLE)
        db.execSQL(CREATE_PROJECT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUALIFICATION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPERIENCE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COVER_LETTER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TECHSKILL")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PROJECT")
        onCreate(db)
    }

    // -------------------------------  COVER LETTER FUNCTIONS -----------------------------------------------

    @SuppressLint("Range")
    fun fetchCoverLetterData(): ArrayList<ModelCoverLetter> {
        val db = this.readableDatabase
        val arrayList: ArrayList<ModelCoverLetter> = ArrayList()
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_COVER_LETTER", null)
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex(COLUMN_CID))
            val fileName = cursor.getString(cursor.getColumnIndex(COLUMN_CLNAME))
            val sName = cursor.getString(cursor.getColumnIndex(COLUMN_SEND_NAME))
            val subject = cursor.getString(cursor.getColumnIndex(COLUMN_SEND_SUBJECT))
            val rName = cursor.getString(cursor.getColumnIndex(COLUMN_REC_NAME))
            val model = ModelCoverLetter()
            model.setCFileName(fileName)
            model.setCid(id)
            model.setSenderName(sName)
            model.setSenderSubject(subject)
            model.setReceiverName(rName)
            arrayList.add(model)

            Log.e("DATA", "fetchCoverLetterData: $model")
        }
        return arrayList
    }

    fun deleteLetterRecord(id: String): Boolean {
        val db = this.writableDatabase
        return if (db.delete(TABLE_COVER_LETTER, "$COLUMN_ID = $id", null) > 0) {
            Toast.makeText(context, "Deleted Succesfully", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(context, "Failed To Delete", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun fetchLetterRecord(id: String): Cursor? {
        var db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_COVER_LETTER WHERE $COLUMN_CID=$id", null)
        cursor.moveToFirst()
        return cursor
    }

    fun addCoverLetterRecord(fileName1: String, id: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CLNAME, fileName1)
        values.put(COLUMN_CID, id)
        values.put(COLUMN_SEND_NAME, "")
        values.put(COLUMN_SEND_ADDRESS, "")
        values.put(COLUMN_SEND_DESIG, "")
        values.put(COLUMN_SEND_COMPANY, "")
        values.put(COLUMN_SEND_SUBJECT, "")
        values.put(COLUMN_SEND_EMAIL, "")
        values.put(COLUMN_SEND_PHONE, "")
        values.put(COLUMN_REC_ADDRESS, "")
        values.put(COLUMN_REC_DESIG, "")
        values.put(COLUMN_REC_NAME, "")
        values.put(COLUMN_DATE, "")
        values.put(COLUMN_DESCRIPTION, "")

        if (db.insert(TABLE_COVER_LETTER, null, values) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addLetterDate(id: String?, date: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_DATE, date)

        if (db.update(TABLE_COVER_LETTER, cv, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addSentInfo(
        id: String,
        company: String,
        subject: String,
        name: String,
        email: String,
        phone: String,
        designation: String,
        address: String
    ) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_SEND_NAME, name)
        cv.put(COLUMN_SEND_EMAIL, email)
        cv.put(COLUMN_SEND_COMPANY, company)
        cv.put(COLUMN_SEND_SUBJECT, subject)
        cv.put(COLUMN_SEND_PHONE, phone)
        cv.put(COLUMN_SEND_DESIG, designation)
        cv.put(COLUMN_SEND_ADDRESS, address)

        if (db.update(TABLE_COVER_LETTER, cv, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addSentToInfo(id: String, recName: String, recDesign: String, recAddress: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_REC_NAME, recName)
        cv.put(COLUMN_REC_DESIG, recDesign)
        cv.put(COLUMN_REC_ADDRESS, recAddress)

        if (db.update(TABLE_COVER_LETTER, cv, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addLetterDescription(id: String?, description: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_DESCRIPTION, description)

        if (db.update(TABLE_COVER_LETTER, cv, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    // -------------------------------    END OF COVER LETTER  -----------------------------------------------

    fun deleteExperienceRow(ID: String) {
        val db1 = this.writableDatabase
        db1.delete(TABLE_EXPERIENCE, "$COLUMN_EXP_ID=$ID", null)
        db1.rawQuery("DELETE FROM $TABLE_EXPERIENCE WHERE $COLUMN_EXP_ID LIKE '$ID'", null)
    }

    fun deleteQualificationRow(ID: String) {
        val db1 = this.writableDatabase
        db1.delete(TABLE_QUALIFICATION, "$COLUMN_QUA_ID=$ID", null)
        db1.rawQuery("DELETE FROM $TABLE_QUALIFICATION WHERE $COLUMN_QUA_ID LIKE '$ID'", null)
    }

    fun deleteTechSkillRow(ID: String) {
        val db1 = this.writableDatabase
        db1.delete(TABLE_TECHSKILL, "$COLUMN_SKILL_ID=$ID", null)
        db1.rawQuery("DELETE FROM $TABLE_TECHSKILL WHERE $COLUMN_SKILL_ID LIKE '$ID'", null)
    }
    fun deleteProjectRow(ID: String) {
        val db1 = this.writableDatabase
        db1.delete(TABLE_PROJECT, "$COLUMN_PROJECT_ID=$ID", null)
        db1.rawQuery("DELETE FROM $TABLE_PROJECT WHERE $COLUMN_PROJECT_ID LIKE '$ID'", null)
    }

    fun saveExperience(modelList: ArrayList<ExperienceModelClass>, uid: String) {
        val id: ArrayList<String> = ArrayList()
        val data = ContentValues()
        val db = this.writableDatabase
        val db1 = this.readableDatabase
        val mcursor = db1.rawQuery("SELECT COUNT(*) FROM $TABLE_EXPERIENCE", null)
        mcursor.moveToFirst()
        val count = mcursor.getInt(0)
        if (count > 0) {
            val cursor = db1.rawQuery("SELECT * FROM $TABLE_EXPERIENCE WHERE $COLUMN_ID=$uid", null)
            if (cursor.moveToFirst()) {
                do {
                    id.add(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_ID)))
                    Log.d(
                        "Cursor",
                        "saveExperience: ID" + cursor.getString(cursor.getColumnIndex(COLUMN_EXP_ID))
                    )
                } while (cursor.moveToNext())
                for (i in id) {
                    db1.delete(TABLE_EXPERIENCE, "$COLUMN_EXP_ID=$i", null)
                    db1.rawQuery("DELETE FROM $TABLE_EXPERIENCE WHERE $COLUMN_ID LIKE '$i'", null)
                    // db.rawQuery("DELETE FROM $TABLE_EXPERIENCE WHERE $COLUMN_EXP_ID='$i'", null)
                    Log.d("Cursor", "saveExperience: ID  $i")
                }
            }
        }
        for (model in modelList) {
            data.put(COLUMN_ID, uid)
            data.put(COLUMN_CNAME, model.getCompanyName())
            data.put(COLUMN_DESIG, model.getDesignation())
            data.put(COLUMN_TO, model.getToDate())
            data.put(COLUMN_FROM, model.getFromDate())

            if (db.insert(TABLE_EXPERIENCE, null, data) > 0) {
                Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveQualification(modelList: ArrayList<QualificationModelClass>, uid: String) {
        val id: ArrayList<String> = ArrayList()
        val data = ContentValues()
        val db = this.writableDatabase
        val db1 = this.readableDatabase
        val mcursor = db1.rawQuery("SELECT COUNT(*)TABLE_$TABLE_QUALIFICATION", null)
        mcursor.moveToFirst()
        val count = mcursor.getInt(0)
        if (count > 0) {
            val cursor =
                db1.rawQuery("SELECT * FROM $TABLE_QUALIFICATION WHERE $COLUMN_ID=$uid", null)
            if (cursor.moveToFirst()) {
                do {
                    id.add(cursor.getString(cursor.getColumnIndex(COLUMN_QUA_ID)))
                } while (cursor.moveToNext())
                for (i in id) {
                    db1.delete(TABLE_QUALIFICATION, "$COLUMN_QUA_ID=$i", null)
                    db1.rawQuery(
                        "DELETE FROM $TABLE_QUALIFICATION WHERE $COLUMN_ID LIKE '$i'",
                        null
                    )
                    // db.rawQuery("DELETE FROM $TABLE_EXPERIENCE WHERE $COLUMN_EXP_ID='$i'", null)
                    Log.d("Cursor", "saveQualification: ID  $i")
                }
            }
        }
        for (model in modelList) {
            data.put(COLUMN_ID, uid)
            data.put(COLUMN_SCHOOL, model.getSchoolName())
            data.put(COLUMN_DEGREE, model.getDegree())
            data.put(COLUMN_MARKS, model.getMarks())
            data.put(COLUMN_ENDDATE, model.getEndDate())

            if (db.insert(TABLE_QUALIFICATION, null, data) > 0) {
                Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Recycle")
    fun saveTechnicalSkill(modelList: ArrayList<TechnicalSkillModelClass>, uid: String) {
        val id: ArrayList<String> = ArrayList()
        val data = ContentValues()
        val db = this.writableDatabase
        val db1 = this.readableDatabase
        val mcursor = db1.rawQuery("SELECT COUNT(*)TABLE_$TABLE_TECHSKILL", null)
        mcursor.moveToFirst()
        val count = mcursor.getInt(0)
        if (count > 0) {
            val cursor =
                db1.rawQuery("SELECT * FROM $TABLE_TECHSKILL WHERE $COLUMN_ID=$uid", null)
            if (cursor.moveToFirst()) {
                do {
                    id.add(cursor.getString(cursor.getColumnIndex(COLUMN_SKILL_ID)))
                } while (cursor.moveToNext())
                for (i in id) {
                    db1.delete(TABLE_TECHSKILL, "$COLUMN_SKILL_ID=$i", null)
                    db1.rawQuery(
                        "DELETE FROM $TABLE_TECHSKILL WHERE $COLUMN_ID LIKE '$i'",
                        null
                    )
                    Log.d("Cursor", "saveQualification: ID  $i")
                }
            }
        }
        for (model in modelList) {
            data.put(COLUMN_ID, uid)
            data.put(COLUMN_SKILL, model.techSkills)

            if (db.insert(TABLE_TECHSKILL, null, data) > 0) {
                Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Recycle")
    fun saveProject(modelList: ArrayList<ProjectModelClass>, uid: String) {
        val id: ArrayList<String> = ArrayList()
        val data = ContentValues()
        val db = this.writableDatabase
        val db1 = this.readableDatabase
        val mcursor = db1.rawQuery("SELECT COUNT(*)TABLE_$TABLE_PROJECT", null)
        mcursor.moveToFirst()
        val count = mcursor.getInt(0)
        if (count > 0) {
            val cursor =
                db1.rawQuery("SELECT * FROM $TABLE_PROJECT WHERE $COLUMN_ID=$uid", null)
            if (cursor.moveToFirst()) {
                do {
                    id.add(cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_ID)))
                } while (cursor.moveToNext())
                for (i in id) {
                    db1.delete(TABLE_PROJECT, "$COLUMN_PROJECT_ID=$i", null)
                    db1.rawQuery(
                        "DELETE FROM $TABLE_PROJECT WHERE $COLUMN_ID LIKE '$i'",
                        null
                    )
                    Log.d("Cursor", "saveQualification: ID  $i")
                }
            }
        }
        for (model in modelList) {
            data.put(COLUMN_ID, uid)
            data.put(COLUMN_PROJECT, model.projectTitle)

            if (db.insert(TABLE_PROJECT, null, data) > 0) {
                Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    fun getAllExperience(id: String): ArrayList<ExperienceModelClass> {
        val modelExperienceList: ArrayList<ExperienceModelClass> = ArrayList()
        val db1 = this.readableDatabase
        val cursor = db1.rawQuery("SELECT * FROM $TABLE_EXPERIENCE WHERE $COLUMN_ID=$id", null)
        if (cursor.moveToFirst()) {
            do {
                val object1: ExperienceModelClass = ExperienceModelClass()
                object1.setFromDate(cursor.getString(cursor.getColumnIndex(COLUMN_FROM)))
                object1.setToDate(cursor.getString(cursor.getColumnIndex(COLUMN_TO)))
                object1.setCompanyName(cursor.getString(cursor.getColumnIndex(COLUMN_CNAME)))
                object1.setDesignation(cursor.getString(cursor.getColumnIndex(COLUMN_DESIG)))
                object1.setExpId(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_ID)))

                modelExperienceList.add(object1)
            } while (cursor.moveToNext())
        }

        return modelExperienceList
    }

    fun getAllQualification(id: String): ArrayList<QualificationModelClass> {
        val modelQualificationList: ArrayList<QualificationModelClass> = ArrayList()
        val db1 = this.readableDatabase
        val cursor = db1.rawQuery("SELECT * FROM $TABLE_QUALIFICATION WHERE $COLUMN_ID=$id", null)
        if (cursor.moveToFirst()) {
            do {
                val object1 = QualificationModelClass()
                object1.setMarks(cursor.getString(cursor.getColumnIndex(COLUMN_MARKS)))
                object1.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_ENDDATE)))
                object1.setDegree(cursor.getString(cursor.getColumnIndex(COLUMN_DEGREE)))
                object1.setSchoolName(cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL)))
                object1.setQuaId(cursor.getString(cursor.getColumnIndex(COLUMN_QUA_ID)))

                modelQualificationList.add(object1)
            } while (cursor.moveToNext())
        }

        return modelQualificationList
    }

    fun getAllTechSkills(id: String): ArrayList<TechnicalSkillModelClass> {
        val modelQualificationList: ArrayList<TechnicalSkillModelClass> = ArrayList()
        val db1 = this.readableDatabase
        val cursor = db1.rawQuery("SELECT * FROM $TABLE_TECHSKILL WHERE $COLUMN_ID=$id", null)
        if (cursor.moveToFirst()) {
            do {
                val object1 = TechnicalSkillModelClass()
                object1.techSkills = (cursor.getString(cursor.getColumnIndex(COLUMN_SKILL)))
                object1.skillId = (cursor.getString(cursor.getColumnIndex(COLUMN_SKILL_ID)))

                modelQualificationList.add(object1)
            } while (cursor.moveToNext())
        }

        return modelQualificationList
    }

    fun getAllProjects(id: String): ArrayList<ProjectModelClass> {
        val modelQualificationList: ArrayList<ProjectModelClass> = ArrayList()
        val db1 = this.readableDatabase
        val cursor = db1.rawQuery("SELECT * FROM $TABLE_PROJECT WHERE $COLUMN_ID=$id", null)
        if (cursor.moveToFirst()) {
            do {
                val object1 = ProjectModelClass()
                object1.projectTitle = (cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT)))
                object1.projectId = (cursor.getString(cursor.getColumnIndex(COLUMN_PROJECT_ID)))

                modelQualificationList.add(object1)
            } while (cursor.moveToNext())
        }

        return modelQualificationList
    }

    fun deleteEntireRecord(id: String): Boolean {
        val db = this.writableDatabase
        return if (db.delete(TABLE_NAME, "$COLUMN_ID = $id", null) > 0) {
            Toast.makeText(context, "Deleted Succesfully", Toast.LENGTH_SHORT).show()
            db.rawQuery("DELETE FROM $TABLE_EXPERIENCE WHERE $COLUMN_ID LIKE $id", null)
            true
        } else {
            Toast.makeText(context, "Failed To Delete", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun getRecord(id: String): Cursor? {
        var db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE ID=$id", null)
        cursor.moveToFirst()
        db.close()
        return cursor
    }

    fun saveIndividualInfo(columnName: String, value: String, id: String) {
        val data = ContentValues()
        val db = this.writableDatabase
        data.put(columnName, value)
        if (db.update(TABLE_NAME, data, "$COLUMN_ID=$id", null) > 0) {
            Log.e("saveIndividualInfo", "$columnName----$value")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateAchievements(achievements: String, id: String) {
        val values = ContentValues()
        values.put(COLUMN_Achievements, achievements)
        val db = this.writableDatabase
        if (db.update(TABLE_NAME, values, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateInterest(interest: String, id: String) {
        val values = ContentValues()
        values.put(COLUMN_Interests, interest)
        val db = this.writableDatabase
        if (db.update(TABLE_NAME, values, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateLanguage(language: String, id: String) {
        val values = ContentValues()
        values.put(COLUMN_Language, language)
        val db = this.writableDatabase
        if (db.update(TABLE_NAME, values, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateQualification(expId: String, model: QualificationModelClass) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_SCHOOL, model.getSchoolName())
        values.put(COLUMN_MARKS, model.getMarks())
        values.put(COLUMN_ENDDATE, model.getEndDate())

        if (db.update(TABLE_QUALIFICATION, values, "$COLUMN_QUA_ID=$expId", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        }
    }

    fun updateTechSkills(expId: String, skill: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_SKILL, skill)

        if (db.update(TABLE_TECHSKILL, values, "$COLUMN_SKILL_ID=$expId", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        }
    }
    fun updateProjects(expId: String, skill: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PROJECT, skill)

        if (db.update(TABLE_PROJECT, values, "$COLUMN_PROJECT_ID=$expId", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        }
    }

    fun updateExperience(expId: String, model: ExperienceModelClass) {
        val values = ContentValues()
        values.put(COLUMN_DESIG, model.getDesignation())
        values.put(COLUMN_CNAME, model.getCompanyName())
        values.put(COLUMN_TO, model.getToDate())
        values.put(COLUMN_FROM, model.getFromDate())
        val db = this.writableDatabase
        if (db.update(TABLE_EXPERIENCE, values, "$COLUMN_EXP_ID=$expId", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        }
    }

    fun updateObjective(value: String, id: String) {
        val values = ContentValues()
        values.put(COLUMN_objective, value)
        val db = this.writableDatabase
        if (db.update(TABLE_NAME, values, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateReference(value: String, id: String) {
        val values = ContentValues()
        values.put(COLUMN_Reference, value)
        val db = this.writableDatabase
        if (db.update(TABLE_NAME, values, "$COLUMN_ID=$id", null) > 0) {
            Log.d("addCoverLetterRecord", "addCoverLetterRecord: Saved")
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun addFileName(fileName1: String, uid: String) {
        val values = ContentValues()
        values.put(COLUMN_FILE_NAME, fileName1)
        values.put(COLUMN_ID, uid)
        values.put(COLUMN_Achievements, "")
        values.put(COLUMN_objective, "")
        values.put(COLUMN_Interests, "")
        values.put(COLUMN_Language, "")
        values.put(COLUMN_Reference, "")
        values.put(COLUMN_imageURI, "")
        values.put(COLUMN_PersonName, "")
        values.put(COLUMN_FatherName, "")
        values.put(COLUMN_Phone, "")
        values.put(COLUMN_emailID, "")
        values.put(COLUMN_CountryCode, "")
        values.put(COLUMN_FullNumber, "")
        values.put(COLUMN_DOB, "")
        values.put(COLUMN_Cnic, "")
        values.put(COLUMN_ADDRESS, "")
        values.put(COLUMN_Nationality, "")
        values.put(COULMN_GENDER, "")
        values.put(COULMN_MARTIALSTATUS, "")

        val db = this.writableDatabase
        if (db.insert(TABLE_NAME, null, values) > 0) {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    fun updateValues(columnName: String, objective: String, id: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(columnName, objective)

        val query =
            "UPDATE $TABLE_NAME SET $columnName='$objective' WHERE $COLUMN_FILE_NAME='$id';"
        Log.d("TAG", "updateValues: $COLUMN_ID= $id")
        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
        db.execSQL(query)
        db.close()
    }

    fun fetchData(): ArrayList<ModelMain> {
        val db = this.readableDatabase
        val arrayList: ArrayList<ModelMain> = ArrayList()
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val fileName = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME))
            val id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
            val pName = cursor.getString(cursor.getColumnIndex(COLUMN_PersonName))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_emailID))
            val imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_imageURI))
            val modelMain = ModelMain(fileName, pName, imageUri, email, id)
            arrayList.add(modelMain)
        }
        return arrayList
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "cvMaker.db"
        const val TABLE_NAME = "CVMaker"
        const val TABLE_QUALIFICATION = "Qualification"
        const val TABLE_EXPERIENCE = "Experience"
        const val TABLE_TECHSKILL = "TechnicalSkills"
        const val TABLE_PROJECT = "Projects"
        const val TABLE_COVER_LETTER = "CoverLetter"

        const val COLUMN_ID = "ID"
        const val COLUMN_FILE_NAME = "fileName"
        const val COLUMN_PersonalInfo = "info"
        const val COLUMN_objective = "objective"
        const val COLUMN_Experience = "experience"
        const val COLUMN_Qualification = "qualification"
        const val COLUMN_Achievements = "Achievements"
        const val COLUMN_Interests = "Interest"
        const val COLUMN_Language = "Language"
        const val COLUMN_Reference = "Reference"
        const val COLUMN_imageURI = "uri"
        const val COLUMN_PersonName = "pName"
        const val COLUMN_FatherName = "fName"
        const val COLUMN_Phone = "phoneNumber"
        const val COLUMN_emailID = "EmailID"
        const val COLUMN_CountryCode = "countryCode"
        const val COLUMN_FullNumber = "fullnumber"
        const val COLUMN_ADDRESS = "address"
        const val COULMN_GENDER = "gender"
        const val COULMN_MARTIALSTATUS = "MartialStatus"

        const val COLUMN_DOB = "dob"
        const val COLUMN_Cnic = "cnic"
        const val COLUMN_Nationality = "nationality"

        const val COLUMN_DEGREE = "degree"
        const val COLUMN_SCHOOL = "school"
        const val COLUMN_MARKS = "marks"
        const val COLUMN_EXP_ID = "expId"
        const val COLUMN_ENDDATE = "endDate"

        const val COLUMN_CNAME = "cnam"
        const val COLUMN_DESIG = "desig"
        const val COLUMN_TO = "toDate"
        const val COLUMN_FROM = "fromDate"
        const val COLUMN_QUA_ID = "quaId"

        const val COLUMN_SKILL = "skill"
        const val COLUMN_SKILL_ID = "skillId"

        const val COLUMN_PROJECT = "project"
        const val COLUMN_PROJECT_ID = "projectId"

        const val COLUMN_CID = "ID"
        const val COLUMN_CLNAME = "cname"
        const val COLUMN_SEND_NAME = "sendname"
        const val COLUMN_SEND_DESIG = "senddesig"
        const val COLUMN_SEND_ADDRESS = "sendaddress"
        const val COLUMN_SEND_PHONE = "sendphone"
        const val COLUMN_SEND_EMAIL = "sendemail"
        const val COLUMN_REC_NAME = "recname"
        const val COLUMN_REC_DESIG = "recdesig"
        const val COLUMN_REC_ADDRESS = "recaddress"
        const val COLUMN_DATE = "date"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_SEND_COMPANY = "company"
        const val COLUMN_SEND_SUBJECT = "subject"
    }
}
