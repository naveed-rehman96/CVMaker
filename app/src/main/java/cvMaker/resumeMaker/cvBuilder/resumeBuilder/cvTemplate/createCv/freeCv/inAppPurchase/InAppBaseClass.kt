package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.inAppPurchase

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.homeMain.HomeActivity.Companion.hideAd
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass.TinyDB
import java.util.ArrayList

open class InAppBaseClass : AppCompatActivity() {


    lateinit var billingClient: BillingClient
    var purchaseUpdateListener: PurchasesUpdatedListener? = null
    var isBillingReady = false

    //Test
    val LIFETIME = "android.test.purchased"

    var arrayListInApp = ArrayList<InAppModel>()

    //Original
//    val LIFETIME = "cvmaker_1";
    var isPremium = false

    override fun onStart() {
        super.onStart()
        initIAP()
    }
    fun initIAP(){
        isPremium = TinyDB(this).getBoolean("inApp")

        purchaseUpdateListener = PurchasesUpdatedListener { billingResult: BillingResult, purchases: List<Purchase>? ->
            Log.i("BillingTag", "getOldPurchases: in Listener")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    handlePurchase(this, purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                Log.i("BillingTag", "getOldPurchases: User Cancelled")
            }
            else {
                Log.i("BillingTag", "getOldPurchases: Other Error")
            }
        }
        billingClient = BillingClient.newBuilder(this).setListener(purchaseUpdateListener!!).enablePendingPurchases().build()
        settingInAppBilling()
        settingSub()
        setupConnection(this)
    }


    fun setupConnection(context: Context) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    isBillingReady = true
                    settingInAppBilling()
                    settingSub()
                    Log.i("BillingTag", "onBillingServiceDisconnected: Setup Connection")
                    getOldPurchases(context)
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.i("BillingTag", "onBillingServiceDisconnected: Setup Connection Failed")
                isBillingReady = false
            }
        })
    }

    fun isPurchased(): Boolean {
        return isPremium
    }

    fun purchase(activity: Activity, product: String) {
        if (isBillingReady) {
            val skuList: MutableList<String> = ArrayList()
            skuList.add(product)
            val params = SkuDetailsParams.newBuilder()
            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
            billingClient.querySkuDetailsAsync(params.build()
            ) { billingResult: BillingResult?, skuDetailsList: List<SkuDetails?>? ->
                // Process the result.
                if (skuDetailsList != null) {
                    if (skuDetailsList.isNotEmpty()) {
                        val billingFlowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetailsList[0]!!).build()
                        val responseCode = billingClient.launchBillingFlow(activity, billingFlowParams).responseCode
                        if (responseCode != BillingClient.BillingResponseCode.OK) {
                            Log.i("BillingTag", "getOldPurchases: Please try Again Later1")
                        }
                    }
                }
            }
        } else {
            Log.i("BillingTag", "getOldPurchases: Please try Again Later2")
            setupConnection(activity)
        }
    }

    fun subscribe(activity: Activity, product: String) {
        if (isBillingReady) {
            val skuList: MutableList<String> = ArrayList()
            skuList.add(product)
            val params = SkuDetailsParams.newBuilder()
            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
            billingClient.querySkuDetailsAsync(params.build()
            ) { billingResult: BillingResult?, skuDetailsList: List<SkuDetails?>? ->
                // Process the result.
                if (skuDetailsList != null) {
                    if (skuDetailsList.size > 0) {
                        val billingFlowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetailsList[0]!!).build()
                        val responseCode = billingClient.launchBillingFlow(activity, billingFlowParams).responseCode
                        if (responseCode != BillingClient.BillingResponseCode.OK) {
                            Log.i("BillingTag", "getOldPurchases: Please try Again Later1")
                        }
                    }
                }
            }
        } else {
            Log.i("BillingTag", "getOldPurchases: Please try Again Later2")
            setupConnection(activity)
        }
    }

    /* public void getDetails(){
        List<SkuDetails> purchaseListingDetails = bp.getPurchaseListingDetails(arrayListOfProductIds);
        priceButton01.setText(purchaseListingDetails.get(0).currency + purchaseListingDetails.get(0).priceValue);
        priceButton02.setText(purchaseListingDetails.get(1).currency + purchaseListingDetails.get(1).priceValue);
        priceButton03.setText(purchaseListingDetails.get(2).currency + purchaseListingDetails.get(2).priceValue);
    }*/
     fun handlePurchase(context: Context, purchase: Purchase) {
        val acknowledgePurchaseResponseListener = AcknowledgePurchaseResponseListener { billingResult: BillingResult? -> Log.i("BillingTag", "getOldPurchases: Acknowledge") }
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (purchase.sku == LIFETIME) {
                isPremium = true
                TinyDB(context).putBoolean("inApp", true)
                hideAd()
                Log.i("BillingTag", "handlePurchase: premium")
            }
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener)
            }
        }
    }

     fun getOldPurchases(context: Context) {
        val result = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
        val purchases = result.purchasesList
        if (purchases != null) {
            for (purchase in purchases) {
                if (purchase.sku == LIFETIME) {
                    isPremium = true
                    TinyDB(context).putBoolean("inApp", true)
                    Log.i("BillingTag", "getOldPurchases: premium")
                }
            }
        }

//        Purchase.PurchasesResult subs = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
//        List<Purchase> subscriptions = subs.getPurchasesList();
//        if (subscriptions != null) {
//            for (Purchase subscription : subscriptions) {
//                if (subscription.getSku().equals(MONTHLY_SUB) || subscription.getSku().equals(YEARLY_SUB)) {
//                    isPremium = true;
//                    new TinyDB(context).putBoolean("inApp", true);
//                }
//            }
//        }
    }

    fun settingInAppBilling() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(LIFETIME)
        //        skuList.add(MONTHLY_SUB);
//        skuList.add(YEARLY_SUB);
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient.querySkuDetailsAsync(params.build()) { billingResult: BillingResult, skuDetailsList: List<SkuDetails>? ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                for (skuDetails in skuDetailsList) {
                    val sku = skuDetails.sku
                    val price = skuDetails.price
                    val freeTrail = skuDetails.freeTrialPeriod
                    val currencyCode = skuDetails.priceCurrencyCode
                    val description = skuDetails.description
                    val subscriptionPeriod = skuDetails.subscriptionPeriod
                    Log.d("inappBill", "  $price")
                    if (LIFETIME == sku) {
                        val inAppModel = InAppModel(sku, price, freeTrail, currencyCode, description, subscriptionPeriod)
                        arrayListInApp.add(inAppModel)
                    }
                    //                    else if (MONTHLY_SUB.equals(sku)) {
//                        InAppModel inAppModel = new InAppModel(sku, price, freeTrail, currencyCode, description, subscriptionPeriod);
//                        arrayListInApp.add(inAppModel);
//                    } else if (YEARLY_SUB.equals(sku)) {
//                        InAppModel inAppModel = new InAppModel(sku, price, freeTrail, currencyCode, description, subscriptionPeriod);
//                        arrayListInApp.add(inAppModel);
//                    }
                }
            }
        }
    }

    fun settingSub() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(LIFETIME)
        //        skuList.add(MONTHLY_SUB);
//        skuList.add(YEARLY_SUB);
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
        billingClient.querySkuDetailsAsync(params.build()) { billingResult: BillingResult, skuDetailsList: List<SkuDetails>? ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                for (skuDetails in skuDetailsList) {
                    val sku = skuDetails.sku
                    val price = skuDetails.price
                    val freeTrail = skuDetails.freeTrialPeriod
                    val currencyCode = skuDetails.priceCurrencyCode
                    val description = skuDetails.description
                    val subscriptionPeriod = skuDetails.subscriptionPeriod
                    Log.d("inappBill", "  $price")
                    if (LIFETIME == sku) {
                        val inAppModel = InAppModel(sku, price, freeTrail, currencyCode, description, subscriptionPeriod)
                        arrayListInApp.add(inAppModel)
                    }

                }
            }
        }
    }

}