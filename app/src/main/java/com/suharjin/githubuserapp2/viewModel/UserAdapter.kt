package com.suharjin.githubuserapp2.viewModel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.suharjin.githubuserapp2.R
import com.suharjin.githubuserapp2.model.UserData
import com.suharjin.githubuserapp2.view.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_item.view.*
import java.util.*
import kotlin.collections.ArrayList

var userFilterList = ArrayList<UserData>()
lateinit var mcontex: Context

class UserAdapter(private var listData: ArrayList<UserData>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>(), Filterable {
    init {
        userFilterList = listData
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_item, viewGroup, false)
        val sch = ListViewHolder(view)
        mcontex = viewGroup.context
        return sch
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = userFilterList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)
        holder.txtUsername.text = data.username
        holder.txtName.text = data.name
        holder.txtCompany.text = data.company
        holder.txtLocation.text = data.location
        holder.itemView.setOnClickListener {
            val dataUser = UserData(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val intentDetail = Intent(mcontex, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA, dataUser)
            mcontex.startActivity(intentDetail)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(dataUsers: UserData)
    }

    override fun getItemCount(): Int = userFilterList.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: CircleImageView = itemView.img_avatar
        var txtUsername: TextView = itemView.txt_username
        var txtName: TextView = itemView.txt_name
        var txtCompany: TextView = itemView.txt_company
        var txtLocation: TextView = itemView.txt_location
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                userFilterList = if (charSearch.isEmpty()) {
                    listData
                } else {
                    val resultList = ArrayList<UserData>()
                    for (row in userFilterList) {
                        if ((row.username.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)))
                        ) {
                            resultList.add(
                                UserData(
                                    row.username,
                                    row.name,
                                    row.avatar,
                                    row.company,
                                    row.location,
                                    row.repository,
                                    row.followers,
                                    row.following
                                )
                            )
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = userFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                userFilterList = results.values as ArrayList<UserData>
                notifyDataSetChanged()
            }
        }
    }
}