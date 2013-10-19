package zach;

import java.util.List;

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
		
		Pyramids thePyramid = Pyramids.generatePyramids(grayscaleChannelData);
		List<int[][]> theImageDatas = thePyramid.getReducedSizeLevels();
		List<int[][]> theBlurryImages = thePyramid.getSameSizeLevels();
		List<int[][]> theLaplacianImages = thePyramid.getLaplacianLevels();
		
		//generates the reduced size images
		for(int imageNumber = 0; imageNumber < theImageDatas.size(); imageNumber++){
			newFileName = "pyramidReducedSizeImages/" + imageFileName + "_withGaussianPyramid_" 
					+ imageNumber + ".jpg";
			
			imageData[0] = theImageDatas.get(imageNumber);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
		
		//generates the same size images
		for(int imageNumber = 0; imageNumber < theBlurryImages.size(); imageNumber++){
			newFileName = "pyramidSameSizeImages/" + imageFileName + "_withGaussianPyramid_" 
					+ imageNumber + ".jpg";
			
			imageData[0] = theBlurryImages.get(imageNumber);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
		
		//generates the laplacian images
		for(int imageNumber = 0; imageNumber < theLaplacianImages.size(); imageNumber++){
			newFileName = "laplacianImages/" + imageFileName + "_withLaplacianPyramid_" 
					+ imageNumber + ".jpg";
			
			imageData[0] = theLaplacianImages.get(imageNumber);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
	}
	


}
