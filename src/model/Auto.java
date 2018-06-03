package model;

import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Auto 
{	
	private final StringProperty 	targa;
	private final IntegerProperty 	fascia;
	private final StringProperty 	modello;
	private final StringProperty 	agenzia;
	private final IntegerProperty 	stato;
	private final IntegerProperty 	chilometraggio;
	private final StringProperty 	nomeAgenzia;
	
	public Auto()
	{
		this.targa = new SimpleStringProperty("");
		this.modello = new SimpleStringProperty("");
		this.agenzia = new SimpleStringProperty("");
		this.fascia = new SimpleIntegerProperty(0);
		this.stato = new SimpleIntegerProperty(0);
		this.chilometraggio = new SimpleIntegerProperty(0);
		this.nomeAgenzia = new SimpleStringProperty("");
		
	}
	
	public StringProperty getNomeAgenziaProperty()
	{
		return nomeAgenzia;
	}

	public StringProperty getTargaProperty() {
		return targa;
	}

	public IntegerProperty getFasciaProperty() {
		return fascia;
	}

	public StringProperty getModelloProperty() {
		return modello;
	}

	public StringProperty getAgenziaProperty() {
		return agenzia;
	}

	public IntegerProperty getStatoProperty() {
		return stato;
	}

	public IntegerProperty getChilometraggioProperty() {
		return chilometraggio;
	}

	public void setTarga(String targa) 
	{
	        this.targa.set(targa);
	}
	
	public void setFascia(int fascia) 
	{
	        this.fascia.set(fascia);
	}
	
	public void setModello(String modello) 
	{
	        this.modello.set(modello);
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
	
	public void setStato(int stato) 
	{
	        this.stato.set(stato);
	}
	
	public void setChilometraggio(int km) 
	{
	        this.chilometraggio.set(km);
	}
	
	public String getTarga()
	{
		return targa.get();
	}

	public int getFascia()
	{
		return fascia.get();
	}
	
	public String getModello()
	{
		return modello.get();
	}
	
	public String getAgenzia()
	{
		return agenzia.get();
	}
	
	public int getChilometraggio()
	{
		return chilometraggio.get();
	}
	
	public int getStato()
	{
		return stato.get();
	}
	
	public StringProperty getStatoStringProperty()
	{
		StringProperty risultato = new SimpleStringProperty("");
		
		switch (stato.get()) {
		case 1:
			risultato.set("Libera");
			break;
		case 2:
			risultato.set("In uso");
			break;
		case 3:
			risultato.set("Ordinaria");
			break;
		case 4:
			risultato.set("Straordinaria");
			break;

		default:
			break;
		}
		
		return risultato;
	}
	

	
}

























