package com.mobile.travelaja.module.home.presenter

import android.content.Context
import org.mockito.MockitoAnnotations
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


class HomePresenterTest{


    @Mock
    lateinit var context : Context

    lateinit var presenter: HomePresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loadItems_WhenDataIsAvailable_ShouldUpdateViews() {

    }
}