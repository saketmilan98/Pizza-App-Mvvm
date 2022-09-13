package pizza.asgn.saket.utils

import android.content.Context
import android.widget.Toast
import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.string
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import pizza.asgn.saket.app.MyApp

fun Context.showToast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}


fun putModel(key: String, value: Any?) {
    val json = Gson().toJson(value)
    val sp = MyApp.sharedPreferences
    sp.edit().putString(key, json).commit()
}

inline fun <reified T> getModel(key: String): T? where T : Any {
    val sp = MyApp.sharedPreferences
    return sp.getString(key, null)?.let { Gson().fromJson(it) }
}