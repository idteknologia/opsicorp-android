import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.opsigo.travelaja.R
import com.opsigo.travelaja.module.create_trip.newtrip_pertamina.viewmodel.CityRepository
import opsigo.com.datalayer.model.result.City
import opsigo.com.datalayer.model.result.Result

//class FakeCityRepository(private val context: Context) : CityRepository {
//    private  var cities : List<City>
//    private val list  = mutableListOf<City>()
//    init {
//        val type = object : TypeToken<List<City>>(){}.type
//        val json = TestUtil.jsonFromRaw(context,R.raw.cities)
//        cities = Gson().fromJson<List<City>>(json,type)
//    }
//    override suspend fun getCities(): Result<List<City>> {
//        return Result.Success(cities)
//    }
//}