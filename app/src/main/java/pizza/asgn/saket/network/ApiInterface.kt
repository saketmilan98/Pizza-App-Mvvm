package pizza.asgn.saket.network

import pizza.asgn.saket.ui.pizza.model.PizzaInfoDataClass
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("pizza/1")
    suspend fun getPizzaInfo(): Response<PizzaInfoDataClass>
}