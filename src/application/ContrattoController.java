package application;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Auto;
import model.Cliente;
import model.Contratto;
import model.DAO;
import utility.DateUtils;

public class ContrattoController 
{
	private int idContratto;
	private int totVersato;
	private int kmIniziali;
	private int kmPrevisti;
	private boolean isAperto;
	private Auto auto;
	private String agenziaApertura;
	private static String agenziaChiusura;
	private String dataInizio;
	private String dataFine;
	private String cliente;
	private String tipoNoleggio;
	private String tipoChilometraggio;
	private String clienteNome;
	private String clienteCognome;
	private String clienteTelefono;
	
	public boolean apriContratto()
	{
		//Inserisco il cliente (se non esistente) nel DB
		inserisciSeNonEsistente(this.cliente);
		//Inserisco il contratto nel DB
		String comando = String.format("INSERT INTO `contratto` (`idContratto`, `Auto`, `AgenziaApertura`, `AgenziaChiusura`, `Inizio`, `Fine`, `TotaleVersato`, `KmIniziali`, `Cliente`, `isAperto`, `TipoNoleggio`, `TipoChilometraggio`, `KmPrevisti`) VALUES ('%d', '%s', '%s', '%s', '%s', '%s', '%d', '%d', '%s', '%d', '%s', '%s','%d')", this.idContratto,this.auto.getTarga(),this.agenziaApertura,ContrattoController.agenziaChiusura,this.dataInizio,this.dataFine,this.totVersato,this.kmIniziali,this.cliente,(this.isAperto) ? 1 : 0,this.tipoNoleggio,this.tipoChilometraggio, this.kmPrevisti);
		if (DAO.esegui(comando))
		{
			//Aggiorno l'auto scelta come "In Uso"
			comando = String.format("UPDATE `auto` SET `Stato` = '2' WHERE `Targa` = '%s'",this.auto.getTarga());
			if (DAO.esegui(comando))
			{
				
				//Aggiorno il cliente col riferimento al contratto
				comando = String.format("UPDATE `cliente` SET `Contratto` = '%d' WHERE `CF` = '%s'",this.idContratto, this.cliente);
				if (DAO.esegui(comando))
				{
					return true;
					
				}else
				{
					return false;
				}
				
			}else
			{
				return false;
			}
		}else
		{
			return false;
		}
	}
	
	private void inserisciSeNonEsistente(String cf)
	{
		boolean esistente = false;
		try {
			esistente = DAO.cerca("SELECT * FROM cliente WHERE `CF` = '"+this.cliente+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!esistente)
		{
			Cliente clienteNuovo = new Cliente();
			clienteNuovo.setNome(this.clienteNome);
			clienteNuovo.setCognome(this.clienteCognome);
			clienteNuovo.setTelefono(this.clienteTelefono);
			clienteNuovo.setCF(this.cliente);
			ClienteController.aggiungiCliente(clienteNuovo);
		}
	}
	
	public void assegnaKmIniziali()
	{
		this.kmIniziali = auto.getChilometraggio();
	}
	
	/**
	 * Genera un numero di contratto contando i contratti presenti e aggiungendo 1
	 * @return Il primo idContratto libero
	 */
	public int generaNumContratto()
	{
		int numContratto = 0;
		String comando = "SELECT * FROM contratto";
		ResultSet rs = DAO.getResultSet(comando);
		try {
			while (rs.next())
			{
				numContratto++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.idContratto = numContratto + 1;
		return this.idContratto;
	}
	
	/**
	 * Genera un'acconto moltiplicando la fascia della macchina per 10
	 * @return L'acconto dovuto
	 */
	public int generaAcconto()
	{
		return (this.auto.getFascia() * 10);
	}
	
	public Auto getAuto()
	{
		return this.auto;
	}
	
	public static boolean chiudiContratto(int idContratto, double totale)
	{
		//Reimposto l'auto come libera
		if (AutoController.liberaAuto(ContrattoController.getContrattoApertoFromId(idContratto).getAuto()))
		{
			if (impostaClienteContrattoNull(idContratto))
			{
				if (AutoController.settaAgenziaAuto(ContrattoController.getContrattoApertoFromId(idContratto).getAuto(), ContrattoController.getContrattoApertoFromId(idContratto).getAgenziaChiusura()))
				{
					if (impostaContrattoChiuso(idContratto,totale))
					{
						return true;
					}else
					{
						Main.lanciaWarning("Chiudi Contratto", "Problemi col Database");
						return false;
					}
				}else
				{
					Main.lanciaWarning("Chiudi Contratto", "Problemi col Database");
                    return false;				
				}
			}else
			{
				Main.lanciaWarning("ChiudiContratto", "Problemi col Database");
				return false;
			}
		}else
		{
			Main.lanciaWarning("ChiudiContratto", "Problemi col Database");
			return false;
		}
		

		
	}
	
	private static boolean impostaClienteContrattoNull(int idContratto)
	{
		String cliente = ContrattoController.getContrattoApertoFromId(idContratto).getCliente();
		String comando = String.format("UPDATE `cliente` SET `Contratto` = NULL WHERE `CF` = '%s';", cliente);
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}   
	
	private static boolean impostaContrattoChiuso(int idContratto, double totale)
	{
		//Imposto il contratto come chiuso
		double totalePiuAcconto = (double) ContrattoController.getContrattoApertoFromId(idContratto).getTotVersato() + totale;
		String comando = String.format(Locale.US,"UPDATE `contratto` SET `isAperto` = false, `TotaleVersato` = %.2f WHERE `idContratto` = '%s'", totalePiuAcconto,idContratto);
		if (DAO.esegui(comando))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * Genera una auto valida per il contratto
	 * @param fascia La fascia desiderata per l'auto
	 * @param agenzia L'agenzia in cui cercare l'auto
	 */
	public void setAuto(int fascia, String agenzia) 
	{
		Auto autoAssegnata = AutoController.getAuto(fascia, agenzia);
		this.agenziaApertura = agenzia;
		this.auto = autoAssegnata;
	}
	
	public String verificaContratto()
	{
		/*
		 * Verifiche da fare per aprire un contratto:
		 * - Controllare che sia presente una auto che soddisfi i requisiti (libera, agenzia)
		 * - Controllare che la data di inizio sia superiore a oggi
		 * - Controllare che il cliente non abbia già un contratto aperto
		 * - Controllare il codice fiscale del cliente
		 */
		String risposta = "Errore";
		if (this.auto.getTarga().equals(""))
		{
			risposta = "Non è presente un auto libera nell'agenzia selezionata";
		}else
		{
			if (!verificaData())
			{
				risposta = "La data inserita è precedente ad oggi";
			}else
			{
				//System.out.println("============================"+ClienteController.getClienteFromCF(this.cliente).getContratto());
				if (ClienteController.getClienteFromCF(this.cliente).getContratto() != 0)
				{
					risposta = "Questo cliente ha già un contratto aperto";
				}else
				{
					if (!ClienteController.verificaCF(ClienteController.getClienteFromCF(this.cliente)))
					{
						risposta = "Codice Fiscale non valido";
					}else
					{
						risposta = "";
					}
				}
			}
		}
		return risposta;
	}
	
	/**
	 * Verifica la data
	 * @return true se this.dataInizio è superiore alla data corrente
	 */
	private boolean verificaData()
	{
		boolean risposta;
		String string = this.dataInizio;
		System.out.println("this.dataInizio = "+this.dataInizio);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
		Date oggi = Date.from(Instant.now());
		oggi = impostaMezzanotte(oggi);
		Date date = Date.from(Instant.now());
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Data da controllare: " + date.toString() + " Oggi: "+oggi.toString());
		
		if (date.before(oggi))
		{
			risposta = false;
		}else
		{
			risposta = true;
		}
		return risposta;
	}
	
	public static Date impostaMezzanotte(Date date) {
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( date );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();
	}
	
	public static ObservableList<Contratto> getListaContratti()
    {
		return caricaListaContratti(false);
    }
	
	public static ObservableList<Contratto> getListaContrattiAperti()
	{
		return caricaListaContratti(true);
	}
	
	private static ObservableList<Contratto> caricaListaContratti(boolean aperti)
	{
		 ObservableList<Contratto> listaContratti = FXCollections.observableArrayList();
	   	 ResultSet rs = null;
	   	 String comando = "";
	   	 if (aperti)
	   	 {
	   		comando = "SELECT * FROM contratto WHERE isAperto = true";
	   	 }else
	   	 {
	   		comando = "SELECT * FROM contratto";
	   	 }
	   	 rs = DAO.getResultSet(comando);
	   	 try {
				while (rs.next())
				 {
					 Contratto tempContratto = new Contratto();
					 
					 int idContratto = rs.getInt("idContratto");
					 int totVersato = rs.getInt("TotaleVersato");
					 int km = rs.getInt("KmIniziali");
					 int kmPrevisti = rs.getInt("KmPrevisti");
					 String auto = rs.getString("Auto");
					 String apertura = rs.getString("AgenziaApertura");
					 String chiusura = rs.getString("AgenziaChiusura");
					 String inizio = rs.getDate("Inizio").toString();
					 String fine = rs.getDate("Fine").toString();
					 String cliente = rs.getString("Cliente");
					 String noleggio = rs.getString("TipoNoleggio");
					 String chilometraggio = rs.getString("TipoChilometraggio");
					 boolean isAperto = rs.getBoolean("isAperto");
					 
					 tempContratto.setIdContratto(idContratto);
					 tempContratto.setTotVersato(totVersato);
					 tempContratto.setKmIniziali(km);
					 tempContratto.setKmPrevisti(kmPrevisti);
					 tempContratto.setAuto(auto);
					 tempContratto.setAgenziaApertura(apertura);
					 tempContratto.setAgenziaChiusura(chiusura);
					 tempContratto.setDataInizio(inizio);
					 tempContratto.setDataFine(fine);
					 tempContratto.setCliente(cliente);
					 tempContratto.setTipoNoleggio(noleggio);
					 tempContratto.setTipoChilometraggio(chilometraggio);
					 tempContratto.setIsAperto(isAperto);
					 
					 listaContratti.add(tempContratto);
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   	 
	   	 return listaContratti;
	}
	
	private static int getKmPercorsi(int kmAttuali, Contratto contratto)
	{
		int risposta = 0;
		risposta = kmAttuali - contratto.getKmIniziali();
		return risposta;
	}
	
	public static double getTotaleContratto(int idContratto, int kmAttuali, LocalDate dataRientroLocal)
	{
		double totale = 0;
		Contratto contratto = ContrattoController.getContrattoApertoFromId(idContratto);
		double kmPercorsi = ContrattoController.getKmPercorsi(kmAttuali, contratto);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
		Date dataFine = Date.from(Instant.now());
		Date dataRientro = DateUtils.asDate(dataRientroLocal);
		try {
			dataFine = format.parse(contratto.getDataFine());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		//Verifico il tipo di noleggio
		if (contratto.getTipoNoleggio().equals("Settimanale"))
		{
			//Contratto Settimanale
			if (dataRientro.after(dataFine))
			{
				//E' stata sforata la data
				int giorniSforati = (int)getDateDiff(dataFine,dataRientro,TimeUnit.DAYS);
				totale += 70+(7*giorniSforati);
				
			}else
			{
				totale += 70;
			}
		}else
		{
			//Contratto Giornaliero
			if (dataRientro.after(dataFine))
			{
				//E' stata sforata la data
				int giorniSforati = (int)getDateDiff(dataFine,dataRientro,TimeUnit.DAYS);
				totale += 10+(5*giorniSforati);
			}else
			{
				totale += 10;
			}
		}
		
		//Verifico il tipo di chilometraggio
		if (contratto.getTipoChilometraggio().equals("Km Illimitati"))
		{
			//Km Illimitati
			totale += (AutoController.getCostoKmFromAuto(contratto.getAuto()) * kmPercorsi)/10;
		}else
		{
			//Km Limitati
			if (kmPercorsi > contratto.getKmPrevisti())
			{
				//Sono stati sforati i Km
				totale += ((AutoController.getCostoKmFromAuto(contratto.getAuto()) * kmPercorsi)/10)*0.7 + (0.8*kmPercorsi);
			}else
			{
				totale = ((AutoController.getCostoKmFromAuto(contratto.getAuto()) * kmPercorsi)/10)*0.7;
			}
		}
		
		//Aggiungo la quota della fascia
		totale += AutoController.getFasciaFromAuto(contratto.getAuto()) * 10;
		
		//Sottraggo l'acconto
		totale -= contratto.getTotVersato();
		
		//risposta = String.format("%.2f", totale);;
		return totale;
		
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static Contratto getContrattoApertoFromId(int id)
	{
		Contratto contratto = new Contratto();
		//Restituire un contratto da un ID
		ObservableList<Contratto> listaContratti = getListaContrattiAperti();
		for (Contratto contrattoCiclo:listaContratti)
		{
			if (contrattoCiclo.getIdContratto() == id)
			{
				contratto = contrattoCiclo;
			}
		}
		return contratto;
	}
	
	public static Contratto getContrattoApertoFromIndice(int indice)
	{
		Contratto contratto = new Contratto();
		ObservableList<Contratto> listaContratti = getListaContrattiAperti();
		contratto = listaContratti.get(indice);
	   	 return contratto;
	}

	public int getIdContratto() {
		return idContratto;
	}

	public void setIdContratto(int idContratto) {
		this.idContratto = idContratto;
	}

	public int getTotVersato() {
		return totVersato;
	}

	public void setTotVersato(int totVersato) {
		this.totVersato = totVersato;
	}

	public int getKmIniziali() {
		return kmIniziali;
	}

	public void setKmIniziali(int kmIniziali) {
		this.kmIniziali = kmIniziali;
	}

	public boolean isAperto() {
		return isAperto;
	}

	public void setAperto(boolean isAperto) {
		this.isAperto = isAperto;
	}

	public String getAgenziaApertura() {
		return agenziaApertura;
	}

	public void setAgenziaApertura(String agenziaApertura) {
		this.agenziaApertura = agenziaApertura;
	}

	public String getAgenziaChiusura() {
		return agenziaChiusura;
	}

	public void setAgenziaChiusura(String agenziaChiusura) {
		ContrattoController.agenziaChiusura = agenziaChiusura;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getTipoNoleggio() {
		return tipoNoleggio;
	}

	public void setTipoNoleggio(String tipoNoleggio) {
		this.tipoNoleggio = tipoNoleggio;
	}

	public String getTipoChilometraggio() {
		return tipoChilometraggio;
	}

	public void setTipoChilometraggio(String tipoChilometraggio) {
		this.tipoChilometraggio = tipoChilometraggio;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public void setClienteCognome(String clienteCognome) {
		this.clienteCognome = clienteCognome;
	}

	public void setClienteTelefono(String clienteTelefono) {
		this.clienteTelefono = clienteTelefono;
	}
	public int getKmPrevisti() {
		return kmPrevisti;
	}

	public void setKmPrevisti(int kmPrevisti) {
		this.kmPrevisti = kmPrevisti;
	}
}
