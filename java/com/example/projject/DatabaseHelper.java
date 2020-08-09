package com.example.projject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projject.DatabaseContract.*;

import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "projject.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    //public DatabaseHelper instance = new DatabaseHelper;
    private SQLiteDatabase db;
    private static final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
            CategoriesTable.TABLE_NAME + "( " +
            CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CategoriesTable.COLUMN_NAME + " TEXT " +
            ")";
    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
            UsersTable.TABLE_NAME + "( " +
            UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UsersTable.COLUMN_EMAIL + " TEXT, " +
            UsersTable.COLUMN_PASSWORD + " TEXT " +
            ")";
    private static final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
            com.example.projject.DatabaseContract.QuestionsTable.TABLE_NAME + " ( " +
            QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionsTable.COLUMN_QUESTION + " TEXT, " +
            QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
            QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
            QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
            QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
            QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
            "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
            CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
            ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*public static DatabaseHelper getInstance(Context context) {
         if (instance == null) {
             instance = new DatabaseHelper(context.getApplicationContext());
         }
         return instance;
     }*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    public boolean fillUsersTable(com.example.projject.User user)
    {
        // boolean result=true;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UsersTable.COLUMN_EMAIL, user.getEmail());
        cv.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
        long a = db.insert(UsersTable.TABLE_NAME,null, cv);
        if(a==-1)
        { return false;}
        else
        { return true;}

    }
    public boolean validateEmail(String email) {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users where email=?", new String[]{email});
        if (c.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }
    public boolean existEmail(String email) {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users where email=?", new String[]{email});
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean matchEmailPass(String email,String password){
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users where email=? and password=?", new String[]{email,password});
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    private void fillCategoriesTable() {
        com.example.projject.Category c1 = new com.example.projject.Category("Computer_Science");
        insertCategory(c1);
        com.example.projject.Category c2 = new com.example.projject.Category("General Knowledge");
        insertCategory(c2);
        com.example.projject.Category c3 = new com.example.projject.Category("Pakistan_currentAffairs");
        insertCategory(c3);
        com.example.projject.Category c4 = new com.example.projject.Category("Entertainment");
        insertCategory(c4);
        com.example.projject.Category c5 = new com.example.projject.Category("Math");
        insertCategory(c5);


    }

    private void insertCategory(com.example.projject.Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }
    private void fillQuestionsTable() {

        com.example.projject.Question q1 = new com.example.projject.Question( " Which header file can be used to define input/output function prototypes and macros in java?",
                "math.h", "stdio.h", "dos.h", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q1);
        com.example.projject.Question q46 = new com.example.projject.Question( "The term ‘Computer’ is derived from__________?",
                "Latin", "German", "French", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q46);
        com.example.projject.Question q47 = new com.example.projject.Question( " Who is the father of Internet ?\n",
                "Denis Riche", "Robert Huns", "Vint Cerf", 3,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q47);
        com.example.projject.Question q48 = new com.example.projject.Question( " WWW stands for___________?",
                "World Wide Web", "Web World Wide", "Whole Web World", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q48);
        com.example.projject.Question q49 = new com.example.projject.Question( " What type of operating system MS-DOS is?",
                "Multitasking", "Command Line Interface", "GUI", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q49);
        com.example.projject.Question q50 = new com.example.projject.Question( "Which technology is used in compact disks?",
                "Laser", "ElectroMagnetic", "Electric", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q50);
        com.example.projject.Question q51 = new com.example.projject.Question( " The computer that process both analog and digital is called____________?",
                "Hybrid", "MainFrame", "Ana_dig", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q51);
        com.example.projject.Question q52 = new com.example.projject.Question( "WAN stands for_____________?",
                "Web Area Network", "Wide Area Network", "Wide Array Net", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q52);
        com.example.projject.Question q2 = new com.example.projject.Question("Faisal Mosque was built by a/an ________ architect.",
                "American", "saudi", "turkish", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q2);
        com.example.projject.Question q3 = new com.example.projject.Question(" Pakistan is located in the",
                "Europe", "Asia", "Antarctica", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q3);
        com.example.projject.Question q4 = new com.example.projject.Question(" Which of the following option leads to the portability and security of Java?.",
                "Use of exception handling", "Bytecode is executed by JVM", "Dynamic binding between objects", 2,
                Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q4);
        com.example.projject.Question q5 = new com.example.projject.Question("Evaluate the following Java expression, if x=3, y=5, and z=10:\n" +
                "\n" +
                "++z + y - y + z + x++.",
                "24", "23", "20", 1,
                Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q5);
        com.example.projject.Question q6 = new com.example.projject.Question("Which from the following countries is not bordered by Pakistan?.",
                "Afghanistan", "saudiA", "India", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q6);
        com.example.projject.Question q7 = new com.example.projject.Question("When israel state was established?.",
                "1940", "1943", "1948", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q7);
        com.example.projject.Question q8 = new com.example.projject.Question("What is the old name of Tokyo?.",
                "surinam", "edo", "rishkek", 2,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q8);
        com.example.projject.Question q9 = new com.example.projject.Question("The shortest river (Roe River) of the world is located in______?",
                "USA", "UK", "INDIA", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q9);
        com.example.projject.Question q10 = new com.example.projject.Question("_____Airways announced to increase its flights operations for Pakistan from July 16, 2020?",
                "Singapore", "Etihad Airways", "qatar", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q10);
        com.example.projject.Question q11 = new com.example.projject.Question("Who introduced “Bakar Mandi” online mobile appliction to facilitate cattle purchase process on Eid-ul-Azha between buyer and seller?",
                "Sindh govt.", "Punjab govt.", "Kpk govt.", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q11);
        com.example.projject.Question q12 = new com.example.projject.Question("According to the UN data, what is population wise rank of Pakistan in 2020?",
                "4th", "5th", "6th", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q12);
        com.example.projject.Question q13 = new com.example.projject.Question("Pakistan has found new deposits of oil and gas in exploratory well Mamikhel South-01, located in Tal block in__________?",
                "sindh", "Punjab", "KPK", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q13);
        com.example.projject.Question q14 = new com.example.projject.Question("Which country became  permanent member of SCO Youth Council in July 2020?",
                "FRANCE", "UK", "PAKISTAN", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q14);
        com.example.projject.Question q15 = new com.example.projject.Question("Pakistan aims to generate 30% of clean energy by__?",
                "2021", "2025", "2030", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q15);
        com.example.projject.Question q16 = new com.example.projject.Question("Who is the current IG of New Railways Police?",
                "Captain (retd) Arif Nawaz Khan", "Wajid Zia", "Mushtaq Mehar", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q16);
        com.example.projject.Question q17 = new com.example.projject.Question("Which country to help Pakistan set up phytosanitary facility?",
                "CHINA", "INDIA", "CHINA", 3,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q17);
        com.example.projject.Question q18 = new com.example.projject.Question("Who becomes first female Lieutenant General of Pakistan Army?",
                "Suhai Aziz Talpur", "Nigar Johar", "Zakia Jamali", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q18);
        com.example.projject.Question q19 = new com.example.projject.Question("Which of the following app has temporary banned by PTA on 1st July, 2020?",
                "PUBG", "WECHAT", "TIKTOK", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Entertainment);
        insertQuestion(q19);
        com.example.projject.Question q20 = new com.example.projject.Question("\tWho won his second Oscar in successive years for Forrest Gump?",
                "Tom Cruise", "Tom Hanks", "kristen", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Entertainment);
        insertQuestion(q20);
        com.example.projject.Question q21 = new com.example.projject.Question("What is Goldie Hawn's real name?",
                "Tom Cruise", "Goldie Hawn", "Salvister", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Entertainment);
        insertQuestion(q21);
        com.example.projject.Question q22 = new com.example.projject.Question("What is the name of Michael Jackson’s home?",
                "Curseland", "BlessLand", "Neverland", 3,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Entertainment);
        insertQuestion(q22);
        com.example.projject.Question q23 = new com.example.projject.Question(" Who had the best-selling album of 2019 in the USA?",
                "Thankyou,Ariana grande", "Lover,Taylor Swift", "The creator,Tyler", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Entertainment);
        insertQuestion(q23);
        com.example.projject.Question q24 = new com.example.projject.Question("Which Shakespeare play is the most performed as of 2016?",
                "Hamlet", "Macbeth", " A Midsummer Night’s Dream", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Entertainment);
        insertQuestion(q24);
        com.example.projject.Question q25 = new com.example.projject.Question("Which 50s film held the record for the most number of animals in a film?",
                "Around the World in 80 Days", "Storm", "Darling", 2,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Entertainment);
        insertQuestion(q25);
        com.example.projject.Question q26 = new com.example.projject.Question("Who is famous for having said: \"A hard man is good to find\"?",
                "Angelina Jolie", "Mae west", " Taylor swift", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Entertainment);
        insertQuestion(q26);
        com.example.projject.Question q27 = new com.example.projject.Question("Who is the creator of Disney World?",
                "Bill Gates", "Stan disney", " Walt disney", 3,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Entertainment);
        insertQuestion(q27);
        com.example.projject.Question q28 = new com.example.projject.Question("In what movie did Shawn Levy direct Bonnie Hunt and Steve Martin?",
                "Hamlet", "Cheaper By The Dozen", "Real Steel", 2,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Entertainment);
        insertQuestion(q28);
        com.example.projject.Question q29 = new com.example.projject.Question("Which Shakespeare play is the most performed as of 2016?",
                "Hamlet", "Macbeth", " A Midsummer Night’s Dream", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Entertainment);
        insertQuestion(q29);

        com.example.projject.Question q39 = new com.example.projject.Question("  Which of the following tool is used to generate API documentation in HTML format from doc comments in source code?",
                "javadoc ", "javap ", "javah", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q39);
        com.example.projject.Question q40 = new com.example.projject.Question("What is the initial quantity of the ArrayList list?",
                "5", "0", "10", 3,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q40);
        com.example.projject.Question q41 = new com.example.projject.Question(" What is the default encoding for an OutputStreamWriter?",
                "UTF-8", "Default encoding of the host platform", "UTF-12", 3,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q41);
        com.example.projject.Question q42 = new com.example.projject.Question(" What is the type and value of the following expression? (Notice the integer division)\n" +
                "-4 + 1/2 + 2*-3 + 5.0",
                "int -5", "double -4.5", "double -5.0", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q42);
        com.example.projject.Question q43 = new com.example.projject.Question(" What is the sequence of major events in the life of an applet?",
                "init, start, stop, destroy", "start, init , stop , destroy", " init, start, destroy", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q43);
        com.example.projject.Question q44 = new com.example.projject.Question(" The wrapping up of data and functions into a single unit is called",
                "Encapsulation", " Abstraction", "message passing", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q44);
        /*com.example.projject.Question q45 = new com.example.projject.Question(" Which header file can be used to define input/output function prototypes and macros?",
                "math.h", "stdio.h", "dos.h", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q45);*/
        com.example.projject.Question q53 = new com.example.projject.Question( "The first computers were programmed using____Language?",
                "Assembly ", "Machine", "Source code", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q53);
        com.example.projject.Question q54 = new com.example.projject.Question( "_________ is called the father of modern digital computer?",
                "Charles Babbage", "J.H Muller", "Leibnitz", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q54);
        com.example.projject.Question q55 = new com.example.projject.Question("A computer cannot ‘boot’ if it does not have the__________?",
                "Operating System", "Hard Drive", "CPU", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q55);
        com.example.projject.Question q56 = new com.example.projject.Question("Which of the following is first generation of computer?",
                "UNIVAC", " IBM-1401", "ICL_2900", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q56);
        com.example.projject.Question q57 = new com.example.projject.Question("Who designed the first electronics computer – ENIAC?",
                "Van-Neumann", "J. Presper Eckert and John W Mauchly", "Joseph M. Jacquard", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q57);
        com.example.projject.Question q58 = new com.example.projject.Question("A fault in a computer program which prevents it from working correctly is known as?",
                "Bug", "Biff", "Boot", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q58);
        com.example.projject.Question q59 = new com.example.projject.Question("If you want to keep track of different editions of a document which features will you use?",
                "Versions", "Editions", "Track Change", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q59);
        com.example.projject.Question q60 = new com.example.projject.Question("The item of documentation added to the description of the new system is",
                "Feedback", " Problem Overview", "I/O Analysis", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q60);
        com.example.projject.Question q61 = new com.example.projject.Question("System implementation phase entails",
                "Pilot Run", " Parallel Run", "System Checkouts", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q61);
        com.example.projject.Question q62 = new com.example.projject.Question(" AI systems that use approximate reasoning to process ambiguous data, is",
                "Fuzzy Logic", " Abstraction", "Neural Network", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q62);
        com.example.projject.Question q63 = new com.example.projject.Question("Representation of data structure in memory is known as:",
                "Abstract data type", " Recursive", "File Structure", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q63);
        com.example.projject.Question q64 = new com.example.projject.Question("If S is an array of 80 characters, then the value assigned to S through the statement scanf(\"%s\",S) with input 12345 would be",
                "12345", " nothing since 12345 is an integer", "s is an illegal name", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q64);
        com.example.projject.Question q65 = new com.example.projject.Question(" Minimun number of comparison required to compute the largest and second largest element in array is",
                "n-[log₂n]-2", "n+[log₂n-2]", "log₂n", 2,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q65);
        com.example.projject.Question q66 = new com.example.projject.Question(" The minmum number of inter changes needed to convert the array 89,19,40,17,12,10,2,5,7,11,6,9,70 into a heap with maximum element at the root is",
                "4", " 1", "2", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.COMPUTER_SCIENCE);
        insertQuestion(q66);
        com.example.projject.Question q67 = new com.example.projject.Question(" Nuclear reaction occurs on sun",
                "Fusion", "Fission", "Both", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q67);
        com.example.projject.Question q68 = new com.example.projject.Question(" Which flower is national flower of Pakistan",
                "Rose", "Jasmine", "Sun Flower", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q68);
        com.example.projject.Question q69 = new com.example.projject.Question("In the composition of the earth aluminum is ",
                "27.5%", "8.1%", "80%", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q69);
        com.example.projject.Question q70 = new com.example.projject.Question(" After many years Cinema houses were opened in Saudi Arabia",
                "12", "23", "35", 3,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q70);
        com.example.projject.Question q71 = new com.example.projject.Question(" When was the Famous children’s television show \"Ainak Wala Jinn\" broadcasted by PTV",
                "1992", "1993", "1990", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q71);
        com.example.projject.Question q72 = new com.example.projject.Question("World Polio Day observed every year on___________",
                "24 Oct", "24 Sept", "24 Dec", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q72);
        com.example.projject.Question q73 = new com.example.projject.Question("The Smallest Continent (by Area) of the World is __________.",
                "Africa", "Australlia", "Europe", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q73);
        com.example.projject.Question q74 = new com.example.projject.Question(" The International Court of Justice is located in _________",
                "Paris", "New York", "The Hauge", 3,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q74);
        com.example.projject.Question q75 = new com.example.projject.Question(" Which is the capital of Angola",
                "Manama", "Luanda", "Baku", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q75);
        com.example.projject.Question q76 = new com.example.projject.Question("Which country from the following is NOT the member of UNO.",
                "Vetican City", "saudiA", "vietnam", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q76);
        com.example.projject.Question q77 = new com.example.projject.Question("Which from the following countries is not bordered by Pakistan?.",
                "Afghanistan", "saudiA", "India", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q77);
        com.example.projject.Question q78 = new com.example.projject.Question("There are ____ non-permanent members of the security council..",
                "15", "10", "5", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q78);
        com.example.projject.Question q79 = new com.example.projject.Question("There are _________ non-permanent members of the security council..",
                "General Assemble", "Security Council", "Justice Court", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q79);
        com.example.projject.Question q80 = new com.example.projject.Question("Which country is called 'Land of thousand islands'.",
                "Maldives", "Phillipines", "Indonesia", 3,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q80);
        com.example.projject.Question q81 = new com.example.projject.Question("World's famous bridge \"Golden Gate\" is in __________..",
                "San Fransisco", "New york", "India", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q81);
        com.example.projject.Question q82 = new com.example.projject.Question("The smallest Sea of the World is __________.",
                "Dead Sea", "Baltic Sea", "Red Sea", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q82);
        com.example.projject.Question q83 = new com.example.projject.Question("The biggest Island of the World is _________..",
                "GreenLand", "England", "Iceland", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q83);
        com.example.projject.Question q84 = new com.example.projject.Question("First paper to use the title “Quaid-e-Azam” was ______________.",
                "pioneer", "comerade", "Dawn", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q84);
        com.example.projject.Question q85 = new com.example.projject.Question("Fuzzy logic is a part of___?.",
                "computer science", "machinery", "physics", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q85);
        com.example.projject.Question q86 = new com.example.projject.Question("Periodontics deals with _____________.",
                "Ligaments Restoration", "Physics", "Dentistry", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q86);
        com.example.projject.Question q87 = new com.example.projject.Question("NEQS refer to ____________.",
                "Environment", "Sea Bed", "Continent", 2,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q87);
        com.example.projject.Question q88 = new com.example.projject.Question("The fastest marine animal found in warmer parts of five oceans of the world ___?",
                "sailfish", "tunny", "indus", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q88);
        com.example.projject.Question q89 = new com.example.projject.Question("Name the World’s Largest cruise ship is ____ of seas",
                "Harmony", "Allure", "Symphony", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q89);
        com.example.projject.Question q90 = new com.example.projject.Question("The richest fishing ground in the world is ______",
                "Northern western Africa", "Northern eastern Asia", "Northern Eastern Asia", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.GENERAL_KNOWLEDGE);
        insertQuestion(q90);
        com.example.projject.Question q91 = new com.example.projject.Question("Mar 14, 2018: The Provincial Govt that approved 'access to clean energy' project to tap energy potential is",
                "Sindh govt.", "Punjab govt.", "Kpk govt.", 3,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q91);
        com.example.projject.Question q92 = new com.example.projject.Question("In March 2018, which provincial assembly approved 'Sikh Marriage Act'",
                "Sindh govt.", "Punjab govt.", "Kpk govt.", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q92);
        com.example.projject.Question q93 = new com.example.projject.Question("On 16th March, Pakistan's first Transplant Immunology Laboratory inaugurated at\n",
                "SIUT karachi.", "Jinnah hosp.", "Agha khan hosp", 1,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q93);
        com.example.projject.Question q94 = new com.example.projject.Question("How many Pakistanis released from UAE jails in July 2020 ?",
                "400.", "1200", "600", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q94);
        com.example.projject.Question q95 = new com.example.projject.Question("Pakistan’s __________ government has banned over 100 textbooks for containing objectionable content ?",
                "Sindh govt.", "Punjab govt.", "Kpk govt.", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q95);
        com.example.projject.Question q96 = new com.example.projject.Question("Who introduced “Bakar Mandi” online mobile appliction to facilitate cattle purchase process on Eid-ul-Azha between buyer and seller?",
                "Sindh govt.", "Punjab govt.", "Kpk govt.", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q96);
        com.example.projject.Question q97 = new com.example.projject.Question("The Islamabad High Court (IHC) on 23-July-2020 ordered sealing of the Navy Sailing Club on the ________?",
                "swaik lake.", "Rawal Lake", "Kalar kahar lake.", 2,
                com.example.projject.Question.DIFFICULTY_EASY, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q97);
        com.example.projject.Question q98 = new com.example.projject.Question("What is the rank of Pakistan, according to the NTI (Nuclear Threat Initiative) nuclear security index 2020?",
                "18", "19", "20", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q98);
        com.example.projject.Question q99 = new com.example.projject.Question("Who is current Executive Director of National Institute of Health Islamabad?",
                "Major Gen Aamar Akram", "Hamid Mir", "Mubashir luqman", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q99);
        com.example.projject.Question q100 = new com.example.projject.Question("Mention the name of the Javelin thrower who has become the first Pakistani to directly qualify for Tokyo Olympics in 2021?",
                "Nawaz Khan", "Arshad Nadeem", "Afnan Faheem", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q100);
        com.example.projject.Question q101 = new com.example.projject.Question("Name the Pakistan born scientist who became the first international female vice president of the biology and medicine section at Germany’s prestigious Max Planck Society.",
                "Laila Akhter", "Arifa Akhter", "Asifa Akhter", 3,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q101);
        com.example.projject.Question q102 = new com.example.projject.Question("Who was behind the Karachi Stock Exchange attack on 29th June 2020?",
                "Balochistan Liberation Army", "Tehrik E Taliban Pakistan", "RAW", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q102);
        com.example.projject.Question q103 = new com.example.projject.Question("The Supreme Court has quashed a presidential reference filed against ____ on 19 June 2020?",
                "Justice Shoaib Muhammadi", "Justice Qazi Faiz Esa", "Justice Naveed", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q103);
        com.example.projject.Question q104 = new com.example.projject.Question("The defence budget has been set at Rs ___ trillion in the Federal Budget 2020-2021?",
                "1.1", "1.3", "1.8", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q104);
        com.example.projject.Question q105 = new com.example.projject.Question("Who has been appointed as Pakistan’s first special representative for Afghanistan?",
                "Captain (retd) Arif Nawaz Khan", "Muhammad Sadiq", "Ayesha Farooq", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q105);
        com.example.projject.Question q106 = new com.example.projject.Question("Name Pakistan’s first Sikh female journalist who has been selected as one of the 100 most influential Sikh personalities under 30 in the world.",
                "Manmeet Kaur", "Mehpreet Kaur", " Maha geet", 1,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q106);
        com.example.projject.Question q107= new com.example.projject.Question("British Pakistani___________has become the first Hijab wearing deputy district judge in the UK.",
                "saba", "Raffia Arshad", "Kainat T", 2,
                com.example.projject.Question.DIFFICULTY_MEDIUM, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q107);
        com.example.projject.Question q108 = new com.example.projject.Question("Which two countries prevented Pakistan’s attempt to set up an informal group of Organisation of Islamic Cooperation (OIC) envoys at the United Nations on Islamophobia?",
                "France and UK", "UK and USA", "UAE and Maldives", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q108);
        com.example.projject.Question q109 = new com.example.projject.Question("Name the Fourteen years old blind British-Pakistani girl who became an international sensation by auditioning at the reality talent show Britain’s Got Talent.",
                "Sarah Khan", "Aiman Ali", "Sirine Jahangir", 3,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q109);
        com.example.projject.Question q110 = new com.example.projject.Question("The Governor of Sindh promulgated the Sindh Epidemic Diseases (Amendment) Ordinance 2020 on_____",
                "May 13", "Apr 13", "Jan 13", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q110);
        com.example.projject.Question q111 = new com.example.projject.Question("Who has been Appointed as Chairman of Kashmir Committee in Pakistan?",
                " Shehryar khan Afridi", " Amjid Ali Khan Niazi", "Fakhar Imam", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q111);
        com.example.projject.Question q112 = new com.example.projject.Question("Pakistan's internet inclusiveness index 2020 is ______",
                "76", "86", "66", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q112);
        com.example.projject.Question q113= new com.example.projject.Question("Which Pakistani wrestler won Gold medal at ANOC world Beach Wrestling Championship at Doha",
                "Usman Akhter", "Muhammad Inam Butt", "Rehan Khan", 2,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q113);
        com.example.projject.Question q114 = new com.example.projject.Question("When NA passed Resolution against Imran Khan, Sheikh Rashid",
                "Jan 18", "Aug 1", "Apr 11", 1,
                com.example.projject.Question.DIFFICULTY_HARD, com.example.projject.Category.Pakistan_currentAffairs);
        insertQuestion(q114);






    }

    private void insertQuestion(com.example.projject.Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<com.example.projject.Category> getAllCategories() {
        List<com.example.projject.Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                com.example.projject.Category category = new com.example.projject.Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }
    public ArrayList<com.example.projject.Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<com.example.projject.Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                com.example.projject.Question question = new com.example.projject.Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}