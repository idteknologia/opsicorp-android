package com.mobile.travelaja.utility

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DownloadManager
import android.content.*
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.text.ClipboardManager
import android.util.Base64
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.*
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.mobile.travelaja.BuildConfig
import com.mobile.travelaja.R
import com.mobile.travelaja.base.InitApplications
import me.echodev.resizer.Resizer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.domainlayer.model.signin.ProfileModel
import java.io.*
import java.text.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


/**
 * Created by khoiron on 11/06/18.
 */

object Globals {

    var ALL_READY_SELECT_DEPARTING = false
    var ONE_TRIP = true
    var isBisnisTrip = true
    var typeAccomodation = "Train"

    //key save data dummy sementara untuk order accomodation
    var DATA_ORDER_FLIGHT = ""
    var DATA_FLIGHT       = ""
    var DATA_LIST_FLIGHT  = ""
    var DATA_ORDER_MULTI_TRIP    = ""
    var DATA_LIST_TRAIN   = ""

    fun showAlert(title: String, message: String, activity: Activity) {
        try {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(activity.getString(R.string.ok)) { dialog, which -> dialog.dismiss() }
            builder.create().show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun stringToBarcodeImage(code: String): Bitmap? {
        //generate QR
        var bitmap: Bitmap? = null
        val multiFormatWriter = MultiFormatWriter()
        try {
            //            int mHeight = (int) (c.getResources().getDisplayMetrics().heightPixels / 2.4);
            //            int mWidth = (int) (c.getResources().getDisplayMetrics().widthPixels / 1.3);
            val bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE, 1500, 1500)
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(bitMatrix)

        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return bitmap
    }

    fun getToken(): String {
        val token = getDataPreferenceString(InitApplications.appContext, "token")
        return token
    }

    fun delay(callback: DelayCallback){
        Handler().postDelayed({
            callback.done()
        }, 1500)
    }

    fun delay(timer: Long, callback: DelayCallback){
        Handler().postDelayed({
            callback.done()
        }, timer)
    }

    interface DelayCallback{
        fun done()
    }

    fun showAlert(title: String, message: String, activity: Context) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(activity.getString(R.string.ok)) { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }

    fun getTmpDir(activity: Context?): String {
        val tmpDir = activity?.cacheDir.toString() + "/CBN"
        File(tmpDir).mkdir()

        return tmpDir
    }

    fun showAlert(title: String, message: String, context: Context, onclikAllert: OnclikAllertSingelSelected) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which -> onclikAllert.onclik() }
        builder.create().show()
    }

    fun showAlertComplete(title: String, message: String, activity: Activity, onclikAllert: OnclikAllertDoubleSelected) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(activity.getString(R.string.yes)) { dialog, which -> onclikAllert.yes() }
        builder.setNegativeButton(activity.getString(R.string.cancel)) { dialogInterface, i ->
            dialogInterface.dismiss()
            onclikAllert.no()
        }
        builder.create().show()
    }

    fun showAlert(title: String, message: String, activity: Activity, onclikAllert: OnclikAllertDoubleSelected) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(activity.getString(R.string.yes)) { dialog, which -> onclikAllert.yes() }
        builder.setNegativeButton(activity.getString(R.string.no)) { dialogInterface, i ->
            dialogInterface.dismiss()
            onclikAllert.no()
        }
        builder.create().show()
    }

    fun setDataPreferenceLong(context: Context, key: String, value: Long?) {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putLong(name, value!!)
        editor.apply()
    }

    fun setDataPreferenceFloat(context: Context, key: String, value: Float?) {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putFloat(name, value!!)
        editor.apply()
    }

    fun setDataPreferenceInteger(context: Context, key: String, value: Int?) {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putInt(name, value!!)
        editor.apply()
    }

    fun setDataPreferenceString(context: Context, key: String, value: String) {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString(name, value)
        editor.apply()
    }

    fun setDataPreferenceBolean(context: Context, key: String, value: Boolean?) {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putBoolean(name, value!!)
        editor.apply()
    }

    fun getDataPreferenceBolean(context: Context, key: String): Boolean {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getBoolean(name, false)
    }

    fun toDp(context: Context, sizeInDP: Int): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, sizeInDP.toFloat(), context.resources
                .displayMetrics).toInt()
    }

    fun getDataPreferenceString(context: Context, key: String): String {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getString(name, "")!!
    }

    fun getDataPreferenceFloat(context: Context, key: String): Float {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getFloat(key, 0f)
    }

    fun getDataPreferenceInteger(context: Context, key: String): Int {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getInt(name, 0)
    }

    fun getDataPreferenceLong(context: Context, key: String): Long {
        val name = "${context.packageName}.$key"
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getLong(name, 0)
    }

    fun validatiEdittext(s: ArrayList<String>): Boolean {

        var valid = true
        for (i in s.indices) {
            if (s[i].matches("".toRegex())) {
                valid = false
            }
        }
        return valid

    }

    fun currencyIDRFormat(price: Double): String {
        val decimalFormat = DecimalFormat()
        val formatSymbols = DecimalFormatSymbols()
        formatSymbols.decimalSeparator = ','
        formatSymbols.groupingSeparator = '.'
        decimalFormat.groupingSize = 3
        decimalFormat.isGroupingUsed = true
        decimalFormat.decimalFormatSymbols = formatSymbols
        return decimalFormat.format(price)
    }

    fun formatAmount(amount: String): String {
        var string = ""
        try {
            val parsed = java.lang.Long.parseLong(amount)
            val myFormatter = DecimalFormat("#,###.###")
            string = myFormatter.format(parsed).replace(",".toRegex(), ".")

        }catch (e: Exception){
            e.printStackTrace()
        }
        return string
    }

    fun formatAmount(amount: Double): String {
        val parsed = java.lang.Long.parseLong(amount.toInt().toString())
        val myFormatter = DecimalFormat("#,###.###")
        return myFormatter.format(parsed).replace(",".toRegex(), ".")
    }

    fun formatCreditCard(amount: String): String {
        val parsed = java.lang.Long.parseLong(amount)
        val myFormatter = DecimalFormat("####.####")
        return myFormatter.format(parsed).replace(",".toRegex(), ".")
    }

    fun formatDate(date: String): Date {

        var date1 = Date()
//        val formatter: DateFormat
//        formatter = SimpleDateFormat("yyyy-MM-DD")
//        try {
//            date1 = formatter.parse(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }

        return date1
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun StringToBitMap(encodedString: String): Bitmap? {
        try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            return null
        }

    }

    fun decodeFile(f: File): Bitmap? {
        try {
            // decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, o)

            // Find the correct scale value. It should be the power of 2.
            val REQUIRED_SIZE = 70
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break
                width_tmp /= 2
                height_tmp /= 2
                scale++
            }

            // decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
        } catch (e: FileNotFoundException) {
        }

        return null
    }

    fun getImageFile(context: Context, requestPicture: String?): MultipartBody.Part? {
        var fileToUpload: MultipartBody.Part? = null

        val file: File
        if (requestPicture != null) {
            file = File(requestPicture)
            val file_size = Integer.parseInt((file.length() / 1024).toString())
            if (file_size > 5120) {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                try {
                    val resizedImage = Resizer(context)
                            .setTargetLength(1000)
                            .setQuality(72)
                            .setOutputFormat("JPEG")
                            .setOutputFilename("resized_image")
                            .setOutputDirPath(dir.absolutePath)
                            .setSourceImage(file)
                            .getResizedFile()
                    val file_size_resized = Integer.parseInt((resizedImage.length() / 1024).toString())
                    val tsLong = System.currentTimeMillis() / 1000
                    val image = RequestBody.create(MediaType.parse("image/jpeg"), resizedImage)
                    fileToUpload = MultipartBody.Part.createFormData("cover_image", tsLong.toString() + ".jpeg", image)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                val tsLong = System.currentTimeMillis() / 1000
                val image = RequestBody.create(MediaType.parse("image/jpeg"), file)
                fileToUpload = MultipartBody.Part.createFormData("cover_image", tsLong.toString() + ".jpeg", image)
            }
        }
        return fileToUpload
    }

    fun getImageFile(context: Context, requestPicture: String?, query: String,file: File): MultipartBody.Part? {
        var fileToUpload: MultipartBody.Part? = null

//        val requestPicture = requestPictures
//        val file = file
        if (requestPicture != null) {
           /* if (requestPicture.contains("external_files")){
                requestPicture = requestPicture.replace("/external_files","") // /storage/emulated/0/DCIM/Camera/IMG_20210713_144320.jpg
                requestPicture = "/storage/emulated/0${requestPicture}" // /external_files/DCIM/Camera/IMG_20210713_144320.jpg
            }
            else if (requestPicture.contains("root_files")){
                requestPicture = requestPicture.replace("/root_files","")
            }
            file = File(requestPicture)*/
            /*
            * comprez size image
            * */
            if (requestPicture.contains("jpg")||requestPicture.contains("png")||requestPicture.contains("jpeg")){
                val file_size = Integer.parseInt((file.length() / 1024).toString())
                setLog("path "+requestPicture)
                setLog("size image file before compress = "+file_size)
                if (file_size > 2000) {
                    setLog("resize")
                    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    try {
                        val resizedImage = Resizer(context)
                            .setTargetLength(1000)
                            .setQuality(100)
                            .setOutputFormat("JPEG")
                            .setOutputFilename("resized_image")
                            .setOutputDirPath(dir.absolutePath)
                            .setSourceImage(file)
                            .getResizedFile()
                        val file_size_resized = Integer.parseInt((resizedImage.length() / 1024).toString())
                        setLog("size image file after compress = "+file_size_resized.toString())
                        val tsLong = System.currentTimeMillis() / 1000
                        val image = RequestBody.create(MediaType.parse("image/jpeg"), resizedImage)
                        fileToUpload = MultipartBody.Part.createFormData(query, tsLong.toString() + ".jpeg", image)
                    } catch (e: Exception) {
                        setLog(e.message.toString())
                        e.printStackTrace()
                    }

                } else {
                    val tsLong = System.currentTimeMillis() / 1000
                    val image = RequestBody.create(MediaType.parse("image/jpeg"), file)
                    fileToUpload = MultipartBody.Part.createFormData(query, tsLong.toString() + ".jpeg", image)
                }
            }
            else if (requestPicture.contains("pdf")||requestPicture.contains("doc")||requestPicture.contains("xls")||requestPicture.contains("xlsx")){
                val tsLong = System.currentTimeMillis() / 1000
                val image = RequestBody.create(MediaType.parse("application/${requestPicture.split(".").last().trim()}"), file)
                fileToUpload = MultipartBody.Part.createFormData(query, tsLong.toString() + ".${requestPicture.split(".").last().trim()}", image)
            }

        }
        return fileToUpload
    }


    fun setToast(s: String, context: Context) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    fun textToHtmlString(s: String): String {
        val builder = StringBuilder()
        var previousWasASpace = false
        for (c in s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;")
                    previousWasASpace = false
                    continue
                }
                previousWasASpace = true
            } else {
                previousWasASpace = false
            }
            when (c) {
                '<' -> builder.append("&lt;")
                '>' -> builder.append("&gt;")
                '&' -> builder.append("&amp;")
                '"' -> builder.append("&quot;")
                '\n' -> builder.append("<br>")
                // We need Tab support here, because we print StackTraces as HTML
                '\t' -> builder.append("&nbsp; &nbsp; &nbsp;")
                else -> if (c.toInt() < 128) {
                    builder.append(c)
                } else {
                    builder.append("&#").append(c.toInt()).append(";")
                }
            }
        }
        return builder.toString()
    }

    fun validationStringRegex(string: String): Boolean {

        return string.matches("^[a-zA-Z0-9]*$".toRegex())
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    fun shareTextUrl(context: Context, link: String) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        share.putExtra(Intent.EXTRA_SUBJECT, "Share link")
        share.putExtra(Intent.EXTRA_TEXT, link)
        context.startActivity(Intent.createChooser(share, "Share link!"))

    }
//
//    fun shareImage(context: Context, url: String, urlImage: String, pDialog: ProgressDialog?) {
//        Picasso.get().load(urlImage).into(object : Target{
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//
//            }
//
//            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
//
//            }
//
//            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
//                if (pDialog != null) {
//                    if (pDialog.isShowing) {
//                        pDialog.dismiss()
//                    }
//                }
//                val share = Intent(Intent.ACTION_SEND)
//                share.type = "*/*"
//                share.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(context, bitmap))
//                //                share.setType("text/plain");
//                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                share.putExtra(Intent.EXTRA_SUBJECT, "Share link")
//                share.putExtra(Intent.EXTRA_TEXT, url)
//                context.startActivity(Intent.createChooser(share, "Share Link"))
//            }
//        })
//    }

    fun getLocalBitmapUri(context: Context, bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png")
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            //            bmpUri = Uri.fromFile(file);
            bmpUri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".my.package.name.provider", file)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpUri
    }

    fun openGoogleMap(context: Context, latitude: Double, longitude: Double){
        val uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    fun detectKeyboard(activity: Activity) {
        KeyboardUtils.addKeyboardToggleListener(activity, object : KeyboardUtils.SoftKeyboardToggleListener {
            override fun onToggleSoftKeyboard(isVisible: Boolean) {
                if (isVisible) {
                    Log.e("keyboard", "keyboard visible: $isVisible")
                }
            }
        })
    }

    fun directInYoutube(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun directEmail(context: Context, email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help cahaya bagi negeri")
        context.startActivity(Intent.createChooser(emailIntent, "Send email.."))
    }

    fun getDateNow():String{
        val obDate = Date();
        val obDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")
        return obDateFormat.format(obDate.getTime())
    }

    fun getDateNowNewFormat():String{
        val obDate = Date();
        val obDateFormat = SimpleDateFormat("EEE, dd MMM yyyy")
        return obDateFormat.format(obDate.getTime())
    }

    fun getDateAfterNow(numberAfter: Int, formatOutPut: String):String{
        var obDate = Date()
        val c = Calendar.getInstance()
        c.setTime(obDate)
        c.add(Calendar.DATE, numberAfter)
        obDate = c.getTime()
        val obDateFormat = SimpleDateFormat(formatOutPut)
        return obDateFormat.format(obDate.getTime())
    }

    fun dpToPx(activity: Activity, dp: Int): Int {
        val r = activity.getResources()
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.getDisplayMetrics()))
    }

    fun changeViewButton(textviews: ArrayList<TextView>, lines: ArrayList<RelativeLayout>, position: Int, context: Context) {
        lines.forEachIndexed { index, linearLayout ->
            if(index==position){
                linearLayout.background = context.resources.getDrawable(R.drawable.rounded_button_green_passanger_button)
                textviews.get(index).setTextColor(context.resources.getColor(R.color.colorWhite))
            }
            else{
                linearLayout.background = context.resources.getDrawable(R.drawable.rounded_button_gray_passanger_button)
                textviews.get(index).setTextColor(context.resources.getColor(R.color.colorTextHint))
            }
        }
    }

    fun changeViewButtonLinearlayout(textviews: ArrayList<TextView>, lines: ArrayList<LinearLayout>, position: Int, context: Context) {
        lines.forEachIndexed { index, linearLayout ->
            if(index==position){
                linearLayout.background = context.resources.getDrawable(R.drawable.rounded_button_green_passanger_button)
                textviews.get(index).setTextColor(context.resources.getColor(R.color.colorWhite))
            }
            else{
                linearLayout.background = context.resources.getDrawable(R.drawable.rounded_button_gray_passanger_button)
                textviews.get(index).setTextColor(context.resources.getColor(R.color.colorTextHint))
            }
        }
    }

    fun changeViewButton(textviews: ArrayList<TextView>, lines: ArrayList<RelativeLayout>, position: Int, drawable: Int, color: Int, drawableDefault: Int, colorDefault: Int, context: Context) {
        lines.forEachIndexed { index, linearLayout ->
            if(index==position){
                linearLayout.background = context.resources.getDrawable(drawable)
                textviews.get(index).setTextColor(context.resources.getColor(color))
            }
            else{
                linearLayout.background = context.resources.getDrawable(drawableDefault)
                textviews.get(index).setTextColor(context.resources.getColor(colorDefault))
            }
        }
    }

    fun changeViewButton(textviews: ArrayList<TextView>, position: Int, context: Context) {
        textviews.forEachIndexed { index, textviews ->
            if(index==position){
                textviews.background = context.resources.getDrawable(R.drawable.rounded_button_green_passanger_button)
                textviews.setTextColor(context.resources.getColor(R.color.colorWhite))
            }
            else{
                textviews.background = context.resources.getDrawable(R.drawable.rounded_button_gray_passanger_button)
                textviews.setTextColor(context.resources.getColor(R.color.colorTextHint))
            }
        }
    }

    fun copyText(text: String, context: Context) {
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = text
        }
    }

     fun showPopup(v: View, layout: View):PopupWindow{
//         val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//         val layout = layoutInflater.inflate(layout, null)

            val popupWindow = PopupWindow(
                    layout,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)


            popupWindow.setBackgroundDrawable(BitmapDrawable())
            popupWindow.setOutsideTouchable(true)
            popupWindow.setOnDismissListener(object : PopupWindow.OnDismissListener {
                override fun onDismiss() {

                }
            })

            popupWindow.showAsDropDown(v)

         return popupWindow
     }

    fun scrollToUp(nestedView: NestedScrollView) {
        nestedView.postDelayed({
            nestedView.fullScroll(View.FOCUS_UP)
            nestedView.smoothScrollTo(0, 0)
        }, 200)
    }

    fun scrollToDown(nestedView: NestedScrollView) {
        nestedView.postDelayed({
            nestedView.fullScroll(View.FOCUS_DOWN)
        }, 200)
    }

//    fun classToHasMap(objects: Objects,nameClass:Class<*>){
//        val maps = HashMap<String, Any>()
//        for (field in nameClass.getDeclaredFields()) {
//            if (!field.isAccessible()) {
//                field.setAccessible(true)
//            }
//            maps.put(field.getName(), field.get(objects))
//        }
//    }

    fun setLog(s: String) {
        if(BuildConfig.DEBUG){
            Log.e("Tag", s)
        }
    }

    fun setLog(tag: String, s: String) {
        if(BuildConfig.DEBUG){
            Log.e(tag, s)
        }
    }

    fun getConfigCompany(context: Context): ConfigModel {
        return Serializer.deserialize(getDataPreferenceString(context, "config"), ConfigModel::class.java)
    }
    fun getProfile(context: Context): ProfileModel {
        return Serializer.deserialize(getDataPreferenceString(context, "profile"), ProfileModel::class.java)
    }

    fun classToHasMap(objects: Any, nameClass: Class<*>):HashMap<String, Any>{
        val maps = HashMap<String, Any>()
        for (field in nameClass.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true)
            }
            maps.put(field.getName(), field.get(objects))
        }

        return maps
    }

    fun classToHashMap(objects: Any, nameClass: Class<*>):HashMap<Any, Any>{
        val maps = HashMap<Any, Any>()
        for (field in nameClass.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true)
            }
            maps.put(field.getName(), field.get(objects))
        }

        return maps
    }

    fun getBaseUrl(context: Context): String {
        return getDataPreferenceString(context, Constants.BASE_URL)
    }

    fun setBaseUrl(context: Context, url: String){
        setDataPreferenceString(context, Constants.BASE_URL, url)
    }

    fun getVersionCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }

    fun getType(): String {
        return BuildConfig.VERSION_CODE.toString()
    }

    fun getVersionName():String{
        return BuildConfig.VERSION_NAME
    }

    fun setStartImage(image: ArrayList<ImageView>, position: ArrayList<Int>){
        position.forEachIndexed { index, i ->
            image[index].visibility = View.VISIBLE
        }
    }
    fun clearCache(context: Context){
        setDataPreferenceBolean(context, "login", false)
        setDataPreferenceBolean(context, "first", false)

        setDataPreferenceString(context, "login_user", "")
        setDataPreferenceString(context, "token", "")
        setDataPreferenceString(context, "username", "")
    }

    fun blinkImageAnimation(image: ImageView){
        val animation = AlphaAnimation(1f, 0f); //to change visibility from visible to invisible
        animation.setDuration(800); //1 second duration for each animation cycle
        animation.setInterpolator(LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        image.startAnimation(animation); //to start animation
    }

    fun getWitdhAndHeightLayout(context: Context):Pair<Int, Int>{

        val displayMetrics = context.resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        return Pair(dpWidth.toInt(), dpHeight.toInt())
    }

    fun calculatePercentage(obtained: Double, total: Double): Double {
        return obtained * total /100
    }

    fun shortListDate(dataTime: ArrayList<String>, formatTime: String):ArrayList<String>{
        val listDates = ArrayList<Date>()
        val dateFormatter: SimpleDateFormat = SimpleDateFormat(formatTime)

        dataTime.forEachIndexed { index, s ->
            listDates.add(dateFormatter.parse(s))
        }

        val dateReturn = ArrayList<String>()
        Collections.sort(listDates)

        listDates.forEachIndexed { index, date ->
            dateReturn.add(dateFormatter.format(date.time))
        }

        return dateReturn
    }

    fun getUserCountry(context: Context): String? {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = tm.simCountryIso
            if (simCountry != null && simCountry.length == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US)
            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                val networkCountry = tm.networkCountryIso
                if (networkCountry != null && networkCountry.length == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US)
                }
            }
        } catch (e: Exception) {
        }
        return null
    }

    fun getCalendarSpinner(context: Context, year: Int, month: Int, day: Int, mDateSetListener: DatePickerDialog.OnDateSetListener){
//        val cal   = Calendar.getInstance()
//        val year  = cal.get(Calendar.YEAR)
//        val month = cal.get(Calendar.MONTH)
//        val day   = cal.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun finishResultOk(context: Context){
        val activity = (context as Activity)
        val resultIntent = Intent()
        activity.setResult(Activity.RESULT_OK, resultIntent)
        activity.finish()
    }

    fun finishResultOk(context: Context, data: Intent){
        val activity = (context as Activity)
        activity.setResult(Activity.RESULT_OK, data)
        activity.finish()
    }

    fun finishResultCancel(context: Context){
        val activity = (context as Activity)
        activity.setResult(Activity.RESULT_CANCELED)
        activity.finish()
    }

    fun writeJsonToFile(data: String, context: Context, namefile: String) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput(namefile, Context.MODE_PRIVATE))//"config.txt"
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        }
        catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    fun readJsonFromFile(context: Context, nameFile: String) :String{

        var ret = ""
        try {
            var inputStream = context.openFileInput(nameFile)

            if ( inputStream != null ) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append(receiveString)
                }

                inputStream.close()
                ret = stringBuilder.toString()
            }
        }
        catch (e: FileNotFoundException) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (e: IOException) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    fun countDaysBettwenTwoDate(inputString1: String, inputString2: String, format: String):Int{
        val myFormat = SimpleDateFormat(format)
        var totalDays = 0
        try {
            val date1: Date = myFormat.parse(inputString1)
            val date2: Date = myFormat.parse(inputString2)
            val diff: Long = date2.getTime() - date1.getTime()
            totalDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return totalDays
    }

    fun gotoActivityModule(context: Context, namaActivity: String) {
        try {
            val intent = Intent(
                    context,
                    Class.forName(namaActivity)
            )
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {
            setLog(e.message.toString())
            e.printStackTrace()
            setToast("Error Halaman Tidak ada",context)
        }
    }

    fun gotoActivityModule(context: Context, intent: Intent) {
        try {
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {
            setLog(e.message.toString())
            e.printStackTrace()
            setToast("Error Halaman Tidak ada",context)
        }
    }

    fun gotoActivityForResultModule(context: Context, intent: Intent,code:Int) {
        try {
            (context as Activity).startActivityForResult(intent,code)
        } catch (e: java.lang.Exception) {
            setLog(e.message.toString())
            e.printStackTrace()
            setToast("Error Halaman Tidak ada",context)
        }
    }

    fun splitCamelCase(s: String): String {
        return s.replace(String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"
        ).toRegex(),
                " "
        )
    }

    fun startViewListener(rating:Int,image:ArrayList<ImageView>){
        image.forEachIndexed { index, imageView ->
            if (index<=rating-1){
                imageView.visibility = View.VISIBLE
            }
            else {
                imageView.visibility = View.GONE
            }
        }
    }

    fun calculationDate(inputString1:String,inputString2:String,formatDateInput:String):Int{
        val myFormat = SimpleDateFormat(formatDateInput)
        var totalDay = 0
        try {
            val date1: Date = myFormat.parse(inputString1)
            val date2: Date = myFormat.parse(inputString2)
            val diff: Long = date2.getTime() - date1.getTime()
            totalDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return totalDay
    }

    fun openSoftKeyboard(context: Context, view: View) {
        view.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }


    private fun originCodeSplit(string: String): String {
        val m: Matcher = Pattern.compile("\\(([^)]+)\\)").matcher(string)
        var str = ""
        while (m.find()) {
            str = m.group(1)
        }
        return str
    }

    fun getDeviceName(): String? {
        val manufacturer: String = Build.MANUFACTURER
        val model: String = Build.MODEL
        return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    fun formatCurrency(value : Number) : String{
        try {
            val format = NumberFormat.getCurrencyInstance()
            val symbol = (format as DecimalFormat).decimalFormatSymbols
            symbol.currencySymbol = ""
            symbol.groupingSeparator = '.'
            format.minimumFractionDigits = 0
            format.decimalFormatSymbols = symbol
            return  format.format(value)
        }catch (t : Throwable){
            return ""
        }
    }

    interface CallbackDownload{
        fun succeessDownload(parse: Uri, downloadMimeType: String)
        fun failedDownload()
    }

    fun downloadFile(string:String,context: Context,callback:CallbackDownload) {
        var reference: Long = 0
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(string)

        val attachmentDownloadCompleteReceive: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val extras = intent?.getExtras()
                val q = DownloadManager.Query()
                val downloaded_id = extras?.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);

                if (reference == downloaded_id) { // so it is my file that has been completed
                    q.setFilterById(downloaded_id);
                    val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val c = manager.query(q);
                    if (c.moveToFirst()) {
                        val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        val downloadLocalUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        val downloadMimeType = c.getString(c.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));

                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            callback.succeessDownload(Uri.parse(downloadLocalUri),downloadMimeType)
                        }
                        else {
                            callback.failedDownload()
                        }
                    }
                    c.close();
                }
            }
        }

        context.registerReceiver(attachmentDownloadCompleteReceive,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        val request: DownloadManager.Request = DownloadManager.Request(uri)
        val fileName: String = URLUtil.guessFileName(string, null, null)
        request.setTitle(fileName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
        reference = manager.enqueue(request)
    }

    fun openDownloadedAttachment(
        context: Context,
        attachmentUri: Uri,
        attachmentMimeType: String
    ) {
        var attachmentUri: Uri? = attachmentUri
        if (attachmentUri != null) {
            // Get Content Uri.
            if (ContentResolver.SCHEME_FILE.equals(attachmentUri.scheme)) {
                // FileUri - Convert it to contentUri.
                val file = File(attachmentUri.path)
                attachmentUri = FileProvider.getUriForFile(context, "${context.getPackageName()}.fileprovider", file)
            }
            val openAttachmentIntent = Intent(Intent.ACTION_VIEW)
            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType)
            openAttachmentIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            try {
                context.startActivity(openAttachmentIntent)
            } catch (e: ActivityNotFoundException) {
                setToast("please enable to open file",context)
            }
        }
    }
}
