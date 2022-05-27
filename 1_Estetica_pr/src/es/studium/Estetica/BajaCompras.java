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

public class BajaCompras implements WindowListener, ActionListener
{
	//Creamos los objetos necesarios para la clase de Baja cliente
	Frame ventana = new Frame ("Eliminar compra");
	Label lblCabecera = new Label ("Elegir compra");
	Choice choCompras = new Choice();
	Button btnBorrar = new Button("Borrar");

	Dialog dlgConfirmacion = new Dialog(ventana,"Confirmación", true);
	Label lblConfirmacion = new Label("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");

	Dialog dlgMensaje = new Dialog(ventana,"Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXX");

	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	int idPersonaBorrar = 0;
	int tipoUsuario;
	public BajaCompras(int tipoUsuario)
	{
		this.tipoUsuario=tipoUsuario;
		//Listener
		ventana.addWindowListener(this);
		btnBorrar.addActionListener(this);

		//Pantalla
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300,150);
		ventana.setResizable(false);
		ventana.add(lblCabecera);
		//Método para rellenar choice
		rellenarChoiceCompras();
		ventana.add(choCompras);
		ventana.add(btnBorrar);
		//Mostrar ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	private void rellenarChoiceCompras()
	{
		// Rellenar el Choice
		choCompras.removeAll();//validación
		choCompras.add("Seleccione una compra...");
		// Conectar BD
		bd.conectar();
		//Sacar los datos de compra de la tabla 
		rs=bd.elegirCompra(bd.conectar());
		try
		{
			while (rs.next())
			{
				//Después de añadir los join en base 
				//Especificamos los nombres de lo que queremos mostrar
				choCompras.add(rs.getInt("idCompra") + "-" +
						rs.getString("idClienteFK") + "-" +
						rs.getString("nombrePersona") + "-" +
						rs.getString("idProductoFK") + "-" +
						rs.getString("tipoProducto"));
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
			//si intentamos borrar "Seleccionar compra" mensaje de aviso:
			if ((choCompras.getSelectedItem().equals("Seleccione una compra...")))
			{
				//mensaje de error si intentas seleccionar compra
				lblMensaje.setText("Debes seleccionar una compra");
				mostrarMensaje();
			}
			else
			{
				//sino muestra el dialogo para elegir compra
				mostrarDialogo();	
			}
		}
		else if (evento.getSource().equals(btnNo))
		{
			//botón no vuelve a la ventana elegir compra
			dlgConfirmacion.setVisible(false);
		}
		else if (evento.getSource().equals(btnSi))
		{
			//Al pulsar botón si conecta con la base de datos
			bd.conectar();
			//cadena para coger los datos 
			String[] array = choCompras.getSelectedItem().split("-");
			int resultado = bd.BajaCompra(Integer.parseInt(array[0]), tipoUsuario);
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
			}
		}
		//Desconectar de la base 		
		bd.desconectar();
		//Validación 
		rellenarChoiceCompras();
	}
	private void mostrarMensaje()
	{
		//creamos la ventana de dialogo para mensaje error
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(300,200);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.add(lblMensaje);

		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.setVisible(true);
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
		lblConfirmacion.setText("¿Está seguro de borrar a " + choCompras.getSelectedItem()+ "?");
		dlgConfirmacion.add(btnSi);
		dlgConfirmacion.add(btnNo);
		//Mostrar diálogo
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
		//Si diáologo de confirmacion es activo cerrarlo primero
		if(dlgConfirmacion.isActive())
		{
			dlgConfirmacion.setVisible(false);
		}
		//Si es el diáologo de mensaje el activo cerrarlo primero
		else if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		//Sino podemos cerrar la ventana principal
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

