package com.mobile.travelaja.base

import java.util.*
import android.app.*
import android.util.Log
import android.os.Build
import android.os.Bundle
import android.view.View
import android.os.Handler
import java.lang.Exception
import android.view.Window
import android.widget.Toast
import com.mobile.travelaja.R
import android.content.Intent
import android.graphics.Color
import android.content.Context
import android.widget.TextView
import android.view.WindowManager
import org.koin.core.KoinComponent
import android.annotation.SuppressLint
import android.net.ConnectivityManager
import com.mobile.travelaja.BuildConfig
import com.mobile.travelaja.utility.Globals
import com.mobile.travelaja.locale.AppLocale
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.Serializer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import opsigo.com.domainlayer.model.ConfigModel
import com.mobile.travelaja.locale.LocaleManager
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.locale.LocalePrefrences
import com.mobile.travelaja.utility.CallbackSnackBar
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.domainlayer.callback.CallbackIdDevice
import opsigo.com.domainlayer.model.signin.ProfileModel
import com.mobile.travelaja.locale.AppLocaleChangeReceiver
import com.mobile.travelaja.module.item_custom.dialog_contact_admin.ContactAdminDialog
import com.mobile.travelaja.module.item_custom.loading.LoadingDialog
import com.mobile.travelaja.module.item_custom.loading.DialogErrorConection
import com.mobile.travelaja.module.item_custom.dialog_contact_admin.NotAuthorizedDialog
import com.mobile.travelaja.module.item_custom.dialog_under_contruction.UnderContructionDialog

abstract class BaseActivity : AppCompatActivity(), KoinComponent, AppLocaleChangeReceiver.AppLocaleChangeListener {

    protected lateinit var pDialog: ProgressDialog
    protected var statusInternet: Boolean = false
    val loading = LoadingDialog()
    val dialogContruction = UnderContructionDialog()
    val dialogContactAdmin = ContactAdminDialog()
    val dialogError = DialogErrorConection()
    val dialogNotAuthorized = NotAuthorizedDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        withStatusBar()

        setContentView(getLayout())
        pDialog = ProgressDialog(this)
        pDialog.setCancelable(false)
        OnMain()

        if (Globals.getDataPreferenceBolean(this, "login")) {
            chekActiveIdUser()
        }
    }

    private fun withStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
        }
    }

    fun showDialogNotAuthorized(disable: Boolean){
        dialogNotAuthorized.showDialogLoading(this,disable)
    }

    fun showDialogContruction(disable: Boolean) {
        dialogContruction.showDialogLoading(this, disable)
    }

    fun hideDialogContruction() {
        try {
            dialogContruction.dismiss()
        } catch (e: Exception) {

        }
    }

    fun showLoadingOpsicorp(disable: Boolean) {
        loading.showDialogLoading(this, disable)
    }

    fun hideLoadingOpsicorp() {
        loading.dismiss()
    }

    fun getBaseUrl(): String {
        return Globals.getBaseUrl(this)
    }

    fun showDialogErrorOpsicorp(disable: Boolean, callback: DialogErrorConection.CallbackErrorDialog) {
        dialogError.showDialogLoading(this, disable, callback)
    }


    fun hideDialogErrorOpsicorp() {
        try {
            dialogError.dismiss()
        } catch (e: Exception) {

        }
    }


    fun showAllert(title: String, message: String) {
        Globals.showAlert(title, message, this)
    }

    abstract fun getLayout(): Int
    abstract fun OnMain()

    protected fun showDialog(Mdialog: String) {
        if (!pDialog.isShowing)
            pDialog.setMessage(Mdialog)
        pDialog.show()
    }

    protected fun hideDialog() {
        if (pDialog.isShowing)
            pDialog.dismiss()
    }

    fun setLog(message: String) {
        if (BuildConfig.DEBUG) {
            Log.e("Test", message)
        }
    }

    fun setLog(tag: String, s: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, s)
        }
    }

    fun showSnackbar(viewParent: View, calback: CallbackSnackBar) {
        val snackbar = Snackbar
                .make(viewParent, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        calback.onclikRetry()
                    }
                })

        snackbar.setActionTextColor(Color.RED)
        val sbView = snackbar.getView()
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.YELLOW)
        snackbar.show()
    }


    fun setToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun closeApplication() {
        AlertDialog.Builder(this)
                .setMessage("Are you sure want exit the app?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val exit = Intent(Intent.ACTION_MAIN)

                    exit.addCategory(Intent.CATEGORY_HOME)

                    exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(exit)
                }
                .setNegativeButton("No", null)
                .show()
    }

    fun gotoActivity(clas: Class<*>?) {
        startActivity(Intent(this, clas))
    }

    fun gotoActivity(intent: Intent){
        startActivity(intent)
    }

    fun gotoActivityResult(clas: Class<*>?, code: Int) {
        startActivityForResult(Intent(this, clas), code)
    }

    fun gotoActivityResultIntent(intent: Intent, code: Int) {
        startActivityForResult(intent, code)
    }

    fun gotoActivityResultWithBundle(clas: Class<*>?, bundle: Bundle, code: Int) {
        val intent = Intent(this, clas)
        intent.putExtra("data", bundle)
        startActivityForResult(intent, code)
    }


    fun gotoActivityWithBundle(clas: Class<*>?, bundle: Bundle) {
        val intent = Intent(this, clas)
        intent.putExtra("data", bundle)
        startActivity(intent)
    }

    fun sendDataCallbackActivity(intent: Intent) {
        setResult(Activity.RESULT_OK, intent)

        val lang = intent.getBooleanExtra("language", false)

        setResult(Activity.RESULT_OK, intent)

        if (lang == true) {

            var selected = AppLocale.English
            val s_lang = intent.getStringExtra("nameCountry")

            if (s_lang.equals("English")) {
                selected = AppLocale.English
            } else if (s_lang.equals("Indonesia")) {
                selected = AppLocale.Bahasa
            }

            LocalePrefrences.getInstance(InitApplications.appContext).selectedLocale = selected
            LocaleManager.getInstance().setCurrentLocale(InitApplications.appContext, Locale(selected.lang))

            onAppLocaleChanged(Locale(selected.lang))
            finish()
        }

        finish()
    }

    override fun onAppLocaleChanged(newLocale: Locale) {

        val launcherIntent = InitApplications.appContext.packageManager.getLaunchIntentForPackage(InitApplications.appContext.packageName)
        launcherIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val settingIntent = Intent(this, BaseActivity::class.java)

        val stack = TaskStackBuilder.create(InitApplications.appContext)
                .addNextIntent(launcherIntent)
                .addNextIntentWithParentStack(settingIntent)

        finish()
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)

        stack.startActivities()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun gotoActivityWithBundleUsingTransition(view: View, bundle: Bundle, clas: Class<*>?) {
        val intent = Intent(this, clas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(this, view, "header")
            intent.putExtra("data", bundle)
            startActivity(intent, options.toBundle())
        } else {
            intent.putExtra("data", bundle)
            startActivity(intent)
        }
    }

    fun gotoActivityUsingTransition(view: View, clas: Class<*>?) {
        val intent = Intent(this, clas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(this, view, "header")
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    @SuppressLint("MissingPermission")
    fun checkInterConection() {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkinfo = connMgr.activeNetworkInfo
        if (networkinfo != null && networkinfo.isConnected()) {
            statusInternet = true
            setLog(statusInternet.toString())
        } else {
            statusInternet = false
        }
    }

    fun showDialogFragment(fragmentDialog: androidx.fragment.app.DialogFragment) {
        val fm = getSupportFragmentManager()
        fragmentDialog.show(fm, "yesNoAlert")
    }


    fun loadFragment(fragment: androidx.fragment.app.Fragment?, place: Int) {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(place, fragment)
                    .addToBackStack("")
                    .commit()
        }
    }

    fun loadFragmentWithAnimationTransaction(fragment: androidx.fragment.app.Fragment?, place: Int) {

        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(place, fragment)
                    .addToBackStack("tag")
                    .commit()
        }


    }

    fun loadFragmentWithAnimationTransactionBack(fragment: androidx.fragment.app.Fragment?, place: Int) {

        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(place, fragment)
                    .addToBackStack("tag")
                    .commit()
        }

    }

    fun loadFragmentWithBundle(fragment: androidx.fragment.app.Fragment?, place: Int, bundle: Bundle) {
        if (fragment != null) {

            fragment.setArguments(bundle)

            supportFragmentManager
                    .beginTransaction()
                    .replace(place, fragment)
                    .addToBackStack("")
                    .commit()
        }
    }


    @SuppressLint("CommitTransaction")
    fun killFragment(fragment: androidx.fragment.app.Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .remove(fragment)
        }
    }

    fun getToken(): String {
        return Globals.getDataPreferenceString(this, "token")
    }

    fun getFCMToken(): String {
        return Globals.getDataPreferenceString(this, Constants.FCM_TOKEN)
    }

    fun getProfile(): ProfileModel {
        return Serializer.deserialize(Globals.getDataPreferenceString(this, "profile"), ProfileModel::class.java)
    }

    lateinit var callbackDialogDummy: CallbackDialogDummy

    fun showDialogDummy(callbackDialogDummy: CallbackDialogDummy) {
        showDialog("")
        Handler().postDelayed({
            hideDialog()
            callbackDialogDummy.done()
        }, 1500)

    }

    interface CallbackDialogDummy {
        fun done()
    }

    override fun attachBaseContext(newBase: Context) {
        val localeBase = LocaleManager.getInstance().wrapContext(newBase)
        super.attachBaseContext(localeBase)
    }

    fun chekActiveIdUser() {
        GetDataGeneral(getBaseUrl()).getCheckIdDevice(getToken(),
                Globals.getDataPreferenceString(this, Constants.FCM_TOKEN),
                object : CallbackIdDevice {
                    override fun success(boolean: Boolean) {
                        if (!boolean) {
                            logoutListener()
                        }
                    }

                    override fun failed(string: String) {
                        showAllert("Sorry", string)
                    }
                })
    }

    fun logoutListener() {
        Globals.setDataPreferenceBolean(this, "login", false)
        Globals.setDataPreferenceBolean(this, "first", false)
        Globals.setDataPreferenceString(this, "login_user", "")
        Globals.setDataPreferenceString(this, "token", "")
        Globals.setDataPreferenceString(this, "username", "")
        val intent = Intent(getString(R.string.init_activity))
        gotoActivity(intent)
        finish()
    }

    fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun getConfigCompany(): ConfigModel {
        return Serializer.deserialize(Globals.getDataPreferenceString(this, "config"), ConfigModel::class.java)
    }

    fun showDialogContactAdmin(disable: Boolean){
        dialogContactAdmin.showDialogLoading(this,disable)
    }

}