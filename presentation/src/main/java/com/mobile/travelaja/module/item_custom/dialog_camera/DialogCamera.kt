package com.mobile.travelaja.module.item_custom.dialog_camera

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.View
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import com.squareup.picasso.Picasso
import com.unicode.kingmarket.Base.BaseDialogFragment
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.dialog_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DialogCamera : BaseDialogFragment() {
    override fun getLayout(): Int { return R.layout.dialog_camera }

    protected val CAMERA_REQUEST  = 0
    protected val GALLERY_PICTURE = 1
    var pictureImagePath          = ""
    lateinit var callbackDialog   : DialogCameraCallback

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        setCornerDialog()
        setInitClickListener()
    }

    private fun setInitClickListener() {
        line_upload_file.setOnClickListener {
            if(line_upload_file.background.constantState == resources.getDrawable(R.drawable.rounded_button_camera).constantState){
                getImageFromGalery()
            }
            else {
                callbackDialog.data(pictureImagePath)
                dismiss()
            }
        }
        btn_camera1.setOnClickListener {
            getOpenCamera()
        }
        btn_camera2.setOnClickListener {
            getOpenCamera()
        }
        image_selected.setOnClickListener {
            getOpenCamera()
        }
    }

    private fun setCornerDialog() {
        if (getDialog() != null && getDialog()?.getWindow() != null) {
            getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun changeButtonUploaded(){
        if (line_upload_file.background.constantState==resources.getDrawable(R.drawable.rounded_button_camera).constantState){
            line_upload_file.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_button_camera_uploaded))
            img_upload_file.visibility = View.GONE
            title_upload_file.setTextColor(resources.getColor(R.color.white))
        }
        else {
            line_upload_file.background = resources.getDrawable(R.drawable.rounded_button_camera)
            title_upload_file.setTextColor(resources.getColor(R.color.colorDarkGrayRound))
            img_upload_file.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CAMERA_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    changeButtonUploaded()
                    setImage(pictureImagePath)
                }
            }

            GALLERY_PICTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    changeButtonUploaded()
                    val selectedImage = data!!.data
                    if (selectedImage != null) {
                        if (selectedImage.path!!.contains("/raw/")){
                            pictureImagePath = selectedImage.path.toString().replace("/raw/","")
                        } else {
                            pictureImagePath  = selectedImage.path!!
                        }
                    }

                    try {
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                        image_selected.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        Globals.setLog("TAG", "Some exception $e")
                    }

                }
            }

        }

    }

    private fun setImage(realPathFromURI: String) {
//        setLog(realPathFromURI)
//        val f = File(realPathFromURI)
//        Picasso.get()
//                .load(f)
//                .into(image_selected)

        setLog(realPathFromURI)
        val imgFile = File(realPathFromURI)

        if (imgFile.exists()) {
            val myBitmap: Bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            image_selected.setImageBitmap(myBitmap)
        }
    }

    private fun getImageFromGalery() {
        val pictureActionIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*")
        startActivityForResult(
                Intent.createChooser(pictureActionIntent, "Select File"),
                GALLERY_PICTURE)
    }

    private fun getOpenCamera() {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "$timeStamp.jpg"
        val storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)
        pictureImagePath = storageDir.absolutePath + "/" + imageFileName
        val file = File(pictureImagePath)
        val outputFileUri = Uri.fromFile(file)
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)

    }

    private fun getCrop(tempUri: Uri) {
        UCrop.of(tempUri, Uri.fromFile(File(Globals.getTmpDir(context), UUID.randomUUID().toString() + ".jpg")))
                .withAspectRatio(16f, 16f)
                .start((context as Activity))
    }

    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf(filePath), null)

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                return null
            }
        }
    }

    fun getRealPathFromURI(uri: Uri?): String {
        val cursor = uri?.let { activity?.getContentResolver()?.query(it, null, null, null, null) }
        cursor?.moveToFirst()
        val idx = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor?.getString(idx!!)!!
    }

    fun setCallbak(callback: DialogCameraCallback){
        callbackDialog = callback
    }

}