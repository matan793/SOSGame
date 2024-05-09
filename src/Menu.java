import javax.swing.*;

public class Menu {
    private int choice;
    private Object[] options;
    private String msg;
    private String title;
    public Menu(Object[] options, String msg, String title) {
        this.options = options;
        this.msg = msg;
        this.title = title;
        show();

    }
    public void show()
    {
        this.choice = JOptionPane.showOptionDialog(null, msg,
                title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }
    public int getChoice() {
        return choice;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object[] getOptions() {
        return options;
    }

    public void setOptions(Object[] options) {
        this.options = options;
    }
}