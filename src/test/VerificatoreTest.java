package test;

import static org.junit.Assert.*;

import org.junit.Test;

import utility.Verificatore;

public class VerificatoreTest {

	
	@Test
	public void testControllaeMail() {
		boolean output = Verificatore.controllaeMail("ciao@libero.it");
		assertEquals(true, output);
	}

	@Test
	public void testControllaTel() {
		boolean output = Verificatore.controllaTel("3201458963");
		assertEquals(true, output);
	}

	@Test
	public void testControllaTarga() {
		boolean output = Verificatore.controllaTarga("DS123EA");
		assertEquals(true, output);
	}

	@Test
	public void testControllaPartitaIva() {
		boolean output = Verificatore.controllaPartitaIva("12345678909");
		assertEquals(true, output);
	}

	@Test
	public void testControllaProvincia() {
		boolean output = Verificatore.controllaProvincia("TA");
		assertEquals(true, output);
	}

	@Test
	public void testControllaCF() {
		String output = Verificatore.ControllaCF("GRGMHL94T05L049U");
		assertEquals("", output);
	}


}
