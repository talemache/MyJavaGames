package game_engine;

import java.awt.event.ActionEvent;
import java.util.List;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.PlayerShip;
import enums.SoundEffects;
import items.CanonBall;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import sound_engine.PlayWave1st;
import util.LoadSounds;

public abstract class UpdateObjects extends InitObjects {

	private static final long serialVersionUID = 1L;
	public static int lifeEvilHead = 3;
	public static int lifePlayerShip = 3;
	public static int lifeBunker = 3;

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateMyShip();
		updateMyCrosshair();
		updateMyShipMissiles();
		updateEHPlasmaBalls();
		updateEHCanons();
		updateRockets();
		updateAliens();
		updateEvilHead();
		updateGold();
		updateHealth();
		updateBullets();

		Collisions.checkCollisions();

		repaint();

	}

	private void inGame() {

		if (!ingame) {
			timerEasy.stop();
			timerMedium.stop();
			timerHard.stop();
		}
	}

	private void updateMyShip() {

		if (PlayerShip.playerOne.isVisible()) {
			PlayerShip.playerOne.move();
		}
	}

	private void updateMyCrosshair() {

		if (Crosshair.crosshair.isVisible()) {
			Crosshair.crosshair.move();
		} else {
			Crosshair.crosshair = null;
		}
	}

	private void updateMyShipMissiles() {

		List<ShipMissile> missiles = PlayerShip.playerOne.getMissiles();

		missiles.removeIf(missile -> missile.isVisible() == false);

		missiles.stream().filter(missile -> missile.isVisible()).forEach(missile -> missile.moveMissile());

	}

	private void updateBullets() {

		Bunker.bullets = Bunker.bunkerObj.getBulletsLeft();
		Bunker.bullets2 = Bunker.bunkerObj.getBulletsRight();

		if (Bunker.bullets.removeIf(bullet -> bullet.isVisible() == false)
				|| Bunker.bullets2.removeIf(bullet -> bullet.isVisible() == false)) {
			LoadSounds.fuse.stop();
		}

		Bunker.bullets.stream().filter(bullet -> bullet.isVisible()).forEach(bullet -> {
			bullet.moveDiagLeft();
			if (PlayerShip.playerOne.x > 200) {
				bullet.moveDiagRight();
				bullet.moveRight();
			} else if (PlayerShip.playerOne.y > 300) {
				bullet.moveDown();
				bullet.moveLeft();
			}
		});

		Bunker.bullets2.stream().filter(bullet -> bullet.isVisible()).forEach(bullet -> {
			bullet.moveDiagRight();
			if (PlayerShip.playerOne.x > 200) {
				bullet.moveDiagLeft();
				bullet.moveLeft();
			} else if (PlayerShip.playerOne.y > 300) {
				bullet.moveDown();
				bullet.moveLeft();
			}
		});
	}

	private void updateEHPlasmaBalls() {

		List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls();

		plasmaBalls.removeIf(plasmaBall -> plasmaBall.isVisible() == false);

		plasmaBalls.stream().filter(plasmaBall -> plasmaBall.isVisible()).forEach(plasmaBall -> {
			if (Dragon.dragons.isEmpty() && timerHard.isRunning()) {
				if (Gold.goldstack.isEmpty() && lifePlayerShip <= 3) {
					plasmaBall.evilShotDiagUp();
					if (plasmaBall.y < 0) {
						plasmaBall.y = 0;
						plasmaBall.evilShot();
					}
				}
				if (Gold.goldstack.size() > 0 && lifePlayerShip <= 3) {
					plasmaBall.evilShotDiagDown();
					if (plasmaBall.y > 768) {
						plasmaBall.y = 768;
						plasmaBall.evilShot();
					}
				}
			} else {
				plasmaBall.evilShot();
			}
		});
	}

	private void updateEHCanons() {

		List<CanonBall> canonballs = EvilHead.evilHead.getCanons();

		canonballs.removeIf(canon -> canon.isVisible() == false);

		canonballs.stream().filter(canon -> canon.isVisible()).forEach(canon -> {
			if (EvilHead.evilHead.x - PlayerShip.playerOne.x > 0) {
				canon.moveCanonLeft();
			}
		});
	}

	private void updateRockets() {

		List<ShipRocket> rocketStack = PlayerShip.playerOne.getRockets();

		rocketStack.removeIf(rocket -> rocket.isVisible() == false);

		rocketStack.stream().filter(rocket -> rocket.isVisible()).forEach(rocket -> rocket.moveRocket());
	}

	private void updateAliens() {

		if (Alien.aliens.removeIf(alien -> alien.isVisible() == false)) {
			SoundEffects.BLOOP.getSound();
		}

		Alien.aliens.stream().filter(alien -> alien.isVisible()).forEach(alien -> {
			if (timerHard.isRunning()) {
				alien.moveFaster();
			}
			alien.move();
		});
	}

	protected static int updateDragons() {

		Dragon.dragons.stream().forEach(dragon -> {
			dragon.setVisible(true);
			Collisions.checkCollisions();
			if (dragon.isVisible()) {
				dragon.move();
			} else {
				SoundEffects.BLOOP.getSound();
				Collisions.dragonKilled++;
				Dragon.dragons.remove(dragon);
			}
		});

		return Collisions.dragonKilled;
	}

	private void updateEvilHead() {
		if (EvilHead.evilHead.isVisible() && timerEasy.isRunning()) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnEasy();
			}
			if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0) {
				EvilHead.evilHead.AIOnEasy();
			}
		}

		if (EvilHead.evilHead.isVisible() && timerMedium.isRunning() == true) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnMedium();
			}
			if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0) {
				EvilHead.evilHead.AIOnMedium();
			}
		}

		if (EvilHead.evilHead.isVisible() && timerHard.isRunning() == true) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnHard();
			}
			if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0) {
				EvilHead.evilHead.AIOnHard();
			}
		}
	}

	private void updateGold() {

		if (Gold.goldstack.removeIf(goldBar -> goldBar.isVisible() == false)) {
			new PlayWave1st("sounds/collect.wav").start();
		}

		Gold.goldstack.stream().filter(goldBar -> goldBar.isVisible()).forEach(goldBar -> goldBar.move());
	}

	private void updateHealth() {

		if (HealthPack.healthpack.removeIf(healthPack -> healthPack.isVisible() == false)) {
			new PlayWave1st("sounds/collect.wav").start();
			if (lifePlayerShip > 3) {
				lifePlayerShip--;
			}
		}

		if (HealthPack.healthpack.size() < 5 && lifePlayerShip > 3) {
			HealthPack.healthpack.add(new HealthPack(EvilHead.evilHead.x, 0));
		}

		HealthPack.healthpack.stream().filter(healthPack -> healthPack.isVisible())
				.forEach(healthPack -> healthPack.move());
	}
}