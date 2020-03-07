package items;

import game_engine.Images;
import game_engine.SpritePattern;

public class ShipMissile extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 390;
	private final int MISSILE_SPEED = 50;
	private String imageName;

	public ShipMissile(int x, int y) {
		super(x, y);
		initMissile();
	}

	public String initMissile() {
		imageName = Images.MISSILEINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void moveMissile() {

		x += MISSILE_SPEED;

		if (x > BOARD_WIDTH + 350)
			vis = false;
	}
}