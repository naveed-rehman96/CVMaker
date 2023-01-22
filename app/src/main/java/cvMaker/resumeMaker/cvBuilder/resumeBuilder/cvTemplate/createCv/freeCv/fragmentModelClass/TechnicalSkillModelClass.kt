package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fragmentModelClass

class TechnicalSkillModelClass {

    var techSkills: String? = null
    var skillId: String? = null

    constructor(techSkills: String?, skillId: String?) {
        this.techSkills = techSkills
        this.skillId = skillId
    }

    constructor() {}
}