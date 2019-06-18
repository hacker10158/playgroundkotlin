package com.troy.playgroundkotlin.core.searchuser.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context.INPUT_METHOD_SERVICE
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.troy.playgroundkotlin.R
import com.troy.playgroundkotlin.core.searchuser.viewmodel.SearchUserViewModel
import com.troy.playgroundkotlin.databinding.FragmentBaseBinding

import javax.inject.Inject
import javax.inject.Named

import dagger.android.support.DaggerFragment

class SearchUserFragment : DaggerFragment(), View.OnKeyListener {

    @field:[Inject Named("searchuser")]
    lateinit var viewModelFactory : ViewModelProvider.Factory

    @Inject
    lateinit var searchUserAdapter : SearchUserAdapter

    private lateinit var binding: FragmentBaseBinding

    private lateinit var viewModel : SearchUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchUserViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
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
        binding.rvContent.addOnScrollListener(viewModel.createScrollListener())

        binding.etInputField.setOnKeyListener(this)

        binding.tvSearch.setOnClickListener {
            viewModel.onSearchClick()
            hideKeyboard()
        }
    }

    override fun onKey(view: View, keyCode: Int, keyEvent: KeyEvent): Boolean {
        if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            viewModel.onSearchClick()
            hideKeyboard()
            return true
        }
        return false
    }

    private fun hideKeyboard() {
        activity?.let {
            val decorView = it.window.decorView
            val imm = it.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(decorView.windowToken, 0)
        }
    }
}
