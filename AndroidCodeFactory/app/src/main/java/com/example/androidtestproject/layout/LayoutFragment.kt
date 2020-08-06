package com.example.androidtestproject.layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.androidtestproject.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_layout.*

class LayoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation_layout.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.sub_menu_constraint_layout -> {
                this.findNavController().navigate(R.id.action_layoutFragment_to_constraintLayoutFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.sub_menu_seekbar -> {
                this.findNavController().navigate(R.id.action_aboutMeFragment_to_seekBar)
                return@OnNavigationItemSelectedListener true
            }
            R.id.sub_menu_toggle_btn -> {
                this.findNavController().navigate(R.id.action_aboutMeFragment_to_toggleButtonGroup)
                return@OnNavigationItemSelectedListener true
            }
            R.id.sub_menu_snack_bar -> {
                this.findNavController().navigate(R.id.action_layoutFragment_to_snackBarFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.sub_menu_coordinator_layout -> {
                this.findNavController().navigate(R.id.action_layoutFragment_to_coordinatorLayout)
                return@OnNavigationItemSelectedListener true
            }
            R.id.sub_menu_collapsing_layout -> {
                this.findNavController().navigate(R.id.action_layoutFragment_to_collapsingLayout)
                return@OnNavigationItemSelectedListener true
            }
            else -> { false }
        }
    }
}
