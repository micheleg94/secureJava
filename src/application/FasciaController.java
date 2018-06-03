package application;

import java.util.ArrayList;

import model.DAO;

public class FasciaController 
{
	public static ArrayList<String> getNomiFasce()
	{
		//Ottengo gli idFascia
		ArrayList<Integer> idFasciaPresenti = DAO.getListaInteri("fascia", "idFascia");
		//Ottengo le descrizioni delle fasce
		ArrayList<String> descrizioniPresenti = DAO.getListaString("fascia", "Requisiti");
		//Costruisco la lista "idFascia + requisiti"
		ArrayList<String> idNomi = new ArrayList<String>();
		for (int i=0; i<idFasciaPresenti.size(); i++)
		{
			idNomi.add(idFasciaPresenti.get(i).toString() + " - " + descrizioniPresenti.get(i));
		}
		return idNomi;
	}

}
