import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Created by TERMINAL on 12/30/2016.
 */
public class AddStudentData extends JFrame {

    JLabel Lname, Lrno, Lclass, Ldob, Lsex;
    JTextField Fname, Frno;
    JComboBox Fclass, Fsex;
    JDatePickerImpl datePicker;
    JPanel formPanel = new JPanel();
    JPanel imagePanel = new JPanel();
    JPanel images[] = new JPanel[5];
    JPanel btnPanel = new JPanel(new GridLayout(1, 2, 25, 25));
    JButton capture[] = new JButton[5];
    GridBagLayout gridForm = new GridBagLayout();
    GridBagLayout gridImage = new GridBagLayout();
    JButton save, cancel;
    BufferedImage faceImgs[] = new BufferedImage[5];
    String imageName[] = new String[5];
    Font font;
    java.util.List<Integer> cid;
    java.util.List<String> cources;

    AddStudentData() {
        super("Add Student Details");
        this.setSize(700, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLayout(new BorderLayout(0, 0));

        font = new Font("Segoe UI", Font.BOLD, 15);

        Lname = new JLabel("Name");
        Lname.setFont(font);
        Lrno = new JLabel("Roll Number");
        Lrno.setFont(font);
        Lclass = new JLabel("Course");
        Lclass.setFont(font);
        Ldob = new JLabel("Date of Birth");
        Ldob.setFont(font);
        Lsex = new JLabel("Sex");
        Lsex.setFont(font);

        Fname = new JTextField();
        Fname.setFont(font);
        Fname.setBorder(BorderFactory.createCompoundBorder(Fname.getBorder(),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        Frno = new JTextField();
        Frno.setFont(font);
        Frno.setBorder(BorderFactory.createCompoundBorder(Frno.getBorder(),BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        Fclass = new JComboBox();
        Fclass.setFont(font);
        Fsex = new JComboBox();
        Fsex.setFont(font);
        Fsex.addItem("Male");
        Fsex.addItem("Female");
        //Fclass.setBorder(BorderFactory.createCompoundBorder(Fclass.getBorder(),BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JFormattedTextField textField = datePicker.getJFormattedTextField();
        textField.setFont(font);

        cid = new ArrayList<Integer>();

        try {
            ResultSet rs = new DBController().getCourceList();
            cources = new java.util.ArrayList<String>();
            while (rs.next()){
                cid.add(rs.getInt(1));
                cources.add(rs.getString(2));
            }
            Fclass.setModel(new DefaultComboBoxModel(cources.toArray()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        gridForm.columnWidths = new int[]{100, 0};
        gridForm.rowHeights = new int[]{20, 20, 20};
        gridForm.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridForm.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        formPanel.setBorder(new EmptyBorder(5, 5, 10, 10));
        formPanel.setLayout(gridForm);

        GridBagConstraints gbc_lbl = new GridBagConstraints();
        gbc_lbl.insets = new Insets(0, 0, 5, 5);
        gbc_lbl.anchor = GridBagConstraints.WEST;
        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 0;
        formPanel.add(Lname, gbc_lbl);

        GridBagConstraints gbc_txt = new GridBagConstraints();
        gbc_txt.insets = new Insets(0, 0, 5, 0);
        gbc_txt.fill = GridBagConstraints.HORIZONTAL;
        gbc_txt.gridx = 1;
        gbc_txt.gridy = 0;
        formPanel.add(Fname, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 1;
        formPanel.add(Lrno, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 1;
        formPanel.add(Frno, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 2;
        formPanel.add(Lclass, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 2;
        formPanel.add(Fclass, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 3;
        formPanel.add(Ldob, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 3;
        formPanel.add(datePicker, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 4;
        formPanel.add(Lsex, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 4;
        formPanel.add(Fsex, gbc_txt);


        ImageIcon cameraIcon = new ImageIcon(getClass().getResource("/icons/camera_blue.png"));
        Image image = cameraIcon.getImage();
        Image newimg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        cameraIcon = new ImageIcon(newimg);

        gridImage.columnWidths = new int[]{0, 0};
        gridImage.rowHeights = new int[]{100, 100, 100};
        gridImage.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridImage.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        imagePanel.setBorder(new EmptyBorder(5, 5, 10, 10));

        GridBagConstraints gbc_image = new GridBagConstraints();
        gbc_image.insets = new Insets(0, 0, 10, 10);
        gbc_image.gridy = 0;
        for (int i = 0; i < 5; i++) {
            gbc_image.gridx = i;
            images[i] = new JPanel();
            images[i].setBorder(BorderFactory.createLineBorder(Color.black));
            images[i].setPreferredSize(new Dimension(120, 120));
            imagePanel.add(images[i], gbc_image);
        }
        gbc_image.gridy = 1;
        for (int i = 0; i < 5; i++) {
            gbc_image.gridx = i;
            capture[i] = new JButton(cameraIcon);
            capture[i].setPreferredSize(new Dimension(120, 30));
            capture[i].addActionListener((ActionEvent event) -> {
                CaptureImage img = new CaptureImage();
                img.setVisible(true);
                img.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                img.captureBtn.addActionListener((ActionEvent clicked) -> {
                    if(img.fdt.face!=null) {
                        img.setVisible(false);
                        BufferedImage face = img.fdt.face;
                        DateFormat df = new SimpleDateFormat("dd-MM-yy-HH-mm-ss");
                        //Date dateobj = new Date();
                        Calendar calobj = Calendar.getInstance();
                        String fname = df.format(calobj.getTime());
                        /*File f = new File(fname + ".png");
                        try {
                            ImageIO.write(face, "png", f);
                        } catch (IOException ex) {
                            Logger.getLogger(AddStudentData.class.getName()).log(Level.SEVERE, null, ex);
                        }*/
                        for(int j=0;j<5;j++) {
                            if (capture[j] == event.getSource()) {
                                Graphics g = images[j].getGraphics();
                                g.drawImage(face, 0, 0, images[j].getWidth(), images[j].getHeight(), 0, 0, face.getWidth(), face.getHeight(), null);
                                imageName[j] = fname;
                                faceImgs[j] = face;
                            }
                        }
                        img.fdt.runnable = false;
                        img.fdt.webCam.release();
                    }
                });
            });
            imagePanel.add(capture[i], gbc_image);
        }

        ImageIcon right = new ImageIcon(getClass().getResource("/icons/right.png"));
        Image rightImg = right.getImage();
        Image rightNew = rightImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        right = new ImageIcon(rightNew);
        save = new JButton(right);

        save.addActionListener((ActionEvent clicked) ->{
            try {
                DBController controller = new DBController();
                String name = Fname.getText();
                Integer rno = Integer.parseInt(Frno.getText());
                String gender = Fsex.getSelectedItem().toString();
                int cids = cid.get(Fclass.getSelectedIndex());
                Date dob = (Date) datePicker.getModel().getValue();
                int sid = controller.insertData(rno, name, gender, new java.sql.Date(dob.getTime()), cids);
                for(int i=0;i<5;i++){
                    File f = new File("face_images", imageName[i]+".png");
                    try {
                        ImageIO.write(faceImgs[i], "png", f);
                        controller.insertPhotos(sid, f.getName());
                    } catch (IOException ex) {
                        Logger.getLogger(AddStudentData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Details of student `"+name+" is added to database.`", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ImageIcon wrong = new ImageIcon(getClass().getResource("/icons/wrong.png"));
        Image wrongImg = wrong.getImage();
        Image wrongNew = wrongImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        wrong = new ImageIcon(wrongNew);
        cancel = new JButton(wrong);



        btnPanel.setBorder(new EmptyBorder(10, 25, 10, 25));// t, l, b, r
        formPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        btnPanel.add(save);
        btnPanel.add(cancel);

        this.setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.NORTH);
        this.add(imagePanel, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.SOUTH);
    }

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
