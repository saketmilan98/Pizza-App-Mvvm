package pizza.asgn.saket.ui.pizza.model

import com.google.gson.annotations.SerializedName

data class PizzaInfoDataClass (
    @SerializedName("id"           ) var id           : String?           = null,
    @SerializedName("name"         ) var name         : String?           = null,
    @SerializedName("isVeg"        ) var isVeg        : Boolean?          = null,
    @SerializedName("description"  ) var description  : String?           = null,
    @SerializedName("defaultCrust" ) var defaultCrust : Int?              = null,
    @SerializedName("crusts"       ) var crusts       : ArrayList<Crusts> = arrayListOf()
)

data class Crusts (
    @SerializedName("id"          ) var id          : Int?             = null,
    @SerializedName("name"        ) var name        : String?          = null,
    @SerializedName("defaultSize" ) var defaultSize : Int?             = null,
    @SerializedName("sizes"       ) var sizes       : ArrayList<Sizes> = arrayListOf(),
    @SerializedName("price"       ) var price       : Int?             = null

)

data class Sizes (
    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("price" ) var price : Int?    = null
)

data class PizzaCartItem (
    var pizzaId : String? = null,
    var pizzaName : String? = null,
    var crustId : Int?    = null,
    var crustName : String? = null,
    var sizeId : Int?    = null,
    var sizeName : String?    = null,
    var price : Int? = null,
    var isVeg : Boolean? = null,
    var itemCount : String? = null
)