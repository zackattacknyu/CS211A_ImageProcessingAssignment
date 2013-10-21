package zach;

import java.util.ArrayList;
import java.util.List;

import samples.MyImageReader;

public class Main {

	public static void main(String[] args) {
		//this will vary depending on which image we care about
		String imageFileName = "kitty"; 
		
		String imageFileNameToUse = "sampleImages/" + imageFileName + ".jpg";
		computeAllImages(imageFileName,imageFileNameToUse);
	}

	public static void computeAllImages(String imageFileName, String imageFileNameToUse){

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
		
		ArrayList<int[][]> zeroCrossingImages = new ArrayList<int[][]>(edgeDerivations.size());
		
		ArrayList<int[][]> edgeDetectionImages = new ArrayList<int[][]>(edgeDerivations.size());
		
		for(int[][] edgeDerivation: edgeDerivations){
			zeroCrossingImages.add(EdgeDetectionZach.generateZeroCrossingImage(edgeDerivation));
		}
		
		for(int[][] edgeDerivation: edgeDerivations){
			edgeDetectionImages.add(EdgeDetectionZach.generatedEdgeImage(edgeDerivation, threshold));
		}
		
		//generate the edge derivative images
		makeImages(imageData,edgeDerivations,"edgeDerivations",imageFileName + "_edgeDerivatives",imageFileNameToUse);
		
		//generate the raw derivative images
		makeImages(imageData,rawDerivatives,"rawDerivatives",imageFileName + "_rawDerivatives",imageFileNameToUse);
		
		//generate the zero crossing images
		makeImages(imageData,zeroCrossingImages,"zeroCrossingImages",imageFileName + "_zeroCrossings",imageFileNameToUse);
		
		//generate the edge detection images
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
