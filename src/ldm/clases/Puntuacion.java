package ldm.clases;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * This class manage the "Mi progreso" menu
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Puntuacion extends Activity {

	private Typeface typeFont;
	private TextView title,todo,t1,t2,t3,t4,t5,t6,t7,t8,t9,hint,media;
	private DBAdapter db;
	private float mediaNotas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puntuacion);
		
		title = (TextView) findViewById(R.id.tu_nota_actual_es_deTextView);
		todo = (TextView) findViewById(R.id.todoPts);
		t1 = (TextView) findViewById(R.id.apisPts);
		t2 = (TextView) findViewById(R.id.lifeCyclePts);
		t3 = (TextView) findViewById(R.id.inputsPts);
		t4 = (TextView) findViewById(R.id.tecladoPts);
		t5 = (TextView) findViewById(R.id.archivosPts);
		t6 = (TextView) findViewById(R.id.viewPts);
		t7 = (TextView) findViewById(R.id.listsPts);
		t8 = (TextView) findViewById(R.id.imagenesPts);
		t9 = (TextView) findViewById(R.id.datosPts);
		hint = (TextView) findViewById(R.id.ConsejoTextView);
		media = (TextView) findViewById(R.id.notaMediaView);
		
		//Formateamos el texto con un tipo de fuente personalizada
		typeFont = Typeface.createFromAsset(getAssets(),"buxtonsketch.ttf");
		
		title.setTypeface(typeFont,Typeface.BOLD); 
		todo.setTypeface(typeFont);
		t1.setTypeface(typeFont);
		t2.setTypeface(typeFont);
		t3.setTypeface(typeFont);
		t4.setTypeface(typeFont);
		t5.setTypeface(typeFont);
		t6.setTypeface(typeFont);
		t7.setTypeface(typeFont);
		t8.setTypeface(typeFont);
		t9.setTypeface(typeFont);
		hint.setTypeface(typeFont);
		media.setTypeface(typeFont,Typeface.BOLD);
		
		db = new DBAdapter(this);
		
		db.open();
		Cursor cursor = db.getScores(Login.currentUser);
		cursor.moveToFirst();
		//Añadimos al texto actual del TextView la puntuacón que tienen el usuario almacenada
		int notaTodo = cursor.getInt(2);
		int notaT1 = cursor.getInt(3);
		int notaT2 = cursor.getInt(4);
		int notaT3 = cursor.getInt(5);
		int notaT4 = cursor.getInt(6);
		int notaT5 = cursor.getInt(7);
		int notaT6 = cursor.getInt(8);
		int notaT7 = cursor.getInt(9);
		int notaT8 = cursor.getInt(10);
		int notaT9 = cursor.getInt(11);
		
		
		int[] notas = {notaTodo,notaT1,notaT2,notaT3,notaT4,notaT5,notaT6,notaT7,notaT8,notaT9};

		todo.setText(todo.getText().toString()+Integer.toString(notaTodo));
		t1.setText(t1.getText().toString()+Integer.toString(notaT1));
		t2.setText(t2.getText().toString()+Integer.toString(notaT2));
		t3.setText(t3.getText().toString()+Integer.toString(notaT3));
		t4.setText(t4.getText().toString()+Integer.toString(notaT4));
		t5.setText(t5.getText().toString()+Integer.toString(notaT5));
		t6.setText(t6.getText().toString()+Integer.toString(notaT6));
		t7.setText(t7.getText().toString()+Integer.toString(notaT7));
		t8.setText(t8.getText().toString()+Integer.toString(notaT8));
		t9.setText(t9.getText().toString()+Integer.toString(notaT9));
		
		ArrayList <TextView> textos = new ArrayList<>();
		textos.add(todo);
		textos.add(t1);
		textos.add(t2);
		textos.add(t3);
		textos.add(t4);
		textos.add(t5);
		textos.add(t6);
		textos.add(t7);
		textos.add(t8);
		textos.add(t9);
	
		int i=0;
		for (TextView view: textos){
			if (notas[i]<5)
				hint.setText(hint.getText()+view.getText().toString()+" ");
			i++;
		}
		
		//Calculamos la nota media
		mediaNotas = 0;
		int suma = 0;
		for (int j=0;j<notas.length;j++)
			suma = suma+notas[j];
		
		mediaNotas = suma/notas.length;
		Log.w("mediaNotas",Float.toString(mediaNotas));
		media.setText(media.getText().toString()+Float.toString(mediaNotas));
		
		db.close();
		
	}


	
}
