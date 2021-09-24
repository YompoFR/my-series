package com.example.seriesorganizer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ExpandableListAdapter(
        var context: Context,
        var serieId: Int,
        var header: MutableList<String>,
        var body: MutableList<MutableList<CheckBox>>
) : BaseExpandableListAdapter() {

    var manageDatabase = ManageDatabase(context)
    var groups = header
    var childs = body

    class ViewHolder() {
        lateinit var cb: CheckBox
    }

    override fun getGroup(groupPosition: Int): String = header[groupPosition]

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(
            groupPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup?
    ): View? {
        var convertView = convertView

        if (convertView == null) { // If view is not empty, we fill layout_group
            val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_group, null)
        }
        // Instantiate title and give it appropriate value
        val title = convertView?.findViewById<TextView>(R.id.tvTitle)
        title?.text = getGroup(groupPosition)

        if (isExpanded){
            val toastText = context.getString(R.string.toast_number_of_chapters) + " "
            var numberChapters = manageDatabase.showSeasonsChapters(serieId, groupPosition + 1)
            Toast.makeText(context, "$toastText  $numberChapters", Toast.LENGTH_SHORT).show()
        }

        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int = body[groupPosition].size

    override fun getChild(groupPosition: Int, childPosition: Int): CheckBox {
        if (childs != null && childs.size > groupPosition && childs[groupPosition] != null) {
            if (childs[groupPosition].size > childPosition)
                return childs[groupPosition][childPosition]
        }
        return childs[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildView(
            groupPosition: Int,
            childPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup?
    ): View? {

        var view: View
        if (convertView == null) { // If view is not empty, we fill layout_child

            // Inflate view and layout_child
            val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_child, null)

            // Obtain checkbox from view and create a viewHolder class which will hold this checkbox
            var childHolder: ViewHolder = ViewHolder()
            childHolder.cb = view.findViewById(R.id.checkbox1)

            // Set listener
            childHolder.cb.setOnCheckedChangeListener() { button: CompoundButton, b: Boolean ->

                // Create an checkbox type item and obtain object from childholder
                var item: CheckBox = childHolder.cb.tag as CheckBox

                // Set item as selected if checkbox is checked
                item.isSelected = button.isChecked

                // Save data in database and update with actual state
                manageDatabase.readCheckBoxState(serieId, item.id, groupPosition, childPosition, item.isSelected.toString())

            }

            // Obtain object from childHolder, which in this case is a checkbox
            view.tag = childHolder

            // Assign appropriate object actual position to childholder
            childHolder.cb.tag = childs[groupPosition][childPosition]

        } else {
            view = convertView
            (view.tag as ViewHolder).cb.tag = childs[groupPosition][childPosition]
        }

        // Create a holder which it going to obtain the view's object
        val holder = view.tag as ViewHolder

        // Set checkbox is checked when located position is selected
        holder.cb.isChecked = childs[groupPosition][childPosition]
                .isSelected

        // Set checkbox text in located position
        holder.cb.text = childs[groupPosition][childPosition].text

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = groupPosition.toLong()

    override fun getGroupCount(): Int = header.size


}