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
import java.sql.ResultSet;

public class BajaProductos implements WindowListener, ActionListener
{
	Frame ventana = new Frame ("Eliminar producto");
	Label lblCabecera = new Label ("Elegir producto");
	Choice choProducto = new Choice();
	Button btnBorrar = new Button("Borrar");

	Dialog dlgConfirmacion = new Dialog(ventana,"Confirmación", true);
	Label lblConfirmacion = new Label("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");

	Dialog dlgMensaje = new Dialog(ventana,"Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	int idProductoBorrar = 0;

	public BajaProductos()
	{

		//Listener
		ventana.addWindowListener(this);
		btnBorrar.addActionListener(this);

		//Pantalla
		ventana.setLayout(new FlowLayout());
		ventana.setSize(310,150);
		ventana.setResizable(false);
		ventana.add(lblCabecera);


		//metodo rellenar choice
		rellenarChoiceProductos();
		ventana.add(choProducto);
		ventana.add(btnBorrar);
		//mostramos pantalla
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	private void rellenarChoiceProductos()
	{
		// Rellenar el Choice
		choProducto.removeAll();//validación
		choProducto.add("Seleccionar un producto...");
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
		//si seleccionamos boton borrar
		if (evento.getSource().equals(btnBorrar))
		{
			//si intentamos borrar "Seleccionar producto" mensaje de aviso:
			if ((choProducto.getSelectedItem().equals("Seleccionar un producto...")))
			{
				//mensaje de error si intentas seleccionar producto
				lblMensaje.setText("Debes seleccionar un producto");
				mostrarMensaje();
			}
			else
			{
				//sino muestra el dialogo para elegir producto
				mostrarDialogo();	
			}
		}
		else if (evento.getSource().equals(btnNo))
		{
			//boton "no" vuelve a la ventana elegir producto
			dlgConfirmacion.setVisible(false);
		}
		else if (evento.getSource().equals(btnSi))
		{
			//Al pulsar botón si conecta con la base de datos
			bd.conectar();
			//cadena para coger los datos 
			String[] array = choProducto.getSelectedItem().split("-");
			int resultado = bd.BajaProducto(Integer.parseInt(array[0]));
			if (resultado == 0)
			{
				//si todo sale bien mensaje de correcto
				lblMensaje.setText("Se ha eliminado correctamente");
				dlgConfirmacion.setVisible(false);
				mostrarMensaje();	
			}
			else 
			{		
				//sino sale bien mensaje de error
				lblMensaje.setText("Se ha producido un error en el borrado");
				//mostramos el dlg de mensaje error
				mostrarMensaje();
			}
		}
		//Desconectar de la base 		
		bd.desconectar();
		rellenarChoiceProductos();
	}
	private void mostrarMensaje()
	{
		//creamos la ventana de dialogo para mensaje error
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(300,200);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.add(lblMensaje);

		//lblMensaje.setText("Error");
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.setVisible(true);
		//dlgConfirmacion.setVisible(false);
	}
	private void mostrarDialogo()
	{ 
		//Mostrar el diálogo de mensaje de confirmación si todo sale bien
		//Listeners
		dlgConfirmacion.addWindowListener(this);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		//Pantalla
		dlgConfirmacion.setLayout(new FlowLayout());
		dlgConfirmacion.setSize(400,100);
		dlgConfirmacion.add(lblConfirmacion);
		lblConfirmacion.setText("¿Está seguro de borrar a " + choProducto.getSelectedItem()+ "?");
		dlgConfirmacion.add(btnSi);
		dlgConfirmacion.add(btnNo);
		//Mostramos la ventana
		dlgConfirmacion.setLocationRelativeTo(null);
		dlgConfirmacion.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		//Cerramos las ventanas
		if(dlgConfirmacion.isActive())
		{
			dlgConfirmacion.setVisible(false);
		}
		else if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
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
