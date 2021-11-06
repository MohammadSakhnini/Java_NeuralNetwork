import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileUtil {

    public static String read(String path) {
	Scanner sc = null;
	StringBuilder builder = new StringBuilder();
	try {
	    sc = new Scanner(new File(path));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	sc.useDelimiter("\n");
	while (sc.hasNext()) {
	    builder.append(sc.next().replaceAll("\\s+", "") + "\n");
	}
	return builder.toString();
    }

    public static String removeDuplicates(String data, String delimiter) {
	return data.replaceAll("(" + delimiter + ")\\1+", "");
    }

    public static void saveTo(String path, String data) {
	try {
	    Files.writeString(Paths.get(path), data, StandardCharsets.UTF_8);
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
    }
}
