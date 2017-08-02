
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.glass.ui.SystemClipboard;
import org.opencv.highgui.VideoCapture;

/**
 * Created by TERMINAL on 12/30/2016.
 */
public class MainFrame extends JFrame {

    //JPanel twoPanel = new JPanel();
    JPanel webCamLayout = new JPanel();
    JPanel btnLayout = new JPanel();
    JButton captureBtn = new JButton("DETECT");
    String imageName;

    FaceDetectionThread fdt = null;
    volatile boolean runnable = false;

    public MainFrame() {
        super("Attendance System using Face Recognition");
        this.setSize(800, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLayout(new BorderLayout(0, 0));

        /*LBPH lbph = new LBPH(1, 256, new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB));
        ImageData imageData = lbph.getNearestImage();
        System.out.println(imageData.name + ":" + imageData.distance);*/

        MenuBarinit();
        
        btnLayout.add(captureBtn);

        this.add(webCamLayout, BorderLayout.CENTER);
        this.add(btnLayout, BorderLayout.SOUTH);

        captureBtn.addActionListener((ActionEvent event) -> {
            if (!runnable) {
                captureBtn.setText("DETECTING");
                fdt = new FaceDetectionThread(webCamLayout, this);
                fdt.webCam = new VideoCapture(0);
                runnable = true;
                fdt.runnable = runnable;
                Thread t = new Thread(fdt);
                t.setDaemon(true);
                t.start();
            } else {
                captureBtn.setText("DETECT");
                runnable = false;
                fdt.runnable = runnable;
                fdt.webCam.release();
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        runnable = false;
                        fdt.runnable = runnable;
                        fdt.webCam.release();
                        System.exit(0);
                    }catch(Exception e){
                        System.out.println(e.toString());
                        System.exit(0);
                    }
                }
            }
        });
    }
    private void MenuBarinit() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        JMenuItem AddStudent = new JMenuItem("Add Student Details");
        AddStudent.setMnemonic(KeyEvent.VK_A);
        AddStudent.addActionListener((ActionEvent event) -> {
            AddStudentData asf = new AddStudentData();
            asf.setVisible(true);
            asf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        });
        JMenuItem EditStudent = new JMenuItem("Edit Student Details");
        EditStudent.setMnemonic(KeyEvent.VK_E);
        JMenuItem DelStudent = new JMenuItem("Delete Student Details");
        DelStudent.setMnemonic(KeyEvent.VK_D);
        JMenuItem Exit = new JMenuItem("Exit");
        Exit.setMnemonic(KeyEvent.VK_X);
        Exit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        JMenuItem AddNewPics = new JMenuItem("Add New Photos");
        AddNewPics.setMnemonic(KeyEvent.VK_N);
        AddNewPics.addActionListener((ActionEvent event) -> {
            AddNewPics anp = new AddNewPics();
            anp.setVisible(true);
            anp.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        });
        menu.add(AddStudent);
        menu.add(EditStudent);
        menu.add(DelStudent);;
        menu.add(Exit);
        menu.add(AddNewPics);
        mb.add(menu);
        setJMenuBar(mb);
    }

    public void FaceIdentified(String imageName){
        this.imageName = imageName;
        captureBtn.setText("DETECT");
        runnable = false;
        fdt.runnable = runnable;
        fdt.webCam.release();
        try {
            DBController controller = new DBController();
            ResultSet rs = controller.getStudent(imageName);
            if(rs!=null && rs.next()){
                int sid = rs.getInt(1);
                String name = rs.getString(2);
                int rno = rs.getInt(3);
                //JOptionPane.showMessageDialog(null, "Detected person: "+name+", Roll Number: "+rno);
                //System.out.println("SID: "+sid);
                AttendanceMarkPrompt prompt = new AttendanceMarkPrompt(sid, rno, name);
                prompt.setVisible(true);
                prompt.setSize(600, 200);
                prompt.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
