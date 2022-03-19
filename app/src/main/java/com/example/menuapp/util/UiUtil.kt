package com.example.menuapp.util

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.menuapp.R
import com.example.menuapp.base.BaseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class UiUtil(private val context: Context) {

    private lateinit var snackbar: Snackbar
    private lateinit var progressDialog: ProgressDialog

    fun showMessage(
        message: String,
        length: Int = Snackbar.LENGTH_SHORT,
        button: Boolean = false,
        buttonText: String = "Ok"
    ) {
        showSnackBar(message, length, button, buttonText)
    }

    fun showToast(
        message: String,
        length: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, message, length)
            .show()
    }

    fun singleLinedInputTextDialog(
        title: String,
        inputText: String = "",
        positiveButtonText: String = "Ok",
        negativeButtonText: String = context.getString(R.string.cancel),
        onPositive: (text: String) -> Unit,
        onNegative: () -> Unit = {},
        onDismiss: () -> Unit = {},
        onShow: () -> Unit = {}
    ) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(title)

        // Set up the input
        val container = LinearLayout(context)
        container.orientation = LinearLayout.VERTICAL
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val marginOffset = 15
        lp.setMargins(marginOffset, 0, marginOffset, 0)
        val input = EditText(context)
        input.layoutParams = lp
        input.setText(inputText)
        input.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(80))
        container.addView(input)
        builder.setView(container)

        builder.setPositiveButton(positiveButtonText) { _, _ ->
            onPositive(input.text.toString())
        }
        builder.setOnDismissListener {
            onDismiss()
        }
        builder.setNegativeButton(negativeButtonText) { _, _ ->
            onNegative()
        }
        builder.show()
        onShow()
    }


    fun showProgress() {
        if (!::progressDialog.isInitialized) {
            progressDialog = ProgressDialog(context)
        }
        progressDialog.show()
    }

    fun hideProgress() {
        if (!::progressDialog.isInitialized) {
            progressDialog = ProgressDialog(context)
            return
        }
        progressDialog.dismiss()
    }

    private fun showSnackBar(
        message: String,
        snackBarLength: Int = Snackbar.LENGTH_LONG,
        button: Boolean = false,
        buttonText: String = "Ok"
    ) {
        if (!::snackbar.isInitialized) {
            (context as BaseActivity<*, *>).getLayoutBinding()
                .root.let {
                    snackbar = Snackbar.make(it, message, snackBarLength)
                }
        }
        if (button) {
            snackbar.setAction(buttonText) {}
        }

        snackbar.setText(message)
        snackbar.show()
    }

}