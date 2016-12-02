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

public abstract class LoadPimpOtomate extends AsyncTask<String, Void, Void>{
	private ProgressDialog progressBar;
	private Context ctx;
	
	private boolean error = false;
	private String errorMessage = "";
	
	private SoapObject requestRetrieve = null;
    private SoapSerializationEnvelope envelope = null;
    private HttpTransportSE androidHttpTransport = null;
    
    private static String NAMESPACE = "http://tempuri.org/";     
	private static String URL = Utility.getURL(); 
    private static String SOAP_ACTION = "http://tempuri.org/GetProductOtomate";     
    private static String METHOD_NAME= "GetProductOtomate";
     
    private ArrayList<HashMap<String, String>> arrList; 
    private HashMap<String, String> hmap;
     
    
    public LoadPimpOtomate (Context ctx, HashMap<String, String> hmap){
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
	            
	            map.put("VEHICLE_MERK", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLE_MERK")));
	            map.put("VEHICLE_MERK_DESC", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLE_MERK_DESC")));
	            map.put("VEHICLE_CATEGORY", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLE_CATEGORY")));
	            map.put("VEHICLE_CATEGORY_DESC", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLE_CATEGORY_DESC")));
	            map.put("VEHICLE_DESC", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLE_DESC")));
	            map.put("VEHICLECATG_DESC", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLECATG_DESC")));
	            map.put("VEHICLECATG", Utility.isAnytype(tableRow.getPropertySafelyAsString("VEHICLECATG")));
	            map.put("BUILT_YEAR", Utility.isAnytype(tableRow.getPropertySafelyAsString("BUILT_YEAR")));	            
	            map.put("PLAT_NO_1", Utility.isAnytype(tableRow.getPropertySafelyAsString("PLAT_NO_1")));
	            map.put("PLAT_NO_2", Utility.isAnytype(tableRow.getPropertySafelyAsString("PLAT_NO_2")));
	            map.put("PLAT_NO_3", Utility.isAnytype(tableRow.getPropertySafelyAsString("PLAT_NO_3")));	            
	            map.put("CHASSIS_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("CHASSIS_NO")));
	            map.put("ENGINE_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("ENGINE_NO")));
	            map.put("COLOUR", Utility.isAnytype(tableRow.getPropertySafelyAsString("COLOUR")));	            
	            map.put("COVERAGE_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("COVERAGE_FLAG")));	 
	            map.put("NON_STANDARD_ACCESORIES", Utility.isAnytype(tableRow.getPropertySafelyAsString("NON_STANDARD_ACCESORIES"))); 
	            map.put("ACCESORIES_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("ACCESORIES_FLAG"))); 
	            map.put("SEATING_CAP", Utility.isAnytype(tableRow.getPropertySafelyAsString("SEATING_CAP")));	            
	            map.put("SI", Utility.isAnytype(tableRow.getPropertySafelyAsString("SI")));	             
	            map.put("ADD_SI", Utility.isAnytype(tableRow.getPropertySafelyAsString("ADD_SI")));  
	            map.put("ACT_OF_GOD", Utility.isAnytype(tableRow.getPropertySafelyAsString("ACT_OF_GOD")));
	            map.put("FLOOD", Utility.isAnytype(tableRow.getPropertySafelyAsString("FLOOD")));
	            map.put("EQ", Utility.isAnytype(tableRow.getPropertySafelyAsString("EQ")));
	            map.put("TPL", Utility.isAnytype(tableRow.getPropertySafelyAsString("TPL")));
	            map.put("PA", Utility.isAnytype(tableRow.getPropertySafelyAsString("PA")));
	            map.put("SRCC", Utility.isAnytype(tableRow.getPropertySafelyAsString("SRCC")));
	            map.put("TS", Utility.isAnytype(tableRow.getPropertySafelyAsString("TS")));
	            map.put("TIPE_RATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("TIPE_RATE")));
	            map.put("PENGGUNAAN", Utility.isAnytype(tableRow.getPropertySafelyAsString("PENGGUNAAN")));
	            map.put("OWN_RISK", Utility.isAnytype(tableRow.getPropertySafelyAsString("OWN_RISK")));
	            map.put("RATE", Utility.isAnytype(tableRow.getPropertySafelyAsString("RATE")));
	            map.put("DAMAGE_FLAG", Utility.isAnytype(tableRow.getPropertySafelyAsString("DAMAGE_FLAG")));
	            map.put("DAMAGE_REMARK", Utility.isAnytype(tableRow.getPropertySafelyAsString("DAMAGE_REMARK")));
	            map.put("TIPE_LOADING", Utility.isAnytype(tableRow.getPropertySafelyAsString("TIPE_LOADING")));
	             

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
			
			getOtomateList(arrList);
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		} 
	}

 
	protected abstract void getOtomateList(ArrayList<HashMap<String, String>> arrList);
	
	
}
