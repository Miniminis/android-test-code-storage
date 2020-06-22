package com.example.androidtestproject.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.FragmentTriviaTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTriviaTitleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_trivia_title, container, false)

        binding.playButton.setOnClickListener(mClickListener)
        setHasOptionsMenu(true)

        return binding.root
    }

    private val mClickListener = View.OnClickListener {
        when(it.id) {
            R.id.playButton -> {
                this.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_about, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }


}
