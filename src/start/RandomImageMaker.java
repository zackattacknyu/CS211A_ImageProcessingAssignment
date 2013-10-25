package start;

import samples.MyImageReader;
import samples.MyImageWriter;

public class RandomImageMaker {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "sunset"; 
		
		//these are the file names
		String originalFileName = "colorImages/" + imageFileName + ".jpg";
		String newFileName = "playgroundImages/randomColorImage.jpg";
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(originalFileName);
		for(int channelNo = 0; channelNo < imageData.length; channelNo++){
			for(int rowNum = 0; rowNum < imageData[channelNo].length; rowNum++){
				
				for(int colNum = 0; colNum < imageData[channelNo][rowNum].length; colNum++){
					imageData[channelNo][rowNum][colNum] = (int)(256*Math.random());
				}
			}
		}
		

		//this writes the image using the image data
		MyImageWriter.writeImage(originalFileName, newFileName, imageData);

	}

}
