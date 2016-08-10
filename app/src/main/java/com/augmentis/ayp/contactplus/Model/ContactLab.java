package com.augmentis.ayp.contactplus.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.augmentis.ayp.contactplus.Model.ContactDbSchema.ContactTable;

/**
 * Created by Apinya on 8/9/2016.
 */
public class ContactLab {

    private static final String TAG = "DB";
    private static ContactLab instance;

    ///////////////////////////////////////////

    public static ContactLab getInstance(Context context){

        if(instance == null){
            instance = new ContactLab(context);
        }
        return instance;
    }

    public static ContentValues getContentValues(Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.Cols.UUID, contact.getId().toString());
        contentValues.put(ContactTable.Cols.NAME, contact.getName());
        contentValues.put(ContactTable.Cols.TEL, contact.getTel());
        contentValues.put(ContactTable.Cols.EMAIL, contact.getEmail());

        return contentValues;
    }

  ///////////////////////////////////////////////

    private SQLiteDatabase database;
    private Context context;


    public ContactLab(Context context) {
        this.context = context;
        ContactsBaseHelper contactsBaseHelper = new ContactsBaseHelper(context);
        database = contactsBaseHelper.getWritableDatabase();
    }

    public  Contact getContactByID(UUID uuid){
        ContactsCursorWrapper cursor = queryContacts(ContactTable.Cols.UUID
                + " = ? ", new String[] { uuid.toString()});

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getContact();
        }finally {
            cursor.close();
        }
    }

    public ContactsCursorWrapper queryContacts(String whereCause, String[] whereArgs){
        Cursor cursor = database.query(ContactTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null);

        return new ContactsCursorWrapper(cursor);
    }


    public List<Contact> getContact() {
        List<Contact> contacts = new ArrayList<>();

        ContactsCursorWrapper cursorWrapper = queryContacts(null, null);
        try {
            cursorWrapper.moveToFirst();
            while ( !cursorWrapper.isAfterLast()){ // cursor until after the last row
                contacts.add(cursorWrapper.getContact());// add crime to crimelist

                cursorWrapper.moveToNext();//move to next crime
            }
        }finally {
            cursorWrapper.close(); //move until the last then close cursor
        }

        return contacts;
    }

    public static void main(String [] args){
        ContactLab contactLab = ContactLab.getInstance(null);
        List<Contact> contactList = contactLab.getContact();
        int size = contactList.size();
        for(int i = 0; i < size; i++){
            System.out.println(contactList.get(i));

        }

    }


    public void addContact(Contact contact) {

        ContentValues contentValues = getContentValues(contact);

        database.insert(ContactTable.NAME, null, contentValues);
    }

    public void updateContact(Contact contact){
        String uuidStr = contact.getId().toString();
        ContentValues contentValues = getContentValues(contact);


        database.update(ContactTable.NAME, contentValues, ContactTable.Cols.UUID
                + " = ?", new String[] { uuidStr}); // uuidStr will manage n put in ? position (sql injection)

        Log.d(TAG, "Add to db : " + contact.getName());

    }

    public void deleteCrime(UUID contactId) {
        database.delete(ContactTable.NAME, ContactTable.Cols.UUID
                + " = ? ", new String[] {contactId.toString() });
    }
}
