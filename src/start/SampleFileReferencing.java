package start;

import java.io.File;


//this illustrates how we will reference the JPG files in the sampleImages folder
public class SampleFileReferencing {

	public static void main(String[] args){
		//this will vary depending on which image we care about
		String imageFileName = "CARTOON.jpg"; 
		
		//this is what to use for the file name if that is a a parameter
		String fileNameForArgument = "sampleImages/" + imageFileName;
		
		//here is an example use of the file
		File someImage = new File(fileNameForArgument);
		System.out.println("My File is " + someImage.length() + " bytes");
	}
}
