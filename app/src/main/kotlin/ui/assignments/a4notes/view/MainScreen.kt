package ui.assignments.a4notes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ui.assignments.a4notes.R
import ui.assignments.a4notes.viewmodel.NotesViewModel

class MainScreen : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.main_screen, container, false)
        // Retrieve the ViewModel associated with the Activity (MainActivity)
        val notesVM: NotesViewModel by activityViewModels { NotesViewModel.Factory }

        view.findViewById<Button>(R.id.addNoteBtn).setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_addScreen)
        }

        val searchBar = view.findViewById<EditText>(R.id.searchBar)
        searchBar.addTextChangedListener {
            val searchStr = searchBar.text.toString()
            notesVM.setSearchStr(searchStr)
            notesVM.filterVisible(searchStr)
        }

        val arcSwitch = view.findViewById<Switch>(R.id.archiveSwitch)
        arcSwitch.setOnCheckedChangeListener { _, isChecked ->
            notesVM.setViewArchived(isChecked)
        }
        notesVM.getViewArchived().observe(viewLifecycleOwner) {
            arcSwitch.isChecked = it
        }

        notesVM.getNotes().observe(viewLifecycleOwner) { it ->
            val notesView = view.findViewById<LinearLayout>(R.id.notes)
            notesView.removeAllViews()
            it.forEach {
                val noteView = layoutInflater.inflate(R.layout.note_view, container, false)
                val id = it.value!!.id
                val archived = it.value!!.archived
                if (it.value!!.important) {
                    noteView.setBackgroundColor(ContextCompat.getColor(activity?.applicationContext!!,
                        R.color.important
                    ))
                }
                if (it.value!!.archived) {
                    noteView.setBackgroundColor(ContextCompat.getColor(activity?.applicationContext!!, R.color.gray_400))
                }
                val title = noteView.findViewById<TextView>(R.id.noteTitle)
                title.text = it.value!!.title
                title.setOnClickListener { _ ->
                    findNavController().navigate(R.id.action_mainScreen_to_editScreen)
                    notesVM.setEditNote(it.value!!)
                }

                val content = noteView.findViewById<TextView>(R.id.noteContent)
                content.text = it.value!!.content
                content.setOnClickListener { _ ->
                    findNavController().navigate(R.id.action_mainScreen_to_editScreen)
                    notesVM.setEditNote(it.value!!)
                }

                noteView.findViewById<Button>(R.id.archiveBtn).setOnClickListener {
                    notesVM.updateNoteArchived(id, !archived)
                }
                noteView.findViewById<Button>(R.id.deleteBtn).setOnClickListener {
                    notesVM.removeNote(id)
                }

                notesView.addView(noteView)
            }
        }

        return view
    }
}