package com.example.androidtestproject.dialogwithpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


private const val NUM_PAGES = 5

class ScreenSlidePagerAdapter(fa: AppCompatActivity): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment  =
        FragmentSlide()
}