package ldm.clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBAdapter class to handle us database
 * 
 * @author M. Da Silva, Bungisa Beto
 */
public class DBAdapter {

	static final String DATABASE_NAME = "MyDB";
	static final int DATABASE_VERSION = 1;
	static final String TAG = "DBAdapter";

	/* Tabla: PREGUNTAS */
	// Titulo de la tabla
	static final String TABLE_PREGUNTAS = "preguntas";
	// Campos de la tabla
	static final String KEY_ID = "_id";
	static final String KEY_QUESTION = "question";
	static final String KEY_CORRECT_ANSWER = "correct_answer";
	static final String KEY_ANSWER1 = "answer1";
	static final String KEY_ANSWER2 = "answer2";
	static final String KEY_ANSWER3 = "answer3";
	static final String KEY_ANSWER4 = "answer4";
	static final String KEY_TAG = "tag";
	/* Array que guarda todos los campos de una fila consultada */
	private static final String[] FILA_PREGUNTAS = new String[] { KEY_ID,
			KEY_QUESTION, KEY_CORRECT_ANSWER, KEY_ANSWER1, KEY_ANSWER2,
			KEY_ANSWER3, KEY_ANSWER4, KEY_TAG };

	/* Tabla: USUARIOS */
	// Titulo de la tabla
	static final String TABLE_USUARIOS = "usuarios";
	// Campos de la tabla
	static final String KEY_ID_U = "_id";
	static final String KEY_NOMBRE_U = "nombre";
	static final String KEY_EMAIL_U = "email";
	static final String KEY_PASS_U = "pass";
	/* Tabla: USUARIOS */
	private static final String[] FILA_USUARIOS = { KEY_ID_U, KEY_NOMBRE_U,
			KEY_EMAIL_U, KEY_PASS_U };

	/* Tabla: PUNTUACIONES */
	// Titulo de tabla
	static final String TABLE_SCORES = "puntuaciones";
	private static final String[] FILA_PUNTUACIONES = new String[] { "_id",
			"usuario", "todo", "apis", "kifeCycle", "iputs", "keyboard",
			"files", "views", "list", "images", "datas" };

	/* Tabla: PREFERENCIAS */
	// Titulo de tabla
	static final String TABLE_PREFERENCES = "preferencias";
	// Campos de la tabla
	static final String KEY_ID_P = "_id";
	static final String KEY_USER_P = "usuario";
	static final String KEY_SOUND_P = "sonido";
	static final String KEY_TIME_P = "tiempo";
	static final String KEY_RPASS_P = "rememberPass";
	/* Tabla: PREFERENCIAS */
	private static final String[] FILA_PREFERENCIAS = new String[] { KEY_ID_P,
			KEY_USER_P, KEY_SOUND_P, KEY_TIME_P, KEY_RPASS_P };

	/* String para crear una base de datos de prueba por primera vez */
	static final String CREATE_PREFERENCIAS = "CREATE TABLE preferencias (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
			+ "usuario TEXT, sonido INTEGER NOT NULL DEFAULT ''1'', tiempo INTEGER NOT NULL DEFAULT ''0'', rememberPass INTEGER NOT NULL DEFAULT ''0'');";
	static final String CREATE_USUARIOS = "CREATE TABLE usuarios (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, nombre TEXT, email TEXT, pass TEXT);";
	static final String CREATE_PUNTUACIONES = "CREATE TABLE puntuaciones (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
			+ "usuario TEXT, tema1 INTEGER, tema2 INTEGER, tema3 INTEGER, tema4 INTEGER, tema5 INTEGER, tema6 INTEGER, tema7 INTEGER, tema8 INTEGER, tema9 INTEGER, tema10 INTEGER, general INTEGER);";
	static final String CREATE_PREGUNTAS = "create table preguntas (_id integer primary key autoincrement, "
			+ "question text not null, correct_answer integer not null, answer1 text not null, answer2 text not null, answer3 text not null, answer4 text not null, tag text not null);";

	static final String[] CREATE_DATABASE = { CREATE_PREFERENCIAS,
			CREATE_USUARIOS, CREATE_PUNTUACIONES, CREATE_PREGUNTAS };

	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	/**
	 * DBadapter object constructor
	 * 
	 * @param ctx
	 */
	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(ctx);
	}

	/**
	 * Static Class to create the DatabaseHelper object
	 * 
	 * @author M. Da Silva, Bungisa Beto
	 *
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			for (String sql : CREATE_DATABASE) {
				try {
					db.execSQL(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS preguntas");
			onCreate(db);
		}
	}

	/**
	 * Opens the database
	 * 
	 * @return DBAdapter
	 * @throws SQLException
	 */
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Closes the database
	 */
	public void close() {
		DBHelper.close();
	}

	/**
	 * Insert a question in the database
	 * 
	 * @param question
	 * @param correct_answer
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @param answer4
	 * @param tag
	 * @return long
	 */
	public long insertQuestion(String question, int correct_answer,
			String answer1, String answer2, String answer3, String answer4,
			String tag) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_QUESTION, question);
		initialValues.put(KEY_CORRECT_ANSWER, correct_answer);
		initialValues.put(KEY_ANSWER1, answer1);
		initialValues.put(KEY_ANSWER2, answer2);
		initialValues.put(KEY_ANSWER3, answer3);
		initialValues.put(KEY_ANSWER4, answer4);
		initialValues.put(KEY_TAG, tag);

		return db.insert(TABLE_PREGUNTAS, null, initialValues);
	}

	/**
	 * Deletes a particular question
	 * 
	 * @param rowId
	 * @return boolean
	 */
	public boolean deleteQuestion(long rowId) {
		return db.delete(TABLE_PREGUNTAS, KEY_ID + "=" + rowId, null) > 0;
	}

	/**
	 * Retrieves all questions
	 * 
	 * @return Cursor
	 */
	public Cursor getQuestions() {
		return db.query(TABLE_PREGUNTAS, FILA_PREGUNTAS, null, null, null,
				null, null);
	}

	/**
	 * Retrieve a cursor with all column order by random of nQuestions results
	 * 
	 * @param nQuestions
	 * @return Cursor
	 */
	public Cursor getQuestions(int nQuestions) {
		Log.w(TAG, "Starting query...");
		return db.rawQuery("SELECT * FROM " + TABLE_PREGUNTAS
				+ " ORDER BY RANDOM() LIMIT " + nQuestions, null);
	}

	/**
	 * Retrieve a cursor with all column, in random order where tag column value
	 * = tag input, nQuestions limit the number of results
	 * 
	 * @param nQuestions
	 * @param tag
	 * @return
	 */
	public Cursor getQuestions(int nQuestions, String tag) {
		Log.w(TAG, "Starting query...");
		if (tag.equals("todo")) {
			return db.rawQuery("SELECT * FROM " + TABLE_PREGUNTAS
					+ " ORDER BY RANDOM() LIMIT " + nQuestions, null);
		} else {
			return db.rawQuery("SELECT * FROM " + TABLE_PREGUNTAS + " WHERE "
					+ KEY_TAG + "='" + tag + "' ORDER BY RANDOM() LIMIT "
					+ nQuestions, null);
		}

	}

	/**
	 * Retrieves a particular question
	 * 
	 * @param rowId
	 * @return CUrsor
	 * @throws SQLException
	 */
	public Cursor getQuestion(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, TABLE_PREGUNTAS, FILA_PREGUNTAS, KEY_ID
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/**
	 * Updates a question
	 * 
	 * @param rowId
	 * @param question
	 * @param correct_answer
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @param answer4
	 * @param tag
	 * @return boolean
	 */
	public boolean updateQuestion(long rowId, String question,
			int correct_answer, String answer1, String answer2, String answer3,
			String answer4, String tag) {
		ContentValues args = new ContentValues();
		args.put(KEY_QUESTION, question);
		args.put(KEY_CORRECT_ANSWER, correct_answer);
		args.put(KEY_ANSWER1, answer1);
		args.put(KEY_ANSWER2, answer2);
		args.put(KEY_ANSWER3, answer3);
		args.put(KEY_ANSWER4, answer4);
		args.put(KEY_TAG, tag);
		return db.update(TABLE_PREGUNTAS, args, KEY_ID + "=" + rowId, null) > 0;
	}

	/**
	 * Retrieve all scores by user name passed
	 * 
	 * @param userName
	 * @return Cursor
	 */
	public Cursor getScores(String userName) {
		return db.rawQuery("SELECT * FROM puntuaciones WHERE usuario" + "='"
				+ userName + "'", null);
	}

	/**
	 * Retrieve all fields from user name passed
	 * 
	 * @param userName
	 * @return Cursor
	 */
	public Cursor getUser(String userName) {
		return db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE nombre"
				+ "='" + userName + "'", null);
	}

	/**
	 * Retrieve all preferences by user name passed
	 * 
	 * @param userName
	 * @return Cursor
	 */
	public Cursor getPreferences(String userName) {
		return db.rawQuery("SELECT * FROM preferencias WHERE usuario" + "='"
				+ userName + "'", null);
	}

	/**
	 * Insert a new user in the database
	 * 
	 * @param userName
	 * @param email
	 * @param pass
	 * @return long
	 */
	public long insertUser(String userName, String email, String pass) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_NOMBRE_U, userName);
		initialValues.put(KEY_EMAIL_U, email);
		initialValues.put(KEY_PASS_U, pass);

		return db.insert(TABLE_USUARIOS, null, initialValues);
	}

	/**
	 * This method is called when an user is create, then is introduced a new
	 * row in the Scores table with default scores in each tag
	 * 
	 * @param values
	 * @return long
	 */
	public long insertScores(String userName) {
		ContentValues initialValues = new ContentValues();

		initialValues.put("usuario", userName);
		initialValues.put("todo", 0);
		initialValues.put("apis", 0);
		initialValues.put("lifeCycle", 0);
		initialValues.put("inputs", 0);
		initialValues.put("keyboard", 0);
		initialValues.put("files", 0);
		initialValues.put("views", 0);
		initialValues.put("list", 0);
		initialValues.put("images", 0);
		initialValues.put("datas", 0);

		return db.insert(TABLE_SCORES, null, initialValues);
	}

	/**
	 * Update a score in the DB, this method receives an user name, the name of
	 * the tag must be
	 * -todo,apis,lifeCycle,inputs,keyboard,files,views,list,images,datas- and
	 * the score value (0-10)
	 * 
	 * @param userName
	 * @param tag
	 * @param score
	 * @return boolean
	 */
	public boolean updateScore(String userName, String tag, int score) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(tag, score);

		return db.update(TABLE_SCORES, initialValues, KEY_USER_P + "='"
				+ userName + "'", null) > 0;
	}

	/**
	 * Insert preferences Row when new user is created
	 * 
	 * @param userName
	 * @param sound
	 * @param time
	 * @param rememberPass
	 * @return long
	 */
	public long insertPreferences(String userName, int sound, int time,
			int rememberPass) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_USER_P, userName);
		initialValues.put(KEY_SOUND_P, sound);
		initialValues.put(KEY_TIME_P, time);
		initialValues.put(KEY_RPASS_P, rememberPass);

		return db.insert(TABLE_PREFERENCES, null, initialValues);
	}

	/**
	 * Update the preferences Row in the DB when the user modify any value in
	 * preferences menu. This method receives the user name, the name of the
	 * preference (sonido, tiempo, rememberPass) and the value (0 is not active,
	 * 1 is active)
	 * 
	 * @param userName
	 * @param preferenceName
	 * @param value
	 * @return boolean
	 */
	public boolean updatePreferences(String userName, String preferenceName,
			int value) {
		ContentValues initialValues = new ContentValues();

		initialValues.put(preferenceName, value);

		return db.update(TABLE_PREFERENCES, initialValues, KEY_USER_P + "='"
				+ userName + "'", null) > 0;
	}

	/**
	 * return true if the user and pass it's ok, false in another case
	 * 
	 * @param userName
	 * @param pass
	 * @return boolean
	 */
	public boolean checkPass(String userName, String pass) {
		Cursor cursor = db
				.rawQuery("SELECT * FROM " + TABLE_USUARIOS
						+ " WHERE nombre=? AND pass=?", new String[] {
						userName, pass });
		return cursor.getCount() > 0;
	}
	
	public Cursor getAllScores(){
		return db.rawQuery("SELECT * FROM " + TABLE_SCORES, null);
	}
}
