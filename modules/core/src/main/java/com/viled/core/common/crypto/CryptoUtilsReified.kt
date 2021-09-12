package com.viled.core.common.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

@RequiresApi(Build.VERSION_CODES.M)
class CryptoUtilsReified : CryptoUtils() {

    private val keyGenerator: KeyGenerator =
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, providerType)

    override fun getKey(alias: String): SecretKey =
        keyStore.getKey(alias, null) as? SecretKey ?: generateKey(alias)

    override fun generateKey(alias: String): SecretKey = keyGenerator.apply {
        init(
            KeyGenParameterSpec
                .Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
    }.generateKey()

    override fun encrypt(keyAlias: String, text: String): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, getKey(keyAlias), GCMParameterSpec(96, bytes))
        return cipher.doFinal(text.toByteArray(encoding))
    }

    override fun decrypt(keyAlias: String, encryptedData: ByteArray): String {
        cipher.init(Cipher.DECRYPT_MODE, getKey(keyAlias), GCMParameterSpec(96, bytes))
        return cipher.doFinal(encryptedData).toString(encoding)
    }
}