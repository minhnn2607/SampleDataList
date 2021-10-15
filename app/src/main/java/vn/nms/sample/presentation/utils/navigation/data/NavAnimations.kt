package vn.nms.sample.presentation.utils.navigation.data

import vn.nms.sample.R

data class NavAnimations(
    val enterAnimation: Int = R.anim.animation_fade_enter,
    val exitAnimation: Int = R.anim.animation_fade_exit,
    val popEnterAnimation: Int = R.anim.animation_close_enter,
    val popExitAnimation: Int = R.anim.animation_close_exit
)
