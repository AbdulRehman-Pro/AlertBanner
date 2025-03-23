package com.rehman.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class AlertBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {


    companion object {
        const val LENGTH_SHORT = 0
        const val LENGTH_LONG = 1
        const val LENGTH_INDEFINITE = -1

        /**
         * Hides the error banner using animation.
         */
        fun hideBanner(alertBanner: AlertBanner) {
            alertBanner.onDismissCallback?.invoke()
            alertBanner.onDismissCallback = null
            alertBanner.cancelAutoDismiss()
            alertBanner.slideOut()
        }

    }


    @Retention(AnnotationRetention.SOURCE)
    @IntDef(LENGTH_SHORT, LENGTH_LONG, LENGTH_INDEFINITE)
    annotation class Duration


    private var animationType: Int = 0 // Default animation direction


    private val alertMessage: TextView
    private val leadingIcon: ImageView
    private val trailingIcon: ImageView
    private var dismissHandler: Handler = Handler(Looper.getMainLooper())
    private var dismissRunnable: Runnable? = null
    private var onDismissCallback: (() -> Unit)? = null


    init {
        // Inflate the banner layout from XML (error_banner.xml)
        inflate(context, R.layout.alert_banner, this)
        orientation = HORIZONTAL
        visibility = GONE


        alertMessage = findViewById(R.id.tvAlertMessage)
        leadingIcon = findViewById(R.id.ivLeadingIcon)
        trailingIcon = findViewById(R.id.ivTrailingIcon)


        // Retrieve custom attributes
        context.theme.obtainStyledAttributes(attrs, R.styleable.AlertBanner, 0, 0).apply {
            try {

                val defaultBackgroundColor =
                    ContextCompat.getColor(context, R.color.default_alert_background)
                val defaultSurfaceColor =
                    ContextCompat.getColor(context, R.color.default_alert_surface)

                val backgroundColorAttr =
                    getColor(R.styleable.AlertBanner_bannerBackgroundColor, defaultBackgroundColor)
                val surfaceColorAttr =
                    getColor(R.styleable.AlertBanner_bannerSurfaceColor, defaultSurfaceColor)
                val fontFamilyAttr = getResourceId(R.styleable.AlertBanner_bannerFontFamily, -1)

                val leadingIconAttr =
                    getResourceId(R.styleable.AlertBanner_bannerLeadingIcon, R.drawable.ic_leading)
                val trailingIconAttr =
                    getResourceId(
                        R.styleable.AlertBanner_bannerTrailingIcon,
                        R.drawable.ic_trailing
                    )

                animationType = getInt(R.styleable.AlertBanner_bannerAnimate, 0)


                val defaultTextSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics
                ) // Default 12sp

                val defaultIconSize = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics
                ) // Default 16dp

                val textSizeAttr =
                    getDimension(R.styleable.AlertBanner_bannerTextSize, defaultTextSize)
                val iconSizeAttr =
                    getDimension(R.styleable.AlertBanner_bannerIconSize, defaultIconSize)


                setBackgroundColor(backgroundColorAttr)

                alertMessage.setTextColor(surfaceColorAttr)
                alertMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeAttr)

                leadingIcon.apply {
                    setColorFilter(surfaceColorAttr)
                    setImageDrawable(ContextCompat.getDrawable(context, leadingIconAttr))
                    layoutParams.height = iconSizeAttr.toInt()
                    layoutParams.width = iconSizeAttr.toInt()
                    requestLayout()
                }

                trailingIcon.apply {
                    setColorFilter(surfaceColorAttr)
                    setImageDrawable(ContextCompat.getDrawable(context, trailingIconAttr))
                    layoutParams.height = iconSizeAttr.toInt()
                    layoutParams.width = iconSizeAttr.toInt()
                    requestLayout()
                }


                if (fontFamilyAttr != -1) {
                    alertMessage.typeface = ResourcesCompat.getFont(context, fontFamilyAttr)
                }

            } finally {
                recycle()
            }
        }


    }

    /**
     * Displays the alert banner with the specified message.
     * @param alertMessage The alert message to display.
     * @param duration Duration before auto-dismiss.
     * @param onDismiss Optional callback executed when the trailing icon is clicked.
     */
    fun showBanner(
        alertMessage: String,
        @Duration duration: Int = LENGTH_SHORT, // Enforce only allowed values
        onDismiss: (() -> Unit)? = null,
    ) {
        this.alertMessage.text = alertMessage
        this.onDismissCallback = onDismiss
        trailingIcon.visibility = if (duration == LENGTH_INDEFINITE) View.VISIBLE else View.GONE

        trailingIcon.setOnClickListener {
            if (trailingIcon.visibility == VISIBLE) Companion.hideBanner(this)

        }

        slideIn()
        scheduleAutoDismiss(duration)

    }


    private fun slideIn() {
        post {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED) // Ensure measurement

            when (animationType) {
                0 -> {
                    translationY = -measuredHeight.toFloat()
                }

                1 -> {
                    translationY = measuredHeight.toFloat()
                }
            }

            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .translationY(0f) // Slide to normal position
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(AccelerateDecelerateInterpolator()) // Smooth animation
                .start()

        }
    }

    private fun slideOut() {

        val targetTranslationY = when (animationType) {
            0 -> -measuredHeight.toFloat()
            1 -> measuredHeight.toFloat()
            else -> -measuredHeight.toFloat()
        }

        animate()
            .translationY(targetTranslationY)
            .alpha(0f)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator()) // Smooth exit
            .withEndAction {
                visibility = View.GONE // Hide after animation

            }
            .start()

    }


    private fun scheduleAutoDismiss(duration: Int) {
        cancelAutoDismiss()
        if (duration == LENGTH_INDEFINITE) return
        val delay = if (duration == LENGTH_SHORT) 3000L else 5000L
        dismissRunnable = Runnable { Companion.hideBanner(this) }
        dismissHandler.postDelayed(dismissRunnable!!, delay)
    }

    private fun cancelAutoDismiss() {
        dismissRunnable?.let {
            dismissHandler.removeCallbacks(it)
            dismissRunnable = null
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAutoDismiss() // Ensure runnable is removed
    }

}