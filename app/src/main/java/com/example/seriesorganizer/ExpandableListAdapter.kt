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

        if (convertView == null) { // Si la vista no está vacía, rellenamos el layout_group
            val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.layout_group, null)
        }
        // Instanciamos el título y le damos el valor correspondiente
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
        if (convertView == null) { // Si la vista no está vacía, rellenamos el layout_child
            // Inflamos la vista y el layout_child
            val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_child, null)

            // Obtenemos el checkbox de la vista y creamos una clase ViewHolder que contendrá ese checkbox
            var childHolder: ViewHolder = ViewHolder()
            childHolder.cb = view.findViewById(R.id.checkbox1)

            // Establecemos el listener
            childHolder.cb.setOnCheckedChangeListener() { button: CompoundButton, b: Boolean ->

                // Creamos un item de tipo chechbox y obtenemos el objeto de childHolder
                var item: CheckBox = childHolder.cb.tag as CheckBox

                // Marcamos el item como seleccionado si el chechkbox está marcado
                item.isSelected = button.isChecked

                // Guardamos los datos en la base de datos y la actualizamos con el estado actual
                manageDatabase.readCheckBoxState(serieId, item.id, groupPosition, childPosition, item.isSelected.toString())

            }

            // Obtenemos el objeto de childHolder, que en este caso es un checkbox
            view.tag = childHolder

            // Le asignamos a childholder el objeto que corresponde a la posición en la que se encuentra
            childHolder.cb.tag = childs[groupPosition][childPosition]

        } else {
            view = convertView
            (view.tag as ViewHolder).cb.tag = childs[groupPosition][childPosition]
        }

        // Creamos un contenedor que va a obtener el objeto de la vista
        val holder = view.tag as ViewHolder

        // Establecemos que el checkbox esté marcado si la posición en la que se encuentra está seleccionado
        holder.cb.isChecked = childs[groupPosition][childPosition]
                .isSelected

        // Establecemos el texto del checkbox en la posición que se encuentra
        holder.cb.text = childs[groupPosition][childPosition].text

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = groupPosition.toLong()

    override fun getGroupCount(): Int = header.size


}