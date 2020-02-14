package navanth.com.pixabayimageloaderapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import navanth.com.pixabayimageloaderapp.R
import navanth.com.pixabayimageloaderapp.databinding.PixabayZoomedImageItemBinding
import navanth.com.pixabayimageloaderapp.model.PixabayImage
import navanth.com.pixabayimageloaderapp.viewmodel.PixabayImageViewModel

class PixabayZoomedImageListAdapter(var imageSearchResultList: MutableList<PixabayImage>? = null) : RecyclerView.Adapter<PixabayZoomedImageListAdapter.PixabayZoomedImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PixabayZoomedImageViewHolder {
        return PixabayZoomedImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pixabay_zoomed_image_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PixabayZoomedImageViewHolder,
        position: Int
    ) {
        holder.pixabayZoomedImageItemBinding.viewmodel = imageSearchResultList?.get(position)?.let {
            PixabayImageViewModel(
                it
            )
        }
    }

    override fun getItemCount(): Int {
        return imageSearchResultList?.size ?: 0
    }

    fun setImageResultList(imageSearchResultList: MutableList<PixabayImage>?) {
        this.imageSearchResultList = imageSearchResultList
        notifyDataSetChanged()
    }

    inner class PixabayZoomedImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val pixabayZoomedImageItemBinding: PixabayZoomedImageItemBinding = PixabayZoomedImageItemBinding.bind(v)
    }

}