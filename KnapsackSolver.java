import java.util.Arrays;
import java.util.LinkedList;


/**
 * 
 * @author AlgoDat Team
 *
 */
public class KnapsackSolver {
	
	
	/**
	 * Tries all possible item combination to solve the knapsack problem. Returns the optimal solution.
	 * @param k Empty knapsack with a maximum capacity to fill
	 * @param items a list of items each with a weight and a value
	 * @return the filled knapsack
	 */
	public Knapsack solveKnapsackOptimal(Knapsack k, LinkedList<Item> items){
		int [][] itemmatrix = new int[items.size()][k.maxWeight+1];
		
		//in case the knapsack can't hold anything
		if(itemmatrix.length == 0){
			return k;
		}
		
		
		//sort list by item weight
		
//		for (Item item : items) {
//			System.out.println(item.getValue()+ " " +item.getWeight());
//		}
//		System.out.println();
		
		LinkedList<Item> sort = new LinkedList<Item>(); //new list that is going to be sorted by weight
		
		//sorting..
		for(int i=0;i<items.size();i++){
			Item curr = new Item(0, 0, 0);
			for (int j=0;j<items.size();j++) {
				if(curr.getWeight()<=items.get(j).getWeight()&&items.get(j).getValue()>0){
					curr=items.get(j);
				}
			}

			sort.addFirst(curr);//add to the new list
			
			//fill in with 0 so that the iteration is still going over the whole list
			items.remove(curr);
			Item n = new Item(0, 0, 0);
			items.add(n);
		}
		
		//print sorted one
//		for (Item item : sort) {
//			System.out.println(item.getValue()+" "+item.getWeight());
//		}
		

		//fill in the first row with 0 since there is no space with capacity = 0 -> hopefully there is no items with weight 0
		for(int i=0;i<=sort.size()-1;i++){
			itemmatrix[i][0]=0;
		}
		
		for(int i=0;i<sort.size();i++){
			for(int w=0; w<k.maxWeight+1;w++){
				//if it can be taken
				if(sort.get(i).getWeight()<=w){
					//if it's the first one in the row
					if(i==0){
						itemmatrix[i][w] = sort.get(i).getValue(); //take it
					}else{
						//either take it or not and apply the above best solution
						itemmatrix[i][w] = Math.max(sort.get(i).getValue() + itemmatrix[i-1][w-sort.get(i).getWeight()], itemmatrix[i-1][w]);
							
					}
				//if it can't be taken
				}else{
					if(i==0){
						itemmatrix[i][w]=0;
					}else{
						itemmatrix[i][w] = itemmatrix[i-1][w];
					}
				}
			}
		}
		
		//print the matrix
//		for (int i = 0; i < sort.size(); i++) {
//			for (int j = 0; j < k.maxWeight+1; j++) {
//				System.out.print(itemmatrix[i][j]);
//			}
//			System.out.println();
//		}
		
		int w=k.maxWeight;
		//retrace to determine which items have been picked
		for (int i = sort.size()-1; i>=0; i--) {	
			if(i==0){
				k.addItem(sort.get(i));
				return k;
			}
			if(itemmatrix[i][w] != itemmatrix[i-1][w]){
				k.addItem(sort.get(i));
				w = w-sort.get(i).getValue()+2;
				w--;
			}
		}
		return k;
	}
	/**
	 * Uses the trivial greedy algorithm to solve the Knapsack problem. 
	 * @param k Empty knapsack with a maximum capacity to fill
	 * @param items a list of items each with a weight and a value
	 * @return the filled knapsack
	 */
	public Knapsack solveKnapsackGreedyStupid(Knapsack k, LinkedList<Item> items){
		//TODO: Implement this
		Item current = items.getFirst();
		while(!items.isEmpty()){
			
			for (Item item : items) {
				
				//find currently highest valuable item, remember it and remove it from list
				if((item.getValue()/item.getWeight()) < (current.getValue()/current.getWeight())){
					current = item;
					items.remove(item);
					
					//add it if it's not too heavy
					if(k.maxWeight - k.currentWeight <= current.getWeight()){
						k.currentWeight += current.getWeight();
						k.addItem(current);
					}	
				}
				
			}
			
		}
		
		return k;
	}
	
	
	/**
	 * Uses a smarter greedy algorithm to solve the Knapsack problem. 
	 * @param k Empty knapsack with a maximum capacity to fill
	 * @param items a list of items each with a weight and a value
	 * @return the filled knapsack
	 */
	public Knapsack solveKnapsackGreedySmart(Knapsack k, LinkedList<Item> items){
		// TODO: Implement this
		return k;
	}
	
	//END
}



