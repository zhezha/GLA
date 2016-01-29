
public class App {

	public static void main(String[] args) {
		int M = 10;
		int N = 64;
		int K = 1;
		
		String filename = "G1.Mat";
		String arrayName = "G";
		double[][] matrix = null;
		try {
			matrix = DataReader.getMatrix(filename, arrayName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GLA gla = new GLA(M, N, matrix, K);
		Label ls = gla.solve();
		System.out.println("best value: " + ls.v);
		System.out.println("best set:");
		for (int w : ls.mSet) {
			System.out.print(w + ", ");
		}
		System.out.println();
	}

}
