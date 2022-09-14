package pizza.asgn.saket.ui.pizza.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.FragmentPizzaInfoBinding
import pizza.asgn.saket.ui.pizza.model.Crusts
import pizza.asgn.saket.ui.pizza.model.Sizes
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, CustomisationFragment(defaultCrustId?:-1, defaultSizeId?:-1)).addToBackStack(null).commit()
        }
    }
}