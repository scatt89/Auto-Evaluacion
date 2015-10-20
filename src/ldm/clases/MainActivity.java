package ldm.clases;

import ldm.clases.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Manages the app main menu
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * Launch the "Evaluación" menu
	 * @param view
	 */
	public void lauchTemas(View view) {
		startActivity(new Intent(this, Temas.class));
	}

	/**
	 * Launch the "Mi Progreso" menu
	 * @param view
	 */
	public void lauchPuntuacion(View view) {
		startActivity(new Intent(this, Puntuacion.class));
	}

	/**
	 * Launch the "Mis Datos" menu
	 * @param view
	 */
	public void lauchUsuario(View view) {
		startActivity(new Intent(this, MisDatos.class));
	}
	
	/**
	 * Launch the "Ranking" menu
	 * @param view
	 */
	public void lauchRanking(View view) {
		startActivity(new Intent(this, Ranking.class));
	}

	/**
	 * Launch the "Ajustes" menu
	 * @param view
	 */
	public void lauchAjustes(View view) {
		startActivity(new Intent(this, Ajustes.class));
	}
	
	/**
	 * Launch the "Salir" button
	 * @param view
	 */
	public void lauchSalir(View view) {
		finish();
	}

}
