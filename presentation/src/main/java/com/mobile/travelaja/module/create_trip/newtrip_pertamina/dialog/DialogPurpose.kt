package com.mobile.travelaja.module.create_trip.newtrip_pertamina.dialog

import android.app.Activity
import android.content.Intent
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Constants
import kotlinx.android.synthetic.main.dialog_purpose.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class DialogPurpose : BaseActivity(),DialogPurposeView  {

    companion object {
        const val SELECT = "SELECT"
    }


    var language = false
    private var hasSelected = false

    override fun getLayout(): Int { return R.layout.dialog_purpose }

    val presenter by inject<DialogPurposePresenter> { parametersOf(this)  }

    override fun OnMain() {
        hasSelected = intent?.getBundleExtra("data")?.getBoolean(SELECT,false) ?: false
        initToolbar()

        presenter.initRecyclerView(rv_purpose)
    }



    private fun initToolbar() {
        ic_back.setOnClickListener {
            finish()
        }
        getData()
    }

    private fun getData() {
        if(intent?.getBundleExtra("data")!=null){
            if("country".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
            }
            else if("city".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataCity()
            }
            else if("purpose".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataPurpose()
            }
            else if("activity".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataActivity()

            }
            else if("budget".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataSelectBudged()
            }
            else if("cost_center".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataCostCenter()
            }
            else if("reason_code".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataReasonCode()
            }
            tv_title.text = intent?.getBundleExtra("data")?.getString("titleHeader")

        }
    }

    override fun callbackFromThisActivity(name: String,code:String) {
        val intent = Intent()
        intent?.putExtra("nameCountry",name)
        intent?.putExtra("idCountry",code)
        intent?.putExtra("language",language)
        if (hasSelected){
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        else{
            sendDataCallbackActivity(intent)
        }
    }



}