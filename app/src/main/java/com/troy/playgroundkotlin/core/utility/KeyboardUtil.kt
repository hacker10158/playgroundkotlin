package com.troy.playgroundkotlin.core.utility

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class KeyboardUtil {
    companion object {
        fun hideKeyboard(activity: Activity?) {
            activity?.let {
                val decorView = it.window.decorView
                val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(decorView.windowToken, 0)
            }
        }
    }
}