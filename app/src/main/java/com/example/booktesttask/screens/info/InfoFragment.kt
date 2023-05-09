package com.example.booktesttask.screens.info

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.booktesttask.R
import com.example.booktesttask.databinding.FragmentInfoBinding
import com.example.booktesttask.utils.factory

class InfoFragment: Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getUser()
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        binding.tryAgainButton.setOnClickListener{ viewModel.getUser()}
        binding.feedbackBtn.setOnClickListener {
            showFeedbackDialog()
        }
        binding.ShareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,viewModel.getShareInfo())
            val chooser = Intent.createChooser(intent,"Отправить")
            startActivity(chooser)
        }
        observeAccountDetails()
        observeState()
        return binding.root
    }

    private fun showFeedbackDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_contact_us)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val email: EditText = dialog.findViewById(R.id.emailEdit)
        val feedback: EditText = dialog.findViewById(R.id.feedbackEdit)
        val btnSubmit: Button = dialog.findViewById(R.id.submitBtn)
        val btnBack: Button = dialog.findViewById(R.id.cancelBtn)

        btnSubmit.setOnClickListener {
            viewModel.submitFeedback(email.text.toString(), feedback.text.toString())
        }
        btnBack.setOnClickListener {
            dialog.dismiss()
        }

        viewModel.dialogState.observe(viewLifecycleOwner) {
            if (it.processOk) {
                dialog.dismiss()
                Log.e("feedback", "ok")
                Toast.makeText(context, "Спасибо за обратную связь!", Toast.LENGTH_SHORT).show()
            }
            email.error = if (it.emptyEmailError) "Пусто" else null
            feedback.error = if (it.emptyMessageError) "Пусто" else null
        }
        dialog.show()
    }

    private fun observeAccountDetails() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) return@observe
            binding.likedcount.text = user.favorite_books?.size.toString()
            binding.readcount.text = user.already_read_books?.size.toString()
            binding.topgenres.text = viewModel.getTopGenres()
        }
    }


    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.tryAgainContainer.visibility = if(it.apiFailInfo) View.VISIBLE else View.INVISIBLE
        binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.INVISIBLE
        binding.all.visibility = if (it.showProgress || it.apiFailInfo) View.INVISIBLE else View.VISIBLE
    }
}