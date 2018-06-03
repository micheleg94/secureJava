package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verificatore {

	private static Pattern pattern;
	private static Matcher matcher;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String TARGA_PATTERN = 
			"^[a-zA-Z]{2}[0-9]{3,4}[a-zA-Z]{2}$";


	private static final String PARTITAIVA_PATTERN = 
			"^[0-9]{11}$";
	
	private static final String PROVINCIA_PATTERN = 
			"^[a-zA-Z]{2}$";
	
	
	private static final String TELEFONO_PATTERN = 
			"^[0-9]{6,10}$";
	
	public static boolean controllaeMail(final String hex) 
	{
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();

	}
	
	public static boolean controllaTel(final String hex)
	{
		pattern = Pattern.compile(TELEFONO_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	
	public static boolean controllaTarga(final String hex) 
	{
		pattern = Pattern.compile(TARGA_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
    }
	
	public static boolean controllaPartitaIva(final String hex) 
	{
		pattern = Pattern.compile(PARTITAIVA_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
    }
	
	public static boolean controllaProvincia(final String hex) 
	{
		pattern = Pattern.compile(PROVINCIA_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
    }
	
	//Gentilmente offerta da icosaedro.it
	public static String ControllaCF(String cf) {
		int i, s, c;
		String cf2;
		int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
			11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
		if( cf.length() == 0 ) return "";
		if( cf.length() != 16 )
			return "La lunghezza del codice fiscale non &egrave;\n"
			+ "corretta: il codice fiscale dovrebbe essere lungo\n"
			+ "esattamente 16 caratteri.";
		cf2 = cf.toUpperCase();
		for( i=0; i<16; i++ ){
			c = cf2.charAt(i);
			if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
				return "Il codice fiscale contiene dei caratteri non validi:\n"
				+ "i soli caratteri validi sono le lettere e le cifre.";
		}
		s = 0;
		for( i=1; i<=13; i+=2 ){
			c = cf2.charAt(i);
			if( c>='0' && c<='9' )
				s = s + c - '0';
			else
				s = s + c - 'A';
		}
		for( i=0; i<=14; i+=2 ){
			c = cf2.charAt(i);
			if( c>='0' && c<='9' )	 c = c - '0' + 'A';
			s = s + setdisp[c - 'A'];
		}
		if( s%26 + 'A' != cf2.charAt(15) )
			return "Il codice fiscale non &egrave; corretto:\n"
			+ "il codice di controllo non corrisponde.";
		return "";
	}

}