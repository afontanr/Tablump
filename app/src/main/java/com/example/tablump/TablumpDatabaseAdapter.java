package com.example.tablump;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class TablumpDatabaseAdapter {
    static final String DATABASE_NAME = "database.db";
    String ok="OK";
    static final int DATABASE_VERSION = 1;
    public  static String getPassword="";
    public static Post post;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table USER( ID integer primary key autoincrement,EMAIL text,USERNAME  text,PASSWORD text); " +
            "create table POST( ID integer primary key autoincrement,TITLE  text,DESCRIPTION text,CATEGORY text, USER text);";
    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DataBaseHelper dbHelper;
    public TablumpDatabaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Method to open the Database
    public TablumpDatabaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();        return this;
    }
    // Method to close the Database
    public void close()
    {
        db.close();
    }
    // method returns an Instance of the Database
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
    // method to insert a record in Table
    public String insertUser(String email, String username,String password)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("EMAIL", email);
            newValues.put("USERNAME", username);
            newValues.put("PASSWORD", password);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("USER", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "Usuario creado", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }
    // method to delete a Record of UserName
    public int deleteUser(String UserName)
    {
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("USER", where, new String[]{UserName}) ;
        //Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    // method to get the password  of userName
    public String getUser(String userName)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("USER", null, "USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        getPassword= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        return getPassword;
    }
    // Method to Update an Existing
    public void  updateUser(String userName,String password)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);
        String where="USERNAME = ?";
        db.update("USER",updatedValues, where, new String[]{userName});
    }

    public String insertPost(String title,String description, String category)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("TITLE", title);
            newValues.put("DESCRIPTION", description);
            newValues.put("CATEGORY", category);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("POST", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "Post creado", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }
    // method to delete a Record of UserName
    public int deletePost (String title)
    {
        String where="TITLE=?";
        int numberOFEntriesDeleted= db.delete("POST", where, new String[]{title}) ;
        //Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    // method to get the password  of userName
    public Post getPost(String title)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("POST", null, "TITLE=?", new String[]{title}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        post = new Post(title, cursor.getString(cursor.getColumnIndex("DESCRIPTION")),cursor.getString(cursor.getColumnIndex("CATEGORY")),cursor.getString(cursor.getColumnIndex("USER")));
        return post;
    }

    // Method to Update an Existing
    public void  updatePost(String title,String description, String category, String user)
    {
        //  create object of ContentValues
        ContentValues updatedValues = new ContentValues();
        // Assign values for each Column.
        updatedValues.put("TITLE", title);
        updatedValues.put("DESCRIPTION", description);
        updatedValues.put("CATEGORY", category);
        updatedValues.put("USER", user);
        String where="TITLE = ?";
        db.update("POST",updatedValues, where, new String[]{title});
    }

    // Method to get all posts with a title like the search parameter
    public Post [] searchPosts(String search)
    {

        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "POST",
                new String[] {"ID","TITLE","DESCRIPTION","CATEGORY","USER"},
                "TITLE" + " LIKE ?",
                new String[] { "%" + search + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Post[] posts = new Post[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            posts[i] = new Post(cursor.getString(cursor.getColumnIndex("TITLE")), cursor.getString(cursor.getColumnIndex("DESCRIPTION")),cursor.getString(cursor.getColumnIndex("CATEGORY")),cursor.getString(cursor.getColumnIndex("USER")));
        }
        return posts;
    }


    public Post [] getPostsFromUser(String user)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "POST",
                new String[] {"ID","TITLE","DESCRIPTION","CATEGORY","USER"},
                "USER=" + "?",
                new String[] { "%" + user + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Post[] posts = new Post[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            posts[i] = new Post(cursor.getString(cursor.getColumnIndex("TITLE")), cursor.getString(cursor.getColumnIndex("DESCRIPTION")),cursor.getString(cursor.getColumnIndex("CATEGORY")),cursor.getString(cursor.getColumnIndex("USER")));
        }
        return posts;
    }

}