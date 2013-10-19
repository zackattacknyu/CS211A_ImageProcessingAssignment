package zach;

import java.util.ArrayList;

import samples.MyImageReader;

public class Main {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "kitty"; 
		
		String imageFileNameToUse = "sampleImages/" + imageFileName + ".jpg";
		computeGaussianPyramidImages(imageFileName,imageFileNameToUse);
	}
	
	public static void computeGaussianPyramidImages(String imageFileName, String imageFileNameToUse){
		String newFileName;
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(imageFileNameToUse);
		
		int[][] grayscaleChannelData = imageData[0];
		
		GaussianPyramid thePyramid = GaussianPyramid.generatePyramids(grayscaleChannelData);
		ArrayList<int[][]> theImageDatas = (ArrayList<int[][]>) thePyramid.getReducedSizeLevels();
		
		for(int imageNumber = 0; imageNumber < theImageDatas.size(); imageNumber++){
			newFileName = "pyramidReducedSizeImages/" + imageFileName + "_withGaussianPyramid_" 
					+ imageNumber + ".jpg";
			
			imageData[0] = theImageDatas.get(imageNumber);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
	}
	
	public static void computeNextReducedSizeImage(String imageFileName, 
			String lastFileName, int nextImageNumber){
		
		String newFileName = "pyramidReducedSizeImages/" + imageFileName + "_withGaussianPyramid_" 
				+ nextImageNumber + ".jpg";
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(lastFileName);
		
		int[][] grayscaleChannelData = imageData[0];
		
		if(grayscaleChannelData.length < 2){
			return;
		}

		int[][] newGrayscaleData = GaussianPyramid.computeNextReducedSizeLevel(grayscaleChannelData);
		
		imageData[0] = newGrayscaleData;

		ZachImageWriter.writeImageUsingImageSize(lastFileName, newFileName, imageData);
		
		computeNextReducedSizeImage(imageFileName,newFileName,nextImageNumber+1);
	}

}
