package com.AITAM.demo.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.AITAM.demo.bean.NotificationBean;

public class Notification {
private final static Logger LOGGER = Logger.getLogger("Notification"); 
public static void pushNotification(NotificationBean n) throws ClassNotFoundException, SQLException, JSONException, IOException{
	String msg=null;
	String From =null;
	String Task = null;
	Connection conn= Connect.connect();
	ResultSet rs= null;
	Statement st=null;

	// Notification Message
	st= conn.createStatement();
	rs= st.executeQuery("select NAME from AITAM_EMPLOYEE where ID=" + n.getFrom());
	while(rs.next()){
		From= rs.getString(1);
	}
	rs= st.executeQuery("select TITLE from AITAM_TASK where TASK_ID=" + n.getTask());
	while(rs.next()){
		Task= rs.getString(1);
	}
	switch(n.getId()){
	case 1: msg= "New Task: "+ From+ " assigned you AI '"+ Task+"'";
	break;
	case 2: msg= "ourTask Review: "+ From+ " submitted AI '"+ Task +"' for Review";
	break;
	case 3: msg= "myTask Review: "+ From+  " APPROVED AI '"+ Task + "'. AI is closed";
	break;
	case 4: msg= "myTask Review: "+ From+ " REJECTED AI '"+ Task + "'. AI to be reworked";
	break;
	case 5: msg= "ourTask Appeal: "+ From+ "appealed the due date of AI '"+ Task+"'";
	break;
	case 6: msg= "myTask Appeal: "+ From+ " APPROVED due date appeal for AI '"+ Task+"'";
	break;
	case 7: msg= "myTask Appeal: "+ From+ " REJECTED due date appeal for AI '"+ Task+"'";
	break;
	case 8: msg= "ourTask Approve: "+ From+ " requested AI '"+ Task+ "' to be ourTask";
	break;
	case 9: msg= "myTask Approve: "+ From+ " APPROVED AI '"+ Task+"' as ourTask";
	break;
	case 10: msg= "myTask Approve: "+ From+ " REJECTED AI '"+ Task+ "' to be ourTask";
	break;
	}
	
	try{
	//get Tokens
	int count=0;
	rs= st.executeQuery("SELECT COUNT(TOKEN) FROM AITAM_DEVICES where EMP_ID=" + n.getTo());
	while(rs.next()){
		count= rs.getInt(1);
	}
	String[] tokens= new String[count];
	String[] tokens1= new String[count];
	int countGCM=0;
	int countIonic=0;
	rs= st.executeQuery("select TOKEN from AITAM_DEVICES where EMP_ID=" + n.getTo());
	while(rs.next()){
		if(rs.getString(1)!= null){
			if(rs.getString(1).toLowerCase().contains("dev")){
				tokens1[countIonic]=rs.getString(1);
				countIonic++;
			}
			else{
				tokens[countGCM]=rs.getString(1);
				countGCM++;
			}
		
		}
	}
	String[] tokensGCM= new String[countGCM];
	System.arraycopy(tokens, 0, tokensGCM, 0, countGCM);
	String[] tokensIonic= new String[countIonic];
	System.arraycopy(tokens1, 0, tokensIonic, 0, countIonic);
	
	//GCM
	
	//create JSON
	JSONObject msg1 = new JSONObject();
	msg1.put("msg", msg);
	JSONObject json      = new JSONObject();
	json.put("registration_ids",tokensGCM);
	json.put("data", msg1);
	String data = json.toString();
	
	//Push
	URL obj = new URL("https://gcm-http.googleapis.com/gcm/send");
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	con.setRequestMethod("POST");
	con.setRequestProperty( "Content-Type", "application/json");
	con.setRequestProperty( "Authorization", "key=AIzaSyAUYF00JJVpiVVGip79PVCUhqvt0PLK7qU");
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(data);
	wr.flush();
	wr.close();
	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	LOGGER.log(Level.SEVERE,"Response:"+response.toString()+" Data "+ data);
	
	//Ionic
	
	//json
	JSONObject msg2 = new JSONObject();
	 msg2.put("alert", msg);
	JSONObject json2      = new JSONObject();
	json2.put("tokens",tokensIonic);
	json2.put("notification", msg2);
	String dataIonic = json2.toString();
	
	//Push
	URL objIonic = new URL("https://push.ionic.io/api/v1/push");
	HttpURLConnection conIonic = (HttpURLConnection) objIonic.openConnection();
	conIonic.setRequestMethod("POST");
	conIonic.setRequestProperty( "Content-Type", "application/json");
	conIonic.setRequestProperty( "X-Ionic-Application-Id", "e554545b");
	conIonic.setRequestProperty( "Authorization", "Basic ZjVkOTkyMWJiN2JmMWIwYWI2Mzc2ZTU4YzZlMDE4OTM3MjM2ZDI0MTQzMzc2ZWFmOg==");
	conIonic.setDoOutput(true);
	DataOutputStream wrIonic = new DataOutputStream(conIonic.getOutputStream());
	wrIonic.writeBytes(dataIonic);
	wrIonic.flush();
	wrIonic.close();
	BufferedReader inIonic = new BufferedReader(
	        new InputStreamReader(conIonic.getInputStream()));
	StringBuffer responseIonic = new StringBuffer();

	while ((inputLine = inIonic.readLine()) != null) {
		responseIonic.append(inputLine);
	}
	inIonic.close();
	LOGGER.log(Level.SEVERE,"ResponseIonic:"+responseIonic.toString()+" Data: "+dataIonic);

	}
	catch(Exception e){
		LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
	}
	finally{
		if(st!=null){
			st.close();
		}
		if(rs!=null){
			rs.close();
		}
		conn.close();
	}
}

}
