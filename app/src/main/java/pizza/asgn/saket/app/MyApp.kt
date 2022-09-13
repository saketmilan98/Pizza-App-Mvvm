package pizza.asgn.saket.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        currentApplication = this
    }

    companion object{
        private var currentApplication: MyApp? = null
        fun getInstance(): MyApp? {
            return currentApplication
        }
        val BASE_URL = "https://625bbd9d50128c570206e502.mockapi.io/api/v1/"
        val sharedPreferences: SharedPreferences by lazy {
            currentApplication!!.getSharedPreferences(
                "pizzaappsharedpref",
                Context.MODE_PRIVATE
            )
        }
    }
}