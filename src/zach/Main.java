package zach;

import java.util.ArrayList;
import java.util.List;

import samples.MyImageReader;

public class Main {

	public static void main(String[] args) {
		
		/*
		 * IMPORTANT LINE:
		 * Change this to vary the image that is currently being processed
		 */
		String imageFileName = "CARTOON"; 
		
		String imageFileNameToUse = "sampleImages/" + imageFileName + ".jpg";
		computeAllImages(imageFileName,imageFileNameToUse);
	}

	public static void computeAllImages(String imageFileName, String imageFileNameToUse){

		//this is used to generate the image data
		int[][][] imageData = MyImageReader.readImageInto2DArray(imageFileNameToUse);
		
		int[][] grayscaleChannelData = imageData[0];
		
		/*
		 * This function computes the Gaussian and Laplacian Pyramids
		 * 		for Part 1 and Part 2
		 */
		Pyramids thePyramid = Pyramids.generatePyramids(grayscaleChannelData);
		
		/*
		 * This is used to make all the reduced size images
		 * 	for Part 1, Step 1
		 */
		makeImages(imageData,thePyramid.getReducedSizeLevels(),"pyramidReducedSizeImages",
				imageFileName + "_withGaussianPyramid",imageFileNameToUse);
		
		/*
		 * This is used to make all the same size images
		 * 	for Part 1, Step 2
		 */
		makeImages(imageData,thePyramid.getSameSizeLevels(),"pyramidSameSizeImages",
				imageFileName + "_withGaussianPyramid",imageFileNameToUse);
		
		/*
		 * This makes the laplacian images for Part 2
		 */
		makeImages(imageData,thePyramid.getLaplacianLevels(),"laplacianImages",
				imageFileName + "_withLaplacianPyramid",imageFileNameToUse);
		
		
		/*
		 * these are the raw derivatives as found for Part 3, Step 1
		 */
		ArrayList<int[][]> rawDerivatives = EdgeDetectionKevin.computeRawDerivates(grayscaleChannelData);
		makeImages(imageData,rawDerivatives,"rawDerivatives",imageFileName + "_rawDerivatives",imageFileNameToUse);
		
		/*
		 * these are the segmented images as found for Part 3, Step 2
		 */
		ArrayList<int[][]> edgeDerivations = EdgeDetectionKevin.computeEdgePyramid(grayscaleChannelData);
		makeImages(imageData,edgeDerivations,"edgeDerivations",imageFileName + "_edgeDerivatives",imageFileNameToUse);
		
		/*
		 * these are the zero crossing images as found for Part 3, Step 3	
		 */
		ArrayList<int[][]> zeroCrossingImages = new ArrayList<int[][]>(edgeDerivations.size());
		for(int[][] edgeDerivation: edgeDerivations){
			zeroCrossingImages.add(EdgeDetectionZach.generateZeroCrossingImage(edgeDerivation));
		}
		makeImages(imageData,zeroCrossingImages,"zeroCrossingImages",imageFileName + "_zeroCrossings",imageFileNameToUse);
		
		/*
		 * these are the edge Detection Images as found for Part 3, Step 4
		 * variance is between 0 and 585,225 for an individual pixel
		 */
		int threshold = 10000;
		ArrayList<int[][]> edgeDetectionImages = new ArrayList<int[][]>(edgeDerivations.size());
		for(int[][] edgeDerivation: edgeDerivations){
			edgeDetectionImages.add(EdgeDetectionZach.generatedEdgeImage(edgeDerivation, threshold));
		}
		makeImages(imageData,edgeDetectionImages,"edgeDetectionImages",
				imageFileName + "_edgeDetectionWithVarianceThreshold" + threshold,imageFileNameToUse);

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
	

	

	


}
