package com.mobile.travelaja.module.my_booking.refund

import java.util.*
import java.io.File
import android.app.*
import android.os.Bundle
import android.view.View
import com.mobile.travelaja.R
import android.widget.DatePicker
import android.widget.TimePicker
import android.view.LayoutInflater
import android.text.format.DateFormat
import org.koin.android.ext.android.inject
import com.mobile.travelaja.utility.Globals
import org.koin.core.parameter.parametersOf
import androidx.fragment.app.DialogFragment
import com.mobile.travelaja.utility.DateConverter
import androidx.recyclerview.widget.LinearLayoutManager
import opsigo.com.datalayer.datanetwork.GetDataTripPlane
import opsigo.com.domainlayer.callback.CallbackUploadFile
import com.mobile.travelaja.utility.OnclickListenerRecyclerView
import com.mobile.travelaja.databinding.DialogRescheduleBinding
import opsigo.com.domainlayer.model.create_trip_plane.UploadModel
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCamera
import com.mobile.travelaja.module.create_trip.newtrip.adapter.AttachmentAdapter
import com.mobile.travelaja.module.item_custom.button_default.ButtonDefaultOpsicorp
import com.mobile.travelaja.module.item_custom.dialog_camera.DialogCameraCallback

class RescheduleDialog(val isFlight : Boolean = true, val callback:CallbackRescheduleDialog, val isReschedule:Boolean = true ) : DialogFragment(),ButtonDefaultOpsicorp.OnclickButtonListener {

    private var _binding: DialogRescheduleBinding? = null
    private val binding get() = _binding!!
    var dataAttachment  = ArrayList<UploadModel>()
    var dialogCamera         = DialogCamera()
    val adapter  by inject<AttachmentAdapter> { parametersOf(dataAttachment)  }
    var selectDateFrom  = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogRescheduleBinding.inflate(LayoutInflater.from(context))
        setViewBinding()
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun setViewBinding() {
        if (!isReschedule){
            binding.lineDate.visibility = View.GONE
            binding.lineNote.visibility = View.GONE
            binding.lineDescriptionAttachment.visibility = View.VISIBLE
        }
        else {
            binding.lineDate.visibility = View.VISIBLE
            binding.lineNote.visibility = View.VISIBLE
            binding.lineDescriptionAttachment.visibility = View.GONE
        }

        binding.tvDepartureTime.setOnClickListener {
            if (!isFlight){
                selectDateFrom = 1
                showDatePicker()
            }else {
                showTimePicker()
            }
        }
        binding.tvDepartureDate.setOnClickListener {
            selectDateFrom = 0
            showDatePicker()
        }

        binding.lineUploadFile.setOnClickListener {
            dialogCamera.show(childFragmentManager,"dialog camera")
            dialogCamera.setCallbak(object : DialogCameraCallback {
                override fun data(imagePath: String,file:File,type : String?) {
                    addDataAttactment(imagePath,file)
                }
            })
        }

        binding.btnReschedule.callbackOnclickButton(this)

        if (!isFlight){
            binding.tvTitleDapartureDate.text = "Check in"
            binding.tvTitleDepartureTime.text = "Check out"
        }

        val layoutManager = LinearLayoutManager(context)
        binding.rvAttachment.layoutManager = layoutManager
        binding.rvAttachment.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        binding.rvAttachment.adapter = adapter

        adapter.setOnclickListener(object : OnclickListenerRecyclerView {
            override fun onClick(views: Int, position: Int) {
                when (views){
                    R.id.image_delet -> {
                        dataAttachment.removeAt(position)
                        adapter.setData(dataAttachment)
                    }
                    R.id.tv_failed -> {
                        uploadImage(position)
                    }
                }
            }
        })
    }

    private fun isSuccessUpload(): Boolean {
        return dataAttachment.filter { it.statusUploaded == "success" }.isNotEmpty()
    }

    private fun isLoadingUpload(): Boolean {
        return dataAttachment.filter { it.statusUploaded == "load" }.isNotEmpty()
    }

    fun addDataAttactment(imagePath: String,file: File) {
        val splitName = imagePath.split("/")
        val mData = UploadModel()
        mData.pathOriginalLocalImage = imagePath
        mData.pathLocalImage = splitName.get(splitName.size-1)
        mData.statusUploaded = "load"
        mData.file           = file
        dataAttachment.add(mData)
        adapter.setData(dataAttachment)
        uploadImage(dataAttachment.size-1)
    }

    fun uploadImage(position: Int){
        GetDataTripPlane(Globals.getBaseUrl(requireContext())).uploadFileReschedule(Globals.getToken(),
            Globals.getImageFile(requireContext(),dataAttachment[position].pathOriginalLocalImage,"file",dataAttachment[position].file!!),object :
                CallbackUploadFile {
            override fun successLoad(data: UploadModel) {
                dataAttachment[position].url = data.url
                dataAttachment[position].nameImage = data.nameImage
                dataAttachment[position].statusUploaded = "success"
                adapter.notifyItemChanged(position)
            }

            override fun failedLoad(message: String) {
                dataAttachment[position].statusUploaded = "failed"
                adapter.notifyItemChanged(position)
                Globals.setToast(message,requireContext())
            }
        })
    }

    val callbackTimePicker = object :TimePickerDialog.OnTimeSetListener{
        override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
            binding.tvDepartureTime.text = DateConverter().getDate("${p1}:${p2}","H:m","HH:mm")
        }
    }

    val callbackDatePickerDialog = object :DatePickerDialog.OnDateSetListener{
        override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
            when(selectDateFrom){
                0 -> {
                    binding.tvDepartureDate.text = DateConverter().getDate("${p1}-${p2}-${p3}","yyyy-MM-dd","dd MMM yyyy")
                }
                1 ->{
                    binding.tvDepartureTime.text = DateConverter().getDate("${p1}-${p2}-${p3}","yyyy-MM-dd","dd MMM yyyy")
                }
            }
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val style = AlertDialog.THEME_HOLO_DARK

        val datePicker = DatePickerDialog(requireContext(), style,callbackDatePickerDialog, year, month, day)
        datePicker.datePicker.setMinDate(c.getTimeInMillis())
        datePicker.show()
    }

    private fun showTimePicker() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val style = AlertDialog.THEME_HOLO_DARK
        TimePickerDialog(activity, style,callbackTimePicker, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onClicked() {
        if (isReschedule){
            if (binding.tvDepartureDate.text.toString().isEmpty()||binding.tvDepartureTime.text.toString().isEmpty()||binding.etNotes.text.toString().isEmpty()){
                Globals.setToast("Sorry ${getString(R.string.warning_canot_be_empty)}",requireContext())
            }else {
                if (!isLoadingUpload()){
                    if (isSuccessUpload()){
                        callback.dataReturn(dataAttachment,binding.tvDepartureDate.text.toString(),binding.tvDepartureTime.text.toString(),binding.tvNotesCount.text.toString())
                        dismiss()
                    }
                    else {
                        Globals.setToast(getString(R.string.please_upload_your_doc),requireContext())
                    }
                }
                else {
                    Globals.setToast(getString(R.string.waiting_upload_file),requireContext())
                }
            }
        }
        else {
            if (!isLoadingUpload()){
                if (isSuccessUpload()){
                    callback.dataReturn(dataAttachment,"","","")
                    dismiss()
                }
                else {
                    Globals.setToast(getString(R.string.please_upload_your_doc),requireContext())
                }
            }
            else {
                Globals.setToast(getString(R.string.waiting_upload_file),requireContext())
            }
        }
    }

    interface CallbackRescheduleDialog{
        fun dataReturn(mDataAttachment :ArrayList<UploadModel>,
                       mStartDate:String,
                       mEndDate: String,
                       mNotes:String)
    }

}