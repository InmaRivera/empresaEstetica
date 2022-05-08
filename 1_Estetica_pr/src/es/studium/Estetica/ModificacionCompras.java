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

public class ModificacionCompras implements WindowListener, ActionListener
{
	//Creamos los objetos necesarios para la clase Modificación 
	//Creación de objetos
	//ventana seleccion
	Frame ventana = new Frame ("Modificar compra");
	Label lblCabecera = new Label ("Elegir una compra: ");
	Choice choCompras = new Choice();
	Button btnEditar = new Button("Editar");

	//Ventana Modificacion
	Dialog dlgModificar = new Dialog(ventana,"Modificación", true);
	Label lblModificar = new Label("Modificar los datos de: ");
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");
	Choice choPersonas = new Choice();
	Choice choProducto = new Choice();

	//casilleros del descuento a modificar
	Label lblCliente = new Label("Elige un cliente: ");
	Label lblProducto = new Label("Elige un producto: ");
	//mensaje que enviamos al cliente según la función que haga (los mensajes se especifican abajo)
	Dialog dlgMensaje = new Dialog(ventana,"Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXX");

	//conexión base de datos
	BaseDatos bd = new BaseDatos();
	int idCompra = 0;
	int idCliente = 0;
	int idProducto = 0;
	Connection connection = null;
	ResultSet rs = null;
	public ModificacionCompras()
	{

		//Pantalla
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300,200);
		ventana.setResizable(false);
		ventana.add(lblCabecera);
		//Rellenar choice
		rellenarChoiceCompra();
		rellenarChoiceProductos();
		rellenarChoiceClientes();
		//Listener
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);

		ventana.add(choCompras);
		ventana.add(btnEditar);
		//Mostramos ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	private void rellenarChoiceCompra()
	{
		// Rellenar el Choice
		//choCompras.removeAll();
		choCompras.add("Seleccionar una compra...");
		// Conectar BD
		bd.conectar();
		//Sacar los datos de compra 
		rs=bd.elegirCompra(bd.conectar());
		try
		{
			while (rs.next())
			{
				choCompras.add(rs.getInt("idCompra") + "-" +
						rs.getString("idClienteFK") + "-" +
						rs.getString("idProductoFK"));
			}
		}
		catch(Exception e){}
		//Desconectar la base de datos
		bd.desconectar();
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		//Si clicamos en Editar
		if(evento.getSource().equals(btnEditar))
		{
			//si intentamos borrar "Seleccionar una compra" mostramos mensaje de error:
			if (choCompras.getSelectedItem().equals("Seleccionar una compra..."))
			{
				//mensaje de error si intentas seleccionar persona
				lblMensaje.setText("Debes seleccionar una compra");
				mostrarDialogo();
			}
			else
			{
				//sino muestra el dialogo para elegir compra
				mostrarModificar();	
			}
		}
		//Botón cancelar muestra ventana principal
		else if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		//Botón modificar si todo va bien indicamos qué hacer
		//modifica al cliente con la siguiente instrucción
		else if(evento.getSource().equals(btnModificar))
		{
			//Validación
			if (choPersonas.getSelectedItem().equals("Seleccione un cliente...       "))
			{
				lblMensaje.setText("Debe seleccionar un cliente");
				mostrarDialogo();
			}
			//si intentamos borrar "Seleccionar producto" nos avisará
			else if (choProducto.getSelectedItem().equals("Seleccionar una producto...          "))
			{
				//mensaje de aviso si intentas seleccionar producto
				lblMensaje.setText("Debes seleccionar una producto");
				mostrarDialogo();

			}
			//Si todo es correcto mostrar el diálogo de modificación
			else
			{
				mostrarModificar();
			}
			if(evento.getSource().equals(btnModificar))
			{
				bd.conectar(); 
				// Coger los datos de personas, productos y compras
				String[] seleccionado = choPersonas.getSelectedItem().split("-");
				int idClienteFK = Integer.parseInt(seleccionado[0]);
				String[] seleccionado1 = choProducto.getSelectedItem().split("-");
				int idProductoFK = Integer.parseInt(seleccionado1[0]);
				String[] seleccionado2 = choCompras.getSelectedItem().split("-");
				int idCompra = Integer.parseInt(seleccionado2[0]);

				String sentencia = "UPDATE estetica_pr.compras SET idClienteFK = '"+ idClienteFK + 
						"', idProductoFK = '" + idProductoFK +
						"' WHERE idCompra = " + idCompra ;	
				System.out.println(sentencia);

				if ((bd.ModificacionCompra(sentencia)==0)) 
				{
					//Si todo bien
					lblMensaje.setText("Modificación correcta");
				}
				//Sino mostrar mensaje de error
				else
				{
					lblMensaje.setText("Modificación errónea");
				}
				//Desconectar la base
				bd.desconectar();
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
		}
	}
	private void rellenarChoiceClientes()
	{
		// Rellenar el Choice
		choPersonas.removeAll();//validación
		choPersonas.add("Seleccione un cliente...       ");
		// Conectar BD
		bd.conectar();
		//Sacar a los clientes de la tabla 
		rs=bd.elegirPersonas(bd.conectar());
		try
		{
			while (rs.next())
			{
				choPersonas.add(rs.getInt("idCliente") + "-" +
						rs.getString("descuentoCliente") + "-" +
						rs.getString("idPersonaFK"));
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
		//Sacar a los productos de la tabla 
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
	private void mostrarModificar()
	{
		//Montar la ventana Modificacion
		dlgModificar.setLayout(new FlowLayout());
		dlgModificar.setSize(230,220); //ancho , alto
		try
		{
			String[] seleccionado = choPersonas.getSelectedItem().split("-");
			int idClienteFK = Integer.parseInt(seleccionado[0]);
			String[] seleccionado1 = choProducto.getSelectedItem().split("-");
			int idProductoFK = Integer.parseInt(seleccionado1[0]);

			rs = bd.buscarCompra(bd.conectar(), "SELECT * FROM compras WHERE idCompra= " + 
					idClienteFK + idProductoFK);		
			rs.next();
		}
		catch(Exception e){}

		rellenarChoiceCompra();
		dlgModificar.add(choPersonas);
		dlgModificar.add(choProducto);
		dlgModificar.add(btnModificar);
		dlgModificar.add(btnCancelar);
		//Listener
		dlgModificar.addWindowListener(this);
		btnModificar.addActionListener(this);
		btnCancelar.addActionListener(this);

		//mostrar en pantalla
		dlgModificar.setResizable(false);
		dlgModificar.setLocationRelativeTo(null);
		dlgModificar.setVisible(true);
	}
	private void mostrarDialogo()
	{
		//Configuración del diálogo mensaje
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(240,160);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.add(lblMensaje);
		//Mostrar el mensaje
		dlgMensaje.setResizable(false);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.setVisible(true);
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		//Si el mensaje está activo primero cerrar mensaje
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		//sino cerrar ventana principal
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
