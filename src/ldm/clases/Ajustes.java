package ldm.clases;

import ldm.clases.R;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Manages the "Ajustes" menu
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Ajustes extends Activity {

	private CheckBox sound_check, time_check, pass_Check;

	private DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajustes);

		sound_check = (CheckBox) findViewById(R.id.activar_sonidoCheckBox);
		time_check = (CheckBox) findViewById(R.id.activar_tiempocheckBox);
		pass_Check = (CheckBox) findViewById(R.id.activar_passCheckBox);

		Typeface type = Typeface.createFromAsset(getAssets(),
				"buxtonsketch.ttf");
		sound_check.setTypeface(type);
		time_check.setTypeface(type);
		pass_Check.setTypeface(type);

		db = new DBAdapter(this);
		/*
		 * Mostramos las preferencias actuales del usuario, para ello
		 * consultamos en la BD en su tabla de preferencias
		 */
		db.open();
		Cursor cursor = db.getPreferences(Login.currentUser);
		cursor.moveToFirst();
		// Establecemos el checkbox de activar sonido
		if (cursor.getInt(2) == 0)
			sound_check.setChecked(false);
		else
			sound_check.setChecked(true);
		// Activar tiempo
		if (cursor.getInt(3) == 0)
			time_check.setChecked(false);
		else
			time_check.setChecked(true);
		// Activer recordar contraseña
		if (cursor.getInt(4) == 0)
			pass_Check.setChecked(false);
		else
			pass_Check.setChecked(true);
		db.close();

		//Listener del checkBox de sonido para actualizar su valor en la BD
		sound_check
		.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				db.open();
				if (isChecked) {
					db.updatePreferences(Login.currentUser,"sonido",1);
				} else {
					db.updatePreferences(Login.currentUser,"sonido",0);
				}
				db.close();
			}
		});
		//Listener del checkBox de tiempo para actualizar su valor en la BD
		time_check
		.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				db.open();
				if (isChecked) {
					db.updatePreferences(Login.currentUser,"tiempo",1);
				} else {
					db.updatePreferences(Login.currentUser,"tiempo",0);
				}
				db.close();
			}
		});
		//Listener del checkBox de recordar contraseña para actualizar su valor en la BD
		pass_Check
		.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				db.open();
				if (isChecked) {
					db.updatePreferences(Login.currentUser,"rememberPass",1);
				} else {
					db.updatePreferences(Login.currentUser,"rememberPass",0);
				}
				db.close();
			}
		});
	}


}
