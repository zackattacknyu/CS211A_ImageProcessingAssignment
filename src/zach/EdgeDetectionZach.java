package zach;

import java.util.ArrayList;
import java.util.List;

public class EdgeDetectionZach {

	public static int calculateVariance(List<Integer> numbers){
		double average = 0;
		for(int number: numbers){
			average += number;
		}
		average = average/numbers.size();
		
		double variance = 0;
		
		for(int number:numbers){
			variance += Math.pow(number - average, 2);
		}
		
		return (int)variance;
	}
	
	public static int[][] generatedEdgeImage(int[][] originalImage,int threshold){
		int[][] edgeImage = new int[originalImage.length][originalImage[0].length];
		
		List<Integer> currentList;
		int currentVariance;
		
		for(int rowInd = 0; rowInd < edgeImage.length; rowInd++){
			for(int colInd = 0; colInd < edgeImage[0].length; colInd++){
				
				currentList = new ArrayList<Integer>(9);
				
				//checks all the neighbors
				for(int currentPixelRow = rowInd-1; currentPixelRow <= rowInd + 1; currentPixelRow++){
					for(int currentPixelCol = colInd-1; currentPixelCol <= colInd + 1; currentPixelCol++){
						
						//makes sure there won't be an array index out of bounds exception
						if(currentPixelRow >= 0 && currentPixelRow < edgeImage.length){
							if(currentPixelCol >= 0 && currentPixelCol < edgeImage[0].length){
								
								currentList.add(originalImage[currentPixelRow][currentPixelCol]);
								
							}
						}
						
					}
				}
				
				currentVariance = calculateVariance(currentList);
				if(currentVariance > threshold){
					edgeImage[rowInd][colInd] = 255;
				}
				
				
			}
		}
		
		return edgeImage;
	}
	
	
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
