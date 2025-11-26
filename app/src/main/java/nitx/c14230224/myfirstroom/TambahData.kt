package nitx.c14230224.myfirstroom

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import nitx.c14230224.myfirstroom.database.Note
import nitx.c14230224.myfirstroom.database.NoteRoomDatabase
import nitx.c14230224.myfirstroom.helper.DateHelper

class TambahData : AppCompatActivity() {
    val DB = NoteRoomDatabase.getDatabase(this)
    var tanggal = DateHelper.getCurrentDate()

    var iID: Int = 0
    var iAddEdit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        iID = intent.getIntExtra("noteId", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            btnTambah.visibility = View.VISIBLE
            btnUpdate.visibility = View.GONE
            etJudul.isEnabled = true
        } else {
            btnTambah.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
            etJudul.isEnabled = false

            CoroutineScope(Dispatchers.IO).async {
                val item = DB.funnoteDao().getNote(iID)
                withContext(Dispatchers.Main) {
                    etJudul.setText(item.judul)
                    etDeskripsi.setText(item.deskripsi)
                }
            }
        }

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().insert(
                    Note(0, etJudul.text.toString(), etDeskripsi.text.toString(), tanggal)
                )
            }
            finish()
        }

        btnUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().update(
                    isi_judul = etJudul.text.toString(),
                    isi_deskripsi = etDeskripsi.text.toString(),
                    isi_id = iID
                )
            }
            finish()
        }
    }
}