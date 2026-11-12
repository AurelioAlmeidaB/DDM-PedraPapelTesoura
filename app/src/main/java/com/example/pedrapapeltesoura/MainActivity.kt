package com.example.pedrapapeltesoura

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Opções do jogo, cada uma ligada à sua imagem
    enum class Opcao(val imagem: Int) {
        PEDRA(R.drawable.pedra),
        PAPEL(R.drawable.papel),
        TESOURA(R.drawable.tesoura)
    }

    private lateinit var imgApp: ImageView
    private lateinit var tvResultado: TextView
    private lateinit var tvPlacar: TextView

    private var vitorias = 0
    private var derrotas = 0
    private var empates = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgApp = findViewById(R.id.imgApp)
        tvResultado = findViewById(R.id.tvResultado)
        tvPlacar = findViewById(R.id.tvPlacar)

        atualizarPlacar()
    }

    fun imgPedraOnClick(view: View) {
        jogar(Opcao.PEDRA)
    }

    fun imgPapelOnClick(view: View) {
        jogar(Opcao.PAPEL)
    }

    fun imgTesouraOnClick(view: View) {
        jogar(Opcao.TESOURA)
    }

    private fun jogar(escolhaUsuario: Opcao) {
        // A máquina sorteia uma das três opções
        val escolhaApp = Opcao.entries.random()
        imgApp.setImageResource(escolhaApp.imagem)

        when {
            escolhaUsuario == escolhaApp -> {
                empates++
                tvResultado.text = getString(R.string.resultado_empate)
            }
            venceDe(escolhaUsuario, escolhaApp) -> {
                vitorias++
                tvResultado.text = getString(R.string.resultado_vitoria)
            }
            else -> {
                derrotas++
                tvResultado.text = getString(R.string.resultado_derrota)
            }
        }
        atualizarPlacar()
    }

    // Pedra ganha da tesoura, papel ganha da pedra, tesoura ganha do papel
    private fun venceDe(a: Opcao, b: Opcao): Boolean {
        return (a == Opcao.PEDRA && b == Opcao.TESOURA) ||
                (a == Opcao.PAPEL && b == Opcao.PEDRA) ||
                (a == Opcao.TESOURA && b == Opcao.PAPEL)
    }

    private fun atualizarPlacar() {
        tvPlacar.text = getString(R.string.tv_placar, vitorias, derrotas, empates)
    }
}
