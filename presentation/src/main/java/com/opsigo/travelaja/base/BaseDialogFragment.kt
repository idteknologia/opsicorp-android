package com.unicode.kingmarket.Base

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.opsigo.travelaja.BuildConfig
import com.opsigo.travelaja.utility.Globals

/**
 * Created by khoiron on 22/01/18.
 */

abstract class BaseDialogFragment: DialogFragment()  {

    protected lateinit var pDialog: ProgressDialog
    protected lateinit var pCurrentFragment: BaseDialogFragment

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

    open fun onBackPress() {

    }

    fun setLog(message: String){
        if(BuildConfig.DEBUG){
            Log.e("Test",message)
        }
    }

    fun setToast(message: String){
        Toast.makeText(activity?.applicationContext,message,Toast.LENGTH_LONG).show()
    }

    fun switchFragment(place :Int,fragment: BaseDialogFragment){
        pCurrentFragment = fragment
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(place, fragment)
        fragmentTransaction?.commit()
    }


    open fun goActivity(c: Class<*>) {
        startActivity(Intent(context,c))
    }

    open fun goActivityWithBundle(c: Class<*>,bundle: Bundle) {
        var intent = Intent(context,c)
        intent.putExtra("data",bundle)
        startActivity(intent)
    }

    open fun goActivityForresult(intent: Intent, position :Int) {
        startActivityForResult(intent,position)
    }

    fun gotoActivityResultWithBundle(clas : Class<*>?,bundle: Bundle,code:Int){
        var intent = Intent(context,clas)
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

    fun getToken():String{
        return Globals.getDataPreferenceString(context!!,"token")
    }

    fun transparantBackground(){
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}