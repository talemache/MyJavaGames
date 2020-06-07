package items;

import game_engine.Images;
import game_engine.SpritePattern;

public class SaveSign extends SpritePattern {

	public static SaveSign saveSign;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public SaveSign(int x, int y) {
		super(x, y);
	}

	public String initSave() {
		imageName = Images.SAVESIGNINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}
}