import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by TERMINAL on 2/7/2017.
 */
public class AddNewPics extends JFrame {
    JLabel Lcource, Lname, Lrno, Lsid, Vcource, Vname, Vrno;
    JComboBox Csid = new JComboBox();
    java.util.List<String> Ldetails = new ArrayList<String>();
    java.util.List<Integer> Lsids = new ArrayList<Integer>();
    java.util.List<String> Lnames = new ArrayList<String>();
    java.util.List<String> Lcources = new ArrayList<String>();
    java.util.List<Integer> Lrnos = new ArrayList<Integer>();

    GridBagLayout gridImage = new GridBagLayout();
    GridBagLayout gridForm = new GridBagLayout();

    JPanel images[] = new JPanel[5];
    JButton capture[] = new JButton[5];
    BufferedImage faceImgs[] = new BufferedImage[5];
    String imageName[] = new String[5];
    JPanel imagePanel = new JPanel();
    JPanel btnPanel = new JPanel(new GridLayout(1, 2, 25, 25));
    JPanel formPanel = new JPanel();
    JButton save, cancel;
    DBController controller;
    public AddNewPics(){
        super("Add New Photos");
        this.setSize(700, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLayout(new BorderLayout(0, 0));

        Font font = new Font("Segoe UI", Font.BOLD, 15);

        Lcource = new JLabel("Course");
        Lname = new JLabel("Name");
        Lrno = new JLabel("Roll Number");
        Lsid = new JLabel("ID");
        Vname = new JLabel("");
        Vcource = new JLabel("");
        Vrno = new JLabel("");

        Lcource.setFont(font);
        Lname.setFont(font);
        Lrno.setFont(font);
        Lsid.setFont(font);
        Lname.setFont(font);
        Lrno.setFont(font);
        Vname.setFont(font);
        Vcource.setFont(font);
        Vrno.setFont(font);

        try {
            controller = new DBController();
            ResultSet rs = controller.getStudents();
            while (rs.next()){
                String name = rs.getString(3);
                Lnames.add(name);
                String cource = rs.getString(5);
                Lcources.add(cource);
                int id = rs.getInt(1);
                Lsids.add(id);
                int rno = rs.getInt(2);
                Lrnos.add(rno);
                Ldetails.add(name+" ::: "+cource);
            }
            Csid.setModel(new DefaultComboBoxModel(Ldetails.toArray()));
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        int index = Csid.getSelectedIndex();
        String name = Lnames.get(index);
        String cource = Lcources.get(index);
        int rno = Lrnos.get(index);

        Vname.setText(name);
        Vcource.setText(cource);
        Vrno.setText(""+rno);

        Csid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = Csid.getSelectedIndex();
                String name = Lnames.get(index);
                String cource = Lcources.get(index);
                int rno = Lrnos.get(index);

                Vname.setText(name);
                Vcource.setText(cource);
                Vrno.setText(""+rno);
            }
        });

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
        formPanel.add(Lsid, gbc_lbl);

        GridBagConstraints gbc_txt = new GridBagConstraints();
        gbc_txt.insets = new Insets(0, 0, 5, 0);
        gbc_txt.fill = GridBagConstraints.HORIZONTAL;
        gbc_txt.gridx = 1;
        gbc_txt.gridy = 0;
        formPanel.add(Csid, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 1;
        formPanel.add(Lrno, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 1;
        formPanel.add(Vrno, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 2;
        formPanel.add(Lname, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 2;
        formPanel.add(Vname, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 3;
        formPanel.add(Lcource, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 3;
        formPanel.add(Vcource, gbc_txt);

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
                        Calendar calobj = Calendar.getInstance();
                        String fname = df.format(calobj.getTime());
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
                int sid = Lsids.get(Csid.getSelectedIndex());
                System.out.println(sid);
                for(int i=0;i<5;i++){
                    File f = new File("face_images", imageName[i]+".png");
                    try {
                        ImageIO.write(faceImgs[i], "png", f);
                        controller.insertPhotos(sid, f.getName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                JOptionPane.showMessageDialog(null, "New photos of student `"+Vname.getText()+" is added to database.`", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        this.add(imagePanel);
    }
}
