package nitx.c14230224.myfirstroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import nitx.c14230224.myfirstroom.database.Note
import nitx.c14230224.myfirstroom.database.NoteRoomDatabase
import nitx.c14230224.myfirstroom.helper.DateHelper

// Langkah 15: Nama class sesuai modul "TambahData"
class TambahData : AppCompatActivity() {

    // Langkah 16: Variabel Global Database [cite: 230]
    val DB = NoteRoomDatabase.getDatabase(this)

    // Langkah 17: Variabel Tanggal [cite: 232]
    var tanggal = DateHelper.getCurrentDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().insert(
                    Note(
                        id = 0,
                        judul = etJudul.text.toString(),
                        deskripsi = etDeskripsi.text.toString(),
                        tanggal = tanggal
                    )
                )
            }
            finish()
        }
    }
}