package com.aca.pimp;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Months;

import android.R.integer;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aca.database.DBA_LEVEL_HISTORY;
import com.aca.database.DBA_MASTER_AGENT;

public class ChooseProductionMenuActivity extends ControlNormalActivity {
	public static final String ARR_LIST_TAG = "ARR_LIST_TAG";

	public static final String FROM = "FROM";
	public static final String TO = "TO";
	public static final String BRANCH = "BRANCH";
	public static final String USERID = "USERID";
	public static final String LEADER = "LEADER";
	public static final String APPROVAL = "APPROVAL";

	Button btnSearch, btnCancel, btnMyResult;

	EditText txtFrom, txtTo;

	TextView lblBranch, lblUserID, lblLeader;

	Spinner spnBranch, spnUserID, spnLeader, spnPeriode;

	RadioButton rbNeedApprove, rbHasBeenApprove, rbHashBeenRejected;

	RadioGroup rgApproval;

	View sectionResult;

	NumberFormat nf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_choose_production_menu);

		getData();
		InitEditText();
		RegisterListener();
		disableView();
	}

	private void getData() {
		HashMap<String, String> map = new HashMap<String, String>();

		DBA_MASTER_AGENT user = new DBA_MASTER_AGENT(this);
		// final DBA_LEVEL_HISTORY hist = new DBA_LEVEL_HISTORY(this);
		Cursor c = null;
		String userID;

		try {
			user.open();
			c = user.getRow();

			if (!c.moveToFirst())
				return;

			userID = c.getString(c.getColumnIndex(DBA_MASTER_AGENT.USER_ID));
			map.put("UserId", userID);

			GetLevelHistory ws = new GetLevelHistory(
					ChooseProductionMenuActivity.this, map) {
				@Override
				protected void getlevelHistoryList(
						ArrayList<HashMap<String, String>> arrList) {
					if (arrList != null) {  
						bindPeriode(arrList);
					} else {
						Toast.makeText(ChooseProductionMenuActivity.this,
								"Gagal mendapatkan data history",
								Toast.LENGTH_SHORT).show();
					}
				}
			};
			ws.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (user != null)
				user.close();

			if (c != null)
				c.close();

		}

	}

	private void InitEditText() {
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnMyResult = (Button) findViewById(R.id.btnMyResult);

		txtFrom = (EditText) findViewById(R.id.txtFrom);
		txtTo = (EditText) findViewById(R.id.txtTo);

		sectionResult = (View) findViewById(R.id.sectionResult);

		spnBranch = (Spinner) findViewById(R.id.spnBranch);
		spnUserID = (Spinner) findViewById(R.id.spnUserID);
		spnLeader = (Spinner) findViewById(R.id.spnLeader);
		spnPeriode = (Spinner) findViewById(R.id.spnPeriode);

		lblBranch = (TextView) findViewById(R.id.lblBranch);
		lblLeader = (TextView) findViewById(R.id.lblLeader);
		lblUserID = (TextView) findViewById(R.id.lblUserID);

		rbNeedApprove = (RadioButton) findViewById(R.id.rbNeedAppove);
		rbHasBeenApprove = (RadioButton) findViewById(R.id.rbHasBeenApprove);
		rbHashBeenRejected = (RadioButton) findViewById(R.id.rbHasBeenRejected);

		rgApproval = (RadioGroup) findViewById(R.id.rgApproval);

		nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);

	}

	private void RegisterListener() {
		btnMyResult.setOnClickListener(btnMyResultListener());
		spnBranch.setOnItemSelectedListener(spnBranchListener());
		spnLeader.setOnItemSelectedListener(spnLeaderListener());
		btnSearch.setOnClickListener(btnSearchListener());
		btnCancel.setOnClickListener(btnCancelListener());
		txtFrom.setOnClickListener(txtFromListener());
		txtTo.setOnClickListener(txtToListener());
	}

	private HashMap<String, String> getUser() {
		DBA_MASTER_AGENT dbAgent = new DBA_MASTER_AGENT(this);
		Cursor c = null;
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			dbAgent.open();
			c = dbAgent.getRow();

			if (!c.moveToFirst())
				return null;

			map.put("U_ID", c.getString(1));
			map.put("U_NAME", c.getString(13));
			map.put("BRANCH_ID", c.getString(0));
			map.put("BRANCH_NAME", "");
			map.put("ID_LEADER", c.getString(14));
			map.put("ID_ROLE", c.getString(18));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbAgent != null)
				dbAgent.close();

			if (c != null)
				c.close();
		}
		return map;
	}

	private void disableView() {

		txtFrom.setFocusable(false);
		txtTo.setFocusable(false);

		ArrayList<HashMap<String, String>> arrList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = getUser();
		if (map == null)
			return;
		else
			arrList.add(map);

		int roleId = Integer.parseInt(map.get("ID_ROLE"));

		switch (roleId) {
		case -1:

			break;

		case 1:
			lblBranch.setVisibility(View.GONE);
			spnBranch.setVisibility(View.GONE);

			lblUserID.setVisibility(View.GONE);
			spnUserID.setVisibility(View.GONE);

			lblLeader.setVisibility(View.GONE);
			spnLeader.setVisibility(View.GONE);

			break;

		case 2:
			sectionResult.setVisibility(View.GONE);

			lblBranch.setVisibility(View.GONE);
			spnBranch.setVisibility(View.GONE);

			lblLeader.setVisibility(View.GONE);
			spnLeader.setVisibility(View.GONE);

			getUserID(map.get("BRANCH_ID"), map.get("U_ID"));
			break;

		case 3:
			sectionResult.setVisibility(View.GONE);

			getBranch("");
			getLeader("");
			getUserID("", "");
			break;

		default:
			break;
		}
	}

	private void getBranch(String branch) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("Branch", branch);

		GetBranch wsBranch = new GetBranch(this, hmap) {
			@Override
			protected void getBranchList(
					ArrayList<HashMap<String, String>> arrList) {
				if (arrList != null) {
					bindBranch(arrList);
				}
			}
		};
		wsBranch.execute();
	}

	private void getUserID(String branch, String leader) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("Branch", branch);
		hmap.put("Leader", leader);

		GetUserID wsUserid = new GetUserID(this, hmap) {
			@Override
			protected void getUserIDList(
					ArrayList<HashMap<String, String>> arrList) {
				if (arrList != null) {
					bindUserID(arrList);
				}
			}
		};
		wsUserid.execute();

	}

	private void getLeader(String branch) {
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("Branch", branch);

		GetLeader wsLeader = new GetLeader(this, hmap) {
			@Override
			protected void getLeaderList(
					ArrayList<HashMap<String, String>> arrList) {
				if (arrList != null) {
					bindLeader(arrList);
				}
			}
		};
		wsLeader.execute();

	}

	private OnClickListener btnMyResultListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String, String> map = Utility
						.getUserData(ChooseProductionMenuActivity.this);
				final String bulan = ((SpinnerGenericItem) spnPeriode
						.getSelectedItem()).getCode();
				final String tahun = ((SpinnerGenericItem) spnPeriode
						.getSelectedItem()).getDesc2();

				LocalDate tanggal = UtilDate.mergeDate(Integer.parseInt(tahun),
						Integer.parseInt(bulan), 1);
				tanggal = tanggal.dayOfMonth().withMaximumValue();

				map.put("Periode",
						tanggal.toString(UtilDate.DATE_PATTERN_SERVER));

				GetAkumulasi ws = new GetAkumulasi(
						ChooseProductionMenuActivity.this, map) {

					@Override
					protected void getAkumulasiList(
							ArrayList<HashMap<String, String>> arrList) {
						if (arrList != null) {
							showResult(arrList, bulan);
						}

					}
				};
				ws.execute();

			}

			private void showResult(ArrayList<HashMap<String, String>> arrList,
					String bulan) {
				final Dialog dialog = new Dialog(
						ChooseProductionMenuActivity.this);
				dialog.setContentView(R.layout.dialog_my_result);
				dialog.setTitle("My Result");

				int textViewId = dialog.getContext().getResources()
						.getIdentifier("android:id/title", null, null);
				TextView tv = (TextView) dialog.findViewById(textViewId);
				tv.setTextColor(getResources().getColor(R.color.Black));

				Button dialogButtonCancel = (Button) dialog
						.findViewById(R.id.dlgBack);
				TextView txtLevel = (TextView) dialog
						.findViewById(R.id.txtLevel);
				TextView txtAkumulasi = (TextView) dialog
						.findViewById(R.id.txtAkumulasiTotal);
				TextView txtTarget = (TextView) dialog
						.findViewById(R.id.txtTarget);
				TextView txtSalary = (TextView) dialog
						.findViewById(R.id.txtSalary);
				TextView txtBasicAllowance = (TextView) dialog
						.findViewById(R.id.txtBasicAllowance);
				TextView txtTotalAllowance = (TextView) dialog
						.findViewById(R.id.txtTotalAllowance);
				TextView txtStatus = (TextView) dialog
						.findViewById(R.id.txtStatus);
				TextView txtResult = (TextView) dialog
						.findViewById(R.id.txtResult);

				TextView txtPeriode = (TextView) dialog
						.findViewById(R.id.txtPeriode);
				TextView txtPenambahan = (TextView) dialog
						.findViewById(R.id.txtPenambahan);
				TextView txtPemotongan = (TextView) dialog
						.findViewById(R.id.txtPemotongan);

				txtStatus.setText(arrList.get(0).get("STATUS"));
				txtLevel.setText(arrList.get(0).get("LEVEL"));
				txtAkumulasi.setText("Rp. "+ nf.format(Double.parseDouble(arrList.get(0).get("AKUMULASI"))));
				txtTarget.setText("Rp. "+ nf.format(Double.parseDouble(arrList.get(0).get("TARGET")))); 
				txtPeriode.setText(((SpinnerGenericItem) spnPeriode.getSelectedItem()).getDesc());
				
				double reward = Double.parseDouble(arrList.get(0).get("REWARD"));
				double basicAllowance = Double.parseDouble(arrList.get(0).get("BASIC_ALLOWANCE"));
				double penambahan = Double.parseDouble(arrList.get(0).get("PENAMBAHAN"));
				double pemotongan = Double.parseDouble(arrList.get(0).get("PEMOTONGAN"));
				double result = reward + basicAllowance + penambahan - pemotongan;
				
				txtSalary.setText("Rp. "+ nf.format(reward));
				txtBasicAllowance.setText("Rp. "+ nf.format(basicAllowance));
				txtPenambahan.setText("Rp. " + nf.format(penambahan));
				txtPemotongan.setText("Rp. " + nf.format(pemotongan));

				txtTotalAllowance.setText("Rp. "+ nf.format(result));
				txtResult.setText(arrList.get(0).get("PASSED"));
				 
				if (txtResult.getText().toString().equalsIgnoreCase("PASSED"))
					txtResult.setTextColor(Color.BLUE);
				else
					txtResult.setTextColor(Color.RED);

				dialogButtonCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}

		};
	}

	private OnClickListener btnCancelListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				Back();
			}
		};
	}

	private OnClickListener txtToListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtTo.setError(null);

				DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker view, int selectedYear,
							int selectedMonth, int selectedDay) {
						String uiDate = Utility.setUIDate(selectedYear,
								selectedMonth, selectedDay);
						txtTo.setText(uiDate);
					}
				};

				Calendar c = Utility.getCalendar(txtTo.getText().toString());
				Dialog dialog = new DatePickerDialog(
						ChooseProductionMenuActivity.this, datePickerListener,
						c.get(Calendar.YEAR), c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH));

				dialog.show();
			}
		};
	}

	private OnClickListener txtFromListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtFrom.setError(null);
				DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker view, int selectedYear,
							int selectedMonth, int selectedDay) {
						String uiDate = Utility.setUIDate(selectedYear,
								selectedMonth, selectedDay);
						txtFrom.setText(uiDate);

					}
				};

				Calendar c = Utility.getCalendar(txtFrom.getText().toString());
				Dialog dialog = new DatePickerDialog(
						ChooseProductionMenuActivity.this, datePickerListener,
						c.get(Calendar.YEAR), c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH));

				dialog.show();
			}
		};
	}

	private OnClickListener btnSearchListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validasiNext()) {
					Bundle bundle = getData();

					Intent intent = new Intent(
							ChooseProductionMenuActivity.this,
							ChooseProductionReportActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}

			private Bundle getData() {
				HashMap<String, String> map = getUser();
				int roleId = Integer.parseInt(map.get("ID_ROLE"));

				Bundle bundle = new Bundle();

				switch (roleId) {
				case 1:
					bundle.putString(FROM, Utility.formatDate(txtFrom.getText()
							.toString(), Utility.standardDatePattern,
							Utility.sqlDatePattern));
					bundle.putString(TO, Utility.formatDate(txtTo.getText()
							.toString(), Utility.standardDatePattern,
							Utility.sqlDatePattern));
					bundle.putString(BRANCH, map.get("BRANCH_ID"));
					bundle.putString(USERID, map.get("U_ID"));
					bundle.putString(LEADER, map.get("ID_LEADER"));
					bundle.putString(APPROVAL, rbNeedApprove.isChecked() ? "0"
							: rbHasBeenApprove.isChecked() ? "1" : "2");

					break;

				case 2:
					bundle.putString(FROM, Utility.formatDate(txtFrom.getText()
							.toString(), Utility.standardDatePattern,
							Utility.sqlDatePattern));
					bundle.putString(TO, Utility.formatDate(txtTo.getText()
							.toString(), Utility.standardDatePattern,
							Utility.sqlDatePattern));
					bundle.putString(BRANCH, map.get("BRANCH_ID"));
					bundle.putString(USERID, ((SpinnerGenericItem) spnUserID
							.getSelectedItem()).getCode());
					bundle.putString(LEADER, map.get("U_ID"));
					bundle.putString(APPROVAL, rbNeedApprove.isChecked() ? "0"
							: rbHasBeenApprove.isChecked() ? "1" : "2");
					break;

				case 3:
					bundle.putString(FROM, Utility.formatDate(txtFrom.getText()
							.toString(), Utility.standardDatePattern,
							Utility.sqlDatePattern));
					bundle.putString(TO, Utility.formatDate(txtTo.getText()
							.toString(), Utility.standardDatePattern,
							Utility.sqlDatePattern));
					bundle.putString(BRANCH, ((SpinnerGenericItem) spnBranch
							.getSelectedItem()).getCode());
					bundle.putString(USERID, ((SpinnerGenericItem) spnUserID
							.getSelectedItem()).getCode());
					bundle.putString(LEADER, ((SpinnerGenericItem) spnLeader
							.getSelectedItem()).getCode());
					bundle.putString(APPROVAL, rbNeedApprove.isChecked() ? "0"
							: rbHasBeenApprove.isChecked() ? "1" : "2");
					break;

				default:
					break;
				}

				return bundle;
			}

			private boolean validasiNext() {
				if (!Utility.isEmptyField(txtFrom))
					return false;
				if (!Utility.isEmptyField(txtTo))
					return false;

				if (Utility.compareDate(txtFrom.getText().toString(),
						Utility.GetTodayString()) > 0) {
					Toast.makeText(ChooseProductionMenuActivity.this,
							"Invalid Date", Toast.LENGTH_SHORT).show();
					return false;
				}

				if (Utility.compareDate(txtTo.getText().toString(), txtFrom
						.getText().toString()) < 0) {
					Toast.makeText(ChooseProductionMenuActivity.this,
							"Invalid Date", Toast.LENGTH_SHORT).show();
					return false;
				}

				if (Utility.monthDiff(txtFrom.getText().toString(), txtTo
						.getText().toString()) > 3) {
					Toast.makeText(ChooseProductionMenuActivity.this,
							"Maksimum 3 months", Toast.LENGTH_SHORT).show();
					return false;
				}
				if (Utility.yearDiff(txtFrom.getText().toString(), txtTo
						.getText().toString()) >= 1) {
					Toast.makeText(ChooseProductionMenuActivity.this,
							"Maksimum 3 months", Toast.LENGTH_SHORT).show();
					return false;
				}
				return true;
			}
		};
	}

	private OnItemSelectedListener spnLeaderListener() {
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int post, long id) {
				String branch = ((SpinnerGenericItem) spnBranch
						.getSelectedItem()).getCode();
				String leader = ((SpinnerGenericItem) spnLeader
						.getSelectedItem()).getCode();

				getUserID(branch, leader);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};
	}

	private OnItemSelectedListener spnBranchListener() {
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int post, long id) {
				String branch = ((SpinnerGenericItem) adapter
						.getItemAtPosition(post)).getCode();
				getLeader(branch);
				spnUserID.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_production, menu);
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
	public void onBackPressed() {
		Back();
	}

	private void Back() {
		Intent i = new Intent(getBaseContext(), FirstActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
		this.finish();
	}

	public void btnHomeClick(View v) {
		Intent i = new Intent(getBaseContext(), FirstActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);

		startActivity(i);
		this.finish();
	} 

	private void bindPeriode(ArrayList<HashMap<String, String>> arrList) {
		List<SpinnerGenericItem> spinnerGenericList = new LinkedList<SpinnerGenericItem>();
		SpinnerGenericAdapter spinnerGenericAdapter = new SpinnerGenericAdapter(
				spinnerGenericList, this);
		String monthPattern = "MMM";
		String namabulan;
		int bulan, tahun;
		LocalDate tanggal;
		try {
			for (HashMap<String, String> hmap : arrList) {
				bulan = Integer.parseInt(hmap.get("BULAN"));
				tahun = Integer.parseInt(hmap.get("TAHUN"));
				tanggal = UtilDate.mergeDate(tahun, bulan, 1);
				namabulan = tanggal.toString(monthPattern);

				spinnerGenericList.add(new SpinnerGenericItem(bulan + "", namabulan + " " + tahun, tahun + ""));
			}

			spnPeriode.setAdapter(spinnerGenericAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindBranch(ArrayList<HashMap<String, String>> arrList) {
		List<SpinnerGenericItem> spinnerGenericList = new LinkedList<SpinnerGenericItem>();
		SpinnerGenericAdapter spinnerGenericAdapter = new SpinnerGenericAdapter(
				spinnerGenericList, this);

		try {
			spinnerGenericList.add(new SpinnerGenericItem("", "ALL"));
			for (HashMap<String, String> hmap : arrList) {
				spinnerGenericList.add(new SpinnerGenericItem(hmap
						.get("BRANCH_ID"), hmap.get("BRANCH_NAME")));
			}

			spnBranch.setAdapter(spinnerGenericAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindLeader(ArrayList<HashMap<String, String>> arrList) {
		List<SpinnerGenericItem> spinnerGenericList = new LinkedList<SpinnerGenericItem>();
		SpinnerGenericAdapter spinnerGenericAdapter = new SpinnerGenericAdapter(
				spinnerGenericList, this);

		try {
			spinnerGenericList.add(new SpinnerGenericItem("", "ALL"));
			for (HashMap<String, String> hmap : arrList) {
				spinnerGenericList.add(new SpinnerGenericItem(hmap
						.get("ID_LEADER"), hmap.get("U_NAME")));
			}

			spnLeader.setAdapter(spinnerGenericAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindUserID(ArrayList<HashMap<String, String>> arrList) {
		List<SpinnerGenericItem> spinnerGenericList = new LinkedList<SpinnerGenericItem>();
		SpinnerGenericAdapter spinnerGenericAdapter = new SpinnerGenericAdapter(
				spinnerGenericList, this);

		try {
			spinnerGenericList.add(new SpinnerGenericItem("", "ALL"));
			for (HashMap<String, String> hmap : arrList) {
				spinnerGenericList.add(new SpinnerGenericItem(hmap.get("U_ID"),
						hmap.get("U_NAME")));
			}

			spnUserID.setAdapter(spinnerGenericAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
