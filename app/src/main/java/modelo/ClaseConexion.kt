package modelo
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection?{

        try {
            val url = "jdbc:oracle:thin:@10.10.0.144:1521:xe"

            val user = "system"

            val contra = "desarrollo"


            val Connection = DriverManager.getConnection(url, user, contra)
                return Connection
        }
        catch (e: Exception){
            println("Este es el error: $e")
            return null
        }
    }
}