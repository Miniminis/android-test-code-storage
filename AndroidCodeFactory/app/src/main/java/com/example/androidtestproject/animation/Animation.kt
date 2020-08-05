package com.example.androidtestproject.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation
import androidx.fragment.app.Fragment
import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_animation.*

class Animation : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_anim_visibility.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) anim_list.animateVisibility(true)
            else anim_list.animateVisibility(false)
        }

    }

    private fun View.animateVisibility(setVisible: Boolean) {
        if (setVisible) expand(this) else collapse(this)
    }

    private fun expand(view: View) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val initialHeight = 0
        val targetHeight = view.measuredHeight

        // Older versions of Android (pre API 21) cancel animations for views with a height of 0.
        //v.getLayoutParams().height = 1;
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE

        animateView(view, initialHeight, targetHeight)
    }

    private fun collapse(view: View) {
        val initialHeight = view.measuredHeight
        val targetHeight = 0

        animateView(view, initialHeight, targetHeight)
    }

    private fun animateView(v: View, initialHeight: Int, targetHeight: Int) {
        val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                v.layoutParams.height = targetHeight
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        valueAnimator.duration = 300
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.start()
    }



}