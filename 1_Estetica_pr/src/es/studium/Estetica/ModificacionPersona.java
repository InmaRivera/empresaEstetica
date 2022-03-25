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
public class ModificacionPersona implements WindowListener, ActionListener
{	
	//ventana seleccion
	Frame ventana = new Frame ("Eliminar persona");
	Label lblCabecera = new Label ("Elegir persona");
	Choice choPersonas = new Choice();
	Button btnEditar = new Button("Editar");

	Dialog dlgMensaje = new Dialog(ventana,"Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXX");

	//Ventana Modificacion
	Dialog dlgModificar = new Dialog(ventana,"Modificación", true);
	Label lblModificar = new Label("Modificar los datos de: ");
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	//Frame ventanaModificacion = new Frame ("Modificar persona");
	TextField txtNombre = new TextField(10);
	Label lblNombre = new Label("Nombre");
	TextField txtApellidos = new TextField(10);
	Label lblApellidos = new Label("Apellidos");
	TextField txtDni = new TextField(10);
	Label lblDni = new Label("Dni o NIF");
	TextField txtDomicilio = new TextField(10);
	Label lblDomicilio = new Label("Domicilio");
	TextField txtTelefono = new TextField(10);
	Label lblTelefono = new Label("Teléfono");
	TextField txtEmail= new TextField(10);
	Label lblEmail = new Label("Email");


	BaseDatos bd = new BaseDatos();
	int idPersona = 0;
	Connection connection = null;
	ResultSet rs = null;

	public ModificacionPersona()
	{
		//Listener
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);


		//Pantalla
		ventana.setLayout(new FlowLayout());
		//tamaño pantalla ancho y alto
		ventana.setSize(300,200);
		//usuario no puede cambiar el tamaño de la ventana ejecutada
		ventana.setResizable(false);
		ventana.add(lblCabecera);
		
		//rellenamos el choice
		rellenarChoicePersonasM();
		ventana.add(choPersonas);
		ventana.add(btnEditar);
		//mostrar ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	private void rellenarChoicePersonasM()
	{
		// Rellenar el Choice
		choPersonas.removeAll();//validación
		choPersonas.add("Seleccionar una persona...");
		// Conectar BD
		bd.conectar();
		//Sacar a los clientes de la tabla 
		rs=bd.rellenarPersonas(bd.conectar());
		try
		{
			while (rs.next())
			{
				//el choice que muestra a los clientes/personas de esta base
				choPersonas.add(rs.getInt("idPersona") + "-" +
						rs.getString("nombrePersona") + "-" +
						rs.getString("apellidosPersona")+"-" + 
						rs.getString("dniPersona"));
			}
		}
		catch(Exception e){}
		//Desconectar la base de datos
		bd.desconectar();
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnEditar))
		{
			// Validación
			if ((choPersonas.getSelectedItem().equals("Seleccionar una persona...")))
			{
				lblMensaje.setText("Debes seleccionar una persona");
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
			else
			{
				mostrarModificar();
			}
		}
		else if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		else if(evento.getSource().equals(btnModificar))
		{
			bd.conectar(); 
			// Coger los datos
			String nombreNuevo = txtNombre.getText();
			String apellidosNuevo = txtApellidos.getText();
			String dniNuevo = txtDni.getText();
			String domicilioNuevo = txtDomicilio.getText();
			String telefonooNuevo = txtTelefono.getText();
			String emailNuevo = txtEmail.getText();
			//Ejecutamos comando modificar "Update"
			String sentencia = "UPDATE estetica_pr.personas SET nombrePersona = '"+ nombreNuevo + 
					"', apellidosPersona = '" + apellidosNuevo +
					"', dniPersona = '" + dniNuevo +
					"', domicilioPersona= '" + domicilioNuevo +
					"', telefonoPersona= '" + telefonooNuevo + 
					"', emailPersona = '" + emailNuevo + 
					"' WHERE idPersona = " + idPersona;

			if ((bd.ModificacionPersona(sentencia)==0)) 
			{
				// Todo bien
				lblMensaje.setText("Modificación correcta");
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
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
		//damos tamaño al diálogo
		dlgModificar.setSize(215,305); //ancho , alto		
		//Crear una sentencia
		String[] seleccionado = choPersonas.getSelectedItem().split("-");
		idPersona = Integer.parseInt(seleccionado[0]);
		try
		{
			//rellena el choice seleccionar
			rs = bd.buscarPersona(bd.conectar(), "SELECT * FROM personas WHERE idPersona = " + idPersona);		
			rs.next();
			//mostrar la persona seleccionada a modificar
			dlgModificar.add(lblModificar);
			dlgModificar.add(lblNombre);
			txtNombre.setText(rs.getString("nombrePersona"));
			dlgModificar.add(txtNombre);
			dlgModificar.add(lblApellidos);
			txtApellidos.setText(rs.getString("apellidosPersona"));
			dlgModificar.add(txtApellidos);
			dlgModificar.add(lblDni);
			txtDni.setText(rs.getString("dniPersona"));
			dlgModificar.add(txtDni);
			dlgModificar.add(lblDomicilio);
			txtDomicilio.setText(rs.getString("domicilioPersona"));
			dlgModificar.add(txtDomicilio);
			dlgModificar.add(lblTelefono);
			txtTelefono.setText(rs.getString("telefonoPersona"));
			dlgModificar.add(txtTelefono);
			dlgModificar.add(lblEmail);
			txtEmail.setText(rs.getString("emailPersona"));
			dlgModificar.add(txtEmail);
			//Mostrar botones
			dlgModificar.add(btnModificar);
			dlgModificar.add(btnCancelar);
		}
		catch(Exception e){	}
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
		//Creamos el diálogo
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(240,160);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.add(lblMensaje);
		
		//Mostramos el diálogo
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
		//Indicamos que si los diáologos están abiertos se cierren primero
		if(dlgModificar.isActive())
		{
			dlgModificar.setVisible(false);
		}
		else if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		//Sino cerramos ventana principal
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
