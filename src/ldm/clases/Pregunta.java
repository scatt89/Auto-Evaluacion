package ldm.clases;

import java.util.Arrays;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * This class schedule the questions appear
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class Pregunta extends Activity {

	private TextView pregunta;
	private RadioGroup group;
	private RadioButton resp1, resp2, resp3, resp4;
	private Typeface typeQuestion,typeAnswer,typeButton;
	private DBAdapter db;
	private Cursor c;
	private Vector<String[]> preguntas = new Vector<String[]>();
	private int contadorPreguntas = 0;
	private int score = 0;

	private int[] seleccion = new int[10];

	private Button atras, siguiente, corregir;
	private int contadorSelecion = 0;
	private int contadorResultados = 0; 

	private boolean resultadoCorregido = false;
	
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pregunta);

		// Initialize used variables
		pregunta = (TextView) findViewById(R.id.textView1);
		group = (RadioGroup) findViewById(R.id.radioGroup1);
		resp1 = (RadioButton) findViewById(R.id.radioButton1);
		resp2 = (RadioButton) findViewById(R.id.radioButton2);
		resp3 = (RadioButton) findViewById(R.id.radioButton3);
		resp4 = (RadioButton) findViewById(R.id.radioButton4);
		atras = (Button) findViewById(R.id.atras);
		siguiente = (Button) findViewById(R.id.siguiente);
		corregir = (Button) findViewById(R.id.corregir);
		
		setFontType();

		db = new DBAdapter(this);

		Arrays.fill(seleccion, -1);
		bundle = getIntent().getExtras();
		initializeQuestions(bundle.getString("tag"));
		corregir.setVisibility(View.INVISIBLE);
		atras.setVisibility(View.INVISIBLE);
		// Set the first question
		DisplayQuestion(preguntas.get(contadorPreguntas));

		// Starting the checkbox listeners
		checkQuestions();
		
		
	}

	/**
	 * @param bundle
	 */
	private void updateScores(Bundle bundle) {
		db.open();
		db.updateScore(Login.currentUser, bundle.getString("tag"), score);
		db.close();
	}

	/**
	 * Set the font type for question and answers
	 */
	private void setFontType() {
		typeQuestion = Typeface.createFromAsset(getAssets(),"buxtonsketch.ttf");
		typeAnswer = Typeface.createFromAsset(getAssets(),"buxtonsketch.ttf");
		typeButton = Typeface.createFromAsset(getAssets(), "buxtonsketch.ttf");
		
		pregunta.setTypeface(typeQuestion);
		resp1.setTypeface(typeAnswer);
		resp2.setTypeface(typeAnswer);
		resp3.setTypeface(typeAnswer);
		resp4.setTypeface(typeAnswer);
		corregir.setTypeface(typeButton,Typeface.BOLD);
	}

	/**
	 * Open the database, make the retrieves with a cursor object, and add each
	 * result to Vector object
	 */
	public void initializeQuestions(String tag) {
		db.open();
		c = db.getQuestions(10,tag);
		if (c.moveToFirst()) {
			do {
				preguntas.add(getRow(c));
			} while (c.moveToNext());
		}
		db.close();
	}

	/**
	 * Generate a String array each one of values retrieve by the cursor
	 * 
	 * @param cursor
	 * @return String[]
	 */
	public String[] getRow(Cursor cursor) {
		String fila[] = new String[8];
		for (int i = 0; i < 8; i++) {
			fila[i] = cursor.getString(i);
		}
		return fila;
	}

	/**
	 * Display the test Activity to each question with a String[] param
	 * 
	 * @param row
	 */
	public void DisplayQuestion(String[] row) {
		pregunta.setText(row[1]);
		resp1.setText(row[3]);
		resp2.setText(row[4]);
		resp3.setText(row[5]);
		resp4.setText(row[6]);
	}

	/**
	 * Implements the checkgroup listener
	 */

	/*
	 * lo utlizo solo para guardar el resultado seleccionada en el array de
	 * seleccion
	 */
	public void checkQuestions() {
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (!resultadoCorregido) {

					int repuestaSelect = 0;

					if (checkedId == resp1.getId())
						repuestaSelect = 1;

					else if (checkedId == resp2.getId())
						repuestaSelect = 2;
					else if (checkedId == resp3.getId())
						repuestaSelect = 3;
					else if (checkedId == resp4.getId())
						repuestaSelect = 4;

					if (group.getCheckedRadioButtonId() != -1) {

						seleccion[contadorPreguntas] = repuestaSelect;
						contadorSelecion++;

					}

				}

			}
		});

		/*
		 * Hay dos opciones que depende del booleano resultadoCorregido si
		 * todavia no hemos corregido el resultado solo mostramos la preguntas
		 * para selecionar para poder selecionar la respuesta pero esta corregir
		 * solo mostramos las repuesta correctas con las que ha contestado mal
		 */
		siguiente.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!resultadoCorregido) {

					group.clearCheck();
					if (contadorPreguntas == 8) {
						siguiente.setVisibility(View.INVISIBLE);
						corregir.setVisibility(View.VISIBLE);

					}
					
					
					if (contadorPreguntas < 9) {

						contadorPreguntas++;
						
						if (seleccion[contadorPreguntas] != -1 )
							group.check(devolverButton(seleccion[contadorPreguntas]).getId());


						DisplayQuestion(preguntas.get(contadorPreguntas));
						atras.setVisibility(View.VISIBLE);
					}
				}
				// cuando el test esta corregido
				else {

					contadorResultados++;

					if (contadorResultados == 9) {
						siguiente.setVisibility(View.INVISIBLE);
						// corregir.setVisibility(View.VISIBLE);

					}

					if (contadorResultados < 10) {

						String[] aux_p = preguntas.get(contadorResultados - 1);
						devolverButton(Integer.parseInt(aux_p[2]))
								.setTextColor(Color.GRAY);
						if (seleccion[contadorResultados - 1] != -1)
							devolverButton(seleccion[contadorResultados - 1])
									.setTextColor(Color.GRAY);

						DisplayQuestion(preguntas.get(contadorResultados));
						group.clearCheck();
						String[] aux = preguntas.get(contadorResultados);
						desactivar();

						if (seleccion[contadorResultados] == -1) {

							group.check(devolverButton(Integer.parseInt(aux[2]))
									.getId());
							devolverButton(Integer.parseInt(aux[2]))
									.setTextColor(Color.GREEN);
						}

						else {

							if (checkAnswer(aux, seleccion[contadorResultados])) {

								group.check(devolverButton(
										seleccion[contadorResultados]).getId());
								devolverButton(seleccion[contadorResultados])
										.setTextColor(Color.GREEN);

							} else {

								group.check(devolverButton(
										seleccion[contadorResultados]).getId());

								devolverButton(seleccion[contadorResultados])
										.setTextColor(Color.RED);

								devolverButton(Integer.parseInt(aux[2]))
										.setTextColor(Color.GREEN);

							}

						}

					}
					atras.setVisibility(View.VISIBLE);

				}

			}

		});

		/* mismo funcionamiento que el boton siguiente */
		atras.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				corregir.setVisibility(View.INVISIBLE);

				if (!resultadoCorregido) {
					contadorPreguntas--;
					if (contadorSelecion > 0)
						contadorSelecion--;

					if (contadorPreguntas == 0) {
						atras.setVisibility(View.INVISIBLE);
						siguiente.setVisibility(View.VISIBLE);
						
						
						

					}

					if (contadorPreguntas > -1) {
						
						if (seleccion[contadorPreguntas] != -1 )
						group.check(devolverButton(seleccion[contadorPreguntas]).getId());

						DisplayQuestion(preguntas.get(contadorPreguntas));
						siguiente.setVisibility(View.VISIBLE);

					}

				} else {

					contadorResultados--;
					if (contadorResultados == 0) {
						atras.setVisibility(View.INVISIBLE);
						siguiente.setVisibility(View.VISIBLE);
					}

					if (contadorResultados > -1) {

						String[] aux_p = preguntas.get(contadorResultados + 1);
						devolverButton(Integer.parseInt(aux_p[2]))
								.setTextColor(Color.GRAY);

						if (seleccion[contadorResultados + 1] != -1)
							devolverButton(seleccion[contadorResultados + 1])
									.setTextColor(Color.GRAY);

						DisplayQuestion(preguntas.get(contadorResultados));
						siguiente.setVisibility(View.VISIBLE);
						group.clearCheck();
						String[] aux = preguntas.get(contadorResultados);
						desactivar();

						if (seleccion[contadorResultados] == -1) {

							group.check(devolverButton(Integer.parseInt(aux[2]))
									.getId());
							devolverButton(Integer.parseInt(aux[2]))
									.setTextColor(Color.GREEN);
						}

						else {

							if (checkAnswer(aux, seleccion[contadorResultados])) {

								group.check(devolverButton(
										seleccion[contadorResultados]).getId());
								devolverButton(seleccion[contadorResultados])
										.setTextColor(Color.GREEN);

							} else {
								group.check(devolverButton(
										seleccion[contadorResultados]).getId());

								devolverButton(seleccion[contadorResultados])
										.setTextColor(Color.RED);

								devolverButton(Integer.parseInt(aux[2]))
										.setTextColor(Color.GREEN);

							}
						}

					}

				}
			}
		});

		corregir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				resultadoCorregido = true;

				for (int i = 0; i < 10; i++) {
					String[] aux = preguntas.get(i);
					if (seleccion[i] != -1)
						if (checkAnswer(aux, seleccion[i]))
							score++;

				}
				

				/* Este metodo sirve para guardar las puntuaciones, pero poniendolo aqui no está actualizando la puntuacion en la BD*/
				updateScores(bundle);
				
				launchScoreDialog();
			}
		});
	}
	
	private void mostrarCorrecion(){
		
		DisplayQuestion(preguntas.get(contadorResultados));
		String[] aux = preguntas.get(contadorResultados);
		desactivar();

		if (seleccion[contadorResultados] == -1) {

			group.check(devolverButton(Integer.parseInt(aux[2]))
					.getId());
			devolverButton(Integer.parseInt(aux[2])).setTextColor(
					Color.GREEN);
		}

		else {

			if (checkAnswer(aux, seleccion[contadorResultados])) {
				group.clearCheck();

				group.check(devolverButton(
						seleccion[contadorResultados]).getId());
				devolverButton(seleccion[contadorResultados])
						.setTextColor(Color.GREEN);

			} else {
				group.check(devolverButton(
						seleccion[contadorResultados]).getId());

				devolverButton(seleccion[contadorResultados])
						.setTextColor(Color.RED);

				devolverButton(Integer.parseInt(aux[2])).setTextColor(
						Color.GREEN);

			}
		}

		siguiente.setVisibility(View.VISIBLE);
		atras.setVisibility(View.INVISIBLE);
		corregir.setVisibility(View.INVISIBLE);
		
	}

	/**
	 * Disable all RadioButton
	 */
	private void desactivar() {
		group.setEnabled(false);
		resp1.setEnabled(false);
		resp2.setEnabled(false);
		resp3.setEnabled(false);
		resp4.setEnabled(false);
	}

	/**
	 * Returns a RadioButton object with an id
	 * 
	 * @param id
	 * @return RadioButton
	 */
	private RadioButton devolverButton(int id) {
		RadioButton identificador = null;
		switch (id) {
		case 1:
			identificador = resp1;
			break;

		case 2:
			identificador = resp2;
			break;
		case 3:
			identificador = resp3;
			break;
		case 4:
			identificador = resp4;
			break;

		}
		return identificador;

	}

	/**
	 * Check if the answer number is equal than radio button selected number
	 * 
	 * @param answer
	 * @param optionNumber
	 * @return boolean
	 */
	private boolean checkAnswer(String[] answer, int optionNumber) {
		boolean value = false;
		if (Integer.parseInt(answer[2]) == optionNumber) {
			// score++;
			value = true;
		}
		return value;
	}

	/**
	 * Launch a Dialog windows with test score
	 */
	protected void launchScoreDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Has obtenido un " + score)
				.setTitle("Test finalizado")
				.setCancelable(false)
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//onClickFinally(null);
								mostrarCorrecion();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Returns to MainActivity
	 * 
	 * @param view
	 */
	public void onClickFinally(View view) {
		startActivity(new Intent(this, MainActivity.class));
	}
}
