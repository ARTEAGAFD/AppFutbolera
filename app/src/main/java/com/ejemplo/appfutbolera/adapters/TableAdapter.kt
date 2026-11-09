package com.ejemplo.appfutbolera.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ejemplo.appfutbolera.R
import com.ejemplo.appfutbolera.network.TableItem
class TableAdapter(
    private val items: List<TableItem>
) : RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

    class TableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRank: TextView = view.findViewById(R.id.tvRank)
        val tvTeamName: TextView = view.findViewById(R.id.tvTeamName)
        val tvPlayed: TextView = view.findViewById(R.id.tvPlayed)
        val tvPoints: TextView = view.findViewById(R.id.tvPoints)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_table, parent, false)
        return TableViewHolder(view)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val item = items[position]
        holder.tvRank.text = item.intRank ?: "-"
        holder.tvTeamName.text = item.strTeam ?: "-"
        holder.tvPlayed.text = item.intPlayed ?: "0"
        holder.tvPoints.text = item.intPoints ?: "0"
    }

    override fun getItemCount(): Int = items.size
}