package com.mobile.travelaja.base.list

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mobile.travelaja.BR
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.BaseListFragmentBinding
import com.mobile.travelaja.module.settlement.view.TransportExpenseFragment
import com.mobile.travelaja.utility.Utils

abstract class BaseListFragment<T : Any> : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    View.OnClickListener {
    private lateinit var alert: MaterialAlertDialogBuilder
    lateinit var binding: BaseListFragmentBinding
    private var snackbar: Snackbar? = null
    val isLoading = ObservableBoolean(false)
    val textBottomValue = ObservableField("")

    abstract fun baseListAdapter(): BaseListAdapter<T>

    abstract fun dividerEnabled(): Boolean

    abstract fun isSearchVisible(): Boolean

    abstract fun isButtonVisible(): Boolean

    abstract fun isButtonBottomVisible(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null)
            alert = MaterialAlertDialogBuilder(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BaseListFragmentBinding.inflate(inflater, container, false)
        binding.isSearch = isSearchVisible()
        binding.setVariable(BR.isLoading, isLoading)
        binding.setVariable(BR.textBottomValue,textBottomValue)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.includeBottom.root.isVisible = isButtonBottomVisible()
        binding.includeBottom.buttonBottom.setOnClickListener(this)
        binding.executePendingBindings()
    }

    fun setTitleName(@StringRes title: Int, @ColorRes color: Int = 0) {
        binding.tvTitle.setText(title)
        if (color != 0)
            binding.tvTitle.setTextColor(resources.getColor(color))
    }

    fun setSubtitle(@StringRes subtitle: Int) {
        binding.tvSubtitle.setText(subtitle)
    }

    fun showingTotal(@StringRes title : Int ?,@StringRes currency : Int?){
        binding.includeBottom.tvTitleTotal.isVisible = title != null
        binding.includeBottom.tvTotalValue.isVisible = title != null
        binding.includeBottom.tvTypePrice.isVisible = currency != null
        if (title != null){
            binding.includeBottom.tvTitleTotal.setText(title)
        }

        if (currency != null){
            binding.includeBottom.tvTypePrice.setText(currency)
        }
    }

    fun setHintSearch(hint: String) {
        binding.includeSearch.searchView.queryHint = hint
    }

    fun setSearchListener(listener: SearchView.OnQueryTextListener) {
        binding.includeSearch.searchView.setOnQueryTextListener(listener)
    }

    fun showError(t: Throwable, tryAgain: (View) -> Unit) {
        Utils.handleErrorMessage(requireContext(), t) { error ->
            snackbar = Snackbar.make(binding.root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.txt_try_again, tryAgain)
            snackbar?.show()
        }
    }

    fun showWarning(warning: String) {
        snackbar = Snackbar.make(binding.root, warning, Snackbar.LENGTH_SHORT)
        snackbar?.show()
    }

    override fun onStop() {
        super.onStop()
        hiddenError()
    }

    fun hiddenError() {
        if (snackbar != null && snackbar!!.isShown) {
            snackbar?.dismiss()
        }
    }

    fun isRefreshing(isRefresh: Boolean) {
        binding.swpRefresh.isRefreshing = isRefresh
    }

    fun setButtonText(@StringRes text: Int) {
        binding.buttonBaseList.setText(text)
    }

    fun isEnabledRefresh(enabled: Boolean) {
        binding.swpRefresh.isEnabled = enabled
        binding.swpRefresh.isRefreshing = enabled
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    fun showWarning(@StringRes text: Int, @StringRes textAction: Int?) {
        snackbar?.setText(text)
        if (textAction != null) {
            snackbar?.setAction(textAction) {
                snackbar?.dismiss()
            }
        }
        snackbar?.show()
    }

    fun showingWarning(
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes negativeName: Int,
        @StringRes positiveName: Int,
        type: Int
    ) {
        alert.setTitle(title).setMessage(message)
        alert.setPositiveButton(positiveName) { d, _ ->
            onWarningClick(d, type, true)
        }
        if (negativeName != -1) {
            alert.setNegativeButton(negativeName) { d, _ ->
                onWarningClick(d, type, false)
            }
        }
        alert.show()
    }

    open fun onWarningClick(dialogInterface: DialogInterface, type: Int, isPositive: Boolean) {

    }

    // Todo scrolling nested for index item
    fun scrollNestedByIndexItem(index: Int) {
        val y = binding.rvBaseList.getChildAt(index).y - 30f
        binding.nested.scrollTo(0, y.toInt())
    }
}