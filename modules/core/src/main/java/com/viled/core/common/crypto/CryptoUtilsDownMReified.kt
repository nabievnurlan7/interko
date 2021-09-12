package com.viled.core.common.crypto

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import com.viled.core.common.SharedPrefLayer
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.security.auth.x500.X500Principal

class CryptoUtilsDownMReified(private val context: Context) : CryptoUtils() {

    val sharedPrefLayer: SharedPrefLayer by lazy { SharedPrefLayer(context) }

    override fun encrypt(keyAlias: String, text: String): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, generateKey(keyAlias), GCMParameterSpec(128, bytes))
        return cipher.doFinal(text.toByteArray(encoding))
    }

    override fun decrypt(keyAlias: String, encryptedData: ByteArray): String {
        cipher.init(Cipher.DECRYPT_MODE, getKey(keyAlias), GCMParameterSpec(128, bytes))
        return cipher.doFinal(encryptedData).toString(encoding)
    }

    override fun getKey(alias: String): SecretKey =
        sharedPrefLayer.keyName.let {
            val encryptedKey = Base64.decode(it, Base64.DEFAULT)

            val rsaPrivateKey = keyStore.getKey(alias, null) as? PrivateKey
                ?: generateRsaKey(alias).private
            rsaCipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey)
            val key = rsaCipher.doFinal(encryptedKey)

            SecretKeySpec(key, AES_ALGORITHM)
        }

    override fun generateKey(alias: String): SecretKey {
        val key = ByteArray(8)
        SecureRandom().run { nextBytes(key) }

        val rsaPublicKey =
            keyStore.getCertificate(alias)?.publicKey ?: generateRsaKey(alias).public
        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey)
        val rsaEncryptKey = rsaCipher.doFinal(key)

        sharedPrefLayer.keyName = Base64.encodeToString(rsaEncryptKey, Base64.DEFAULT)
        return SecretKeySpec(key, AES_ALGORITHM)
    }

    fun generateRsaKey(alias: String): KeyPair {
        val start: Calendar = Calendar.getInstance()
        val end: Calendar = Calendar.getInstance()
        end.add(Calendar.YEAR, 30)
        val spec = KeyPairGeneratorSpec.Builder(context)
            .setAlias(alias)
            .setSubject(X500Principal("CN=$alias"))
            .setSerialNumber(BigInteger.TEN)
            .setStartDate(start.time)
            .setEndDate(end.time)
            .build()
        return KeyPairGenerator.getInstance(RSA_ALGORITHM, providerType).run {
            initialize(spec)
            generateKeyPair()
        }
    }
}