package com.mobile.travelaja.module.settlement

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mobile.travelaja.R
import com.mobile.travelaja.base.SearchActivity
import com.mobile.travelaja.module.settlement.view.TripFragmentArgs
import com.mobile.travelaja.module.settlement.viewmodel.TripViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory


class TripSearchActivity : SearchActivity() {
    private lateinit var viewModel : TripViewModel

    override fun getNavigation(): Int {
        setHintSearch(getString(R.string.txt_enter_trip_code))
        viewModel = ViewModelProvider(this, DefaultViewModelFactory(false, this)).get(TripViewModel::class.java)
        return R.navigation.settlement_nav
    }

    override fun startDestinationNavId(): Int = R.id.tripFragment
    override fun arguments(): Bundle {
        val selectedCode = intent.getStringExtra(SELECTED)
        return TripFragmentArgs(9,selectedCode).toBundle()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return if (query.isNullOrEmpty() || query.isNullOrBlank()){
            false
        }else {
            viewModel.onSearch(query)
            true
        }
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return if (query.isNullOrEmpty() || query.isNullOrBlank()){
            false
        }else {
            viewModel.onSearch(query)
            true
        }
    }

    companion object {
        const val SELECTED = "SELECTED_CODE"
    }
}
