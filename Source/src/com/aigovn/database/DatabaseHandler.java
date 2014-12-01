package com.aigovn.database;
import java.util.ArrayList;
import java.util.List;

import com.aigovn.item.ItemMail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "PushMailDatabase.db";
    private static SQLiteDatabase _db;
 
    private static DatabaseHandler instance;
	private static SQLiteDatabase database;
	private static final Object lockObject = new Object();

	/*---------------------------------------------------------------------------
	 * Define some information for mail table
	 */
	private static final String TABLE_MAIL = "mail";
	private static final String TAG_MAIL = "MAIL";
	
	// Common column names in table ChatHistory
	private static final String _COLUMN_ID = "_id";
	private static final String _COLUMN_MAIL_ID = "mail_id";
	private static final String _COLUMN_USER_ID = "user_id";
	private static final String _COLUMN_FROM_ADDRESS = "from_address";
	private static final String _COLUMN_TO_ADDRESS = "to_address";
	private static final String _COLUMN_CC_ADDRESS = "cc_address";
	private static final String _COLUMN_DESTINATION_TYPE = "des_type";
	private static final String _COLUMN_SUBJECT = "subject";
	private static final String _COLUMN_BODY = "body";
	private static final String _COLUMN_RECEIVE_DATE = "receive_date";
	private static final String _COLUMN_PROCESS_DATE = "process_date";
	private static final String _COLUMN_RECEIVE_TIMESTAMP = "receive_timestamp";
	private static final String _COLUMN_PROCESS_TIMESTAMP = "process_timestamp";
	private static final String _COLUMN_STATUS = "status";
	private static final String _COLUMN_TYPE = "type";
	
	private static final String CREATE_TABLE_MAIL = "CREATE TABLE " + TABLE_MAIL + "(" 
			+ _COLUMN_ID + " INTEGER PRIMARY KEY," 
			+ _COLUMN_MAIL_ID + " TEXT," 
			+ _COLUMN_USER_ID + " TEXT,"
			+ _COLUMN_FROM_ADDRESS + " TEXT,"
			+ _COLUMN_TO_ADDRESS + " TEXT,"
			+ _COLUMN_CC_ADDRESS + " TEXT,"
			+ _COLUMN_DESTINATION_TYPE + " TEXT,"
			+ _COLUMN_SUBJECT + " TEXT,"
			+ _COLUMN_BODY + " TEXT,"
			+ _COLUMN_RECEIVE_DATE + " TEXT,"
			+ _COLUMN_PROCESS_DATE + " TEXT,"
			+ _COLUMN_RECEIVE_TIMESTAMP + " INTEGER,"
			+ _COLUMN_PROCESS_TIMESTAMP + " INTEGER,"
			+ _COLUMN_STATUS + " INTEGER,"
			+ _COLUMN_TYPE + " INTEGER,"
			+ ")";
	
	private static final String INSERT_MULTIPLE_MAIL = "INSERT INTO TABLE_MAIL " + "("
			+ "COLUMN_MAIL_ID,"
			+ "_COLUMN_USER_ID,"
			+ "_COLUMN_FROM_ADDRESS,"
			+ "_COLUMN_TO_ADDRESS,"
			+ "_COLUMN_CC_ADDRESS,"
			+ "_COLUMN_DESTINATION_TYPE,"
			+ "_COLUMN_SUBJECT,"
			+ "_COLUMN_BODY,"
			+ "_COLUMN_RECEIVE_DATE,"
			+ "_COLUMN_PROCESS_DATE,"
			+ "_COLUMN_RECEIVE_TIMESTAMP,"
			+ "_COLUMN_PROCESS_TIMESTAMP,"
			+ "_COLUMN_STATUS,"
			+ "_COLUMN_TYPE,"
			+ ") "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	try{
			db.execSQL(CREATE_TABLE_MAIL);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIL);
 
        // Create tables again
        onCreate(db);
    }

    //Open database
    public void openDatabase() {
		try {
			_db = this.getWritableDatabase();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    //Close database
    public void closeDatabase() {
		try {
			_db.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /*---------------------------------------------------------------------------
	 * Implement CRUD mail
	 */
    /*
     * Adding a object mail
     * @param: mail => object mail
     */
    public long addMail(ItemMail mail) {
    	synchronized (lockObject) {
	    	openDatabase();
	    	
	        ContentValues values = new ContentValues();
	        values.put(_COLUMN_MAIL_ID, mail.getMailID());
	        values.put(_COLUMN_USER_ID, mail.getUserID());
	        values.put(_COLUMN_FROM_ADDRESS, mail.getFromAddress());
	        values.put(_COLUMN_TO_ADDRESS, mail.getToAddress());
	        values.put(_COLUMN_CC_ADDRESS, mail.getCcAddress());
	        values.put(_COLUMN_DESTINATION_TYPE, mail.getDesType());
	        values.put(_COLUMN_SUBJECT, mail.getSubject());
	        values.put(_COLUMN_BODY, mail.getBody());
	        values.put(_COLUMN_RECEIVE_DATE, mail.getReceiveDate());
	        values.put(_COLUMN_PROCESS_DATE, mail.getProcessDate());
	        values.put(_COLUMN_RECEIVE_TIMESTAMP, mail.getReceiveTimeStamp());
	        values.put(_COLUMN_PROCESS_TIMESTAMP, mail.getProcessTimeStamp());
	        values.put(_COLUMN_STATUS, mail.getStatus());
	        values.put(_COLUMN_TYPE, mail.getType());
	 
	        // Inserting Row
	        long todo_id =  _db.insert(TABLE_MAIL, null, values);
	        closeDatabase();
	        return todo_id;
    	}
    }
    
    /*
     * Adding bundle object mail
     * @param: mails => array list mail
     */
    public void addMail(ArrayList<ItemMail> mails) {
    	synchronized (lockObject) {
	    	openDatabase();
	    	_db.beginTransaction();
	
	    	SQLiteStatement stmt = _db.compileStatement(INSERT_MULTIPLE_MAIL);
	    	ItemMail _tmp;
	    	for (int i = 0; i < mails.size(); i++) {
	    		_tmp = mails.get(i);
	    	    stmt.bindString(1, String.valueOf(_tmp.getMailID()));
	    	    stmt.bindString(2, String.valueOf(_tmp.getUserID()));
	    	    stmt.bindString(3, String.valueOf(_tmp.getFromAddress()));
	    	    stmt.bindString(4, String.valueOf(_tmp.getToAddress()));
	    	    stmt.bindString(5, String.valueOf(_tmp.getCcAddress()));
	    	    stmt.bindString(6, String.valueOf(_tmp.getDesType()));
	    	    stmt.bindString(7, String.valueOf(_tmp.getSubject()));
	    	    stmt.bindString(8, String.valueOf(_tmp.getBody()));
	    	    stmt.bindString(9, String.valueOf(_tmp.getReceiveDate()));
	    	    stmt.bindString(10, String.valueOf(_tmp.getProcessDate()));
	    	    stmt.bindString(11, String.valueOf(_tmp.getReceiveTimeStamp()));
	    	    stmt.bindString(12, String.valueOf(_tmp.getProcessTimeStamp()));
	    	    stmt.bindString(13, String.valueOf(_tmp.getStatus()));
	    	    stmt.bindString(14, String.valueOf(_tmp.getType()));
	    	    stmt.execute();
	    	    stmt.clearBindings();
	    	}
	    	_db.setTransactionSuccessful();
	    	_db.endTransaction();
	    	closeDatabase();
    	}
    }
    
    /*
     * Update a mail
     * @param: mail => object mail
     */
    public int updateMail(ItemMail mail){
    	synchronized (lockObject) {
	    	openDatabase();
	    	
	        ContentValues values = new ContentValues();
	        values.put(_COLUMN_MAIL_ID, mail.getMailID());
	        values.put(_COLUMN_USER_ID, mail.getUserID());
	        values.put(_COLUMN_FROM_ADDRESS, mail.getFromAddress());
	        values.put(_COLUMN_TO_ADDRESS, mail.getToAddress());
	        values.put(_COLUMN_CC_ADDRESS, mail.getCcAddress());
	        values.put(_COLUMN_DESTINATION_TYPE, mail.getDesType());
	        values.put(_COLUMN_SUBJECT, mail.getSubject());
	        values.put(_COLUMN_BODY, mail.getBody());
	        values.put(_COLUMN_RECEIVE_DATE, mail.getReceiveDate());
	        values.put(_COLUMN_PROCESS_DATE, mail.getProcessDate());
	        values.put(_COLUMN_RECEIVE_TIMESTAMP, mail.getReceiveTimeStamp());
	        values.put(_COLUMN_PROCESS_TIMESTAMP, mail.getProcessTimeStamp());
	        values.put(_COLUMN_STATUS, mail.getStatus());
	        values.put(_COLUMN_TYPE, mail.getType());
	 
	        // Update Row
	        int count = _db.update(TABLE_MAIL, values, _COLUMN_ID + " = ?",
	                new String[] { String.valueOf(mail.get_id()) });
	        closeDatabase();
	        return count;
    	}
    }
    
    /*
     * Delete mail
     * @param: mail => object mail
     */
    public void deleteMail(ItemMail mail) {
		synchronized (lockObject) {
			openDatabase();
			_db.delete(TABLE_MAIL, _COLUMN_ID + " = ? ", 
					new String[] { String.valueOf(mail.get_id()) });
			closeDatabase();
		}
	}
    
    /*
     * Get a single mail
     * @param: id => id of mail
     */
    public ItemMail getMail(int id){
    	synchronized (lockObject) {
    		openDatabase();
    		ItemMail _item = new ItemMail();
    		Cursor cursor = _db.query(TABLE_MAIL, null, _COLUMN_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            
            _item.set_id(cursor.getInt(cursor.getColumnIndex(_COLUMN_ID)));
            _item.setMailID(cursor.getLong(cursor.getColumnIndex(_COLUMN_MAIL_ID)));
            _item.setUserID(cursor.getLong(cursor.getColumnIndex(_COLUMN_USER_ID)));
            _item.setFromAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_FROM_ADDRESS)));
            _item.setToAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_TO_ADDRESS)));
            _item.setCcAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_CC_ADDRESS)));
            _item.setDesType(cursor.getString(cursor.getColumnIndex(_COLUMN_DESTINATION_TYPE)));
            _item.setSubject(cursor.getString(cursor.getColumnIndex(_COLUMN_SUBJECT)));
            _item.setBody(cursor.getString(cursor.getColumnIndex(_COLUMN_BODY)));
            _item.setReceiveDate(cursor.getString(cursor.getColumnIndex(_COLUMN_RECEIVE_DATE)));
            _item.setProcessDate(cursor.getString(cursor.getColumnIndex(_COLUMN_PROCESS_DATE)));
            _item.setFromAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_FROM_ADDRESS)));
            _item.setReceiveTimeStamp(cursor.getString(cursor.getColumnIndex(_COLUMN_RECEIVE_DATE)));
            _item.setProcessTimeStamp(cursor.getString(cursor.getColumnIndex(_COLUMN_PROCESS_DATE)));
            _item.setStatus(cursor.getInt(cursor.getColumnIndex(_COLUMN_STATUS)));
            _item.setType(cursor.getInt(cursor.getColumnIndex(_COLUMN_TYPE)));
            
            closeDatabase();
            return _item;
    	}
    }
    
    /*
     * Get multiple mail and include paging
     * @param: pageIndex => index page
     * 		   pageSize => number record in one page
     * 		   type => type of mail (0: inbox, 1:outbox, 2:draft, 3:trash)
     */
    public List<ItemMail> getMail(int pageIndex, int pageSize, int type){
    	synchronized (lockObject) {
    		openDatabase();
    		List<ItemMail> mailList = new ArrayList<ItemMail>();
    	    String selectQuery = "SELECT  * FROM " + TABLE_MAIL 
    	    						+ " LIMIT ? " + " OFFSET ? "
    	    						+ " WHERE " + _COLUMN_TYPE + " = ?" ;
    	 
    	    Cursor cursor = null;
    	    try {
    	    	cursor = _db.rawQuery(selectQuery, new String[] { String.valueOf(pageSize), String.valueOf(pageIndex * pageSize), String.valueOf(type) });

				if (cursor != null && cursor.moveToFirst()) {
					do {
						ItemMail _item = new ItemMail();
						_item.set_id(cursor.getInt(cursor.getColumnIndex(_COLUMN_ID)));
			            _item.setMailID(cursor.getLong(cursor.getColumnIndex(_COLUMN_MAIL_ID)));
			            _item.setUserID(cursor.getLong(cursor.getColumnIndex(_COLUMN_USER_ID)));
			            _item.setFromAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_FROM_ADDRESS)));
			            _item.setToAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_TO_ADDRESS)));
			            _item.setCcAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_CC_ADDRESS)));
			            _item.setDesType(cursor.getString(cursor.getColumnIndex(_COLUMN_DESTINATION_TYPE)));
			            _item.setSubject(cursor.getString(cursor.getColumnIndex(_COLUMN_SUBJECT)));
			            _item.setBody(cursor.getString(cursor.getColumnIndex(_COLUMN_BODY)));
			            _item.setReceiveDate(cursor.getString(cursor.getColumnIndex(_COLUMN_RECEIVE_DATE)));
			            _item.setProcessDate(cursor.getString(cursor.getColumnIndex(_COLUMN_PROCESS_DATE)));
			            _item.setFromAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_FROM_ADDRESS)));
			            _item.setReceiveTimeStamp(cursor.getString(cursor.getColumnIndex(_COLUMN_RECEIVE_DATE)));
			            _item.setProcessTimeStamp(cursor.getString(cursor.getColumnIndex(_COLUMN_PROCESS_DATE)));
			            _item.setStatus(cursor.getInt(cursor.getColumnIndex(_COLUMN_STATUS)));
			            _item.setType(cursor.getInt(cursor.getColumnIndex(_COLUMN_TYPE)));
			            mailList.add(_item);
					}
					while (cursor.moveToNext());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (cursor != null) {
					cursor.close();
				}
			}
            
            closeDatabase();
            return mailList;
    	}
    }
    
    /*
     * Search mail
     * @param: keyword => keyword to search
     * 		   type => type of mail (0: inbox, 1:outbox, 2:draft, 3:trash)
     */
    public List<ItemMail> searchMail(String keyword, int type){
    	synchronized (lockObject) {
    		openDatabase();
    		List<ItemMail> mailList = new ArrayList<ItemMail>();
    	    String selectQuery = "SELECT  * FROM " + TABLE_MAIL 
    	    						+ " WHERE (" + _COLUMN_SUBJECT + " LIKE %" + keyword + "%"
    	    						+ " OR " + _COLUMN_BODY + " LIKE %" + keyword + "%)" 
    	    						+ " AND " + _COLUMN_TYPE + " = " + type ;
    	 
    	    Cursor cursor = null;
    	    try {
    	    	cursor = _db.rawQuery(selectQuery, null);

				if (cursor != null && cursor.moveToFirst()) {
					do {
						ItemMail _item = new ItemMail();
						_item.set_id(cursor.getInt(cursor.getColumnIndex(_COLUMN_ID)));
			            _item.setMailID(cursor.getLong(cursor.getColumnIndex(_COLUMN_MAIL_ID)));
			            _item.setUserID(cursor.getLong(cursor.getColumnIndex(_COLUMN_USER_ID)));
			            _item.setFromAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_FROM_ADDRESS)));
			            _item.setToAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_TO_ADDRESS)));
			            _item.setCcAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_CC_ADDRESS)));
			            _item.setDesType(cursor.getString(cursor.getColumnIndex(_COLUMN_DESTINATION_TYPE)));
			            _item.setSubject(cursor.getString(cursor.getColumnIndex(_COLUMN_SUBJECT)));
			            _item.setBody(cursor.getString(cursor.getColumnIndex(_COLUMN_BODY)));
			            _item.setReceiveDate(cursor.getString(cursor.getColumnIndex(_COLUMN_RECEIVE_DATE)));
			            _item.setProcessDate(cursor.getString(cursor.getColumnIndex(_COLUMN_PROCESS_DATE)));
			            _item.setFromAddress(cursor.getString(cursor.getColumnIndex(_COLUMN_FROM_ADDRESS)));
			            _item.setReceiveTimeStamp(cursor.getString(cursor.getColumnIndex(_COLUMN_RECEIVE_DATE)));
			            _item.setProcessTimeStamp(cursor.getString(cursor.getColumnIndex(_COLUMN_PROCESS_DATE)));
			            _item.setStatus(cursor.getInt(cursor.getColumnIndex(_COLUMN_STATUS)));
			            _item.setType(cursor.getInt(cursor.getColumnIndex(_COLUMN_TYPE)));
			            mailList.add(_item);
					}
					while (cursor.moveToNext());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (cursor != null) {
					cursor.close();
				}
			}
            
            closeDatabase();
            return mailList;
    	}
    }
    
}
