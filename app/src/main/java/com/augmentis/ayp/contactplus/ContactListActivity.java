package com.augmentis.ayp.contactplus;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.augmentis.ayp.contactplus.Model.Contact;
import com.augmentis.ayp.contactplus.Model.ContactLab;

import java.util.List;

public class ContactListActivity extends SingleFragmentActivity implements ContactListFragment.Callbacks,
        ContactFragment.Callbacks{


    @Override
    protected Fragment onCreateFragment() {
        return new ContactListFragment();
    }


    @Override
    public void onContactUpdated(Contact contact) {

        ContactListFragment listFragment = (ContactListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        listFragment.updateUI();

    }

    @Override
    public void onContactDelete() {

        ContactListFragment listFragment = (ContactListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        ContactFragment detailFragment = (ContactFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);

        listFragment.updateUI();

        //clear
        getSupportFragmentManager()
                .beginTransaction()
                .detach(detailFragment)
                .commit();

    }

    @Override
    public void onContactSelected(Contact contact) {

        if(findViewById(R.id.detail_fragment_container) == null){
            //if it equal null it mean single pane
            Intent intent = ContactActivity.newIntent(this, contact.getId());
            startActivity(intent);
        }else {

            Fragment newDetailFragment = ContactFragment.newInstance(contact.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onOpenSelectFirst() {

        if(findViewById(R.id.detail_fragment_container) != null){
            List<Contact> contactList = ContactLab.getInstance(this).getContact();

            if(contactList != null && contactList.size() > 0){
                Contact contact = contactList.get(0);

                Fragment newDetailFragment = ContactFragment.newInstance(contact.getId());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetailFragment)
                        .commit();
            }
        }

    }
}
