package vn.nms.sample.presentation.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExtendedBottomNavigationView(
    context     : Context,
    attrs       : AttributeSet?= null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    constructor (context : Context) : this(context, null, 0)

    constructor (context : Context, attrs : AttributeSet) : this(context, attrs, 0)

    private val selectedListeners = mutableListOf<(menuItem : MenuItem) -> Boolean>()

    fun addOnNavigationItemSelectedListener(listener: (menuItem : MenuItem) -> Boolean) {
        this.selectedListeners.add(listener)
        this.setOnNavigationItemSelectedListener {
            var result = false
            for (i in 0 until this.selectedListeners.size) {
                result = this.selectedListeners[i].invoke(it)
                if (result) {
                    continue
                } else {
                    break
                }
            }
            result
        }
    }
}