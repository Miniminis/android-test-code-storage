package com.example.androidtestproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onResume() {
        super.onResume()

        (this.activity as MainActivity).bottom_navigation.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_view.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->

        when(item.itemId) {
            R.id.menu_unittest -> {
                //1. Navigation.findNavController(item).nagivate(R.id.action_mainFragment_to_unitTestFragment)
                //2. kotlin-extention / ktx : view.findNavController().navigate(R.id.action_mainFragment_to_unitTestFragment)
                //3. Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_unitTestFragment)
                this.findNavController().navigate(R.id.action_mainFragment_to_unitTestFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_dice_roller -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_diceRollerFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_layout -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_layoutFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_navigation -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_titleFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_webview -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_webViewFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_share -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_shareFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_lifecycle -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_lifeCycleActivity)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_encryption -> {
                this.findNavController().navigate(R.id.action_mainFragment_to_securityFragment)
                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_viewPager -> {
//                this.findNavController().navigate(R.id.action_mainFragment_to_viewPager2Activity)
//                (this.activity as MainActivity).bottom_navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
            else -> { false }
        }
    }

}
