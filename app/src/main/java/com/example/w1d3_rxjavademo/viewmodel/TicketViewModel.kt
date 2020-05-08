package com.example.w1d3_rxjavademo.viewmodel

import TicketRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.w1d3_rxjavademo.network.model.Price
import com.example.w1d3_rxjavademo.network.model.Ticket
import io.reactivex.disposables.CompositeDisposable

class TicketsViewModel(private val repository: TicketRepository): ViewModel() {

    private val disposable = CompositeDisposable()
    private val ticketLiveData = MutableLiveData<TicketState>()
    private val priceLiveData = MutableLiveData<PriceState>()

    sealed class PriceState {
        object LOADING: PriceState()
        data class SUCCESS(val price: Price): PriceState()
        data class ERROR(val errorMessage: String): PriceState()
    }

    fun getPriceState(): LiveData<PriceState> {
        return priceLiveData
    }

    sealed class TicketState {
        object LOADING: TicketState()
        data class SUCCESS(val list: MutableList<Ticket>): TicketState()
        data class ERROR(val errorMessage: String): TicketState()
    }

    fun getTicketState(): LiveData<TicketState> {
        return ticketLiveData
    }

    fun getTickets(from: String, to: String) {
        ticketLiveData.value = TicketState.LOADING
        disposable.add(
            repository
                .getTicketList(from, to)
                .subscribe({
                    ticketLiveData.value = TicketState.SUCCESS(it)
                },{
                    ticketLiveData.value = TicketState.ERROR(it.message!!)
                })
        )
    }

    fun getPrices(flightNumber: String, from: String, to: String) {
        priceLiveData.value = PriceState.LOADING
        disposable.add(
            repository
                .getPrice(flightNumber, from, to)
                .subscribe({
                    priceLiveData.value = PriceState.PSUCCESS(it)
                },{
                    priceLiveData.value = PriceState.ERROR(it.message!!)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}