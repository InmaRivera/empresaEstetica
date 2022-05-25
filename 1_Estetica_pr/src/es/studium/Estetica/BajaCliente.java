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

public class BajaCliente implements WindowListener, ActionListener
{
	//Creamos los objetos necesarios para la clase de Baja cliente
	Frame ventana = new Frame ("Eliminar cliente");
	Label lblCabecera = new Label ("Elegir cliente");
	Choice choPersonas = new Choice();
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
	//Constructor
	public BajaCliente(int tipoUsuario)
	{
		this.tipoUsuario=tipoUsuario;
		//Listener
		ventana.addWindowListener(this);
		btnBorrar.addActionListener(this);

		//Pantalla
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300,200);
		ventana.setResizable(false);
		ventana.add(lblCabecera);
		//Método para rellenar choice
		rellenarChoiceClientes();
		ventana.add(choPersonas);
		ventana.add(btnBorrar);
		//Mostrar ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	private void rellenarChoiceClientes()
	{
		// Rellenar el Choice
		choPersonas.removeAll();//validación
		choPersonas.add("Seleccione un cliente...");
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
	public void actionPerformed(ActionEvent evento)
	{		
		//si seleccionamos boton borrar
		if (evento.getSource().equals(btnBorrar))
		{
			//si intentamos borrar "Seleccionarpersona
			if ((choPersonas.getSelectedItem().equals("Seleccione un cliente...")))
			{
				//mensaje de error si intentas seleccionar persona
				lblMensaje.setText("Debes seleccionar una persona");
				mostrarMensaje();
			}
			else
			{
				//sino muestra el dialogo para elegir persona
				mostrarDialogo();	
			}
		}
		else if (evento.getSource().equals(btnNo))
		{
			//boton no vuelve a la ventana elegir persona
			dlgConfirmacion.setVisible(false);
		}
		else if (evento.getSource().equals(btnSi))
		{
			
			//Al pulsar botón si conecta con la base de datos
			bd.conectar();
			//cadena para coger los datos 
			String[] array = choPersonas.getSelectedItem().split("-");
			int resultado = bd.BajaCliente(Integer.parseInt(array[0]), tipoUsuario);
	
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
		rellenarChoiceClientes();
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
		lblConfirmacion.setText("¿Está seguro de borrar a " + choPersonas.getSelectedItem()+ "?");
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
		// TODO Auto-generated method stu		
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