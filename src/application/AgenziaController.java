package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Agenzia;
import model.DAO;
import utility.Verificatore;

public class AgenziaController 
{
	
	public AgenziaController(){}
	public static ObservableList<Agenzia> getListaAgenzia()
    {
		return caricaListaAgenzia();
    }
	
	public static boolean aggiungiAgenzia(Agenzia agenzia)
	{
		String comando = String.format("INSERT INTO `agenzia` (`PartitaIVA`, `Nome`, `Citta`, `Provincia`, `Via`, `Civico`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", agenzia.getPartitaIva(),agenzia.getNome(),agenzia.getCitta(),agenzia.getProvincia(),agenzia.getVia(),agenzia.getCivico());
		if(DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public static boolean eliminaAgenzia(Agenzia agenzia)
	{
		//Elimino l'agenzia dal DB
		String comando = String.format("DELETE FROM `agenzia` WHERE `PartitaIVA` IN ('%s')", agenzia.getPartitaIva());
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public static String verificaAgenzia(Agenzia agenzia)
	{
		String risposta = "Errore!";
		
		if (agenzia.getPartitaIva().length() == 11) {
			if (Verificatore.controllaPartitaIva(agenzia.getPartitaIva())) {
				if (agenzia.getNome().length() > 0 && agenzia.getNome().length() < 30) {
					if (agenzia.getCitta().length() > 0 && agenzia.getCitta().length() < 20) {
						if (Verificatore.controllaProvincia(agenzia.getProvincia())) {
							if (agenzia.getVia().length() > 0 && agenzia.getVia().length() < 30) {
								if (!partitaIvaEsistente(agenzia)) {

									risposta = "";
								} else {
									risposta = "Un'agenzia con questa Partita IVA e' gia' presente";
								}
							} else {
								risposta = "La Via non puo' essere vuota";
							}
						} else {
							risposta = "La Provincia deve essere composta da 2 sole lettere";
						}

					} else

					{
						risposta = "La Citta non puo' essere vuota";
					}
				} else {
					risposta = "Il Nome non puo' essere vuoto";
				}
			} else {
				risposta = "La Partita IVA deve contenere solo cifre";
			}
		} else {
			risposta = "La Partita IVA deve essere di 11 caratteri";
		}

		return risposta;
	}
	
	private static boolean partitaIvaEsistente(Agenzia agenzia)
	{
	String comando = String.format("SELECT PartitaIva FROM agenzia WHERE PartitaIva = '%s'", agenzia.getPartitaIva());
		try {
			return DAO.cerca(comando);
			} catch (SQLException e) {
			e.printStackTrace();
		return false;
			}
	}			

	
	
	
	
	
	
	
	
	public static ObservableList<Agenzia> caricaListaAgenzia()
	{
		ObservableList<Agenzia> listaAgenzia = FXCollections.observableArrayList();
   	 ResultSet rs = null;
   	 String comando = "SELECT * FROM agenzia";
   	 rs = DAO.getResultSet(comando);
   	 try {
			while (rs.next())
			 {
				 Agenzia tempAgenzia = new Agenzia();
				
				 String partitaIva = rs.getString("PartitaIVA");
				 String nome = rs.getString("Nome");
				 String citta = rs.getString("Citta");
				 String provincia = rs.getString("Provincia");
				 String via = rs.getString("Via");
				 String civico = rs.getString("Civico");
				 
				 tempAgenzia.setPartitaIva(partitaIva);
				 tempAgenzia.setNome(nome);
				 tempAgenzia.setCitta(citta);
				 tempAgenzia.setProvincia(provincia);
				 tempAgenzia.setVia(via);
				 tempAgenzia.setCivico(civico);
				 
				 listaAgenzia.add(tempAgenzia);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
		return listaAgenzia;
		
	}
	
	public static boolean isAgenziaSenzaAuto(Agenzia agenzia)
	{
		boolean risposta = false;
		String comando = String.format("SELECT * FROM auto WHERE Agenzia = '%s'", agenzia.getPartitaIva());
		try {
			risposta = !DAO.cerca(comando);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
	public static boolean isAgenziaSenzaDipendenti(Agenzia agenzia)
	{
		boolean risposta = false;
		String comando = String.format("SELECT * FROM dipendente WHERE Agenzia = '%s'", agenzia.getPartitaIva());
		try {
			risposta = !DAO.cerca(comando);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
	public static boolean isAgenziaSenzaContratti(Agenzia agenzia)
	{
		boolean risposta = false;
		String comando = String.format("SELECT * FROM contratto WHERE AgenziaApertura = '%s' OR AgenziaApertura = '%s'", agenzia.getPartitaIva(),agenzia.getPartitaIva());
		try {
			risposta = !DAO.cerca(comando);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risposta;
	}
	
	public static String getPivaFromNome(String nomeAgenzia)
	{
		try {
			return DAO.cercaS("SELECT PartitaIVA FROM agenzia WHERE Nome = '" + nomeAgenzia + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getNomeFromPiva(String piva)
	{
		try {
			return DAO.cercaS("SELECT Nome FROM agenzia WHERE PartitaIVA = '" + piva + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static ArrayList<String> getNomiAgenzie()
	{
		return DAO.getListaString("agenzia", "Nome");
	}
	

}
