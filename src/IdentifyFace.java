import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.opencv.core.Core;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.opencv.highgui.Highgui.CV_LOAD_IMAGE_GRAYSCALE;

//import org.opencv.core.Mat;
// import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
// import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;

/**
 * Created by TERMINAL on 2/2/2017.
 */
public class IdentifyFace {
    List<String> imageNames = new ArrayList<String>();
    int predictedIndex;
    public IdentifyFace(){
    }
    public double detectPerson(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        opencv_core.Mat resizeimage = new opencv_core.Mat();
        org.bytedeco.javacpp.opencv_core.Size sz = new org.bytedeco.javacpp.opencv_core.Size(58, 58);

        String trainingDir = "face_images";
        opencv_core.Mat testImage = imread("Img.png", CV_LOAD_IMAGE_GRAYSCALE);

        org.bytedeco.javacpp.opencv_imgproc.resize(testImage, resizeimage, sz);
        //testImage = resizeimage;

        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".bmp") || name.endsWith(".png") || name.endsWith(".pgm") || name.endsWith(".sad");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);

        MatVector images = new MatVector(imageFiles.length);

        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();

        int counter = 0;

        for (File image : imageFiles) {
            opencv_core.Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);//CV_HAAR_SCALE_IMAGE //CV_LOAD_IMAGE_GRAYSCALE
            org.bytedeco.javacpp.opencv_imgproc.resize(img, resizeimage, sz);
            //img = resizeimage;

            imageNames.add(image.getName());

            int label = counter;//Integer.parseInt(image.getName().split("\\-")[1].split("\\.")[0]);

            images.put(counter, img);

            labelsBuf.put(counter, label);

            counter++;
        }

        //FaceRecognizer faceRecognizer = createFisherFaceRecognizer(); // Threshold => 1400
        //FaceRecognizer faceRecognizer = createEigenFaceRecognizer(); // Threshold => 2200
        FaceRecognizer faceRecognizer = createLBPHFaceRecognizer(); // Threshold => 90

        faceRecognizer.train(images, labels);

        IntPointer i = new IntPointer(0);
        DoublePointer d = new DoublePointer(0);

        faceRecognizer.predict(testImage, i, d);

        predictedIndex = faceRecognizer.predict(testImage);
        //int predictedLabel = faceRecognizer.predict(testImage);
        System.out.println("Predicted label: " + imageNames.get(predictedIndex)  + " Threa: " + d.get());
        return d.get();
    }
    public String getImageName(){
        return imageNames.get(predictedIndex);
    }
}
