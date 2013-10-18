package zach;

import samples.MyImageReader;
import samples.MyImageWriter;

public class Main {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "text"; 
		
		//these are the file names
		String originalFileName = "sampleImages/" + imageFileName + ".jpg";
		String newFileName = "filteredImages/" + imageFileName + "_withBasicFilter.jpg";
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(originalFileName);
		
		int[][] grayscaleChannelData = imageData[0];
		
		int[][] newGrayscaleData = Convolve.apply3by3Filter(grayscaleChannelData, Convolve.basicFilter());
		
		imageData[0] = newGrayscaleData;
		
		//this writes the image using the image data
		MyImageWriter.writeImage(originalFileName, newFileName, imageData);

	}

}
