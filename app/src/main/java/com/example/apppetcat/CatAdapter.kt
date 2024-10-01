import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apppetcat.Cat
import com.example.apppetcat.R

class CatAdapter(private var cats: List<Cat>) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat_card, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        holder.catName.text = cat.name
        holder.catAge.text = "Age: ${cat.age} years"

        // Загрузка изображения с использованием Glide
        Glide.with(holder.itemView.context)
            .load(cat.imageUrl)
            .placeholder(R.drawable.ic_cat_placeholder)
            .into(holder.catImage)
    }

    override fun getItemCount(): Int = cats.size

    fun updateCats(newCats: List<Cat>) {
        cats = newCats
        notifyDataSetChanged()
    }

    class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val catName: TextView = view.findViewById(R.id.catName)
        val catAge: TextView = view.findViewById(R.id.catAge)
        val catImage: ImageView = view.findViewById(R.id.catImage)
    }
}
