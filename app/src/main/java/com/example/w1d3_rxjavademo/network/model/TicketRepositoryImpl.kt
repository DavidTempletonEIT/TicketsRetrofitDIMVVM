package com.example.w1d3_rxjavademo.network.model

import TicketRepository
import com.example.w1d3_rxjavademo.network.ApiService
import com.example.w1d3_rxjavademo.network.model.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class TicketRepositoryImpl(private val ticketService: ApiService):TicketRepository{
    override fun getTicketList(from: String, to:String): Single<List<Ticket>>{
        return ticketService
            .getTickets(from,to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getPrice(flightNum:String,from:String,to:String): Single<Price>{
        return ticketService
            .getPrice(flightNum,from,to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getPriceObservable(ticket: Ticket): Observable<Ticket> {
        TODO("Not yet implemented")
    }


}