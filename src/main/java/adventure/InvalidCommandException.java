package adventure;

import javax.swing.JOptionPane;

/**
 * A custom exception to throw when an invalid command is inputted
 */
public final class InvalidCommandException extends Exception{
    @Override
    public void printStackTrace() {
        JOptionPane.showMessageDialog(null,"Invalid Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
}
