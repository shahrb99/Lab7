package Serialized;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * FileUtils class represents a writer and reader for our notes in noting system
 */
public class FileUtils {

    // A list of notes
    private ArrayList<Note> notes;

    // Path of the notes folder
    private String filename;

    /**
     * Create a new FileUtils
     */
    public FileUtils(String filename) {
        this.filename = filename;
        notes = new ArrayList<>();

        for (File file : Objects.requireNonNull(new File(filename + "\\Classes").listFiles())) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                notes.add((Note) inputStream.readObject());
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Get the path of the notes folder
     *
     * @return filename field
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Get the list of files
     *
     * @return notes field
     */
    public ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * Add a note to the list of notes
     *
     * @param note a note
     */
    public void addNote(Note note, String text) {
        if (!notes.contains(note)) {
            note.setFile(new File(filename + "\\" + note.getTitle() + ".txt"));
            note.setNote(new File(filename + "\\Classes\\" + note.getTitle() + ".txt"));

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(note.getFile()));
                bos.write(text.getBytes());
                bos.flush();
                bos.close();

                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(note.getNote()));
                outputStream.writeObject(note);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            notes.add(note);
        }
    }

    /**
     * Remove a note from the list of notes
     *
     * @param note a note
     */
    public void removeNote(Note note) {
        if (notes.contains(note)) {
            note.getFile().delete();
            note.getNote().delete();
            notes.remove(note);
        }
    }

    /**
     * Read a note
     *
     * @param note the note
     * @return the text of note
     */
    public String readNote(Note note) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(note.getFile()));

            int i;
            while ((i = bis.read()) != -1) {
                text.append((char) i);
            }

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

    /**
     * Print the information of notes
     *
     * @return information of notes
     */
    public ArrayList<String> printNotes() {
        ArrayList<String> notesInfo = new ArrayList<>();

        for (Note note : notes) {
            String text = readNote(note);
            int endIndex = text.length();

            if (endIndex > 20) {
                endIndex /= 2;
            }

            String info = "Title : " + note.getTitle() + " | Content : " + note.getContent() + " | Date : " + note.getDate() + "\n" + readNote(note).substring(0, endIndex);

            notesInfo.add(info);
        }

        return notesInfo;
    }
}

