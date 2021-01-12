package adventure;

import javax.swing.JTextArea;
import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {

    private JTextArea textArea;

    public CustomOutputStream(JTextArea aTextArea) {
        this.textArea = aTextArea;
    }

    //TODO dodělat javadoc
    /**
     * @param b
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
