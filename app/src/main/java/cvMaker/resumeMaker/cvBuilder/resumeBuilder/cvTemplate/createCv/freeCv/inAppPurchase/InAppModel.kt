package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.inAppPurchase


data class InAppModel(
    val sku: String,
    val price: String,
    val freeTrail: String,
    val currencyCode:String,
    val description:String,
    val subscriptionPeriod:String
)
