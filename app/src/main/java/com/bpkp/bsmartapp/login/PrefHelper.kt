package com.bpkp.bsmartapp.login

import android.content.Context
import android.content.SharedPreferences

class PrefHelper (context : Context) {

    //DECLARATION
    private val PREFS_NAME = "sharedpref12345"
    private var sharedPref : SharedPreferences
    val editor : SharedPreferences.Editor

    //CONSTRUCTOR
    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    //SETTER
    fun put(key: String, value: String){
        editor.putString(key, value).apply()
    }

    fun put(key: String, value: Boolean){
        editor.putBoolean(key, value).apply()
    }

    //GETTER
    fun getString(key: String):String?{
        return sharedPref.getString(key, null)
    }

    fun getBoolean(key: String):Boolean{
        return sharedPref.getBoolean(key, false)
    }

    //CLEANER UP
    fun clear(){
        editor.clear().apply()
    }


}