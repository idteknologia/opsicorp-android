package com.mobile.travelaja.base.compose

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mobile.travelaja.R
import com.mobile.travelaja.utility.Utils

@Composable
fun <T> ComposeListScreen(list: List<T>, content: @Composable (T) -> Unit) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(list) { i, item ->
            content(item)
        }
    }
}

@Composable
fun <T : Any> PagingListScreen(
    @StringRes placeHolder: Int?,
    pager: Pager<Int, T>,
    content: @Composable (T?) -> Unit,
    onSearch: (value: String) -> Unit,
    onClickClose: () -> Unit,
    selectedNames: List<Int> = listOf(),
    selectedAction: (nameRes: Int) -> Unit
) {
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
    val context = LocalContext.current
    var textSearch by remember{ mutableStateOf("")}
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val localFocusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        SearchViewList(
            placeHolder = placeHolder,
            onSearch = {
                textSearch = it
                onSearch.invoke(it)
                lazyPagingItems.refresh()
            },
            onClickClose = onClickClose,
            onClickClear = {
                textSearch = ""
                onSearch.invoke(textSearch)
                localFocusManager.clearFocus()
            },
            text = textSearch
        )
        if (selectedNames.isNotEmpty()) {
            ButtonSelectedGroup(nameButtons = selectedNames) {
                textSearch = ""
                localFocusManager.clearFocus()
                selectedAction.invoke(it)
                lazyPagingItems.refresh()
            }
        }
        SwipeRefresh(
            state = swipeRefreshState,
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(1f),
            onRefresh = {
                lazyPagingItems.refresh()
            }) {
            LazyColumn(modifier = Modifier
                .semantics { contentDescription = "paggingList" }) {
                items(lazyPagingItems) { item ->
                    content(item)
                }
                lazyPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            swipeRefreshState.isRefreshing = true
                        }
                        loadState.append is LoadState.Loading -> {
                            swipeRefreshState.isRefreshing = false
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            swipeRefreshState.isRefreshing = false
                            val e = lazyPagingItems.loadState.refresh as LoadState.Error
                            Utils.handleErrorMessage(context, e.error) { error ->
                                item {
                                    if (lazyPagingItems.itemCount > 0) {
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    } else {
                                        var imageRes = R.drawable.error_500
                                        var buttonVisible = true
                                        var errorString = error
                                        if (error == Utils.EMPTY){
                                            imageRes =  R.drawable.no_list_approval
                                            buttonVisible = false
                                            errorString = stringResource(id = R.string.data_is_empty)
                                        }
                                        Box(modifier = Modifier
                                            .fillMaxSize()
                                            .padding(top = 50.dp)){
                                            ErrorPageView(
                                                titleRes = R.string.uh_oh,
                                                buttonName = R.string.txt_try_again,
                                                subTitle = errorString,
                                                imageRes = imageRes,
                                                buttonVisible = buttonVisible
                                            ) {
                                                lazyPagingItems.retry()
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = lazyPagingItems.loadState.append as LoadState.Error
                            Utils.handleErrorMessage(context, e.error) { error ->
                                if (error != Utils.EMPTY){
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    item {
                                        Box(
                                            modifier = Modifier.fillParentMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            ButtonChip(nameRes = R.string.txt_try_again) {
                                                retry()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SnackbarView(
    @StringRes actionRes: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit
) {
    Snackbar(
        action = {
            Button(onClick = onClickAction) {
                Text(text = stringResource(id = actionRes))
            }
        }, modifier = modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun ButtonChip(@StringRes nameRes: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = stringResource(id = nameRes))
    }
}

@Composable
fun SearchViewList(
    @StringRes placeHolder: Int?,
    modifier: Modifier = Modifier,
    onSearch: (value: String) -> Unit,
    onClickClose: () -> Unit,
    onClickClear: () -> Unit,
    text : String = ""
) {
    val marginDefault = dimensionResource(id = R.dimen.margin_default)
    ConstraintLayout(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.colorPrimary))
    ) {
        val (search, button) = createRefs()
        BasicTextField(value = text,
            onValueChange = onSearch,
            modifier = Modifier
                .constrainAs(search) {
                    start.linkTo(parent.start, margin = marginDefault)
                    top.linkTo(parent.top, margin = 11.dp)
                    bottom.linkTo(parent.bottom, margin = 11.dp)
                    end.linkTo(button.start, margin = 11.dp)
                    width = Dimension.fillToConstraints
                }
                .height(35.dp)
                .background(color = Color.White), singleLine = true
        ) { innerTextField ->
            ConstraintLayout(
                modifier =
                Modifier
                    .background(Color.White, RoundedCornerShape(2.dp))
                    .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
            ) {
                val (startIcon, box, endIcon) = createRefs()
                Icon(painter = painterResource(id = R.drawable.ic_search_orange),
                    contentDescription = "start_icon", modifier = Modifier.constrainAs(startIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    })
                Box(
                    modifier = Modifier
                        .constrainAs(box) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(startIcon.end, margin = 10.dp)
                            end.linkTo(endIcon.start)
                            width = Dimension.fillToConstraints
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty() && placeHolder != null) {
                        Text(text = stringResource(id = placeHolder))
                    }
                    innerTextField()
                }
                if (text.isNotEmpty()) {
                    IconButton(onClick = onClickClear, modifier = Modifier.constrainAs(endIcon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_dark_gray),
                            contentDescription = "clear"
                        )
                    }
                }
            }
        }

        TextButton(onClick = onClickClose, modifier = Modifier.constrainAs(button) {
            end.linkTo(parent.end, margin = marginDefault)
            top.linkTo(search.top)
            bottom.linkTo(search.bottom)
        }) {
            Text(text = stringResource(id = R.string.close), color = Color.White)
        }

    }
}

@Composable
fun ErrorPageView(
    @StringRes titleRes: Int,
    @StringRes buttonName: Int,
    imageRes : Int,
    subTitle: String,
    buttonVisible : Boolean,
    onClickAction: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_default))
    ) {
        val (title, Subtitle, image, button) = createRefs()
        Text(text = stringResource(id = titleRes),
            fontSize = 28.sp,
            fontFamily = AssetsUtils.fontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                linkTo(start = parent.start, end = parent.end)
                bottom.linkTo(Subtitle.top, margin = 11.dp)
            })
        Text(text = subTitle,
            fontSize = 14.sp,
            fontFamily = AssetsUtils.fontFamily,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(Subtitle) {
                linkTo(start = parent.start, end = parent.end)
                bottom.linkTo(image.top, margin = 40.dp)
            })
        Image(painter = painterResource(id = imageRes),
            contentDescription = "image_error",
            modifier = Modifier
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )

                }
                .aspectRatio(16 / 9f))
        if (buttonVisible){
            ButtonFilled(buttonName = buttonName,
                onClickAction = {
                    onClickAction.invoke()
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        top.linkTo(image.bottom, margin = 50.dp)
                    })
        }
    }
}

@Composable
fun ButtonFilled(
    buttonName: Int,
    onClickAction: (buttonName: Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    val description = stringResource(id = buttonName)
    Button(
        onClick = { onClickAction.invoke(buttonName) },
        enabled = enabled,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.buttonHeight))
            .semantics {
                contentDescription = description
            },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.buttonColor),
            disabledBackgroundColor = Color.LightGray,
            contentColor = colorResource(id = R.color.textButtonColor),
            disabledContentColor = Color.Gray
        )
    ) {
        Text(
            text = stringResource(id = buttonName),
            fontFamily = AssetsUtils.fontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ButtonSelected(
    buttonName: Int,
    onClickAction: (buttonName: Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    val description = stringResource(id = buttonName)
    Button(
        onClick = { onClickAction.invoke(buttonName) },
        enabled = enabled,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.buttonSelected))
            .semantics {
                contentDescription = description
            },
        border = BorderStroke(if (enabled) 0.5.dp else 0.dp, Color.Gray),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            disabledBackgroundColor = colorResource(id = R.color.buttonColor),
            contentColor = Color.Gray,
            disabledContentColor = colorResource(id = R.color.textButtonColor)
        )
    ) {
        Text(
            text = stringResource(id = buttonName),
            fontFamily = AssetsUtils.fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ButtonSelectedGroup(
    nameButtons: List<Int>,
    selectedAction: (nameRes: Int) -> Unit
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(nameButtons[0]) }
    LazyRow(
        Modifier
            .selectableGroup()
            .background(colorResource(id = R.color.backgroundConfirmInformation)),
                 contentPadding = PaddingValues(
            end = dimensionResource(id = R.dimen.margin_default),
            top = 14.dp,
            bottom = 14.dp)
    ) {
        items(nameButtons) { nameRes ->
            ButtonSelected(
                buttonName = nameRes,
                modifier = Modifier
                    .selectable(
                        selected = (nameRes != selectedOption),
                        onClick = { onOptionSelected(nameRes) },
                        role = Role.Button
                    )
                    .padding(start = 16.dp),
                onClickAction = {
                    onOptionSelected(nameRes)
                    selectedAction.invoke(it)
                }, enabled = (nameRes != selectedOption)
            )
        }
    }
}

//@Preview()
//@Composable
//fun ButtonFilledPreview() {
//    ButtonFilled(buttonName = R.string.txt_try_again, onClickAction = {  }, enabled = true)
//}

@Preview
@Composable
fun ButtonSelectedPreview() {
    val nameRes = listOf(R.string.see_all, R.string.approved)
    ButtonSelectedGroup(nameButtons = nameRes) {

    }
}

//@Preview(device = Devices.PIXEL_2, showBackground = true, showSystemUi = true)
//@Composable
//fun EmptyListViewPreview() {
//    val error = "We're having difficulty connecting to the server Please check your connection."
//    EmptyListView(
//        titleRes = R.string.uh_oh,
//        buttonName = R.string.txt_try_again,
//        subTitle = error
//    ) {
//    }
//}
//
//@Preview(device = Devices.PIXEL_2, showBackground = true, showSystemUi = true)
//@Composable
//fun ListPreview() {
//    ComposeListScreen(list = listOf<String>("One")) {
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .background(Color.Blue)
//        ) {
//            EmptyListViewPreview()
//        }
//    }
//}

//@Preview
//@Composable
//fun SnackbarViewPreview() {
//    SnackbarView(actionRes = R.string.txt_try_again, text = "No Internet") {
//
//    }
//}
//
//@Preview
//@Composable
//fun SearchViewPreview() {
//    SearchViewList(R.string.txt_enter_trip_code, Modifier, { value ->
//
//    }, {
//
//    })
//}



