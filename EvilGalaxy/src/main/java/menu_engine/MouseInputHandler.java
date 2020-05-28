package menu_engine;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import frames.Main;
import game_engine.InitObjects;
import game_engine.LoadGame;
import game_engine.UpdateObjects;
import menu_states.ControlsState;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import sound_engine.LoadSounds;
import sound_engine.PlayWave1st;
import sound_engine.SoundEffects;

public class MouseInputHandler implements MouseListener {

	public static Main main;
	private String soundName = SoundEffects.CLICK.getSound();

	@Override
	public void mousePressed(MouseEvent e) {

		int mouseX = e.getX();
		int mouseY = e.getY();

		if (MenuState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.menuMusic.stop();
					new PlayWave1st(soundName).start();
					MenuState.isOn = false;
					UpdateObjects.lifeMyShip = 3;
					UpdateObjects.lifeEvilHead = 3;
					UpdateObjects.lifeBunker = 3;
					InitObjects.ingame = true;
					if (main == null) {
						Display.frame.remove(Display.canvas);
						Display.frame.dispose();
						EventQueue.invokeLater(() -> {
							main = new Main();
							main.setVisible(true);
						});
					}
					MenuState.isOn = true;
				} else if (mouseY >= 250 && mouseY <= 300) {
					new PlayWave1st(soundName).start();
					new LoadGame().openFileChooser();
				} else if (mouseY >= 350 && mouseY <= 400 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.CONTROLS);
					MenuState.isOn = false;
					ControlsState.isOn = true;
				} else if (mouseY >= 450 && mouseY <= 500 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.SETTINGS);
					MenuState.isOn = false;
					SettingsState.isOn = true;
				} else if (mouseY >= 550 && mouseY <= 600 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					System.exit(0);
				}
			}
		}

		if (ControlsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					new PlayWave1st(soundName).start();
					ControlsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			}
		}
		
		if (SettingsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					new PlayWave1st(soundName).start();
					SettingsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		if((CanvasMenu.State.getState() == StateManager.STATES.SETTINGS) && (point.getX() >= 800 && point.getX() <= 800 + Constants.LOAD_ASSETS.myShip.getWidth(null))) {
			if(point.getY() >= 255 && point.getY() <= 255 + Constants.LOAD_ASSETS.myShip.getHeight(null)) {				
				new PlayWave1st(soundName).start();
				CanvasMenu.color.nextColor(CanvasMenu.color.getColor());
			}
		}
	}

}
