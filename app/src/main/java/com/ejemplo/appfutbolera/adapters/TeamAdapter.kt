package com.ejemplo.appfutbolera.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ejemplo.appfutbolera.R
import com.ejemplo.appfutbolera.network.Team
class TeamAdapter(
    private val teams: List<Team>,
    private val onTeamClick: (Team) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBadge: ImageView = view.findViewById(R.id.ivTeamBadge)
        val tvName: TextView = view.findViewById(R.id.tvTeamName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        holder.tvName.text = team.strTeam
        Glide.with(holder.itemView.context)
            .load(team.strBadge)
            .into(holder.ivBadge)
        holder.itemView.setOnClickListener { onTeamClick(team) }
    }

    override fun getItemCount(): Int = teams.size
}