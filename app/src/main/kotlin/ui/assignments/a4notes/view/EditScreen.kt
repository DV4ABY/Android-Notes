package ui.assignments.a4notes.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class EditScreen : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.edit_screen, container, false)
        // Retrieve the ViewModel associated with the Activity (MainActivity)
        val notesVM: NotesViewModel by activityViewModels { NotesViewModel.Factory }

        val impSwitch = view.findViewById<Switch>(R.id.changeImpSwitch)
        val arcSwitch = view.findViewById<Switch>(R.id.changeArcSwitch)
        val title = view.findViewById<TextView>(R.id.changeNoteTitle)
        val content = view.findViewById<TextView>(R.id.changeNoteContent)

        notesVM.getEditNote().observe(viewLifecycleOwner) {
            impSwitch.isChecked = it.important
            arcSwitch.isChecked = it.archived
            title.text = SpannableStringBuilder(it.title)
            content.text = SpannableStringBuilder(it.content)
            title.addTextChangedListener { title ->
                notesVM.updateNoteTitle(it.id, title.toString())
            }
            content.addTextChangedListener { content ->
                notesVM.updateNoteContent(it.id, content.toString())
            }
            impSwitch.setOnCheckedChangeListener { _, impCheck ->
                notesVM.updateNoteImportant(it.id, impCheck)
                if (impCheck) arcSwitch.isChecked = false
            }
            arcSwitch.setOnCheckedChangeListener { _, arcCheck ->
                notesVM.updateNoteArchived(it.id, arcCheck)
                if (arcCheck) impSwitch.isChecked = false
            }
        }

        return view
    }
}