package com.example.androidtestproject.biometric

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.androidtestproject.R
import kotlinx.android.synthetic.main.fragment_biometric2.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private val TAG = "mhh"

private lateinit var biometricPrompt: BiometricPrompt

/**
 * A simple [Fragment] subclass.
 * Use the [BiometricFragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class BiometricFragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        biometricPrompt = createBiometricPrompt()

        return inflater.inflate(R.layout.fragment_biometric2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            val promptInfo = createPromptInfo()
            when (BiometricManager.from(this.activity!!).canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
//                biometricPrompt.authenticate(promptInfo, cryptoObject)
                    biometricPrompt.authenticate(promptInfo)
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    Log.e(TAG, "No biometric features available on this device.")
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    Log.e(TAG,"Biometric features are currently unavailable.")
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    Log.e(TAG,"The user hasn't associated any biometric credentials " +
                            "with their account.")
                else -> loginWithPassword()
            }
        }
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                Log.d(TAG, "$errorCode :: $errString")
                txt_result.text = "onAuthenticationError : $errorCode :: $errString "

                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    loginWithPassword() // Because in this app, the negative button allows the user to enter an account password. This is completely optional and your app doesn’t have to do it.
                }

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "Authentication failed for an unknown reason")
                txt_result.text = "Authentication failed for an unknown reason"

            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                Log.d(TAG, "Authentication was successful")
                txt_result.text = "Authentication was successful"

                // Proceed with viewing the private encrypted message.
                showEncryptedMessage(result.cryptoObject)
            }
        }

        return BiometricPrompt(this, executor, callback)
    }

    private fun loginWithPassword() {
        Log.d(TAG, "user log in with pw")
        txt_result.text = "loginWithPassword"
    }

    private fun showEncryptedMessage(cryptoObject: BiometricPrompt.CryptoObject?) {
        Log.d(TAG, "show encrypted msg : $cryptoObject")
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("지문 로그인")
            .setSubtitle("등록된 지문을 통해 로그인 해주세요.")
//            .setDescription("R.string.prompt_info_description")
            // Authenticate without requiring the user to press a "confirm"
            // button after satisfying the biometric check
//            .setConfirmationRequired(false)
            .setNegativeButtonText("비밀번호로 로그인")
            .build()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BiometricFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BiometricFragment2().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
