import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zodipair.R

class ImageGalleryAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageUrls[position]
        Glide.with(holder.itemView.context)
            .load(currentItem) // Cargar la URL
            .placeholder(R.drawable.confused) // Imagen mientras se carga
            .into(holder.imageView)
    }

    override fun getItemCount() = imageUrls.size
}
