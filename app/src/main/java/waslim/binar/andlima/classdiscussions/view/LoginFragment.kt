package waslim.binar.andlima.classdiscussions.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import waslim.binar.andlima.classdiscussions.R
import waslim.binar.andlima.classdiscussions.datastore.UserManager
import waslim.binar.andlima.classdiscussions.model.GetAllUserResponseItem
import waslim.binar.andlima.classdiscussions.network.ApiClient


class LoginFragment : Fragment() {
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check()
        login()

    }

    private fun check(){
        userManager = UserManager(requireContext())
        userManager.username.asLiveData().observe(viewLifecycleOwner){
            when {
                it != "" -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }


    private fun login(){
        btn_login.setOnClickListener {
            when {
                masukan_password.text.toString().isEmpty() -> {
                    Toast.makeText(requireContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
                }
                masukan_username.text.toString().isEmpty() -> {
                    Toast.makeText(requireContext(), "Username Salah", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Login Sukses", Toast.LENGTH_LONG).show()
                    getApiLogin()
                }
            }
        }
    }

    private fun getApiLogin(){
        val username = masukan_username.text.toString()
        val password = masukan_password.text.toString()
        userManager = UserManager(requireContext())

        ApiClient.instance.getDataUser(username, password)
            .enqueue(object : Callback<List<GetAllUserResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    when {
                        response.isSuccessful -> {
                            when {
                                response.body()?.isEmpty() == true -> {
                                    Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_LONG).show()
                                }
                                else -> {
                                    when {
                                        response.body()?.size!! > 1 -> {
                                            Toast.makeText(requireContext(), "Data Salah", Toast.LENGTH_LONG).show()
                                        }
                                        username != response.body()!![0].username -> {
                                            Toast.makeText(requireContext(), "Username Salah", Toast.LENGTH_LONG).show()
                                        }
                                        password != response.body()!![0].password -> {
                                            Toast.makeText(requireContext(), "Password Salah", Toast.LENGTH_LONG).show()
                                        }
                                        else -> {
                                            GlobalScope.launch {
                                                userManager.saveData(username, password, response.body()!![1].name, response.body()!![1].umur.toString(), response.body()!![1].image, response.body()!![1].address)
                                            }
                                            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()

                }

            })
    }


}