import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TERMINAL on 2/12/2017.
 */
public class LBPH {
    int radius, numPattern;
    BufferedImage image;
    List<Integer> lookUp = new ArrayList<Integer>();
    LBPH(int radius, int numPattern, BufferedImage image){
        this.radius = radius;
        this.numPattern = numPattern;
        this.image = image;
        //System.out.println("Okey");
        initLookUp();
        /*try {
            this.image = ImageIO.read(new File("face_images\\06-02-17-13-19-19.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    ImageData getNearestImage(){
        File root = new File("face_images");
        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".bmp") || name.endsWith(".png") || name.endsWith(".pgm") || name.endsWith(".sad");
            }
        };
        //Mat hist = ELBP(src, 1);

        int min = 0;
        double dist = 0;
        String minDist = "";

        try {
            BufferedImage src = gaussianBlurPlusGrayscale(image);

            src = ELBP(src, 1);//LBP(src);//ELBP(src, 1);//

            src = normalizeImage(src, 60);
            double hist[] = getHistogram(src, 10, 10, 60);
            File[] imageFiles = root.listFiles(imgFilter);

            BufferedImage img = ImageIO.read(imageFiles[0]);
            img = gaussianBlurPlusGrayscale(img);
            img = ELBP(img, 1);//LBP(img);//ELBP(img, 1);//
            img = normalizeImage(img, 60);

            minDist = imageFiles[0].getName();
            dist = ChiSquare(hist, getHistogram(img, 10, 10, 60));
            //System.out.println(imageFiles[0].getName()+":::"+dist);
            for(int i=1;i<imageFiles.length;i++){
                img = ImageIO.read(imageFiles[i]);
                img = gaussianBlurPlusGrayscale(img);
                img = ELBP(img, 1);//LBP(img);//ELBP(img, 1);//
                img = normalizeImage(img, 60);
                double d = ChiSquare(hist, getHistogram(img, 10, 10, 60));
                if(d<dist){
                    dist = d;
                    minDist = imageFiles[i].getName();
                }
                //System.out.println(imageFiles[i].getName()+":::"+d);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return new ImageData(minDist, min, dist);
    }

    BufferedImage gaussianBlurPlusGrayscale(BufferedImage src){
        for (int x = 0; x < src.getWidth(); ++x)
            for (int y = 0; y < src.getHeight(); ++y)
            {
                int rgb = src.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int k = (int)(0.56*g + 0.33*r + 0.11*b);
                int gray = (0xff000000 | k<<16 | k<<8 | k);
                src.setRGB(x, y, gray);
            }
        return src;
    }

    BufferedImage ELBP(BufferedImage src, int r){
        //System.out.println(src.getWidth()+":::"+src.getHeight());
        int neighbour = 8 * r;//max(min(neighbour, 31), 1);
        BufferedImage padd = new BufferedImage(src.getWidth()+2, src.getHeight()+2, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster pa = padd.getRaster();
        WritableRaster wr = src.getRaster();
        for(int i=1;i<wr.getWidth()+1;i++){
            for(int j=1;j<wr.getHeight()+1;j++){
                pa.setSample(i, j, 0, wr.getSample(i-1, j-1, 0));
            }
        }
        padd.setData(pa);
        wr = padd.getRaster();
        BufferedImage nImg = new BufferedImage(padd.getWidth() - 2 * r, padd.getHeight() - 2 * r, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster dest = nImg.getRaster();
        float fr = (float) r;
        float fn = (float) neighbour;
        for (int n = 0; n < neighbour; n++) {
            float x = (float) (fr * -Math.sin(2.0 * Math.PI * n / fn));
            float y = (float) (fr * Math.cos(2.0 * Math.PI * n / fn));

            int fx = (int) Math.floor(x);
            int fy = (int) Math.floor(y);
            int cx = (int) Math.ceil(x);
            int cy = (int) Math.ceil(y);

            float ty = y - fy;
            float tx = x - fx;

            float w1 = (1 - tx) * (1 - ty);
            float w2 = tx * (1 - ty);
            float w3 = (1 - tx) * ty;
            float w4 = tx * ty;
            for (int i = r; i < src.getWidth() - r; i++) {
                for (int j = r; j < src.getHeight() - r; j++) {
                    try {
                        float t = w1 * wr.getSample(i + fy, j + fx, 0) + w2 * wr.getSample(i + fy, j + cx, 0) + w3 * wr.getSample(i + cy, j + fx, 0) + w4 * wr.getSample(i + cy, j + cx, 0);
                        boolean f = (t > wr.getSample(i, j, 0));//?1<<n:0<<n;
                        int a = 0 << n;
                        if (f) {
                            a = 1 << n;
                        }
                        int b = dest.getSample(i - r, j - r, 0);
                        dest.setSample(i - r, j - r, 0, a + b);
                    } catch (Exception e) {
                        System.out.println("e1"+e);
                    }
                }
            }
        }
        for(int i=0;i<dest.getWidth();i++){
            for(int j=0;j<dest.getHeight();j++){
                dest.setSample(i, j, 0, lookUp.get(dest.getSample(i, j, 0)));
            }
        }
        nImg.setData(dest);
        return nImg;
    }
    double[] histogram(BufferedImage src, int numPatterns, int minVal, int maxVal){
        WritableRaster wr = src.getRaster();
        double[] buff = new double[numPatterns];
        for (int i = 0; i < wr.getWidth(); i++) {
            for (int j = 0; j < wr.getHeight(); j++) {
                try {
                    int bin = wr.getSample(i, j, 0);
                    buff[bin]++;
                }catch (Exception e){}
            }
        }
        for(int i=0;i<numPatterns;i++)
            buff[i] /= (src.getHeight()*src.getWidth());
        return buff;
    }
    double[] getHistogram(BufferedImage src, int cols, int rows, int numPattern){
        double data[] = new double[numPattern*cols*rows];
        try {
            java.util.List<double[]> hist = new ArrayList<double[]>();
            int dx = src.getWidth() / cols;
            int dy = src.getHeight() / rows;
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    try {
                        BufferedImage buff = src.getSubimage((i * dx), (j * dy), dx, dy);
                        hist.add(histogram(buff, numPattern, 0, numPattern));
                    }
                    catch (Exception e){
                        System.out.println("e2"+e);
                    }
                }
            }
            int k = 0;
            for (int i = 0; i < hist.size(); i++) {
                for(int j=0;j<hist.get(i).length;j++){
                    data[k++] = hist.get(i)[j];
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
    BufferedImage LBP(BufferedImage src, int x){
        double d[] = new double[256];
        BufferedImage padd = new BufferedImage(src.getWidth()+2, src.getHeight()+2, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster pa = padd.getRaster();
        WritableRaster wr = src.getRaster();
        for(int i=1;i<wr.getHeight()+1;i++){
            for(int j=1;j<wr.getWidth()+1;j++){
                pa.setSample(i, j, 0, wr.getSample(i-1, j-1, 0));
            }
        }
        padd.setData(pa);
        wr = padd.getRaster();
        BufferedImage nImg = new BufferedImage(padd.getWidth() - 2, padd.getHeight() - 2, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster dest = nImg.getRaster();
        for (int i = 1; i < src.getWidth() - 1; i++) {
            for (int j = 1; j < src.getHeight() - 1; j++) {
                try {
                    double center = wr.getSample(i, j, 0);
                    int bin = 0;
                    if((wr.getSample(i - 1, j - 1, 0) > center))
                        bin+=1<<7;
                    if((wr.getSample(i - 1, j, 0) > center))
                        bin+=1<<6;
                    if((wr.getSample(i - 1, j + 1, 0) > center))
                        bin+=1<<5;
                    if(wr.getSample(i, j + 1, 0) > center)
                        bin+=1<<4;
                    if(wr.getSample(i + 1, j + 1, 0) > center)
                        bin+=1<<3;
                    if(wr.getSample(i + 1, j, 0) > center)
                        bin+=1<<2;
                    if(wr.getSample(i + 1, j - 1, 0) > center)
                        bin+=1<<1;
                    if(wr.getSample(i, j - 1, 0) > center)
                        bin+=1<<0;
                    dest.setSample(i - 1, j - 1, 0, bin);
                }catch (Exception e){
                    System.out.println("e3"+e);
                }
            }
        }
        for (int i=0;i<dest.getHeight();i++)
            for (int j=0;j<dest.getWidth();j++)
                d[dest.getSample(i, j, 0)]++;
        for (int i=0;i<256;i++)
            System.out.print(d[i]+" ");
        System.out.println();
        nImg.setData(dest);
        return nImg;
    }

    int rightShift(int num, int shift){
        return (num>>shift)|(num<<(8-shift)&0xff);
    }
    int countBits(int code){
        int cnt;
        int v = code;
        for(cnt=0;v!=0;cnt++){
            v&=v-1;
        }
        return cnt;
    }
    boolean checkUniform(int code){
        int b = rightShift(code, 1);
        int c = code^b;
        int cnt = countBits(c);
        if(cnt<=2)
            return true;
        return false;
    }

    void initLookUp(){
        int index = 0;
        for(int i=0;i<256;i++){
            boolean stat = checkUniform(i);
            if(stat){
                lookUp.add(index);
                index++;
            }
            else{
                lookUp.add(59);
            }
        }
    }

    double ChiSquare(double[] h1, double[] h2) {
        double r = 0;
        for (int i=0;i<h1.length;i++){
            double t = h1[i]+h2[i];
            if(t!=0){
                r += Math.pow(h1[i]-h2[i], 2)/t;
            }
        }
        return r;
    }
    BufferedImage normalizeImage(BufferedImage src, int numPattern){
        double[] hist = new double[numPattern];
        WritableRaster wr = src.getRaster();
        for(int i=0;i<wr.getWidth();i++){
            for (int j=0;j<wr.getHeight();j++){
                hist[wr.getSample(i, j, 0)]++;
            }
        }
        double[] buff = new double[numPattern];
        double[] t = new double[numPattern];
        buff[0] = 0;
        for(int i=1;i<numPattern;i++){
            buff[i] = hist[i-1]+hist[i];
            t[i] = Math.round(numPattern*buff[i]/(wr.getHeight()*wr.getWidth()));
        }
        for(int i=0;i<wr.getWidth();i++){
            for (int j=0;j<wr.getHeight();j++){
                wr.setSample(i, j, 0, wr.getSample(i, j, 0)*t[wr.getSample(i, j, 0)]);
            }
        }
        src.setData(wr);
        return src;
    }
}
