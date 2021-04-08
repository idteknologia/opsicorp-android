package com.opsigo.travelaja.module.signin.select_nationality.activity

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import androidx.appcompat.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.opsigo.travelaja.locale.ApplyLanguageActivity
import com.opsigo.travelaja.BaseActivity
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.InitApplications
import com.opsigo.travelaja.locale.AppLocaleChangeReceiver
import com.opsigo.travelaja.module.signin.select_nationality.presenter.SelectedNationalityPresenter
import com.opsigo.travelaja.module.signin.select_nationality.view.SelectNationalityView
import com.opsigo.travelaja.utility.Constants
import com.opsigo.travelaja.utility.gone
import com.opsigo.travelaja.utility.visible
import kotlinx.android.synthetic.main.select_nationality_view.*
import kotlinx.android.synthetic.main.toolbar_white.*
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class SelectNationalityActivity : BaseActivity(),SelectNationalityView , AppLocaleChangeReceiver.AppLocaleChangeListener  {

    companion object {
        const val SELECT = "SELECT"
    }


    var language = false
    private var hasSelected = false

    override fun getLayout(): Int { return R.layout.select_nationality_view }

    val presenter by inject<SelectedNationalityPresenter> { parametersOf(this) }

    override fun OnMain() {
        hasSelected = intent.getBundleExtra("data")?.getBoolean(SELECT,false) ?: false
        initToolbar()
        initFilter()
        presenter.initRecyclerView(rv_nationality)
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
                    lay_title.gone()
                }
                else{
                    presenter.filterActivated = false
                    getData()
                    lay_title.visible()
                }
            }
        })

        imgCloseDest.setOnClickListener {
            et_filter.text.clear()
        }

        tvCloseSearch.setOnClickListener {
            finish()
        }
    }


    private fun initToolbar() {
        val toolbarView = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbarView)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(" ")
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_dark_blue)
        toolbar_title.visibility = View.INVISIBLE
        getData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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
            et_filter.hint = intent.getBundleExtra("data").getString("searchHint")
        }
    }

    override fun callbackFromThisActivity(name: String,code:String) {
        val intent = Intent()
        intent.putExtra("nameCountry",name)
        intent.putExtra("idCountry",code)
        intent.putExtra("language",language)
        if (hasSelected){
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        else{
            sendDataCallbackActivity(intent)
        }
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