package waslim.binar.andlima.classdiscussions.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import waslim.binar.andlima.classdiscussions.R
import waslim.binar.andlima.classdiscussions.datastore.UserManager

@SuppressLint("SetTextI18n")
class HomeFragment : Fragment() {
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataUsername()
        goToProfile()
    }


    private fun getDataUsername(){
        userManager = UserManager(requireContext())

        userManager.username.asLiveData().observe(viewLifecycleOwner){
            welcome.text = "Welcome, $it"
        }
    }

    private fun goToProfile(){
        btn_profil.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

}