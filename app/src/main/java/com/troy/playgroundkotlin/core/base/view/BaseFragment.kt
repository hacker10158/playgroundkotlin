package com.troy.playgroundkotlin.core.base.view

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
import com.troy.playgroundkotlin.core.base.viewmodel.BaseViewModel
import com.troy.playgroundkotlin.databinding.FragmentBaseBinding

import javax.inject.Inject
import javax.inject.Named

import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable

class BaseFragment : DaggerFragment(), View.OnKeyListener {

    @field:[Inject Named("base")]
    lateinit var viewModelFactory : ViewModelProvider.Factory

    @Inject
    lateinit var searchUserAdapter : SearchUserAdapter

    private lateinit var binding: FragmentBaseBinding

    private lateinit var compositeDisposable : CompositeDisposable
    private lateinit var viewModel : BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel::class.java)
        compositeDisposable = CompositeDisposable()
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
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
