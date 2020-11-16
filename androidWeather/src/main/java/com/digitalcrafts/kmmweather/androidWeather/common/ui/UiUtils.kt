package com.digitalcrafts.kmmweather.androidWeather.common.ui

import android.content.res.ColorStateList
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.digitalcrafts.kmmweather.androidWeather.R
import com.google.android.material.button.MaterialButton

fun View.show() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.hide() {
    if (visibility != View.GONE) visibility = View.GONE
}

fun View.toggleVisibility(shouldShow: Boolean? = null) {
    when (shouldShow) {
        null -> {
            visibility = if (visibility != View.VISIBLE) View.VISIBLE
            else View.GONE
        }
        true -> show()
        false -> hide()
    }
}


@BindingAdapter("app:alphaToTint")
fun MaterialButton.setAlphaToTint(enabled: Boolean?) {
    alpha = if (enabled == true) 1f else 0.5f
    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, R.color.colorAccent))
    isClickable = enabled ?: false
}

@BindingAdapter("app:marqueeEnabled")
fun TextView.enableMarquee(marqueeEnabled: Boolean) {

    if (marqueeEnabled) {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        marqueeRepeatLimit = -1
        maxLines = 1
        isSelected = true
        isFocusable = true
        isFocusableInTouchMode = true
    }
}

fun View.enable() {
    isEnabled = true
    alpha = 1f
}

fun View.disable() {
    isEnabled = false
    alpha = 0.3f
}

@BindingAdapter("app:enabled")
fun View.enabled(enabled: Boolean) = if (enabled) enable() else disable()