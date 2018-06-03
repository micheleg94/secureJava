package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Auto;
import model.DAO;
import utility.Verificatore;

public class AutoController 
{
	public AutoController(){}
	public static ObservableList<Auto> getListaAuto()
    {
		return caricaListaAuto();
    }
	public static boolean aggiungiAuto(Auto auto)
	{
		String comando = String.format("INSERT INTO `auto` (`Targa`, `Fascia`, `Modello`, `Agenzia`, `Stato`, `Chilometraggio`) VALUES ('%s', %d, '%s', '%s', %d, %d)", auto.getTarga(),auto.getFascia(),auto.getModello(),auto.getAgenzia(),auto.getStato(),auto.getChilometraggio());
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	public static boolean eliminaAuto(Auto auto)
	{
		String comando = String.format("DELETE FROM `auto` WHERE `Targa` IN ('%s')", auto.getTarga());
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	public static int getCostoKmFromAuto(String targa)
	{
		int risposta = 0;
		int fascia = getFasciaFromAuto(targa);
		String comando = String.format("SELECT PrezzoKm FROM `fascia` WHERE `idFascia` = %d",fascia);
		try {
			risposta = DAO.cercaI(comando);
		} catch (SQLException e) {
			risposta = 100000;
		}
		return risposta;
	}
	public static int getFasciaFromAuto(String targaCerca)
	{
		int risposta = 0;
		Auto auto = new Auto();
		String comando = String.format("SELECT * FROM `auto` WHERE `Targa` = '%s'",targaCerca);
		ResultSet rs = DAO.getResultSet(comando);
	   	 try {
				while (rs.next())
				 {
					 Auto tempAuto = new Auto();
					
					 String targa = rs.getString("Targa");
					 int fascia = rs.getInt("Fascia");
					 String modello = rs.getString("Modello");
					 String agenzia = rs.getString("Agenzia");
					 int stato = rs.getInt("Stato");
					 int km = rs.getInt("Chilometraggio");
					 
					 tempAuto.setTarga(targa);
					 tempAuto.setFascia(fascia);
					 tempAuto.setModello(modello);
					 tempAuto.setAgenzia(agenzia);
					 tempAuto.setStato(stato);
					 tempAuto.setChilometraggio(km);
					 
					 auto = tempAuto;
					 //Mi basta la prima auto che trovo
					 break;
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	 risposta = auto.getFascia();
		return risposta;
	}
	public static boolean settaManutenzioneAuto(Auto auto, int stato)
	{
		//Aggiorno l'auto nel DB
		String comando = String.format("UPDATE `auto` SET `Stato` = '%d' WHERE `Targa` = '%s';", stato,auto.getTarga());
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	
	public static boolean settaAgenziaAuto(String targa, String agenzia)
	{
		boolean risposta = false;
		//Aggiorno l'auto nel DB
		String comando = String.format("UPDATE `auto` SET `Agenzia` = '%s' WHERE `Targa` = '%s';",agenzia, targa);
		if (DAO.esegui(comando))
		{
			risposta = true;
		}else
		{
			risposta = false;
		}
		return risposta;
	}
	
	public static boolean settaKmAuto(String targa, int km)
	{
		boolean risposta = false;
		//Aggiorno l'auto nel DB
		String comando = String.format("UPDATE `auto` SET `Chilometraggio` = '%d' WHERE `Targa` = '%s';",km, targa);
		if (DAO.esegui(comando))
		{
			risposta = true;
		}else
		{
			risposta = false;
		}
		return risposta;
	}
	
	public static boolean liberaAuto(String targa)
	{
		boolean risposta = false;
		//Aggiorno l'auto nel DB
		String comando = String.format("UPDATE `auto` SET `Stato` = '1' WHERE `Targa` = '%s';",targa);
		if (DAO.esegui(comando))
		{
			risposta = true;
		}else
		{
			risposta = false;
		}
		return risposta;
	}


	public static String verificaAuto(Auto auto)
	{
		String risposta = "Errore!";
		
		if (auto.getTarga().length() == 7)
		{
			if (Verificatore.controllaTarga(auto.getTarga()))
			{
				if (!targaEsistente(auto))
				{
					if (auto.getModello().length() > 0 && auto.getModello().length() < 30)
					{
						if (auto.getChilometraggio() < 10000000)
						{
							risposta = "";
						}else
						{
							risposta = "Non ci credo che hai fatto più di 10000000 chilometri!!!";
						}
					}else
					{
						risposta = "Il modello deve essere compreso tra 0 e 30 caratteri";
					}
				}else
				{
					risposta = "Un'auto con questa targa è già presente";
				}
			}else
			{
				risposta = "Targa non valida";
			}
		}else
		{
			risposta = "La targa deve essere di 7 caratteri";
		}
		
		return risposta;
	}
	
	private static boolean targaEsistente(Auto auto)
	{
		String comando = String.format("SELECT Targa FROM auto WHERE Targa = '%s'", auto.getTarga());
		try {
			return DAO.cerca(comando);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static ObservableList<Auto> caricaListaAuto()
	{
		ObservableList<Auto> listaAuto = FXCollections.observableArrayList();
	   	 ResultSet rs = null;
	   	 String comando = "SELECT * FROM auto";
	   	 rs = DAO.getResultSet(comando);
	   	 try {
				while (rs.next())
				 {
					 Auto tempAuto = new Auto();
					
					 String targa = rs.getString("Targa");
					 int fascia = rs.getInt("Fascia");
					 String modello = rs.getString("Modello");
					 String agenzia = rs.getString("Agenzia");
					 int stato = rs.getInt("Stato");
					 int km = rs.getInt("Chilometraggio");
					 
					 tempAuto.setTarga(targa);
					 tempAuto.setFascia(fascia);
					 tempAuto.setModello(modello);
					 tempAuto.setAgenzia(agenzia);
					 tempAuto.setStato(stato);
					 tempAuto.setChilometraggio(km);
					 
					 listaAuto.add(tempAuto);
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	 
	   	 return listaAuto;
	}
	
	public static Auto getAuto(int fasciaCerca, String agenziaCerca)
	{
		Auto auto = new Auto();
		String comando = String.format("SELECT * FROM `auto` WHERE `Fascia` = '%d' AND `Stato` = '1' AND `Agenzia` = '%s'", fasciaCerca,agenziaCerca);
		ResultSet rs = DAO.getResultSet(comando);
	   	 try {
				while (rs.next())
				 {
					 Auto tempAuto = new Auto();
					
					 String targa = rs.getString("Targa");
					 int fascia = rs.getInt("Fascia");
					 String modello = rs.getString("Modello");
					 String agenzia = rs.getString("Agenzia");
					 int stato = rs.getInt("Stato");
					 int km = rs.getInt("Chilometraggio");
					 
					 tempAuto.setTarga(targa);
					 tempAuto.setFascia(fascia);
					 tempAuto.setModello(modello);
					 tempAuto.setAgenzia(agenzia);
					 tempAuto.setStato(stato);
					 tempAuto.setChilometraggio(km);
					 
					 auto = tempAuto;
					 //Mi basta la prima auto che trovo
					 break;
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return auto;
	}

}
