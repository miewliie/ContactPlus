package com.augmentis.ayp.contactplus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.contactplus.Model.Contact;
import com.augmentis.ayp.contactplus.Model.ContactLab;

/**
 * Created by Apinya on 8/9/2016.
 */
public class ContactListFragment extends Fragment {

    private RecyclerView _RecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_fragment, container, false);

        _RecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);
        _RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
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

        switch (item.getItemId()){
            case R.id.menu_item_new_contact:


                Contact contact = new Contact();
                ContactLab.getInstance(getActivity()).addCrime(contact);
                return true;// return true is nothing to do after this Laew Na

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView _imageView;
        private TextView _textName;

        public ContactHolder(View itemView) {
            super(itemView);

            _imageView = (ImageView) itemView.findViewById(R.id.imageView);
            _textName = (TextView) itemView.findViewById(R.id.textName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
