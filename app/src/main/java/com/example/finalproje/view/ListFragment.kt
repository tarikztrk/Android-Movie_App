package com.example.finalproje.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.koinretrofit.viewmodel.MovieViewModel
import com.example.finalproje.databinding.FragmentListBinding
import com.example.finalproje.model.MovieModel
import com.example.finalproje.view.RecyclerViewAdapter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class ListFragment : Fragment(), RecyclerViewAdapter.Listener, AndroidScopeComponent {

    private var _binding: FragmentListBinding? = null
    private val binding get()= _binding!!
    //private lateinit var viewModel : CryptoViewModel
    private var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)

    //with di inject vm
    private val viewModel by viewModel<MovieViewModel>()

    //if i needed to inject any class here it would have gone like this
    //this is not lazy injection, this will be created once fragment is created
    //private val api = get<CryptoAPI>()

    //this is lazy injection, this is only injected when it is first used time
    //private val apilazy by inject<CryptoAPI>()


    //if we want to inject scoped providing modules, it is not different
    // we need to define the scope by adding AndroidScopeComponent to Fragment implementation
    //then we can override the scope
    // if we have more than two same objects such as strings we can use named parameter
    //change hello to hi and you will see other one will be injected
    override val scope: Scope by fragmentScope()
    private val hello by inject<String>(qualifier = named("hello"))

/*
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }
 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        //viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        viewModel.getDataFromAPI()
        //loadData()

        observeLiveData()

        //println(hello)
    }
/*
    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()
            println("get data called")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    println("successful")
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it, this@ListFragment)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }
                    }
                }
            }
        }
    }

 */

    fun observeLiveData() {
        viewModel.movieList.observe(viewLifecycleOwner, Observer {movies ->

            movies?.let {
                binding.recyclerView.visibility = View.VISIBLE
                cryptoAdapter = RecyclerViewAdapter(ArrayList(movies.data?.Search ?: arrayListOf()),this@ListFragment)
                binding.recyclerView.adapter = cryptoAdapter
            }

        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it.data == true) {
                    binding.movieErrorText.visibility = View.VISIBLE
                } else {
                    binding.movieErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it.data == true) {
                    binding.movieProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.movieErrorText.visibility = View.GONE
                } else {
                    binding.movieProgressBar.visibility = View.GONE
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //job?.cancel()
        _binding = null
    }

    override fun onItemClick(movieModel: MovieModel) {
        Toast.makeText(requireContext(),"Clicked on: ${movieModel.Title}", Toast.LENGTH_SHORT).show()


    }
}