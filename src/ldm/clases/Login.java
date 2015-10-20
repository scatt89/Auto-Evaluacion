package ldm.clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ldm.clases.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Manages user identification to enter the application 
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Login extends Activity {

	static String currentUser;
	private DBAdapter db;
	
	private EditText inputUser,inputPass;
	private Button conect, note, register;
	
	final Context context = this;
	
	private Typeface typeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		db = new DBAdapter(this);
		
		inputUser = (EditText) findViewById(R.id.txtUser);
		inputPass = (EditText) findViewById(R.id.txtPass);
		conect = (Button) findViewById(R.id.loginButton);
		note = (Button) findViewById(R.id.noteText);
		register = (Button) findViewById(R.id.registerButton);
		
		typeButton = Typeface.createFromAsset(getAssets(),"buxtonsketch.ttf");
		register.setTypeface(typeButton);
		
		try {
			String destPath = "/data/data/" + getPackageName() + "/databases";
			File f = new File(destPath);
			if (!f.exists()) {
				f.mkdirs();
				f.createNewFile();
				// copy the db from the assets folder into the databases folder
				CopyDB(getBaseContext().getAssets().open("mydb"),
						new FileOutputStream(destPath + "/MyDB"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Si ha pulsado iniciar sesion
		conect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = inputUser.getText().toString();
				String pass = inputPass.getText().toString();
				db.open();
				if (db.checkPass(userName,pass)){
					currentUser = userName;
					onCheckPass(null);
				}
				else
					note.setText("Usuario o contraseña incorrectos, ¿no puedes iniciar sesión?");
				db.close();	
			}
		});
		
		//Si ha pulsado he olvidado mi contraseña
		note.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
 
				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);
 
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
 
				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Aceptar",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    }
					  })
					.setNegativeButton("Cancelar",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}
		});
		
		//Si ha pulsado crear una nueva cuenta
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRegisterListener(null);
			}
		});
		
	}
	
	/**
	 * Copy the database file
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public void CopyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// ---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}

	/**
	 * Launch the main menu
	 * @param view
	 */
	protected void onCheckPass(View view) {
		startActivity(new Intent(this, MainActivity.class));
	}

	/**
	 * Launch the register menu
	 * @param view
	 */
	protected void onRegisterListener(View view) {
		startActivity(new Intent(this, Registro.class));
	}
	
}
