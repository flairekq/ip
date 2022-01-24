public class UpdateCommand extends Command {
    public UpdateCommand(String command, String[] tokenizedCommand) {
        super(command, tokenizedCommand);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        int index = Integer.parseInt(this.tokenizedCommand[1]) - 1;
        Task t = tasks.getTask(index);
        String oldDetails = t.getSaveFormat();
        String outputMsg = "";
        switch (this.tokenizedCommand[0]) {
        case "mark":
            t.markAsDone();
            outputMsg = "Good job! I've marked this task as done:\n" + t;
            break;
        case "unmark":
            t.markAsNotDone();
            outputMsg = "Okay, I've marked this task as not done yet:\n" + t;
            break;
        }
        storage.updateSavedTasks(oldDetails, t.getSaveFormat());
        ui.printMsg(outputMsg);
    }
}
