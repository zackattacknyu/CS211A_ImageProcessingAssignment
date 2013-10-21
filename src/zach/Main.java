package zach;

import java.util.ArrayList;
import java.util.List;

import samples.MyImageReader;

public class Main {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "kitty"; 
		
		String imageFileNameToUse = "sampleImages/" + imageFileName + ".jpg";
		computeGaussianPyramidImages(imageFileName,imageFileNameToUse);
	}
	
	public static void makeImages(int[][][] imageData, List<int[][]> imageMatrices, 
			String folderName, String imageName, String originalImageName){
		String newFileName;
		for(int imageNumber = 0; imageNumber < imageMatrices.size(); imageNumber++){
			newFileName = folderName + "/" + imageName + "_" + imageNumber + ".jpg";
			imageData[0] = imageMatrices.get(imageNumber);
			ZachImageWriter.writeImageUsingImageSize(originalImageName, newFileName, imageData);
		}
	}
	
	public static void computeGaussianPyramidImages(String imageFileName, String imageFileNameToUse){
		String newFileName;
		
		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(imageFileNameToUse);
		
		int[][] grayscaleChannelData = imageData[0];
		
		Pyramids thePyramid = Pyramids.generatePyramids(grayscaleChannelData);
		
		//generates the reduced size images
		makeImages(imageData,thePyramid.getReducedSizeLevels(),"pyramidReducedSizeImages",
				imageFileName + "_withGaussianPyramid",imageFileNameToUse);
		
		//generates the same size images
		makeImages(imageData,thePyramid.getSameSizeLevels(),"pyramidSameSizeImages",
				imageFileName + "_withGaussianPyramid",imageFileNameToUse);
		
		//generates the laplacian images
		makeImages(imageData,thePyramid.getLaplacianLevels(),"laplacianImages",
				imageFileName + "_withLaplacianPyramid",imageFileNameToUse);
		
		//generates an edge detection image
		//variance is between 0 and 585,225 for an individual pixel
		int threshold = 20;
		
		ArrayList<int[][]> edgeDerivations = EdgeDetectionKevin.computeEdgePyramid(grayscaleChannelData);
		
		ArrayList<int[][]> rawDerivatives = EdgeDetectionKevin.computeRawDerivates(grayscaleChannelData);
		
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
	

	


}
