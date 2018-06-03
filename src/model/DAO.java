
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAO {
	private static Connection connessione;

	//Inizializzatore
	/**
     * Connessione al database
     * @exception ClassNotFoundException Quando si cerca di accederead una classe che in realt� non esiste.
     */
	public static Connection connetti() throws ClassNotFoundException
	{

		try{
                Class.forName("com.mysql.jdbc.Driver");
                connessione = DriverManager.getConnection("jdbc:mysql://localhost:3306/carloan", "root", "");
                return connessione;
                }
                catch (ClassNotFoundException | SQLException e) {
                                System.out.println(e);
                return null;
                }       

	}
	
	public static boolean isDBesistente(String dbName){

	    try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connessione2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
	        ResultSet resultSet = connessione2.getMetaData().getCatalogs();

	        while (resultSet.next()) {

	          String databaseName = resultSet.getString(1);
	            if(databaseName.equals(dbName)){
	                return true;
	            }
	        }
	        resultSet.close();

	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }

	    return false;
	}
	
	public static void importaSeed() throws SQLException, FileNotFoundException
	{
		InputStream in = new FileInputStream("carloan.sql");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "");
		Scanner s = new Scanner(in);
		s.useDelimiter("(;(\r)?\n)|(--\n)");
		Statement st = null;
		st = conn.createStatement();
		st.execute("CREATE SCHEMA carloan");
		st = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carloan", "root", "");
		try
		{
			st = conn.createStatement();
			while (s.hasNext())
			{
				String line = s.next();
				if (line.startsWith("/*!") && line.endsWith("*/"))
				{
					int i = line.indexOf(' ');
					line = line.substring(i + 1, line.length() - " */".length());
				}

				if (line.trim().length() > 0)
				{
					st.execute(line);
				}
			}
		}
		finally
		{
			if (st != null) st.close();
		}
		s.close();
	}


	/**
     * Esegue i predicati SQL
     * @param SQLString � la query da eseguire nel database.
     * @return true Quando non viene rilevata un' eccezione.
     * @return false Quando viene rilevata un'eccezione.
     */
	public static boolean esegui(String SQLString)
	{
		//Crea un oggetto per le operazioni sul database
		try {
			//Connessione
			Statement st = connessione.createStatement( );
			System.out.println("Database -> Esecuzione predicato SQL: "+ SQLString);		
			st.executeUpdate(SQLString);
			return true;
		} catch (SQLException e) {
			//ERRORE: restituisce falso
			e.printStackTrace();
			System.out.println("Errore: Impossibile aggiornare il database.");
			return false;
		}
	}
        
	
	/**
     * Effettua una ricerca dei predicati sql all'interno del database
     * @param SQLString � la query da eseguire nel database.
     * @exception SQLException Un'eccezione che fornisce informazioni su un errore di accesso al database o altri errori.
     * @return true Quando non viene rilevata un' eccezione.
     * @return false Quando viene rilevata un'eccezione.
     */
        public static boolean cerca(String SQLString) throws SQLException{
                    PreparedStatement pst=null;
                    ResultSet rs=null;
                    pst=connessione.prepareStatement(SQLString);
                    System.out.println("Database -> Esecuzione predicato SQL: "+ SQLString);
                    rs=pst.executeQuery(SQLString);
                        
                    if(rs.next()){
                        return true;
        
                    }else{
                        return false;
                }
        }
        
        
        /**
         * Effettua una ricerca dei predicati sql all'interno del database (cerca user)
         * @param SQLString Predicato sql
         * @exception SQLException Un'eccezione che fornisce informazioni su un errore di accesso al database o altri errori.
         * @return ris Restituisce 1 se ha trovato l'elemento oppure 0 altrimenti.
         */
        public static String cercaS(String SQLString) throws SQLException{
                    PreparedStatement pst=null;
                    ResultSet rs=null;
                    String ris = null;
                    
                    pst=connessione.prepareStatement(SQLString);
                    System.out.println("Database -> Esecuzione predicato SQL: "+ SQLString);
                    rs=pst.executeQuery(SQLString);
                        
                    if(rs.next()){
                         ris=rs.getString(1);
        
                    }else{
                    System.out.println("elemento non trovato");

                    }
                     System.out.println(ris);
                    return ris;
        }
        
        public static float cercaF(String SQLString) throws SQLException{
                    PreparedStatement pst=null;
                    ResultSet rs=null;
                    float ris = 0;
                    
                    pst=connessione.prepareStatement(SQLString);
                    System.out.println("Database -> Esecuzione predicato SQL: "+ SQLString);
                    rs=pst.executeQuery(SQLString);
                        
                    if(rs.next()){
                         ris=rs.getFloat(1);
        
                    }else{
                    System.out.println("elemento non trovato");

                    }
                     System.out.println(ris);
                    return ris;
        }
        
        /**
         * Preleva la mail-list dal database.
         * @param querytext Predicato sql.
         * @param colonna Contiene l'identificativo degli utenti a cui deve essere inviata la mail.
         * @exception SQLException Un'eccezione che fornisce informazioni su un errore di accesso al database o altri errori.
         * @return arr Contiene la lista degli indirizzi email.
         */
        
         public static ArrayList<String> caricaListaId(String queryText,String colonna) throws SQLException{
            PreparedStatement pst=null;
                    ResultSet rs=null;
                    //String ris = null;
                    ArrayList<String> arr=new ArrayList<String>();
                    

                    pst=connessione.prepareStatement(queryText);
                    System.out.println("Database -> Esecuzione predicato SQL: "+ queryText);
                    rs=pst.executeQuery(queryText);
                    System.out.println("Database -> Esecuzione predicato SQL: "+ rs);
                    while(rs.next()){
                        arr.add(rs.getString(colonna));
                        
        
                    }
                     System.out.println("Database -> Esecuzione predicato SQL: "+ arr);

                       return arr;
         }
         
         
        
         /**
          * Effettua una ricerca dei predicati sql all'interno del database (cerca id)
          * @param SQLString Predicato sql
          * @exception SQLException Un'eccezione che fornisce informazioni su un errore di accesso al database o altri errori.
          * @return ris Restituisce 1 se ha trovato l'elemento oppure 0 altrimenti.
          */
        
        
         public static int cercaI(String SQLString) throws SQLException{
                    PreparedStatement pst=null;
                    ResultSet rs=null;
                    int ris = 0 ;
                    
                    pst=connessione.prepareStatement(SQLString);
                    System.out.println("Database -> Esecuzione predicato SQL: "+ SQLString);
                    rs=pst.executeQuery(SQLString);
                        
                    if(rs.next()){
                         ris=rs.getInt(1);
        
                    }else{
                    System.out.println("elemento non trovato");

                    }
                    System.out.println(ris);

                    return ris;

        }
	

         

     
     public static ObservableList<Dipendente> getListaDipendenti()
     {
    	 ObservableList<Dipendente> listaDipendenti = FXCollections.observableArrayList();
    	 ResultSet rs = null;
    	 String comando = "SELECT * FROM dipendente";
    	 rs = DAO.getResultSet(comando);
    	 try {
			while (rs.next())
			 {
				 Dipendente tempDipendente = new Dipendente();
				
				 String username = rs.getString("Username");
				 String agenzia = rs.getString("Agenzia");
				 String nome = rs.getString("Nome");
				 String cognome = rs.getString("Cognome");
				 String telefono = rs.getString("Telefono");
				 
				 tempDipendente.setUsername(username);
				 tempDipendente.setAgenzia(agenzia);
				 tempDipendente.setNome(nome);
				 tempDipendente.setCognome(cognome);
				 tempDipendente.setTelefono(telefono);
				
				 
				 listaDipendenti.add(tempDipendente);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    	 return listaDipendenti;
     }


     
     public static ArrayList<Integer> getListaInteri(String tabella, String colonna)
     {
    	 ArrayList<Integer> lista = new ArrayList<Integer>();
    	 
    	 String comando = String.format("SELECT %s FROM %s", colonna,tabella);
    	 ResultSet rs = DAO.getResultSet(comando);
    	 if (rs != null)
    	 {
    		 try {
				while (rs.next())
				 {
					 lista.add(rs.getInt(1));
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	 }
    	 return lista;
     }
     
     public static ArrayList<String> getListaString(String tabella, String colonna)
     {
    	 ArrayList<String> lista = new ArrayList<String>();
    	 
    	 String comando = String.format("SELECT %s FROM %s", colonna,tabella);
    	 ResultSet rs = DAO.getResultSet(comando);
    	 if (rs != null)
    	 {
    		 try {
				while (rs.next())
				 {
					 lista.add(rs.getString(1));
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	 }
    	 return lista;
     }




	/**
	 * Esegue una query sul database e restituisce il relativo ResultSet
	 * @param queryTXT
	 * @return AgroludosTableModel
	 * 
	 */
	public static ResultSet getResultSet(String queryText)
	{
	
		Statement enunciato;
		try {
			
			//Crea uno statement per l'interrogazione del database
			enunciato = connessione.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
			//Trace
			System.out.println("Database -> Interrogazione SQL: " + queryText);		
			
			//Crea un ResultSet eseguendo l'interrogazione desiderata
			ResultSet tabellina = enunciato.executeQuery(queryText);
			
			return tabellina;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**Metodo da applicare ai valori di tipo stringa prima dell'inserimento nel database
	 * 
	 * @param stringaInput
	 */
	public static String string2sqlstring(String stringaInput)
	{
		String stringaOutput;
		
		//Rimedio bug apostrofo
		stringaOutput=stringaInput.replaceAll("'", "''");
		
		
		return stringaOutput;
	}

	public void finalize()
	{
		disconnetti();
	}

	/**Metodo per disconnettersi dal database.
	 * @exception SQLException Un'eccezione che fornisce informazioni su un errore di accesso al database o altri errori.
	 */

	public void disconnetti()
	{
		try {
			System.out.println("Database -> Disconnessione.");		
			connessione.close();
		} catch (SQLException e) {
			System.out.println("Impossibile terminare la connessione");
			e.printStackTrace();
		}	
		
	}

}
