package com.aca.pimp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseProductActivity extends ControlNormalActivity {
	private LinearLayout 	rowOtomate,
							rowAsi,
							rowTravelsafe,
							rowTravelDom,
							rowMedisafe,
							rowWellWoman,
							rowDNO,
							rowCargo,
							rowOtomateSyariah,
							rowAsriSyariah,
							rowKonvensional,
							rowPA;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choose_product);
		 
		initInstance();
		disableView(-1);
		
		getLOB();

		
	}

	private void initInstance() {
		rowOtomate			= (LinearLayout)findViewById(R.id.rowOtomate);
		rowAsi				= (LinearLayout)findViewById(R.id.rowAsri);
		rowTravelsafe		= (LinearLayout)findViewById(R.id.rowTravelSafe);
		rowTravelDom		= (LinearLayout)findViewById(R.id.rowTravelDom);
		rowMedisafe			= (LinearLayout)findViewById(R.id.rowMedisafe);
		rowWellWoman		= (LinearLayout)findViewById(R.id.rowWellWoman);
		rowDNO				= (LinearLayout)findViewById(R.id.rowDNO);
		rowCargo			= (LinearLayout)findViewById(R.id.rowCargo);
		rowOtomateSyariah	= (LinearLayout)findViewById(R.id.rowOtomateSyariah);
		rowAsriSyariah		= (LinearLayout)findViewById(R.id.rowAsriSyariah);
		rowKonvensional		= (LinearLayout)findViewById(R.id.rowKonvensional);
		rowPA				= (LinearLayout)findViewById(R.id.rowPA);
	}

	private void getLOB() {
		String userid = Utility.getUserID(this);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("UserId", userid);
		
		GetLOB ws = new GetLOB(this, map) {
			
			@Override
			protected void getLOBList(ArrayList<HashMap<String, String>> arrList) {
				if (arrList != null) {
					int lob = 0;
					for (HashMap<String, String> hashMap : arrList) {
						lob = Integer.parseInt(hashMap.get("ID_LOB"));
						disableView(lob);
					}
				}
			}

		};
		ws.execute();
	}


	private void disableView(int lob) {
		switch (lob) {
		case 1: rowAsi.setVisibility(View.VISIBLE); break;
		case 2: rowKonvensional.setVisibility(View.VISIBLE); break;
		case 3: rowOtomate.setVisibility(View.VISIBLE); break;
		case 4: rowMedisafe.setVisibility(View.VISIBLE); break; 
		case 6: rowTravelDom.setVisibility(View.VISIBLE); break;
		case 7: rowTravelsafe.setVisibility(View.VISIBLE); break;
		case 8: rowAsriSyariah.setVisibility(View.VISIBLE); break;
		case 9: rowOtomateSyariah.setVisibility(View.VISIBLE); break;
		case 10: rowPA.setVisibility(View.VISIBLE); break; 
		case 13: rowCargo.setVisibility(View.VISIBLE); break;
		case 16: rowWellWoman.setVisibility(View.VISIBLE); break;
		case 17: rowDNO.setVisibility(View.VISIBLE); break;

		case -1:
			rowOtomate.setVisibility(View.GONE);
			rowAsi.setVisibility(View.GONE);
			rowTravelsafe.setVisibility(View.GONE);
			rowTravelDom.setVisibility(View.GONE);
			rowMedisafe.setVisibility(View.GONE);
			rowWellWoman.setVisibility(View.GONE);
			rowDNO.setVisibility(View.GONE);
			rowCargo.setVisibility(View.GONE);
			rowOtomateSyariah.setVisibility(View.GONE);
			rowAsriSyariah.setVisibility(View.GONE);
			rowKonvensional.setVisibility(View.GONE);
			rowPA.setVisibility(View.GONE);
			break;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choos_product, menu);
		return true;
	}

	public void btnHomeClick(View v) {
		Home();
	}
	
	private void Home() {
		Intent i = new Intent(getBaseContext(),  FirstActivity.class);
		startActivity(i);
		this.finish();
	}
	
	@Override
	public void onBackPressed()
	{
		Back();
	}
	
	private void Back() {
		Intent i = new Intent(getBaseContext(),  FirstActivity.class);
		startActivity(i);
		this.finish();
	}
	
	public void btnOtomateClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoOtomateActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "OTOMATE");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
		
	}
	
	public void btnASRIClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoAsriActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "ASRI");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnTravelClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoTravelActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "TRAVELSAFE");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnTravelDomClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoTravelDomActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "TRAVELDOM");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnMedisafeClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoMedisafeActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "MEDISAFE");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnExecutiveSafeClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoExecutiveActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "EXECUTIVESAFE");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnOfficeClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoTokoActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "TOKO");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnOtomateSyariahClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoOtomateSyariahActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "OTOMATESYARIAH");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnASRISyariahClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoAsriSyariahActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "ASRISYARIAH");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnPAAmanahClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoPAAmanahActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "PAAMANAH");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnACAMobilClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoACAMobilActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "ACAMOBIL");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}

	
	public void btnCargoClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoCargoActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "CARGO");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	

	public void btnWellWomanClick(View v) {
		Intent i = new Intent(getBaseContext(),  InfoWellWomanActivity.class);
		Bundle b = new Bundle();
		b.putString("PRODUCT_TYPE", "WELLWOMAN");
		b.putString("PRODUCT_ACTION", "NEW");
		i.putExtras(b);
		startActivity(i);
		this.finish();
	}
	
	public void btnConvensionalClick(View v) {  
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_konvensional_choose_product);
		dialog.setTitle("Choose Product");


		int textViewId = dialog.getContext().getResources().getIdentifier("android:id/title", null, null);
		TextView tv = (TextView) dialog.findViewById(textViewId);
		tv.setTextColor(getResources().getColor(R.color.Black));
		
 
		Button dialogCompre = (Button) dialog.findViewById(R.id.dlgCompre);
		Button dialogTLO = (Button) dialog.findViewById(R.id.dlgTLO);
		
		// if button is clicked, close the custom dialog
		dialogCompre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),  InfoKonvensionalActivity.class);
				Bundle b = new Bundle();
				b.putString("PRODUCT_TYPE", "KONVENSIONAL");
				b.putString("PRODUCT_ACTION", "NEW");
				b.putString("TIPE_KONVENSIONAL", "COMPREHENSIVE");
				i.putExtras(b);
				startActivity(i);
				ChooseProductActivity.this.finish();
				
				dialog.dismiss();
			}
		});
		
		dialogTLO.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),  InfoKonvensionalActivity.class);
				Bundle b = new Bundle();
				b.putString("PRODUCT_TYPE", "KONVENSIONAL");
				b.putString("PRODUCT_ACTION", "NEW");
				b.putString("TIPE_KONVENSIONAL", "TLO");
				i.putExtras(b);
				startActivity(i);
				ChooseProductActivity.this.finish();
				
				dialog.dismiss();
			}
		});
		
		
 
		dialog.show();
	}
	
	public void btnDNOClick(View v) {
		ShowDialog(R.layout.dialog_dno_choose_language, "Pilih Bahasa");
	}
	

	private void ShowDialog(int layoutID, String title)
	{
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(layoutID);
		dialog.setTitle(title);


		int textViewId = dialog.getContext().getResources().getIdentifier("android:id/title", null, null);
		TextView tv = (TextView) dialog.findViewById(textViewId);
		tv.setTextColor(getResources().getColor(R.color.Black));
		
 
		Button dialogindo= (Button) dialog.findViewById(R.id.dlgIndonesia);
		Button dialoginggris = (Button) dialog.findViewById(R.id.dlgInggris);
		
		// if button is clicked, close the custom dialog
		dialogindo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),  InfoDNOActivity.class);
				Bundle b = new Bundle();
				b.putString("PRODUCT_TYPE", "DNO");
				b.putString("PRODUCT_ACTION", "NEW");
				b.putString("LANGUAGE", "INDONESIA");
				i.putExtras(b);
				startActivity(i);
				ChooseProductActivity.this.finish();
				
				dialog.dismiss();
			}
		});
		
		
		dialoginggris.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),  InfoDNOActivity.class);
				Bundle b = new Bundle();
				b.putString("PRODUCT_TYPE", "DNO");
				b.putString("PRODUCT_ACTION", "NEW");
				b.putString("LANGUAGE", "ENGLISH");
				i.putExtras(b);
				startActivity(i);
				ChooseProductActivity.this.finish();
				
				dialog.dismiss();
			}
		});
		
		
 
		dialog.show();
	}
	

}
