package com.opsigo.travelaja.logic

import com.opsigo.travelaja.utility.Globals
import opsigo.com.datalayer.mapper.Serializer
import opsigo.com.domainlayer.model.aprover.ParticipantModelDomain
import org.hamcrest.CoreMatchers
import org.json.JSONArray
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


@RunWith(MockitoJUnitRunner::class)
class TestLogic {

    @Before
    fun setUp() {

    }

//    @Test
//    fun test(){
//        Assert.assertThat("Tuesday", CoreMatchers.`is`(""))
//    }

    fun calculatePercentages(obtained: Double, total: Double): Double {
        return obtained / total *100
    }

    @Test
    fun tesPresentage(){
        println(calculatePercentages(224.0, 375.0).toFloat())
    }



    @Test
    fun testStringName(){
        var test = "Fathurbhknjk rahmanu jchdbsjckjndc"
        var name = ""
        if (test.length>15){
            if ((test.split(" ")[1].length+test.split(" ")[0].length)<15){
                name = test.split(" ")[0]+" "+test.split(" ")[1]
            }
            else{
                name = test.split(" ")[0]
            }
        }
        else{
            name = test
        }

        Assert.assertThat("Fathur", CoreMatchers.`is`(name))
    }

    @Test
    fun testCountPassanger(){
        /*var totals = "3 Adults 2 Child"
        if (totals.contains("Adults")){
            totals  = totals.replace("Adults",",")
        }
        if(totals.contains("Child")){
            totals = totals.replace("Child",",")
        }
        if(totals.contains("Infant")){
            totals = totals.replace("Infant",",")
        }

        var totalPassanger = 0
        totals.replace(" ","").split(",").forEachIndexed { index, s ->
            if (s.isNotEmpty()){
                totalPassanger = totalPassanger+(s.toInt())
            }
        }

        print(totalPassanger)

        Assert.assertThat("5", CoreMatchers.`is`(totalPassanger.toString()))*/

        /*val mData1 = DetailPassangerModel()
        mData1.email        = "whcbkj"
        mData1.firstname    = "hcjwcb"
        mData1.lastname     = "ftgyhj"
        mData1.mobileNumber = "ftdw"

        data.add(mData1)*/

//        Assert.assertThat(true, CoreMatchers.`is`(completedData()))
    }

    /*var data = ArrayList<DetailPassangerModel>()

    private fun completedData(): Boolean {
        var completed = false

        val totalEmpety = data.filter {
            it.email.isEmpty()||
            it.firstname.isEmpty()||
            it.lastname.isEmpty()||
            it.mobileNumber.isEmpty()
//            it.title.isEmpty()
        }.size

        if (totalEmpety==0){
            completed = true
        }

        return completed
    }
*/

    private fun totalPassanger(): Pair<String,String> {

        /*var totals = dataOrder.totalPassagerString
        if (totals.contains("Adults")){
            totals  = totals.replace("Adults",",")
        }
        if(totals.contains("Child")){
            totals = totals.replace("Child",",")
        }
        if(totals.contains("Infant")){
            totals = totals.replace("Infant",",")
        }

        var totalPassanger = 0
        totals.replace(" ","").split(",").forEachIndexed { index, s ->
            if (s.isNotEmpty()){
                totalPassanger = totalPassanger+(s.toInt())
            }
        }*/
//        val totalPassangerFlight = dataOrderflight.totalPassangerInt.split(",")[0].toInt()+dataOrderflight.totalPassangerInt.split(",")[1].toInt()+dataOrderflight.totalPassangerInt.split(",")[2].toInt()
//        val totalPassangerTrain  = dataOrderTrain.totalPassangerInt.split(",")[0].toInt()+dataOrderTrain.totalPassangerInt.split(",")[1].toInt()+dataOrderTrain.totalPassangerInt.split(",")[2].toInt()



        return Pair("Fathur","")
    }

    @Test
    fun saveArrayList(){

        val listData = ArrayList<Testing>()

        for (i in 0 until  5){
            val dataTest = Testing()
            dataTest.aku = i.toString()+" csdbnk"
            dataTest.oke = "gsvcdj"
            listData.add(dataTest)
        }

        val dataCity = JSONArray(Serializer.serialize(listData))
        val listCity = ArrayList<Testing>()
        for (i in 0 until dataCity.length()){
            val data = Serializer.deserialize(dataCity[i].toString(),Testing::class.java)
            listCity.add(data)
        }

        listCity.forEachIndexed { index, testing ->
            println(testing.aku)
        }


    }

    class Testing{
        var aku = ""
        var oke = ""
    }

    @Test
    fun testShortListByName(){
        val items = ArrayList<Tester>()
        items.add(Tester("Jakarta","indofood"))
        items.add(Tester("Bandung","unesa"))
        items.add(Tester("Jakarta","Suprime"))

        items.sortBy { it.aku }
        items.forEach {

            println(it.aku)
            println(it.oke)
        }
    }

    class Tester(
        var aku :String,
        var oke :String
    )

    @Test
    fun testShortList(){

        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

        val date = ArrayList<departure>()
        val model = departure()
        model.date = dateFormatter.parse("2019-06-12 08:22")
        model.name = "oke"
        date.add(model)

        val model1 = departure()
        model1.date = dateFormatter.parse("2019-06-12 07:22")
        model1.name = "oke1"
        date.add(model1)

        val model2 = departure()
        model2.date = dateFormatter.parse("2019-06-12 09:22")
        model2.name = "oke2"
        date.add(model2)

        date.forEachIndexed { index, s ->
            println("Before sorting: ${dateFormatter.format(s.date.time)}")
        }

        date.sortBy { it.date }

        date.forEachIndexed { index, s ->
            println("After sorting: ${dateFormatter.format(s.date.time)}")
        }


    }

    @Test
    fun testAlso(){
        class Model{
            var name = ""
            var umur = ""
            var kelas = ""
        }
        val data = ArrayList<Model>()
        val k = Model()
        k.name = "Andi"
        k.umur = "18"
        data.add(k)

        data.also {
            it[0].kelas = "9"
        }

        println(data[0].name)
        println(data[0].umur)
        println(data[0].kelas)
    }

    @Test
    fun testRun(){

    }

//    fun shortListDate(dataTime:ArrayList<departure>,formatTime:String):ArrayList<departure>{
//        val listDates = ArrayList<Date>()
//        val dateFormatter: DateFormat = SimpleDateFormat(formatTime)
//
//        dataTime.forEachIndexed { index, s ->
//            listDates.add(dateFormatter.parse(s.date))
//        }
//
//        val dateReturn = ArrayList<departure>()
//
//
//        Collections.sort(listDates)
//
//        listDates.forEachIndexed { index, date ->
//            dateReturn.add(dateFormatter.format(date.time))
//        }
//
//        return dateReturn
//    }

    class departure{
        var date = Date()
        var name = ""
    }


    @Test
    fun shortPrizeTest(){
        val dataPrize = ArrayList<Int>()
        dataPrize.add(9008)
        dataPrize.add(9001)
        dataPrize.add(9003)

        Collections.sort(dataPrize)
    }

    fun shortPrice(data:ArrayList<Int>):ArrayList<Int>{

        return data
    }

    @Test
    fun testCalendarAgo(){

        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        cal.time = sdf.parse("2020-04-22")
        cal.add(Calendar.DATE, 7)

        print(sdf.format(cal.time))
    }


    @Test
    fun testCheckMonday(){

        val listDates = ArrayList<Date>()
        val dateFormatter: DateFormat = SimpleDateFormat("HH:mm")

        try {
            listDates.add(dateFormatter.parse("08:22"))
            listDates.add(dateFormatter.parse("05:12"))
            listDates.add(dateFormatter.parse("07:18"))
            listDates.add(dateFormatter.parse("14:18"))
        } catch (ex: ParseException) {
            System.err.print(ex)
        }

        println("--------------")
        listDates.filter { it.after(dateFormatter.parse("06:00")) && it.before(dateFormatter.parse("12:00")) }
                .forEachIndexed { index, date ->
                    println(dateFormatter.format(date.time))
        }


//        it.after(dateFormatter.parse("00:00")) && it.before(dateFormatter.parse("06:00"))
//        it.after(dateFormatter.parse("06:00")) && it.before(dateFormatter.parse("12:00"))
//        it.after(dateFormatter.parse("12:00")) && it.before(dateFormatter.parse("18:00"))
//        it.after(dateFormatter.parse("18:00")) && it.before(dateFormatter.parse("24:00"))

    }

    /*@Test
    fun testCheckMonday(){
        val cal = Calendar.getInstance()

        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        try {
            cal.time = dateFormatter.parse("2020-01-26")
        } catch (ex: ParseException) {
            System.err.print(ex)
        }

        val monday = cal[Calendar.DAY_OF_WEEK] === Calendar.SUNDAY
        println("--------------")
        println(monday)
    }*/

   /* fun shortListDate(dataTime:ArrayList<String>,formatTime:String):ArrayList<String>{
        val listDates = ArrayList<Date>()
        val dateFormatter: DateFormat = SimpleDateFormat(formatTime)

        dataTime.forEachIndexed { index, s ->
            listDates.add(dateFormatter.parse(s))
        }

        val dateReturn = ArrayList<String>()
        Collections.sort(listDates)

        listDates.forEachIndexed { index, date ->
            dateReturn.add(dateFormatter.format(date.time))
        }

        return dateReturn
    }*/


    /*fun shortListDate(dataTime:ArrayList<String>){
        val listDates = ArrayList<Date>()
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")

        try {
            listDates.add(dateFormatter.parse("2019-06-12 08:22"))
            listDates.add(dateFormatter.parse("2020-01-26 02:12"))
            listDates.add(dateFormatter.parse("2019-06-12 09:18"))
        } catch (ex: ParseException) {
            System.err.print(ex)
        }

        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm")

        listDates.forEachIndexed { index, date ->
            println("Before sorting: ${formatter.format(date.getTime())}")
        }

        Collections.sort(listDates)

        listDates.forEachIndexed { index, date ->
            println("After sorting: ${formatter.format(date.getTime())}")
        }
    }*/

    @Test
    fun testapproval(){
        val data = dummyParticipant()
        data.forEach {
            println("---------")
            println(it.name)
            println(it.layer)
            println(it.isCompletelyReviewed)
        }

        val positionList = data.indexOf(data.filter { it.name == "icha" }.first())

        if (positionList>0){
            if (data[positionList-1].layer == data[positionList].layer){
                if (data[positionList-1].isCompletelyReviewed){
                    println("approve done")
                }
                else {
                    println("show approve")
                }
            }
            else {
                if (data[positionList-1].isCompletelyReviewed){
                    println("show approve")
                }
                else{
                    println("disable approve")
                }
            }
        }
        else {
            if (data.size>1){
                if (data[positionList+1].layer == data[positionList].layer){
                    if (data[positionList+1].isCompletelyReviewed){
                        println("approve done")
                    }
                    else{
                        println("show approve")
                    }
                }
                else {
                    println("show approve")
                }
            }
            else{
                if (data[positionList].isCompletelyReviewed){
                    println("approve done")
                }
                else{
                    println("show approve")
                }
            }
        }
    }

    private fun dummyParticipant() :ArrayList<ParticipantModelDomain>{
        val data = ArrayList<ParticipantModelDomain>()

        val model1 = ParticipantModelDomain()
        model1.name = "vody"
        model1.isCompletelyReviewed = false
        model1.layer = 1

        val model2 = ParticipantModelDomain()
        model2.name = "Khoiron"
        model2.isCompletelyReviewed = true
        model2.layer = 1

        val model3 = ParticipantModelDomain()
        model3.name = "icha"
        model3.isCompletelyReviewed = false
        model3.layer = 3

        data.add(model1)
        data.add(model2)
        data.add(model3)

        return data

    }

    @Test
    fun test(){
        val myFormat = SimpleDateFormat("dd MM yyyy")
        val inputString1 = "23 04 1997"
        val inputString2 = "27 04 1997"

        try {
            val date1: Date = myFormat.parse(inputString1)
            val date2: Date = myFormat.parse(inputString2)
            val diff: Long = date2.getTime() - date1.getTime()
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    @Test
    fun tests(){
        val data=ArrayList<String>()
        data.add("cbh")
        data.add("gcdb")
        var total  = 0
        for (i in 0 until data.size){
            total++
        }
        println(total)
    }

    @Test
    fun testIataMapping(){
        var dataAllIata = ArrayList<Flight>()
        var dataFilterIAta = ArrayList<FlightFilter>()

        var flight = Flight()
        flight.flightIata = "kd"
        flight.flightCode = 1

        var flight1 = Flight()
        flight1.flightIata = "nt"
        flight1.flightCode = 1

        var flight2 = Flight()
        flight2.flightIata = "bp"
        flight2.flightCode = 1

        var flight3 = Flight()
        flight3.flightIata = "wd"
        flight3.flightCode = 2

        var flight4 = Flight()
        flight4.flightIata = "kk"
        flight4.flightCode = 2


        dataAllIata.add(flight)
        dataAllIata.add(flight1)
        dataAllIata.add(flight3)
        dataAllIata.add(flight2)
        dataAllIata.add(flight4)

        var numSame = 0
        dataAllIata.sortBy { it.flightCode }
        dataAllIata.forEachIndexed { index, it ->
            if (it.flightCode!=numSame){
                val model = FlightFilter()
                model.flightCode = it.flightCode
                model.flightIata.add(it.flightIata)
                dataFilterIAta.add(model)
            }
            else{
                if (dataFilterIAta.isNotEmpty()){
                    dataFilterIAta[dataFilterIAta.size-1].flightIata.add(it.flightIata)
                }
                else {
                    val model = FlightFilter()
                    model.flightCode = it.flightCode
                    model.flightIata.add(it.flightIata)
                    dataFilterIAta.add(model)
                }
            }
            numSame = it.flightCode
        }

        dataFilterIAta.forEach {
            println(it.flightCode)
            it.flightIata.forEachIndexed { index, s ->
                print(s)
            }
            println("")
        }

    }

    class Flight{
        var flightIata = "wd"
        var flightCode = 0
    }

    class FlightFilter{
        var flightIata = ArrayList<String>()
        var flightCode = 0
    }

    class TestingLop{
        var date = ""
        var isi  = ""
        var type = ""
    }

    @Test
    fun testLogic(){
        var data = ArrayList<TestingLop>()

        var testing  = TestingLop()
        testing.date = "999999"
        testing.isi  = "aaaaa"
        testing.type = "1"

        var testing2  = TestingLop()
        testing2.date = "66666"
        testing2.isi  = "bbbbb"
        testing2.type = "2"

        var testing1  = TestingLop()
        testing1.date = "66666"
        testing1.isi  = "bbbbb"
        testing1.type = "2"

        var testing3  = TestingLop()
        testing3.date = "999999"
        testing3.isi  = "aaaaa"
        testing3.type = "1"

        data.add(testing)
        data.add(testing1)
        data.add(testing2)
        data.add(testing3)

        /*for (i in 0 until data.size){
            if (data[i].type=="2"){
                data.remove(data[i])
            }
        }*/
        data.removeAll(data.filter { it.type=="2"})

        data.forEach {
            println(it.type)
        }
    }

    @Test
    fun testDateCalculation(){
        println("${Globals.countDaysBettwenTwoDate("2021-01-01","2021-01-01","yyyy-MM-dd")} Nigh")
    }

    fun dateCheckoutCalculation(string: String): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        cal.time = sdf.parse(string)
        cal.add(Calendar.DATE, 4)
        return sdf.format(cal.time)
    }


}


