package com.suharjin.githubuserapp2.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.suharjin.githubuserapp2.R
import com.suharjin.githubuserapp2.model.UserData
import com.suharjin.githubuserapp2.viewModel.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setData()
        viewPagerConfig()
    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        dataUser.name?.let { setActionBarTitle(it) }
        detail_name.text = dataUser.name
        detail_username.text = dataUser.username
        detail_company.text = getString(R.string.company, dataUser.company)
        detail_location.text = getString(R.string.location, dataUser.location)
        detail_repository.text = getString(R.string.repository, dataUser.repository)
        Glide.with(this)
            .load(dataUser.avatar)
            .into(detail_avatar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}