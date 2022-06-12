package com.sadxlab.movielist.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.sadxlab.movielist.R

class Utils
var dialog: Dialog? = null
fun pdialog(context: Context?) {
    try {
        if (dialog != null) if (dialog!!.isShowing) dialog!!.dismiss()
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.view_dialog)
        dialog!!.setCancelable(false)
        dialog!!.show()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun pdialog_dismiss() {
    try {
        if (dialog!!.isShowing) dialog!!.dismiss()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
