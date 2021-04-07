package com.opsigo.travelaja.module.signin.select.activity

import android.app.TaskStackBuilder
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.opsigo.travelaja.locale.ApplyLanguageActivity
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.InitApplications
import com.opsigo.travelaja.locale.AppLocaleChangeReceiver
import com.opsigo.travelaja.module.signin.select.presenter.LookUpPresenter
import com.opsigo.travelaja.module.signin.select.view.LookUpView
import com.opsigo.travelaja.utility.Constants
import kotlinx.android.synthetic.main.look_up_view.*
//import kotlinx.android.synthetic.main.select_nationality_view.*
//import kotlinx.android.synthetic.main.select_nationality_view.et_filter
//import kotlinx.android.synthetic.main.select_nationality_view.imgCloseDest
//import kotlinx.android.synthetic.main.select_nationality_view.lay_search
//import kotlinx.android.synthetic.main.select_nationality_view.rv_nationality
//import kotlinx.android.synthetic.main.select_nationality_view.tv_title
import kotlinx.android.synthetic.main.toolbar_white.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class LookUpActivity : BaseActivity(),LookUpView , AppLocaleChangeReceiver.AppLocaleChangeListener  {

    var language = false

    override fun getLayout(): Int { return R.layout.look_up_view }

    val presenter by inject<LookUpPresenter> { parametersOf(this) }

    override fun OnMain() {
        initToolbar()
        initFilter()
        presenter.initRecyclerView(rv_nationality)
        tv_close.setOnClickListener{
            onBackPressed()
        }
        //et_filter.hint = ""
    }

    private fun initFilter() {
        et_filter.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length>0){
                    presenter.filterActivated = true
                    presenter.filterDataAdapter(s.toString())
                }
                else{
                    presenter.filterActivated = false
                    getData()
                }
            }
        })

        imgCloseDest.setOnClickListener {
            et_filter.text.clear()
        }
    }


    private fun initToolbar() {
        val toolbarView = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbarView)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(" ")
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_dark_blue)
        toolbar_title.visibility = View.INVISIBLE

        Log.d("xcountry77","go here")

        getData()
    }

    private fun getData() {
        if(intent.getBundleExtra("data")!=null){
            if("country".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                presenter.getDataNationality()
            }
            else if("city".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                presenter.getDataCity()
            }
            else if("purpose".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataPurpose()
            }
            else if("budget".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataSelectBudged()
            }
            else if("cost_center".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataCostCenter()
            }
            else if("reason_code".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataReasonCode()
            }
            else if ("train_station".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                presenter.getDataStationTrain()
            }
            else if("language".equals(intent.getBundleExtra("data").getString(Constants.SELECT_RESULT))){
                language = true
                lay_search.visibility = View.GONE
                presenter.getDataLanguage()
            }

            tv_title.text = intent.getBundleExtra("data").getString("titleHeader")
            et_filter.hint = intent.getBundleExtra("data").getString("hint")
        }
    }

    override fun callbackFromThisActivity(name: String,code:String) {
        val intent = Intent()
        intent.putExtra("nameCountry",name)
        intent.putExtra("idCountry",code)
        intent.putExtra("language",language)
        sendDataCallbackActivity(intent)
    }

    override fun onAppLocaleChanged(newLocale: Locale) {

        val launcherIntent = InitApplications.appContext.packageManager.getLaunchIntentForPackage(InitApplications.appContext.packageName)
        launcherIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val settingIntent = Intent(this, ApplyLanguageActivity::class.java)

        val stack = TaskStackBuilder.create(InitApplications.appContext)
                .addNextIntent(launcherIntent)
                .addNextIntentWithParentStack(settingIntent)


        finish()
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)

        stack.startActivities()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

}