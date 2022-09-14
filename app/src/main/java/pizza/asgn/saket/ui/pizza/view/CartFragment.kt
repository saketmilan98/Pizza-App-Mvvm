package pizza.asgn.saket.ui.pizza.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.FragmentCartBinding
import pizza.asgn.saket.ui.pizza.adapter.CartDataAdapter
import pizza.asgn.saket.ui.pizza.model.PizzaCartItem
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel

class CartFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentCartBinding

    private val cartDataAdapter by lazy { CartDataAdapter(requireContext(),
        onAddItemClicked = { cartItem, position ->
            viewModel.addPizzaItem(cartItem.copy(itemCount = null))
            setCartRvData()
        },
        onRemoveItemClicked = { cartItem, position ->
            viewModel.removePizzaItem(cartItem.copy(itemCount = null))
            setCartRvData()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pizzaListRv.adapter = cartDataAdapter
        setCartRvData()
        viewModel.pizzaInCartList.observe(viewLifecycleOwner, Observer {
            var sum = 0
            var totalPrice = 0
            for((k,v) in it){
                sum += v
                totalPrice+= ((k.price?:1)*v)
            }
            if(sum==0){
                binding.placeOrderCl.isVisible = false
            }
            else {
                binding.placeOrderCl.isVisible = true
                binding.totalPriceTv.text = "₹$totalPrice"
            }
        })
    }

    fun setCartRvData(){
        val cartHashMap = viewModel.getPizzaInCartList()
        val mainCartList = ArrayList<PizzaCartItem>()
        for((k,v) in cartHashMap){
            mainCartList.add(k.copy(itemCount = v.toString()))
        }
        cartDataAdapter.setCartList(mainCartList)
        if(mainCartList.isEmpty()){
            requireActivity().onBackPressed()
        }
    }

}