package com.troy.playgroundkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.troy.playgroundkotlin.core.base.BaseFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(BaseFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name

        val manager = supportFragmentManager

        val ft = manager.beginTransaction()
        ft.add(R.id.fl_fragment_container, fragment, backStateName)
        ft.commitAllowingStateLoss()
    }
}
