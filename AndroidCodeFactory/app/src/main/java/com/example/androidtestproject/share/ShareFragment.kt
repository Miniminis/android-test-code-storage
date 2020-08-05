package com.example.androidtestproject.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_share.*


class ShareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val cliableView: List<View> = listOf(
            btn_instagram, btn_facebook, btn_kakao, btn_share
        )

        for (item in cliableView) {
            item.setOnClickListener(mClickListener)
        }

    }

    private val mClickListener = View.OnClickListener {
        when(it.id) {
            R.id.btn_instagram -> {

            }
            R.id.btn_facebook -> {

            }
            R.id.btn_kakao -> {

            }
            R.id.btn_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "https://deeplink.miniapp.com")
                startActivity(Intent.createChooser(intent, "다른 어플로 공유하기"))
            }
        }
    }

    private fun getDeepLink(): Uri {
        val inviteCode: String = inviteCode.text.toString()
        return Uri.parse("https://miniapp.com/promotion?code=$inviteCode")
    }

    private fun onDymanicLinkClick() {

    }

}
