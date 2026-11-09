package com.ejemplo.appfutbolera.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.appfutbolera.R
import com.ejemplo.appfutbolera.network.Player
class PlayerAdapter(
    private val players: List<Player>,
    private val onPlayerClick: (Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvPlayerName)
        val tvPosition: TextView = view.findViewById(R.id.tvPlayerPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.tvName.text = player.strPlayer
        holder.tvPosition.text = player.strPosition ?: "Sin posición"
        holder.itemView.setOnClickListener { onPlayerClick(player) }
    }

    override fun getItemCount(): Int = players.size
}