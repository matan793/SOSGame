import javax.swing.*;

public class Menu {
    private int choice;
    private Object[] options;
    private String msg;
    private String title;
    /**
     * Constructs a Menu object with the specified options, message, and title.
     *
     * @param options The array of menu options.
     * @param msg The message to display in the dialog.
     * @param title The title of the dialog.
     */
    public Menu(Object[] options, String msg, String title) {
        this.options = options;
        this.msg = msg;
        this.title = title;
        show();

    }
    /**
     * Displays the menu dialog.
     */
    public void show()
    {
        this.choice = JOptionPane.showOptionDialog(null, msg,
                title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
    }
    /**
     * Returns the index of the selected option.
     *
     * @return The index of the selected option.
     */
    public int getChoice() {
        return choice;
    }
    /**
     * Sets the message to be displayed in the dialog.
     *
     * @param msg The message to be displayed.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    /**
     * Sets the title of the dialog.
     *
     * @param title The title of the dialog.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Returns the array of menu options.
     *
     * @return The array of menu options.
     */
    public Object[] getOptions() {
        return options;
    }
    /**
     * Sets the array of menu options.
     *
     * @param options The array of menu options.
     */
    public void setOptions(Object[] options) {
        this.options = options;
    }
}