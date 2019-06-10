package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.FireBall;

public class FireBallTests {

	private FireBall fireBall;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		fireBall = new FireBall(x, y);
	}

	@Test
	public void testFireBallUnit() {
		assertNotNull(fireBall.loadImage(fireBall.initEvilGun()));
		assertFalse(fireBall.initEvilGun().equals(""));
	}

	@After
	public void tearDown() throws Exception {
		fireBall = null;
	}
}