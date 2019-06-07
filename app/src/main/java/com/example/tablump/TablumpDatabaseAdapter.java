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
    public  static User getPassword=null;
    public static Post post;
    public static final int NAME_COLUMN = 1;

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE_USERS = "create table USER( ID integer primary key autoincrement,EMAIL text,USERNAME  text,PASSWORD text);";
    static final String DATABASE_CREATE_POSTS = "create table POST( ID integer primary key autoincrement,TITLE  text,DESCRIPTION text,CATEGORY text, USER text);";
    static final String DATABASE_CREATE_NOTIFICATIONS = "create table NOTIFICATION( ID integer primary key autoincrement,TYPE text, TITLE  text,USERRECEIVE text,USERMAKE text);";
    static final String DATABASE_CREATE_LIKES = "create table LIKEUSER( ID integer primary key autoincrement,TITLE text, USER  text);";
    static final String DATABASE_CREATE_COMENTARIOS = "create table COMMENTS( ID integer primary key autoincrement,TITLE text, USER  text, COMMENT text);";
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
            //Toast.makeText(context, "Usuario creado", Toast.LENGTH_LONG).show();
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
    // method to get the data of userName
    public User getUser(String userName)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("USER", null, "USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        User us = new User(cursor.getString(cursor.getColumnIndex("EMAIL")),cursor.getString(cursor.getColumnIndex("USERNAME")),cursor.getString(cursor.getColumnIndex("PASSWORD")));
        getPassword= us;
        return getPassword;
    }
    // Method to Update an Existing username password
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

    public String insertPost(String title,String description, String category, String user)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("TITLE", title);
            newValues.put("DESCRIPTION", description);
            newValues.put("CATEGORY", category);
            newValues.put("USER", user);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("POST", null, newValues);
            System.out.print(result);
            //Toast.makeText(context, "Post creado", Toast.LENGTH_LONG).show();
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
            cursor.moveToNext();
        }
        return posts;
    }


    public Post [] getPostsFromUser(String user)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "POST",
                new String[] {"ID","TITLE","DESCRIPTION","CATEGORY","USER"},
                "USER" + " LIKE ?",
                new String[] { "%" + user + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Post[] posts = new Post[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            posts[i] = new Post(cursor.getString(cursor.getColumnIndex("TITLE")), cursor.getString(cursor.getColumnIndex("DESCRIPTION")),cursor.getString(cursor.getColumnIndex("CATEGORY")),cursor.getString(cursor.getColumnIndex("USER")));
            cursor.moveToNext();
        }
        return posts;
    }

    public Post [] getPostsFromCategory(String category)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "POST",
                new String[] {"ID","TITLE","DESCRIPTION","CATEGORY","USER"},
                "CATEGORY" + " LIKE ?",
                new String[] { "%" + category + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Post[] posts = new Post[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            posts[i] = new Post(cursor.getString(cursor.getColumnIndex("TITLE")), cursor.getString(cursor.getColumnIndex("DESCRIPTION")),cursor.getString(cursor.getColumnIndex("CATEGORY")),cursor.getString(cursor.getColumnIndex("USER")));
            cursor.moveToNext();
        }
        return posts;
    }



    public Notification [] getNotificationsFromUser(String user)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "NOTIFICATION",
                new String[] {"ID","TYPE","TITLE","USERRECEIVE","USERMAKE"},
                "USERRECEIVE" + " LIKE ?",
                new String[] { "%" + user + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Notification[] notifications = new Notification[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            notifications[i] = new Notification(cursor.getString(cursor.getColumnIndex("TYPE")),cursor.getString(cursor.getColumnIndex("TITLE")), cursor.getString(cursor.getColumnIndex("USERRECEIVE")),cursor.getString(cursor.getColumnIndex("USERMAKE")));
            cursor.moveToNext();
        }
        return notifications;
    }

    public String insertNotification(String type, String title,String userReceive, String userMake)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("TYPE", type);
            newValues.put("TITLE", title);
            newValues.put("USERRECEIVE", userReceive);
            newValues.put("USERMAKE", userMake);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("NOTIFICATION", null, newValues);
            System.out.print(result);
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }

    public Like [] getLikesFromUser(String user)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "LIKEUSER",
                new String[] {"ID","TITLE","USER"},
                "USER" + " LIKE ?",
                new String[] { "%" + user + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Like[] likes = new Like[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            likes[i] = new Like(cursor.getString(cursor.getColumnIndex("TITLE")),cursor.getString(cursor.getColumnIndex("USER")));
            cursor.moveToNext();
        }
        return likes;
    }

    public Like [] getLikesFromPost(String title)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "LIKEUSER",
                new String[] {"ID","TITLE","USER"},
                "TITLE" + " LIKE ?",
                new String[] { "%" + title + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Like[] likes = new Like[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            likes[i] = new Like(cursor.getString(cursor.getColumnIndex("TITLE")),cursor.getString(cursor.getColumnIndex("USER")));
            cursor.moveToNext();
        }
        return likes;
    }

    public Boolean getLikePostUser(String title, String user)
    {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "LIKEUSER",
                new String[] {"ID","TITLE","USER"},
                "TITLE" + " LIKE ? and USER LIKE ?",
                new String[] { "%" + title + "%" , "%" + user + "%"},
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return false;
        else return true;
    }

    public String insertLike(String title,String user)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("TITLE", title);
            newValues.put("USER", user);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("LIKEUSER", null, newValues);
            System.out.print(result);
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }

    public int deleteLike(String title,String user)
    {
        String where="TITLE=? and USER=?";
        int numberOFEntriesDeleted= db.delete("LIKEUSER", where, new String[]{title,user}) ;
        //Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    // method to insert a record in Table
    public String insertComment(String title, String username,String content)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("TITLE", title);
            newValues.put("USER", username);
            newValues.put("COMMENT", content);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("COMMENTS", null, newValues);
            System.out.print(result);
            //Toast.makeText(context, "Comentario creado en" + title, Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }

    public Comment [] getCommentFromTitle(String title) {
        db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "COMMENTS",
                new String[] {"ID","TITLE","USER","COMMENT"},
                "TITLE" + " LIKE ?",
                new String[] { "%" + title + "%" },
                null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
            return null;
        cursor.moveToFirst();
        Comment[] comments = new Comment[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            comments[i] = new Comment(cursor.getString(cursor.getColumnIndex("TITLE")), cursor.getString(cursor.getColumnIndex("USER")),cursor.getString(cursor.getColumnIndex("COMMENT")));
            cursor.moveToNext();
        }
        return comments;
    }


}