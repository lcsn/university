package de.lsn.raspberrypi.service;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lsn.raspberrypi.framework.pwm.GpioPwmValueProvider;

public class GpioPwmValueProviderTest {

	@Test
	public void test() {
		GpioPwmValueProvider.getInstance().provide();
//		1hz
		Object[] values = GpioPwmValueProvider.getInstance().getValue(100, 1);
		assertEquals(1000l, values[0]);
		assertEquals(0l, values[1]);
		values = GpioPwmValueProvider.getInstance().getValue(80, 1);
		assertEquals(800l, values[0]);
		assertEquals(200l, values[1]);
//		2hz
		values = GpioPwmValueProvider.getInstance().getValue(100, 2);
		assertEquals(500l, values[0]);
		assertEquals(0l, values[1]);
		values = GpioPwmValueProvider.getInstance().getValue(80, 2);
		assertEquals(400l, values[0]);
		assertEquals(100l, values[1]);
//		10hz
		values = GpioPwmValueProvider.getInstance().getValue(100, 10);
		assertEquals(100l, values[0]);
		assertEquals(0l, values[1]);
		values = GpioPwmValueProvider.getInstance().getValue(80, 10);
		assertEquals(80l, values[0]);
		assertEquals(20l, values[1]);
	}

}

