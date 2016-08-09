package com.augmentis.ayp.contactplus.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import com.augmentis.ayp.contactplus.Model.ContactDbSchema.ContactTable;

/**
 * Created by Apinya on 8/9/2016.
 */
public class ContactLab {

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
    }

    private List<Contact> getContact() {
        List<Contact> contacts = new ArrayList<>();

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


    public void addCrime(Contact contact) {

        ContentValues contentValues = getContentValues(contact);

        database.insert(ContactTable.NAME, null, contentValues);
    }
}
