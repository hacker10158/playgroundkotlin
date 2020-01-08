package com.troy.playgroundkotlin.core.base


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.troy.playgroundkotlin.MainActivity
import com.troy.playgroundkotlin.R
import com.troy.playgroundkotlin.core.base.viewmodel.BaseViewModel
import com.troy.playgroundkotlin.core.fragment.BottomSheetFragment
import com.troy.playgroundkotlin.core.fragment.SlidingUpPanelFragment
import com.troy.playgroundkotlin.core.utility.Log
import com.troy.playgroundkotlin.databinding.FragmentBaseBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_base.*
import javax.inject.Inject
import javax.inject.Named


class BaseFragment : DaggerFragment() {

    private var binding: FragmentBaseBinding? = null
    private var viewModel: BaseViewModel? = null

    @field:[Inject Named("base")]
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var compositeDisposable: CompositeDisposable? = null

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

    override fun onDestroy() {
        compositeDisposable!!.dispose()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {

        tv_sliding_up_button.setOnClickListener {
            if (activity is MainActivity) {
                (activity as? MainActivity)?.addFragment(SlidingUpPanelFragment())
            }
        }

        tv_buttom_sheet.setOnClickListener {
            if (activity is MainActivity) {
                (activity as? MainActivity)?.addFragment(BottomSheetFragment())
            }
        }
    }

}
