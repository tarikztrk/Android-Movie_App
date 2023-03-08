package com.example.finalproje.view

import android.R
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.finalproje.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val imgUri: Uri = Uri.parse( DetailFragmentArgs.fromBundle(it).movie.Poster)

            Picasso.with(requireContext()).load(imgUri).into(binding.moviePoster);

            //binding.moviePoster.setImageBitmap(image)
            binding.movieName.text = DetailFragmentArgs.fromBundle(it).movie.Title
            binding.actors.text= DetailFragmentArgs.fromBundle(it).movie.Type
            binding.imdbRate.text= DetailFragmentArgs.fromBundle(it).movie.imdbID
            binding.releaseYear.text= DetailFragmentArgs.fromBundle(it).movie.Year

        }
    }


}