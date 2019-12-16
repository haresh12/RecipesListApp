package com.broadcast.recipeslistapp

 import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.broadcast.recipeslistapp.adapter.RecipeRecyclerAdapter
 import com.broadcast.recipeslistapp.com.broadcast.recipeslistapp.adapter.OnRecipeListener
 import kotlinx.android.synthetic.main.activity_recipe_list.*
import com.broadcast.recipeslistapp.models.Recipe
import com.broadcast.recipeslistapp.util.VerticalSpacingItemDecorator
import com.broadcast.recipeslistapp.viewmodels.RecipeListViewModel
import androidx.recyclerview.widget.RecyclerView.OnScrollListener as OnScrollListener1

open class RecipeListActivity : BaseActivity(),
    OnRecipeListener {

    private lateinit var layoutManager: LinearLayoutManager
    private val TAG = "RecipeListActivity"
    private lateinit var mRecipeListViewModel: RecipeListViewModel
    private var mAdapter: RecipeRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        setSupportActionBar(toolbar)
        mRecipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        initRecyclerView()
        subscribeObservers()
        initSearchView()

        if (!mRecipeListViewModel.isViewingRecipes()) {
            displaySearchCategories()
        }

    }

    private fun initRecyclerView() {
        mAdapter = RecipeRecyclerAdapter(this)
        val itemDecorator = VerticalSpacingItemDecorator(30);
        recipe_list.addItemDecoration(itemDecorator);
        recipe_list.adapter = mAdapter
        layoutManager = LinearLayoutManager(this);
        recipe_list.layoutManager = layoutManager

        recipe_list.addOnScrollListener(object : OnScrollListener1() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    mRecipeListViewModel.searchNextPage()
                }

            }

        })

    }


    //Observe the recipe list changes from viewModel
    private fun subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, Observer<MutableList<Recipe>> {
            if (mRecipeListViewModel.isViewingRecipes()) {
                mAdapter?.setRecipes(it)


                mRecipeListViewModel.setPerformingQuery(false)
            }
        })
        mRecipeListViewModel.isQueryExhausted().observe(this,
            Observer<Boolean> { isExhausted ->
                if (isExhausted!!) {
                    Log.e("RecipeListActivity", "onChanged: the query is exhausted...")
                    mAdapter?.setQueryExhausted();
                }
            })


    }

    private fun initSearchView() {
        search_view.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {
                mAdapter?.displayLoading()
                mRecipeListViewModel.searchRecipeApi(newText!!, 1)
                search_view.clearFocus()
                return false
            }

            override fun onQueryTextChange(quary: String?): Boolean {
                return false
            }

        })


    }


    override fun onRecipeClick(position: Int) {
        startActivity(
            Intent(this, RecipeActivity::class.java)
                .putExtra("recipe", mAdapter?.getRecipe(position))
        )
    }

    override fun onCategoryClick(category: String) {
        mAdapter?.displayLoading()
        mRecipeListViewModel.searchRecipeApi(category, 1)
        search_view.clearFocus()

    }

    private fun displaySearchCategories() {
        Log.d(TAG, "displaySearchCategories: called.")
        mRecipeListViewModel.setViewingRecipe(false)
        mAdapter!!.displaySearchCategories();
    }

    override fun onBackPressed() {

        if (mRecipeListViewModel.isViewingRecipes() || mRecipeListViewModel.onBackPressed()) {
            mRecipeListViewModel.setViewingRecipe(false)
            displaySearchCategories()
        } else {
            super.onBackPressed()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_categories) {
            displaySearchCategories()
        }
        return super.onOptionsItemSelected(item)
    }
}
