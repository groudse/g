package com.example.finalapp


import android.graphics.ColorSpace.get
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapp.databinding.RowGameBinding
import com.squareup.picasso.Picasso



class GameListAdaptater: ListAdapter<GameBean, GameListAdaptater.ViewHolder>(Comparator()) {

    class ViewHolder(val bind: RowGameBinding) : RecyclerView.ViewHolder(bind.root)

    class Comparator : DiffUtil.ItemCallback<GameBean>() {
        override fun areItemsTheSame(oldItem: GameBean, newItem: GameBean) = oldItem === newItem

        override fun areContentsTheSame(oldItem: GameBean, newItem: GameBean) =
            oldItem.gameID == newItem.gameID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       return ViewHolder(RowGameBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        Picasso.get().load(data.thumb).into(holder.bind.ivTemp)
        holder.bind.Nom.text = data.external
        holder.bind.Prix.text = data.cheapest
        holder.bind.Nom.setOnClickListener {
            holder.bind.webview.loadUrl("https://store.steampowered.com/app/${data.steamAppID}/")
        }
    }


}
