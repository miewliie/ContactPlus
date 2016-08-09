package com.augmentis.ayp.contactplus.Model;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.augmentis.ayp.contactplus.Model.ContactDbSchema.ContactTable;

import java.util.UUID;

/**
 * Created by Apinya on 8/9/2016.
 */
public class ContactsCursorWrapper extends CursorWrapper {

    public ContactsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact(){

        String uuidString = getString(getColumnIndex(ContactTable.Cols.UUID));// get String from this column index
        String name = getString(getColumnIndex(ContactTable.Cols.NAME));
        String tel = getString(getColumnIndex(ContactTable.Cols.TEL));
        String email = getString(getColumnIndex(ContactTable.Cols.EMAIL));

        Contact contact = new Contact(UUID.fromString(uuidString));//new crime to get id, title, date, solved, suspect
        contact.setName(name);
        contact.setTel(tel);
        contact.setEmail(email);

        return contact;
    }
}
