package com.example.ankit.findyourfellow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 2017-03-22.
 */

public class ManageAdapter extends ArrayAdapter {

    private FirebaseAuth mAuth;
    private List list = new ArrayList();
    private List id = new ArrayList();
    Context c;

    public ManageAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add(String object, String object2) {
        list.add(object);
        id.add(object2);
        super.add(object);
        super.add(object2);
    }

    static class RowHolder {
        TextView NAME;
        Button DELETE;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    public Object getId(int position) {
        return this.id.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;
        row = convertView;
        ManageAdapter.RowHolder holder;

        final int currentPosition = position;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.delete_item, parent, false);

            holder = new ManageAdapter.RowHolder();

            holder.NAME = (TextView) row.findViewById(R.id.list_item_text);
            holder.DELETE = (Button) row.findViewById(R.id.list_item_delete);

            holder.DELETE.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {

                    mAuth = FirebaseAuth.getInstance();

                    String friendId = (String) getId(currentPosition);

                    String userId = mAuth.getCurrentUser().getUid();

                    Firebase friendRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + friendId + "/Friends/");

                    Firebase userRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + userId + "/Friends/");

                    userRef.child(friendId).removeValue();

                    friendRef.child(userId).removeValue();
                }
            });

            row.setTag(holder);
        }
        else
        {
            holder = (ManageAdapter.RowHolder) row.getTag();
        }

        String FR = (String) getItem(position);
        holder.NAME.setText(FR);

        return row;
    }
}