package com.troy.playgroundkotlin.core.fragment


import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.troy.playgroundkotlin.R
import kotlinx.android.synthetic.main.bottom_sheet_content.bottom_sheet_content
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : Fragment() {

    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onStart() {
        super.onStart()

        sheetBehavior = BottomSheetBehavior.from(bottom_sheet_content)

        tv_bottom_sheet_show.setOnClickListener {
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                tv_bottom_sheet_show.text = "Close sheet"
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                tv_bottom_sheet_show.text = "Expand sheet"
            }
        }
    }
}
