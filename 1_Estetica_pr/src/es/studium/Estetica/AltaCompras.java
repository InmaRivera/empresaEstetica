package es.studium.Estetica;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class AltaCompras implements WindowListener, ActionListener
{
	//Creamos los objetos necesario para la clase Alta cliente
	Frame ventana = new Frame ("Nueva Compra");
	Dialog dlgFeedback = new Dialog (ventana, "Feedback", true);
	Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXX");

	Label lblCliente1 = new Label("Cliente                 ");
	Label lblProducto1 = new Label("Producto                  ");

	Button btnAceptar = new Button ("Aceptar");
	Button btnCancelar = new Button ("Cancelar");
	//choise para elegir a la persona
	Choice choProducto = new Choice();
	Choice choPersonas = new Choice();

	BaseDatos bd = new BaseDatos();
	Connection connection = null;
	ResultSet rs = null;
	int tipoUsuario;
	public AltaCompras(int tipoUsuario)
	{
		//Para poder dar funcionalidad al FicheroLog
		this.tipoUsuario=tipoUsuario;
		//Configuración de la ventana y Listeners
		ventana.setLayout(new FlowLayout());
		ventana.setSize(280,200);
		ventana.setVisible(true);
		ventana.addWindowListener(this);
		//ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		//Mostramos en pantalla y botones necesarios
		ventana.add(lblCliente1);
		ventana.add(choPersonas);

		ventana.add(lblProducto1);
		ventana.add(choProducto);

		//Método rellenar choice
		rellenarChoiceClientes();
		rellenarChoiceProductos();
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);	
	}
	private void rellenarChoiceClientes()
	{
		// Rellenar el Choice
		choPersonas.removeAll();//validación
		choPersonas.add("Seleccione un cliente...       ");
		// Conectar BD
		bd.conectar();
		//Sacar a los clientes de la tabla 
		rs=bd.elegirClientes(bd.conectar());
		try
		{
			while (rs.next())
			{
				choPersonas.add(rs.getInt("idCliente") + "-" +
						rs.getString("descuentoCliente") + "-" +
						rs.getString("idPersonaFK") + "-" +
						rs.getString("nombrePersona"));
			}
		}
		catch(Exception e){}
		//Desconectar la base de datos
		bd.desconectar();
	}
	private void rellenarChoiceProductos()
	{
		// Rellenar el Choice
		choProducto.removeAll();//validación
		choProducto.add("Seleccionar una producto...          ");
		// Conectar BD
		bd.conectar();
		//Sacar a los clientes de la tabla 
		rs=bd.rellenarProducto(bd.conectar());
		try
		{
			while (rs.next())
			{
				choProducto.add(rs.getInt("idProducto") + "-" +
						rs.getString("tipoProducto") + "-" +
						rs.getString("cantidadProducto")+"-" + 
						rs.getString("ivaProducto"));

			}
		}
		catch(Exception e){}
		//Desconectar la base de datos
		bd.desconectar();
	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		//Limpiar 
		if(evento.getSource().equals(btnCancelar))
		{
			Limpiar();
		}
		//Aceptar
		else if (evento.getSource().equals(btnAceptar))
		{

			//conectar la base de datos

			connection = bd.conectar();
			//Validación
			if (choPersonas.getSelectedItem().equals("Seleccione un cliente...       "))
			{
				lblMensaje.setText("Debe seleccionar un cliente");

			}
			//si intentamos borrar "Seleccionar producto" nos avisará
			else if (choProducto.getSelectedItem().equals("Seleccionar una producto...          "))
			{
				//mensaje de error si intentas seleccionar producto
				lblMensaje.setText("Debes seleccionar una producto");

			}
			//Si todo está bien instrucción para insertar datos en la tabla compras
			else
			{
				String[] seleccionado = choPersonas.getSelectedItem().split("-");
				int idClienteFK = Integer.parseInt(seleccionado[0]);
				String[] seleccionado1 = choProducto.getSelectedItem().split("-");
				int idProductoFK = Integer.parseInt(seleccionado1[0]);
				// Hacer el insert
				String sentencia = "INSERT INTO compras VALUES (null, '";
				sentencia+= + idClienteFK + "'," + "'" + idProductoFK + "'" +");";
				bd.guardarLog(tipoUsuario, sentencia);
				if((bd.AltaCompra(connection, sentencia))==0) 
				{
					// Todo bien
					lblMensaje.setText("Nueva compra correcta");	
					ventana.setVisible(false);	
				}
				else 
				{
					// si no sale mensaje de error
					lblMensaje.setText("Error registro compra");	
				}
			}
			//Desconectamos base de datos
			bd.desconectar();
			mostrarDialogo();
		}
	}
	private void mostrarDialogo()
	{
		// Configuramos el diálogo
		dlgFeedback.setSize(280,150);
		dlgFeedback.setLayout(new FlowLayout());
		//Añadimos listeners
		dlgFeedback.addWindowListener(this);
		dlgFeedback.add(lblMensaje);
		//Mostramos el diálogo
		dlgFeedback.setLocationRelativeTo(null);
		dlgFeedback.setVisible(true);
	}
	private void Limpiar()
	{
		//volvemos a la ventana principal
		ventana.setVisible(false);
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		//Indicar que se cierre primero el diálogo
		if(dlgFeedback.isActive())
		{
			dlgFeedback.setVisible(false);
		}
		//Sino pues podemos cerrar la ventana principal
		else
		{
			ventana.setVisible(false);	
		}

	}
	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
}
