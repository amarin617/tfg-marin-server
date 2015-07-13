package marin.tfg.server.libprovider;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {
	  public static byte[] process(byte[] data) {
		  
	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	        System.out.println("\nRunning FaceDetector");
	 
	        CascadeClassifier faceDetector = new CascadeClassifier(FaceDetector.class.getResource("haarcascade_frontalface_alt.xml").getPath());
	        Mat image = new Mat();
	        image.put(0, 0, data);
	 
	        MatOfRect faceDetections = new MatOfRect();
	        faceDetector.detectMultiScale(image, faceDetections);
	 
	        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	 
	        for (Rect rect : faceDetections.toArray()) {
	        	Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	        }
	 
	        byte[] return_buff = new byte[(int) (image.total() * 
	        		image.channels())];
	        image.get(0, 0, return_buff);
	        
	        return return_buff;
	    }
}
