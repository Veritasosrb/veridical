package modules;

import static constants.Constants.EMPTY_STRING;
import java.io.FileReader;
import com.opencsv.CSVReader;

public class FetchData {

	public String retrieve_data() throws Exception {
		CSVReader reader = new CSVReader(new FileReader("./intermediate/UserStoryJiraNewOutputTrial2.csv"));
		// Reading the contents of the csv file
		String line[];
		String s = EMPTY_STRING;

		while ((line = reader.readNext()) != null) {
			for (int i = 0; i < line.length; i++) {
				// System.out.print(line[i]);
				// System.out.print("\n");
				if (i == line.length - 1) {
					s = line[i];
				}
			}
		}
		System.out.print(s);
		return s;
	}
}