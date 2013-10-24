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
		ArrayList<int[][]> segmentedImages = EdgeDetectionKevin.computeEdgePyramid(grayscaleChannelData);
		makeImages(imageData,segmentedImages,"segmentedImages",imageFileName + "_segmented",imageFileNameToUse);
		
		/*
		 * these are the zero crossing images as found for Part 3, Step 3	
		 */
		ArrayList<int[][]> zeroCrossingImages = new ArrayList<int[][]>(segmentedImages.size());
		ArrayList<boolean[][]> zeroCrossingDatas = new ArrayList<boolean[][]>(segmentedImages.size());
		for(int[][] segmentedImage: segmentedImages){
			zeroCrossingImages.add(EdgeDetectionZach.generateZeroCrossingImage(segmentedImage));
			zeroCrossingDatas.add(EdgeDetectionZach.generateZeroCrossingData(segmentedImage));
		}
		makeImages(imageData,zeroCrossingImages,"zeroCrossingImages",imageFileName + "_zeroCrossings",imageFileNameToUse);
		
		/*
		 * these are the edge Detection Images as found for Part 3, Step 4
		 * variance is between 0 and 585,225 for an individual pixel
		 */
		ArrayList<int[][]> edgeDetectionImages;
		for(int threshold = 100; threshold <= 500; threshold += 100){
			edgeDetectionImages = new ArrayList<int[][]>(segmentedImages.size());
			for(int index = 0; index < segmentedImages.size(); index++){
				edgeDetectionImages.add(EdgeDetectionZach.generatedEdgeImage(rawDerivatives.get(index), zeroCrossingDatas.get(index), threshold));
			}
			makeEdgeDetectionImages(imageData,edgeDetectionImages,"edgeDetectionImages",
					imageFileName,imageFileNameToUse,threshold);
		}
		
	}	
	
	public static void makeEdgeDetectionImages(int[][][] imageData, List<int[][]> imageMatrices, 
			String folderName, String imageName, String originalImageName, int threshold){
		String newFileName;
		for(int imageNumber = 0; imageNumber < imageMatrices.size(); imageNumber++){
			newFileName = folderName + "/" + imageName + "_" + imageNumber + "_withVarianceThreshold_" + threshold + ".jpg";
			imageData[0] = imageMatrices.get(imageNumber);
			ZachImageWriter.writeImageUsingImageSize(originalImageName, newFileName, imageData);
		}
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
