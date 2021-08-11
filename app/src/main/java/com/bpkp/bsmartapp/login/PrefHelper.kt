package com.bpkp.bsmartapp.login

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(context: Context) {

    //DECLARATION
    private val PREFS_NAME = "sharedpref12345"

    //CONSTRUCTOR
    private val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()
    //    private var sharedPref: SharedPreferences =
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val spec = KeyGenParameterSpec.Builder(
//                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
//                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//            )
//                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
//                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//                .build()
//            val masterKey = MasterKey.Builder(context)
//                .setKeyGenParameterSpec(spec)
//                .build()
//            EncryptedSharedPreferences
//                .create(
//                    context,
//                    PREFS_NAME,
//                    masterKey,
//                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//                )
//        }
//        else {
//            SecurePreferences(context, "bsmart", PREFS_NAME)
//        }


    //SETTER
    fun put(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    //GETTER
    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    //CLEANER UP
    fun clear() {
        editor.clear().apply()
    }

}