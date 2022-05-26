package waslim.binar.andlima.classdiscussions.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import waslim.binar.andlima.classdiscussions.R
import waslim.binar.andlima.classdiscussions.datastore.UserManager

@SuppressLint("SetTextI18n")
class ProfileFragment : Fragment() {
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireContext())

        userManager.image.asLiveData().observe(this){
            Glide.with(this).load(it).into(imageProfile)
        }

        userManager.username.asLiveData().observe(viewLifecycleOwner) {
            username.text = "Welcome, $it"
        }

        userManager.name.asLiveData().observe(viewLifecycleOwner) {
            name.text = "Welcome, $it"
        }

        userManager.umur.asLiveData().observe(viewLifecycleOwner) {
            umur.text = "Welcome, $it"
        }

        userManager.address.asLiveData().observe(viewLifecycleOwner) {
            address.text = "Welcome, $it"
        }

        btn_logout.setOnClickListener {
            val al = AlertDialog.Builder(requireContext())
            al.setTitle("Konfirmasi Logout")
            al.setMessage("Anda Yakin Ingin Logout ?")
            al.setCancelable(false)
            val algt = al.create()

            al.setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                GlobalScope.launch {
                    userManager.logout()
                }
                Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_LONG).show()
            } .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                algt.dismiss()
                Toast.makeText(requireContext(), "Batal Logout", Toast.LENGTH_LONG).show()
            }

            al.show()
        }
    }


}