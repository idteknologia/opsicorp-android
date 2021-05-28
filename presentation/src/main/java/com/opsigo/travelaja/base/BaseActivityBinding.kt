package com.opsigo.travelaja.base

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import android.net.ConnectivityManager
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.app.ActivityOptions
import android.view.WindowManager
import android.app.ProgressDialog
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.app.Activity
import android.widget.Toast
import android.view.Window
import android.os.Bundle
import android.view.View
import android.util.Log
import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.viewbinding.BuildConfig
import com.opsicorp.sliderdatepicker.utils.Constant
import com.opsigo.travelaja.module.item_custom.loading.LoadingDialog
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.signin.ProfileModel


/**
 * Created by khoiron on 18/02/18.
 */

abstract class BaseActivityBinding<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var pDialog: ProgressDialog
    protected var statusInternet: Boolean = false
    val loading = LoadingDialog()
    protected val viewBinding: VB by lazy { bindLayout() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)*/
        setContentView(viewBinding.root)

        pDialog = ProgressDialog(this)
        pDialog.setCancelable(false)

        onMain()
    }

    abstract fun bindLayout(): VB

    abstract fun onMain()

    protected fun setLog(message: String) {
        if(BuildConfig.DEBUG){
            Log.e("Test", message)
        }
    }

    protected fun setLog(tag:String,message: String) {
        if(BuildConfig.DEBUG){
            Log.e(tag, message)
        }
    }

    fun setToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    fun finishResultOk(){
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    fun finishResultOk(data:Intent){
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    fun finishResultCancel(context: Context){
        setResult(Activity.RESULT_CANCELED)
        finish()
    }


    fun closeApplication() {
        AlertDialog.Builder(this)
                .setMessage("Apa Anda yakin ingin menutup aplikasi?")
                .setCancelable(false)
                .setPositiveButton("IYA") { _, _ ->
                    val exit = Intent(Intent.ACTION_MAIN)

                    exit.addCategory(Intent.CATEGORY_HOME)

                    exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(exit)
                }
                .setNegativeButton("Tidak", null)
                .show()
    }

    fun closeApplication(message: String) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("IYA") { _, _ ->
                    val exit = Intent(Intent.ACTION_MAIN)

                    exit.addCategory(Intent.CATEGORY_HOME)

                    exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(exit)
                }
                .setNegativeButton("Tidak", null)
                .show()
    }

    @SuppressLint("MissingPermission")
    fun checkInterConnection() {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        statusInternet = networkInfo != null && networkInfo.isConnected
    }

    fun gotoActivity(clas : Class<*>?){
        startActivity(Intent(this,clas))
    }

    fun gotoActivityResult(clas : Class<*>?,code:Int){
        startActivityForResult(Intent(this,clas),code)
    }

    fun gotoActivityResultWithBundle(clas : Class<*>?,bundle: Bundle,code:Int){
        val intent = Intent(this,clas)
        intent.putExtra("data",bundle)
        startActivityForResult(intent,code)
    }

    protected fun showDialog(Mdialog: String) {
        if (!pDialog.isShowing)
            pDialog.setMessage(Mdialog)
        pDialog.show()
    }

    protected fun hideDialog() {
        if (pDialog.isShowing)
            pDialog.dismiss()
    }

    fun gotoActivityWithBundle(clas : Class<*>?,bundle: Bundle){
        val intent = Intent(this,clas)
        intent.putExtra("data",bundle)
        startActivity(intent)
    }

    fun gotoActivityWithBundleUsingTransition(view: View, bundle:Bundle, clas : Class<*>?) {
        val intent = Intent(this, clas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(this, view, "header")
            intent.putExtra("data",bundle)
            startActivity(intent, options.toBundle())
        } else {
            intent.putExtra("data",bundle)
            startActivity(intent)
        }
    }

    fun getProfile(): ProfileModel {
        return Serializer.deserialize(Globals.getDataPreferenceString(this, "profile"), ProfileModel::class.java)
    }

    fun getToken(): String {
        return Globals.getDataPreferenceString(this, "token")
    }

    fun getFCMToken(): String {
        return Globals.getDataPreferenceString(this, Constants.FCM_TOKEN)
    }

    fun showLoadingOpsicorp(disable: Boolean) {
        loading.showDialogLoading(this, disable)
    }

    fun hideLoadingOpsicorp() {
        loading.dismiss()
    }

    fun showDialogFragment(fragmentDialog: androidx.fragment.app.DialogFragment) {
        val fm = getSupportFragmentManager()
        fragmentDialog.show(fm, "yesNoAlert")
    }

    fun getBaseUrl(): String {
        return Globals.getBaseUrl(this)
    }

    fun showAllert(title: String, message: String) {
        Globals.showAlert(title, message, this)
    }
}