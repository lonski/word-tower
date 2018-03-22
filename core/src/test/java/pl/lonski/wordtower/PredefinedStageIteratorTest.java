package pl.lonski.wordtower;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PredefinedStageIteratorTest {

	@Mock
	private StageLoader loader;
	@Mock
	private PlayStage s1;
	@Mock
	private PlayStage s2;
	@Mock
	private PlayStage sX;

	private PredefinedStageIterator iterator;

	@Before
	public void setUp() {
		when(loader.load("levels/s1.lev")).thenReturn(s1);
		when(loader.load("levels/s2.lev")).thenReturn(s2);
		iterator = new PredefinedStageIterator(loader, 2);
	}

	@Test
	public void shouldIterateCyclic() {

		assertEquals(s1, iterator.next());
		assertEquals(s2, iterator.next());
		assertEquals(s1, iterator.next());

	}

}
