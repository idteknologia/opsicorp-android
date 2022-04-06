package com.mobile.travelaja.module.profile

import java.util.*
import android.util.Log
import android.os.Bundle
import android.view.View
import java.lang.Exception
import android.app.Activity
import android.content.Intent
import com.mobile.travelaja.BuildConfig
import com.mobile.travelaja.R
import com.squareup.picasso.Picasso
import com.mobile.travelaja.locale.AppLocale
import com.mobile.travelaja.base.BaseFragment
import opsigo.com.datalayer.mapper.Serializer
import com.mobile.travelaja.utility.Constants
import opsigo.com.datalayer.mapper.CountryMapper
import com.mobile.travelaja.locale.LocaleManager
import com.mobile.travelaja.base.InitApplications
import com.mobile.travelaja.locale.LocalePrefrences
import opsigo.com.datalayer.model.general.CountryEntity
import kotlinx.android.synthetic.main.profile_fragment.*
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.my_booking.home_my_booking.MyBookingActivity
import com.mobile.travelaja.module.my_booking.home_my_booking.MyBookingFragment
import com.mobile.travelaja.module.signin.detail_profile.activity.DataProfileActivity
import com.mobile.travelaja.module.signin.login.OpenIdLogin
import com.mobile.travelaja.module.signin.select_nationality.activity.SelectNationalityActivity
import net.openid.appauth.AuthorizationService

class ProfileFragment : BaseFragment(),
        ButtonDefaultOpsicorp.OnclickButtonListener,View.OnClickListener {
    var SELECT_CODE_LANGUAGE  = 71

    override fun getLayout(): Int {
        return R.layout.profile_fragment
    }

    override fun onClick(view: View?) {
        when(view){
            lay_language -> {
                selectLanguangeListener()
            }
            lay_personal -> {
                gotoActivity(DataProfileActivity::class.java)
            }
            lay_faq -> {
                gotoActivity(FAQActivity::class.java)
            }
            lay_privacy -> {
                gotoActivity(PrivacyPolicyActivity::class.java)
            }
            lay_terms -> {
                gotoActivity(TermsActivity::class.java)
            }
            rlMyBooking -> {
                gotoActivity(MyBookingActivity::class.java)
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
        val signOut: String = getString(R.string.sign_out)
        btn_logout.changeTextColorButton(R.color.gray_total)
        btn_logout.changeBackgroundDrawable(R.drawable.rounded_button_dark_select_budget)

        tv_version.text = "Version ${BuildConfig.VERSION_NAME}"

        btn_logout.setTextButton(signOut)
        btn_logout.callbackOnclickButton(this)

        lay_language.setOnClickListener(this)
        lay_personal.setOnClickListener(this)
        lay_faq.setOnClickListener(this)
        lay_privacy.setOnClickListener(this)
        lay_terms.setOnClickListener(this)
        rlMyBooking.setOnClickListener(this)

        setProfileData()

    }

    private fun setProfileData() {

        val dataProfile = getProfile()
        /*if (dataProfile.imageUrl.isNotEmpty()) {
            Picasso.get()
                    .load(dataProfile.imageUrl)
                    .fit()
                    .centerCrop()
                    .into(img_profile)

        }*/

        tv_name.text      = dataProfile.fullName //StringUtils().setUppercaseFirstLetter(dataProfile.firstName) + " " + StringUtils().setUppercaseFirstLetter(dataProfile.lastName)
        tv_position.text  = if (getConfigCompany().codeCompany==Constants.CodeCompany.PertaminaDTM) dataProfile.approval.reqPosName else dataProfile.position
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
