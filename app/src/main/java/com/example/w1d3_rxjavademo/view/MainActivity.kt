package com.example.w1d3_rxjavademo.view

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.Observer
import com.example.w1d3_rxjavademo.R
import com.example.w1d3_rxjavademo.TicketViewModelFactory
import com.example.w1d3_rxjavademo.inject.Injection
import com.example.w1d3_rxjavademo.network.model.Price
import com.example.w1d3_rxjavademo.network.model.Ticket
import com.example.w1d3_rxjavademo.viewmodel.TicketsViewModel
import com.example.w1d3_rxjavademo.viewmodel.TicketViewModelFactory

import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private val from = "DEL"
    private val to = "HYD"

    // CompositeDisposable is used to dispose the subscriptions in onDestroy() method.
    private val disposable = CompositeDisposable()
    val injection = Injection()
    lateinit var mAdapter: TicketsAdapter
    private var ticketsList: MutableList<Ticket> = mutableListOf()
    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: TicketViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "$from > $to"


        viewModel = ViewModelProvider(
            this,
            TicketViewModelFactory(injection.provideUserRepo(context = applicationContext))
        ).get(TicketViewModel::class.java)

        viewModel.stateLiveData.observe(this, Observer { appState ->
            when (appState) {
                is TicketViewModel.AppState.LOADING -> displayLoading()


                is TicketViewModel.AppState.SUCCESS -> displayTickets(appState.ticketList)

                is TicketViewModel.AppState.PSUCCESS -> displayPrice(appState.price)

                is TicketViewModel.AppState.ERROR -> displayMessage(appState.message)
                else -> displayMessage("Something Went Wrong... Try Again.")
            }
        })
        initRecyclerView()



        fun onTicketSelected(ticket: Ticket) {
            Toast.makeText(this, "Clicked: ${ticket?.flightNumber}", Toast.LENGTH_LONG).show()
        }

        fun dpToPx(i: Int): Int {
            val r: Resources = resources
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                i.toFloat(),
                r.displayMetrics
            ).roundToInt()
        }

        fun initRecyclerView() {
            mAdapter = TicketsAdapter(ticketsList) { ticket: Ticket -> onTicketSelected(ticket) }
            val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.layoutManager = mLayoutManager
            recyclerView.addItemDecoration(GridSpacingItemDecoration(1, dpToPx(5), true))
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = mAdapter
        }


        /**
         * Making Retrofit call to get single ticket price
         * get price HTTP call returns Price object, but
         * map() operator is used to change the return type to Ticket
         */


        fun showError() {
            Log.e("", "showError: ")
        }

        fun onDestroy() {
            super.onDestroy()
            disposable.dispose()
        }


        fun displayPrice(price: Price) {

            for (ticket in ticketsList) {
                if (ticket.flightNumber == price.flightNumber) {
                    ticket.price = price
                }
                val position = ticketsList.indexOf(ticket)
                mAdapter.updatePrice(ticket, position)
            }
        }

        fun displayLoading() {
            Log.d("myTagload", "loading----");

        }

        fun displayMessage(message: String) {
            Log.d("myTagMessage", "message");
        }


    }

    private fun displayMessage(message: String) {

    }

    private fun displayPrice(price: Any) {

    }

    private fun displayTickets(ticketList: MutableList<Ticket>) {

    }

    private fun displayLoading() {
        TODO("Not yet implemented")
    }
}