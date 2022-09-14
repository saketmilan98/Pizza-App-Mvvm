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
import pizza.asgn.saket.ui.pizza.model.Sizes

class SizeDataAdapter(val context : Context, val onItemClicked: (Sizes, Int) -> Unit) : RecyclerView.Adapter<SizeDataAdapter.SizeViewHolder>() {

    private var sizeDataa: ArrayList<Sizes>? = null
    var selectedSizeId = -1
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SizeViewHolder {
        val optionsRvLayoutBinding: OptionsRvLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.options_rv_layout, viewGroup, false
        )
        return SizeViewHolder(optionsRvLayoutBinding)
    }

    override fun onBindViewHolder(sizeViewHolder: SizeViewHolder, i: Int) {
        val currentItem: Sizes = sizeDataa!![i]
        sizeViewHolder.crustListItemBinding.crustData = Crusts(id = currentItem.id, name = currentItem.name, price = currentItem.price)
        sizeViewHolder.crustListItemBinding.root.setOnClickListener {
            onItemClicked(currentItem, i)
            selectedSizeId = currentItem.id?:-1
            notifyDataSetChanged()
        }
        if(selectedSizeId == currentItem.id){
            sizeViewHolder.crustListItemBinding.checkTv.isVisible = true
            sizeViewHolder.crustListItemBinding.backgroundCl.background = ContextCompat.getDrawable(context, R.drawable.red_border_light_bg_round)
        }
        else {
            sizeViewHolder.crustListItemBinding.checkTv.isVisible = false
            sizeViewHolder.crustListItemBinding.backgroundCl.background = ContextCompat.getDrawable(context, R.drawable.black_border_white_bg_round)
        }
    }

    override fun getItemCount(): Int {
        return if (sizeDataa != null) {
            sizeDataa!!.size
        } else {
            0
        }
    }

    fun setSizeList(sizes: ArrayList<Sizes>?) {
        this.sizeDataa = sizes
        notifyDataSetChanged()
    }

    inner class SizeViewHolder(optionsRvLayoutBinding: OptionsRvLayoutBinding) :
        RecyclerView.ViewHolder(optionsRvLayoutBinding.root) {
        val crustListItemBinding: OptionsRvLayoutBinding

        init {
            crustListItemBinding = optionsRvLayoutBinding
        }
    }
}