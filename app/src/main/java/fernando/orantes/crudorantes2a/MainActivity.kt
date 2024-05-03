package fernando.orantes.crudorantes2a

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.DataClassProductos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1-Mandar a llamar a todos los elementos de la pantalla
        val txtNombre =findViewById<EditText>(R.id.txtNombre)
        val txtPrecio =findViewById<EditText>(R.id.txtPrecio)
        val txtCantidad =findViewById<EditText>(R.id.txtCantidad)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
         GlobalScope.launch(Dispatchers.IO){


             //Guardar datos
                 //1-Creo un objeto de la clase conexion
             val claseC = ClaseConexion().cadenaConexion()

             //2 Creo una variable que contenga un PreparedStatment
             val addProducto = claseC?.prepareStatement( "insert into tbProductos(nombreProductos, precio, cantidad) values(?,?,?)")!!
             addProducto.setString(1, txtNombre.text.toString())
             addProducto.setInt(2, txtPrecio.text.toString().toInt())
             addProducto.setInt(3, txtCantidad.text.toString().toInt())
             addProducto.executeUpdate()
         }
        }

        ////////////////////////Mostrar////////////////////
        val rcvProductos = findViewById<RecyclerView>(R.id.rcvProductos)

        //asignar um loyout
        rcvProductos.layoutManager = LinearLayoutManager(this)


        //Funcion para obtener datos
        fun obetenerDatos(): List<DataClassProductos>{
            val objConexion = ClaseConexion().cadenaConexion()

            val statment = objConexion?.createStatement()
            val resulset = statment?.executeQuery( "select * from tbProductos")!!
            val productos = mutableListOf<DataClassProductos>()
            while (resulset.next()){
                val nombre = resulset.getString("nombreProducto")
                val producto = DataClassProductos(nombre)
                productos.add(producto)
            }
            return productos

        }
            CoroutineScope(Dispatchers.IO).launch {
            val productosDB = obetenerDatos()
                withContext(Dispatchers.Main){
                    val miAdapter = Adaptador(productosDB)
                    rcvProductos.adapter = miAdapter
                }
        }
    }
}