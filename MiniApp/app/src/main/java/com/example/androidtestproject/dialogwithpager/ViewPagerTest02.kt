package com.example.androidtestproject.dialogwithpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.activity_view_pager_test02.*

class ViewPagerTest02 : AppCompatActivity() {

    private lateinit var mPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_test02)

        mPager = layout_viewpager2

        var fileList = ArrayList<String>()
        fileList.add("invest_ready")
        fileList.add("invest_guide")

//        mPager.adapter = ViewPagerTest02Adapter(this, fileList)

    }
}
