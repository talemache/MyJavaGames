package sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class LevelsAndBgs {

	static ArrayList<String> levels = new ArrayList<String>();
	static ArrayList<Image> backgrounds = new ArrayList<Image>();

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	static void addLevels() {

		try {
			Stream<Path> files = Files.list(Paths.get("levels"));
			long filesCount = files.count();
			String levelString;
			for (int i = 0; i < filesCount; i++) {
				levelString = readFile("levels/level" + i + ".txt", Charset.defaultCharset());
				levels.add(levelString);
			}
			files.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void addBackgrounds() {

		try {
			Stream<Path> images = Files.list(Paths.get("backgrounds"));
			long imagesCount = images.count();

			for (int i = 0; i < imagesCount; i++) {
				File sourceImage = new File("backgrounds/bg" + i + ".jpg");
				Image levelBG = ImageIO.read(sourceImage);
				backgrounds.add(levelBG);
			}

			images.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}