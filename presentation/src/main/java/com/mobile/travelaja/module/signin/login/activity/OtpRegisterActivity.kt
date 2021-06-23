package com.mobile.travelaja.module.signin.login.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.signin.register.ComplitelyRegisterActivity
import com.mobile.travelaja.utility.Globals
import kotlinx.android.synthetic.main.otp_register_activity.*
import opsigo.com.datalayer.datanetwork.GetDataLogin
import opsigo.com.datalayer.network.MyURL
import opsigo.com.domainlayer.callback.CallbackString
import opsigo.com.domainlayer.model.signin.OtpRegisterRequest
import java.util.*
import kotlin.collections.ArrayList


class OtpRegisterActivity : BaseActivity(),
        ButtonDefaultOpsicorp.OnclickButtonListener {

    override fun getLayout(): Int { return R.layout.otp_register_activity }

    var email = ""

    override fun OnMain() {
        parsingDataWithIntent()
        initOtpListener()
    }

    private fun parsingDataWithIntent() {
        email = intent.getBundleExtra("data").getString("Email")!!
    }

    private fun initOtpListener() {
        onTextWatcher(editText1,editText2)
        onTextWatcher(editText2,editText3)
        onTextWatcher(editText3,editText4)
        onTextWatcher(editText4,editText5)
        onTextWatcher(editText5,editText6)
        onTextWatcher(editText6,editText6)

        onKeyListener(editText6,editText5)
        onKeyListener(editText5,editText4)
        onKeyListener(editText4,editText3)
        onKeyListener(editText3,editText2)
        onKeyListener(editText2,editText1)
        onKeyListener(editText1,editText1)

        btn_next.callbackOnclickButton(this)
        btn_back.setOnClickListener { finish() }

    }

    private fun onKeyListener(currentView: EditText, previousView: EditText) {
        currentView.setOnKeyListener(object:View.OnKeyListener{
            override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.editText1 && currentView.text.isEmpty()) {
                    //If current is empty then previous EditText's number will also be deleted
                    previousView.text = null
                    previousView.requestFocus()
                    return true
                }
                return false
            }
        })
    }

    private fun onTextWatcher(editText1: EditText, nextView: EditText) {
        editText1.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                when (editText1) {
                    editText1 -> if (text.length == 1) nextView.requestFocus()
                    editText2 -> if (text.length == 1) nextView.requestFocus()
                    editText3 -> if (text.length == 1) nextView.requestFocus()
                    editText4 -> if (text.length == 1) nextView.requestFocus()
                    editText5 -> if (text.length == 1) nextView.requestFocus()
                    editText6 -> if (text.length == 1) btn_next.requestFocus()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    override fun onClicked() {
        if (Globals.validatiEdittext(getDataField())){
            getValidationOtp()
        }
        else{
            failedWarning(getString(R.string.warning_canot_be_empty))
        }
    }

    private fun getValidationOtp() {
        showLoadingOpsicorp(true)
        GetDataLogin(MyURL.URL_TRAVELAJA).getDataValidationOtp(emailUser(),object : CallbackString {
            override fun successLoad(data: String) {
                val bundle = Bundle()
                bundle.putString("Email",email)
                bundle.putString("Otp",getCodeOtp())
                bundle.putString("id",data)
                gotoActivityWithBundle(ComplitelyRegisterActivity::class.java,bundle)
                hideLoadingOpsicorp()
            }

            override fun failedLoad(message: String) {
                hideLoadingOpsicorp()
                showAllert(getString(R.string.sorry),message)
            }
        })
    }

    private fun getDataField(): java.util.ArrayList<String> {
        val data = ArrayList<String>()
        data.add(editText1.text.toString())
        data.add(editText2.text.toString())
        data.add(editText3.text.toString())
        data.add(editText4.text.toString())
        data.add(editText5.text.toString())
        data.add(editText6.text.toString())
        return data
    }

    private fun emailUser(): HashMap<Any, Any> {
        val data = OtpRegisterRequest()
        data.email = email
        data.token = getCodeOtp()
        return Globals.classToHashMap(data,OtpRegisterRequest::class.java)
    }

    private fun getCodeOtp(): String {
        return  editText1.text.toString()+
                editText2.text.toString()+
                editText3.text.toString()+
                editText4.text.toString()+
                editText5.text.toString()+
                editText6.text.toString()
    }

    private fun failedWarning(message: String) {
        Globals.showAlert(getString(R.string.failed),message,this)
    }

}