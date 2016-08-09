package com.augmentis.ayp.contactplus;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment onCreateFragment() {
        return new ContactListFragment();
    }
}
