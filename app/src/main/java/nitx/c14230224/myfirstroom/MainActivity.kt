package nitx.c14230224.myfirstroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import nitx.c14230224.myfirstroom.database.Note
import nitx.c14230224.myfirstroom.database.NoteRoomDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var DB: NoteRoomDatabase

    private lateinit var adapterN: adapterNote
    private var arNote: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DB = NoteRoomDatabase.getDatabase(this)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        val rvNotes = findViewById<RecyclerView>(R.id.rvNotes)

        adapterN = adapterNote(arNote)

        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = adapterN

        fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }

        adapterN.setOnItemClickCallback(object : adapterNote.OnItemClickCallback {
            override fun delData(dtnote: Note) {
                CoroutineScope(Dispatchers.IO).async {
                    DB.funnoteDao().delete(dtnote)
                    val note = DB.funnoteDao().selectAll()
                    withContext(Dispatchers.Main) {
                        adapterN.isiData(note)
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val note = DB.funnoteDao().selectAll()
            adapterN.isiData(note)
            Log.d("data ROOM", note.toString())
        }
    }
}