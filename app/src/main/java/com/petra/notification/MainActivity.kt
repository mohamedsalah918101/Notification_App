package com.petra.notification

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.petra.notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var channelID = "channelID"
    var channelName = "channelName"
    var notificationID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Notifications
        createChannelNotification()
        val intent = Intent(this, MainActivity::class.java)
        val PendingIntent = TaskStackBuilder.create(this).run {
            addNextIntent(intent)
            getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE)
        }

        val notifyBuilder = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Hello Mohamed Salah")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(PendingIntent).build()

        val notification = NotificationManagerCompat.from(this)
        binding.btnNotifyMe.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }else{
                notificationID++
                notification.notify(notificationID,notifyBuilder)
            }
            // Dialogs
            var dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            MaterialAlertDialogBuilder(this)
                .setTitle("Log Out")
                .setView(R.layout.layout_custom_dialog)
                .setNegativeButton("Cancel"){
                   dialog, which ->
                   Log.d("cancel","cancel")
                }
                    .setPositiveButton("Bye"){
                    dialog, which ->
                    Log.d("go","go")
                }.show()

        }

    }

    private fun createChannelNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}