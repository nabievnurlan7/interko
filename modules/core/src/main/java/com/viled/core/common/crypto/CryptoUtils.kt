package com.viled.core.common.crypto

import java.security.KeyStore
import javax.crypto.Cipher

abstract class CryptoUtils {

    private val bytes = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val providerType = "AndroidKeyStore"

    private val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    private val rsaCipher = Cipher.getInstance("RSA/ECB/NoPadding")
    private val encoding = charset("UTF-8")

    private val keyStore = KeyStore.getInstance(providerType).apply { load(null) }


    abstract fun encrypt(keyAlias: String, text: String): ByteArray

    abstract fun decrypt(keyAlias: String, encryptedData: ByteArray): String
}