package com.example.androidtestproject.dialogwithpager


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.androidtestproject.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_dialog_frament.*

class DialogFragment : DialogFragment() {

    private lateinit var mPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_frament, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPager = viewpager_in_dialog

        var titleList = ArrayList<Int>()
        var firstList = ArrayList<Int>()
        var secondList = ArrayList<Int>()
        var thirdList = ArrayList<Int>()

        createList(titleList, R.drawable.dice_1, R.drawable.dice_2)
        createList(firstList, R.drawable.dice_3, R.drawable.dice_4)
        createList(secondList, R.drawable.dice_5, R.drawable.dice_6)
        createList(thirdList, R.drawable.honeycomb, R.drawable.froyo)

        mPager.adapter = ViewPagerTest02Adapter(activity!!, titleList, firstList, secondList, thirdList)

        var tablayout: TabLayout = tab
//        TabLayoutMediator(tablayout, mPager) { _, _ -> }.attach()       //meterialDesign 1.1.0+
    }

    private fun createList(listName:ArrayList<Int>, firstSrc:Int, secondSrc: Int) {
        listName.apply {
            add(firstSrc)
            add(secondSrc)
        }
    }
}
