package com.aca.pimp;

import java.util.ArrayList;
import java.util.HashMap;
  
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.os.Looper;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhotoShowActivity extends ControlNormalActivity { 
	private ListView lv; 	
	private String SppaNo, approval, productType;
	private double tsi;
	private ProgressDialog progressDialog;
	private View sectionBottom;
	protected int mShortAnimationDuration;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_photo_show);
		
		init();
		initProgressDialog();
		getIntentData();
		changeLayout();
		getProductDocumentData();
	}

	private void getIntentData() {
		if (getIntent().getExtras().get("SPPA_NO") != null) {
			SppaNo = getIntent().getExtras().get("SPPA_NO").toString();
			approval = getIntent().getExtras().getString("APPROVAL");
			productType = getIntent().getExtras().getString("PRODUCT_TYPE");
		}
		if (getIntent().getExtras().get("TSI") != null) {
			tsi = (Double) getIntent().getExtras().get("TSI"); 
		}
	}

	private void init() { 
		lv			= (ListView)findViewById(R.id.lvPhoto);
		sectionBottom = (View)findViewById(R.id.sectionBottom);
	}


	private void initProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Loading Images ...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}
	private void showProgressDialog() {
		progressDialog.show();
	}
	private void hideProgressDialog() {
		if (progressDialog == null)
			return;
		
		progressDialog.hide();
		progressDialog.dismiss();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_show, menu);
		return true;
	}

	private void getProductDocumentData () {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("SppaNo", SppaNo);
		
		GetProductDocument ws = new GetProductDocument(this, map ) {
			
			@Override
			protected void getProductDocumentList(
					final ArrayList<HashMap<String, String>> arrList) {
				if (arrList != null) {
					 
					showProgressDialog();
					SampleAdapter adapter = new SampleAdapter(PhotoShowActivity.this, arrList);
					adapter.notifyDataSetChanged();
					lv.setAdapter(adapter);
					hideProgressDialog();
					 
				}
				
			}
		};
		ws.execute();
	}
	 
	private void getPictures (final ImageView imgPhoto, final ProgressBar pbLoading, String imgName, final View convertView) {		
		String url = Utility.getSppaimageurl();
		String sppa = url + SppaNo + "/" + imgName;
		
		Picasso.with(PhotoShowActivity.this)
		.load(sppa)
		.placeholder(R.drawable.no_image)
	    .error(R.drawable.no_image)
	    .into(imgPhoto, new Callback() {
			
			@Override
			public void onSuccess() {
				imgPhoto.setVisibility(View.VISIBLE);
				pbLoading.setVisibility(View.GONE);
				
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Utility.popupImage(PhotoShowActivity.this, imgPhoto.getDrawable());
					}
				});
			}
			
			@Override
			public void onError() {
				
			}
		});
	}
	
	

	private void changeLayout() {
		HashMap<String, String> map = Utility.getUserData(this);
		int roleid = Integer.parseInt(map.get("IdRole"));
				
		switch (roleid) {
		case 1:
			sectionBottom.setVisibility(View.GONE); 
			break; 
		default:
			if (approval.equalsIgnoreCase("1") || approval.equalsIgnoreCase("2")) {
				sectionBottom.setVisibility(View.GONE); 
			}
			break;
		} 
	}
	
	public boolean isValidLimit () {
		Double limit = Utility.getLimit(this, productType);
	
		if (limit.isNaN()) 
			return true;
		
		if (tsi <= limit)
			return true;
		else
			return false;
	}
	public void btnApproveClick (View v) {
		if (!isValidLimit()) {
			Utility.showCustomDialogInformation(this, "Limit Mentor tidak mencukupi", "Silahkan hubungi Special-Agent untuk melakukan approval");
			return;
		}
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("SppaNo", getIntent().getExtras().getString("SPPA_NO"));
		hmap.put("IsApprove", "1");
		hmap.put("Remark", "");
		
		ApproveProduction ws = new ApproveProduction(this, hmap) { 
			@Override
			protected void getFlagApprovalProduction(Boolean flag) {
				if (flag) 
					showDialog("disetujui");
				else 
					Utility.showCustomDialogInformation(PhotoShowActivity.this, "Informasi", "Produksi gagal di-approve").show();
			}
		};
		ws.execute(); 
	}
 
	
	public void btnRejectClick (View v) {
		final Dialog dialog =  new Dialog(this); 
		dialog.setContentView(R.layout.dialog_remark);
		dialog.setTitle("Remark");
		dialog.show();
		
		final EditText txtRemark = (EditText)dialog.findViewById(R.id.txtRemark);
		final Button btnOk 		 = (Button)dialog.findViewById(R.id.btnOk);
		
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (txtRemark.getText().toString().isEmpty()) {
					txtRemark.setError("Please fill the field");
					return;
				}
				
				HashMap<String, String> hmap = new HashMap<String, String>();
				hmap.put("SppaNo", getIntent().getExtras().getString("SPPA_NO"));
				hmap.put("IsApprove", "2");
				hmap.put("Remark", txtRemark.getText().toString());
				
				ApproveProduction ws = new ApproveProduction(PhotoShowActivity.this, hmap) { 
					@Override
					protected void getFlagApprovalProduction(Boolean flag) {
						if (flag) 
							showDialog("ditolak");
						else 
							Utility.showCustomDialogInformation(PhotoShowActivity.this, "Informasi", "Produksi gagal di-reject").show();
				
					}
				};
				ws.execute();
				dialog.dismiss();
			}
		});
		 
	} 
	
	
	public void showDialog(String message) {
		Dialog dialog =  Utility.showCustomDialogInformation(this, "Informasi", "Produksi berhasil " + message);
		dialog.show();
		dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) { 
				PhotoShowActivity.this.finish();
				Utility.getTempactivity().finish();
				
			}
		}); 
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
            return arrList.size();
        }

        @Override
        public HashMap getItem(int position) {
            return arrList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            @SuppressWarnings("rawtypes")
			final HashMap item = getItem(position);
            
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.list_item_photo_approve, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            } 
            getPictures(holder.imgPhoto, holder.pbLoading, item.get("DOCUMENT_FILENAME").toString(), convertView);   
            holder.txtDescription.setText(item.get("DOCUMENT_FILENAME").toString());
             
            return convertView;
        }

		
    } 
	 
	 private static class ViewHolder {

	        private View view; 
	        private ImageView imgPhoto;
	        private TextView txtDescription;
	        private ProgressBar pbLoading;

	        private ViewHolder(View view) {
	            this.view 		= view;
	           
	            imgPhoto		= (ImageView) view.findViewById(R.id.imgPhoto); 
	            txtDescription  = (TextView)view.findViewById(R.id.txtDesc);
	            pbLoading		= (ProgressBar)view.findViewById(R.id.pbPhoto);
	        }
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
