package com.viled.core.common.crypto

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class CryptoUtilsReified : CryptoUtils() {

    override fun encrypt(keyAlias: String, text: String): ByteArray {
        TODO("Not yet implemented")
    }

    override fun decrypt(keyAlias: String, encryptedData: ByteArray): String {
        TODO("Not yet implemented")
    }
}