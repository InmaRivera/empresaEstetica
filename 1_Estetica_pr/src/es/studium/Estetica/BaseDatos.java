package es.studium.Estetica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class BaseDatos 
{
	//conector BD
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/estetica_pr";
	String login = "administrador_estetica";
	String password = "Studium;";
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	BaseDatos(){}//Método vacío para poder crear objetos en otras clases

	public Connection conectar()//conectamos con la base de datos
	{
		try
		{
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe){}
		catch (SQLException sqle){}
		return connection;
	}
	public int consultar(String sentencia)
	{
		//Creamos la instrucción para los usuarios
		int resultado = -1;
		ResultSet rs  = null;
		try 
		{
			statement =connection.createStatement();
			rs =statement.executeQuery(sentencia);
			if(rs.next())
			{
				resultado = rs.getInt("tipoUsuario");
			}

		}
		catch (SQLException e ){}
		return(resultado);

	}
	public void desconectar ()//método para desconectar de la base de datos
	{
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException e){}
	}
	public String ConsultaPersona()
	{
		//Creamos la sentencia SQL para la consulta 
		String contenido = "";
		sentencia = "SELECT * FROM personas";
		try
		{
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido 
			// y ejecutar la sentencia SQL 
			resultSet = statement.executeQuery(sentencia);
			// Por cada departamento, meter sus datos en el Choice
			while(resultSet.next())
			{
				contenido = contenido + (resultSet.getInt("idPersona")+
						"-"+ resultSet.getString("nombrePersona")+
						"-"+ resultSet.getString("apellidosPersona")+"\n");
			}
		}
		catch (SQLException e){}
		return contenido;
	}
	public int AltaPersona(Connection connection, String sentencia)
	{
		//Creamos la instrucción para conexión de alta persona
		//Devuelve 0 para ok
		//devuelve un 1 para error
		int resultado = 1;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//y ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				resultado = 0;
			}
			else
			{
				resultado = 1;
			}
		}
		catch (SQLException sqle){}
		return (resultado);
	}
	public int BajaPersona(int idPersona)
	{
		//Aplicamos la sentencia para la baja personas
		int resultado = 0;
		//Devolver 0 --> borrado con éxito
		//Devolver 1 ---> Error
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutamos el comando borrar
			String sentencia = "DELETE FROM personas WHERE idPersona = "+ idPersona;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			//Error devuelve 1
			resultado = -1;
		}
		return (resultado);
	}
	public int ModificacionPersona(String sentencia)
	{
		//Creamos la instrucción para la modidicación persona
		//Devuelve 0 para ok
		//devuelve un 1 para error
		int resultado = 1;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			statement.executeUpdate(sentencia);
			//y ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				resultado = 0;
			}
			else
			{
				resultado = 1;
			}
		}
		catch (SQLException sqle){}
		return (resultado);
	}

	public ResultSet rellenarPersonas(Connection connection)
	{
		//Creamos la instrucción para la consulta de personas (rellenar el choice)
		ResultSet rs = null;
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery( "SELECT * FROM personas");					
		}
		catch (SQLException sqle){}
		return (rs);
	}

	public ResultSet buscarPersona(Connection connection, String sentencia)
	{
		//Instrucción para ejecutar la sentencia 
		ResultSet rs = null;
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);					
		}
		catch (SQLException sqle){}
		return (rs);
	}

	public String ConsultaClientes()
	{
		//Creamos la instrucción para ejecutar la sentencia en la consulta de clientes
		String contenido = "";
		sentencia = "SELECT * FROM clientes";
		try
		{
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido 
			// y ejecutar la sentencia SQL 
			resultSet = statement.executeQuery(sentencia);
			// Mostramos en choice los clientes de la empresa
			while(resultSet.next())
			{
				contenido = contenido + (resultSet.getInt("idCliente")+
						"-"+ resultSet.getString("descuentoCliente")+
						"-"+ resultSet.getString("idPersonaFK")+"\n");
			}
		}
		catch (SQLException e){}
		return contenido;
	}
	public int AltaCliente(Connection connection, String sentencia)
	{
		//Creamos las instrucciones para el alta de cliente
		//Devuelve 0 para ok
		//devuelve un 1 para error
		int resultado = 1;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//y ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				resultado = 0;
			}
			else
			{
				resultado = 1;
			}
		}
		catch (SQLException sqle){}
		return (resultado);
	}

	public int BajaCliente(int idCliente)
	{
		//Creamos la instrucción con una sentencia para la baja del cliente 
		int resultado = 0;
		//Devolver 0 --> borrado con éxito
		//Devolver 1 ---> Error
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutamos el comando borrar
			String sentencia = "DELETE FROM clientes WHERE idCliente = "+ idCliente;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			//Error devuelve 1
			resultado = -1;
		}
		return (resultado);
	}

	public ResultSet elegirPersonas(Connection connection)
	{
		//Creanmos la instrucción para ejecutar la sentencia buscar al cliente de nuestra base
		ResultSet rs = null;
		try
		{

			statement = connection.createStatement();
			rs = statement.executeQuery( "SELECT * FROM clientes");					
			

		}
		catch (SQLException sqle){}
		return (rs);
	}

	public int ModificacionCliente(String sentencia)
	{
		//Creamos las instrucciones para ejecutar la modidificación de clientes
		//Devuelve 0 para ok
		//devuelve un 1 para error
		int resultado = 1;
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			statement.executeUpdate(sentencia);
			//y ejecutar la sentencia SQL
			if((statement.executeUpdate(sentencia))==1)
			{
				resultado = 0;
			}
			else
			{
				resultado = 1;
			}
		}
		catch (SQLException sqle){}
		return (resultado);
	}
}


