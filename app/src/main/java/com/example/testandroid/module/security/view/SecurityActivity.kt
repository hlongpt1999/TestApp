package com.example.testandroid.module.security.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testandroid.R
import com.example.testandroid.databinding.ActivitySecurityBinding
import com.example.testandroid.function.OpenActivity.open
import com.example.testandroid.function.OpenActivity.openLoginActivity
import com.example.testandroid.model.ScreenEnum
import java.util.*

class SecurityActivity : AppCompatActivity(), View.OnClickListener,
    KeyboardAdapter.IOnClickListener {

    private lateinit var binding: ActivitySecurityBinding

    private val listKeyBoard = arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecurityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inTime()
        initKeyBoard()
        initClickEvent()
    }

    private fun inTime() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+07:00"))
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        if (!(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)) {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            if (hour in 9..18) {
                return
            }
        }
        //todo
//        this.openLoginActivity()
        this.open(ScreenEnum.DASHBOARD, true)
    }

    private fun initKeyBoard() {
        binding.rcvKeyBoard.apply {
            layoutManager = GridLayoutManager(this@SecurityActivity, 3)
            adapter = KeyboardAdapter(context, ArrayList(listKeyBoard), this@SecurityActivity)
            hasFixedSize()
        }
    }

    private fun initClickEvent() {
        binding.apply {
            edtPin.setOnClickListener(this@SecurityActivity)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.edtPin -> {
                binding.edtPin.setText("")
            }
        }
    }

    override fun onItemClick(text: String) {
        if (binding.edtPin.length() < 4) {
            val textAfter = binding.edtPin.text.toString() + text
            binding.edtPin.setText(textAfter)
        } else if (binding.edtPin.text.toString() == "2299")  {
            this.openLoginActivity()
        }
    }
}