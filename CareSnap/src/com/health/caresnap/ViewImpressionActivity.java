package com.health.caresnap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewImpressionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_impression);
//		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_view_impression,));
//		 
//		ListView listView = getListView();
//		listView.setTextFilterEnabled(true);
// 
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//			    // When clicked, show a toast with the TextView text
//			    Toast.makeText(getApplicationContext(),
//				((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//			}
//		});
// onResu
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.impression, menu);
		return true;
	}

}
