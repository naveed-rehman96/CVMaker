package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.fcmNotification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.R
import cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.ui.activities.SplashScreenActivity
import java.util.*

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FCMService : FirebaseMessagingService()  {

    override fun onCreate() {
        Log.d("MyDeviceTokenIs", "Service Started")
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCMSERVICEEEEEEEEEE", "On Message Received Called")
        showNotification(remoteMessage.data)
    }

    override fun onNewToken(p0: String) {
        Log.d("token", p0)
        super.onNewToken(p0)
    }

    private fun showNotification(data: Map<String, String>) {
        val title = data["title"]
        val body = data["body"]
        var appId: String? = ""
        val contentType = data["content_type"]
        if (contentType != null) {
            if (contentType == "self") {
                startActivity(Intent(this, SplashScreenActivity::class.java))
            } else {
                appId = data["appid"]
            }
        }
        Log.d("FCMSERVICEEEEEEEEEE", "Foreground Called")
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val NotificationChanelId = "cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            val notificationChannel = NotificationChannel(
                NotificationChanelId,
                "Notification",
                NotificationManager.IMPORTANCE_MAX
            )
            notificationChannel.description = "My Channel"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(appId))
        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, browserIntent, 0)
        if (title != null || body != null) {
            val notificationBuilder = NotificationCompat.Builder(this, NotificationChanelId)
            notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setContentInfo("Info")
            notificationManager.notify(Random().nextInt(), notificationBuilder.build())
        }
    }


}