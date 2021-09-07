package com.mobile.travelaja.module.settlement.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mobile.travelaja.R
import com.mobile.travelaja.base.compose.ComposeListScreen
import com.mobile.travelaja.base.compose.PagingListScreen
import com.mobile.travelaja.module.settlement.viewmodel.TripViewModel
import com.mobile.travelaja.viewmodel.DefaultViewModelFactory
import opsigo.com.domainlayer.model.trip.Trip

@Composable
fun SettlementScreen(onClickClose: () -> Unit) {
    val query = mutableMapOf<String, Any>()
    val context = LocalContext.current
    val viewModel : TripViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = DefaultViewModelFactory(false,context))
    val pager = viewModel.repository.pagingSettlement(query)
    var statePager by remember { mutableStateOf(pager) }
    PagingListScreen(
        placeHolder = R.string.txt_enter_trip_code,
        pager = statePager,
        content = { trip ->
            if (trip != null) {
                ItemSettlement(data = trip)
            }
        },
        onSearch = {
           query["Keyword"] = it
           statePager = viewModel.repository.pagingSettlement(query)
        },
        onClickClose = onClickClose
    )
}

@Composable
fun ListSettlementScreen(list: List<Trip>) {
    ComposeListScreen(list = list) {
        ItemSettlement(data = it)
    }
}

@Composable
fun ItemSettlement(data: Trip) {
    val fontFamily = FontFamily(
        Font(R.font.lato_regular, weight = FontWeight.Normal),
        Font(R.font.lato_bold, weight = FontWeight.Bold),
        Font(R.font.lato_black, weight = FontWeight.Black)
    )
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val (type, purpose, icon, dirDate, tripCode) = createRefs()
        TextChip(text = data.StatusView, Modifier.constrainAs(type) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
        })
        Text(
            text = data.Purpose,
            fontSize = 14.sp,
            fontFamily = fontFamily, fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(purpose) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(type.bottom, margin = 13.dp)
                width = Dimension.fillToConstraints
            })
        Icon(painter = painterResource(id = R.drawable.departure_date),
            contentDescription = "icon",
            modifier = Modifier
                .size(20.dp)
                .constrainAs(icon) {
                    start.linkTo(purpose.start)
                    top.linkTo(purpose.bottom, margin = 10.dp)
                })
        Text(text = data.getDirectionDate(),
            color = colorResource(id = R.color.colorBlueTitle),
            fontSize = 12.sp,
            fontFamily = fontFamily, fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(dirDate) {
                start.linkTo(icon.end, margin = 10.dp)
                top.linkTo(icon.top)
                bottom.linkTo(icon.bottom)
                end.linkTo(tripCode.start, margin = 10.dp)
                width = Dimension.fillToConstraints
            })
        Text(text = data.TripCode,
            color = Color.Gray,
            fontSize = 12.sp,
            fontFamily = fontFamily, fontStyle = FontStyle.Normal,
            modifier = Modifier.constrainAs(tripCode) {
                end.linkTo(parent.end)
                top.linkTo(icon.top)
            })
    }
}

@Composable
fun TextChip(text: String, modifier: Modifier = Modifier) {
    val fontFamily = FontFamily(Font(R.font.lato_regular, weight = FontWeight.Bold))
    Text(
        text = text,
        color = colorResource(id = R.color.colorWhite),
        fontFamily = fontFamily,
        fontSize = 12.sp,
        modifier = modifier
            .background(color = colorResource(id = R.color.chipStatus), shape = CircleShape)
            .padding(start = 10.dp, top = 3.dp, end = 10.dp, bottom = 3.dp)
    )
}

@Preview
@Composable
fun TextChipPreview() {
    TextChip(text = "Waiting for Approval")
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun ItemSettlementPreview() {
    val trip = Trip(
        "T1",
        "TP202104270002",
        "Makassar",
        "Benchmarking / Company Visit heheheh hdfdhfgd gfdhfgd fgdhfg dhgfd hfgdhgfdgf d",
        "2021-04-26 00:00:00",
        "2021-04-27 00:00:00",
        "",
        "",
        "TP202104270002",
        "Trip Completed",
        mutableListOf()
    )
    ItemSettlement(data = trip)
}

@Preview(showBackground = true, device = Devices.PIXEL_2, showSystemUi = true)
@Composable
fun SettlementScreenPreview() {
    val trip = Trip(
        "T1", "TP202104270002", "Makassar",
        "Benchmarking / Company Visit", "2021-04-26 00:00:00",
        "2021-04-27 00:00:00", "", "", "TP202104270002",
        "Trip Completed",
        mutableListOf()
    )

    ListSettlementScreen(list = listOf(trip, trip, trip))
}
