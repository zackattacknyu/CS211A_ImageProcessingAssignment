package zach;

import java.util.ArrayList;
import java.util.List;

public class GaussianPyramid {

	List<int[][]> reducedSizeLevels;
	List<int[][]> sameSizeLevels;
	
	public GaussianPyramid(int[][] channelData){
		reducedSizeLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
		sameSizeLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
		
		reducedSizeLevels.add(channelData);
		sameSizeLevels.add(channelData);
		
		generateReducedSizeLevels(channelData);
		generateSameSizeLevels();
	}
	
	public static GaussianPyramid generatePyramids(int[][] channelData){
		return new GaussianPyramid(channelData);
	}
	
	public List<int[][]> getReducedSizeLevels() {
		return reducedSizeLevels;
	}

	public List<int[][]> getSameSizeLevels() {
		return sameSizeLevels;
	}

	/*
	 * This generates the same size images.
	 * It works by taking the reduced size images and scaling them all up
	 */
	private void generateSameSizeLevels(){
		int[][] currentLevel = reducedSizeLevels.get(0);
		int[][] referenceImage;
		int scaleFactor = 1;
		
		
		for(int levelNumber = 1; levelNumber < reducedSizeLevels.size(); levelNumber++){
			currentLevel = new int[currentLevel.length][currentLevel[0].length];
			referenceImage = reducedSizeLevels.get(levelNumber);
			scaleFactor = (int)Math.pow(2, levelNumber);
			
			for(int referenceRowNum = 0; referenceRowNum < referenceImage.length; referenceRowNum++){
				for(int referenceColNum = 0; referenceColNum < referenceImage[0].length; referenceColNum++){
					
					for(int outputRowNum = referenceRowNum*scaleFactor; 
							outputRowNum < (referenceRowNum+1)*scaleFactor; 
							outputRowNum++){
						for(int outputColNum = referenceColNum*scaleFactor; 
								outputColNum < (referenceColNum+1)*scaleFactor; 
								outputColNum++){
							
							//puts the same image into entire square
							currentLevel[outputRowNum][outputColNum] = 
									referenceImage[referenceRowNum][referenceColNum];
							
						}
					}
					
				}
			}
			
			sameSizeLevels.add(currentLevel);
		}
	}
	
	
	private void generateReducedSizeLevels(int[][] channelData){
		if(channelData.length < 2){
			return;
		}
		int[][] nextLevel = computeNextReducedSizeLevel(channelData);
		reducedSizeLevels.add(nextLevel);
		generateReducedSizeLevels(nextLevel);
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
