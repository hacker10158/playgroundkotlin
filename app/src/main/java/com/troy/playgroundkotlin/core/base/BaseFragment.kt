package com.troy.playgroundkotlin.core.base

import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import android.os.Bundle
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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BaseViewModel::class.java)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
        binding!!.viewModel = viewModel
        return binding!!.root
    }

    override fun onDestroy() {
        compositeDisposable!!.dispose()
        super.onDestroy()
    }
}
