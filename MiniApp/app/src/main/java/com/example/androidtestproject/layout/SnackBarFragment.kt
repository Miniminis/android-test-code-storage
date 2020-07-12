package com.example.androidtestproject.layout

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.ActionBarContextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.androidadvance.topsnackbar.TSnackbar
import com.example.androidtestproject.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_snack_bar.*


class SnackBarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snack_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val views: List<View> = listOf(btn_snackbar_01, btn_snackbar_02, btn_snackbar_03)
        for (v in views) {
            v.setOnClickListener(mClickListener)
        }
    }

    private val mClickListener = View.OnClickListener {
        when (it.id) {
            R.id.btn_snackbar_01 -> {
//                Snackbar.make(view!!, "my message!", Snackbar.LENGTH_SHORT).show()
                SimpleCustomSnackbar.make(view!!, "Something went wrong", Snackbar.LENGTH_INDEFINITE, clicklistneer,
                    R.drawable.oreo, "RETRY", ContextCompat.getColor(this.activity!!, R.color.green)
                )?.show()
            }
            R.id.btn_snackbar_02 -> {
                Snackbar.make(view!!, "snackbar with action!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Update") {
                        Toast.makeText(this.activity, "snackbar pressed!", Toast.LENGTH_SHORT)
                            .show()
                    }
//                    .setAnchorView(R.id.layout_activity)
                    .show()
            }
            R.id.btn_snackbar_03 -> {
//                TSnackbar.make(view!!,"Hello from TSnackBar.",TSnackbar.LENGTH_LONG).show()

                val snackbar = TSnackbar
                    .make(view!!, "Snacking Left & Right", TSnackbar.LENGTH_LONG)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.setIconLeft(R.drawable.android, 24f) //Size in dp - 24 is great!
//                snackbar.setIconRight(R.drawable.ic_android_green_24dp, 48f) //Resize to bigger dp
                snackbar.setIconPadding(8)
                snackbar.setMaxWidth(3000) //if you want fullsize on tablets

                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.parseColor("#ffffff"))
                val textView =
                    snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
                textView.setTextColor(Color.parseColor("#4b4b4b"))
                snackbar.show()
            }
        }
    }

    private val clicklistneer: View.OnClickListener = View.OnClickListener {
        SimpleCustomSnackbar.make(
            view!!,
            "Updated successfully",
            Snackbar.LENGTH_INDEFINITE,
            null,
            R.drawable.nougat,
            null,
            ContextCompat.getColor(this.activity!!, R.color.colorAccentBright)
        )?.show()
    }

}
