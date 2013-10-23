package zach;

public class BilinearInterpolation {

	public static int[][] generateInterpolatedImage(int[][] startingData, int maxSize){
		if(startingData.length >= maxSize){
			return startingData;
		}
		int[][] nextLevel = computeBilinearInterpolation(startingData);
		return generateInterpolatedImage(nextLevel,maxSize);
	}
	
	private static int[][] computeBilinearInterpolation(int[][] channelData){

		int[][] interpolatedImage = new int[channelData.length*2][channelData[0].length*2];

		//puts the average into the upper left corners of the 2x2 squares
		for(int rowNum = 0; rowNum < channelData.length; rowNum++){
			for(int colNum = 0; colNum < channelData[0].length; colNum++){
				interpolatedImage[rowNum*2][colNum*2] = channelData[rowNum][colNum];
			}
		}
		
		//computes the even rows of the interpolated Image
		int[] currentData = new int[interpolatedImage.length];
		for(int rowNum = 0; rowNum < interpolatedImage.length; rowNum+=2){
			
			//puts the current row into an array
			currentData = new int[interpolatedImage[rowNum].length];
			for(int colNum = 0; colNum < interpolatedImage[rowNum].length; colNum++){
				currentData[colNum] = interpolatedImage[rowNum][colNum];
			}
			
			//computes the interpolation of the array
			currentData = computeInterpolation(currentData);
			
			//puts the interpolation back into the current row
			for(int colNum = 0; colNum < interpolatedImage[rowNum].length; colNum++){
				interpolatedImage[rowNum][colNum] = currentData[colNum];
			}
		}
		
		//computes the columns of the interpolated image
		for(int colNum = 0; colNum < interpolatedImage[0].length; colNum++){
			
			//puts the current column into an array
			currentData = new int[interpolatedImage.length];
			for(int rowNum = 0; rowNum < interpolatedImage.length; rowNum++){
				currentData[rowNum] = interpolatedImage[rowNum][colNum];
			}
			
			//computes the interpolation of the array
			currentData = computeInterpolation(currentData);
			
			//puts the interpolation back into the current row
			for(int rowNum = 0; rowNum < interpolatedImage.length; rowNum++){
				interpolatedImage[rowNum][colNum] = currentData[rowNum];
			}
		}
		
		return interpolatedImage;
		
	}
	
	private static int[] computeInterpolation(int[] originalArray){
		int[] interpolatedArray = new int[originalArray.length];
		for(int index = 0; index < interpolatedArray.length - 2; index+=2){
			interpolatedArray[index] = originalArray[index];
			interpolatedArray[index+1] = getAverage(originalArray[index],originalArray[index+2]);
			interpolatedArray[index+2] = originalArray[index+2];
		}
		
		interpolatedArray[interpolatedArray.length-2] = originalArray[interpolatedArray.length-2];
		interpolatedArray[interpolatedArray.length-1] = originalArray[interpolatedArray.length-2];
		return interpolatedArray;
	}
	
	private static int getAverage(int one, int two){
		double valOne, valTwo;
		valOne = one;
		valTwo = two;
		return (int)((valOne + valTwo)/2);
	}
}
