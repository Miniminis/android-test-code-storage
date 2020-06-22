/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidtestproject.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.FragmentTriviaGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTriviaGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_trivia_game_won, container, false)

        binding.nextMatchButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

//        var args:GameWonFragmentArgs? = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
//        Toast.makeText(context, "NumCurrect : ${args?.numCorrects} / ${args?.numQuestions}", Toast.LENGTH_SHORT).show()

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getShareIntent(): Intent {
        var args:GameWonFragmentArgs? = arguments?.let { GameWonFragmentArgs.fromBundle(it) }

        //방법 1)
//        return Intent(Intent.ACTION_SEND).apply {
//            type = "text/plain"
//            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args?.numCorrects, args?.numQuestions))
//        }

        //방법 2)
        return ShareCompat.IntentBuilder.from(this.activity!!)
            .setText(getString(R.string.share_success_text, args?.numCorrects, args?.numQuestions))
            .setType("text/plain")        //raise exception
            .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_share, menu)

        //만약에 공유 가능한 앱 (액티비티)가 없을 경우에는 아예 share icon 을 숨김
        if(getShareIntent().resolveActivity(activity!!.packageManager) == null) {
            menu?.findItem(R.id.share)?.isVisible = false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.share -> {
                shareSuccess()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
