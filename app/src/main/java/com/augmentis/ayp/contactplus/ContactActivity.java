package com.augmentis.ayp.contactplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.augmentis.ayp.contactplus.Model.Contact;

import java.util.UUID;


public class ContactActivity extends SingleFragmentActivity implements ContactFragment.Callbacks{
    private UUID contactId;

    private static final String CONTACT_ID = "ContactActivity.CONTACT_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Fragment onCreateFragment() {
        contactId = (UUID) getIntent().getSerializableExtra(CONTACT_ID);
        return ContactFragment.newInstance(contactId);
    }

    public static Intent newIntent(Context activity, UUID id) {
        Intent intent = new Intent(activity, ContactActivity.class);
        intent.putExtra(CONTACT_ID, id);
        return intent;
    }


    @Override
    public void onContactUpdated(Contact contact) {

    }

    @Override
    public void onContactDelete() {
        finish();
    }
}
