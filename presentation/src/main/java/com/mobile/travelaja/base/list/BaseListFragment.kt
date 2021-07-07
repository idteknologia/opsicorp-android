package com.mobile.travelaja.base.list

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.BaseListFragmentBinding
import com.mobile.travelaja.utility.Utils

abstract class BaseListFragment<T : Any> : Fragment(), SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    private lateinit var binding: BaseListFragmentBinding
    private var snackbar: Snackbar ?= null

    abstract fun baseListAdapter(): BaseListAdapter<T>

    abstract fun dividerEnabled(): Boolean

    abstract fun isSearchVisible() : Boolean

    abstract fun isButtonVisible() : Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BaseListFragmentBinding.inflate(inflater, container, false)
        binding.isSearch = isSearchVisible()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBaseList.adapter = baseListAdapter()
        binding.rvBaseList.apply {
            adapter = baseListAdapter()
            if (dividerEnabled()) {
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        binding.swpRefresh.setOnRefreshListener(this)
        binding.ivBack.setOnClickListener(this)
        binding.includeSearch.buttonClose.setOnClickListener(this)
        binding.buttonBaseList.setOnClickListener(this)
        binding.includeSearch.root.isVisible = isSearchVisible()
        binding.buttonBaseList.isVisible = isButtonVisible()
    }

    fun setTitleName(@StringRes title : Int,@ColorRes color : Int = 0){
        binding.tvTitle.setText(title)
        if (color != 0)
        binding.tvTitle.setTextColor(resources.getColor(color))
    }

    fun setSubtitle(@StringRes subtitle : Int){
        binding.tvSubtitle.setText(subtitle)
    }

    fun setHintSearch(hint : String){
        binding.includeSearch.searchView.queryHint = hint
    }

    fun setSearchListener(listener : SearchView.OnQueryTextListener){
        binding.includeSearch.searchView.setOnQueryTextListener(listener)
    }

    fun showError(t: Throwable, tryAgain: (View) -> Unit) {
        Utils.handleErrorMessage(requireContext(), t) { error ->
            snackbar = Snackbar.make(binding.root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.txt_try_again, tryAgain)
            snackbar?.show()
        }
    }

    fun showWarning(warning : String){
        snackbar = Snackbar.make(binding.root,warning,Snackbar.LENGTH_SHORT)
        snackbar?.show()
    }

    override fun onStop() {
        super.onStop()
        hiddenError()
    }

    fun hiddenError(){
        if (snackbar != null && snackbar!!.isShown){
            snackbar?.dismiss()
        }
    }

    fun isRefreshing(isRefresh: Boolean) {
        binding.swpRefresh.isRefreshing = isRefresh
    }

    fun setButtonText(@StringRes text : Int){
        binding.buttonBaseList.setText(text)
    }

    fun isEnabledRefresh(enabled : Boolean){
        binding.swpRefresh.isEnabled = enabled
        binding.swpRefresh.isRefreshing = enabled
    }
}