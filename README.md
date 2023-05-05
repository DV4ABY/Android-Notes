# Android-Notes
This is a simple note-taking application. The app allows users to create, edit, and manage notes, with the ability to mark notes as "important" or "archived." The main screen displays a scrollable list of notes, which can be filtered to show only non-archived notes, and includes a search function to find notes by their content. Notes are sorted by their status and creation date, with more recent notes appearing first.

## Features

- Single-Activity Architecture with multiple Fragments for different screens
- ViewModel for maintaining state and coordinating between Fragments
- LiveData for notifying Views about data changes

## Screens

- Main Screen: Displays a list of notes, with options to filter, archive, and delete notes. Also includes a button to create new notes.
- Edit Screen: Allows users to edit the title, content, and status of a note.
- Add Screen: Provides a form to create a new note with a title, content, and importance status.

## Setup and Usage

1. Clone the repository
2. Open the project in IntelliJ Idea
3. Run the app on an emulator or an Android device
