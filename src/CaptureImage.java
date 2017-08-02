
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

import org.opencv.highgui.VideoCapture;

/**
 * Created by TERMINAL on 12/30/2016.
 */
public class CaptureImage extends JFrame {

    JPanel webCamLayout = new JPanel();
    JPanel btnLayout = new JPanel();
    JButton captureBtn;

    FaceDetectionThread fdt = null;
    volatile boolean runnable = true;

    public CaptureImage() {
        super("Attendance System using Face Recognition");
        this.setSize(600, 450);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLayout(new BorderLayout(0, 0));

        ImageIcon cameraIcon = new ImageIcon(getClass().getResource("/icons/camera_blue.png"));
        Image image = cameraIcon.getImage();
        Image newimg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        cameraIcon = new ImageIcon(newimg);

        captureBtn = new JButton(cameraIcon);

        btnLayout.add(captureBtn);

        this.add(webCamLayout, BorderLayout.CENTER);
        this.add(btnLayout, BorderLayout.SOUTH);

        fdt = new FaceDetectionThread(webCamLayout);
        fdt.webCam = new VideoCapture(0);
        runnable = true;
        fdt.runnable = runnable;
        Thread tt = new Thread(fdt);
        tt.setDaemon(true);
        tt.start();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(CaptureImage.this,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    runnable = false;
                    fdt.runnable = runnable;
                    fdt.webCam.release();
                    CaptureImage.this.setVisible(false);
                }
            }
        });

    }
}
