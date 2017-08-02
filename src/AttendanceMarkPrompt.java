import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by TERMINAL on 2/3/2017.
 */
public class AttendanceMarkPrompt extends JFrame {
    int sid;
    JLabel Lhead, Lname, Lrno, Lnamev, Lrnov, Lmsg;
    JPanel topPanel, middlePanel, bottomPanel;
    GridBagLayout gridForm = new GridBagLayout();
    Font font, ifont, sfont;
    public AttendanceMarkPrompt(int sid, int rno, String name){
        super("Successfully Marked Your Attendance");
        this.setSize(600, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setLayout(new BorderLayout(0, 0));

        font = new Font("Chiller", Font.BOLD, 25);
        ifont = new Font("Chiller", Font.BOLD, 28);
        sfont = new Font("Chiller", Font.BOLD, 20);

        this.sid = sid;
        Lhead = new JLabel("Attendance Marked");
        Lname  = new JLabel("Name: ");
        Lrno = new JLabel("Roll Number: ");
        Lhead.setFont(new Font("Chiller", Font.BOLD, 30));
        Lname.setFont(font);
        Lrno.setFont(font);
        Lhead.setForeground(Color.BLUE);
        Lname.setForeground(Color.RED);
        Lrno.setForeground(Color.RED);
        Lnamev = new JLabel(name);
        Lnamev.setForeground(Color.BLUE);
        Lrnov = new JLabel(""+rno);
        Lrnov.setForeground(Color.BLUE);
        Lnamev.setFont(ifont);
        Lrnov.setFont(ifont);
        topPanel = new JPanel();
        middlePanel = new JPanel();
        bottomPanel = new JPanel();
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        topPanel.add(Lhead);
        Lhead.setHorizontalAlignment(JLabel.CENTER);

        gridForm.columnWidths = new int[]{100, 100};
        gridForm.rowHeights = new int[]{20, 20, 20};
        gridForm.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridForm.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        middlePanel.setBorder(new EmptyBorder(5, 5, 10, 10));
        middlePanel.setLayout(gridForm);

        GridBagConstraints gbc_lbl = new GridBagConstraints();
        gbc_lbl.insets = new Insets(0, 0, 5, 5);
        gbc_lbl.anchor = GridBagConstraints.EAST;
        //gbc_lbl.fill = GridBagConstraints.NONE;
        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 0;
        middlePanel.add(Lname, gbc_lbl);

        GridBagConstraints gbc_txt = new GridBagConstraints();
        gbc_txt.insets = new Insets(0, 0, 5, 0);
        gbc_lbl.anchor = GridBagConstraints.WEST;
        gbc_txt.fill = GridBagConstraints.HORIZONTAL;
        gbc_txt.gridx = 1;
        gbc_txt.gridy = 0;
        middlePanel.add(Lnamev, gbc_txt);

        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = 1;
        gbc_lbl.anchor = GridBagConstraints.EAST;
        middlePanel.add(Lrno, gbc_lbl);

        gbc_txt.gridx = 1;
        gbc_txt.gridy = 1;
        gbc_lbl.anchor = GridBagConstraints.WEST;
        gbc_txt.fill = GridBagConstraints.HORIZONTAL;
        middlePanel.add(Lrnov, gbc_txt);

        Lmsg = new JLabel("*If the system fails to detect you contact system admin");
        Lmsg.setFont(sfont);
        Lmsg.setForeground(Color.RED);
        Lmsg.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(Lmsg);

        topPanel.setForeground(Color.WHITE);
        middlePanel.setForeground(Color.WHITE);
        bottomPanel.setForeground(Color.WHITE);
    }
}
