package com.mobile.travelaja.module.settlement.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import opsigo.com.domainlayer.model.settlement.Ticket

class TicketRefundViewModel : ViewModel() {
    val ticketRefunds = mutableListOf<Ticket>()
    val isRemoveVisible = ObservableBoolean(false)

    fun setTicket(pos: Int, ticket: Ticket) {
        val t = ticketRefunds[pos]
        t.TicketNumber = ticket.TicketNumber
        t.Price = ticket.Price
        t.Category = ticket.Category
    }

    fun setRefundAmount(amount: Number, pos: Int) {
        val t = ticketRefunds[pos]
        t.Amount = amount
    }

    fun addItem() {
        ticketRefunds.add(Ticket())
        isRemoveVisible.set(true)
    }

    fun removeItem(pos: Int) {
        ticketRefunds.removeAt(pos)
        isRemoveVisible.set(ticketRefunds.size > 1)
    }

    fun getItem(pos: Int) : Ticket {
        return ticketRefunds[pos]
    }

}