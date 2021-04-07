package com.opsigo.travelaja.base

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.opsigo.travelaja.BuildConfig
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.item_custom.dialog_contact_admin.ContactAdminDialog
import com.opsigo.travelaja.module.item_custom.dialog_under_contruction.UnderContructionDialog
import com.opsigo.travelaja.module.item_custom.loading.LoadingDialog
import com.opsigo.travelaja.module.signin.login.activity.LoginActivity
import com.opsigo.travelaja.utility.CallbackSnackBar
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.datanetwork.GetDataGeneral
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.callback.CallbackSetDeviceId
import opsigo.com.domainlayer.model.ConfigModel
import opsigo.com.domainlayer.model.signin.ProfileModel
import java.lang.Exception

/**
 * Created by khoiron on 22/01/18.
 */

abstract class BaseFragment: Fragment()  {

    protected lateinit var pDialog: ProgressDialog
    protected lateinit var pCurrentFragment: BaseFragment
    val loading = LoadingDialog()
    val dialogContruction = UnderContructionDialog()
    val dialogContactAdmin = ContactAdminDialog()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragment : View = inflater.inflate(getLayout(), container, false)

        pDialog = ProgressDialog(context)
        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onMain(view,savedInstanceState)

    }

    abstract fun getLayout(): Int
    abstract fun onMain(fragment: View, savedInstanceState: Bundle?)

    open fun onBackPress() {}

    fun setLog(message: String){
        if(BuildConfig.DEBUG){
            Log.e("Test",message)
        }
    }

    fun setLog(tag:String,message: String){
        if(BuildConfig.DEBUG){
            Log.e(tag,message)
        }
    }

    fun getBaseUrl():String{
        return Globals.getBaseUrl(context!!)
    }

    fun setToast(message: String){
        Toast.makeText(activity?.applicationContext,message,Toast.LENGTH_LONG).show()
    }

    fun switchFargment(place :Int,fragment: BaseFragment){
        pCurrentFragment = fragment
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(place, fragment)
        fragmentTransaction?.commit()
    }

    open fun gotoActivity(c: Class<*>) {
        startActivity(Intent(context,c))
    }

    open fun gotoActivityModule(context: Context, namaActivity: String) {
        try {
            val intent = Intent(
                    context,
                    Class.forName(namaActivity)
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            setLog(e.message.toString())
            e.printStackTrace()
            setToast("Error Halaman Tidak ada")
        }
    }

    open fun gotoActivityModule(context: Context, intent: Intent) {
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            setLog(e.message.toString())
            e.printStackTrace()
            setToast("Error Halaman Tidak ada")
        }
    }

    open fun gotoActivityForResultModule(context: Context, intent: Intent,code:Int) {
        try {
            (context as Activity).startActivityForResult(intent,code)
        } catch (e: Exception) {
            setLog(e.message.toString())
            e.printStackTrace()
            setToast("Error Halaman Tidak ada")
        }
    }

    open fun gotoActivityWithBundle(c: Class<*>,bundle: Bundle) {
        val intent = Intent(context,c)
        intent.putExtra(Constants.KEY_BUNDLE,bundle)
        startActivity(intent)
    }

    open fun gotoActivityForResult(c: Class<*>, code :Int) {
        val intent = Intent(context,c)
        startActivityForResult(intent,code)
    }

    open fun gotoActivityForResult(intent:Intent, code :Int) {
        startActivityForResult(intent,code)
    }

    fun gotoActivityResultWithBundle(clas : Class<*>?,bundle: Bundle,code:Int){
        val intent = Intent(context,clas)
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

    protected fun failedWarning(message:String) {
        Globals.showAlert("Failed",message,context!!)
    }

    fun logoutListener(){
        Globals.setDataPreferenceBolean(context!!,"login",false)
        Globals.setDataPreferenceBolean(context!!,"first",false)
        Globals.setDataPreferenceString(context!!,"login_user","")
        Globals.setDataPreferenceString(context!!,"token","")
        Globals.setDataPreferenceString(context!!,"username", "")
        Globals.setDataPreferenceString(context!!,Constants.IMAGE_LOGO_SPLASH,"")
        Globals.setDataPreferenceString(context!!,Constants.IMAGE_BACKGROUND_SPLASH,"")
        gotoActivity(LoginActivity::class.java)
        activity?.finish()
    }

    fun getToken():String{
        return Globals.getDataPreferenceString(context!!,"token")
    }

    fun getModelPhone():String{
        return Build.MANUFACTURER + " " + Build.MODEL
    }

    fun getUsername():String{
        return Globals.getDataPreferenceString(context!!,"username")
    }

    fun getFCM():String{
        return Globals.getDataPreferenceString(context!!,Constants.FCM_TOKEN)
    }

    fun getProfile(): ProfileModel {
        return Serializer.deserialize(Globals.getDataPreferenceString(context!!,"profile"), ProfileModel::class.java)
    }

    fun getConfig(): ConfigModel {
        return Serializer.deserialize(Globals.getDataPreferenceString(context!!, "config"), ConfigModel::class.java)
    }

    fun showSnackbar(viewParent:View,calback: CallbackSnackBar,message:String,titleButton:String,colorButton:Int,colorMessage:Int){
        val snackbar = Snackbar
                .make(viewParent, message, Snackbar.LENGTH_LONG)
                .setAction(titleButton, object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        calback.onclikRetry()
                    }
                })

        snackbar.setActionTextColor(resources.getColor(colorButton))
        val sbView = snackbar.getView()
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(resources.getColor(colorMessage))
        snackbar.show()
    }

    fun logout(){
        AlertDialog.Builder(activity!!)
                .setMessage(getString(R.string.confirm_logout))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes_logout)) { dialog, id ->
                    removeDeviceId()
                }
                .setNegativeButton(getString(R.string.no_logout), null)
                .show()
    }

    private fun removeDeviceId() {

        showDialog("")

        GetDataGeneral(Globals.getBaseUrl(context!!)).removeDeviceId(getToken(), getUsername(), getFCM(), getModelPhone(), object : CallbackSetDeviceId {

            override fun successLoad(isSuccess: Boolean) {
                logoutListener()
                hideDialog()
            }

            override fun failedLoad(message: String) {
                Log.d("proxx 6",": failed " + message )
                hideDialog()
                failedWarning(message)
            }
        })

    }

    fun showDialogContruction(disable: Boolean){
        dialogContruction.showDialogLoading(context!!,disable)
    }

    fun showDialogContactAdmin(disable: Boolean){
        dialogContactAdmin.showDialogLoading(context!!,disable)
    }

    fun hideDialogContruction(){
        try {
            dialogContruction.dismiss()
        }catch (e: Exception){

        }
    }

    fun hideDialogContactAdmin(){
        try {
            dialogContactAdmin.dismiss()
        }catch (e: Exception){

        }
    }

    fun showLoadingOpsicorp(disable:Boolean){
        loading.showDialogLoading(context as Activity,disable)
    }

    fun hideLoadingOpsicorp(){
        loading.dismiss()
    }
}