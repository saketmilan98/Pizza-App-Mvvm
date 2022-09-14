package pizza.asgn.saket.ui.pizza.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pizza.asgn.saket.databinding.FragmentCustomisationBinding
import pizza.asgn.saket.ui.pizza.adapter.CrustsDataAdapter
import pizza.asgn.saket.ui.pizza.adapter.SizeDataAdapter
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel

class CustomisationFragment(val defaultCrustId : Int, val defaultSizeId : Int) : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentCustomisationBinding
    private val crustsDataAdapter by lazy { CrustsDataAdapter(requireContext(),
        onItemClicked = { item, position ->
            sizeDataAdapter.selectedSizeId = item.defaultSize?:-1
            sizeDataAdapter.setSizeList(item.sizes)
        }).apply {
            selectedCrustId = defaultCrustId
            binding.sizeRv.adapter = sizeDataAdapter
            sizeDataAdapter.setSizeList(viewModel.pizzaInfo.value!!.data?.crusts!!.filter { itt-> itt.id == selectedCrustId }[0].sizes)
        }
    }

    private val sizeDataAdapter by lazy { SizeDataAdapter(requireContext(),
        onItemClicked = { item, position ->

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
        binding.crustRv.adapter = crustsDataAdapter
        crustsDataAdapter.setCrustList(viewModel.pizzaInfo.value!!.data?.crusts)
    }
}