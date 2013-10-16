package start;

import java.io.File;


//this illustrates how we will reference the JPG files in the sampleImages folder
public class SampleFileReferencing {

	public static void main(String[] args){
		File someImage = new File("sampleImages/CARTOON.jpg");
		System.out.println("My File is " + someImage.length() + " bytes");
	}
}
