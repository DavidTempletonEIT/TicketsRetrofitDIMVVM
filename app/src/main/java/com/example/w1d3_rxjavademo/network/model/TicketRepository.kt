import com.example.w1d3_rxjavademo.network.ApiService

import com.example.w1d3_rxjavademo.network.model.Ticket
import com.example.w1d3_rxjavademo.network.model.Price
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

interface TicketRepository {
    //get latest news using safe api call
    fun getTicketList(from: String, to: String): Single<MutableList<Ticket>>

    fun getPrice(flightNum: String, from: String, to: String): Single<Price>

    fun getPriceObservable(ticket: Ticket): Observable<Ticket>

}