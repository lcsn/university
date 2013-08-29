package playground.damage;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lsn.gameplay.data.Damage;

public class DamageTest {

	@Test
	public void testDamage20() {
		assertEquals(Damage.D20, Damage.getMultiplicator(0));
		assertEquals(Damage.D20, Damage.getMultiplicator(1));
		assertEquals(Damage.D20, Damage.getMultiplicator(2));
		assertEquals(Damage.D20, Damage.getMultiplicator(3));
		assertEquals(Damage.D20, Damage.getMultiplicator(4));
		assertEquals(Damage.D20, Damage.getMultiplicator(5));
	}

	@Test
	public void testDamage40() {
		assertEquals(Damage.D40, Damage.getMultiplicator(6));
		assertEquals(Damage.D40, Damage.getMultiplicator(7));
		assertEquals(Damage.D40, Damage.getMultiplicator(8));
		assertEquals(Damage.D40, Damage.getMultiplicator(9));
		assertEquals(Damage.D40, Damage.getMultiplicator(10));
		assertEquals(Damage.D40, Damage.getMultiplicator(11));
		assertEquals(Damage.D40, Damage.getMultiplicator(12));
		assertEquals(Damage.D40, Damage.getMultiplicator(13));
		assertEquals(Damage.D40, Damage.getMultiplicator(14));
		assertEquals(Damage.D40, Damage.getMultiplicator(15));
		assertEquals(Damage.D40, Damage.getMultiplicator(16));
		assertEquals(Damage.D40, Damage.getMultiplicator(17));
		assertEquals(Damage.D40, Damage.getMultiplicator(18));
		assertEquals(Damage.D40, Damage.getMultiplicator(19));
		assertEquals(Damage.D40, Damage.getMultiplicator(20));
		assertEquals(Damage.D40, Damage.getMultiplicator(21));
		assertEquals(Damage.D40, Damage.getMultiplicator(22));
		assertEquals(Damage.D40, Damage.getMultiplicator(23));
		assertEquals(Damage.D40, Damage.getMultiplicator(24));
		assertEquals(Damage.D40, Damage.getMultiplicator(25));
	}

	@Test
	public void testDamage60() {
		assertEquals(Damage.D60, Damage.getMultiplicator(26));
		assertEquals(Damage.D60, Damage.getMultiplicator(27));
		assertEquals(Damage.D60, Damage.getMultiplicator(28));
		assertEquals(Damage.D60, Damage.getMultiplicator(29));
		assertEquals(Damage.D60, Damage.getMultiplicator(30));
		assertEquals(Damage.D60, Damage.getMultiplicator(31));
		assertEquals(Damage.D60, Damage.getMultiplicator(32));
		assertEquals(Damage.D60, Damage.getMultiplicator(33));
		assertEquals(Damage.D60, Damage.getMultiplicator(34));
		assertEquals(Damage.D60, Damage.getMultiplicator(35));
		assertEquals(Damage.D60, Damage.getMultiplicator(36));
		assertEquals(Damage.D60, Damage.getMultiplicator(37));
		assertEquals(Damage.D60, Damage.getMultiplicator(38));
		assertEquals(Damage.D60, Damage.getMultiplicator(39));
		assertEquals(Damage.D60, Damage.getMultiplicator(40));
		assertEquals(Damage.D60, Damage.getMultiplicator(41));
		assertEquals(Damage.D60, Damage.getMultiplicator(42));
		assertEquals(Damage.D60, Damage.getMultiplicator(43));
		assertEquals(Damage.D60, Damage.getMultiplicator(44));
		assertEquals(Damage.D60, Damage.getMultiplicator(45));
		assertEquals(Damage.D60, Damage.getMultiplicator(46));
		assertEquals(Damage.D60, Damage.getMultiplicator(47));
		assertEquals(Damage.D60, Damage.getMultiplicator(48));
		assertEquals(Damage.D60, Damage.getMultiplicator(49));
		assertEquals(Damage.D60, Damage.getMultiplicator(50));
	}

	@Test
	public void testDamage80() {
		assertEquals(Damage.D80, Damage.getMultiplicator(51));
		assertEquals(Damage.D80, Damage.getMultiplicator(52));
		assertEquals(Damage.D80, Damage.getMultiplicator(53));
		assertEquals(Damage.D80, Damage.getMultiplicator(54));
		assertEquals(Damage.D80, Damage.getMultiplicator(55));
		assertEquals(Damage.D80, Damage.getMultiplicator(56));
		assertEquals(Damage.D80, Damage.getMultiplicator(57));
		assertEquals(Damage.D80, Damage.getMultiplicator(58));
		assertEquals(Damage.D80, Damage.getMultiplicator(59));
		assertEquals(Damage.D80, Damage.getMultiplicator(60));
		assertEquals(Damage.D80, Damage.getMultiplicator(61));
		assertEquals(Damage.D80, Damage.getMultiplicator(62));
		assertEquals(Damage.D80, Damage.getMultiplicator(63));
		assertEquals(Damage.D80, Damage.getMultiplicator(64));
		assertEquals(Damage.D80, Damage.getMultiplicator(65));
		assertEquals(Damage.D80, Damage.getMultiplicator(66));
		assertEquals(Damage.D80, Damage.getMultiplicator(67));
		assertEquals(Damage.D80, Damage.getMultiplicator(68));
		assertEquals(Damage.D80, Damage.getMultiplicator(69));
		assertEquals(Damage.D80, Damage.getMultiplicator(70));
		assertEquals(Damage.D80, Damage.getMultiplicator(71));
		assertEquals(Damage.D80, Damage.getMultiplicator(72));
		assertEquals(Damage.D80, Damage.getMultiplicator(73));
		assertEquals(Damage.D80, Damage.getMultiplicator(74));
		assertEquals(Damage.D80, Damage.getMultiplicator(75));
	}

	@Test
	public void testDamage100() {
		assertEquals(Damage.D100, Damage.getMultiplicator(76));
		assertEquals(Damage.D100, Damage.getMultiplicator(77));
		assertEquals(Damage.D100, Damage.getMultiplicator(78));
		assertEquals(Damage.D100, Damage.getMultiplicator(79));
		assertEquals(Damage.D100, Damage.getMultiplicator(80));
		assertEquals(Damage.D100, Damage.getMultiplicator(81));
		assertEquals(Damage.D100, Damage.getMultiplicator(82));
		assertEquals(Damage.D100, Damage.getMultiplicator(83));
		assertEquals(Damage.D100, Damage.getMultiplicator(84));
		assertEquals(Damage.D100, Damage.getMultiplicator(85));
		assertEquals(Damage.D100, Damage.getMultiplicator(86));
		assertEquals(Damage.D100, Damage.getMultiplicator(87));
		assertEquals(Damage.D100, Damage.getMultiplicator(88));
		assertEquals(Damage.D100, Damage.getMultiplicator(89));
		assertEquals(Damage.D100, Damage.getMultiplicator(90));
		assertEquals(Damage.D100, Damage.getMultiplicator(91));
		assertEquals(Damage.D100, Damage.getMultiplicator(92));
		assertEquals(Damage.D100, Damage.getMultiplicator(93));
		assertEquals(Damage.D100, Damage.getMultiplicator(94));
		assertEquals(Damage.D100, Damage.getMultiplicator(95));
	}

	@Test
	public void testDamage200() {
		assertEquals(Damage.D200, Damage.getMultiplicator(96));
		assertEquals(Damage.D200, Damage.getMultiplicator(97));
		assertEquals(Damage.D200, Damage.getMultiplicator(98));
		assertEquals(Damage.D200, Damage.getMultiplicator(99));
		assertEquals(Damage.D200, Damage.getMultiplicator(100));
	}

}
