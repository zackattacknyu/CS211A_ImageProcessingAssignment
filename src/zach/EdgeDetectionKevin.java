package zach;

import java.util.ArrayList;
import java.util.List;

public class EdgeDetectionKevin {
	
	
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
