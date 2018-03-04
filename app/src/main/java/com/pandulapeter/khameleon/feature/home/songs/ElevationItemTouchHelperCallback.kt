package com.pandulapeter.khameleon.feature.home.songs

import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

abstract class ElevationItemTouchHelperCallback(
    private val activeElevationChange: Float,
    dragDirs: Int = 0,
    swipeDirs: Int = 0
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    private var isElevated = false
    private var originalElevation = 0f

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, false)
        if (isCurrentlyActive && !isElevated) {
            updateElevation(recyclerView, viewHolder, true)
        }
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        updateElevation(recyclerView!!, viewHolder, false)
    }

    private fun updateElevation(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, elevate: Boolean) {
        if (elevate) {
            originalElevation = ViewCompat.getElevation(holder.itemView)
            ObjectAnimator.ofFloat(
                holder.itemView,
                "Elevation",
                originalElevation,
                activeElevationChange + findMaxElevation(recyclerView)
            ).apply {
                duration = 150
            }.start()
            isElevated = true
        } else {
            ObjectAnimator.ofFloat(
                holder.itemView,
                "Elevation",
                ViewCompat.getElevation(holder.itemView),
                originalElevation
            ).apply {
                duration = 150
            }.start()
            originalElevation = 0f
            isElevated = false
        }
    }

    private fun findMaxElevation(recyclerView: RecyclerView) = (0 until recyclerView.childCount)
        .map { ViewCompat.getElevation(recyclerView.getChildAt(it)) }
        .max() ?: 0f
}