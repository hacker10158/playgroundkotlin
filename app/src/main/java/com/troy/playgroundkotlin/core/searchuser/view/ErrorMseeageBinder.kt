package com.troy.playgroundkotlin.core.searchuser.view

import android.databinding.BindingAdapter
import android.widget.RelativeLayout
import android.widget.Toast
import com.troy.playgroundkotlin.R
import com.troy.playgroundkotlin.server.ErrorCode

@BindingAdapter("android:errorcode")
fun RelativeLayout.handleErrorCode( code : Int) {
    when (code) {
        ErrorCode.ERROR_CODE_DEFAULT -> { return }
        ErrorCode.ERROR_CODE_EMPTY_KEYWORD ->{ Toast.makeText(context, context.getString(R.string.error_message_empty_keyword) ,Toast.LENGTH_SHORT).show() }
        ErrorCode.ERROR_CODE_NO_RESULT ->{ Toast.makeText(context, context.getString(R.string.error_message_no_result) ,Toast.LENGTH_SHORT).show() }
        ErrorCode.ERROR_CODE_UNKNOWN ->{ Toast.makeText(context, context.getString(R.string.error_message_unknown_error) ,Toast.LENGTH_SHORT).show() }
    }
}