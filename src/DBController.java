import org.bytedeco.javacpp.presets.opencv_core;

import javax.swing.*;
import java.sql.*;

/**
 * Created by TERMINAL on 2/2/2017.
 */
public class DBController {
    Connection conn;
    public DBController() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance_system", "root", "");
    }
    public int insertData(int rno, String name, String gender, Date dob, int cid){
        try{
            String qry = "INSERT INTO `student`(`rno`, `name`, `gender`, `date_of_birth`, `cid`) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, rno);
            pst.setString(2, name);
            pst.setString(3, gender);
            pst.setDate(4, dob);
            pst.setInt(5, cid);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }
    public ResultSet getCourceList(){
        try{
            String qry = "SELECT * FROM `cource`";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry);
            return rs;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public int insertPhotos(int sid, String name){
        try{
            String qry = "INSERT INTO `photos`(`photo`, `sid`) VALUES (?,?)";
            PreparedStatement pst = conn.prepareStatement(qry);
            pst.setString(1, name);
            pst.setInt(2, sid);
            return pst.executeUpdate();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public ResultSet getStudent(String name){
        try{
            String qry = "SELECT photos.sid, student.name, student.rno FROM photos, student WHERE photos.photo IN(?) AND photos.sid=student.sid";
            PreparedStatement pst = conn.prepareStatement(qry);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            return  rs;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public ResultSet getStudents(){
        try{
            String qry = "SELECT student.sid, student.rno, student.name, student.cid, cource.name FROM student, cource WHERE student.cid=cource.cid";
            PreparedStatement pst = conn.prepareStatement(qry);
            ResultSet rs = pst.executeQuery();
            return  rs;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
