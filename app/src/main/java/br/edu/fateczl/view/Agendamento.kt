package br.edu.fateczl.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import br.edu.fateczl.databinding.ActivityAgendamentoBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class Agendamento : AppCompatActivity() {

    private lateinit var binding: ActivityAgendamentoBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val nome = intent.extras?.getString("nome").toString()

        val datePicker = binding.datePicker
        datePicker.minDate = System.currentTimeMillis() - 1000 // Impede a seleção de dias passados
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dia = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
            val mes =
                if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else (monthOfYear + 1).toString()

            data = "$dia / $mes / $year"
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuto = if (minute < 10) "0$minute" else minute.toString()
            hora = "$hourOfDay:$minuto"
        }

        binding.timePicker.setIs24HourView(true)

        binding.btnAgendar.setOnClickListener {
            val barbeiro1 = binding.barbeiro1
            val barbeiro2 = binding.barbeiro2
            val barbeiro3 = binding.barbeiro3

            // Verifica se data e hora foram preenchidas
            if (data.isEmpty() || hora.isEmpty()) {
                mensagem(binding.root, "Preencha data e horário", "#FF0000")
                return@setOnClickListener
            }

            // Converte data e hora selecionadas para Calendar
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.timeInMillis = calendar.timeInMillis
            selectedCalendar.set(Calendar.HOUR_OF_DAY, hora.split(":")[0].toInt())
            selectedCalendar.set(Calendar.MINUTE, hora.split(":")[1].toInt())

            // Verifica se a data selecionada é no futuro
            if (selectedCalendar.before(Calendar.getInstance())) {
                mensagem(binding.root, "Selecione uma data e hora futura", "#FF0000")
                return@setOnClickListener
            }

            // Verifica se o horário está dentro do intervalo de funcionamento
            val horaInicio = 8 // 8:00 AM
            val horaFim = 19 // 7:00 PM
            val horaSelecionada = hora.split(":")[0].toInt()
            if (horaSelecionada < horaInicio || horaSelecionada > horaFim) {
                mensagem(binding.root, "Fechado - Horário de atendimento das 8:00 às 19:00", "#FF0000")
                return@setOnClickListener
            }

            // Verifica se pelo menos um barbeiro foi selecionado
            if (!barbeiro1.isChecked && !barbeiro2.isChecked && !barbeiro3.isChecked) {
                mensagem(binding.root, "Escolha um barbeiro", "#FF0000")
                return@setOnClickListener
            }

            // Se chegou até aqui, significa que tudo está correto
            mensagem(binding.root, "Agendamento realizado com sucesso!", "#03DAC5")
        }
    }

    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
}
