package com.husenansari.offlinefirst.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.husenansari.offlinefirst.R
import com.husenansari.offlinefirst.adapter.UserAdapterMvvmPagination
import com.husenansari.offlinefirst.adapter.UserAdapterSimplePagination
import com.husenansari.offlinefirst.db.UserDatabase
import com.husenansari.offlinefirst.model.Item
import com.husenansari.offlinefirst.repository.GetUserRepository
import com.husenansari.offlinefirst.util.HelperFunction
import com.husenansari.offlinefirst.util.PaginationScrollListener
import com.husenansari.offlinefirst.util.Resource
import com.husenansari.offlinefirst.viewmodel.UserViewModel
import com.husenansari.offlinefirst.viewmodel.UserViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: UserViewModel
    lateinit var userAdapter: UserAdapterMvvmPagination
    private var loader: Dialog? = null
    private var currentPage = 1
    private var TOTAL_PAGES : Int = 0
    var linearLayoutManager: LinearLayoutManager? = null
    private var isLoading = false
    private var isLastPage = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loader = HelperFunction.getLoader(this)

        val getUserRepository = GetUserRepository(UserDatabase(this))
        val viewModelProviderFactory = UserViewModelProviderFactory(
            application,
            getUserRepository
        )
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)


        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvUser!!.layoutManager = linearLayoutManager
        userAdapter = UserAdapterMvvmPagination(this)
        rvUser.adapter = userAdapter

        setupObservers(currentPage)

         rvUser!!.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager!!) {
            override fun loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                setupObservers(currentPage)
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

    }


    private fun setupObservers(currentPage: Int) {

        viewModel.getAllUser(currentPage,20, "desc", "reputation", "stackoverflow")

        viewModel.allUserMutableLiveData.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    loader!!.dismiss()
                    isLoading = false
                    response.data?.let { userResponse ->
                        TOTAL_PAGES = userResponse.quota_max
                        if (currentPage <= TOTAL_PAGES) {

                           /* // save data to room database
                            viewModel.saveAllUser(userResponse.items)*/


                            // set data direct to adapter
                            userAdapter.differ.submitList(userResponse.items.toList())

                        } else {
                            isLastPage = true
                       }
                    }
                }
                is Resource.Error -> {
                    loader!!.dismiss()
                    isLoading = false
                    response.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    loader!!.show()
                    isLoading = true
                }
            }
        })

       /* // fetch data from room database  and set to adapter
        viewModel.getAllResponse().observe(this, Observer { userList ->
            //userAdapter.setUserData(userList as ArrayList<Item>)
            userAdapter.differ.submitList(userList.toList())
        })*/
    }

}