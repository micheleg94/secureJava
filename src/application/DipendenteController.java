package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import utility.Verificatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DAO;
import model.Dipendente;

public class DipendenteController
{
 
	public DipendenteController(){}
	public static ObservableList<Dipendente> getListaDipendente()
	{
		return caricaListaDipendente();
	}
	
	public static boolean aggiungiDipendente(Dipendente dipendente)
	{
		 String comando = String.format("INSERT INTO `dipendente` (`Agenzia`, `Nome`, `Cognome`, `Telefono`, `Password`, `Username`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",dipendente.getAgenzia(),dipendente.getNome(),dipendente.getCognome(),dipendente.getTelefono(),dipendente.getPassword(),dipendente.getUsername());
	     if(DAO.esegui(comando))
	     {
	    	 return true;
	     }else
	     {
	    	 return false;
	     }
	}
	
	public static boolean eliminaDipendente(Dipendente dipendente)
	{
		//Elimino il dipendente dal DB
		String comando = String.format("DELETE FROM `dipendente` WHERE `Username` IN ('%s')", dipendente.getUsername());
		if(DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public static String verificaDipendente(Dipendente dipendente)
	{
		String risposta = "Errore!";
		if (dipendente.getNome().length() > 0 &&dipendente.getNome().length() < 46)
		{
			if (dipendente.getCognome().length() > 0 && dipendente.getCognome().length() < 46)
			{
				if (dipendente.getTelefono().length() > 8 && dipendente.getTelefono().length() < 11)
				{
					if (Verificatore.controllaTel(dipendente.getTelefono()))
					{
						if (!isUsernameEsistente(dipendente))
						{
							risposta = "";
						}else
						{
							risposta = "Un dipendente con questo Username è già presente";
						}
					}else
					{
						risposta = "Il telefono deve essere numerico";
					}
				}else
				{
					risposta = "Il telefono deve essere compreso tra 9 e 10 caratteri";
				}
			}else
			{
				risposta = "Il cognome deve essere compreso tra 1 e 45 caratteri";
			}
		}else
		{
			risposta = "Il nome deve essere compreso tra 1 e 45 caratteri";
		}
		return risposta;
	}
	
	private static boolean isUsernameEsistente(Dipendente dipendente)
	{
		String comando = String.format("SELECT Username FROM dipendente WHERE Username = '%s'", dipendente.getUsername());
		try {
			return DAO.cerca(comando);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public static ObservableList<Dipendente> caricaListaDipendente()
	{
		 ObservableList<Dipendente> listaDipendenti = FXCollections.observableArrayList();
    	 ResultSet rs = null;
    	 String comando = "SELECT * FROM dipendente";
    	 rs = DAO.getResultSet(comando);
    	 try {
			while (rs.next())
			 {
				 Dipendente tempDipendente = new Dipendente();
				
				 String username = rs.getString("Username");
				 String agenzia = rs.getString("Agenzia");
				 String nome = rs.getString("Nome");
				 String cognome = rs.getString("Cognome");
				 String telefono = rs.getString("Telefono");
				 
				 tempDipendente.setUsername(username);
				 tempDipendente.setAgenzia(agenzia);
				 tempDipendente.setNome(nome);
				 tempDipendente.setCognome(cognome);
				 tempDipendente.setTelefono(telefono);
				
				 
				 listaDipendenti.add(tempDipendente);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    	 return listaDipendenti;
	}
}
