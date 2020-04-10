package com.knight.oneday.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.knight.oneday.R;
import com.knight.oneday.nav.NavigationModelItem;
import com.ramotion.directselect.DSAbstractPickerBox;

public class EventTagPickerBoxJava extends DSAbstractPickerBox<NavigationModelItem.NavEventTag> {
    private TextView text;
    private ImageView icon;
    private View cellRoot;

    public EventTagPickerBoxJava(@NonNull Context context) {
        this(context, null);
    }

    public EventTagPickerBoxJava(@NonNull Context context,
                                 @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventTagPickerBoxJava(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(R.layout.event_tag_picker_box_layout, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.text = findViewById(R.id.cell_title_tv);
        this.icon = findViewById(R.id.cell_icon_iv);
        this.cellRoot = findViewById(R.id.cell_container);
    }

    @Override
    public void onSelect(NavigationModelItem.NavEventTag selectedItem, int selectedIndex) {
        this.text.setText(selectedItem.getEventTag());
        this.icon.setImageResource(selectedItem.getIcon());
    }

    @Override
    public View getCellRoot() {
        return this.cellRoot;
    }
}
