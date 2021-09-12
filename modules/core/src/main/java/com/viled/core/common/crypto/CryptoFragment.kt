package com.viled.core.common.crypto

import android.content.Context
import android.os.Build
import com.viled.core.R
import com.viled.core.common.base.BaseFragment

class CryptoFragment : BaseFragment(R.layout.fragment_crypto) {

    private lateinit var cryptoUtils: CryptoUtils

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cryptoUtils =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) CryptoUtilsReified()
            else CryptoUtilsLowerMReified(requireContext())
    }

    override fun setUI() {
        super.setUI()


    }
}