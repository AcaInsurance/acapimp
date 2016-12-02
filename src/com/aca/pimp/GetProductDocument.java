package com.aca.pimp;

import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public abstract class GetProductDocument extends AsyncTask<String, Void, Void>{
	private static String NAMESPACE = "http://tempuri.org/";     
    private static String URL = Utility.getURL(); 
    
    private static String SOAP_ACTION = "http://tempuri.org/GetProductDocument";     
    private static String METHOD_NAME = "GetProductDocument";
     
    private ProgressDialog progressBar;     
    private HttpTransportSE androidHttpTransport = null;
    
    private SoapObject requestretrive = null;
    private SoapSerializationEnvelope envelope = null;
	private Context context;

    private boolean error;
    private String errorMessage = "";  
    private HashMap<String, String> hmap = null;
    private ArrayList<HashMap<String, String>> arrList = null;

	
	@SuppressWarnings("unused")
	private static final String TAG = "com.aca.pimp.GetProductDocument";
	 
	
	public GetProductDocument (Context context, HashMap<String, String> map ) {
		this.context  = context;
		this.hmap = map;
		
	} 
	
		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			progressBar = new ProgressDialog(context);
			progressBar.setCancelable(false);
			progressBar.setMessage("Please wait ...");
			progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressBar.show();
			
			envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
			envelope.implicitTypes = true;
	    	envelope.dotNet = true;	//used only if we use the webservice from a dot net file (asmx)	    				
			androidHttpTransport = new HttpTransportSE(URL);

			requestretrive = new SoapObject(NAMESPACE, METHOD_NAME);
		}
		
		@Override
		protected Void doInBackground(String... params) {

    		error = false; 
			SoapObject table = null;                        // Contains table of dataset that returned through SoapObject
	        SoapObject tableRow = null;                     // Contains row of table
	        SoapObject responseBody = null;					// Contains XML content of dataset
	        HashMap<String, String> map = null;
        	
			try{   

		        requestretrive.addProperty(Utility.GetPropertyInfo("SppaNo", hmap.get("SppaNo").toString(), String.class)); 
		        
		    	envelope.setOutputSoapObject(requestretrive);
		    	envelope.bodyOut = requestretrive;
	    		androidHttpTransport.call(SOAP_ACTION, envelope);  
	    			
	            responseBody = (SoapObject) envelope.getResponse();
	            responseBody = (SoapObject) responseBody.getProperty(1);
	            
	            if(responseBody.getPropertyCount() > 0) {
	            	table = (SoapObject) responseBody.getProperty(0);	            	
	            	arrList = new ArrayList<HashMap<String, String>>(); 
	            	 
	            	for (int i = 0; i < table.getPropertyCount(); i++) {
						
		            	tableRow = (SoapObject) table.getProperty(i);
		                map = new HashMap<String, String>(); 
		                
		                map.put("DOCUMENT_FILENAME", Utility.isAnytype(tableRow.getPropertySafelyAsString("DOCUMENT_FILENAME"))); 
		                map.put("CRE_DATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("CRE_DATE")));   
		                
			            arrList.add(map);	
	            	}
	        	}
	            else {
	            	error = true;
	            	errorMessage = "Data Document tidak ditemukan";
	            } 
			}	
			catch (Exception e) { 
        		error = true;
        		e.printStackTrace();	        		
				errorMessage = new MasterExceptionClass(e).getException();
			} 
			return null;
		}
		
		@Override	
		protected void onPostExecute(Void result) {			
			super.onPostExecute(result);
			
			progressBar.hide();
			progressBar.dismiss();
			
			try{	
				if (error) {
					Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
					arrList = null;
				}
				getProductDocumentList(arrList);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		protected abstract void getProductDocumentList(ArrayList<HashMap<String, String>> arrList) ;
		 
}

