package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;
import model.DAO;
import utility.Verificatore;

public class ClienteController {

	public ClienteController(){}
	public static ObservableList<Cliente> getListaCliente()
    {
		return caricaListaCliente();
    }
	
	
	public static Cliente getClienteFromCF(String cf)
	{
		String nome = "",cognome = "",telefono = "";
		int contratto = 0;
		try {
			nome = DAO.cercaS("SELECT Nome FROM `cliente` WHERE `CF` = '"+cf+"'");
			cognome = DAO.cercaS("SELECT Cognome FROM `cliente` WHERE `CF` = '"+cf+"'");
			telefono = DAO.cercaS("SELECT Telefono FROM `cliente` WHERE `CF` = '"+cf+"'");
			contratto= DAO.cercaI("SELECT Contratto FROM `cliente` WHERE `CF` = '"+cf+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setCognome(cognome);
		cliente.setTelefono(telefono);
		cliente.setCF(cf);
		cliente.setContratto(contratto);
		return cliente;
	}
	
	public static boolean aggiungiCliente(Cliente cliente)
	{
		String comando = String.format("INSERT INTO `cliente` (`CF`, `Nome`, `Cognome`, `Telefono`, `Contratto`) VALUES ('%s', '%s', '%s', '%s', %s)",cliente.getCF(),cliente.getNome(),cliente.getCognome(),cliente.getTelefono(),"NULL");
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	private  static ObservableList<Cliente> caricaListaCliente()
	{
		ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();
   	 ResultSet rs = null;
   	 String comando = "SELECT * FROM cliente";
   	 rs = DAO.getResultSet(comando);
   	 try {
			while (rs.next())
			 {
				 Cliente tempCliente = new Cliente();
				
				 String cf = rs.getString("CF");
				 String nome = rs.getString("Nome");
				 String cognome = rs.getString("Cognome");
				 String telefono = rs.getString("Telefono");
				 int contratto = rs.getInt("Contratto");
				 tempCliente.setCF(cf);
				 tempCliente.setNome(nome);
				 tempCliente.setCognome(cognome);
				 tempCliente.setTelefono(telefono);
				 tempCliente.setContratto(contratto);
				 
				 listaCliente.add(tempCliente);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
   	 	
		return listaCliente;
		
	}
	
	public static boolean verificaCF(Cliente cliente)
	{
		boolean risposta = false;
		if (Verificatore.ControllaCF(cliente.getCF()).equals(""))
		{
			risposta = true;
		}
		return risposta;
	}
	
	
	public static ArrayList<String> getDatiCliente()
	{
		
		ObservableList<Cliente> listaCliente = caricaListaCliente();
		ArrayList<String> datiCliente = new ArrayList<String>();
		for (Cliente cliente:listaCliente)
		{
			datiCliente.add(cliente.getCF() + " - " + cliente.getNome() + " " + cliente.getCognome());
		}
		return datiCliente;
	}
	
	
}
