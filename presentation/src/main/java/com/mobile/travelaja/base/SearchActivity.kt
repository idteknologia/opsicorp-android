package com.mobile.travelaja.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.mobile.travelaja.R
import com.mobile.travelaja.databinding.SearchActivityBinding

abstract class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: SearchActivityBinding
    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.search_activity)
        navHost = supportFragmentManager.findFragmentById(R.id.search_nav_host_fragment) as NavHostFragment
        setNavigationGraph()
        binding.searchView.setOnQueryTextListener(this)
        binding.buttonClose.setOnClickListener {
            finish()
        }
    }

    private fun setNavigationGraph() {
        val inflater = navHost.navController.navInflater
        val graph = inflater.inflate(getNavigation())
        graph.startDestination = startDestinationNavId()
        navHost.navController.setGraph(graph,arguments())
    }

    fun setHintSearch(hint : String){
        binding.searchView.queryHint = hint
    }

    abstract fun getNavigation(): Int

    abstract fun startDestinationNavId(): Int

    abstract fun arguments() : Bundle
}