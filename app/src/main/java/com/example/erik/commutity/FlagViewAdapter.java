package com.example.erik.commutity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An adapter for transforming flag data into a grid view
 */
public class FlagViewAdapter extends ArrayAdapter<FlagButton> {
    private Context currentContext;
    private int resource;
    private ArrayList<FlagButton> flags = new ArrayList();

    /**
     * Constructor for the adapter
     *
     * @param currentContext The current context
     * @param resource       The resource ID for a layout file containing a TextView to use when instantiating views.
     * @param flags          Data containing the flag buttons
     */
    public FlagViewAdapter(Context currentContext, int resource, ArrayList<FlagButton> flags) {
        super(currentContext, resource);
        this.currentContext = currentContext;
        this.resource = resource;
        this.flags = flags;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) currentContext).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new RecordHolder();

            //  TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
            // ImageView imageViewIte = (ImageView) row.findViewById(R.id.imageView);


        }else{
            holder = (RecordHolder) row.getTag();
        }

        FlagButton flagButton = flags.get(position);
        holder.txtTitle.setText(flagButton.getDe);
        holder.imageItem.setImageBitmap(item.getImage());
        return row;
    }
*/

        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(currentContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(flags.get(position).getBackground());
        return imageView;

    }
}
