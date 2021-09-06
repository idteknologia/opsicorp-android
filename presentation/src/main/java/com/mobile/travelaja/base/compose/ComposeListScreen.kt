package com.mobile.travelaja.base.compose

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
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
    onClickClose: () -> Unit
) {
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()
    val context = LocalContext.current
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = true)

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (search, swipeRefresh) = createRefs()
        SearchViewList(
            placeHolder = placeHolder,
            modifier = Modifier.constrainAs(search) {
                top.linkTo(parent.top)
            },
            onSearch = {
                onSearch.invoke(it)
                lazyPagingItems.refresh()
            }, onClickClose = onClickClose
        )
        SwipeRefresh(
            state = swipeRefreshState,
            modifier = Modifier
                .constrainAs(swipeRefresh) {
                    top.linkTo(search.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            onRefresh = {
                lazyPagingItems.refresh()
            }) {

            if (lazyPagingItems.loadState.refresh is LoadState.Error) {
                swipeRefreshState.isRefreshing = false
                val e = lazyPagingItems.loadState.refresh as LoadState.Error
                var error = ""
                Utils.handleErrorMessage(context, e.error) {
                    error = it
                }
                if (lazyPagingItems.itemCount > 0) {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                } else {
                    EmptyListView(
                        titleRes = R.string.uh_oh,
                        buttonName = R.string.txt_try_again,
                        subTitle = error
                    ) {
                        lazyPagingItems.retry()
                    }

                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(lazyPagingItems) { item ->
                    content(item)
                }
                lazyPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            swipeRefreshState.isRefreshing = true
                        }
                        loadState.refresh is LoadState.NotLoading -> {
                            swipeRefreshState.isRefreshing = false
                        }
                        loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = lazyPagingItems.loadState.refresh as LoadState.Error
                            Utils.handleErrorMessage(context, e.error) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                            item {
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
    onClickClose: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val marginDefault = dimensionResource(id = R.dimen.margin_default)
    ConstraintLayout(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.colorPrimary))
    ) {
        val (search, button) = createRefs()
        BasicTextField(value = text,
            onValueChange = {
                text = it
                onSearch.invoke(text)
            },
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
                    IconButton(onClick = {
                        text = ""
                    }, modifier = Modifier.constrainAs(endIcon) {
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
fun EmptyListView(
    @StringRes titleRes: Int,
    @StringRes buttonName: Int,
    subTitle: String,
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
        Image(painter = painterResource(id = R.drawable.error_500),
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
        ButtonFilled(buttonName = buttonName,
            onClick = onClickAction,
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button) {
                    top.linkTo(image.bottom, margin = 50.dp)
                })
    }
}

@Composable
fun ButtonFilled(
    @StringRes buttonName: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(dimensionResource(id = R.dimen.buttonHeight)),
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

@Preview(device = Devices.PIXEL_2)
@Composable
fun ButtonFilledPreview() {
    ButtonFilled(
        buttonName = R.string.txt_try_again,
        onClick = { },
        enabled = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(device = Devices.PIXEL_2, showBackground = true, showSystemUi = true)
@Composable
fun EmptyListViewPreview() {
    val error = "We're having difficulty connecting to the server Please check your connection."
    EmptyListView(
        titleRes = R.string.uh_oh,
        buttonName = R.string.txt_try_again,
        subTitle = error
    ) {
    }
}

@Preview(device = Devices.PIXEL_2, showBackground = true, showSystemUi = true)
@Composable
fun ListPreview() {
    ComposeListScreen(list = listOf<String>("One")) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Blue)
        ) {
            EmptyListViewPreview()
        }
    }
}

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



