package yuga.ridho.pml3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Ambil data dari payload 'notification'
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        // Ambil data dari payload 'data'
        val data = remoteMessage.data

        // PENTING: Kita akan selalu membuat notifikasi manual agar bisa mengontrol Intent
        if (title != null && body != null) {
            sendNotification(title, body, data)
        }

        // Kode untuk update UI jika aplikasi sedang terbuka (foreground)
        // Ini memastikan UI tetap responsif jika app sudah berjalan
        MainActivity.title.value = title ?: ""
        MainActivity.body.value = body ?: ""
        if (data.isNotEmpty()) {
            MainActivity.customData.value = data.toString()
        }
    }

    private fun sendNotification(title: String, body: String, data: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java).apply {
            // Selalu bersihkan task sebelumnya agar tidak menumpuk
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // Masukkan title dan body ke intent dengan key yang jelas
            putExtra("notification_title", title)
            putExtra("notification_body", body)

            // Masukkan semua custom data ke intent
            data.forEach { (key, value) ->
                putExtra(key, value)
            }
        }

        val pendingIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = "fcm_default_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // Pastikan Anda memiliki ikon ini
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Wajib untuk Android Oreo (API 26) ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Firebase Cloud Messaging",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Tampilkan notifikasi dengan ID unik
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}