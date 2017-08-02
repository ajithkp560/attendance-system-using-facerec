
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.opencv.core.Core;

/**
 * Created by TERMINAL on 12/30/2016.
 */
public class MainWindow {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(() -> {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
            mf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        });
    }

}
