package zach;

public class EdgeDetectionZach {

	
	public static int[][] generateZeroCrossingImage(int[][] originalImage){
		
		int[][] zeroCrossingImage = new int[originalImage.length][originalImage[0].length];
		
		int currentPixel;
		boolean zeroCrossingPixel = false;
		
		for(int rowInd = 0; rowInd < zeroCrossingImage.length; rowInd++){
			for(int colInd = 0; colInd < zeroCrossingImage[0].length; colInd++){
				
				currentPixel = originalImage[rowInd][colInd];
				zeroCrossingPixel = false;
				
				//checks all the neighbors
				for(int currentPixelRow = rowInd-1; currentPixelRow <= rowInd + 1; currentPixelRow++){
					for(int currentPixelCol = colInd-1; currentPixelCol <= colInd + 1; currentPixelCol++){
						
						//makes sure there won't be an array index out of bounds exception
						if(currentPixelRow >= 0 && currentPixelRow < zeroCrossingImage.length){
							if(currentPixelCol >= 0 && currentPixelCol < zeroCrossingImage[0].length){
								if(currentPixel != originalImage[currentPixelRow][currentPixelCol]){
									zeroCrossingPixel = true;
								}
							}
						}
						
					}
				}
				
				if(zeroCrossingPixel){
					zeroCrossingImage[rowInd][colInd] = 255;
				}
				
			}
		}
		
		return zeroCrossingImage;
	}
}
