package pizza.asgn.saket.ui.pizza.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.ActivityMainBinding
import pizza.asgn.saket.ui.pizza.viewmodel.MainViewModel
import pizza.asgn.saket.utils.Resource
import pizza.asgn.saket.utils.Status
import pizza.asgn.saket.utils.showToast

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            mainViewModel.getPizzaInfo()
        }
        mainViewModel.pizzaInfo.observe(this, Observer {
            when(it.status){
                Status.LOADING -> {
                    Log.d("apiStatus","loading")
                }
                Status.SUCCESS -> {
                    it.data?.let { mainInfo ->
                        Log.d("apiStatus","success: ${Gson().toJson(mainInfo)}")
                    }
                }
                Status.ERROR -> {
                    Log.d("apiStatus","failed, ${it.message}")
                }
            }
        })
    }
}