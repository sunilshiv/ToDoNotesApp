package com.todo.notesapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object NotesUtils {

    fun hideKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    @JvmStatic
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

