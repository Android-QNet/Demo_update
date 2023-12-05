package com.demo.android.presentation.home



import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.demo.android.R
import com.demo.android.data.models.Person
import com.demo.android.databinding.ActivityHomeBinding
import com.demo.android.presentation.core.BaseActivity
import com.demo.android.presentation.home.adapter.PersonListAdapter
import com.demo.android.presentation.home.listener.PhotoItemClickListener
import com.demo.android.presentation.utility.EndlessPaginationScrollListener
import com.demo.android.presentation.utility.isNetworkAvailable
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), PhotoItemClickListener {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getBaseViewModel() = homeViewModel

    private lateinit var binding: ActivityHomeBinding

    private lateinit var adapter: PersonListAdapter

    private var page: Int = 1
    private var hasMore: Boolean? = false
    private var isLoading: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        setupToolbar(binding.llToolbarMain.toolbar, getString(R.string.app_name), false, Color.BLACK)

        attachObserver()
        setupAdapter()
        getPersonList()

        binding.swipe.setOnRefreshListener {
            getPersonList()
        }

        //check for app update on the google play
        checkForAppUpdate()
    }

    private fun attachObserver() {

        homeViewModel.personLocalListRSLiveData.observe(this, Observer {
            it?.apply {
                updateAdapter(this)
                hideSwipeProgressBar()
                isLoading = false
            }
        })
    }

    private fun getPersonList() {
        if (isNetworkAvailable()) {
            isLoading = true
            if (page == 1) {
                showSwipeProgressBar()
            }
        } else {
            homeViewModel.getPersonListLocal()
        }
    }

    private fun setupAdapter() {
        adapter = PersonListAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : EndlessPaginationScrollListener() {
            override fun requestNewPage() {
                super.requestNewPage()
                if (isLoading == false && hasMore!!) {
                    getPersonList()
                }
            }
        })
    }

    private fun updateAdapter(list: List<Person>) {
        if (page == 1) {
            adapter.setData(list)
        } else {
            adapter.addData(list)
        }
    }

    private fun showSwipeProgressBar() {
        if (binding.swipe.isRefreshing == false) {
            binding.swipe.isRefreshing = true
        }
    }

    private fun hideSwipeProgressBar() {
        if (binding.swipe.isRefreshing == true) {
            binding.swipe.isRefreshing = false
        }
    }

    override fun onPhotoItemClick(person: Person) {

    }
}
