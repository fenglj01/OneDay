package com.knight.oneday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.knight.oneday.R;
import com.knight.oneday.nav.NavigationModelItem;

import java.util.List;

public class TagPickerAdapterJava extends ArrayAdapter<NavigationModelItem.NavEventTag> {
    private List<NavigationModelItem.NavEventTag> items;
    private Context context;

    public TagPickerAdapterJava(@NonNull Context context, int resource, @NonNull List<NavigationModelItem.NavEventTag> objects) {
        super(context, resource, objects);
        this.items = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TagPickerAdapterJava.ViewHolder holder;
        if (null == convertView) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert vi != null;
            convertView = vi.inflate(R.layout.event_tag_cell_list_item_layout, parent, false);
            holder = new TagPickerAdapterJava.ViewHolder();
            holder.text = convertView.findViewById(R.id.cell_title_tv);
            holder.icon = convertView.findViewById(R.id.cell_icon_iv);
            convertView.setTag(holder);
        } else {
            holder = (TagPickerAdapterJava.ViewHolder) convertView.getTag();
        }

        if (null != holder) {
            holder.text.setText(items.get(position).getEventTag());
            holder.icon.setImageResource(items.get(position).getIcon());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
