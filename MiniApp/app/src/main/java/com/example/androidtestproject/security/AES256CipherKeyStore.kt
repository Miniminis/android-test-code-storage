package com.example.androidtestproject.security


import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import org.apache.commons.codec.binary.Base64
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AES256CipherKeyStore {

    companion object {
        private var INSTANCE: AES256CipherKeyStore? = null

        private var secretKey: SecretKey? = null
        private var iv: ByteArray? = null

        private val ALIAS = "miniKey"

        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

        @RequiresApi(Build.VERSION_CODES.M)
        val keyGenParameterSpec
                = KeyGenParameterSpec.Builder(ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()
    }

    fun getInstance(): AES256CipherKeyStore {
        if (INSTANCE == null) {
            synchronized(AES256CipherKeyStore::class.java) {
                if (INSTANCE == null)
                    INSTANCE =
                        AES256CipherKeyStore()
            }
        }

        return INSTANCE!!
    }

    fun AES_Encode(str: String): String? {

        var encodedString: String? = null

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(keyGenParameterSpec)
                secretKey = keyGenerator.generateKey()
            }

            var c = Cipher.getInstance("AES/CBC/PKCS7Padding")
            c.init(Cipher.ENCRYPT_MODE, secretKey)

            iv = c.iv

            var encrypted = c.doFinal(str.toByteArray(charset("UTF-8")))
            encodedString = String(Base64.encodeBase64(encrypted))

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return encodedString
    }

    fun AES_Decode(str: String): String? {

        var decodedString: String? = null

        try {

            var keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val secretKeyEntry: KeyStore.SecretKeyEntry =
                keyStore.getEntry(ALIAS, null) as KeyStore.SecretKeyEntry
            secretKey = secretKeyEntry.secretKey

            var c = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivspec = IvParameterSpec(iv)

            c.init(Cipher.DECRYPT_MODE, secretKey, ivspec)

            var byteStr = Base64.decodeBase64(str.toByteArray())
            decodedString = String(c.doFinal(byteStr), Charset.forName("UTF-8"))

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return decodedString
    }

}