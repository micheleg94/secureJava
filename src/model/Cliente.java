package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
	
	private StringProperty CF;
	private StringProperty nome;
	private StringProperty cognome;
	private StringProperty telefono;
	private IntegerProperty contratto;
	
	public Cliente()
	{
		this.CF = new SimpleStringProperty("");
		this.nome = new SimpleStringProperty("");
		this.cognome = new SimpleStringProperty("");
		this.telefono = new SimpleStringProperty("");
		this.contratto = new SimpleIntegerProperty(0);
	}
	
	public StringProperty getCFProperty()
	{
		return CF;
	}
	
	public String getCF()
	{
		return this.CF.get();
	}
	
	public void setCF(String CF)
	{
		this.CF.set(CF);
	}
	
	public StringProperty getNomeProperty()
	{
		return nome;
	}
	
	public String getNome()
	{
		return this.nome.get();
	}
	
	public void setNome(String nome)
	{
		this.nome.set(nome);
	}
	
	public StringProperty getCognomeProperty()
	{
		return cognome;
	}
	
	public String getCognome()
	{
		return this.cognome.get();
	}
	
	public void setCognome(String cognome)
	{
		this.cognome.set(cognome);
	}
	
	public StringProperty getTelefonoProperty()
	{
		return telefono;
	}
	
	public String getTelefono()
	{
		return this.telefono.get();
	}
	
	public void setTelefono(String telefono)
	{
		this.telefono.set(telefono);
	}
	
	public IntegerProperty getContrattoProperty()
	{
		return contratto;
	}
	
	public int getContratto()
	{
		return this.contratto.get();
	}
	
	public void setContratto(int contratto)
	{
		this.contratto.set(contratto);
	}
	

}
