package com.opsigo.travelaja.module.notif
//package com.android4dev.pushnotification.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.RemoteInput
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RemoteViews
//import com.android4dev.pushnotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.InitApplications
import com.opsigo.travelaja.module.approval.activity.DetailTripActivity
import com.opsigo.travelaja.utility.Constants

import com.opsigo.travelaja.utility.Constants.CHANNEL_DESC
import com.opsigo.travelaja.utility.Constants.CHANNEL_NAME_X
import com.opsigo.travelaja.utility.Constants.CHANNNEL_ID_X
import com.opsigo.travelaja.utility.Constants.KEY_INTENT_APPROVE_REJECT
import com.opsigo.travelaja.utility.Constants.KEY_INTENT_NOTIF_ID_INT
import com.opsigo.travelaja.utility.Constants.KEY_INTENT_TRIPID
import com.opsigo.travelaja.utility.Constants.KEY_INTENT_TRIP_CODE
import com.opsigo.travelaja.utility.Constants.NOTIFICATION_CONFIRM_APPROVE
import com.opsigo.travelaja.utility.Constants.NOTIFICATION_REASON
import com.opsigo.travelaja.utility.Constants.REQUEST_CODE_APPROVE

import opsigo.com.domainlayer.model.summary.SummaryModel

import org.json.JSONObject


class CorpFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseToken"
    private lateinit var notificationManager: NotificationManager
    private val ADMIN_CHANNEL_ID = "Android4Dev"
    val REQUEST_ACCEPT = 123

    var SUMM = SummaryModel()
    internal var SEND_TO = 0

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.i(TAG, token)
    }

//    override fun onTokenRefresh() {
//        // Mengambil token perangkat
//        val token = FirebaseInstanceId.getInstance().token
//        Log.d(TAG, "Token perangkat ini: ${token}")
//
//        // Jika ingin mengirim push notifcation ke satu atau sekelompok perangkat,
//        // simpan token ke server di sini.
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        Constants.isApproval = false
        Constants.isParticipant = false

        remoteMessage?.let { message ->
            Log.i(TAG, message.data.get("message"))

            //var broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
            Log.d(TAG, "Dikirim dari: ${remoteMessage.from}")

            //Log.i(TAG, message.data.get("additionalData"))

            val strnotifType = message.data.get("notifType")
            val notifType = strnotifType?.toInt()

            //if(notifType == )

//ActivityManager mActivityManager =(ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);

//if(Build.VERSION.SDK_INT > 20){
//String mPackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
//}
//else{
//  String mpackageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
//}

  //           ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

//            val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//            val cn = activityManager.getRunningTasks(1).get(0).topActivity
//            //val cn2 = activityManager.getrun(1).get(0).topActivity

            //ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            val notif_id = SystemClock.uptimeMillis().toInt()
            var intent = Intent()

            Log.d("myfir01","here " + message.data.toString())
            Log.d("myfir01","" + message.data.get("additionalData"))

            if(notifType == Constants.NotifType.SubmitTripPan){

                val additionalData = message.data.get("additionalData")
                val data = JSONObject(additionalData)

                Log.i(TAG, additionalData)

                SEND_TO = data.optInt("SendTo", -1)

                val tpId = data.optString("TripPlanId", null)
                val tripCode = data.optString("TripCode", null)
                val purpose = data.optString("Purpose", null)
                val startDate = data.optString("StartDate", null)
                val endDate = data.optString("EndDate", null)
                val tp_status = data.optString("Status", null)

                Log.d("myfir02","here " + data)
                if (SEND_TO == Constants.SendTo.Approvers) {

                    Log.d("myfir03","here ")
                    displayNotification(InitApplications.appContext, notif_id,
                            tpId, tripCode, purpose, startDate, endDate )

                }

            }else if(notifType == Constants.NotifType.ProgressPnr){
                val progressdata = message.data.get("progress")
                val prog = JSONObject(progressdata)
                val vprogress   = prog.optString("Progress", null)
                val vtext       = prog.optString("Text", null)
                val vjobtype    = prog.optString("JobType", null)
                val vPnrId      = prog.optString("PnrId", null)
                val vParticipantId = prog.optString("ParticipantId", null)
                val PnrCode     = prog.optString("PnrCode", null)

                Log.d("udpatex1",": udate " + vprogress + " - " + vtext + " - " )

                intent.putExtra("notif_id",notif_id)
                intent.putExtra("vProgress",vprogress)
                intent.putExtra("vText",vtext)
                intent.putExtra("vPnrId",vPnrId)
                intent.putExtra("vParticipantId",vParticipantId)
                intent.putExtra("PnrCode",PnrCode)
//                intent.setAction(Constants.ACT_DETAIL_PART)
                intent.setAction(Constants.ACT_CART)

            }else if(notifType == Constants.NotifType.FinishFollowUpPerPax){

                val additionalData = message.data.get("additionalData")
                Log.i(TAG, additionalData)
                val data = JSONObject(additionalData)

                val TripPlanId = data.optString("TripPlanId", null)
                val TripCode = data.optString("TripCode", null)
                val tp_status = data.optString("Status", null)
                Log.d("udpatex",": udate " + tp_status + " - " + notif_id)

                intent.putExtra(Constants.KEY_INTENT_TRIPID, TripPlanId)
                intent.putExtra(Constants.KEY_INTENT_TRIP_CODE, TripCode)
                intent.putExtra("notif_id",notif_id)
                intent.putExtra("tp_status",tp_status)
                intent.setAction(Constants.ACT_DETAIL_TP)
            }
            sendBroadcast(intent)



//
//            if (SEND_TO == Constants.SendTo.Participant) {
//                //sendNotification(InitApplications.appContext, SystemClock.uptimeMillis().toInt(), tpId, "Tripplan is being reviewed by approver")
//            } else if (SEND_TO == Constants.SendTo.Bookers) {
//                //sendNotification(InitApplications.appContext, SystemClock.uptimeMillis().toInt(), tpId, "Tripplan has been sent to each of the approver")
//            } else if (SEND_TO == Constants.SendTo.Approvers) {
//                //RequestSummary(tpId)
//                //loadSummaryRet(Globals.getToken(), tpId, InitApplications.appContext)
//
//                //displayNotification(InitApplications.appContext, notif_id, tpId, tripCode, purpose, startDate, endDate )
//            }


        }

    }

    fun displayNotification(context: Context, NOTIFICATION_ID_INT: Int, tripID: String, tripCode: String, purpose: String, startDate: String, endDate: String ) {

        createNotificationChannel(NOTIFICATION_ID_INT)

        Log.v("xx_DE_dd", "here")

        var isMoreItem = false
        val approvePendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE_APPROVE + NOTIFICATION_ID_INT,
                Intent(context, NotificationReceiver::class.java)
                        .putExtra(KEY_INTENT_APPROVE_REJECT, Constants.ApprovalAction.Approve)
                        .putExtra(KEY_INTENT_TRIPID, tripID)
//                        .putExtra(KEY_INTENT_TRIP_PARTIC, tripParticipantID)
//                        .putExtra(KEY_TRIP_ITEM_TYPE, tripType)
                        .putExtra(KEY_INTENT_NOTIF_ID_INT, NOTIFICATION_ID_INT),
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        Constants.FROM_SUCCESS_CHECKOUT = false
        val DT_Intent = Intent(this, DetailTripActivity::class.java)

        var isApproval  = false
        var isParticipant = false

        if (SEND_TO == Constants.SendTo.Approvers) {
            isApproval = true
            Log.d("goAppx",":" + "1")
        }
        if (SEND_TO == Constants.SendTo.Participant) {
            isParticipant = true
            Log.d("goAppx",":" + "2")
        }

        Constants.isApproval = isApproval
        Constants.isParticipant = isParticipant

        Log.d("goAppx",":" + "3 " + Constants.isApproval)

        DT_Intent.putExtra(Constants.IS_APPROVER, true)
        DT_Intent.putExtra(ConsJS.TripplanIDX, tripID)
        DT_Intent.putExtra(KEY_INTENT_TRIP_CODE, tripCode)
        DT_Intent.putExtra(KEY_INTENT_TRIPID, tripID)
        DT_Intent.putExtra(KEY_INTENT_NOTIF_ID_INT, NOTIFICATION_ID_INT)

        val DTPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_INT, DT_Intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANNNEL_ID_X + NOTIFICATION_ID_INT)

        //builder.setSmallIcon(R.drawable.ic_iscon);
        builder.setSmallIcon(R.mipmap.ic_launcher)

        builder.setContentText("TripPlan $tripCode with")
        builder.setContentIntent(DTPendingIntent)
        builder.priority = NotificationCompat.PRIORITY_HIGH

        // Get the layouts to use in the custom notification
        val notif_short = RemoteViews(context.packageName, R.layout.layout_notif_v2)
        val notif_expanded = RemoteViews(context.packageName, R.layout.layout_expanded_v2)

        notif_short.setTextViewText(R.id.tvPurpose, purpose)
        notif_short.setTextViewText(R.id.tv_date_from, startDate)
        notif_short.setTextViewText(R.id.tv_date_to, endDate)

//        notif_expanded.setTextViewText(R.id.tvDestination, destination)

        //laterx
//        notif_expanded.setTextViewText(R.id.tvName, participantName)
        notif_expanded.setTextViewText(R.id.tvPurpose, purpose)
        //notif_expanded.setTextViewText(R.id.tvDestination, des)
        notif_expanded.setTextViewText(R.id.tv_date_from, startDate)
        notif_expanded.setTextViewText(R.id.tv_date_to, endDate)
        notif_expanded.setViewVisibility(R.id.tvDotMore, GONE)
        //notif_expanded.setViewVisibility(R.emplaoyId.tvDestination, GONE);

        val bitmap: Bitmap? = null
        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notif_short)
                .setCustomBigContentView(notif_expanded)


        val notificationManagerCompat = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val remoteInputReject = RemoteInput.Builder(NOTIFICATION_REASON).setLabel("Remarks").build()
            val remoteInputApprove = RemoteInput.Builder(NOTIFICATION_CONFIRM_APPROVE).setLabel("Type ok or anything to approve").build()

            notificationManagerCompat.notify(NOTIFICATION_ID_INT, builder.build())

            Log.d("xrx_imgurlg okx", ":" + " go here 01")

            //builder.addAction(R.drawable.ic_icon,"Approve", approvePendingIntent);
            //notificationManagerCompat.notify(NOTIFICATION_ID_INT, builder.build());
        }


        Log.d("myfir04","here ")

        if (isMoreItem) {
            notif_expanded.setViewVisibility(R.id.tvDotMore, VISIBLE)
            builder.addAction(R.drawable.ic_icon, "See More Items", DTPendingIntent)
            notificationManagerCompat.notify(NOTIFICATION_ID_INT, builder.build())
        }
    }

    private fun createNotificationChannel(NOTIF_ID: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val mNotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel =
                    NotificationChannel(CHANNNEL_ID_X + NOTIF_ID, CHANNEL_NAME_X + NOTIF_ID, importance)
            mChannel.description = CHANNEL_DESC
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }


    private fun showNotification(title: String?, body: String?) {

        Log.d(TAG, "kadieu 1: " + title)

        val intent = Intent(this, YesActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())

        Log.d(TAG, "kadieu 2: " + title)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannels() {
        val adminChannelName = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin_channel_description)

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }
}