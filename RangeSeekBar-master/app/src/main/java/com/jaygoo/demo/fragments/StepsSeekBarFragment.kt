package com.jaygoo.demo.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.jaygoo.demo.R
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.jaygoo.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_step.*
import java.util.ArrayList

/**
//                       _ooOoo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                       O\ = /O
//                   ____/`---'\____
//                 .   ' \\| |// `.
//                  / \\||| : |||// \
//                / _||||| -:- |||||- \
//                  | | \\\ - /// | |
//                | \_| ''\---/'' | |
//                 \ .-\__ `-` ___/-. /
//              ______`. .' /--.--\ `. . __
//           ."" '< `.___\_<|>_/___.' >'"".
//          | | : `- \`.;`\ _ /`;.`/ - ` : | |
//            \ \ `-. \_ __\ /__ _/ .-` / /
//    ======`-.____`-.___\_____/___.-`____.-'======
//                       `=---='
//
//    .............................................
//             佛祖保佑             永无BUG
 * =====================================================
 * 作    者：JayGoo
 * 创建日期：2019-06-14
 * 描    述:
 * =====================================================
 */
class StepsSeekBarFragment : BaseFragment() {
	override fun getLayoutId(): Int {
		return R.layout.fragment_step
	}

	override fun initView(view: View) {
		val stepsDrawables = ArrayList<Int>()
		stepsDrawables.add(R.drawable.step_1)
		stepsDrawables.add(R.drawable.step_2)
		stepsDrawables.add(R.drawable.step_3)
		stepsDrawables.add(R.drawable.step_4)
		sb_steps_1?.setStepsDrawable(stepsDrawables)
		sb_steps_2?.setStepsDrawable(stepsDrawables)

		sb_steps_8.setOnRangeChangedListener(mRangeChangeListener)
	}

	private val mRangeChangeListener = object : OnRangeChangedListener {
		override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

		}

		override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
			Log.d("mhh", "$leftValue :: $rightValue")
		}

		override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
			Log.d("mhh", "${view?.minProgress} : ${view?.maxProgress} : ${view?.tickMarkTextArray} : ")
		}

	}

}