package com.pandulapeter.khameleon.util

import android.content.Context
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.pandulapeter.khameleon.R

fun Context.color(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Context.dimension(@DimenRes dimensionId: Int) = resources.getDimensionPixelSize(dimensionId)

fun Context.drawable(@DrawableRes drawableId: Int) = AppCompatResources.getDrawable(this, drawableId)

fun View.showSnackbar(@StringRes message: Int) = showSnackbar(context.getString(message))

fun View.showSnackbar(message: String) = Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()

fun View.showSnackbar(message: String, undoAction: () -> Unit) = Snackbar.make(this, message, Snackbar.LENGTH_LONG).setAction(R.string.undo, { undoAction() }).show()

fun Fragment.setArguments(bundleOperations: (Bundle) -> Unit): Fragment {
    arguments = Bundle().apply { bundleOperations(this) }
    return this
}

inline fun EditText.onTextChanged(crossinline listener: (String) -> Unit) = addTextChangedListener(object : TextWatcher {

    override fun afterTextChanged(text: Editable?) = listener(text.toString())

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
})

inline fun ObservableBoolean.onPropertyChanged(fragment: Fragment? = null, crossinline callback: (Boolean) -> Unit) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (fragment?.isAdded != false) {
                callback(get())
            }
        }
    })
}
