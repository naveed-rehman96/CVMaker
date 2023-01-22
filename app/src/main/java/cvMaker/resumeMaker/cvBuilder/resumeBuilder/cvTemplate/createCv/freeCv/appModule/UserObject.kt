package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.appModule

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.modelClasses.CvMainModel
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.*

object UserObject {

    var userCvData : CVModelEntity = CVModelEntity()
    var cvMainModel : CvMainModel = CvMainModel()
    var userQualificationList  = ArrayList<QualificationEntity>()
    var userExperienceList  = ArrayList<ExperienceEntity>()
    var userSkillList  = ArrayList<SkillsEntity>()
    var userProjectList  = ArrayList<ProjectsEntity>()

}