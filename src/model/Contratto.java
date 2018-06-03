package model;

import java.sql.SQLException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contratto 
{
	public Contratto() 
	{
		this.idContratto = new SimpleIntegerProperty(0);
		this.auto = new SimpleStringProperty("");
		this.agenziaApertura = new SimpleStringProperty("");
		this.agenziaChiusura = new SimpleStringProperty("");
		this.dataInizio = new SimpleStringProperty("");
		this.dataFine = new SimpleStringProperty("");
		this.totVersato = new SimpleIntegerProperty(0);
		this.kmIniziali = new SimpleIntegerProperty(0);
		this.kmPrevisti = new SimpleIntegerProperty(0);
		this.cliente = new SimpleStringProperty("");
		this.isAperto = new SimpleBooleanProperty(false);
		this.tipoNoleggio = new SimpleStringProperty("");
		this.tipoChilometraggio = new SimpleStringProperty("");
		this.nomeAgenziaAp = new SimpleStringProperty("");
		this.nomeAgenziaCh = new SimpleStringProperty("");
	}

	private IntegerProperty idContratto;
	private StringProperty	auto;
	private StringProperty	agenziaApertura;
	private StringProperty 	agenziaChiusura;
	private StringProperty	dataInizio;
	private StringProperty	dataFine;
	private IntegerProperty	totVersato;
	private IntegerProperty kmIniziali;
	private IntegerProperty kmPrevisti;
	private StringProperty	cliente;
	private BooleanProperty	isAperto;
	private	StringProperty 	tipoNoleggio;
	private	StringProperty	tipoChilometraggio;
	private final StringProperty 	nomeAgenziaAp;
	private final StringProperty 	nomeAgenziaCh;

	
	public StringProperty getNomeAgenziaApProperty()
	{
		return nomeAgenziaAp;
	}
	
	public StringProperty getNomeAgenziaChProperty()
	{
		return nomeAgenziaCh;
	}
	
	public IntegerProperty idContrattoProperty() {
		return this.idContratto;
	}
	
	public int getIdContratto() {
		return this.idContrattoProperty().get();
	}
	
	public void setIdContratto(final int idContratto) {
		this.idContrattoProperty().set(idContratto);
	}
	
	public StringProperty autoProperty() {
		return this.auto;
	}
	
	public java.lang.String getAuto() {
		return this.autoProperty().get();
	}
	
	public void setAuto(final java.lang.String auto) {
		this.autoProperty().set(auto);
	}
	
	public StringProperty agenziaAperturaProperty() {
		return this.agenziaApertura;
	}
	
	public java.lang.String getAgenziaApertura() {
		return this.agenziaAperturaProperty().get();
	}
	
	public void setAgenziaApertura(final java.lang.String agenziaApertura) {
		this.agenziaAperturaProperty().set(agenziaApertura);
		try {
			String nomeAgenzia = DAO.cercaS("SELECT Nome FROM agenzia WHERE PartitaIVA = '" + this.agenziaApertura.get() + "'");
			this.nomeAgenziaAp.set(nomeAgenzia);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public StringProperty agenziaChiusuraProperty() {
		return this.agenziaChiusura;
	}
	
	public java.lang.String getAgenziaChiusura() {
		return this.agenziaChiusuraProperty().get();
	}
	
	public void setAgenziaChiusura(final java.lang.String agenziaChiusura) {
		this.agenziaChiusuraProperty().set(agenziaChiusura);
		try {
			String nomeAgenzia = DAO.cercaS("SELECT Nome FROM agenzia WHERE PartitaIVA = '" + this.agenziaChiusura.get() + "'");
			this.nomeAgenziaCh.set(nomeAgenzia);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public StringProperty dataInizioProperty() {
		return this.dataInizio;
	}
	
	public java.lang.String getDataInizio() {
		return this.dataInizioProperty().get();
	}
	
	public void setDataInizio(final java.lang.String dataInizio) {
		this.dataInizioProperty().set(dataInizio);
	}
	
	public StringProperty dataFineProperty() {
		return this.dataFine;
	}
	
	public java.lang.String getDataFine() {
		return this.dataFineProperty().get();
	}
	
	public void setDataFine(final java.lang.String dataFine) {
		this.dataFineProperty().set(dataFine);
	}
	
	public IntegerProperty totVersatoProperty() {
		return this.totVersato;
	}
	
	public int getTotVersato() {
		return this.totVersatoProperty().get();
	}
	
	public void setTotVersato(final int totVersato) {
		this.totVersatoProperty().set(totVersato);
	}
	
	public IntegerProperty kmInizialiProperty() {
		return this.kmIniziali;
	}
	
	public int getKmIniziali() {
		return this.kmInizialiProperty().get();
	}
	
	public void setKmIniziali(final int kmIniziali) {
		this.kmInizialiProperty().set(kmIniziali);
	}
	
	public StringProperty clienteProperty() {
		return this.cliente;
	}
	
	public java.lang.String getCliente() {
		return this.clienteProperty().get();
	}
	
	public void setCliente(final java.lang.String cliente) {
		this.clienteProperty().set(cliente);
	}
	
	public BooleanProperty isApertoProperty() {
		return this.isAperto;
	}
	
	public boolean isIsAperto() {
		return this.isApertoProperty().get();
	}
	
	public void setIsAperto(final boolean isAperto) {
		this.isApertoProperty().set(isAperto);
	}
	
	public StringProperty tipoNoleggioProperty() {
		return this.tipoNoleggio;
	}
	
	public java.lang.String getTipoNoleggio() {
		return this.tipoNoleggioProperty().get();
	}
	
	public void setTipoNoleggio(final java.lang.String tipoNoleggio) {
		this.tipoNoleggioProperty().set(tipoNoleggio);
	}
	
	public StringProperty tipoChilometraggioProperty() {
		return this.tipoChilometraggio;
	}
	
	public java.lang.String getTipoChilometraggio() {
		return this.tipoChilometraggioProperty().get();
	}
	
	public void setTipoChilometraggio(final java.lang.String tipoChilometraggio) {
		this.tipoChilometraggioProperty().set(tipoChilometraggio);
	}

	public final IntegerProperty kmPrevistiProperty() {
		return this.kmPrevisti;
	}
	

	public final int getKmPrevisti() {
		return this.kmPrevistiProperty().get();
	}
	

	public final void setKmPrevisti(final int kmPrevisti) {
		this.kmPrevistiProperty().set(kmPrevisti);
	}
	
	
}
