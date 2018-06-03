package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utility.Crittografia;

public class TestCrittografia {
	
	private static final String[] parole = {"ciao"," ","123","0xEA43","CarLoan"};
	private static final String[] md5 = {"6e6bc4e49dd477ebc98ef4046c067b5f","7215ee9c7d9dc229d2921a40e899ec5f","202cb962ac59075b964b07152d234b70","ec08c14d76917405f41d4e7144f2ec65","f45abe841f6b9d73a6107b19741a2019"};

	@Test
	public void testEncrypt() 
	{
		for (int i=0; i<5; i++)
		{
			String dettagli = String.format("Parola: %s", parole[i]);
			assertEquals(dettagli, md5[i], Crittografia.encrypt(parole[i]));
		}
	}

}
