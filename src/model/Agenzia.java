package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Agenzia {

	private final StringProperty 	partitaIva;
	private final StringProperty 	nome;
	private final StringProperty 	citta;
	private final StringProperty 	provincia;
	private final StringProperty 	via;
	private final StringProperty 	civico;
	
	public Agenzia()
	{
		this.partitaIva = new SimpleStringProperty("");
		this.nome = new SimpleStringProperty("");
		this.citta = new SimpleStringProperty("");
		this.provincia = new SimpleStringProperty("");
		this.via = new SimpleStringProperty("");
		this.civico = new SimpleStringProperty("");
	}
	
	public StringProperty getPartitaIvaProperty() {
		return partitaIva;
	}
	
	public StringProperty getNomeProperty() {
		return nome;
	}
	
	public StringProperty getCittaProperty() {
		return citta;
	}
	
	public StringProperty getProvinciaProperty() {
		return provincia;
	}
	
	public StringProperty getViaProperty() {
		return via;
	}
	
	public StringProperty getCivicoProperty() {
		return civico;
	}
	
	public void setPartitaIva(String partitaIva) 
	{
	        this.partitaIva.set(partitaIva);
	}
	
	public void setNome(String nome) 
	{
	        this.nome.set(nome);
	}
	
	public void setCitta(String citta) 
	{
	        this.citta.set(citta);
	}

	public void setProvincia(String provincia) 
	{
	        this.provincia.set(provincia);
	}
	
	public void setVia(String via) 
	{
	        this.via.set(via);
	}
	
	public void setCivico(String civico) 
	{
	        this.civico.set(civico);
	}
	
	public String getPartitaIva()
	{
		return partitaIva.get();
	}
	
	public String getNome()
	{
		return nome.get();
	}

	public String getCitta()
	{
		return citta.get();
	}
	
	public String getProvincia()
	{
		return provincia.get();
	}
	public String getVia()
	{
		return via.get();
	}

	public String getCivico()
	{
		return civico.get();
	}
	

}

	

