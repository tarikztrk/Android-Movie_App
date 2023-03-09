package com.example.finalproje.view

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproje.databinding.RecyclerRowBinding
import com.example.finalproje.model.MovieModel
import com.squareup.picasso.Picasso
import java.security.AccessController.getContext

class RecyclerViewAdapter(private val movieList : ArrayList<MovieModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: MovieModel)
    }
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class RowHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return movieList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(movieList.get(position))
            val action = ListFragmentDirections.actionListFragment2ToDetailFragment2(movieList?.get(position))
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.movieNameText.text = movieList.get(position).Title
        holder.binding.movieYearText.text = movieList.get(position).Year
        val imgUri: Uri = Uri.parse(movieList.get(position).Poster)

        Picasso.get().load(imgUri).into( holder.binding.imageView)
    }


}