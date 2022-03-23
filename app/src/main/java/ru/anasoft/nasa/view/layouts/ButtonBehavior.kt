package ru.anasoft.nasa.view.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ButtonBehavior(context: Context, attr: AttributeSet?=null): CoordinatorLayout.Behavior<View>(context,attr) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    )= dependency is AppBarLayout


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        val bar = dependency as AppBarLayout
        val barHeight = bar.height.toFloat()
        val barY = bar.y

        if (abs(barY) > barHeight * 0.7){
            child.visibility = View.GONE
        }
        else {
            child.visibility = View.VISIBLE
            child.alpha = 1 - abs(barY) / barHeight
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }
}