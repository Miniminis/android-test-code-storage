package com.example.androidtestproject.security

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.androidtestproject.R
import com.example.androidtestproject.databinding.FragmentSecurityBinding
import kotlinx.android.synthetic.main.fragment_security.*
import kotlinx.android.synthetic.main.fragment_share.*

class SecurityFragment : Fragment() {

    private lateinit var binding: FragmentSecurityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_security, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cliableView: List<View> = listOf(
            btn_aes_encryption, btn_aes_decryption, btn_rsa_encryption, btn_rsa_decryption
        )

        for (item in cliableView) {
            item.setOnClickListener(mClickListener)
        }

    }

    private val mClickListener = View.OnClickListener {
        when(it.id) {
            R.id.btn_aes_encryption -> {
                binding.txtSecurity02.text = AES256CipherKeyStore().getInstance().AES_Encode(binding.txtSecurity01.text.trim().toString())
//                binding.txtSecurity02.text = AES256CipherEncryptedSharedPreferences(activity!!).getInstance().AES_Encode(binding.txtSecurity01.text.trim().toString())
                binding.txtSecurity03.text = binding.txtSecurity02.text
            }
            R.id.btn_aes_decryption -> {
                binding.txtSecurity04.text = AES256CipherKeyStore().getInstance().AES_Decode(binding.txtSecurity03.text.trim().toString())
//                binding.txtSecurity04.text = AES256CipherEncryptedSharedPreferences(activity!!).getInstance().AES_Decode(binding.txtSecurity03.text.trim().toString())
            }
            R.id.btn_rsa_encryption -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    binding.txtSecurity02.text = RSACipher().encrypt(activity!!, binding.txtSecurity01.text.trim().toString())
                }
                binding.txtSecurity03.text = binding.txtSecurity02.text
            }
            R.id.btn_rsa_decryption -> {
                binding.txtSecurity04.text = RSACipher().decrypt(binding.txtSecurity03.text.trim().toString())
            }
        }
    }

}
