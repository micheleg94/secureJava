package model;

import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Dipendente {

	private final StringProperty 	username;
	private final StringProperty 	agenzia;
	private final StringProperty 	nome;
	private final StringProperty 	cognome;
	private final StringProperty 	telefono;
	private final StringProperty 	password;
	private final StringProperty 	nomeAgenzia;
	
	
	public Dipendente()
	{
		this.username = new SimpleStringProperty("");
		this.agenzia = new SimpleStringProperty("");
		this.nome = new SimpleStringProperty("");
		this.cognome = new SimpleStringProperty("");
		this.telefono = new SimpleStringProperty("");
		this.password = new SimpleStringProperty("");
		this.nomeAgenzia = new SimpleStringProperty("");
	}
	
	public StringProperty getAgenziaNomeProperty()
	{
		return nomeAgenzia;
	}

	public StringProperty getUsernameProperty() {
		return username;
	}
	
	public StringProperty getAgenziaProperty() {
		return agenzia;
	}
	
	public StringProperty getNomeProperty() {
		return nome;
	}
	
	public StringProperty getCognomeProperty() {
		return cognome;
	}
	
	public StringProperty getTelefonoProperty() {
		return telefono;
	}
	
	public String getUsername()
	{
		return username.get();
	}
	
	public String getAgenzia()
	{
		return agenzia.get();
	}
	
	public String getNome()
	{
		return nome.get();
	}
	
	public String getCognome()
	{
		return cognome.get();
	}
	
	public String getTelefono()
	{
		return telefono.get();
	}

	public void setUsername(String username) 
	{
	        this.username.set(username);
	}
	
	public void setAgenzia(String agenzia) 
	{
	        this.agenzia.set(agenzia);
			try {
				String nomeAgenzia = DAO.cercaS("SELECT Nome FROM agenzia WHERE PartitaIVA = '" + this.agenzia.get() + "'");
				this.nomeAgenzia.set(nomeAgenzia);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void setNome(String nome) 
	{
	        this.nome.set(nome);
	}
	
	public void setCognome(String cognome) 
	{
	        this.cognome.set(cognome);
	}
	
	public void setTelefono(String telefono) 
	{
	        this.telefono.set(telefono);
	}

	public  StringProperty getPasswordProperty() {
		return this.password;
	}
	
	public String getPassword()
	{
		return this.password.get();
	}
	
	public void setPassword(String password)
	{
		this.password.set(password);
	}
	
}
	
	
	
	