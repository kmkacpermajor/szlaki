package com.mahor.szlaki

import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream


class TrailDetailFragment : Fragment() {
    private val trailViewModel: TrailViewModel by activityViewModels {
        TrailViewModelFactory((requireActivity().application as TrailsApplication).repository)
    }
    var trailId: Long = 0
    lateinit var tempImageUri: Uri
    lateinit var resultLauncher: ActivityResultLauncher<Uri>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trail_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            val stoper = StoperFragment((requireActivity().application as TrailsApplication).repository, trailId)
            val ft: FragmentTransaction = childFragmentManager.beginTransaction()
            ft.add(R.id.stoper_container, stoper)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }else{
            trailId = savedInstanceState.getLong(DetailActivity().EXTRA_TRAIL_ID)
        }

        tempImageUri = initTempUri()
        resultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
            saveCapturedImage(tempImageUri)
        }

    }

    fun saveCapturedImage(uri: Uri): String {
        val bitmap = when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)

            else -> {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        }
        val file = File(requireContext().applicationInfo.dataDir,"image_"+System.currentTimeMillis().toString()+".jpg")

        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)

        out.close()

        return file.path
    }

    fun initTempUri(): Uri {
        val tempImagesDir = File(requireContext().filesDir, "images")

        tempImagesDir.mkdir()

        val tempImage = File(tempImagesDir, "photo")

        return FileProvider.getUriForFile(requireContext(), "com.mahor.provider", tempImage)
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {

            view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
                resultLauncher.launch(tempImageUri)
            }

            val titleTextView = view.findViewById<TextView>(R.id.textTitle)
            val descriptionTextView = view.findViewById<TextView>(R.id.textDescription)
            val durationTextView = view.findViewById<TextView>(R.id.textDuration)
            val difficultyTextView = view.findViewById<TextView>(R.id.textDifficulty)
            val imageView = view.findViewById<ImageView>(R.id.image_detail)

            trailViewModel.getTrailById(trailId).observe(viewLifecycleOwner) { trail ->
                titleTextView.text = trail.name
                descriptionTextView.text = trail.description
                durationTextView.text = "${trail.walk_minutes / 60}h ${trail.walk_minutes % 60}min"
                difficultyTextView.text = trail.getLocalizedDifficulty(requireContext())
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                Glide.with(view).load(trail.photoUrl).apply(options).into(imageView)
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putLong(DetailActivity().EXTRA_TRAIL_ID, trailId)
    }
}