package com.viled.core.common.crypto

import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey

abstract class CryptoUtils {

    protected val bytes = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    protected val providerType = "AndroidKeyStore"

    protected val cipher: Cipher = Cipher.getInstance("AES/GCM/NoPadding")
    protected val rsaCipher: Cipher = Cipher.getInstance("RSA/ECB/NoPadding")
    protected val encoding = charset("UTF-8")

    protected val keyStore: KeyStore = KeyStore.getInstance(providerType).apply { load(null) }

    abstract fun encrypt(keyAlias: String, text: String): ByteArray

    abstract fun decrypt(keyAlias: String, encryptedData: ByteArray): String

    abstract fun getKey(alias: String): SecretKey

    abstract fun generateKey(alias: String): SecretKey

    companion object {
        const val RSA_ALGORITHM = "RSA"
        const val AES_ALGORITHM = "AES"
    }
}