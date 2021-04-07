package com.opsigo.travelaja.module.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.opsigo.travelaja.R
import com.opsigo.travelaja.base.InitApplications
import com.opsigo.travelaja.locale.AppLocale
import com.opsigo.travelaja.locale.LocaleManager
import com.opsigo.travelaja.locale.LocalePrefrences
import com.opsigo.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.opsigo.travelaja.module.signin.detail_profile.activity.DataProfileActivity
import com.opsigo.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import com.opsigo.travelaja.utility.StringUtils
import com.squareup.picasso.Picasso
import com.opsigo.travelaja.base.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*
import opsigo.com.datalayer.mapper.CountryMapper
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.datalayer.model.general.CountryEntity
import java.lang.Exception
import java.util.*

class ProfileFragment : BaseFragment(),
        ButtonDefaultOpsicorp.OnclickButtonListener,View.OnClickListener {
    var SELECT_CODE_LANGUAGE  = 71

    override fun getLayout(): Int {
        return R.layout.profile_fragment
    }

    override fun onClick(view: View?) {
        when(view){
            tv_join_trip -> {
                showDialogContruction(false)
//                joinToTrip()
            }
            ic_join_trip -> {
                showDialogContruction(false)
//                joinToTrip()
            }
            lay_language -> {
                selectLanguangeListener()
            }
            lay_personal -> {
                gotoActivity(DataProfileActivity::class.java)
            }
            lay_settings -> {
                selectSettingsListener()
            }
        }
    }

    private fun selectLanguangeListener() {
        val bundle = Bundle()
        bundle.putString("emplaoyId","language")
        bundle.putString("titleHeader","Select Language")
        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_LANGUAGE)
    }

    private fun selectSettingsListener() {
//        val bundle = Bundle()
//        bundle.putString("emplaoyId","language")
//        bundle.putString("titleHeader","Select Language")
//        gotoActivityResultWithBundle(SelectNationalityActivity::class.java,bundle,SELECT_CODE_LANGUAGE)

        //

        try {
                var xxx = "{\n" +
                        "    \"result\": [\n" +
                        "        {\n" +
                        "            \"Code\": \"AD\",\n" +
                        "            \"Name\": \"ANDORRA\",\n" +
                        "            \"CultureCode\": null,\n" +
                        "            \"CurrencyName\": \"Euro\",\n" +
                        "            \"CurrencyCode\": \"EUR\",\n" +
                        "            \"Region\": null,\n" +
                        "            \"Continent\": null,\n" +
                        "            \"IsRequiredVisa\": false,\n" +
                        "            \"CountryRateCap\": 0,\n" +
                        "            \"CityCap\": 0\n" +
                        "        }],\n" +
                        "\t\"total\": 239\n" +
                        "}\n"
                Log.d("cekcountry_AAdef","go here 1a " + xxx )

                //callbackCountry.successLoad(CountryMapper().mapping(Serializer.deserialize(response.body()?.string().toString(), CountryEntity::class.java)))
            var zz = CountryMapper().mapping(Serializer.deserialize(xxx, CountryEntity::class.java))

            Log.d("cekcountry_AAdef","go here 2a " + zz)


        }catch (e: Exception){
            Log.d("cekcountry_AA1abc","go here 4 " + e.toString() )
            //callbackCountry.failedLoad(messageFailed)
        }
    }

    private fun joinToTrip() {
        gotoActivity(JoinTripActivity::class.java)
    }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        btn_logout.changeBackground(R.color.colorGrayRoundWhite)

        val sign_out: String = getString(R.string.sign_out)

        tv_version.text = "Ver 1.0.11 debug"

        btn_logout.setTextButton(sign_out)
        btn_logout.changeBackground(R.color.colorGrayRoundWhite)
        btn_logout.callbackOnclickButton(this)

        lay_language.setOnClickListener(this)
        ic_join_trip.setOnClickListener(this)
        tv_join_trip.setOnClickListener(this)
        lay_personal.setOnClickListener(this)
        lay_settings.setOnClickListener(this)

        setProfileData()

    }

    private fun setProfileData() {

        val dataProfile = getProfile()
        if (dataProfile.imageUrl.isNotEmpty()) {
            Picasso.get()
                    .load(dataProfile.imageUrl)
                    .fit()
                    .centerCrop()
                    .into(img_profile)

            img_dummy.visibility = View.GONE
        }

        tv_name.text      = StringUtils().setUppercaseFirstLetter(dataProfile.firstName) + " " + StringUtils().setUppercaseFirstLetter(dataProfile.lastName)
        tv_position.text  = dataProfile.position
        tv_address.text   = dataProfile.address
        tv_email.text     = dataProfile.email

    }

    override fun onClicked() {
        logout()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){

            SELECT_CODE_LANGUAGE -> {
                if (resultCode == Activity.RESULT_OK){
                    val s_lang =  data?.getStringExtra("nameCountry")
                    tv_language.text = data?.getStringExtra("nameCountry")

                    var selected = AppLocale.English
                    if(s_lang.equals("English")){
                        selected = AppLocale.English
                    }else if(s_lang.equals("Indonesia")){
                        selected = AppLocale.Bahasa
                    }

                    LocalePrefrences.getInstance(InitApplications.appContext).selectedLocale = selected
                    LocaleManager.getInstance().setCurrentLocale(InitApplications.appContext, Locale(selected.lang))

                }
            }
        }
    }



}
