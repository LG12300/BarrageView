package cn.senseless.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.senseless.playground.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btAdd.setOnClickListener {
            binding.barrageView.addBarrage(Random.nextInt(150, 250), 50)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}