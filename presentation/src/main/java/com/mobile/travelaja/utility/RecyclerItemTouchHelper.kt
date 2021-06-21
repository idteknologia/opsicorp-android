package com.mobile.travelaja.utility

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import com.mobile.travelaja.module.approval.adapter.ApprovalAdapter
import java.lang.Exception

class RecyclerItemTouchHelper(dragDirs: Int, swipeDirs: Int, private val listener: RecyclerItemTouchHelperListener) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, actionState: Int) {
        try {
            if (viewHolder != null) {
                val foregroundView = (viewHolder as ApprovalAdapter.WaitingAdapter).viewForeground

                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foregroundView)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView,
                                 viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, dX: Float, dY: Float,
                                 actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView = (viewHolder as ApprovalAdapter.WaitingAdapter).viewForeground
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
        val foregroundView = (viewHolder as ApprovalAdapter.WaitingAdapter).viewForeground
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foregroundView)
    }

    override fun getSwipeDirs(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder): Int {
        if (viewHolder is ApprovalAdapter.ListApprovalAdapter) return 0
        return super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onChildDraw(c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView,
                             viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, dX: Float, dY: Float,
                             actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView = (viewHolder as ApprovalAdapter.WaitingAdapter).viewForeground

        ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}