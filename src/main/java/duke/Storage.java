package duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import task.*;

public class Storage {
    private String filePath;
    private ArrayList<String> content;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.content = new ArrayList<>();
    }

    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File f = new File(this.filePath);
            Scanner s = new Scanner(f);
            this.content = new ArrayList<>();
            while (s.hasNextLine()) {
                this.content.add(s.nextLine());
                String[] command = this.content.get(this.content.size() - 1).split(",");
                Task t;
                switch (command[0]) {
                case "T":
                    t = new Todo(command[2]);
                    break;
                case "D":
                    t = new Deadline(command[2], command[3]);
                    break;
                case "E":
                    t = new Event(command[2], command[3]);
                    break;
                default:
                    t = new Task("placeholder task");
                    break;
                }

                if (command[1].equals("1")) {
                    t.markAsDone();
                }
                tasks.add(t);
            }
        } catch (IOException e) {
            // throw new DukeException("Something went wrong with loading the tasks file");
            try {
                File parentDir = new File("./data");
                parentDir.mkdir();
                String fileName = "duke.txt";
                File file = new File(parentDir, fileName);
                file.createNewFile();
            } catch (IOException e2) {
                throw new DukeException("Something went wrong with creating a file to save the tasks");
            }
        }
        return tasks;
    }

    public void updateSavedTasks(String oldDetails, String updatedDetails) throws DukeException {
        try {
            FileWriter fw;
            if (oldDetails.isEmpty()) {
                this.content.add(updatedDetails);
                fw = new FileWriter(this.filePath, true);
                fw.write(updatedDetails);
            } else {
                fw = new FileWriter(this.filePath);
                StringBuilder sb = new StringBuilder();
                if (updatedDetails.isEmpty()) {
                    int indexToRemoveAt = 0;
                    for (int i = 0; i < this.content.size(); i++) {
                        if (!this.content.get(i).equals(oldDetails)) {
                            sb.append(this.content.get(i) + System.lineSeparator());
                        } else {
                            indexToRemoveAt = i;
                        }
                    }
                    this.content.remove(indexToRemoveAt);
                } else {
                    for (int i = 0; i < this.content.size(); i++) {
                        if (this.content.get(i).equals(oldDetails)) {
                            this.content.set(i, updatedDetails);
                        }
                        sb.append(this.content.get(i) + System.lineSeparator());
                    }
                }
                fw.write(sb.toString());
            }
            fw.close();
        } catch (IOException e) {
            throw new DukeException("Something went wrong with saving the tasks");
        }
    }
}
