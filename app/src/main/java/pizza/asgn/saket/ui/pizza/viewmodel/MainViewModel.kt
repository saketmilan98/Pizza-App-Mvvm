package pizza.asgn.saket.ui.pizza.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import pizza.asgn.saket.network.ApiInterface
import pizza.asgn.saket.network.RetrofitClient
import pizza.asgn.saket.ui.pizza.model.PizzaInfoDataClass
import pizza.asgn.saket.utils.Resource
import pizza.asgn.saket.utils.Status

class MainViewModel : ViewModel() {
    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface = retrofit.create(ApiInterface::class.java)

    private val _pizzaInfo: MutableLiveData<Resource<PizzaInfoDataClass>> = MutableLiveData()
    val pizzaInfo: LiveData<Resource<PizzaInfoDataClass>> = _pizzaInfo

    suspend fun getPizzaInfo(){
        _pizzaInfo.postValue(Resource.loading(null))
        try{
            val response = apiInterface.getPizzaInfo()
            if (response.isSuccessful) {
                _pizzaInfo.postValue(Resource.success(Gson().fromJson(Gson().toJson(response.body()), PizzaInfoDataClass::class.java)))
            } else {
                _pizzaInfo.postValue(Resource.error(response.errorBody().toString(), null))
            }
        }
        catch (e:Exception){
            _pizzaInfo.postValue(Resource.error(e.localizedMessage?:"unknown error", null))
        }
    }
}