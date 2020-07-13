package com.example.androidtestproject.dialogwithpager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager

import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_dialog_main.*

/**
 * A simple [Fragment] subclass.
 */
class DialogMainFragment : Fragment() {

    lateinit var dialogFragment: DialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_viewpager.setOnClickListener(mClickListener)
        btn_Viewpager2.setOnClickListener(mClickListener)
        btn_dialog.setOnClickListener(mClickListener)
    }

    private var mClickListener = View.OnClickListener {
        when(it.id) {
            R.id.btn_viewpager -> {
                var intent = Intent(this.activity, FragmentSlideActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_Viewpager2 -> {
                var intent = Intent(this.activity, ViewPagerTest02::class.java)
                startActivity(intent)
            }
            R.id.btn_dialog -> {

//                var pref = getSharedPreferences("SETTING", 0)
//                var chkNotAnymore = pref.getBoolean("SETTING_CHK", false)
//                if(!chkNotAnymore) {
                var fm: FragmentManager = activity?.supportFragmentManager!!
                dialogFragment = DialogFragment()
                dialogFragment.show(fm, "DialogFragment")
//                }
            }
        }
    }



}
