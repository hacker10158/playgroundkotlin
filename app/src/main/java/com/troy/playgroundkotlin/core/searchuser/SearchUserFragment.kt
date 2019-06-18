package com.troy.playgroundkotlin.core.searchuser

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.troy.playgroundkotlin.R
import com.troy.playgroundkotlin.core.searchuser.view.SearchUserAdapter
import com.troy.playgroundkotlin.core.searchuser.viewmodel.SearchUserViewModel
import com.troy.playgroundkotlin.core.utility.KeyboardUtil
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.databinding.FragmentSearchBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class SearchUserFragment : DaggerFragment(), View.OnKeyListener {

    companion object {
        const val PRELOAD_COUNT = 15
    }

    @field:[Inject Named("search")]
    lateinit var viewModelFactory : ViewModelProvider.Factory

    @Inject
    lateinit var searchUserAdapter : SearchUserAdapter

    private lateinit var binding: FragmentSearchBinding

    private lateinit var viewModel : SearchUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchUserViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.rvContent.adapter = searchUserAdapter
        binding.rvContent.layoutManager = LinearLayoutManager(context)
        binding.rvContent.addOnScrollListener(createScrollListener())

        binding.etInputField.setOnKeyListener(this)

        binding.tvSearch.setOnClickListener {
            viewModel.onSearchClick()
            KeyboardUtil.hideKeyboard(activity)
        }
    }

    override fun onKey(view: View, keyCode: Int, keyEvent: KeyEvent): Boolean {
        if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            viewModel.onSearchClick()
            KeyboardUtil.hideKeyboard(activity)
            return true
        }
        return false
    }

    private fun createScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = recyclerView!!.layoutManager.itemCount
                val lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                Log.d("lastPosition $lastPosition totalCount $totalCount")

                if (dy > 0 && lastPosition >= totalCount - PRELOAD_COUNT) {
                    viewModel.loadNextPage()
                }
            }
        }
    }
}
