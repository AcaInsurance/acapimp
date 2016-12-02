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

public abstract class LoadPimpAsri extends AsyncTask<String, Void, Void>{
	private ProgressDialog progressBar;
	private Context ctx;
	
	private boolean error = false;
	private String errorMessage = "";
	
	private SoapObject requestRetrieve = null;
    private SoapSerializationEnvelope envelope = null;
    private HttpTransportSE androidHttpTransport = null;
    
    private static String NAMESPACE = "http://tempuri.org/";     
	private static String URL = Utility.getURL(); 
    private static String SOAP_ACTION = "http://tempuri.org/GetProductAsri";     
    private static String METHOD_NAME= "GetProductAsri";
     
    private ArrayList<HashMap<String, String>> arrList; 
    private HashMap<String, String> hmap;
    
    
    public LoadPimpAsri (Context ctx, HashMap<String, String> hmap){
    	this.ctx = ctx;
    	this.hmap = hmap;
    	 
    }
    
	protected void onPreExecute() {

		super.onPreExecute();
		
		progressBar = new ProgressDialog(ctx);
		progressBar.setCancelable(false);
		progressBar.setMessage("Please wait ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.show();
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		envelope.implicitTypes = true;
    	envelope.dotNet = true;	//used only if we use the webservice from a dot net file (asmx)
		androidHttpTransport = new HttpTransportSE(URL);
		requestRetrieve = new SoapObject(NAMESPACE, METHOD_NAME);
	}
	
	@Override
	protected Void doInBackground(String... params) {
		error = false;
		try{
			
			SoapObject table = null;                        // Contains table of dataset that returned through SoapObject
	        SoapObject tableRow = null;                     // Contains row of table
	        SoapObject responseBody = null;					// Contains XML content of dataset
	        
	        requestRetrieve.addProperty(Utility.GetPropertyInfo("SppaNo", hmap.get("SPPA_NO"), String.class));
	        
	    	envelope.setOutputSoapObject(requestRetrieve);
    		envelope.bodyOut = requestRetrieve;
    		androidHttpTransport.call(SOAP_ACTION, envelope);  

    		
            responseBody = (SoapObject) envelope.getResponse();
            responseBody = (SoapObject) responseBody.getProperty(1);
            
            if (responseBody.getPropertyCount() == 0) {
            	error = true;
            	errorMessage = "Data tidak dapat ditemukan";
            	return null;
            			
            }
            
            table = (SoapObject) responseBody.getProperty(0);            
            arrList = new ArrayList<HashMap<String, String>>();             
            int iTotalDataFromWebService = table.getPropertyCount();
            
            for(int i = 0; i < iTotalDataFromWebService; i++)
            {
            	tableRow = (SoapObject) table.getProperty(i);

	            HashMap<String, String> map = new HashMap<String, String>();  
	              
	            map.put("SPPA_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("SPPA_NO")));
	            map.put("BUILDING_SIZE", Utility.isAnytype(tableRow.getPropertySafelyAsString("BUILDING_SIZE")));
	            map.put("BUILDING_SI", Utility.isAnytype(tableRow.getPropertySafelyAsString("BUILDING_SI")));
	            map.put("CONTENT_SI", Utility.isAnytype(tableRow.getPropertySafelyAsString("CONTENT_SI")));
	            map.put("OTHER_1_DESC", Utility.isAnytype(tableRow.getPropertySafelyAsString("OTHER_1_DESC")));
	            map.put("OTHER_1_SI", Utility.isAnytype(tableRow.getPropertySafelyAsString("OTHER_1_SI")));
	            map.put("OTHER_2_DESC", Utility.isAnytype(tableRow.getPropertySafelyAsString("OTHER_2_DESC")));
	            map.put("OTHER_2_SI", Utility.isAnytype(tableRow.getPropertySafelyAsString("OTHER_2_SI")));
	            map.put("RISK_LOCATION_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_LOCATION_FLAG")));
	            map.put("RISK_ADDRESS", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_ADDRESS")));
	            map.put("RISK_RT_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_RT_NO")));
	            map.put("RISK_RW_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_RW_NO")));
	            map.put("RISK_KELURAHAN", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_KELURAHAN")));
	            map.put("RISK_KECAMATAN", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_KECAMATAN")));
	            map.put("RISK_CITY", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_CITY")));
	            map.put("RISK_POST_CODE", Utility.isAnytype(tableRow.getPropertySafelyAsString("RISK_POST_CODE")));
	            map.put("WALL_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("WALL_FLAG")));
	            map.put("WALL_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("WALL_NOTE")));
	            map.put("FLOOR_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("FLOOR_FLAG")));
	            map.put("FLOOR_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("FLOOR_NOTE")));
	            map.put("CEILING_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("CEILING_FLAG")));
	            map.put("CEILING_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("CEILING_NOTE")));
	            map.put("QUESTION_4A_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("QUESTION_4A_FLAG")));
	            map.put("QUESTION_4A_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("QUESTION_4A_NOTE")));
	            map.put("QUESTION_4B_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("QUESTION_4B_FLAG")));
	            map.put("QUESTION_4B_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("QUESTION_4B_NOTE")));
	            map.put("LONGITUDE", Utility.isAnytype(tableRow.getPropertySafelyAsString("LONGITUDE")));
	            map.put("LATITUDE", Utility.isAnytype(tableRow.getPropertySafelyAsString("LATITUDE")));
	            map.put("RATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("RATE")));
	            map.put("APPROVAL_IMG", Utility.isAnytype(tableRow.getPropertySafelyAsString("APPROVAL_IMG")));
	            map.put("REMARK", Utility.isAnytype(tableRow.getPropertySafelyAsString("REMARK")));
	            map.put("APPROVAL_DATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("APPROVAL_DATE")));
	            map.put("PAYMENT_METHOD_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("PAYMENT_METHOD_FLAG")));
	            map.put("PAYMENT_PROOF_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("PAYMENT_PROOF_NO")));
	            map.put("PAYMENT_AMOUNT", Utility.isAnytype(tableRow.getPropertySafelyAsString("PAYMENT_AMOUNT")));
	             

	            map.put("CUSTOMER_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("CUSTOMER_NO")));
	            map.put("CUSTOMER_NAME", Utility.isAnytype(tableRow.getPropertySafelyAsString("CUSTOMER_NAME")));
	            map.put("TOTAL_PREMIUM", Utility.isAnytype(tableRow.getPropertySafelyAsString("TOTAL_PREMIUM")));
	            map.put("EFF_DATE", Utility.formatDate(Utility.isAnytype(tableRow.getPropertySafelyAsString("EFF_DATE")).substring(0, 10), "yyyy-MM-dd", "dd/MM/yyyy"));
	            map.put("EXP_DATE", Utility.formatDate(Utility.isAnytype(tableRow.getPropertySafelyAsString("EXP_DATE")).substring(0, 10), "yyyy-MM-dd", "dd/MM/yyyy"));
	      
	        
	            arrList.add(map);
            }				
		}	
		catch (Exception e) {
    		error = true;
    		e.printStackTrace();	        		
			errorMessage = new MasterExceptionClass(e).getException();
		}
		return null;
	}
	
	protected void onPostExecute(Void result) {
		
		super.onPostExecute(result);
		 
		try
		{
			progressBar.hide();
			progressBar.dismiss();
			 
			if (error)
				Utility.showCustomDialogInformation(ctx, "Informasi", 
						errorMessage);	
			
			getAsriList(arrList);
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		} 
	}

	protected abstract void getAsriList(ArrayList<HashMap<String, String>> arrList);
	
	
}
