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
