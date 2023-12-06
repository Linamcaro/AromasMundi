package com.example.aromasmundi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.aromasmundi.R
import com.example.aromasmundi.adapters.PagerAdapter
import com.example.aromasmundi.data.database.entities.FavoritesEntity
import com.example.aromasmundi.databinding.ActivityDetailsBinding
import com.example.aromasmundi.ui.fragments.ingredients.IngredientsDetailFragment
import com.example.aromasmundi.ui.fragments.instructions.InstructionsDetailFragment
import com.example.aromasmundi.ui.fragments.overview.OverviewDetailFragment
import com.example.aromasmundi.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailActivityBinding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailActivityBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(detailActivityBinding.root)


        setSupportActionBar(detailActivityBinding.detailsToolBar)
        detailActivityBinding.detailsToolBar.setTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewDetailFragment())
        fragments.add(IngredientsDetailFragment())
        fragments.add(InstructionsDetailFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        //Pass the result data to each fragment
        val pageAdapter = PagerAdapter(resultBundle, fragments, this)
        detailActivityBinding.detailsViewPager.isUserInputEnabled = false
        detailActivityBinding.detailsViewPager.apply {
            adapter = pageAdapter
        }

        //Setup the title for each tab
        TabLayoutMediator(
            detailActivityBinding.detailsTabLayout,
            detailActivityBinding.detailsViewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favoritesMenu)
        return true
    }

    //Set the details options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favoritesMenu) {
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0,
            args.result
        )

        mainViewModel.insetFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved")

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(detailActivityBinding.detailsLayout, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }


}