package ldm.clases;

import ldm.clases.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Launch each topic for a test
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Temas extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temas);
	}
	
	/**
	 * Launch "todo" topic
	 * @param view
	 */
	public void lauchTodo (View view){
		Intent intent = new Intent (this, Pregunta.class);
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * "Launch "apis" topic
	 * @param view
	 */
	public void lauchApis (View view){
		Intent intent = new Intent (this, Pregunta.class);
		intent.putExtra("tag", "apis");
		startActivity(intent);
	}
	
	//Como se puede ver, a partir de aqui todos tags son "todo", esto es porque
	//no hemos introducido preguntas de estos temas, así que hemos comentado
	//la línea donde se llama con la etiqueta original de cada tema.
	//No es un error, unicamente haría que introducir las entradas de cada tema
	//en la base de datos.
	
	/**
	 * Launch "lifeCycle" topic
	 * @param view
	 */
	public void lauchLifeCycle (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "lifeCycle");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "inputs" topic
	 * @param view
	 */
	public void lauchInputs (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "inputs");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "keyboard events" topic
	 * @param view
	 */
	public void lauchKeyboardEvents (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "keyboard");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "files" topic
	 * @param view
	 */
	public void lauchFiles (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "files");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "views" topic 
	 * @param view
	 */
	public void lauchViews (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "views");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "ListVIews" topic
	 * @param view
	 */
	public void lauchListViews (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "list");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "Images" topic
	 * @param view
	 */
	public void lauchImages (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "images");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
	
	/**
	 * Launch "datas" topic
	 * @param view
	 */
	public void lauchDatas (View view){
		Intent intent = new Intent (this, Pregunta.class);
		//intent.putExtra("tag", "datas");
		intent.putExtra("tag", "todo");
		startActivity(intent);
	}
}
