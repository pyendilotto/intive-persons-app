package com.pieroyendilotto.testyendilotto;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {


    static class ViewHolder {

        TextView thumbnail;
        TextView large;
        TextView username;
        TextView firstname;
        TextView lastname;
        TextView email;
        ImageView icono;

    }

    private static final String TAG = "CustomAdapter";
    private static int convertViewCounter = 0;

    private ArrayList<Person> data;
    private LayoutInflater inflater = null;

    public PersonAdapter(Context c, ArrayList<Person> d) {
        Log.v(TAG, "Constructing CustomAdapter");

        this.data = d;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        Log.v(TAG, "in getCount()");
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        Log.v(TAG, "in getItem() for position " + position);
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.v(TAG, "in getItemId() for position " + position);
        return position;
    }

    @Override
    public int getViewTypeCount() {
        Log.v(TAG, "in getViewTypeCount()");
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.v(TAG, "in getItemViewType() for position " + position);
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        Log.v(TAG, "in getView for position " + position + ", convertView is "
                + ((convertView == null) ? "null" : "being recycled"));

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_person, null);

            convertViewCounter++;
            Log.v(TAG, convertViewCounter + " convertViews have been created");

            holder = new ViewHolder();

            holder.thumbnail = (TextView) convertView.findViewById(R.id.txt_thumbnail);
            holder.large = (TextView) convertView.findViewById(R.id.txt_large);
            holder.username = (TextView) convertView.findViewById(R.id.txt_username);
            holder.firstname = (TextView) convertView.findViewById(R.id.txt_firstname);
            holder.lastname = (TextView) convertView.findViewById(R.id.txt_lastname);
            holder.email = (TextView) convertView.findViewById(R.id.txt_email);
            holder.icono = (ImageView) convertView.findViewById(R.id.icono);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        // Setting all values in listview
        holder.thumbnail.setText(data.get(position).getThumbnail());
        holder.large.setText(data.get(position).getLarge());
        holder.username.setText(data.get(position).getUsername());
        holder.firstname.setText(data.get(position).getFirstname());
        holder.lastname.setText(data.get(position).getLastname());
        holder.email.setText(data.get(position).getEmail());

        //SETEO EL ICONO EN BASE A LA URL TRAIDA:
        new DownloadImageTask(holder.icono).execute(data.get(position).getThumbnail());

        holder.icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OBTENGO EL ID DEL RENGLON:
                Integer renglonId = position;

                Log.v("PERSONADAPTER","ID DEL RENGLON: "+ Integer.toString(renglonId));

                //ABRO LA SEGUNDA ACTIVIDAD:

                Context context = v.getContext();
                Intent intent = new Intent(context, Main2Activity.class);
                intent.putExtra("id", renglonId);
                context.startActivity(intent);
            }
        });

        return convertView;

    }





}




