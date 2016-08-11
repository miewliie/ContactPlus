package com.augmentis.ayp.contactplus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.augmentis.ayp.contactplus.Model.Contact;
import com.augmentis.ayp.contactplus.Model.ContactLab;
import com.augmentis.ayp.contactplus.Model.PictureUtils;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Apinya on 8/9/2016.
 */
public class ContactListFragment extends Fragment {

    private static final String TAG = "ContactListFragment";
    private static final int REQUEST_UPDATED_CONTACT = 111;
    private static final int MY_PERMISSION_REQUEST_CALL_PHONE = 211;
    private RecyclerView _RecyclerView;
    private ContactAdapter _adapter;
    private Contact contact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_fragment, container, false);

        _RecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);
        _RecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        updateUI();
        return view;
    }

    private void updateUI() {
        ContactLab contactLab = ContactLab.getInstance(getActivity());
        List<Contact> contacts = contactLab.getContact();

        if (_adapter == null) {
            _adapter = new ContactAdapter(this, contacts);
            _RecyclerView.setAdapter(_adapter);
        } else {
            _adapter.setContacts(contactLab.getContact());
            _adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_new_contact:

                Contact contact = new Contact();
                ContactLab.getInstance(getActivity()).addContact(contact);
                Intent intent = ContactActivity.newIntent(getActivity(), contact.getId());
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView _imageViewList;
        private TextView _textNameList;

        private File file;
        Contact _contact;
        int _position;

        public ContactHolder(View itemView) {
            super(itemView);

            _imageViewList = (ImageView) itemView.findViewById(R.id.imageViewList);
            _textNameList = (TextView) itemView.findViewById(R.id.textNameList);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void bind(final Contact contact, int position) {
            _contact = contact;
            _position = position;

            _textNameList.setText(_contact.getName());
            Log.d(TAG, "DATA: " + _contact.toString());

            file = ContactLab.getInstance(getActivity()).getPhotoFile(_contact);
            Bitmap bitmap = PictureUtils.getScaledBitmap(file.getPath(), getActivity());

            _imageViewList.setImageBitmap(bitmap);
            Log.d(TAG, "A : " + _contact.getName());


        }

        @Override
        public void onClick(View view) {

            if (hasCallPermission()) {
                call(_contact);
            }
        }

        @Override
        public boolean onLongClick(View view) {

            Intent intent = ContactActivity.newIntent(getActivity(), _contact.getId());
            startActivity(intent);

            return true;
        }
    }

    private boolean hasCallPermission() {

        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    MY_PERMISSION_REQUEST_CALL_PHONE);

            return false; // checking -- wait for dialog
        }

        return true; // already has permission
    }

    private void call(Contact contact) {
        Intent i = new Intent(Intent.ACTION_CALL);
        String phone = contact.getTel();
        i.setData(Uri.parse("tel:" + phone));

        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Granted permission
                    call(_adapter.contact);

                } else {

                    // Denied permission
                    Toast.makeText(getActivity(),
                            R.string.denied_permission_to_call,
                            Toast.LENGTH_LONG)
                            .show();
                }
                return;
            }
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
        private List<Contact> _contacts;
        private Fragment _f;
        private Contact contact;

        public ContactAdapter(Fragment f, List<Contact> contacts) {
            _contacts = contacts;
            _f = f;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_contact, parent, false);

            return new ContactHolder(v);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {

            Contact contact = _contacts.get(position);
            holder.bind(contact, position);
            set_contact(position);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "Count : " + _contacts.size());
            return _contacts.size();
        }

        public void setContacts(List<Contact> contacts) {
            _contacts = contacts;
        }

        public void set_contact(int position) {
            contact = _adapter._contacts.get(position);
        }

        public Contact getContact() {
            return contact;
        }
    }

}//
