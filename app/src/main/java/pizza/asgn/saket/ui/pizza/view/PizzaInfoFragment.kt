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

/**
 * A simple [Fragment] subclass.
 * Use the [PizzaInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PizzaInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentPizzaInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        var defaultSize = 0
        if(defaultCrustObject.isNotEmpty()){
            defaultSize = defaultCrustObject[0].defaultSize?:0
            val defaultSizeObject = defaultCrustObject[0].sizes.filter { itt-> itt.id == defaultSize } as ArrayList<Sizes>
            binding.priceTv.text = "₹${defaultSizeObject[0].price}"
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PizzaInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PizzaInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}