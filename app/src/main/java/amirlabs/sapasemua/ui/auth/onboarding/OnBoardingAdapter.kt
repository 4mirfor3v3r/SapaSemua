package amirlabs.sapasemua.ui.auth.onboarding

import amirlabs.sapasemua.R
import amirlabs.sapasemua.databinding.ItemOnboardingBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


internal class OnBoardingAdapter(private val onItemClick:(OnBoardingModel) -> Unit) : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {
    private val items = arrayOf(
        OnBoardingModel(R.drawable.img_walkthrough_1, R.string.onboarding_title_1, R.string.onboarding_desc_1),
        OnBoardingModel(R.drawable.img_walkthrough_2, R.string.onboarding_title_2, R.string.onboarding_desc_2),
        OnBoardingModel(R.drawable.img_walkthrough_3, R.string.onboarding_title_3, R.string.onboarding_desc_3)
    )

    // This method returns our layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemOnboardingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_onboarding,parent,false)
        return ViewHolder(binding)
    }

    // This method binds the screen with the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // This Method returns the size of the Array
    override fun getItemCount(): Int {
        return items.size
    }

    // The ViewHolder class holds the view
    inner class ViewHolder(private val binding: ItemOnboardingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OnBoardingModel){
            Glide.with(binding.ivIllustration.context)
                .load(data.image)
                .into(binding.ivIllustration)
            binding.tvTitle.text =binding.root.context.getString(data.title)
            binding.tvDesc.text = binding.root.context.getString(data.desc)
            binding.root.setOnClickListener{
                onItemClick(data)
            }
        }
    }
}
class OnBoardingModel (
    var image: Int,
    var title: Int,
    var desc: Int
)