package com.mobile.travelaja.module.item_custom.dialog_camera

import java.io.File

interface DialogCameraCallback {
    fun data(imagePath: String,file:File,type : String?)
}