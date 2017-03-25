package com.example.ankit.findyourfellow;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RequestAdapter extends ArrayAdapter {

    private FirebaseAuth mAuth;
    private List list = new ArrayList(); //name
    private List id = new ArrayList();

    public RequestAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add(String object, String object2)
    {
        list.add(object);
        id.add(object2);
        super.add(object);
        super.add(object2);
    }

    static class RowHolder
    {
        TextView EMAIL;
        Button ACCEPT;
        Button DECLINE;
    }

    @Override
    public int getCount()
    {
        return this.list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.list.get(position);
    } //name

    public Object getId(int position) { return this.id.get(position);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;
        row = convertView;
        RowHolder holder;

        final int currentPosition = position;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);

            holder = new RowHolder();

            holder.EMAIL = (TextView) row.findViewById(R.id.list_item_text);
            holder.ACCEPT = (Button) row.findViewById(R.id.list_item_accept);
            holder.DECLINE = (Button) row.findViewById(R.id.list_item_decline);

            holder.ACCEPT.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    mAuth = FirebaseAuth.getInstance();

                    String friendId = (String) getId(currentPosition);

                    String userId = mAuth.getCurrentUser().getUid().toString();

                    String userEmail = mAuth.getCurrentUser().getEmail().toString();

                    Firebase friendRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + friendId);

                    Firebase userRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + userId);

                    userRef.child("Friends").child(friendId).setValue((String) getItem(currentPosition));

                    friendRef.child("Friends").child(userId).setValue(userEmail);

                    userRef.child("FriendRequest").child(friendId).removeValue();
                }
            });

            holder.DECLINE.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    mAuth = FirebaseAuth.getInstance();

                    String friendId = (String) getId(currentPosition);

                    String userId = mAuth.getCurrentUser().getUid().toString();

                    Firebase userRef = new Firebase("https://findyourfellow.firebaseio.com/Users/" + userId + "/FriendRequest/");

                    userRef.child(friendId).removeValue();
                }
            });

            row.setTag(holder);
        }
        else
        {
            holder = (RowHolder) row.getTag();
        }

        String FR = (String) getItem(position);
        holder.EMAIL.setText(FR);

        return row;
    }
}
