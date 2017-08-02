import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by TERMINAL on 12/30/2016.
 */
public class EditStudentData extends JFrame {
    public EditStudentData(BufferedImage src){
        super("Edit Student Details");
        this.setSize(500, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLayout(new BorderLayout(0, 0));
        JPanel image = new JPanel();
        Graphics g = image.getGraphics();
        g.drawImage(src, 0, 0, image.getWidth(), image.getHeight(), 0, 0, src.getWidth(), src.getHeight(), null);
    }
}
