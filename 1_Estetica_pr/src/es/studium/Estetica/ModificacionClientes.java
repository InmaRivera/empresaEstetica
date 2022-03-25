package es.studium.Estetica;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class ModificacionClientes implements WindowListener, ActionListener
{
	//Creación de objetos
	//ventana seleccion
	Frame ventana = new Frame ("Modificar cliente");
	Label lblCabecera = new Label ("Elegir cliente");
	Choice choPersonas = new Choice();
	Button btnEditar = new Button("Editar");

	//casilleros del descuento a modificar
	TextField txtDescuento = new TextField(10);
	Label lblDescuento = new Label("Descuento del cliente");
	//mensaje que enviamos al cliente según la función que haga (los mensajes se especifican abajo)
	Dialog dlgMensaje = new Dialog(ventana,"Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXX");

	//Ventana Modificacion
	Dialog dlgModificar = new Dialog(ventana,"Modificación", true);
	Label lblModificar = new Label("Modificar los datos de: ");
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	//conexión base de datos
	BaseDatos bd = new BaseDatos();
	int idCliente = 0;
	Connection connection = null;
	ResultSet rs = null;

	//Constructor
	public ModificacionClientes()
	{
		//Listener
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);


		//Pantalla
		ventana.setLayout(new FlowLayout());
		ventana.setSize(300,200);
		ventana.setResizable(false);
		ventana.add(lblCabecera);
		// Rellenar el Choice
		choPersonas.add("Seleccionar un cliente...");
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

		//Sacar los datos de la tabla personas
		// Registro a registro, meteros en el choice
		//Desconectar la base de datos
		bd.desconectar();
		ventana.add(choPersonas);
		ventana.add(btnEditar);
		//Mostramos ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		//Si clicamos en Editar
		if(evento.getSource().equals(btnEditar))
		{
			// Validación
			if ((choPersonas.getSelectedItem().equals("Seleccionar un cliente...")))
			{
				lblMensaje.setText("Debes seleccionar un cliente");
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
			//Si todo es correcto mostrar el diálogo de modificación
			else
			{
				mostrarModificar();
			}
		}
		//Boton cancelar muestra ventana principañ
		else if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		//boton modificar si todo va bien indicamos qué hacer
		//modifica al cliente con la siguiente instrucción
		else if(evento.getSource().equals(btnModificar))
		{
			bd.conectar(); 
			// Coger los datos
			String descuentoNuevo = txtDescuento.getText();

			//Ejecutamos comando modificar "Update"
			String sentencia = "UPDATE estetica_pr.clientes SET descuentoCliente = '"+ descuentoNuevo + 
					"' WHERE idCliente = " + idCliente;	
			if ((bd.ModificacionCliente(sentencia)==0)) 
			{
				//Si todo bien
				lblMensaje.setText("Modificación correcta");
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
			//Sino mostrar mensaje de error
			else
			{
				lblMensaje.setText("Modificación errónea");
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
			//Desconectar la base
			bd.desconectar();	
		}
	}
	private void mostrarModificar()
	{
		//Montar la ventana Modificacion
		dlgModificar.setLayout(new FlowLayout());
		dlgModificar.setSize(200,220); //ancho , alto


		//Crear una sentencia para buscar al cliente
		String[] seleccionado = choPersonas.getSelectedItem().split("-");
		idCliente = Integer.parseInt(seleccionado[0]);
		try
		{
			rs = bd.buscarPersona(bd.conectar(), "SELECT * FROM clientes WHERE idCliente = " + idCliente);		
			rs.next();

			dlgModificar.add(lblModificar);
			dlgModificar.add(lblDescuento);
			txtDescuento.setText(rs.getString("descuentoCliente"));
			dlgModificar.add(txtDescuento);
			dlgModificar.add(btnModificar);
			dlgModificar.add(btnCancelar);
		}
		catch(Exception e){}

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
