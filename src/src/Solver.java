import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

	private double[][] segmentsGains;
	private int numOfNodes;

	private ArrayList<Integer[]> nonTouchingLoops;
	private ArrayList<Double> nonTouchingLoopGains;

	private ArrayList<ArrayList<Integer>> forwardPaths;
	private ArrayList<boolean[]> forwardPathsMask;
	private ArrayList<Double> forwardPathGains;
	private ArrayList<Double> deltaa;

	private ArrayList<ArrayList<Integer>> loops;
	private ArrayList<boolean[]> loopsMask;
	private ArrayList<Double> loopGains;

	public void setSFG(double[][] segmentsGains) {
		numOfNodes = segmentsGains.length;
		this.segmentsGains = segmentsGains;
		initialize();
	}

	private void initialize() {
		forwardPaths = new ArrayList<ArrayList<Integer>>();
		forwardPathGains = new ArrayList<Double>();
		loops = new ArrayList<ArrayList<Integer>>();
		loopGains = new ArrayList<Double>();
		nonTouchingLoops = new ArrayList<Integer[]>();
		nonTouchingLoopGains = new ArrayList<Double>();
		forwardPathsMask = new ArrayList<boolean[]>();
		loopsMask = new ArrayList<boolean[]>();
		deltaa=new ArrayList<Double>();
		generateFBAndLoops();
		ArrayList<ArrayList<Integer>> loopLabels = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < loops.size(); i++) {
			loopLabels.add(new ArrayList<Integer>());
			loopLabels.get(loopLabels.size() - 1).add(i);
		}
		generateNonTouching(loopLabels, 1);
	}

	//calculate gains
	private double calcGain(ArrayList<Integer> arr) {
		double temp = 1;
		if (arr.size() > 1) {
			for (int i = 0; i < arr.size() - 1; i++)
				temp *= segmentsGains[arr.get(i)][arr.get(i + 1)];
			return temp;
		}
		return segmentsGains[arr.get(0)][arr.get(0)];

	}

	private void addToLoops(ArrayList<Integer> arr) {
		arr.add(arr.get(0));   //last node of loop = first node of it
		if (!isLoopFound(arr)) {
			loops.add(arr);
			loopsMask.add(mapNodes(arr));
			loopGains.add(calcGain(arr));
		}
	}

	private boolean[] mapNodes(ArrayList<Integer> arr) {
		boolean[] temp = new boolean[numOfNodes];
		for (int i = 0; i < arr.size(); i++) {
			temp[arr.get(i)] = true;
		}
		return temp;
	}

	private boolean isEquivalentLoop(boolean[] arr1, boolean[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i])
				return false;
		}
		return true;
	}

	private boolean isLoopFound(ArrayList<Integer> arr) {
		boolean[] loop = mapNodes(arr);
		for (int i = 0; i < loops.size(); i++) {
			if (loops.get(i).size() == arr.size()
					&& isEquivalentLoop(loop, loopsMask.get(i))) {
				return true;
			}
		}
		return false;
	}

	private void addToFP(ArrayList<Integer> arr) {

		forwardPaths.add(arr);
		forwardPathsMask.add(mapNodes(arr));
		forwardPathGains.add(calcGain(arr));
	}

	private void generateFBAndLoops() {
		FBAndLoops(new ArrayList<Integer>(), new boolean[numOfNodes], 0);
	}

	private void FBAndLoops(ArrayList<Integer> path, boolean[] visited, int nodeNum) {
		path.add(nodeNum);
		visited[nodeNum] = true;
		// forward path case
		if (path.size() > 1 && nodeNum == numOfNodes-1) {  /////-1
			addToFP(new ArrayList<>(path));
			return;
		}
		for (int neighbour = 0; neighbour < numOfNodes; neighbour++) {
			if (segmentsGains[nodeNum][neighbour] != 0) {
				if (!visited[neighbour]) {
					FBAndLoops(path, visited, neighbour);
					path.remove(path.size() -1); ////////////-1
					visited[neighbour] = false;
					// loop case
				} else {
					int index = path.indexOf(neighbour);
					if (index != -1) {
						List<Integer> temp = path.subList(index, path.size());
						addToLoops(new ArrayList<Integer>(temp));
					}
				}
			}
		}
	}

	private void generateNonTouching(ArrayList<ArrayList<Integer>> ArrList, int nth) {
		Set<List<Integer>> foundbefore = new HashSet<List<Integer>>();
		boolean moveOnFlag = false;
		ArrayList<ArrayList<Integer>> nextArrList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < ArrList.size(); i++) {
			for (int j = i + 1; j < ArrList.size(); j++) {
				for (int k = 0; k < ArrList.get(j).size(); k++) {
					int cand = ArrList.get(j).get(k);
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp.addAll(ArrList.get(i));
					temp.add(cand);
					if (isNonTouching(temp)) {
						Collections.sort(temp);
						if (!foundbefore.contains(temp)) {
							foundbefore.add(temp);
							moveOnFlag = true;
							nextArrList.add(new ArrayList<Integer>());
							nextArrList.get(nextArrList.size() - 1)
									.addAll(temp);
							nonTouchingLoops.add(temp.toArray(new Integer[temp
									.size()]));
							nonTouchingLoopGains.add(getNonTouchingGain(temp));
						}
					}
				}
			}

		}
		if (moveOnFlag) {
			generateNonTouching(nextArrList, ++nth);
		}
	}

	private boolean isNonTouching(ArrayList<Integer> arr) {
		int flag;
		// looping over columns
		for (int i = 0; i < numOfNodes; i++) {
			flag = 0;
			// looping over rows
			for (int j = 0; j < arr.size(); j++) {
				if (loopsMask.get(arr.get(j))[i])
					flag++;
			}
			if (flag > 1)
				return false;
		}
		return true;
	}

	private double getNonTouchingGain(ArrayList<Integer> arr) {
		double gain = 1;
		for (int j = 0; j < arr.size(); j++)
			gain *= loopGains.get(arr.get(j));
		return gain;
	}

	public Double[] getForwardPathGains() {
		return forwardPathGains.toArray(new Double[forwardPathGains.size()]);
	}

	public Double[] getLoopGains() {
		return loopGains.toArray(new Double[loopGains.size()]);
	}

	public Double[] getNonTouchingLoopGains() {
		return nonTouchingLoopGains.toArray(new Double[nonTouchingLoopGains.size()]);
	}

	/*
	 * convert FPs, loops and nontouching loops into strings
	 */
	public String[] getForwardPaths() {
		String fbString[] = new String[forwardPaths.size()];
		int itr = 0;
		for (ArrayList<Integer> arr : forwardPaths) {  //hena arr de 7t3di 3la elem elem fe forwardPaths
			fbString[itr] = "";                        //elle no3ha ArrayList
			for (int i = 0; i < arr.size(); i++) {
				fbString[itr] += (arr.get(i) + 1) + " ";
			}
			itr++;
		}
		return fbString;
	}

	public String[] getLoops() {
		String loopsString[] = new String[loops.size()];
		int itr = 0;
		for (ArrayList<Integer> arr : loops) {
			loopsString[itr] = "";
			for (int i = 0; i < arr.size(); i++) {
				loopsString[itr] += (arr.get(i) + 1) + " ";
			}
			itr++;
		}
		return loopsString;
	}

	public String[] getNonTouchingLoops() {
		String[] temp = getLoops();
		String nonString[] = new String[nonTouchingLoops.size()];
		int itr = 0;
		for (Integer[] arr : nonTouchingLoops) {
			nonString[itr] = "";

			if (arr.length > 0)
				nonString[itr] += temp[arr[0]];

			for (int i = 1; i < arr.length; i++)
				nonString[itr] += " , " + temp[arr[i]];

			itr++;
		}
		return nonString;
	}

	//calculate the transfer function
	private boolean isNonTouchingWithFP(ArrayList<Integer> arr, int fbNum) {
		int flag;
		for (int i = 0; i < numOfNodes; i++) {
			flag = 0;
			for (int j = 0; j < arr.size(); j++) {
				if (loopsMask.get(arr.get(j))[i])
					flag++;
			}
			if (forwardPathsMask.get(fbNum)[i])
				flag++;
			if (flag > 1)
				return false;
		}
		return true;
	}


	public double transferFn() {
		double current = 0;
		double delta = 0;
		double delta1=0;
		double delta2=0;
		double delta3=0;
		int opr = -1;
		int nth = 1;

		Double [] tempDou = NeedsData.loopsGain;
		for (int i = 0; i < tempDou.length; i++) {
			delta1 += tempDou[i].doubleValue();
		}

	   /*tempDou =  NeedsData.nonTouchingloopsGain;
		for (int i = 0; i < tempDou.length; i++) {
			delta2 += tempDou[i].doubleValue();
		}*/

	/*	for (int i = 0; i < nonTouchingLoops.size(); i++) {
			if (nonTouchingLoops.get(i).length == nth) {
				current += nonTouchingLoopGains.get(i);
			} else {
				delta += opr * current;
				opr *= -1;
				++nth;
			}

		}*/

		tempDou =  NeedsData.nonTouchingloopsGain;
		String[] tempArr = NeedsData.nonTouchingloops;
		int count=0;
		for(int i=0 ; i<tempArr.length ; i++) {
			count =tempArr[i].replaceAll("[^,]","").length();
			if(count%2==1) {
				delta2 += tempDou[i].doubleValue();
			}
			else  {
				delta2 -= tempDou[i].doubleValue();
			}

		}






		delta = 1 - delta1 + delta2 ;      ///////////
		//delta= 1 -delta;
		//delta = 1 - delta1 - delta ;
         NeedsData.deltaDeno=delta;
		double numerator = 0;
		double deltaN;

		for (int i = 0; i < forwardPaths.size(); i++) {
			deltaN = 1;
			current = 0;
			opr = -1;
			for (int j = 0; j < nonTouchingLoops.size(); j++) {
				if (isNonTouchingWithFP(
						new ArrayList<Integer>(Arrays.asList(nonTouchingLoops
								.get(j))), i)) {
					current +=  nonTouchingLoopGains.get(j);
					//opr *= -1;
				} else
					break;
			}
			deltaN += current;
			System.out.println(deltaN);
			if(deltaN != 1) {
				deltaN= 1 - deltaN;
				System.out.println(deltaN);
			}
			numerator += deltaN * forwardPathGains.get(i);  ///////
			deltaa.add(deltaN);
			System.out.println(deltaa.get(i));
		}


		return numerator / delta;
	}
	public Double[] getforwardPathLoopDelta() {
		return deltaa.toArray(new Double[forwardPaths.size()]);
	}
}