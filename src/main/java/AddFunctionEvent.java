import java.util.Arrays;

public class AddFunctionEvent extends Function {

    public AddFunctionEvent(String function, boolean exit) {
        super(function, exit);
    }

    public void run(TaskList tasks, UITextBox textBox, Storage storage) throws CleverNotBotException {
        String[] desc = getFunction().split(" ");
        try{
            if (desc.length == 1) {
                throw new CleverNotBotException("Please fill in the description of Event!", textBox);
            } else if (!getFunction().contains("/at")) {
                throw new CleverNotBotException("Please include a /at in your description of Deadline! ", textBox);
            } else {
                String searchWord = " /at";
                int start = "event ".length();
                int mid = getFunction().indexOf(searchWord);
                String functionName = getFunction().substring(start, mid);
                String at = getFunction().substring(mid + searchWord.length() + 1); // to remove the space;
                Task newTask = new Event(functionName, false, at);
                tasks.addTask(newTask);
                storage.writeToFile(tasks.getTaskList());
                textBox.chat(String.format(
                        "Got it. I've added this task:" +
                                "\n  %s" +
                                "\nNow you have %d tasks in the list."
                        , newTask.toString(), tasks.getSize()));
            }
        } catch(CleverNotBotException e){
            throw new CleverNotBotException("Event description must not be empty or must contain /at!", textBox);
        }

    }

}