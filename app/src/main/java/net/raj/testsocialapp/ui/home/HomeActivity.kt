package net.raj.testsocialapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import net.raj.testsocialapp.IPostAdapter
import net.raj.testsocialapp.PostAdapter
import net.raj.testsocialapp.R
import net.raj.testsocialapp.databinding.ActivityHomeBinding
import net.raj.testsocialapp.models.Post
import net.raj.testsocialapp.ui.signIn.SignInActivity

class HomeActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var adapter: PostAdapter
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = HomeActivityViewModel()

        binding.fab.setOnClickListener {
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        viewModel.updateUtilities()
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(viewModel.query, Post::class.java).build()
        adapter = PostAdapter(recyclerViewOptions,this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

     override fun onLikeClicked(postId: String) {
         viewModel.updatePostLikes(postId)
    }
}