package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.models

import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.roomDatabaseClasses.model.*


data class CvMainModel(
    var personInfo : CVModelEntity = CVModelEntity(),
    var experienceInfo : ArrayList<ExperienceEntity>  ?=null,
    var qualificationEntity: ArrayList<QualificationEntity> ? = null,
    var projectsEntity: ArrayList<ProjectsEntity> = ArrayList(),
    var skillsEntity: ArrayList<SkillsEntity>  = ArrayList()
)