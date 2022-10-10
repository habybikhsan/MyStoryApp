package com.example.myStoryApp.customview.editText

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.myStoryApp.R

class PasswordEditText : AppCompatEditText{
    private lateinit var emailIcon: Drawable
    var isPassValid: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        emailIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_vpn_key_24) as Drawable
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = emailIcon,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
    private fun checkPass() {
        val pass = text?.trim()
        when {
            pass.isNullOrEmpty() -> {
                isPassValid = false
                error = resources.getString(R.string.input_pass)
            }
            pass.length < 6 -> {
                isPassValid = false
                error = resources.getString(R.string.pass_length)
            }
            else -> {
                isPassValid = true
            }
        }
    }
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) checkPass()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Masukkan Password Anda"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}