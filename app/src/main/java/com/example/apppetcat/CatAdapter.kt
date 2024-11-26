import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppetcat.Cat
import com.example.apppetcat.R


class CatAdapter(private val cats: MutableList<Cat>, private val onDeleteClick: (Cat) -> Unit) :
    RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat_card, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        holder.bind(cat)
    }

    override fun getItemCount(): Int = cats.size

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.catNameTextView)
        private val ageTextView: TextView = itemView.findViewById(R.id.catAgeTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(cat: Cat) {
            nameTextView.text = cat.name
            ageTextView.text = "Возраст: ${cat.age}"
            deleteButton.setOnClickListener {
                onDeleteClick(cat) // Вызываем лямбду для удаления кота
            }
        }
    }
}