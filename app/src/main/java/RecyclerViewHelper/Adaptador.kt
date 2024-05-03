package RecyclerViewHelper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fernando.orantes.crudorantes2a.R
import modelo.DataClassProductos

class Adaptador(private val Datos: List<DataClassProductos>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.itemcard, parent, )
    return ViewHolder(vista)    }

override fun getItemCount() = Datos.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val producto = Datos[position]
        holder.textView.text = producto.nombreProductos
    }
}