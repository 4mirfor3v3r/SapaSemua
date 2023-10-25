package amirlabs.sapasemua.ui.menu.module

import amirlabs.sapasemua.R
import amirlabs.sapasemua.data.model.Module
import amirlabs.sapasemua.databinding.ItemModuleBinding
import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.nio.charset.StandardCharsets




class ModuleAdapter(private val onItemClick: (Module) -> Unit) :
    RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {
    val listData = ArrayList<Module>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleAdapter.ViewHolder {
        val view: ItemModuleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_module,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModuleAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemCount() = listData.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Module>) {
        this.listData.clear()
        this.listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemModuleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Module, position: Int) {
            with(binding) {
                tvModuleTitle.text = data.name
                tvModuleDesc.text = data.description
                tvModuleLevel.text = when(data.level) {
                    1 -> "Basic"
                    2 -> "Intermediate"
                    3 -> "Advanced"
                    else -> "Beginner"
                }
                val image: ByteArray = Base64.decode(data.image, Base64.DEFAULT)
                Glide.with(binding.root.context)
                    .asBitmap()
                    .load(image)
                    .into(ivModule)

                binding.root.setOnClickListener { onItemClick(data) }
            }
        }
    }
}