package zach;

import java.util.ArrayList;
import java.util.List;

public class GaussianPyramid {

	List<int[][]> reducedSizeLevels;
	List<int[][]> sameSizeLevels;
	
	public GaussianPyramid(int[][] channelData){
		reducedSizeLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
		sameSizeLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
	}
	
	public static int[][] computeNextReducedSizeLevel(int[][] channelData){
		int[][] outputImage = new int[channelData.length/2][channelData[0].length/2];
		double pixel;
		for(int outputRow = 0; outputRow < outputImage.length; outputRow++){
			for(int outputCol = 0; outputCol < outputImage[0].length; outputCol++){
				
				//computes an individual pixel in the output image
				pixel = channelData[outputRow*2][outputCol*2] + 
						channelData[outputRow*2][outputCol*2 + 1] + 
						channelData[outputRow*2 + 1][outputCol*2] + 
						channelData[outputRow*2 + 1][outputCol*2 + 1];
				pixel = pixel/4; //averages the 4 pixels
				
				//records the output
				outputImage[outputRow][outputCol] = (int)pixel;
				
			}
		}
		return outputImage;
	}
	
}
