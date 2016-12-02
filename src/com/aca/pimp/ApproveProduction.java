package com.aca.pimp;

import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public abstract class ApproveProduction extends AsyncTask<String, Void, Boolean>{
	private ProgressDialog progressBar;
	private Context ctx;
	
	private boolean error = false;
	private String errorMessage = "";
	
	private SoapObject requestRetrieve = null;
    private SoapSerializationEnvelope envelope = null;
    private HttpTransportSE androidHttpTransport = null;
    
    private static String NAMESPACE = "http://tempuri.org/";     
	private static String URL = Utility.getURL();    
    private static String SOAP_ACTION = "http://tempuri.org/ApproveRejectProduction";     
    private static String METHOD_NAME= "ApproveRejectProduction";
      
	@SuppressWarnings("unused")
	private static final String TAG = "com.aca.pimp.ApproveRejectProduction";
	private HashMap<String, String> hmap ;
	
    public ApproveProduction (Context ctx,  HashMap<String, String> hmap){
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
	protected Boolean doInBackground(String... params) {
		error = false;
		Boolean flag = false;
		
		try{
			SoapObject responseBody = null;					// Contains XML content of dataset
	        
	        requestRetrieve.addProperty(Utility.GetPropertyInfo("SppaNo", hmap.get("SppaNo"), String.class));
	        requestRetrieve.addProperty(Utility.GetPropertyInfo("IsApprove", hmap.get("IsApprove"), String.class));
	        requestRetrieve.addProperty(Utility.GetPropertyInfo("Remark", hmap.get("Remark"), String.class));
	        
	    	envelope.setOutputSoapObject(requestRetrieve);
    		androidHttpTransport.call(SOAP_ACTION, envelope);  

            responseBody = (SoapObject) envelope.bodyIn;
            flag  = Boolean.parseBoolean(responseBody.getPropertySafelyAsString("ApproveRejectProductionResult"));
            Log.i(TAG, "::doInBackground:" + "flag approval = " + flag);
		}          
            
		catch (Exception e) {
    		error = true;
    		e.printStackTrace();	        		
			errorMessage = new MasterExceptionClass(e).getException();
		} 
		
		return flag;
	}
	
	protected void onPostExecute(Boolean flag) {
		
		super.onPostExecute(flag);

		progressBar.hide();
		progressBar.dismiss();
		
		try
		{
			if (error) {
				Utility.showCustomDialogInformation(ctx, "Informasi", 
						errorMessage); 
			} 
			
			getFlagApprovalProduction(flag);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	protected abstract void getFlagApprovalProduction(Boolean flag) ;
	
}
