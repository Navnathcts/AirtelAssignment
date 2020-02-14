package navanth.com.pixabayimageloaderapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import navanth.com.pixabayimageloaderapp.R
import navanth.com.pixabayimageloaderapp.ui.adapter.PixabayImageListAdapter.PixabayImageViewHolder
import navanth.com.pixabayimageloaderapp.databinding.PixabayImageItemBinding
import navanth.com.pixabayimageloaderapp.model.PixabayImage
import navanth.com.pixabayimageloaderapp.viewmodel.PixabayImageViewModel

class PixabayImageListAdapter(private val listener: ShowLargeImageView) :
    RecyclerView.Adapter<PixabayImageViewHolder>() {
    private var imageSearchResultList: MutableList<PixabayImage>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PixabayImageViewHolder {
        return PixabayImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pixabay_image_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PixabayImageViewHolder,
        position: Int
    ) {
        holder.pixabayImageItemBinding.setViewmodel(
            imageSearchResultList?.get(position)?.let {
                PixabayImageViewModel(
                    it
                )
            }
        )
        holder.itemView.setOnClickListener { listener.showLargeImage(position) }
    }

    override fun getItemCount(): Int {
        return imageSearchResultList?.size ?: 0
    }

    fun setImageResultList(imageSearchResultList: MutableList<PixabayImage>?) {
        this.imageSearchResultList = imageSearchResultList
        notifyDataSetChanged()
    }

    inner class PixabayImageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val pixabayImageItemBinding: PixabayImageItemBinding = PixabayImageItemBinding.bind(v)
    }

    interface ShowLargeImageView {
        fun showLargeImage(position: Int)
    }
}