package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Water;

public class WaterTest {

	private Water water;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		water = new Water(x, y);
	}

	@Test
	public void testWater() {
		assertNotNull(water.getImage());
		assertFalse("Water img name is empty", water.img.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		water = null;
	}

}
