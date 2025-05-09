package ToDoApp;

public class TodoItem {
    private String description;
    private boolean isDone;

    public TodoItem(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public void setIsDone(boolean isDone){
        this.isDone = isDone;
    }

    public String toString() {
        if(isDone){
            return description + " DONE";
        } else {
            return description + " NOT DONE";
        }
    }
}
