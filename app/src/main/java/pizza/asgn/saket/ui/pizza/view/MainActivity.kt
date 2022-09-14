package pizza.asgn.saket.ui.pizza.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.ActivityMainBinding
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel
import pizza.asgn.saket.utils.Status
import pizza.asgn.saket.utils.showToast

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            mainViewModel.getPizzaInfo()
        }
        mainViewModel.pizzaInfo.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { mainInfo ->
                        supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, PizzaInfoFragment()).commit()
                    }
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    showToast("Api Call Failed: ${it.message}")
                }
            }
        })

    }
}