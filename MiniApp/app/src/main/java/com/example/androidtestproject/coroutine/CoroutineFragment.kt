package com.example.androidtestproject.coroutine

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.FragmentCoroutineBinding
import kotlinx.coroutines.*
import java.lang.Exception

class CoroutineFragment : Fragment() {

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _str = MutableLiveData<String>()
    val str: LiveData<String> get() = _str

    private lateinit var binding: FragmentCoroutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coroutine, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //lifecycle owner, binding variable 연결 필수 ㅠㅠㅠ 내 2시간 ㅠㅠㅠㅠㅠ
        binding.lifecycleOwner = this
        binding.vmodel = this

        binding.button.setOnClickListener {
            Toast.makeText(this.activity, "btn click!", Toast.LENGTH_SHORT).show()
            connectGet2()
        }

        connectGet()
        observation()
    }

    private fun observation() {
        str.observe(this, Observer {
            if(it != null) {
                Toast.makeText(this.activity, "데이터 로드!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun connectGet() {
        commonLaunch(Api.retrofitService.getPropertiesAsync(), "connectGet")
    }

    fun connectGet2() {
        commonLaunch(Api.retrofitService.getPropertiesAsync2(), "connectGet2")
    }

    fun commonLaunch(ApiFunName: Deferred<Any>, funName: String) {
        coroutineScope.launch {

            //            var deferredProperty = Api.retrofitService.getPropertiesAsync()
            var deferredProperty = ApiFunName

            try {
//                _status.value = MarsApiStatus.LOADING
                var listResult = deferredProperty.await()
//                _status.value = MarsApiStatus.DONE      //list 가 받아지면 , done
                when(funName) {
                    "connectGet" -> {
                        _str.value = listResult.toString()
                    }
                    "connectGet2" -> {
                        _str.value = "connectGet2 result :  $listResult"
                    }
                    else -> {
                        Toast.makeText(activity, "데이터 로드!", Toast.LENGTH_SHORT).show()
                    }
                }

//                viewModelJob.cancel()

            } catch (e: Exception) {
                Log.d("test", "${e.message}")
//                _status.value = MarsApiStatus.ERROR
            }
        }
    }



}
