package nitx.c14230224.myfirstroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nitx.c14230224.myfirstroom.database.Note

class adapterNote(private val listNote: MutableList<Note>) :
    RecyclerView.Adapter<adapterNote.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtnote: Note)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        var tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        var tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        var btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        var btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_notes, parent, false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var note = listNote[position]

        holder.tvJudul.text = note.judul
        holder.tvDeskripsi.text = note.deskripsi
        holder.tvTanggal.text = note.tanggal

        holder.btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahData::class.java)
            intent.putExtra("noteId", note.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            onItemClickCallback.delData(note)
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    fun isiData(list: List<Note>) {
        listNote.clear()
        listNote.addAll(list)
        notifyDataSetChanged()
    }
}