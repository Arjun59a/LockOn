package com.example.lockon

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppInfoAdapter(val applist: Array<Applist>,private var sharedPreferences: SharedPreferences) :
    RecyclerView.Adapter<AppInfoAdapter.ApplistViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApplistViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)

        return ApplistViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ApplistViewHolder,
        position: Int
    ) {

        val app = applist[position]

        holder.tvAppName.text = app.appname
        holder.imgappicon.setImageDrawable(app.icon)

        holder.checkvalue.setOnCheckedChangeListener(null)
        holder.checkvalue.isChecked = app.lockset

        holder.checkvalue.setOnCheckedChangeListener { _, is_checked ->
            app.lockset = is_checked

            sharedPreferences.edit()
                .putBoolean(app.pck, is_checked)
                .commit()
        }



    }

    override fun getItemCount(): Int {
        return applist.size
    }

    class ApplistViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var tvAppName: TextView =
            itemView.findViewById(R.id.app_name)

        var imgappicon : ImageView =
            itemView.findViewById(R.id.icon_of_app)





        var checkvalue : CheckBox = itemView.findViewById(R.id.app_lock)





    }
}