package com.mobile.travelaja.module.settlement.view

import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.FragmentCreateSettlementBinding
import com.mobile.travelaja.databinding.TripDetailDialogBinding
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCameraCallback
import com.mobile.travelaja.module.settlement.FilePermissionListener
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import com.mobile.travelaja.module.settlement.viewmodel.SettlementViewModel
import com.mobile.travelaja.utility.Utils
import com.mobile.travelaja.module.settlement.view.OtherExpenseFragment.Companion.KEY_OTHER_EXPENSE_LIST
import com.mobile.travelaja.module.settlement.view.OtherExpenseFragment.Companion.KEY_EXPENSE_TYPE_LIST
import com.mobile.travelaja.module.settlement.view.IntercityTransportFragment.Companion.KEY_INTERCITY_TRANSPORTS
import com.mobile.travelaja.module.settlement.view.TripsListFragment.Companion.KEY_TRIP_CODE
import com.mobile.travelaja.module.settlement.view.TripsListFragment.Companion.KEY_TRIP_ID
import com.mobile.travelaja.module.settlement.view.TripsListFragment.Companion.KEY_CREATE_REIMBURSEMENT
import com.mobile.travelaja.module.settlement.view.adapter.AttachmentAdapter
import com.mobile.travelaja.module.settlement.view.adapter.TripsListAdapter.Companion.TYPE_SELECTED
import com.mobile.travelaja.module.settlement.view.adapter.TripsListAdapter.Companion.TYPE_DRAFT
import opsigo.com.domainlayer.model.settlement.*

import java.io.File
import java.util.jar.Manifest

class CreateSettlementFragment : Fragment(), View.OnClickListener, DialogCameraCallback {
    private lateinit var viewModel: SettlementViewModel
    private lateinit var binding: FragmentCreateSettlementBinding
    private val fileDialog = DialogCamera()
    private lateinit var adapter: AttachmentAdapter
    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>
    private var filePermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileDialog.setCallbak(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateSettlementBinding.inflate(inflater, container, false)
        bottomSheet = BottomSheetBehavior.from(binding.includeExpand.expandDetail)
        bottomSheet.isDraggable = false
        bottomSheet.addBottomSheetCallback(callbackDialog())
        return binding.root
    }

    private fun callbackDialog() = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            viewModel.isExpandDetail.set(newState == BottomSheetBehavior.STATE_EXPANDED)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            DefaultViewModelFactory(false, requireContext())
        ).get(SettlementViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.clickListener = this

        binding.increaseDecreaseView.setValue(1, 0, object : IncreaseDecreaseListener {
            override fun onChangeValue(value: Int) {
                viewModel.calculateOvernight(value)
            }
        })

        binding.checkboxDeclare.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.buttonNextEnabled.set(isChecked)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { t ->
                Utils.handleErrorMessage(requireContext(), t) { errorString ->
                    if (errorString.contains("code has been used", true)
                        || errorString == "This trip code has been used."
                    ) {
                        showDraftDialog()
                    } else
                        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.successSubmit.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { isSuccess ->
                if (isSuccess) {
                    navigateDetailDraft()
                }
            }
        }

        viewModel.dayValueEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { value ->
                binding.increaseDecreaseView.setValue(1, value)
            }
        }

        setFragmentResultListener(KEY_REQUEST) { k, b ->
            if (k == KEY_REQUEST) {
                val items = b.getParcelableArrayList<OtherExpense>(KEY_OTHER_EXPENSE_LIST)
                viewModel.addingOtherExpense(items?.toList()!!)
                b.getParcelableArray(KEY_EXPENSE_TYPE_LIST)?.also {
                    if (viewModel.typeExpense.isEmpty() && it.isNotEmpty()) {
                        viewModel.typeExpense = it as Array<ExpenseType>
                    }
                }
            }

        }
        fragmentResultIntercityTransport()
        fragmentResultTripCode()
        setRecyclerAttachment()
    }

    private fun setRecyclerAttachment() {
        adapter = AttachmentAdapter(binding.rvAttachment, viewModel)
        binding.rvAttachment.adapter = adapter
    }

    private fun fragmentResultIntercityTransport() {
        setFragmentResultListener(KEY_REQUEST_INTERCITY_TRANSPORT) { k, b ->
            if (k == KEY_REQUEST_INTERCITY_TRANSPORT) {
                val items = b.getParcelableArrayList<IntercityTransport>(KEY_INTERCITY_TRANSPORTS)
                viewModel.addingIntercityTransport(items?.toList()!!)
            }
        }
    }

    private fun fragmentResultTripCode() {
        setFragmentResultListener(KEY_REQUEST_TRIP_LIST) { k, b ->
            if (k == KEY_REQUEST_TRIP_LIST) {
                val isCreateReimbursement = b.getBoolean(KEY_CREATE_REIMBURSEMENT)
                if (isCreateReimbursement) {
                    viewModel.submitSettlement.value = DetailSettlement()
                } else {
                    val codeTrip = b.getString(KEY_TRIP_CODE)
                    val idTrip = b.getString(KEY_TRIP_ID)
                    viewModel.submitSettlement.value = DetailSettlement()
                    viewModel.getDetailSubmit()?.TripCode = codeTrip ?: ""
                    viewModel.tempTripId = idTrip ?: ""
//                    if (!idTrip.isNullOrEmpty()) {
//                        viewModel.getDetailTrip(idTrip)
//                    }
                }
            }
        }
    }

    private fun showWarning() {
        Toast.makeText(context, "City is Empty", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        filePermissionGranted = isCameraPermissionGranted() && isStoragePermissionGranted()
    }

    //Todo onclick
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.etBank -> navigateBank()
            R.id.ivBack -> activity?.finish()
            R.id.viewDetailInformation -> navigateTransportExpense()
            R.id.viewDetailOtherExpense -> navigateOtherExpense()
            R.id.viewIntercity -> navigateIntercity()
            R.id.switchExpense -> {
                if (v is SwitchMaterial) {
                    viewModel.isEnabledDetailExpense.set(v.isChecked)
                    if (v.isChecked)
                        navigateOtherExpense()
                    else {
                        showWarning(
                            R.string.warning,
                            R.string.alert_not_include_other_expense,
                            R.string.no,
                            R.string.yes,
                            WARNING_NOT_INCLUDE_OTHER_EXPENSE
                        )
                    }
                }
            }
            R.id.switchRefund -> {
                if (v is SwitchMaterial) {
                    if (v.isChecked) {
                        viewModel.addListRefunds()
                    } else {
                        showWarning(
                            R.string.warning,
                            R.string.alert_not_include_ticket_refund,
                            R.string.no,
                            R.string.yes,
                            WARNING_NOT_INCLUDE_TICKET_REFUND
                        )
                    }
                }
            }
            R.id.switchIntercity -> {
                if (v is SwitchMaterial) {
                    viewModel.isEnabledDetailIntercity.set(v.isChecked)
                    if (v.isChecked)
                        navigateIntercity()
                    else {
                        showWarning(
                            R.string.warning,
                            R.string.alert_not_include_intercity_transport,
                            R.string.no,
                            R.string.yes,
                            WARNING_NOT_INCLUDE_INTERCITY_TRANSPORT
                        )
                    }
                }
            }
            R.id.viewAlpha -> {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            R.id.buttonSubmit -> {
                val idTrip = viewModel.tempTripId
                if (idTrip.isNotEmpty()) {
                    viewModel.getDetailTrip()
                }
            }
            R.id.tvViewDetailTrip -> {
                val detail = viewModel.getDetailSubmit()
                showViewDetailDialog(detail!!)
            }
            R.id.switchTransportation -> {
                if (v is SwitchMaterial) {
                    if (v.isChecked) {
                        navigateTransportExpense()
                    } else {
                        showWarning(
                            R.string.warning,
                            R.string.alert_not_include_transport_expense,
                            R.string.no,
                            R.string.yes,
                            WARNING_NOT_INCLUDE_TRANSPORT_EXPENSE
                        )
                    }
                }
            }
            R.id.switchTraveling -> {
                if (v is SwitchMaterial) {
                    viewModel.checkedTravelling(v.isChecked)
                }
            }
            R.id.switchOvernight -> {
                if (v is SwitchMaterial) {
                    viewModel.checkedOverNight(v.isChecked)
                }
            }
            R.id.btnUploadEvidence -> {
                if (filePermissionGranted)
                fileDialog.show(childFragmentManager, "GettingFile")
                else{
                    (activity as FilePermissionListener).onPermissionInputFile(filePermissionGranted)
                }
            }
            R.id.linearLayoutExpandDetail -> {
                if (bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            R.id.tvLastDraft -> {
                navigateTripCode(TYPE_DRAFT)
            }
            R.id.buttonNext -> {
                viewModel.submit("Save")
            }
            else -> navigateTripCode(TYPE_SELECTED)
        }
    }

    private fun navigateTransportExpense() {
        val data = viewModel.getDetailSubmit()
        val cities = data?.cities()?.toTypedArray()
        val tripCode = data?.TripCode
        if (cities.isNullOrEmpty()) {
            val warning = "${getString(R.string.this_trip)} $tripCode ${getString(R.string.alert_not_have_route)}"
            showWarning(
                R.string.sorry,
                warning,
                -1,
                R.string.ok,
                WARNING_NO_ROUTE
            )
            return
        }
        val modeTransports = viewModel.modeTransports.toTypedArray()
        cities.let {
            val action =
                CreateSettlementFragmentDirections.actionCreateSettlementToTransportExpenseFragment(
                    cities,
                    modeTransports
                )
            findNavController().navigate(action)
        }
    }

    //Todo navigate intercity transport
    private fun navigateIntercity() {
        val detail = viewModel.getDetailSubmit()
        val golper = detail?.Golper ?: 0
        val items = detail?.OtherTransportExpenses?.toTypedArray()
        val total = detail?.TotalOtherTransportExpense.toString()
        val action = CreateSettlementFragmentDirections.actionCreateSettlementToIntercityTransportFragment(
                viewModel.routes.toTypedArray(),
                golper, items ?: arrayOf(), total
            )
        findNavController().navigate(action)
    }

    // Todo Navigate Other Expense
    private fun navigateOtherExpense() {
        val expenseTypes = viewModel.typeExpense
        val others = viewModel.getDetailSubmit()?.OtherExpense?.toTypedArray()
        val isPcu = viewModel.submitSettlement.value?.TripType?.contains("pcu", true) ?: false
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToOtherExpenseFragment(
                expenseTypes,
                others ?: arrayOf(),
                isPcu
            )
        findNavController().navigate(action)
    }

    private fun navigateBank() {
        val action =
            CreateSettlementFragmentDirections.actionCreateSettlementToBankListFragment()
        findNavController().navigate(action)
    }

    private fun navigateTripCode(viewType: Int) {
        val idString = viewModel.submitSettlement.value?.TripId
        val action = CreateSettlementFragmentDirections.actionCreateSettlementToTripCodeFragment(
            idString,
            viewType
        )
        findNavController().navigate(action)
    }

    private fun navigateTicketRefund() {
        val list = viewModel.tickets
        if (!list.isNullOrEmpty()) {
            val action =
                CreateSettlementFragmentDirections.actionCreateSettlementToTicketRefundFragment(list.toTypedArray())
            findNavController().navigate(action)
        } else {
            showWarning()
        }
    }


    private fun navigateDetailDraft() {
        val idTrip = viewModel.getDetailSubmit()?.TripId
        idTrip?.let {
            val action =
                CreateSettlementFragmentDirections.actionCreateSettlementToSettlementDetailDraftFragment(
                    it,true)
            findNavController().navigate(action)
            viewModel.isDraftLabelVisible.set(true)
        }
    }

    private fun showDraftDialog() {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val height = size.y / 2
        val inset = height / 2
        val v = layoutInflater.inflate(R.layout.dialog_tripid_isdraft, null)
        val alert = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.Theme_MaterialComponents_Dialog_MinWidth
        )
            .setView(v).setBackgroundInsetTop(0).setBackgroundInsetBottom(inset).show()
        v.findViewById<Button>(R.id.buttonCheckNow).setOnClickListener {
            alert.dismiss()
            navigateTripCode(TYPE_DRAFT)
            viewModel.isDraftLabelVisible.set(true)
        }
    }

    private fun showViewDetailDialog(detail: DetailSettlement) {
        val display = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val height = size.y / 2
        val inset = height / 2
        val v = layoutInflater.inflate(R.layout.trip_detail_dialog, null)
        val binding = TripDetailDialogBinding.bind(v)
        binding.data = detail
        val alert = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.Theme_MaterialComponents_Dialog_MinWidth
        ).setBackground(ColorDrawable(android.graphics.Color.TRANSPARENT)).setView(binding.root)
            .setBackgroundInsetBottom(inset)
        val dialog = alert.show()
        binding.ivClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun data(imagePath: String, file: File, type: String?) {
        val data = Attachment("", "", file.name, imagePath, false, type)
        val pos = adapter.itemCount
        viewModel.uploadFile(pos, data)
    }

    private fun showWarning(
        @StringRes title: Int,
        message: Any,
        @StringRes negativeName: Int,
        @StringRes positiveName: Int,
        type: Int
    ) {
        val alert = MaterialAlertDialogBuilder(requireContext())
        alert.setCancelable(false)
        alert.setTitle(title)
        if (message is String)
        alert.setMessage(message)
        else alert.setMessage(message as Int)
        alert.setPositiveButton(positiveName) { d, _ ->
            when (type) {
                WARNING_NO_ROUTE -> {
                    binding.switchTransportation.isChecked = false
                    d.dismiss()
                }
                WARNING_NOT_INCLUDE_TICKET_REFUND -> viewModel.getDetailSubmit()?.TicketRefunds?.clear()
                WARNING_NOT_INCLUDE_TRANSPORT_EXPENSE -> viewModel.clearTransportExpense()
                WARNING_NOT_INCLUDE_INTERCITY_TRANSPORT -> viewModel.clearTransportIntercity()
                WARNING_NOT_INCLUDE_OTHER_EXPENSE -> viewModel.clearOtherExpenses()
            }
        }
        if (negativeName != -1) {
            alert.setNegativeButton(negativeName) { d, _ ->
                when (type) {
                    WARNING_NOT_INCLUDE_TICKET_REFUND -> binding.switchRefund.isChecked = true
                    WARNING_NOT_INCLUDE_TRANSPORT_EXPENSE -> binding.switchTransportation.isChecked =
                        true
                    WARNING_NOT_INCLUDE_INTERCITY_TRANSPORT -> binding.switchIntercity.isChecked =
                        true
                    WARNING_NOT_INCLUDE_OTHER_EXPENSE -> binding.switchExpense.isChecked = true
                }
                d.dismiss()
            }

        }
        alert.show()
    }

    fun isCameraPermissionGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        )
    }

    fun isStoragePermissionGranted(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) && PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }




    companion object {
        const val KEY_REQUEST = "CREATE_SETTLEMENT"
        const val KEY_REQUEST_INTERCITY_TRANSPORT = "KEY_REQUEST_INTERCITY_TRANSPORT"
        const val KEY_REQUEST_TRIP_LIST = "CREATE_SETTLEMENT_TRIP_LIST"
        const val WARNING_NO_ROUTE = 1
        const val WARNING_NOT_INCLUDE_TICKET_REFUND = 2
        const val WARNING_NOT_INCLUDE_TRANSPORT_EXPENSE = 3
        const val WARNING_NOT_INCLUDE_INTERCITY_TRANSPORT = 4
        const val WARNING_NOT_INCLUDE_OTHER_EXPENSE = 5

    }


}