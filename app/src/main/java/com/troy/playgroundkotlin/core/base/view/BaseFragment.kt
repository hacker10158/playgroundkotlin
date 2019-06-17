package com.troy.playgroundkotlin.core.base.view

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.troy.playgroundkotlin.R
import com.troy.playgroundkotlin.core.base.viewmodel.BaseViewModel
import com.troy.playgroundkotlin.databinding.FragmentBaseBinding

import javax.inject.Inject
import javax.inject.Named

import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable

class BaseFragment : DaggerFragment() {

    private var binding: FragmentBaseBinding? = null
    private var viewModel: BaseViewModel? = null

    @field:[Inject Named("base")]
    lateinit var viewModelFactory : ViewModelProvider.Factory

    @Inject
    lateinit var searchUserAdapter : SearchUserAdapter

    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel::class.java)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
        binding!!.viewModel = viewModel
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()

        viewModel?.searchUsers()
    }

    override fun onDestroy() {
        compositeDisposable!!.dispose()
        super.onDestroy()
    }

    private fun initView() {
        binding?.rvContent?.adapter = searchUserAdapter
        binding?.rvContent?.layoutManager = LinearLayoutManager(context)
//        binding.rvContent.addOnScrollListener(viewModel.createScrollListener())
    }
}
