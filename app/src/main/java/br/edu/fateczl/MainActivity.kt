package br.edu.fateczl

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.fateczl.databinding.ActivityMainBinding
import br.edu.fateczl.view.Home
import com.google.android.material.snackbar.Snackbar

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {

            val nome = binding.etNome.text.toString()
            val senha = binding.etSenha.text.toString()

            when {
                nome.isEmpty() -> {
                    mensagem(it, "Informe seu nome!")
                }
                senha.isEmpty() -> {
                    mensagem(it, "Informe sua senha!")
                }
                senha.length <= 5 -> {
                    mensagem(it, "A senha deve possuir mais de 6 caracteres!")
                }
                else -> {
                    navegarHome(nome)
                }
            }
        }
    }

    private fun mensagem(view: View, mensagem: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun navegarHome(nome: String) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome", nome)
        startActivity(intent)
    }
}
