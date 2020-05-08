package com.example.w1d3_rxjavademo.view


import TicketRepository
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w1d3_rxjavademo.network.model.Ticket
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import io.reactivex.disposables.CompositeDisposable
import rx.Notification
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class TicketViewModel constructor(private val ticketRepository: TicketRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val stateMutableLiveData = MutableLiveData<AppState>()
    val stateLiveData: LiveData<AppState> get() = stateMutableLiveData
    var loaded = false

    fun getTickets(from: String, to: String) {

        stateMutableLiveData.value = AppState.LOADING
        disposable.add(
            ticketRepository.getTicketList(from, to).subscribe({
                loaded = true
                if (it.isEmpty()) {
                    stateMutableLiveData.value = AppState.ERROR("No Definitions Retrieved")
                } else {
                    stateMutableLiveData.value = AppState.SUCCESS(it)
                }
            }, {
                loaded = true
                //errors
                val errorString = when (it) {
                    is UnknownHostException -> "No Internet Connection"
                    else -> it.localizedMessage
                }
                stateMutableLiveData.value = AppState.ERROR(errorString)
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
    sealed class AppState {
        object LOADING : AppState()
        data class SUCCESS(val ticketList: MutableList<Ticket>) : AppState()
        data class ERROR(val message: String) : AppState()
    }

}
