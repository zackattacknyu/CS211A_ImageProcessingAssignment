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
	
	//this does the initial basic Convolution
	//TODO: Make more flexible
	public static int[][][] transformUsingFilter(int[][][] originalImage, float[][] filter){
		float currentSum = 0;
		int[][][] outputImage = new int[originalImage.length][originalImage[0].length][originalImage[0][0].length];
		for(int row = 0; row < originalImage[0].length; row++){
			for(int col = 0; col < originalImage[0][row].length; col++){
				currentSum = 0;
				for(int rowDiff = -1; rowDiff < 2; rowDiff++){
					for(int colDiff = -1; colDiff < 2; colDiff++){
						
						//this is how to make sure there are no problems around the boundary
						if((row + rowDiff >= 0) && (row + rowDiff < originalImage[0].length)){
							if((col + colDiff >= 0) && (col + colDiff < originalImage[0][row].length)){
								currentSum += (1.0/9.0)*originalImage[0][row + rowDiff][col + colDiff];
							}
						}
					}
				}
				outputImage[0][row][col] = (int)currentSum;
			}
		}
		return outputImage;
	}
	
}
