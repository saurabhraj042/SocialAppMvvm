package net.raj.testsocialapp.ui.signIn
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import net.raj.testsocialapp.R
import net.raj.testsocialapp.databinding.ActivitySignInBinding
import net.raj.testsocialapp.ui.home.HomeActivity

class SignInActivity : AppCompatActivity() {
    private val RC_SIGN_IN: Int = 123
    private val TAG = "SignInActivity"

    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding : ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(SignInViewModel::class.java)

        val  gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        viewModel.firebaseUser.observe(this,{
            updateUI(viewModel.firebaseUser.value)
        })

        binding.signInButton.setOnClickListener {
            signIn()
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = viewModel.auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode==RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>?) {
        try {
            val account =
                completedTask?.getResult(ApiException::class.java)
            if (account != null) {
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            }

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        hideLoadingUI()
        viewModel.firebaseAuthWithGoogleHelper(idToken)
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {
            viewModel.addUser(firebaseUser)
            val mainActivityIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        } else {
            binding.signInButton.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun hideLoadingUI(){
        binding.signInButton.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }
}