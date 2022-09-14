package pizza.asgn.saket.ui.pizza.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import pizza.asgn.saket.network.ApiInterface
import pizza.asgn.saket.network.RetrofitClient
import pizza.asgn.saket.ui.pizza.model.PizzaCartItem
import pizza.asgn.saket.ui.pizza.model.PizzaInfoDataClass
import pizza.asgn.saket.utils.Resource
import pizza.asgn.saket.utils.Status
import pizza.asgn.saket.utils.getModel
import pizza.asgn.saket.utils.putModel

class MainViewModel : ViewModel() {
    private val retrofit = RetrofitClient.getInstance()
    private val apiInterface = retrofit.create(ApiInterface::class.java)

    private val _pizzaInfo: MutableLiveData<Resource<PizzaInfoDataClass>> = MutableLiveData()
    val pizzaInfo: LiveData<Resource<PizzaInfoDataClass>> = _pizzaInfo

    private val _pizzaInCartList: MutableLiveData<LinkedHashMap< PizzaCartItem, Int >> = MutableLiveData()
    val pizzaInCartList: LiveData<LinkedHashMap<PizzaCartItem, Int >> = _pizzaInCartList

    suspend fun getPizzaInfo(){
        _pizzaInfo.postValue(Resource.loading(null))
        if(getModel<PizzaInfoDataClass>("pizzaResponseModel") != null){
            _pizzaInfo.postValue(Resource.success(getModel("pizzaResponseModel")))
        }
        else {
            try{
                val response = apiInterface.getPizzaInfo()
                if (response.isSuccessful) {
                    _pizzaInfo.postValue(Resource.success(response.body()))
                    putModel("pizzaResponseModel", response.body())
                } else {
                    _pizzaInfo.postValue(Resource.error(response.errorBody().toString(), null))
                }
            }
            catch (e:Exception){
                _pizzaInfo.postValue(Resource.error(e.localizedMessage?:"unknown error", null))
            }
        }
    }

    fun getPizzaInCartList() : LinkedHashMap<PizzaCartItem, Int> {
        return _pizzaInCartList.value?: LinkedHashMap()
    }

    fun addPizzaItem(pizzaItem : PizzaCartItem){
        val tempHashMap = _pizzaInCartList.value?: LinkedHashMap()
        tempHashMap[pizzaItem] = (tempHashMap[pizzaItem]?:0)+1
        _pizzaInCartList.postValue(tempHashMap)
    }

    fun removePizzaItem(pizzaItem : PizzaCartItem){
        val tempHashMap = _pizzaInCartList.value?:LinkedHashMap()
        tempHashMap[pizzaItem] = (tempHashMap[pizzaItem]?:0)-1
        if((tempHashMap[pizzaItem]?:0)<=0){
            tempHashMap.remove(pizzaItem)
        }
        _pizzaInCartList.postValue(tempHashMap)
    }

}