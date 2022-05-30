package com.korucu.exampleapp.Utils

import android.text.Editable
import android.text.TextUtils
import android.util.Patterns

object Utils {

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)