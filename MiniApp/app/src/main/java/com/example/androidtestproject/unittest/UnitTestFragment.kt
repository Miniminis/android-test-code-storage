package com.example.androidtestproject.unittest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_unit_test.*

class UnitTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unit_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_to_unittest_result.setOnClickListener {
            it.findNavController().navigate(R.id.action_unitTestFragment_to_unitTestFragment02)
        }
    }
}
