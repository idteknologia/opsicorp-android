package com.unicode.kingmarket.Base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import com.opsigo.travelaja.BuildConfig
import com.opsigo.travelaja.utility.Globals


/**
 * Created by khoiron on 22/01/18.
 */

abstract class BaseBottomSheetDialogFrament: BottomSheetDialogFragment()  {

    protected lateinit var pCurrentFragment: BaseBottomSheetDialogFrament

    private lateinit var mBehavior: BottomSheetBehavior<FrameLayout>
    var fullScreen = false
    lateinit var inflatedView: View
    var transparant = false


    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        inflatedView = View.inflate(context, getLayout(), null)
        dialog.setContentView(inflatedView)

        onMain(inflatedView)

        if (fullScreen){
            setFullscreenView(inflatedView,dialog)
        }

        if(transparant){
            setTransparantView(inflatedView,dialog)
        }

    }

    private fun setTransparantView(inflatedView: View?, dialog: Dialog?) {
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun setFullScreen(){
        fullScreen = true
    }

    fun setTransParantBackground(){
        transparant = true
    }

    fun setFullscreenView(inflatedView: View, dialog: Dialog?) {
        val params = (inflatedView.parent as View).layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        val parent = inflatedView.parent as View
        parent.fitsSystemWindows = true
        val bottomSheetBehavior = BottomSheetBehavior.from(parent)
        inflatedView.measure(0, 0)
        val displaymetrics = DisplayMetrics()
        activity!!.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics)
        val screenHeight = displaymetrics.heightPixels
        bottomSheetBehavior.peekHeight = screenHeight
        if (params.behavior is BottomSheetBehavior<*>) {
            (params.behavior as BottomSheetBehavior<*>).setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        params.height = screenHeight
        parent.layoutParams = params

    }


    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged( bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        onMain(inflatedView)
    }

    abstract fun getLayout(): Int
    abstract fun onMain(fragment: View)

    open fun onBackPress() {}

    fun setLog(message: String){
        if(BuildConfig.DEBUG){
            Log.e("Test",message)
        }
    }

    fun setToast(message: String){
        Toast.makeText(activity?.applicationContext,message,Toast.LENGTH_LONG).show()
    }

    fun switchFargment(place :Int,fragment: BaseBottomSheetDialogFrament){
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
        intent.putExtra("getDataLogin",bundle)
        startActivity(intent)
    }

    open fun goActivityForresult(intent: Intent, position :Int) {
        startActivityForResult(intent,position)
    }

    fun setStyleBottomSheet(style: Int){

    }

    fun getToken():String{
        return Globals.getDataPreferenceString(context!!,"token")
    }
}