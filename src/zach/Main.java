package zach;

import samples.MyImageReader;
import samples.MyImageWriter;

public class Main {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "CARTOON"; 
		
		//these are the file names
		String originalFileName = "sampleImages/" + imageFileName + ".jpg";
		String newFileName = "filteredImages/" + imageFileName + "_withGaussianPyramid_1.jpg";
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(originalFileName);
		
		int[][] grayscaleChannelData = imageData[0];
		
		//basic Convolution as example
		//int[][] newGrayscaleData = Convolve.apply3by3Filter(grayscaleChannelData, Convolve.basicFilter());
		
		int[][] newGrayscaleData = GaussianPyramid.computeNextReducedSizeLevel(grayscaleChannelData);
		
		imageData[0] = newGrayscaleData;
		
		//this writes the image using the image data
		ZachImageWriter.writeImageUsingImageSize(originalFileName, newFileName, imageData);
		//MyImageWriter.writeImage(originalFileName, newFileName, imageData);

	}

}
