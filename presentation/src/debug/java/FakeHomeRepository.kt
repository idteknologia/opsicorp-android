import android.content.Context
import com.google.gson.Gson
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.home.repository.HomeRepository
import opsigo.com.datalayer.model.result.Result
import opsigo.com.domainlayer.model.trip.TripResult
import java.io.IOException

/*
class FakeHomeRepository(private val context: Context) : HomeRepository {
    private  var result : TripResult
    private var isError = false

    init {
//        val resultStr = TestUtil.jsonFromRaw(context, R.raw.trip_schedule)
        result = Gson().fromJson("",TripResult::class.java)
    }

    override suspend fun getTrip(query: MutableMap<String, String>): Result<TripResult> {
        if (isError)
            return Result.Error(IOException())
        return Result.Success(result)
    }

    fun getData(){

    }
}*/
