package start;

public class BirthdayProblem {

	public static void main(String[] args){
		System.out.println("The number of people it takes for there to be a 99% chance\n\t"
				+ " that two people have the exact same birth date\n\t given"
				+ " a certain age range");
		System.out.println("Range in Years: Number of People");
		for(int numYears = 1; numYears < 300; numYears++){
			System.out.println(numYears + ":" + numPeopleFor99Prob(numYears));
		}
	}
	
	public static int numPeopleFor99Prob(double numYears){
		for(int numPeople = 5; numPeople < 5000; numPeople ++){
			if(probability(numPeople,numYears) > 0.99){
				return numPeople;
			}
		}
		return 500;
	}
	
	
	/*
	 * Does the birthday problem but figures it out 
	 * 		with multiple years in mind to figure
	 * 		out the probability that two people 
	 * 		with be born on the exact same day
	 * 		given an age range of X years among the people. 
	 */
	public static double probability(double numPeople, double numYears){
		double prob = 1;
		double numDays = numYears * (366);
		
		for(double numDaysLeft = numDays-1; numDaysLeft >= (numDays-numPeople); numDaysLeft--){
			prob = prob * (numDaysLeft/numDays);
		}
		
		return 1-prob;
	}
}
