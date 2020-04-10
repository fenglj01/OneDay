package com.knight.oneday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.knight.oneday.adapters.TagPickerAdapterJava
import com.knight.oneday.nav.NavigationModel
import com.knight.oneday.nav.NavigationModelItem
import com.knight.oneday.views.EventTagPickerBoxJava
import com.ramotion.directselect.DSListView
import kotlinx.android.synthetic.main.fragment_custom.*

/**
 * A simple [Fragment] subclass.
 */
class CustomFragment : Fragment() {

    private lateinit var contentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_custom, container, false)
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* val tagItems = NavigationModel.getNavTagItems()
        contentView.findViewById<DSListView<NavigationModelItem.NavEventTag>>(R.id.event_tag_picker_list)
            .setAdapter(
                TagPickerAdapterJava(
                    requireContext(),
                    R.layout.event_tag_cell_list_item_layout,
                    tagItems
                )
            )
        contentView.findViewById<FrameLayout>(R.id.content).invalidate()*/

    }


    override fun onResume() {
        super.onResume()

    }

}
