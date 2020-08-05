package com.example.androidtestproject.biometric

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.androidtestproject.R
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private val TAG = "mhh"

private lateinit var biometricPrompt: BiometricPrompt

/**
 * A simple [Fragment] subclass.
 * Use the [BiometricFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BiometricFragment : Fragment() {
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

        biometricPrompt = createBiometricPrompt()

        return inflater.inflate(R.layout.fragment_biometric, container, false)
    }

    private fun createBiometricPrompt(): BiometricPrompt {

        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                Log.d(TAG, "$errorCode :: $errString")

                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    loginWithPassword()
                    // Because in this app, the negative button allows the user to enter an account password.
                    // This is completely optional and your app doesn’t have to do it.
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Authentication was successful")

                // Proceed with viewing the private encrypted message.
                //showEncryptedMessage(result.cryptoObject)
            }

        }

        val biometricPrompt = BiometricPrompt(this, executor, callback)
        return biometricPrompt
    }

    private fun loginWithPassword() {
        Toast.makeText(activity, "비밀번호로 로그인을 하겠습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("프롬프트 제목")
            .setSubtitle("프롬프트 부제목")
            .setDescription("프롬프트 상세")
            // Authenticate without requiring the user to press a "confirm"
            // button after satisfying the biometric check
            .setConfirmationRequired(false)
            .setNegativeButtonText("비밀번호 이용해서 로그인하기")
            .build()
        return promptInfo
    }

    private fun generateSecretKey(keyGenParameterSpec: KeyGenParameterSpec) {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyGenerator.init(keyGenParameterSpec)
        }
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null)
        return keyStore.getKey("KEY_NAME", null) as SecretKey
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BiometricFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BiometricFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
