package com.augmentis.ayp.contactplus;

import android.content.Intent;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.augmentis.ayp.contactplus.Model.Contact;
import com.augmentis.ayp.contactplus.Model.ContactLab;

import java.util.UUID;

public class ContactFragment extends Fragment {

    private static final String CONTACT_ID = "ContactFragment.CONTACT_ID";
    private static final int REQUEST_DETAIL = 211;
    private static final java.lang.String DIALOG_DETAIL = "ContactFragment.DELDIALOG";
    private Contact contact;
    private EditText editName;
    private EditText editTel;
    private EditText editEmail;
    private Button buttonDel;
    private ImageView imageView;
    private Button buttonPic;

    public ContactFragment() {
    }

    public static ContactFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_ID, crimeId);

        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactLab contactLab = ContactLab.getInstance(getActivity());

        if (getArguments().get(CONTACT_ID) != null) {
            UUID contact_id = (UUID) getArguments().getSerializable(CONTACT_ID);
            contact = ContactLab.getInstance(getActivity()).getContactByID(contact_id);

        } else {
            Contact contact = new Contact();
            contactLab.addContact(contact);
            this.contact = contact;
        }
    }


    public void updateContact(){
        ContactLab.getInstance(getActivity()).updateContact(contact);// update crime in db

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contact_fragment, container, false);

        editName = (EditText) view.findViewById(R.id.contact_name);
//        editName.setText(contact.getName());
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                contact.setName(editName.getText().toString());
                updateContact();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTel = (EditText) view.findViewById(R.id.contact_tel);
//        editTel.setText(contact.getTel());
        editTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setTel(editTel.getText().toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editEmail = (EditText) view.findViewById(R.id.contact_email);
//        editEmail.setText(contact.getEmail());
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setEmail(editEmail.getText().toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonDel = (Button) view.findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DelDialog delDialog = DelDialog.newInstance(getActivity(), contact.getId());
                delDialog.setTargetFragment(ContactFragment.this, REQUEST_DETAIL);
                delDialog.show(fm, DIALOG_DETAIL);

            }



        });

        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////
            }
        });

        buttonPic = (Button) view.findViewById(R.id.buttonPic);
        buttonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //////////////
            }
        });

        updateContact();
        return view;
    }
}
