package pizza.asgn.saket.ui.pizza.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.FragmentCustomisationBinding
import pizza.asgn.saket.ui.pizza.adapter.CrustsDataAdapter
import pizza.asgn.saket.ui.pizza.adapter.SizeDataAdapter
import pizza.asgn.saket.ui.pizza.model.Crusts
import pizza.asgn.saket.ui.pizza.model.PizzaCartItem
import pizza.asgn.saket.ui.pizza.model.PizzaInfoDataClass
import pizza.asgn.saket.ui.pizza.model.Sizes
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel

class CustomisationFragment(val defaultCrustId : Int, val defaultSizeId : Int, val pizzaInfo : PizzaInfoDataClass) : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentCustomisationBinding
    var totalPrice = 0
    lateinit var selectedCrustItem : Crusts
    lateinit var selectedSizeItem : Sizes
    private val crustsDataAdapter by lazy { CrustsDataAdapter(requireContext(),
        onItemClicked = { item, sizeItem, position ->
            sizeDataAdapter.selectedSizeId = item.defaultSize?:-1
            sizeDataAdapter.setSizeList(item.sizes)
            totalPrice = item.sizes.filter { itt-> itt.id == item.defaultSize }[0].price?:0
            binding.addTv.text = "${requireContext().getString(R.string.add_item)} ₹$totalPrice"
            selectedCrustItem = item
            selectedSizeItem = sizeItem

        }).apply {
            selectedCrustId = defaultCrustId
            binding.sizeRv.adapter = sizeDataAdapter
            sizeDataAdapter.setSizeList(viewModel.pizzaInfo.value!!.data?.crusts!!.filter { itt-> itt.id == selectedCrustId }[0].sizes)
        }
    }

    private val sizeDataAdapter by lazy { SizeDataAdapter(requireContext(),
        onItemClicked = { sizeItem, position ->
            totalPrice = sizeItem.price?:0
            binding.addTv.text = "${requireContext().getString(R.string.add_item)} ₹$totalPrice"
            selectedSizeItem = sizeItem
        }).apply {
            selectedSizeId = defaultSizeId

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomisationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbarTitleTv.text = getString(R.string.add_pizza)
        binding.toolbarLayout.toolbarBackIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val dataa = viewModel.pizzaInfo.value!!.data
        binding.crustRv.adapter = crustsDataAdapter
        crustsDataAdapter.setCrustList(dataa?.crusts)
        totalPrice = dataa?.crusts!!.filter { itt-> itt.id == defaultCrustId }[0].sizes.filter { itt-> itt.id == defaultSizeId }[0].price?:0
        binding.addTv.text = "${requireContext().getString(R.string.add_item)} ₹$totalPrice"

        selectedCrustItem = dataa.crusts.filter { itt-> itt.id == defaultCrustId }[0]
        selectedSizeItem = selectedCrustItem.sizes.filter { itt-> itt.id == defaultSizeId }[0]

        binding.addItemCl.setOnClickListener {
            val pizzaItem = PizzaCartItem(pizzaId = "${pizzaInfo.id}_${selectedCrustItem.id}_${selectedSizeItem.id}",pizzaName = pizzaInfo.name, crustId = selectedCrustItem.id, crustName = selectedCrustItem.name, sizeId = selectedSizeItem.id, sizeName = selectedSizeItem.name, price = selectedSizeItem.price, isVeg = pizzaInfo.isVeg)
            viewModel.addPizzaItem(pizzaItem)
            requireActivity().onBackPressed()
        }
    }
}