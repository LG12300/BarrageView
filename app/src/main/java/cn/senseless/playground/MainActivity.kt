package cn.senseless.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.senseless.playground.databinding.ActivityMainBinding
import com.google.android.flexbox.FlexboxLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var permissionLauncher: ActivityResultLauncher<String>
//    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btAdd.setOnClickListener {
            binding.barrageView.addBarrage(Random.nextInt(150,250), 50)
        }
    }

//    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
//        val preview: Preview = Preview.Builder()
//            .build()
//
//        val cameraSelector: CameraSelector = CameraSelector.Builder()
//            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//            .build()
//
//        preview.setSurfaceProvider(binding.cameraPreviewView.surfaceProvider)
//        val imageAnalysis = ImageAnalysis.Builder()
//            // enable the following line if RGBA output is needed.
//            // .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//            .setTargetResolution(Size(1280, 720))
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .build()
//        var camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview)
//    }
//
//    private fun openCamera() {
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//            bindPreview(cameraProvider)
//        }, ContextCompat.getMainExecutor(this))
//    }

    companion object {
        private const val TAG = "MainActivity"
    }
}