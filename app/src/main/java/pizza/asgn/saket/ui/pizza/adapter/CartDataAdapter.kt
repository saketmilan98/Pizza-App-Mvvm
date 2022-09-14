package pizza.asgn.saket.ui.pizza.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.CartItemRvLayoutBinding
import pizza.asgn.saket.databinding.OptionsRvLayoutBinding
import pizza.asgn.saket.ui.pizza.model.Crusts
import pizza.asgn.saket.ui.pizza.model.PizzaCartItem
import pizza.asgn.saket.ui.pizza.model.Sizes

class CartDataAdapter(val context : Context, val onAddItemClicked: (PizzaCartItem, Int) -> Unit, val onRemoveItemClicked: (PizzaCartItem, Int) -> Unit) : RecyclerView.Adapter<CartDataAdapter.CartViewHolder>() {

    private var cartDataa: ArrayList<PizzaCartItem>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CartViewHolder {
        val cartRvLayoutBinding: CartItemRvLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.cart_item_rv_layout, viewGroup, false
        )
        return CartViewHolder(cartRvLayoutBinding)
    }

    override fun onBindViewHolder(cartViewHolder: CartViewHolder, i: Int) {
        val currentItem: PizzaCartItem = cartDataa!![i]
        cartViewHolder.cartListItemBinding.pizzaItem = currentItem
        cartViewHolder.cartListItemBinding.totalPrice = (currentItem.price?:0)*(currentItem.itemCount?.toInt()?:0)
        cartViewHolder.cartListItemBinding.addIconTv.setOnClickListener {
            onAddItemClicked(currentItem, i)
        }
        cartViewHolder.cartListItemBinding.minusIconTv.setOnClickListener {
            onRemoveItemClicked(currentItem, i)
        }
    }

    override fun getItemCount(): Int {
        return if (cartDataa != null) {
            cartDataa!!.size
        } else {
            0
        }
    }

    fun setCartList(sizes: ArrayList<PizzaCartItem>?) {
        this.cartDataa = sizes
        notifyDataSetChanged()
    }

    inner class CartViewHolder(cartItemRvLayoutBinding: CartItemRvLayoutBinding) :
        RecyclerView.ViewHolder(cartItemRvLayoutBinding.root) {
        val cartListItemBinding: CartItemRvLayoutBinding

        init {
            cartListItemBinding = cartItemRvLayoutBinding
        }
    }
}