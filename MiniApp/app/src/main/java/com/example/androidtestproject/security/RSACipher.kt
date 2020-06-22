package com.example.androidtestproject.security

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import androidx.annotation.RequiresApi
import java.io.IOException
import java.math.BigInteger
import java.security.cert.CertificateException
import java.security.cert.Certificate
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.security.auth.x500.X500Principal
import org.apache.commons.codec.binary.Base64
import java.security.*
import java.util.*


class RSACipher {

    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val ALIAS = "miniapp"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Throws(
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        CertificateException::class,
        IOException::class,
        UnrecoverableEntryException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun createKeys(context: Context): KeyStore.Entry? {

        val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)

        val containsAlias: Boolean = keyStore.containsAlias(ALIAS)

        if (!containsAlias) {
            val kpg: KeyPairGenerator = KeyPairGenerator.getInstance("RSA", ANDROID_KEY_STORE)

            val start: Calendar = Calendar.getInstance(Locale.ENGLISH)
            val end: Calendar = Calendar.getInstance(Locale.ENGLISH)
            end.add(Calendar.YEAR, 1)

            val spec = KeyPairGeneratorSpec.Builder(context)
                .setAlias(ALIAS)
                .setSubject(X500Principal("CN=$ALIAS"))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()
            kpg.initialize(spec)
            kpg.generateKeyPair()
        }

        return keyStore.getEntry(ALIAS, null)
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    private fun encryptUsingKey(publicKey: PublicKey, bytes: ByteArray): ByteArray {
        val inCipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        inCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return inCipher.doFinal(bytes)
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    private fun decryptUsingKey(privateKey: PrivateKey, bytes: ByteArray): ByteArray {
        val inCipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        inCipher.init(Cipher.DECRYPT_MODE, privateKey)
        return inCipher.doFinal(bytes)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Throws(
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        CertificateException::class,
        IOException::class,
        UnrecoverableEntryException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class
    )
    fun encrypt(context: Context?, plainText: String): String? {
        val entry = createKeys(context!!)
        if (entry is KeyStore.PrivateKeyEntry) {
            val certificate: Certificate = entry.certificate!!
            val publicKey = certificate.publicKey
            val bytes = plainText.toByteArray(charset("UTF-8"))
            val encryptedBytes = encryptUsingKey(publicKey, bytes)
            val base64encryptedBytes: ByteArray = Base64.encodeBase64(encryptedBytes)
            return String(base64encryptedBytes)
        }
        return null
    }

    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        KeyStoreException::class,
        CertificateException::class,
        IOException::class,
        UnrecoverableEntryException::class
    )
    fun decrypt(cipherText: String): String? {

        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)

        val entry = keyStore.getEntry(ALIAS, null)

        if (entry is KeyStore.PrivateKeyEntry) {
            val privateKey = entry.privateKey
            val bytes = cipherText.toByteArray(charset("UTF-8"))
            val base64encryptedBytes: ByteArray = Base64.decodeBase64(bytes)
            val decryptedBytes = decryptUsingKey(privateKey, base64encryptedBytes)
            return String(decryptedBytes)
        }

        return null
    }


}