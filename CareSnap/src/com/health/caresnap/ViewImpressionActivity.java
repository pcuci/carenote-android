package com.health.caresnap;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewImpressionActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		updateList();

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.impression, menu);
		return true;
	}

	private void updateList() {
		CaptureSessionGlobal global = ((CaptureSessionGlobal) getApplicationContext());
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.activity_view_impression,
				convertList(global.getAllImpressions())));
	}

	private List<String> convertList(List<Impression> list) {
		List<String> result = new ArrayList<String>();
		for (Impression entry : list) {
			result.add(entry.getTime() + ": Doctor: " + entry.getName() + " - "
					+ entry.getSpecialty() + " @ " + entry.getLocation()
					+ "\n" + entry.getNote());
		}
		return result;
	}
}
