package zach;

public class Convolve {

	//this is the basic filter in the assignment
	public static float[][] basicFilter(){
		float[][] toReturn = new float[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				toReturn[i][j] = (float) (1.0/9.0);
			}
		}
		return toReturn;
	}
	
	public static float[][] edgeFilter(){
		float[][] toReturn = new float[3][3];
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1)
					toReturn[i][j] = 1;
				else
					toReturn[i][j] = (float) (-1.0/8.0);
			}
		}
		return toReturn;
	}
	
	private static int[][] addPadding(int[][] channelData){
		//image with zeros as padding
		int[][] imageToUse = new int[channelData.length + 2][channelData[0].length + 2];
		
		//makes the first row of zeros
		for(int colInd = 0; colInd < imageToUse[0].length; colInd++){
			imageToUse[0][colInd] = 0;
		}
		
		//puts the image data into the padded matrix
		int paddedMatrixRow = 1;
		int paddedMatrixCol = 0;
		for(int row = 0; row < channelData.length; row++){
			paddedMatrixCol = 0;
			imageToUse[paddedMatrixRow][paddedMatrixCol] = 0;
			for(int col = 0; col < channelData[row].length; col++){
				paddedMatrixCol++;
				imageToUse[paddedMatrixRow][paddedMatrixCol] = channelData[row][col];
			}
			paddedMatrixCol++;
			imageToUse[paddedMatrixRow][paddedMatrixCol] = 0;
			paddedMatrixRow++;
		}
		
		//makes the last row of zeros
		for(int colInd = 0; colInd < imageToUse[paddedMatrixRow].length; colInd++){
			imageToUse[paddedMatrixRow][colInd] = 0;
		}
		
		return imageToUse;
	}
	
	public static int[][] apply3by3Filter(int[][] channelData,float[][] filter){
		
		int[][] imageToUse = addPadding(channelData);

		int[][] outputImage = new int[channelData.length][channelData[0].length];
		
		//sets up the variables used for the filter computation
		int paddedMatrixRow = 1;
		int paddedMatrixCol = 1;
		
		int currentPixelRow = 0;
		int currentPixelCol = 0;
		
		int currentFilterRow = 0;
		int currentFilterCol = 0;
		
		double filterValue = 0;
		double imagePixelValue = 0;
		
		double currentSummation = 0;
		
		//makes the output image
		for(int row = 0; row < channelData.length; row++){
			
			paddedMatrixCol = 1;
			for(int col = 0; col < channelData[0].length; col++){
				
				//finds the filter value for each pixel
				currentSummation = 0;
				for(int rowDiff = -1; rowDiff <= 1; rowDiff++){
					for(int colDiff = -1; colDiff <= 1; colDiff++){
						
						//sets the indices to be used
						currentPixelRow = paddedMatrixRow + rowDiff;
						currentPixelCol = paddedMatrixCol + colDiff;
						
						currentFilterRow = rowDiff + 1;
						currentFilterCol = colDiff + 1;
						
						//finds the values for the next term in the summation
						filterValue = filter[currentFilterRow][currentFilterCol];
						imagePixelValue = imageToUse[currentPixelRow][currentPixelCol];
						
						//computes the next part of the summation
						currentSummation += imagePixelValue*filterValue;
						
					}
				}
				outputImage[row][col] = (int) currentSummation;
				
				paddedMatrixCol++;
				
			}
			
			paddedMatrixRow++;
		}
		
		return outputImage;
	}
	
}
