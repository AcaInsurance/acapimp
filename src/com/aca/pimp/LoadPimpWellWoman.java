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

public abstract class LoadPimpWellWoman extends AsyncTask<String, Void, Void>{
	private ProgressDialog progressBar;
	private Context ctx;
	
	private boolean error = false;
	private String errorMessage = "";
	
	private SoapObject requestRetrieve = null;
    private SoapSerializationEnvelope envelope = null;
    private HttpTransportSE androidHttpTransport = null;
    
    private static String NAMESPACE = "http://tempuri.org/";     
	private static String URL = Utility.getURL(); 
    private static String SOAP_ACTION = "http://tempuri.org/GetProductWellWoman";     
    private static String METHOD_NAME= "GetProductWellWoman";
     
    private ArrayList<HashMap<String, String>> arrList; 
    private HashMap<String, String> hmap;
    
    
    public LoadPimpWellWoman (Context ctx, HashMap<String, String> hmap){
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
	             
	           map.put("PRODUCT_PLAN_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("PRODUCT_PLAN_FLAG")));
	           map.put("PREMI_REAS", Utility.isAnytype(tableRow.getPropertySafelyAsString("PREMI_REAS")));
	           map.put("Q1_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q1_FLAG")));
	           map.put("Q1_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q1_NOTE")));
	           map.put("Q2_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q2_FLAG")));
	           map.put("Q2_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q2_NOTE")));
	           map.put("Q3_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q3_FLAG")));
	           map.put("Q3_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q3_NOTE")));
	           map.put("Q4_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q4_FLAG")));
	           map.put("Q4_NOTE", Utility.isAnytype(tableRow.getPropertySafelyAsString("Q4_NOTE")));
	           map.put("APPROVAL_IMG", Utility.isAnytype(tableRow.getPropertySafelyAsString("APPROVAL_IMG")));
	           map.put("REMARK", Utility.isAnytype(tableRow.getPropertySafelyAsString("REMARK")));
	             

	            map.put("CUSTOMER_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("CUSTOMER_NO")));
	            map.put("CUSTOMER_NAME", Utility.isAnytype(tableRow.getPropertySafelyAsString("CUSTOMER_NAME")));
	            map.put("TOTAL_PREMIUM", Utility.isAnytype(tableRow.getPropertySafelyAsString("TOTAL_PREMIUM")));
	            map.put("EFF_DATE", Utility.formatDate(Utility.isAnytype(tableRow.getPropertySafelyAsString("EFF_DATE")).substring(0, 10), "yyyy-MM-dd", "dd/MM/yyyy"));
	            map.put("EXP_DATE", Utility.formatDate(Utility.isAnytype(tableRow.getPropertySafelyAsString("EXP_DATE")).substring(0, 10), "yyyy-MM-dd", "dd/MM/yyyy"));
	      
	            /*
	            map.put("CC_NAME", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_NAME"))));
	            map.put("CC_TYPE_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_TYPE_FLAG")))); 
	            map.put("CC_EXPR_MONTH", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_EXPR_MONTH")))); 
	            map.put("CC_EXPR_YEAR", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_EXPR_YEAR"))));
	            map.put("CC_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_NO"))));
	            map.put("CC_SECURITY_CODE", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_SECURITY_CODE"))));
	            map.put("CC_SOLD_DATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_SOLD_DATE"))));
	            map.put("CC_SETTLE_DATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("CC_SETTLE_DATE"))));
	            map.put("CRE_DATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("CRE_DATE"))));
	            map.put("CRE_BY", Utility.isAnytype(tableRow.getPropertySafelyAsString("CRE_BY"))));
	            map.put("CRE_IP_ADDRESS", Utility.isAnytype(tableRow.getPropertySafelyAsString("CRE_IP_ADDRESS"))));
	            map.put("MOD_DATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("MOD_DATE"))));
	            map.put("MOD_BY", Utility.isAnytype(tableRow.getPropertySafelyAsString("MOD_BY"))));
	            map.put("MOD_IP_ADDRESS", Utility.isAnytype(tableRow.getPropertySafelyAsString("MOD_IP_ADDRESS"))));
	            */
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
			
			getWellWomanList(arrList);
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		} 
	}

	protected abstract void getWellWomanList(ArrayList<HashMap<String, String>> arrList);
	
	
}
