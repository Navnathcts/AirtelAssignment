package navanth.com.pixabayimageloaderapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.fragment_pixabay_zoomed_image_view.*
import navanth.com.pixabayimageloaderapp.POSITION
import navanth.com.pixabayimageloaderapp.R
import navanth.com.pixabayimageloaderapp.SEARC_IMAGE_LIST
import navanth.com.pixabayimageloaderapp.model.PixabayImageList
import navanth.com.pixabayimageloaderapp.ui.adapter.PixabayZoomedImageListAdapter

class PixabayZoomedImageViewFragment : Fragment() {
    private lateinit var mPixabayPagerAdapter: PixabayZoomedImageListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pixabay_zoomed_image_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPixabayPagerAdapter =
            PixabayZoomedImageListAdapter(
                (arguments?.getSerializable(SEARC_IMAGE_LIST) as? PixabayImageList)?.imageSearchResultList
            )
        val snapHelper = PagerSnapHelper()
        rvImageList?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            onFlingListener = null
            adapter = mPixabayPagerAdapter
            snapHelper.attachToRecyclerView(this)
            arguments?.getInt(POSITION)?.let { scrollToPosition(it) }
        }
    }
}