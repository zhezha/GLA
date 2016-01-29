import java.util.Map;
import com.jmatio.types.MLArray;


public class DataReaderTest {

	public static void main(String[] args) {
		
		String filename = "G2.mat";
		String arrayName = "G";
		Map<String, MLArray> content = null;
		String arrayString = null;
		double[][] userChannelMat = null;
		
		try {
			content = DataReader.getContent(filename);
			arrayString = DataReader.readArrayToString(filename, arrayName);
			userChannelMat = DataReader.getMatrix(filename, arrayName);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Map.Entry<String, MLArray> entry : content.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		System.out.println();
		System.out.println(arrayString);
		printMat(userChannelMat);
	}
	
	
	
	public static void printMat(double[][] matrix) {
		
		int M = matrix.length;
		int N = matrix[0].length;
		
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				System.out.format("%-15.10f   ", matrix[i][j]);
			}
			System.out.println();
		}
	}

}
