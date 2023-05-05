package ui.assignments.a4notes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class AddScreen : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.add_screen, container, false)
        // Retrieve the ViewModel associated with the Activity (MainActivity)
        val notesVM: NotesViewModel by activityViewModels { NotesViewModel.Factory }

        view.findViewById<Button>(R.id.createNoteBtn).setOnClickListener {
            val editTitle = view.findViewById<EditText>(R.id.newNoteTitle)
            val title = editTitle.text.toString().ifEmpty { "Title" }
            val editContent = view.findViewById<EditText>(R.id.newNoteContent)
            val content = editContent.text.toString().ifEmpty { "Content" }
            val impSwitch = view.findViewById<Switch>(R.id.addImpSwitch)
            val isImportant = impSwitch.isChecked
            notesVM.addNote(title, content, isImportant)
            notesVM.setSearchStr("")
            notesVM.filterVisible("")
            findNavController().navigate(R.id.action_addScreen_to_mainScreen)
        }

        return view
    }
}