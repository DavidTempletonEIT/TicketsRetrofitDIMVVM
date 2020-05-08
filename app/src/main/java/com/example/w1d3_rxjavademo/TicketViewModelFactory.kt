package com.example.w1d3_rxjavademo

import TicketRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.w1d3_rxjavademo.view.TicketViewModel

class TicketViewModelFactory(private val ticketRepository: TicketRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TicketViewModel(ticketRepository) as T
    }}