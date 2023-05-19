package com.mobile.travelaja.module.signin.select_nationality.activity

import java.util.*
import android.view.View
import org.koin.core.inject
import android.app.Activity
import android.text.Editable
import android.view.MenuItem
import android.content.Intent
import com.mobile.travelaja.R
import android.text.TextWatcher
import android.app.TaskStackBuilder
import androidx.appcompat.widget.Toolbar
import com.mobile.travelaja.utility.gone
import org.koin.core.parameter.parametersOf
import com.mobile.travelaja.utility.visible
import com.mobile.travelaja.base.BaseActivity
import com.mobile.travelaja.utility.Constants
import com.mobile.travelaja.base.InitApplications
import kotlinx.android.synthetic.main.toolbar_white.*
import com.mobile.travelaja.locale.ApplyLanguageActivity
import com.mobile.travelaja.locale.AppLocaleChangeReceiver
import kotlinx.android.synthetic.main.select_nationality_view.*
import com.mobile.travelaja.module.signin.select_nationality.view.SelectNationalityView
import com.mobile.travelaja.module.signin.select_nationality.presenter.SelectedNationalityPresenter

class SelectNationalityActivity : BaseActivity(),SelectNationalityView , AppLocaleChangeReceiver.AppLocaleChangeListener  {

    companion object {
        const val SELECT = "SELECT"
    }


    var language = false
    private var hasSelected = false

    override fun getLayout(): Int { return R.layout.select_nationality_view }

    val presenter by inject<SelectedNationalityPresenter> { parametersOf(this) }

    override fun OnMain() {
        hasSelected = intent?.getBundleExtra("data")?.getBoolean(SELECT,false) ?: false
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        if(intent?.getBundleExtra("data")!=null){
            if("country".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataNationality()
            }else if("country2".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataNationalityOrigin()
            }
            else if("city".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                try {
                    if (intent.getBundleExtra("data")?.getStringArrayList("listCity")?.isNotEmpty()!!){
                        presenter.getDataAirports(intent.getBundleExtra("data")?.getStringArrayList("listCity")!!)
                    }
                    else {
                        presenter.getDataCity()
                    }
                }catch (e:Exception){
                    presenter.getDataCity()
                }
            }
            else if("purpose".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataPurpose()
            }
            else if("budget".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataSelectBudged()
            }
            else if("cost_center".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataCostCenter()
            }
            else if("reason_code".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                lay_search.visibility = View.GONE
                presenter.getDataReasonCode()
            }
            else if ("train_station".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                presenter.getDataStationTrain()
            }
            else if("language".equals(intent?.getBundleExtra("data")?.getString(Constants.SELECT_RESULT))){
                language = true
                lay_search.visibility = View.GONE
                presenter.getDataLanguage()
            }

            tv_title.text = intent?.getBundleExtra("data")?.getString("titleHeader")
            et_filter.hint = intent?.getBundleExtra("data")?.getString("searchHint")
        }
    }

    override fun callbackFromThisActivity(name: String,country: String,code:String,countryCode: String) {
        val intent = Intent()
        intent?.putExtra("nameCountry",name)
        intent?.putExtra("countryName",country)
        intent?.putExtra("countryCode",countryCode)
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