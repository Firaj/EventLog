package com.oz.eventlog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

public class MainActivity extends Activity implements OnItemClickListener {

	// navigator
	private DrawerLayout drawerLayout;
	private ListView listView, recentList;
	private String controls[];
	private LinearLayout layer;
	// Google Map
	private GoogleMap googleMap;
	MapView mMapView;

	// drawer
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	
	Button joinus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		controls = getResources().getStringArray(R.array.controls);

		navigator();
		
		joinus = (Button) findViewById(R.id.joinus);
		
		joinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),JoinUsActivity.class);
				startActivity(intent);
			}
		});

		recentList = (ListView) findViewById(R.id.recentList);
		recentList.setAdapter(new NavigatorAdapter(this, R.layout.recentitem,
				controls));
		recentList.setOnItemClickListener(this);

		mMapView = (MapView) findViewById(R.id.mapView);
		mMapView.onCreate(savedInstanceState);

		mMapView.onResume();// needed to get the map to display immediately

		try {
			MapsInitializer.initialize(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		googleMap = mMapView.getMap();
		googleMap.setMyLocationEnabled(true);

		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		// latitude and longitude
		double latitude = 28.53033;
		double longitude = 83.877905;

		// create marker
		// MarkerOptions marker = new MarkerOptions().position(new
		// LatLng(latitude, longitude)).title("Hello Maps");

		// Changing marker icon
		// marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));

		// adding marker
		// googleMap.addMarker(marker);

		// CameraPosition cameraPosition = new CameraPosition.Builder().target(
		// new LatLng(28.53033,83.877905)).zoom(12).build();

		// googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}

	private void navigator() {
		// navigator
		layer = (LinearLayout) findViewById(R.id.layer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};

		drawerArrow.setColor(R.color.gray);
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		listView = (ListView) findViewById(R.id.drawerList);
		listView.setAdapter(new NavigatorAdapter(this, R.layout.navigator,
				controls));
		listView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (drawerLayout.isDrawerOpen(layer)) {
				drawerLayout.closeDrawer(layer);
			} else {
				drawerLayout.openDrawer(layer);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
