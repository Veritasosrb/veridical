package modules;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static constants.Constants.*;

public class CommonPage {

	public static String getCurrentPath() {
		String currentPath = System.getProperty(USER_DIR);
		return currentPath;
	}

	public static String getInputFilePath(String functionality) {
		String folderPath = getCurrentPath() + INPUT_FOLDER_PATH + File.separator;
		String fileName = functionality + UNDERSCORE + INPUT + TXT_EXTENTSION;
		return folderPath + fileName;
	}

	public static String getIntermediateFilePath(String functionality, String... filePathKeywords) {
		String fileKeyword = filePathKeywords.length > 0 ? filePathKeywords[0] : null;
		String folderPath = getCurrentPath() + INTERMEDIATE_FOLDER_PATH + File.separator;
		String filePath = USER_STORY_KEYWORD + UNDERSCORE + functionality;

		if (fileKeyword == null) {
			filePath += CSV_EXTENTSION;
		} else if (fileKeyword == COMBINE_TESTCASES) {
			filePath += UNDERSCORE + COMBINE_TESTCASES + CSV_EXTENTSION;
		} else if (fileKeyword == DUPLICATES_REMOVED) {
			filePath += UNDERSCORE + DUPLICATES_REMOVED + CSV_EXTENTSION;
		} else if (fileKeyword == ZERO) {
			filePath += UNDERSCORE + ZERO + CSV_EXTENTSION;
		} else if (fileKeyword == DEPENDENCIES) {
			filePath += UNDERSCORE + DEPENDENCIES + CSV_EXTENTSION;
		} else {
			filePath += UNDERSCORE ;
		}

		return folderPath + filePath;
	}

	public static String getOutputFilePath(String functionality) {
		String timeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		String folderPath = getCurrentPath() + OUTPUT_FOLDER_PATH + File.separator;
		String fileName = TESTCASE + UNDERSCORE + functionality + UNDERSCORE + timeStamp + CSV_EXTENTSION;
		return folderPath + fileName;
	}
}
