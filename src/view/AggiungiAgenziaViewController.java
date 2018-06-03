package view;

import application.AgenziaController;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Agenzia;

public class AggiungiAgenziaViewController {
	
private Stage dialogStage;
	
	@FXML
	private TextField partitaIvaTF;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cittaTF;
	//@FXML
	//private TextField provinciaTF;
	@FXML
	private TextField viaTF;
	@FXML
	private TextField civicoTF;
	@FXML
	private Button aggiungiAgenziaButton;
	@FXML
	private Button annullaButton;
	@FXML 
	private ChoiceBox<String> provinceCB;
	
	private ObservableList<Agenzia> listaAgenzie;
	
	private ObservableList<String> province = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() 
	{
		configuraPicker();
    }
	
	@FXML
	private void aggiungiAgenzia()
	{
		
		
			//Ottengo i dati inseriti
			 String partitaIva= partitaIvaTF.getText();
			 String nome= nomeTF.getText();
			 String citta = cittaTF.getText();
			 String provincia = provinceCB.getSelectionModel().getSelectedItem(); 
			 String via = viaTF.getText();
			 String civico;
			 if((civicoTF.getText().length())==0)
			 { 
				 civico = "s.n.c.";
			 }else
			 {
					 civico = civicoTF.getText();
			 } 
					
			 //Creo l'agenzia da aggiungere
			 Agenzia tempAgenzia = new Agenzia();
			 tempAgenzia.setPartitaIva(partitaIva);
			 tempAgenzia.setNome(nome);
			 tempAgenzia.setCitta(citta);
			 tempAgenzia.setProvincia(provincia);
			 tempAgenzia.setVia(via);
			 tempAgenzia.setCivico(civico);
			 
			 //Se i dati sono corretti
			 if (AgenziaController.verificaAgenzia(tempAgenzia).equals(""))
				{
					 
					 if (AgenziaController.aggiungiAgenzia(tempAgenzia))
					 {
						 Main.lanciaInfo("Nuova Agenzia", "Agenzia aggiunta!");
						 listaAgenzie.add(tempAgenzia);
						 dialogStage.close();
					 }else
					 {
						 Main.lanciaWarning("Nuova Agenzia", "Agenzia NON aggiunta!");
					 }
				}else
				{
					Main.lanciaWarning("Impossibile aggiungere agenzia", AgenziaController.verificaAgenzia(tempAgenzia));
				}
		}

	private void configuraPicker()
	{
		String[] listaProvince = {"AG","AL","AN","AO","AR","AP","AT","AV","BA","BT","BL","BN","BG","BI","BO","BZ","BS","BR","CA","CL","CB","CE","CT","CZ","CH","CO","CS","CR","KR","CN","EN","FM","FE","FI","FG","FC","FR","GE","GO","GR","IM","IS","AQ","SP","LT","LE","LC","LI","LO","LU","MC","MN","MS","MT","ME","MI","MO","MB","NA","NO","NU","OR","PD","PA","PR","PV","PG","PU","PE","PC","PI","PT","PN","PZ","PO","RG","RA","RC","RE","RI","RN","RM","RO","SA","SS","SV","SI","SR","SO","TA","TE","TR","TO","TP","TN","TV","TS","UD","VA","VE","VB","VC","VR","VV","VI","VT"};
		for (String prov: listaProvince)
		{
			province.add(prov);
		}
		provinceCB.setItems(province);	
		provinceCB.getSelectionModel().selectFirst();
	}
	
	@FXML
	private void premutAnnulla()
	{
		 dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage) 
	    {
	        this.dialogStage = dialogStage;
	    }

	public ObservableList<Agenzia> getListaAgenzia() {
			return listaAgenzie;
		}

	public void setListaAgenzie(ObservableList<Agenzia> listaAgenzie) {
			this.listaAgenzie = listaAgenzie;
		}
}
