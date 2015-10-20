package ldm.clases;

import ldm.clases.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Manages the registration of a new user
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Registro extends Activity {

	EditText inputUser, inputEmail, inputPass, confirmPass;
	Button ok;

	Context context;

	DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		inputUser = (EditText) findViewById(R.id.introUserEditText);
		inputEmail = (EditText) findViewById(R.id.introEmailEditText);
		inputPass = (EditText) findViewById(R.id.introPassEditText);
		confirmPass = (EditText) findViewById(R.id.confirmIntroPassEditText);
		ok = (Button) findViewById(R.id.aceptarButton);

		context = this;
		db = new DBAdapter(this);

		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = inputUser.getText().toString();
				String email = inputEmail.getText().toString();
				String pass = inputPass.getText().toString();
				String cPass = confirmPass.getText().toString();

				if (pass.equals(cPass)) {
					// Password debe tener una longitud minima de 3 caracteres
					if (pass.length() < 3)
						dialogPassShort();
					else if (userName.length() < 3)
						dialogNameShort();
					else if (inputEmail.length()==0)
						dialogEmailShort();
					else {
						db.open();
						// Creamos una nueva entrada en la tabla de usuarios
						db.insertUser(userName, email, cPass);
						// Nueva entrada en la tabla de puntuaciones
						db.insertScores(userName);
						// Nueva entrada en la tabla de preferencias
						db.insertPreferences(userName, 0, 0, 1);

						Toast.makeText(context, "Usuario creado con éxito",
								Toast.LENGTH_LONG).show();
						db.close();

						launchLogin(null);
					}
				} else {
					dialogPass();
				}
			}
		});
	}

	protected void dialogPass() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Las contraseñas de los campos Contraseña y Confirmar contraseña no coinciden")
				.setTitle("Error al crear contraseña")
				.setCancelable(false)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// No se hace nada
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void dialogPassShort() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"La contraseña debe tener una longitud mínima de 3 caracteres")
				.setTitle("Error al crear usuario")
				.setCancelable(false)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// No se hace nada
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void dialogNameShort() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"El nombre de usuario debe tener una longitud mínima de 3 caracteres")
				.setTitle("Error al crear usuario")
				.setCancelable(false)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// No se hace nada
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void dialogEmailShort() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Debe rellenar el campo \"email\"")
				.setTitle("Error al crear usuario")
				.setCancelable(false)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// No se hace nada
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	protected void launchLogin(View view) {
		startActivity(new Intent(this, Login.class));
	}

}
