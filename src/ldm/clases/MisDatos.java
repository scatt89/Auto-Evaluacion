package ldm.clases;

import ldm.clases.R;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Show all user register data
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class MisDatos extends Activity {

	TextView nombreT,nombre,emailT,email,passT,pass;
	
	DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_misdatos);
		
		nombre = (TextView) findViewById(R.id.introUser);
		email = (TextView) findViewById(R.id.introEmailTextView);
		nombreT = (TextView) findViewById(R.id.nombreUsuario);
		emailT = (TextView) findViewById(R.id.emailTextView);
		pass = (TextView) findViewById(R.id.introPassTextView);
		passT = (TextView) findViewById(R.id.passTextView);
		
		Typeface type = Typeface.createFromAsset(getAssets(),"buxtonsketch.ttf"); 
		nombreT.setTypeface(type,Typeface.BOLD);
		emailT.setTypeface(type,Typeface.BOLD);
		passT.setTypeface(type,Typeface.BOLD);
		
		db = new DBAdapter(this);
		
		db.open();
		Cursor cursor = db.getUser(Login.currentUser);
		cursor.moveToFirst();
		nombre.setText(cursor.getString(1));
		email.setText(cursor.getString(2));
		pass.setText(cursor.getString(3));
		db.close();
		
	}


}
