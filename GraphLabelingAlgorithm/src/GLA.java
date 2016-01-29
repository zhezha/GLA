import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Graph Labeling Algorithm
 * @author Zhao Zhengyang
 *
 */
public class GLA {
	// number of users
	public int M;
	// number of channels
	public int N;
	// utility matrix (user : channel)
	public double[][] utilityMatrix;
	// the whole user set, i.e. {1, 2, ..., M}
	public Set<Integer> totalMSet;
	// bucket L(j, m), 0 <= j <= N, 0 <= m <= M
	public List<List<Set<Label>>> L;
	// parameter for bucket capacity
	public int K;
	
	public GLA(int M, int N, double[][] matrix, int K) {
		this.M = M;
		this.N = N;
		this.utilityMatrix = matrix;
		this.K = K;
		
		// totalMSet = {1, 2, ..., M}
		totalMSet = new HashSet<Integer>();
		for (int m = 0; m < M; m++) {
			totalMSet.add(m + 1);
		}
		
		L = new ArrayList<List<Set<Label>>>();
		for (int j = 0; j <= N; j++) {
			L.add(new ArrayList<Set<Label>>());
			for (int m = 0; m <= M; m++) {
				// L(j, m) <- {}
				L.get(j).add(new HashSet<Label>());
			}
		}
		for (int j = 0; j <= N; j++) {
			// L(j, 0) <- {(0, 0, {})}
			L.get(j).get(0).add(new Label(0, 0, new HashSet<Integer>()));
		}
	}
	
	
	public Label solve() {
		for (int j = 1; j <= N; j++) {
			for (int m = 1; m <= Math.min(j, M); m++) {
				for (int i = 0; i <= j - 1; i++) {
					// L' <- L(i, m)
					// should be a local copy!!!
					Set<Label> LPrime = new HashSet<Label>(L.get(i).get(m));
					// L(i, m - 1)
					Set<Label> tmpL1 = L.get(i).get(m - 1);
					for (Label l : tmpL1) {
						Set<Integer> ml = l.mSet;
						Set<Integer> mlComplementary = new HashSet<Integer>(totalMSet);
						mlComplementary.removeAll(ml);
						for (int w : mlComplementary) {
							// Need to find a way how to get utility value from the given Gain Matrix?????
							double vNew = l.v + getPerformanceValue(w, i, j);
							int mNumNew = l.mNum + 1;
							HashSet<Integer> mSetNew = new HashSet<Integer>(l.mSet);
							mSetNew.add(w);
							LPrime.add(new Label(vNew, mNumNew, mSetNew));
						}
					}
					for (Label lp : LPrime) {
						int count = 0;
						for (int s = 1; s <= m; s++) {
							// L(j, s)
							Set<Label> tmpL2 = L.get(j).get(s);
							for (Label h : tmpL2) {
								if (lp.isDominantedBy(h)) {
									count++;
									break;
								}
							}
							if (count > 0)
								break;
						}
						if (count == 0) {
							// L(j, m)
							Set<Label> Ljm = L.get(j).get(m);
							if (Ljm.size() < K) {
								Ljm.add(lp);
							}
							else {
								Label lb = argmin((HashSet<Label>) Ljm);
								if (lp.v > lb.v) {
									Ljm.remove(lb);
									Ljm.add(lp);
								}
							}
						}
					}
				}
			}
		}
		
		List<Set<Label>> LN = L.get(N - 1);
		
		System.out.println("size of LN: " + LN.size());
		for (Label l : LN.get(1)) {
			System.out.print(l.v + ", ");
		}
		System.out.println();
		
		Label ls = argMax(LN);
		
		return ls;
	}
	
	
	public Label argmin(HashSet<Label> L) {
		double vMin = L.iterator().next().v;
		Label lMin = L.iterator().next();
		for (Label l : L) {
			if (l.v < vMin) {
				vMin = l.v;
				lMin = l;
			}
		}		
		return lMin;
	}
	
	
	public Label argMax(List<Set<Label>> list) {
		double vMax = list.get(0).iterator().next().v;
		Label lMax = list.get(0).iterator().next();
		for (Set<Label> set : list) {
			for (Label l : set) {
				if (l.v > vMax) {
					vMax = l.v;
					lMax = l;
				}
			}
		}
		return lMax;
	}
	
	// Need to find a way how to use the Gain Matrix?????
	public double getPerformanceValue(int w, int i, int j) {
		double value = 0;
		for (int index = i + 1; index <= j; index++) {
			// 1 <= w <= M, 1 <= i, j, <= N
			value += utilityMatrix[w - 1][index - 1];
		}
		return value;
	}
	
}
