package zach;

import java.util.ArrayList;
import java.util.List;

public class Pyramids {

	private List<int[][]> reducedSizeLevels;
	private List<int[][]> sameSizeLevels;
	
	private List<int[][]> laplacianLevels;
	
	private int maxSize;
	
	public Pyramids(int[][] channelData){
		
		maxSize = channelData.length;
		reducedSizeLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
		sameSizeLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
		laplacianLevels = new ArrayList<int[][]>((int)Math.log(channelData.length));
		
		reducedSizeLevels.add(channelData);
		sameSizeLevels.add(channelData);
		
		generateReducedSizeLevels(channelData);
		generateSameSizeLevels();
		generateLaplacianLevels(true);
	}
	
	/**
	 * This computes the laplacian levels after the Gaussian 
	 * 		Pyramid has been found
	 * @param topMinusBottom	whether to do top level minus bottom level.
	 * 							if false, bottom level minus top is done
	 */
	private void generateLaplacianLevels(boolean topMinusBottom){
		int[][] currentLevel;
		int[][] topGaussianLevel;
		int[][] bottomGaussianLevel;
		double minPixel,maxPixel,range,currentPixel,scaledPixel;
		
		for(int level = 0; level < sameSizeLevels.size() - 1; level++){
			topGaussianLevel = sameSizeLevels.get(level);
			bottomGaussianLevel = sameSizeLevels.get(level+1);
			
			currentLevel = new int[topGaussianLevel.length][topGaussianLevel[0].length];
			
			//generates the raw pixel values
			for(int rowIndex = 0; rowIndex < topGaussianLevel.length; rowIndex++){
				for(int colIndex = 0; colIndex < topGaussianLevel[0].length; colIndex++){
					currentLevel[rowIndex][colIndex] = 
							topGaussianLevel[rowIndex][colIndex] - 
							bottomGaussianLevel[rowIndex][colIndex];
					
					if(!topMinusBottom){
						currentLevel[rowIndex][colIndex] = -1*currentLevel[rowIndex][colIndex];
					}
				}
			}
			
			//finds the minimum and maximum differences to use for scaling
			minPixel = 255;
			maxPixel = -255;
			for(int rowIndex = 0; rowIndex < topGaussianLevel.length; rowIndex++){
				for(int colIndex = 0; colIndex < topGaussianLevel[0].length; colIndex++){
					if(currentLevel[rowIndex][colIndex] < minPixel){
						minPixel = currentLevel[rowIndex][colIndex];
					}
					if(currentLevel[rowIndex][colIndex] > maxPixel){
						maxPixel = currentLevel[rowIndex][colIndex];
					}
				}
			}
			range = maxPixel - minPixel;
			
			//scales the values to [0,255]
			for(int rowIndex = 0; rowIndex < topGaussianLevel.length; rowIndex++){
				for(int colIndex = 0; colIndex < topGaussianLevel[0].length; colIndex++){
					currentPixel = currentLevel[rowIndex][colIndex];
					
					//takes the current range of values and scales it to between 0 and 255
					scaledPixel = ((currentPixel - minPixel)/range)*255.0;
					
					currentLevel[rowIndex][colIndex] = (int)scaledPixel;
				}
			}
			
			
			laplacianLevels.add(currentLevel);
		}
		
	}
	
	public List<int[][]> getLaplacianLevels() {
		return laplacianLevels;
	}

	public static Pyramids generatePyramids(int[][] channelData){
		return new Pyramids(channelData);
	}
	
	public List<int[][]> getReducedSizeLevels() {
		return reducedSizeLevels;
	}

	public List<int[][]> getSameSizeLevels() {
		return sameSizeLevels;
	}

	/*
	 * This generates the same size images.
	 * It works by taking the reduced size images and scaling them all up using bilinear interpolation
	 */
	private void generateSameSizeLevels(){
		for(int levelNumber = 1; levelNumber < reducedSizeLevels.size(); levelNumber++){
			sameSizeLevels.add( 
					BilinearInterpolation.generateInterpolatedImage(
							reducedSizeLevels.get(levelNumber),
							maxSize) );
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
