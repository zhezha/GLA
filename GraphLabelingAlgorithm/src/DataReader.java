import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;


/**
 * Data reader class for reading MAT-File
 * @author Zhao Zhengyang
 *
 */
public class DataReader {
	/**
	 * Read MAT file and return a 2-Dimensional array.
	 * @param filename
	 * @param arrayName
	 * @return the target 2-demension array with arrayName
	 * @throws Exception
	 */
	public static double[][] getMatrix(String filename, String arrayName) throws Exception {
		
		double[][] matrix = null;
		MatFileReader mfr = new MatFileReader(filename);
		MLArray mlArray = mfr.getMLArray(arrayName);
		
		int[] dimesions = mlArray.getDimensions();
		if (dimesions.length != 2)
			throw(new Exception("Wrong data dimension!"));
		else {
			matrix = new double[dimesions[0]][dimesions[1]];
			matrix = ((MLDouble) mlArray).getArray();
		}
		
		return matrix;
	}
	
	
	/**
	 * Read MAT file and return information of arrays involved in this mat file.
	 * @param filename 
	 * @return a Map with array name as Key and array information as Value
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map<String, MLArray> getContent(String filename) throws FileNotFoundException, IOException {
		
		MatFileReader mfr = new MatFileReader(filename);
		Map<String, MLArray> content = mfr.getContent();
		
		// print file content
//		for (Map.Entry<String, MLArray> entry : content.entrySet()) {
//			System.out.println(entry.getKey() + ", " + entry.getValue());
//		}
		
		return content;
	}
	
	
	/**
	 * 
	 * @param filename
	 * @param arrayName
	 * @return data of the target array in String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readArrayToString(String filename, String arrayName) throws FileNotFoundException, IOException {
		
		MatFileReader mfr = new MatFileReader(filename);
		MLArray mlArray = mfr.getMLArray(arrayName);
		return mlArray.contentToString();
	}
}
