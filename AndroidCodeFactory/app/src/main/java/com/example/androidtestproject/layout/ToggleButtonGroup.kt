package com.example.androidtestproject.layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_toggle_button_group.*

/**
 * A simple [Fragment] subclass.
 */
class ToggleButtonGroup : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toggle_button_group, container, false)
    }
}
