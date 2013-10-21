package zach;

import java.util.ArrayList;
import java.util.List;

import samples.MyImageReader;

public class Main {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "CARTOON"; 
		
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
		
		//generates an edge detection image
		//variance is between 0 and 585,225 for an individual pixel
		int threshold = 20;
		
		ArrayList<int[][]> edgeDerivations = computeEdgePyramid(grayscaleChannelData);
		
		ArrayList<int[][]> rawDerivatives = computeRawDerivates(grayscaleChannelData);
		
		for (int i = 0; i < edgeDerivations.size(); i++) {
			newFileName = "edgeImages/" + imageFileName + "_withVarianceThreshold_" 
					+ i + ".jpg";
			
			imageData[0] = EdgeDetectionZach.generatedEdgeImage(edgeDerivations.get(i), threshold);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
		
		// Edge derivative images
		for (int i = 0; i < edgeDerivations.size(); i++) {
			newFileName = "edgeImages/" + imageFileName + "_edgeDerivatives_" 
					+ i + ".jpg";
			
			imageData[0] = edgeDerivations.get(i);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
		
		// Raw derivative images
		for (int i = 0; i < edgeDerivations.size(); i++) {
			newFileName = "edgeImages/" + imageFileName + "_rawDerivatives_" 
					+ i + ".jpg";
			
			imageData[0] = rawDerivatives.get(i);
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}
		
		// Zero Crossings
		for (int i = 0; i < edgeDerivations.size(); i++) {
			newFileName = "edgeImages/" + imageFileName + "_withZeroCrossings_" 
					+ i + ".jpg";
			
			imageData[0] = EdgeDetectionZach.generateZeroCrossingImage(edgeDerivations.get(i));
			ZachImageWriter.writeImageUsingImageSize(imageFileNameToUse, newFileName, imageData);
		}


	}
	
	public static ArrayList<int[][]> computeEdgePyramid(int[][] grayscaleChannelData) {
		int positiveThreshVal = 255;


		Pyramids thePyramid = Pyramids.generatePyramids(grayscaleChannelData);
		List<int[][]> images = thePyramid.getReducedSizeLevels();
		ArrayList<int[][]> theEdgeDerivativeResults = new ArrayList<int[][]>();

		for (int imageNumber = 0; imageNumber < images.size(); imageNumber++) {
			
			int[][] currentData = images.get(imageNumber);

			float[][] edgeFilter = Convolve.edgeFilter();
			int[][] resultImage = Convolve.apply3by3Filter(
					currentData, edgeFilter);
			for (int row = 0; row < resultImage.length; row++) {
				for (int column = 0; column < resultImage[0].length; column++) {
					if (resultImage[row][column] < 0) {
						resultImage[row][column] = 0;
					} else {
						resultImage[row][column] = positiveThreshVal;
					}
				}
			}

			theEdgeDerivativeResults.add(resultImage);
		}

		return theEdgeDerivativeResults;

	}
	
	public static ArrayList<int[][]> computeRawDerivates(int[][] grayscaleChannelData) {

		Pyramids thePyramid = Pyramids.generatePyramids(grayscaleChannelData);
		List<int[][]> images = thePyramid.getReducedSizeLevels();
		ArrayList<int[][]> theEdgeDerivativeResults = new ArrayList<int[][]>();

		for (int imageNumber = 0; imageNumber < images.size(); imageNumber++) {
			
			int[][] currentData = images.get(imageNumber);

			float[][] edgeFilter = Convolve.edgeFilter();
			int[][] resultImage = Convolve.apply3by3Filter(
					currentData, edgeFilter);

			theEdgeDerivativeResults.add(resultImage);
		}

		return theEdgeDerivativeResults;

	}
	


}
