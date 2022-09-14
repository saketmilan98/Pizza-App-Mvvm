package pizza.asgn.saket.ui.pizza.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pizza.asgn.saket.R
import pizza.asgn.saket.databinding.OptionsRvLayoutBinding
import pizza.asgn.saket.ui.pizza.model.Crusts

class CrustsDataAdapter(val context : Context, val onItemClicked: (Crusts, Int) -> Unit) : RecyclerView.Adapter<CrustsDataAdapter.CrustsViewHolder>() {

    private var crustDataa: ArrayList<Crusts>? = null
    var selectedCrustId = -1
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CrustsViewHolder {
        val optionsRvLayoutBinding: OptionsRvLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.options_rv_layout, viewGroup, false
        )
        return CrustsViewHolder(optionsRvLayoutBinding)
    }

    override fun onBindViewHolder(crustsViewHolder: CrustsViewHolder, i: Int) {
        val currentItem: Crusts = crustDataa!![i]
        crustsViewHolder.crustListItemBinding.crustData = currentItem
        crustsViewHolder.crustListItemBinding.root.setOnClickListener {
            onItemClicked(currentItem, i)
            selectedCrustId = currentItem.id?:-1
            notifyDataSetChanged()
        }
        if(selectedCrustId == currentItem.id){
            crustsViewHolder.crustListItemBinding.checkTv.isVisible = true
            crustsViewHolder.crustListItemBinding.backgroundCl.background = ContextCompat.getDrawable(context, R.drawable.red_border_light_bg_round)
        }
        else {
            crustsViewHolder.crustListItemBinding.checkTv.isVisible = false
            crustsViewHolder.crustListItemBinding.backgroundCl.background = ContextCompat.getDrawable(context, R.drawable.black_border_white_bg_round)
        }
    }

    override fun getItemCount(): Int {
        return if (crustDataa != null) {
            crustDataa!!.size
        } else {
            0
        }
    }

    fun setCrustList(crusts: ArrayList<Crusts>?) {
        this.crustDataa = crusts
        notifyDataSetChanged()
    }

    inner class CrustsViewHolder(optionsRvLayoutBinding: OptionsRvLayoutBinding) :
        RecyclerView.ViewHolder(optionsRvLayoutBinding.root) {
        val crustListItemBinding: OptionsRvLayoutBinding

        init {
            crustListItemBinding = optionsRvLayoutBinding
        }
    }
}