package es.studium.Estetica;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public String ConsultaClientes(int tipoUsuario)
	{
		//Creamos la instrucción para ejecutar la sentencia en la consulta de clientes
		String contenido = "";
		//Añadimos join para poder ver los usuarios
		sentencia = "SELECT * FROM clientes join personas on idPersona =  idPersonaFK";
		//Hacemos registro de los movimientos y los guardamos en este fichero
		guardarLog(tipoUsuario, sentencia);
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
						"-"+ resultSet.getString("nombrePersona")+
						"-"+ resultSet.getString("apellidosPersona")+
						"-"+ resultSet.getString("descuentoCliente")+
						"-"+ resultSet.getString("idPersonaFK")+"\n");
				guardarLog(tipoUsuario, contenido);
				
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

	public int BajaCliente(int idCliente, int tipoUsuario)
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
			//guardar movimientos en ficheroLog
			guardarLog(tipoUsuario, sentencia);
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

	public int AltaProducto(Connection connection, String sentencia)
	{
		//Creamos la instrucción para conexión de alta producto
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

	public String ConsultaProductos()
	{
		//Creamos la sentencia SQL para la consulta 
		String contenido = "";
		sentencia = "SELECT * FROM productos";
		try
		{
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido 
			// y ejecutar la sentencia SQL 
			resultSet = statement.executeQuery(sentencia);
			// Por cada departamento, meter sus datos en el Choice
			while(resultSet.next())
			{
				contenido = contenido + (resultSet.getInt("idProducto")+
						"-"+ resultSet.getString("tipoProducto")+
						"-"+ resultSet.getString("cantidadProducto")+
						"-"+ resultSet.getString("ivaProducto")+"\n");
			}
		}
		catch (SQLException e){}
		return contenido;

	}
	public ResultSet rellenarProducto(Connection connection)
	{
		//Creamos la instrucción para la consulta de personas (rellenar el choice)
		ResultSet rs = null;
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery( "SELECT * FROM productos");					
		}
		catch (SQLException sqle){}
		return (rs);
	}

	public int BajaProducto(int idProducto)
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
			String sentencia = "DELETE FROM productos WHERE idProducto = "+ idProducto;
			statement.executeUpdate(sentencia);
		}
		catch (SQLException sqle)
		{
			//Error devuelve 1
			resultado = -1;
		}
		return (resultado);
	}

	public ResultSet buscarProducto(Connection connection, String sentencia)
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

	public String ConsultaCompras(int tipoUsuario)
	{
		//Creamos la instrucción para ejecutar la sentencia en la consulta de compras
		String contenido = "";
		//Añadimos los join para ver los nombres de productos y clientes;
		//Al tener dos FK necesitamos hacer más join que cuando es uno, la sentencia queda más larga
		sentencia = "SELECT * FROM compras join clientes on idCliente =  idClienteFK join personas on idPersona = idPersonaFK \r\n"
				+ "join productos on idProducto = idProductoFK";
		guardarLog(tipoUsuario, sentencia);
		try
		{
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido 
			// y ejecutar la sentencia SQL 
			resultSet = statement.executeQuery(sentencia);
			// Mostramos en choice los clientes de la empresa
			while(resultSet.next())
			{
				contenido = contenido + (resultSet.getInt("idCompra")+
						"-"+ resultSet.getString("idClienteFK")+
						"-"+ resultSet.getString("nombrePersona")+
						"-"+ resultSet.getString("idProductoFK")+
						"-"+ resultSet.getString("tipoProducto")+"\n");
			}
			guardarLog(tipoUsuario, sentencia);
		}
		catch (SQLException e){}
		return contenido;
	}

	public int AltaCompra(Connection connection, String sentencia)
	{
		//Creamos la instrucción para conexión de alta compra
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

	public ResultSet elegirCompra(Connection connection)
	{
		//Creanmos la instrucción para ejecutar la sentencia buscar la compra de nuestra base
				ResultSet rs = null;
				try
				{

					statement = connection.createStatement();
					rs = statement.executeQuery( "SELECT * FROM compras");					


				}
				catch (SQLException sqle){}
				return (rs);
	}

	public int BajaCompra(int idCompra)
	{
		//Creamos la instrucción con una sentencia para la baja de la compra
		int resultado = 0;
		//Devolver 0 --> borrado con éxito
		//Devolver 1 ---> Error
		try
		{
			//Crear una sentencia
			statement = connection.createStatement();
			//Ejecutamos el comando borrar
			String sentencia = "DELETE FROM compras WHERE idCompra = "+ idCompra;
			statement.executeUpdate(sentencia);
			System.out.println(sentencia);
		}
		catch (SQLException sqle)
		{
			//Error devuelve 1
			resultado = -1;
		}
		return (resultado);
	}

	public int ModificacionCompra(String sentencia)
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

	public ResultSet buscarCompra(Connection connection, String sentencia)
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
	public void guardarLog(int tipoUsuario, String mensaje)
	{
		String usuario;
		if (tipoUsuario==0)
		{
			usuario = "basico";
		}
		else
		{
			usuario = "administrador";
		}
		Date fecha = new Date ();
		String pattern = "dd//MM/YY HH:mm:SS";
		SimpleDateFormat formatear = new SimpleDateFormat (pattern);
	
		try
		{
			FileWriter fw = new FileWriter("FicheroLog.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.println("["+formatear.format(fecha)+ "]" + "["+ usuario +"]" +  "["+mensaje+"]");
			System.out.println("["+formatear.format(fecha)+ "]"+ "["+ usuario +"]"  + "["+mensaje+"]");
			salida.close();
			bw.close();
			fw.close();
		}
		catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		
	}
}
	


