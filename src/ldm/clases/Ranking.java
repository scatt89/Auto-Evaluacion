package ldm.clases;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Manages the "Ranking" Activity
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Ranking extends ListActivity {

	private DBAdapter db;
	private Cursor cursor;
	private ArrayList<String> elementos = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_ranking);
		 
		db = new DBAdapter(this);
		
		db.open();
		cursor = db.getAllScores();
		if (cursor.moveToFirst()) {
			do {
				elementos.add(cursor.getString(1)+": "+Float.toString(getAverage(cursor)));
			} while (cursor.moveToNext());
		}
		db.close();	
		
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,elementos));
	}

	/**
	 * Calculate the average rating user
	 * @param cursor
	 * @return float
	 */
	private float getAverage(Cursor cursor) {
		float average = 0;
		int nTotal = 1;
		for (int i=2; i<cursor.getColumnCount();i++){
			average = average+cursor.getInt(i);
			nTotal++;
		}
		return average==0?0:average/nTotal;
	}
	
}
