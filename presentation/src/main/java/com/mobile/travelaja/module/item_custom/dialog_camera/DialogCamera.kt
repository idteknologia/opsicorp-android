package com.mobile.travelaja.module.item_custom.dialog_camera

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.os.StrictMode
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Globals
import com.unicode.kingmarket.Base.BaseDialogFragment
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.dialog_camera.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class DialogCamera : BaseDialogFragment() {

    protected val CAMERA_REQUEST = 0
    protected val GALLERY_PICTURE = 1
    var imgFile: File ?= null
    private var pictureImagePath = ""
    private var type : String ?= null
    private var tempFilePath = ""
    lateinit var callbackDialog: DialogCameraCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pictureImagePath = ""
        type = ""
    }

    override fun getLayout(): Int {
        return R.layout.dialog_camera
    }

    override fun onMain(fragment: View, savedInstanceState: Bundle?) {
        setCornerDialog()
        setInitClickListener()
    }

    private fun setInitClickListener() {
        line_upload_file.setOnClickListener {
            if (img_upload_file.isVisible) {
                getImageFromGalery()
//                openDirectory()
            } else {
                if (imgFile != null && pictureImagePath.isNotEmpty()){
                    callbackDialog.data(pictureImagePath, imgFile!!,type)
                    dismiss()
                }
            }
        }
        btn_camera1.setOnClickListener {
            dispatchTakePicture()
        }
        btn_camera2.setOnClickListener {
            dispatchTakePicture()
        }
        image_selected.setOnClickListener {
            dispatchTakePicture()
        }
    }

    private fun setCornerDialog() {
        if (getDialog() != null && getDialog()?.getWindow() != null) {
            getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun changeButtonUploaded() {
        if (pictureImagePath.isNotEmpty() && img_upload_file.isVisible) {
            line_upload_file.setBackgroundResource(R.drawable.rounded_button_camera_uploaded)
            img_upload_file.isVisible = false
            title_upload_file.setTextColor(resources.getColor(R.color.white))
        } else {
            line_upload_file.setBackgroundResource(R.drawable.rounded_button_camera)
            img_upload_file.isVisible = true
            title_upload_file.setTextColor(resources.getColor(R.color.colorDarkGrayRound))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    setImageFileFromCamera()
//                  setImage(pictureImagePath)
                }
            }
            GALLERY_PICTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        println(data)
                        data?.data?.let { uri ->
                            val path = uri.path
                            println(path)
                            showFileGallery(uri)
                            /*if (uri.path != null && uri.path!!.contains("/raw/")) {
                                pictureImagePath = uri.path.toString().replace("/raw/", "")
                            } else {
                            }*/

                            if (pictureImagePath.contains("pdf") || pictureImagePath.contains("doc") || pictureImagePath.contains(
                                    "xls"
                                ) || pictureImagePath.contains("xlsx")
                            ) {
                                callbackDialog.data(pictureImagePath, File(pictureImagePath),type)
                                dismiss()
                            }


                        }
                    } catch (e: IOException) {
                        Globals.setLog("TAG", "Some exception $e")
                    }

                }
            }
        }
        if (resultCode == Activity.RESULT_OK && pictureImagePath.isNotEmpty()){
            changeButtonUploaded()
        }
    }


    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor =
            context?.getContentResolver()?.query(uri!!, projection, null, null, null) ?: return null
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s: String = cursor.getString(column_index)
        cursor.close()
        return s
    }

    private fun setImage(realPathFromURI: String) {
        setLog(realPathFromURI)
        imgFile = File(realPathFromURI)

        if (imgFile!!.exists()) {
            val myBitmap: Bitmap = BitmapFactory.decodeFile(imgFile!!.absolutePath)
            image_selected.setImageBitmap(myBitmap)
        }
    }

    private fun getImageFromGalery() {
//        val type = "*/*"//"image/*|application/pdf|application/doc/|application/docx/|application/xls/|application/xlsx/*"
        val pictureActionIntent = Intent(Intent.ACTION_PICK).apply {
            type = "*/*"
        } // */* setType("image/*")
        startActivityForResult(pictureActionIntent, GALLERY_PICTURE)

        //Todo getting pdf
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("application/pdf")
        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        startActivityForResult(intent,GALLERY_PICTURE)
    }

//    fun openDirectory() {
//        // Choose a directory using the system's file picker.
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
//            // Provide read access to files and sub-directories in the user-selected
//            // directory.
//            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//
//            // Optionally, specify a URI for the directory that should be opened in
//            // the system file picker when it loads.
//            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
//        }
//
//        startActivityForResult(intent, GALLERY_PICTURE)
//    }

    //Todo show file from Gallery
    private fun showFileGallery(uri: Uri) {
        val inputPfd: ParcelFileDescriptor?
        var size = 0L
        var name = ""
        var compress = 100
        try {
            inputPfd = context?.contentResolver?.openFileDescriptor(uri, "r")
            val type = context?.contentResolver?.getType(uri)
            this.type = type
            if (type.isNullOrEmpty()) {
                return
            }
            context?.contentResolver?.query(uri, null, null, null, null).use { cursor ->
                val s = cursor?.getColumnIndex(OpenableColumns.SIZE)
                val n = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor?.moveToFirst()
                if (s != null && n != null) {
                    size = cursor.getLong(s) / (1024 * 1024)
                    name = cursor.getString(n)
                    compress = getCompressSize(size)
                }

                val fd = inputPfd?.fileDescriptor ?: return
                val imageFolder = File(context?.cacheDir, "Opsicorp")
                if (!imageFolder.exists()) {
                    imageFolder.mkdir()
                }
                val imgFile = File(imageFolder, "opsicorp-$name")
                val fOut = FileOutputStream(imgFile)

                if (!type.contains("image")) {
                    if (size>1){
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.txt_failed_upload_file_size_is_to_large),
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    val input = FileInputStream(fd)
                    var read: Int
                    val bytes = ByteArray(4096)
                    while ((input.read(bytes).also { read = it }) != -1) {
                        fOut.write(bytes, 0, read)
                    }
                    pictureImagePath = imgFile.absolutePath
                    return
                }

                if (compress == -1){
                    return
                }
                val bitmap = getBitmapImage(fd)
                bitmap?.compress(Bitmap.CompressFormat.JPEG, compress, fOut)
                fOut.flush()
                fOut.close()
                this.imgFile = imgFile
                this.pictureImagePath = imgFile.absolutePath
//                image_selected.setImageURI(Uri.fromFile(imgFile))
                image_selected.setImageBitmap(bitmap)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return
        }
    }

    @Throws(IOException::class)
    private fun getBitmapImage(fd: FileDescriptor): Bitmap? {
        val b = BitmapFactory.decodeFileDescriptor(fd)
        return b
    }

    //Todo Take Picture
    private fun dispatchTakePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context?.packageManager!!)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${context?.packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}",
            ".jpg",
            storageDir
        ).apply {
            tempFilePath = absolutePath
        }
    }

    fun setImageFileFromCamera() {
        try {
            imgFile = File(tempFilePath)
            if (imgFile == null)
                return
            val size = imgFile!!.length()
            val s = size / (1024 * 1024)
            val compress = getCompressSize(s)
            if (compress == -1) return
            val bitmap = BitmapFactory.decodeFile(tempFilePath)
            val fOut = FileOutputStream(imgFile)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, compress, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }
        type = "image/jpeg"
        pictureImagePath = tempFilePath
        image_selected.setImageURI(Uri.parse(pictureImagePath))
    }

    private fun getCompressSize(sizeMb: Long): Int {
        var compressSize = 100
        when {
            sizeMb > 10 -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.txt_failed_upload_file_size_is_to_large),
                    Toast.LENGTH_SHORT
                ).show()
                return -1
            }
            sizeMb > 8 -> {
                compressSize = 10
            }
            sizeMb > 5 -> {
                compressSize = 20
            }
            sizeMb > 2 -> {
                compressSize = 30
            }
            sizeMb > 1 -> {
                compressSize = 50
            }
        }
        return compressSize
    }

    private fun getOpenCamera() {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "$timeStamp.jpg"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        pictureImagePath = storageDir.absolutePath + "/" + imageFileName
        val file = File(pictureImagePath)
        val outputFileUri = Uri.fromFile(file)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)

    }

    private fun getCrop(tempUri: Uri) {
        UCrop.of(
            tempUri,
            Uri.fromFile(File(Globals.getTmpDir(context), UUID.randomUUID().toString() + ".jpg"))
        )
            .withAspectRatio(16f, 16f)
            .start((context as Activity))
    }

    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ",
            arrayOf(filePath), null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(
                cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID)
            )
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                )
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

    fun setCallbak(callback: DialogCameraCallback) {
        callbackDialog = callback
    }


}