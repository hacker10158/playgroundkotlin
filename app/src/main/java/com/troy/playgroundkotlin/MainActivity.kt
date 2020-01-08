package com.troy.playgroundkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import com.troy.playgroundkotlin.core.rxjavademo.RxJavaDemoFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(RxJavaDemoFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name

        val manager = supportFragmentManager

        val ft = manager.beginTransaction()
        ft.add(R.id.fl_fragment_container, fragment, backStateName)
        ft.commitAllowingStateLoss()
    }
}
