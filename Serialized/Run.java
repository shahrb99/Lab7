package Serialized;

import java.time.LocalDate;
import java.util.Scanner;

public class Run {

    static Scanner input = new Scanner(System.in);
    static FileUtils fileUtils = new FileUtils("H:\\Notes");

    public static void main(String[] args) {

        int choice = 0;
        boolean cond = true;
        while (cond) {
            System.out.println("1. New note\n2. Notes\n3. Delete note\n4. Exit");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    newNote();
                    break;
                case 2:
                    notes();
                    break;
                case 3:
                    deleteNote();
                    break;
                case 4:
                    cond = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static void newNote() {

        input.nextLine();

        System.out.println("Title : ");
        String title = input.nextLine();

        System.out.println("Content : ");
        String content = input.nextLine();

        Note note = new Note(title, content, LocalDate.now().toString());

        System.out.println("Write your note : ");
        String text = input.nextLine();

        fileUtils.addNote(note, text);

    }

    private static void notes() {
        int i = 1;
        if (fileUtils.getNotes().isEmpty()) {
            System.out.println("There is no notes");
            return;
        } else {
            for (String info : fileUtils.printNotes()) {
                System.out.println(i + ". " + info);
                i++;
            }
        }

        int choice;
        boolean cond = true;
        while (cond) {
            choice = input.nextInt();
            if (choice > 0 && choice < i) {
                Note note = fileUtils.getNotes().get(choice - 1);
                System.out.println(note.getTitle() + " :\n" + fileUtils.readNote(note));
            } else if (choice == 0) {
                cond = false;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private static void deleteNote() {
        int i = 1;
        if (fileUtils.getNotes().isEmpty()) {
            System.out.println("There is no notes");
            return;
        } else {
            for (String info : fileUtils.printNotes()) {
                System.out.println(i + ". " + info);
                i++;
            }
        }

        int choice;
        boolean cond = true;
        while (cond) {
            choice = input.nextInt();
            if (choice > 0 && choice < i) {
                Note note = fileUtils.getNotes().get(choice - 1);
                fileUtils.removeNote(note);
                deleteNote();
            } else if (choice == 0) {
                cond = false;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
}
