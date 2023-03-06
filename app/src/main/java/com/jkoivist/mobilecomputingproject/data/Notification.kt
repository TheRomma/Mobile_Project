package com.jkoivist.mobilecomputingproject.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jkoivist.mobilecomputingproject.MainActivity
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jkoivist.mobilecomputingproject.R

class NotificationHelper(
    val context: Context
){
    private val channelId = "mobile_computing_notifications"
    private val notificationId = 1

    public fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "Channel for mobilecomputing notifications."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelId, importance).apply{
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    public fun createNotification(id: Int, title: String, message: String){
        val intent = Intent(context, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        //val icon = BitmapFactory.decodeResource(context.resources, )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)){
            notify(id, builder.build())
        }
    }
}

class ReminderWorker(val context: Context, val params: WorkerParameters) : Worker(context, params){
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getInt("id", 1),
            inputData.getString("title").toString(),
            inputData.getString("message").toString()
        )
        return Result.success()
    }
}