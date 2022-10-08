package vn.nms.sample.presentation.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import vn.nms.sample.R

class AppNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var tvLeft: TextView? = null
    private var tvRight: TextView? = null
    private var imgLeft: ImageView? = null
    private var imgRight: ImageView? = null

    init {
        val layoutId: Int
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AppNavigationBar)
        val type = attributes.getInt(R.styleable.AppNavigationBar_anb_type, 0)
        layoutId = when (type) {
            0 -> R.layout.navigation_bar_back
            else -> R.layout.navigation_bar_left_right
        }
        val isCenterTitle =
            attributes.getBoolean(R.styleable.AppNavigationBar_anb_center_title, false)
        val isShowBack =
            attributes.getBoolean(R.styleable.AppNavigationBar_anb_show_back, true)
        val titleText = attributes.getString(R.styleable.AppNavigationBar_anb_title)
        val leftText = attributes.getString(R.styleable.AppNavigationBar_anb_left_text)
        val rightText = attributes.getString(R.styleable.AppNavigationBar_anb_right_text)
        val leftImage = attributes.getResourceId(R.styleable.AppNavigationBar_anb_left_image, 0)
        val rightImage = attributes.getResourceId(R.styleable.AppNavigationBar_anb_right_image, 0)

        val view = LayoutInflater.from(context).inflate(layoutId, this, true)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)

        if (isCenterTitle) {
            val param = tvTitle.layoutParams as ConstraintLayout.LayoutParams
            param.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            tvTitle.gravity = Gravity.CENTER
            tvTitle.setPadding(paddingStart, paddingTop, paddingStart, paddingTop)
        }
        tvLeft = view.findViewById(R.id.tvLeft)
        tvRight = view.findViewById(R.id.tvRight)
        imgLeft = view.findViewById(R.id.imgLeft)
        imgRight = view.findViewById(R.id.imgRight)

        if (!titleText.isNullOrEmpty()) {
            tvTitle?.text = titleText
        }
        if (!leftText.isNullOrEmpty()) {
            tvLeft?.text = leftText
            tvLeft?.visibility = View.VISIBLE
        } else {
            tvLeft?.visibility = View.GONE
        }
        if (!rightText.isNullOrEmpty()) {
            tvRight?.text = rightText
            tvRight?.visibility = View.VISIBLE
        } else {
            tvRight?.visibility = View.GONE
        }
        if (leftImage != 0) {
            tvLeft?.visibility = View.GONE
            imgLeft?.visibility = View.VISIBLE
            imgLeft?.setImageResource(leftImage)
        }
        if (rightImage != 0) {
            tvRight?.visibility = View.GONE
            imgRight?.visibility = View.VISIBLE
            imgRight?.setImageResource(rightImage)
        }
        if (!isShowBack) {
            imgLeft?.visibility = View.GONE
        }
        attributes.recycle()
    }

    fun setTitle(title: String) {
        findViewById<TextView>(R.id.tvTitle)?.text = title
    }

    fun setRightText(text: String) {
        findViewById<TextView>(R.id.tvRight).text = text
    }

    fun setOnClickLeftMenu(onClickListener: OnClickListener?) {
        tvLeft?.setOnClickListener(onClickListener)
        imgLeft?.setOnClickListener(onClickListener)
    }

    fun setOnClickRightMenu(onClickListener: OnClickListener?) {
        tvRight?.setOnClickListener(onClickListener)
        imgRight?.setOnClickListener(onClickListener)
    }
}
