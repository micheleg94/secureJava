package view;

import java.time.LocalDate;

import application.AutoController;
import application.ClienteController;
import application.ContrattoController;
import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Contratto;

public class ChiudiContrattoViewController 
{
	private Stage dialogStage;
	
	@FXML
	private ChoiceBox<String> idContrattoCB;
	
	@FXML
	private Label labelIdContratto;
	@FXML
	private Label labelAuto;
	@FXML
	private Label labelTipoNoleggio;
	@FXML
	private Label labelTipoTariffa;
	@FXML
	private Label labelScrittaKmPrevisti;
	@FXML
	private Label labelKmPrevisti;
	@FXML
	private TextField kmAttualiTF;
	@FXML
	private Label labelTotale;
	@FXML
	private DatePicker dataRientroDP;
	
	private Contratto contrattoSelezionato = new Contratto();
	private int kmAttuali = 0;
	private LocalDate dataSelezionata = LocalDate.now();
	
	private boolean generato = false;
	
	private double totale = 0;
	
	@FXML
	private void initialize()
	{
		configuraPicker();
		configuraTextField();
		configuraDatePicker();
		labelScrittaKmPrevisti.setVisible(false);
		labelKmPrevisti.setVisible(false);
		labelTotale.setText("Premi 'Genera Totale'");
	}

	@FXML
	private void premutoChiudiContratto()
	{
		if (generato)
		{
			if (AutoController.settaKmAuto(ContrattoController.getContrattoApertoFromId(ottieniNumContratto()).getAuto(), kmAttuali))
			{
				if (ContrattoController.chiudiContratto(ottieniNumContratto(),totale))
				{
					Main.lanciaInfo("Chiudi Contratto", "Contratto chiuso");
					dialogStage.close();
				}else
				{
					Main.lanciaWarning("Chiudi Contratto", "Problemi con il database");	
				}
			}else
			{
				Main.lanciaWarning("Chiudi Contratto", "Genera un Totale per poter chiudere il contratto");
			}
		}else
		{
			Main.lanciaWarning("Chiudi Contratto", "Genera un Totale per poter chiudere il contratto");
		}
	}
	
	@FXML
	private void premutoAnnulla()
	{
		dialogStage.close();
	}
	
	@FXML
	private void premutoGeneraTotale()
	{
		if (verificaTotale().equals(""))
		{
			generato = true;
			totale = ContrattoController.getTotaleContratto(contrattoSelezionato.getIdContratto(), kmAttuali, dataSelezionata);
			String scrittaTotale = String.format("%.2f€", ContrattoController.getTotaleContratto(contrattoSelezionato.getIdContratto(), kmAttuali, dataSelezionata));
			labelTotale.setText(scrittaTotale);
		}else
		{
			Main.lanciaWarning("Genera Totale", verificaTotale());
		}
	}
	
	private String verificaTotale()
	{
		String risposta = "Errore!";
		int kmPartenza = contrattoSelezionato.getKmIniziali();
		if (contrattoSelezionato.getIdContratto() != 0)
		{
			if (kmAttuali != -100)
			{
				if (kmAttuali >= kmPartenza)
				{
					risposta = "";
				}else
				{
					risposta = "Hai inserito meno Km di quanti ne aveva l'auto al momento del noleggio ("+String.valueOf(kmPartenza)+")";
				}
			}else
			{
				risposta = "I Km si esprimono in numeri";
			}
		}else
		{
			risposta = "Seleziona un contratto per poterlo chiudere";
		}
		return risposta;
	}
	
	private int ottieniNumContratto()
	{
		int risposta = 0;
		risposta = contrattoSelezionato.getIdContratto();
		return risposta;
	}
	
	
	private void configuraPicker()
	{
		ObservableList<Contratto> listaContratti = ContrattoController.getListaContrattiAperti();
		ObservableList<String> idNomeContratti = FXCollections.observableArrayList();
		for (Contratto contratto:listaContratti)
		{
			idNomeContratti.add(String.valueOf(contratto.getIdContratto())+" - "+ClienteController.getClienteFromCF(contratto.getCliente()).getNome()+" "+ClienteController.getClienteFromCF(contratto.getCliente()).getCognome());
		}
		idContrattoCB.setItems(idNomeContratti);
		idContrattoCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) 
		      {
		    	  int indice = (Integer) number2;
		    	  contrattoSelezionato = ContrattoController.getContrattoApertoFromIndice(indice);
		    	  configuraContrattoTF();
		      }
		    });
	}
	
	private void configuraTextField()
	{
		kmAttualiTF.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observableValue, final String oldValue, final String newValue) 
            {
            	String testo = newValue;
            	int kmTF = -100;
            	try {
					kmTF = Integer.parseInt(testo);
	            	kmAttuali = kmTF;
	            	
				} catch (NumberFormatException e) {
					kmTF = -100;
	            	kmAttuali = kmTF;
				}
            }
        });
	}
	
	private void configuraDatePicker()
	{
		dataRientroDP.setValue(LocalDate.now());
		dataRientroDP.setOnAction(event -> {
		    LocalDate dataScelta = dataRientroDP.getValue();
		    /*
		    if (dataScelta.isBefore(LocalDate.now()))
		    {
		    	dataScelta = LocalDate.now();
		    	dataRientroDP.setValue(dataScelta);
		    }
		    */
		    setDataSelezionata(dataScelta);
		    });
	}
	
	private void configuraContrattoTF()
	{
		labelIdContratto.setText(String.valueOf(contrattoSelezionato.getIdContratto()));
		labelAuto.setText(contrattoSelezionato.getAuto());
		labelTipoNoleggio.setText(contrattoSelezionato.getTipoNoleggio());
		labelTipoTariffa.setText(contrattoSelezionato.getTipoChilometraggio());
		if (contrattoSelezionato.getTipoChilometraggio().equals("Km Limitati"))
		{
			labelScrittaKmPrevisti.setVisible(true);
			labelKmPrevisti.setText(String.valueOf(contrattoSelezionato.getKmPrevisti()));
			labelKmPrevisti.setVisible(true);
			kmAttualiTF.setText(String.valueOf(contrattoSelezionato.getKmIniziali()+contrattoSelezionato.getKmPrevisti()));
		}else
		{
			labelScrittaKmPrevisti.setVisible(false);
			labelKmPrevisti.setVisible(false);
			kmAttualiTF.setText(String.valueOf(contrattoSelezionato.getKmIniziali()));
		}
		
		
	}

	public Stage getDialogStage() {
		return dialogStage;
	}


	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public LocalDate getDataSelezionata() {
		return dataSelezionata;
	}

	public void setDataSelezionata(LocalDate dataSelezionata) {
		this.dataSelezionata = dataSelezionata;
	}
}
