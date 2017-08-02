
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 * Created by TERMINAL on 12/30/2016.
 */
public class FaceDetectionThread implements Runnable {
    JPanel videoPanel;
    VideoCapture webCam = null;
    MatOfByte mem = new MatOfByte();
    CascadeClassifier haar = new CascadeClassifier(this.getClass().getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
    Mat frame = new Mat();
    MatOfRect faceRect = new MatOfRect();
    volatile boolean runnable = false;
    BufferedImage face = null;
    MainFrame mainFame = null;
    boolean recogniize;
    FaceDetectionThread(JPanel jpanel, MainFrame fame){
        videoPanel = jpanel;
        mainFame =fame;
        recogniize = true;
    }
    FaceDetectionThread(JPanel jpanel){
        videoPanel = jpanel;
        recogniize = false;
    }
    IdentifyFace identifyFace = new IdentifyFace();
    @Override
    public void run() {
        synchronized (this) {
            while (runnable) {
                if (webCam.grab()) {
                    try {
                        webCam.retrieve(frame);
                        Graphics g = videoPanel.getGraphics();
                        haar.detectMultiScale(frame, faceRect);
                        
                        Highgui.imencode(".bmp", frame, mem);
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                        BufferedImage buff = (BufferedImage) im;
                        if (g.drawImage(buff, 0, 0, videoPanel.getWidth(), videoPanel.getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                            if (!runnable) {
                                this.wait();
                            }
                        }
                        for (Rect rect : faceRect.toArray()) {
                            //Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0));
                            //System.out.println("Got Face");
                            face = buff.getSubimage(rect.x, rect.y, rect.width, rect.height);
                            File f = new File("Img.png");
                            ImageIO.write(face, "png", f);
                            if(recogniize) {
                                try {
                                    LBPH lbph = new LBPH(1, 256, ImageIO.read(f));
                                    ImageData imageData = lbph.getNearestImage();
                                    System.out.println(imageData.name + ":" + imageData.distance);
                                    if (imageData.distance < 30) {
                                        mainFame.FaceIdentified(imageData.name);
                                    }
                                    /*double threashold = identifyFace.detectPerson();
                                    if (threashold < 80) {
                                        String image = identifyFace.getImageName();
                                        mainFame.FaceIdentified(image);
                                    }*/
                                } catch (Exception e) {

                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
