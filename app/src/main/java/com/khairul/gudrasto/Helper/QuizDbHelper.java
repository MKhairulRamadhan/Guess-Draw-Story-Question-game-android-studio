package com.khairul.gudrasto.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.khairul.gudrasto.Model.Questions;
import com.khairul.gudrasto.R;

import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GoQuiz";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;

        final String SQL_CRATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionTable.TABLE_NAME + " ( " +
                QuizContract.QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionTable.COLUMN_STORY + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_TITLE + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_IMG + " INTEGER, " +
                QuizContract.QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_ANSWER_NR + " INTEGER " +
                " ) ";

        db.execSQL(SQL_CRATE_QUESTIONS_TABLE);
        fillQuestionsTable(); //insert data to table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionTable.TABLE_NAME);
    }

    private void addQuestions(Questions questions){
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionTable.COLUMN_STORY, questions.getStory());
        cv.put(QuizContract.QuestionTable.COLUMN_TITLE, questions.getTitle());
        cv.put(QuizContract.QuestionTable.COLUMN_IMG, questions.getImg());
        cv.put(QuizContract.QuestionTable.COLUMN_QUESTION, questions.getQuestion());
        cv.put(QuizContract.QuestionTable.COLUMN_OPTION1, questions.getOption1());
        cv.put(QuizContract.QuestionTable.COLUMN_OPTION2, questions.getOption2());
        cv.put(QuizContract.QuestionTable.COLUMN_OPTION3, questions.getOption3());
        cv.put(QuizContract.QuestionTable.COLUMN_OPTION4, questions.getOption4());
        cv.put(QuizContract.QuestionTable.COLUMN_ANSWER_NR, questions.getAnswerNr());

        db.insert(QuizContract.QuestionTable.TABLE_NAME, null, cv);

    }

    private void fillQuestionsTable(){

        Questions q1 = new Questions("Spongeboob sangat senang bekerja, ia bekerja di kraby patty restorannya tuan krab, setiap hari dia memasak menggunakan spatula kesayangannya dan selesai bekerja dia pulang dengan gembira..!",
                "Spongeboob" , R.drawable.spogebob, "Dengan apa spongebob memasak..?", "kraby patty", "tuan crab", "spatula", "makanan", 3);
        addQuestions(q1);

        Questions q2 = new Questions("Doraemon adalah robot yang datang dari masa depan, dia kembali kemasa lalu untuk menolong temannya yang dalam kesulitan, temannya itu adalah nobita yang selalu menggunakan kacamata, doraemon mempunyai senjata rahasia yaitu adalah kantong ajaib yang dapat menyimpan apapun.",
                "Doraemon", R.drawable.dorae, "Apa senjata rahasia doraemon.?", "kantong ajaib", "nobita", "kacamata", "robot", 1);
        addQuestions(q2);

        Questions q4 = new Questions("Ular merupakan jenis reptil yang dapat dengan mudah ditemui di berbagai tempat. Jenis ular ada yang berbisa dan tidak berbisa. Ular berbisa biasanya membunuh mangsanya dengan bisanya tersebut. Contoh ular berbisa antara lain kobra dan welang. Sementara itu, ular yang tidak berbisa biasanya membunuh mangsa dengan melilit tubuh mangsanya tersebut. Beberapa jenis ular kerap mempunyai kebiasaan berjemur di bawah terik matahari untuk membantu mencerna makanan.",
                "Ular", R.drawable.ular , " Bagaimana cara ular berbisa membunuh mangsanya ?", "mengigit", "memukul", "berputar", "melilit", 4);
        addQuestions(q4);

        Log.d("DATA:", String.valueOf(q4.getImg()));
    }

    public ArrayList<Questions> getAllQuestions(){
        ArrayList<Questions> questionsList = new ArrayList<>();

        db = getReadableDatabase();
        String Projection[] = {
                QuizContract.QuestionTable._ID,
                QuizContract.QuestionTable.COLUMN_STORY,
                QuizContract.QuestionTable.COLUMN_TITLE,
                QuizContract.QuestionTable.COLUMN_IMG,
                QuizContract.QuestionTable.COLUMN_QUESTION,
                QuizContract.QuestionTable.COLUMN_OPTION1,
                QuizContract.QuestionTable.COLUMN_OPTION2,
                QuizContract.QuestionTable.COLUMN_OPTION3,
                QuizContract.QuestionTable.COLUMN_OPTION4,
                QuizContract.QuestionTable.COLUMN_ANSWER_NR,
        };

        Cursor data = db.query(QuizContract.QuestionTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null);

        if (data.moveToFirst()){
            do {
                Questions question = new Questions();
                question.setStory(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_STORY)));
                question.setTitle(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_TITLE)));
                question.setImg(data.getInt(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_IMG)));
                question.setQuestion(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_QUESTION)));
                question.setOption1(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION1)));
                question.setOption2(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION2)));
                question.setOption3(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION3)));
                question.setOption4(data.getString(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION4)));
                question.setAnswerNr(data.getInt(data.getColumnIndex(QuizContract.QuestionTable.COLUMN_ANSWER_NR)));

                questionsList.add(question);
            }while (data.moveToNext());

        }
        data.close();   //close cursor
        return questionsList;
    }

}
