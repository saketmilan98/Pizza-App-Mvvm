package pizza.asgn.saket.ui.pizza.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.FragmentPizzaInfoBinding
import pizza.asgn.saket.ui.pizza.model.Crusts
import pizza.asgn.saket.ui.pizza.model.Sizes
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel

class PizzaInfoFragment : Fragment() {
    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentPizzaInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPizzaInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dataa = viewModel.pizzaInfo.value?.data
        binding.toolbarLayout.toolbarTitleTv.text = getString(R.string.app_name)
        binding.toolbarLayout.toolbarBackIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_local_pizza_24))
        val dataa = viewModel.pizzaInfo.value?.data
        val defaultCrustId = dataa?.defaultCrust
        val defaultCrustObject = dataa?.crusts?.filter { itt-> itt.id == defaultCrustId } as ArrayList<Crusts>
        var defaultSizeId = 0
        if(defaultCrustObject.isNotEmpty()){
            defaultSizeId = defaultCrustObject[0].defaultSize?:0
            val defaultSizeObject = defaultCrustObject[0].sizes.filter { itt-> itt.id == defaultSizeId } as ArrayList<Sizes>
            binding.priceTv.text = "₹${defaultSizeObject[0].price}"
        }
        binding.addTv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, CustomisationFragment(defaultCrustId?:-1, defaultSizeId?:-1, dataa)).addToBackStack(null).commit()
        }
        binding.addIconTv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, CustomisationFragment(defaultCrustId?:-1, defaultSizeId?:-1, dataa)).addToBackStack(null).commit()
        }
        binding.minusIconTv.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, CartFragment()).addToBackStack(null).commit()
        }
        binding.viewCartCl.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, CartFragment()).addToBackStack(null).commit()
        }

        viewModel.pizzaInCartList.observe(viewLifecycleOwner, Observer {
            var sum = 0
            var totalPrice = 0
            for((k,v) in it){
                sum += v
                totalPrice+= ((k.price?:1)*v)
            }
            if(sum==0){
                binding.addTv.text = "ADD"
                binding.addIconTv.isVisible = false
                binding.minusIconTv.isVisible = false
                binding.viewCartCl.isVisible = false
            }
            else {
                binding.addTv.text = "$sum"
                binding.addIconTv.isVisible = true
                binding.minusIconTv.isVisible = true
                binding.viewCartCl.isVisible = true
                binding.totalPriceTv.text = "₹$totalPrice"
            }
        })

    }
}