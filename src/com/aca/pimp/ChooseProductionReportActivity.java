package com.aca.pimp;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aca.database.DBA_MASTER_AGENT;
import com.aca.pimp.GetProductionReport.InterfaceRetriveProduction;

public class ChooseProductionReportActivity extends ControlNormalActivity implements InterfaceRetriveProduction   {
	private static final String TAG = "com.aca.pimp.ChooseProductionReportActivity";
	
	private ListView lv; 
	private Bundle bundle;
	private Button btnMyResult;
	private NumberFormat nf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choose_production_report);
		
	
		initInstance();
		getBundle();
		getWSData(); 
		registerListener();
		disableView();
	}


	private void disableView() {
//		HashMap<String, String> map = getUserData();
//		int roleId =  Integer.parseInt(map.get("IdRole"));
//		
//		if (roleId != 1)
//			btnMyResult.setVisibility(View.GONE);
		
		btnMyResult.setVisibility(View.GONE);
		
	}


	private void getBundle() {
		if (getIntent().getExtras() != null) {
			bundle = getIntent().getExtras();
		}
	}


	private void initInstance() {
		lv = (ListView)findViewById(R.id.lvSPPA);
		btnMyResult = (Button)findViewById(R.id.btnMyResult);
		
		nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}



	private void bindData(ArrayList<HashMap<String, String>> arrList) { 
		SampleAdapter adapter = new SampleAdapter(this, arrList);
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);
		
	}
 

    private void registerListener() {
    	btnMyResult.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getMyResult();
			}
 
		});
		
	}

    
    private HashMap<String, String> getUserData() {
    	DBA_MASTER_AGENT dbUser = new DBA_MASTER_AGENT(this);
		Cursor c = null;
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			dbUser.open();
			c = dbUser.getRow();

			if (!c.moveToFirst())
				return null;

			map.put("IdLevel", c.getString(15));
			map.put("UserId", c.getString(1));
			map.put("StatusLevel", c.getString(17));
		    map.put("IdRole", c.getString(18));	 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbUser != null)
				dbUser.close();

			if (c != null)
				c.close();

		}
		return map;
    }
    
    private void getMyResult() {  
    	HashMap<String, String> map = getUserData();
    	
    	if (map == null)
    		return;
    			
    	GetAkumulasi ws = new GetAkumulasi(this, map) {
			
			@Override
			protected void getAkumulasiList(ArrayList<HashMap<String, String>> arrList) {
				if (arrList != null) {
//					showResult(arrList);
				}
				
			}
		};
		ws.execute();
		
    }
 
	

	private void getWSData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("FromDate", bundle.getString(ChooseProductionMenuActivity.FROM));
		map.put("ToDate", bundle.getString(ChooseProductionMenuActivity.TO));
		map.put("Branch", bundle.getString(ChooseProductionMenuActivity.BRANCH));
		map.put("UserID", bundle.getString(ChooseProductionMenuActivity.USERID));
		map.put("Leader", bundle.getString(ChooseProductionMenuActivity.LEADER));
		map.put("Approval", bundle.getString(ChooseProductionMenuActivity.APPROVAL));
		
		GetProductionReport ws = new GetProductionReport(this, map);
		ws.execute();
		ws.mCallBack = this;
	}
	

	@Override
	public void getListProduction(ArrayList<HashMap<String, String>> arrList) {
		if (arrList != null) { 
			Log.i(TAG, "::getListProduction:" + "arrlist size = " +  arrList.size());
			bindData(arrList);
		}
		else {
			bindData(null);
		}
		
	}
	
	

	private class SampleAdapter extends BaseAdapter {
    	private ArrayList<HashMap<String, String>> arrList;
    	private Context context;
    	
    	public SampleAdapter (Context context, ArrayList<HashMap<String, String>> arrList) {
    		this.arrList = arrList;
    		this.context = context;
    	}
    	
        @Override
        public int getCount() {
        	if (arrList != null)
        		return arrList.size();
        	else 
        		return 0;
        }

        @Override
        public HashMap getItem(int position) {
        	if (arrList == null)
        		return null;
        	else
        		return arrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.list_item_menu_production_report, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final HashMap item = getItem(position);
            
            holder.imgDetail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

                    HashMap data = getItem(position);
				}
			});
            
            holder.tvUserID.setText(item.get("SPPA_AGENT_ID").toString());
            holder.tvSppaNo.setText(item.get("SPPA_NO").toString());
            holder.tvPolicyNo.setText(item.get("POLICY_NO").toString());
            holder.tvSubmitDate.setText(item.get("CRE_DATE").toString());
            holder.tvRemark.setText(item.get("REMARK").toString());
            holder.tvCustomer.setText(item.get("CUSTOMER_NAME").toString());
 
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sppaNo = item.get("SPPA_NO").toString();
                    String kodeProduk = Utility.getProductNameBySPPAno(sppaNo);
                    Intent i = null;
                    Bundle b = new Bundle();
                    b.putString("PRODUCT_ACTION", "APPROVE");
                    b.putString("SPPA_NO", sppaNo);
                    b.putString("APPROVAL", bundle.getString(ChooseProductionMenuActivity.APPROVAL));
                    
                	if(kodeProduk.equalsIgnoreCase("OTOMATE")){		
        				i = new Intent(getBaseContext(),  FillOtomateActivity.class);
        				i.putExtras(b); 
        			}
                	else if(kodeProduk.equalsIgnoreCase("KONVENSIONAL")){
        				i = new Intent(getBaseContext(),  FillKonvensionalActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("TRAVELDOM")){
        				i = new Intent(getBaseContext(),  FillTravelDomActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("TRAVELSAFE")){
        				i = new Intent(getBaseContext(),  FillTravelActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("TOKO")){
        				i = new Intent(getBaseContext(),  FillTokoActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("MEDISAFE")){
        				i = new Intent(getBaseContext(),  FillMedisafe.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("ASRI")){
        				i = new Intent(getBaseContext(),  FillAsriActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("ASRISYARIAH")){
        				i = new Intent(getBaseContext(),  FillAsriSyariahActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("OTOMATESYARIAH")){
        				i = new Intent(getBaseContext(),  FillOtomateSyariahActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("EXECUTIVESAFE")){
        				i = new Intent(getBaseContext(),  FillExecutiveActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("PAAMANAH")){
        				i = new Intent(getBaseContext(),  FillPAAmanahActivity.class);
        				i.putExtras(b);

        			} 
 
        			else if(kodeProduk.equalsIgnoreCase("CARGO")){
        				i = new Intent(getBaseContext(),  FillCargoActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("WELLWOMAN")){
        				i = new Intent(getBaseContext(),  FillWellWomanActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("DNO")){
        				i = new Intent(getBaseContext(),  FillDNOActivity.class);
        				i.putExtras(b);

        			}
        			else if(kodeProduk.equalsIgnoreCase("KONVENSIONAL")){
        				i = new Intent(getBaseContext(),  FillKonvensionalActivity.class);
        				b.putString("TIPE_KONVENSIONAL", "");
        				i.putExtras(b);

        			}
                	
                	if (i != null)
                		startActivity(i);
                	
                }
            });
 
            return convertView;
        }
  
    }

	
 
	 private static class ViewHolder {

	        private View view;
	        private TextView tvUserID;
	        private TextView tvSppaNo;
	        private TextView tvPolicyNo;
	        private TextView tvSubmitDate;
	        private TextView tvRemark;
	        private TextView tvCustomer;
	        private ImageView imgDetail;


	        private ViewHolder(View view) {
	            this.view 		= view;
	            tvUserID		= (TextView) view.findViewById(R.id.txtUserID);
	            tvSppaNo		= (TextView) view.findViewById(R.id.txtSPPANo);
	            tvPolicyNo 		= (TextView) view.findViewById(R.id.txtPolicyNo);
	            tvSubmitDate	= (TextView) view.findViewById(R.id.txtSubmitDate);
	            tvRemark		= (TextView) view.findViewById(R.id.txtRemark);
	            tvCustomer		= (TextView) view.findViewById(R.id.txtCustomer);
	            imgDetail		= (ImageView) view.findViewById(R.id.imgDetail);
	            imgDetail.setVisibility(View.GONE);
	        }
	    }
	 
	 
	 

	@Override
	protected void onResume() { 
		super.onResume();
		getWSData(); 
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_production_report, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed()
	{
		Back();
	}
	
	private void Back() { 
		this.finish();
	}
	
	public void btnHomeClick(View v) {
		Intent i = new Intent(getBaseContext(), FirstActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		
		startActivity(i);
		this.finish();
	}
	



}
