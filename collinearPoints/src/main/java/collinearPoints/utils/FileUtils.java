package collinearPoints.utils;



import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;


public class FileUtils {
	
	private static final String EXTENSION_FILE = ".txt";
	
	public static File getFileFromName(String nameResource) throws IOException {
	
			File file = new ClassPathResource(nameResource.concat(EXTENSION_FILE)).getFile();
			return file;
	}
}
