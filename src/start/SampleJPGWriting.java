package start;

import java.io.IOException;

import samples.MyImageReader;
import samples.MyImageWriter;

public class SampleJPGWriting {

	/*
	 * This takes in an image and writes
	 * 		lines on it randomly.  
	 */
	public static void main(String[] args) throws IOException {
		//this will vary depending on which image we care about
		String imageFileName = "CARTOON"; 
		
		//these are the file names
		String originalFileName = "sampleImages/" + imageFileName + ".jpg";
		String newFileName = "playgroundImages/" + imageFileName + "_withLines.jpg";
		
		//this is used for probability that a line will be made all one color
		double probDenominator = 400;
		double probNumerator = 53;
		
		//this is the color to write
		int lineColor = 100;
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(originalFileName);
		for(int channelNo = 0; channelNo < imageData.length; channelNo++){
			for(int rowNum = 0; rowNum < imageData[channelNo].length; rowNum++){
				//this randomizes which lines are affected
				if((Math.random()*probDenominator) < probNumerator){ 
					for(int colNum = 0; colNum < imageData[channelNo][rowNum].length; colNum++){
						imageData[channelNo][rowNum][colNum] = lineColor;
					}
				}
				
			}
		}

		//this writes the image using the image data
		MyImageWriter.writeImage(originalFileName, newFileName, imageData);

	}

}
