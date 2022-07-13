package com.example.foodRecipes.domain.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

class BottomViewBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0.0f, min(child.height.toFloat(), child.translationY + dy))
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        if (child.translationY > child.height / 2) {
            if (child.translationY.toInt() != child.height) {
                child.animate().translationY(child.height.toFloat()).start()
            }
        } else {
            if (child.translationY.toInt() != 0) {
                child.animate().translationY(0f).start()
            }
        }
    }
}
