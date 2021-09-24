package com.example.seriesorganizer

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_data.*
import kotlinx.android.synthetic.main.activity_edit_data.edtImage
import kotlinx.android.synthetic.main.activity_edit_data.edtNumberChaptersPerSeason
import kotlinx.android.synthetic.main.activity_edit_data.edtNumberOfSeasons
import kotlinx.android.synthetic.main.activity_edit_data.edtTitle
import kotlinx.android.synthetic.main.activity_edit_data.imageView
import java.lang.Exception

class EditData : AppCompatActivity() {

    var manageDatabase = ManageDatabase(this)

    var id = 0
    var title = ""
    var numberSeasons = 1
    var numberChaptersPerSeason = 1
    var image = ""

    lateinit var toastText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)

        checkIntentInfo()

        btnRemoveSerie.setOnClickListener(){ // Establish alert dialog to be sure when removing
            val title = this.getString(R.string.builder_title)
            val message = this.getString(R.string.builder_message)
            val positiveButtonText = this.getString(R.string.builder_positive_button)

            val builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            builder.setMessage(message)

            builder.setPositiveButton(positiveButtonText){ dialogInterface: DialogInterface, i: Int ->
                manageDatabase.removeSerie(id) // Delete serie in case is positive
                finish()
            }

            builder.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int -> }
            builder.show()
        }

        btnSaveChanges.setOnClickListener() {
            toastText = this.getString(R.string.btn_save_changes_number_format_exception)
            try {
                modifySerie()
            } catch (numberError: NumberFormatException) {
                Toast.makeText(
                    this,
                    toastText,
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                toastText = this.getString(R.string.exception)
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun checkIntentInfo() {
        if (intent.extras != null) {
            // Filling fields with extracted info
            id = intent.getIntExtra("id", 0)
            edtTitle.setText(intent.getStringExtra("title"))
            edtNumberOfSeasons.setText(intent.getIntExtra("numberOfSeasons", 1).toString())
            edtNumberChaptersPerSeason.setText(
                intent.getIntExtra("numberChaptersPerSeason", 1).toString()
            )
            Picasso.get().load(intent.getStringExtra("image")).into(imageView)
        }
    }

    private fun modifySerie() {
        collectInfo()
        assignInfo()
    }

    // Establish variables with info extracted from fields
    private fun collectInfo() {
        title = edtTitle.text.toString()
        numberSeasons = edtNumberOfSeasons.text.toString().toInt()
        numberChaptersPerSeason = edtNumberChaptersPerSeason.text.toString().toInt()
        checkImage()
    }

    // Check if image is empty
    private fun checkImage() {
        var edtImage = edtImage.text.toString()
        image = if (edtImage.isEmpty()) intent.getStringExtra("image").toString()
        else {
            edtImage
        }
    }

    private fun assignInfo() {
        toastText = this.getString(R.string.toast_set_title)
        if (title.isEmpty()) {
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        } else {
            toastText = this.getString(R.string.toast_saved_correctly)
            manageDatabase.updateDatabase(id, title, numberSeasons, numberChaptersPerSeason, image)
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }
}
