package com.aca.pimp;

import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;


class GetProductionReport extends AsyncTask<String, Void, Void>{
	private Activity ctx ;
	
	private boolean error = false;
	private String errorMessage = "";
	
	private SoapObject requestretrive = null;
    private SoapSerializationEnvelope envelope = null;
    private HttpTransportSE androidHttpTransport = null;
    
    private static String NAMESPACE = "http://tempuri.org/";     
	private static String URL = Utility.getURL(); 

    private static String SOAP_ACTION = "http://tempuri.org/GetListProductionReport";     
    private static String METHOD_NAME= "GetListProductionReport";
    
    private HashMap<String, String> map;
    
    private ArrayList<HashMap<String, String>> arrList;

	private ProgressDialog progressBar;
    
	public InterfaceRetriveProduction mCallBack = null;
	
	public GetProductionReport (Activity ctx, HashMap<String, String> map){
		this.ctx = ctx;
		this.map = map;
		
	}
	
	public interface InterfaceRetriveProduction {
		public void getListProduction (ArrayList<HashMap<String, String>> arrList);
	}
	
	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		
//		Log.d("context", );
//
		progressBar = new ProgressDialog(ctx);
		progressBar.setCancelable(false);
		progressBar.setMessage("Please wait ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.show();
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		envelope.implicitTypes = true;
		envelope.dotNet = true;	//used only if we use the webservice from a dot net file (asmx)
		androidHttpTransport = new HttpTransportSE(URL);

	}
	
	@Override
	protected Void doInBackground(String... params) {
		
		error = false;
		
		try{
			SoapObject table = null;                        // Contains table of dataset that returned through SoapObject
	        SoapObject tableRow = null;                     // Contains row of table
	        SoapObject responseBody = null;					// Contains XML content of dataset
			

			requestretrive = new SoapObject(NAMESPACE, METHOD_NAME);
	        requestretrive.addProperty(Utility.GetPropertyInfo("FromDate", map.get("FromDate").toString(), String.class));
	        requestretrive.addProperty(Utility.GetPropertyInfo("ToDate", map.get("ToDate").toString(), String.class));
	        requestretrive.addProperty(Utility.GetPropertyInfo("Branch", map.get("Branch").toString(), String.class));
	        requestretrive.addProperty(Utility.GetPropertyInfo("UserID", map.get("UserID").toString(), String.class));
	        requestretrive.addProperty(Utility.GetPropertyInfo("Leader", map.get("Leader").toString(), String.class));
	        requestretrive.addProperty(Utility.GetPropertyInfo("Approval", map.get("Approval").toString(), String.class)); 
	        
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
	                 
	                HashMap<String, String> map = new HashMap<String, String>(); 
	            	map.put("SPPA_AGENT_ID", Utility.isAnytype(tableRow.getPropertySafelyAsString("SPPA_AGENT_ID")));
	            	map.put("SPPA_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("SPPA_NO")));
	            	map.put("POLICY_NO", Utility.isAnytype(tableRow.getPropertySafelyAsString("POLICY_NO")));
	                map.put("SPPA_AGENT_ID", Utility.isAnytype(tableRow.getPropertySafelyAsString("SPPA_AGENT_ID")));
	                map.put("TOTAL_PREMIUM", Utility.isAnytype(tableRow.getPropertySafelyAsString("TOTAL_PREMIUM")));                
	                map.put("CRE_DATE", Utility.formatDate(Utility.isAnytype(tableRow.getPropertySafelyAsString("CRE_DATE")).substring(0, 10), "yyyy-MM-dd", "dd/MM/yyyy")); 
	                map.put("REMARK", Utility.isAnytype(tableRow.getPropertySafelyAsString("REMARK")));
	                map.put("CUSTOMER_NAME", Utility.isAnytype(tableRow.getPropertySafelyAsString("CUSTOMER_NAME")));
	                
	                arrList.add(map);
            	}
        	}
            else {
            	error = true;
            	errorMessage = "Data SPPA tidak ditemukan";
            			
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
			if(error){
				arrList = null;
				Toast.makeText(ctx, errorMessage, Toast.LENGTH_SHORT).show();
			}
			
			mCallBack.getListProduction(arrList);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	} 
	
}