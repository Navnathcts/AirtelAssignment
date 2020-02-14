package navanth.com.pixabayimageloaderapp.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pixabay_image_list_view.*
import navanth.com.pixabayimageloaderapp.*
import navanth.com.pixabayimageloaderapp.api_service.PixabayApiService
import navanth.com.pixabayimageloaderapp.model.PixabayImageList
import navanth.com.pixabayimageloaderapp.repository.SearchRepository
import navanth.com.pixabayimageloaderapp.ui.adapter.PixabayImageListAdapter

class PixabayImageListFragment : Fragment(), PixabayImageListAdapter.ShowLargeImageView {
    private var mAdapter: PixabayImageListAdapter? = null
    private var searchMenuItem: MenuItem? = null
    private var imageSearchResultList: PixabayImageList? = null
    private var mNavigationController: NavController? = null
    private var isLargeImageSeen: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pixabay_image_list_view, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        this.mAdapter =
            PixabayImageListAdapter(
                this
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavigationController =
            Navigation.findNavController(
                requireActivity(),
                R.id.my_nav_host_fragment
            )
        //number colomns to be shown can changed based on requirement
        rvImageList?.apply {
            layoutManager = GridLayoutManager(activity, NUMBER_COLOMN)
            setOnFlingListener(null)
            adapter = mAdapter
            hasFixedSize()
        }
        //by default loading images of flower
        if (!isLargeImageSeen) {
            searchPixabayImages("Nature")
        } else {
            showLoader(loaderVisibility = View.GONE)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        searchMenuItem = menu.findItem(R.id.action_search)
        (searchMenuItem?.actionView as? SearchView)?.setOnQueryTextListener(searchListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val searchListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMenuItem?.collapseActionView()
                resetImageList()
                searchPixabayImages(imageSearchKeyword = query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        }

    /**
     * This will search images from Pixabay.
     * NUMBER_OF_IMAGES can changed runtime with remote config
     */
    private fun searchPixabayImages(imageSearchKeyword: String) {
        showLoader()
        SearchRepository(PixabayApiService.Factory.create()).searchUsers(
            getString(R.string.API_KEY_PIXABAY),
            imageSearchKeyword,
            1,
            NUMBER_OF_IMAGES
        )?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
            ?.subscribe({ result ->
                imageSearchResultList = result
                showLoader(loaderVisibility = View.GONE)
                result?.imageSearchResultList?.let {
                    if (it.size > 0) {
                        mAdapter?.setImageResultList(imageSearchResultList = it)
                    } else {
                        showLoader(
                            loaderVisibility = View.GONE,
                            noResultTextVisibility = View.VISIBLE,
                            searchKeyword = imageSearchKeyword
                        )
                    }
                }

            }, { error ->
                showLoader(loaderVisibility = View.GONE)
                error.printStackTrace()
            })
    }

    /**
     * This will reset the search result list.
     */
    private fun resetImageList() {
        mAdapter?.setImageResultList(null)
    }

    /**
     * This will hide or show loader/no results view
     */
    private fun showLoader(
        loaderVisibility: Int = View.VISIBLE,
        noResultTextVisibility: Int = View.GONE,
        searchKeyword: String = ""
    ) {
        txtNoResult?.apply {
            text = String.format(getString(R.string.loading_images_of), searchKeyword)
            visibility = noResultTextVisibility
        }
        imgNoResult.visibility = noResultTextVisibility
        pbLoader.visibility = loaderVisibility
    }

    override fun showLargeImage(position: Int) {
        isLargeImageSeen = true
        Bundle().apply {
            this.putInt(POSITION, position)
            this.putSerializable(SEARC_IMAGE_LIST, imageSearchResultList)
        }.apply {
            mNavigationController?.navigate(
                R.id.action_imageListFragment_to_imageLargeViewFragment,
                this
            )
        }
    }
}