package com.example.androidtestproject.layout

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_constraint_layout.*


class ConstraintLayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_constraint_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> = listOf(textView12, textView13, textView14, textView16)

        for (item in clickableViews) {
            item?.setOnClickListener(mOnClickListener)
        }
    }

    private val mOnClickListener = View.OnClickListener {
        when(it.id) {
            R.id.textView12 -> {
                it.setBackgroundColor(Color.BLACK)
                this.findNavController().navigate(R.id.action_aboutMeFragment_to_toggleButtonGroup)
            }
            R.id.textView13 -> it.setBackgroundResource(R.color.colorAccent)
            R.id.textView14 -> it.setBackgroundColor(Color.MAGENTA)
            R.id.textView16 -> it.setBackgroundResource(R.color.colorPrimaryDark)
        }
    }


}
