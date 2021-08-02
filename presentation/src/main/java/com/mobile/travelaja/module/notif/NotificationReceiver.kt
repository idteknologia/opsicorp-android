package com.mobile.travelaja.module.notif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import android.util.Log
import com.mobile.travelaja.R
import com.mobile.travelaja.base.InitApplications
import com.mobile.travelaja.utility.Constants.APPROVE_IT
import com.mobile.travelaja.utility.Constants.CHANNEL_DESC
import com.mobile.travelaja.utility.Constants.CHANNEL_NAME_X
import com.mobile.travelaja.utility.Constants.CHANNNEL_ID_X
import com.mobile.travelaja.utility.Constants.KEY_INTENT_MORE
import com.mobile.travelaja.utility.Constants.KEY_INTENT_NOTIF_ID_INT
import com.mobile.travelaja.utility.Constants.KEY_INTENT_REJECT
import com.mobile.travelaja.utility.Constants.NOTIFICATION_REPLY
import com.mobile.travelaja.utility.Constants.REJECT_IT

class NotificationReceiver : BroadcastReceiver() {


    private var NOTIF_ID_INT = 0

    override fun onReceive(context: Context, intent: Intent) {
        //getting the remote input bundle from intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        NOTIF_ID_INT = intent.getIntExtra(KEY_INTENT_NOTIF_ID_INT, -1)


        //if there is some input
        if (remoteInput != null) {

            //getting the input value
            val name = remoteInput.getCharSequence(NOTIFICATION_REPLY)

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(NOTIF_ID_INT)
            //notificationManager.cancel(MainActivity.NOTIFICATION_ID);

        }


        //if help button is clicked
        if (intent.getIntExtra(KEY_INTENT_REJECT, -1) == REJECT_IT) {
            //Toast.makeText(context, "You Clicked Help", Toast.LENGTH_LONG).show();
            displayProgressNotification(context, "reject")
        }


        //if more button is clicked
        if (intent.getIntExtra(KEY_INTENT_MORE, -1) == APPROVE_IT) {
            //Toast.makeText(context, "You Clicked More", Toast.LENGTH_LONG).show();

            displayProgressNotification(context, "apprvoe")
        }
        /* */
    }

    fun displayProgressNotification(context: Context, status: String) {

        Log.d("notifxxx", "emplaoyId : $NOTIF_ID_INT")
        createNotificationChannel()

        //final NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getContext(), CHANNNEL_ID);
        val builder = NotificationCompat.Builder(context, CHANNNEL_ID_X + NOTIF_ID_INT)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("$status $NOTIF_ID_INT")
        builder.setContentText("sync progress")
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        val max_progress = 100
        val current_progress = 0
        builder.setProgress(max_progress, current_progress, false)
        notificationManagerCompat.notify(NOTIF_ID_INT, builder.build())
        //notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        val thread = object : Thread() {
            override fun run() {
                var count = 0

                try {
                    while (count <= 100) {
                        count = count + 10
                        Thread.sleep(1000)
                        builder.setProgress(max_progress, count, false)
                        notificationManagerCompat.notify(NOTIF_ID_INT, builder.build())
                    }

                    //builder.setContentText("completed!");
                    //builder.setProgress(0,0,false);
                    //notificationManagerCompat.notify(NOTIF_ID, builder.build());

                    val notificationManager =
                        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancel(NOTIF_ID_INT)

                } catch (e: InterruptedException) {
                }

            }
        }

        thread.start()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val mNotificationManager
                    = InitApplications.appContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                CHANNNEL_ID_X + NOTIF_ID_INT,
                CHANNEL_NAME_X + NOTIF_ID_INT,
                importance
            )
            mChannel.description = CHANNEL_DESC
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

}