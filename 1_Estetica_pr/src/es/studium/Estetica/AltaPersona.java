package es.studium.Estetica;
import java.awt.Button;
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

public class AltaPersona implements WindowListener, ActionListener
{
	//Creamos los objetos de la ventana alta cliente
	Frame ventana = new Frame ("Alta de Persona");
	Dialog dlgFeedback = new Dialog (ventana, "Feedback", true);
	Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXX");
	//Sus componentes
	TextField txtNombre = new TextField(15);
	Label lblNombre = new Label("Nombre Persona *");
	TextField txtApellidos = new TextField(15);
	Label lblApellidos = new Label("Apellidos persona *");
	TextField txtDni = new TextField(15);
	Label lblDni = new Label("Dni/Documento Identidad *");
	TextField txtDomicilio = new TextField(15);
	Label lblDomicilio = new Label("Domicilio de la Persona *");
	TextField txtTelefono = new TextField(15);
	Label lblTelefono = new Label("Teléfono persona *");
	TextField txtEmail= new TextField(15);
	Label lblEmail = new Label("Correo electrónico");
	Button btnAceptar = new Button ("Aceptar");
	Button btnLimpiar = new Button ("Limpiar");
	//para conectar con la clase base de datos
	BaseDatos bd = new BaseDatos();
	Connection connection = null;

	public AltaPersona()
	{
		//Configuración de la ventana
		ventana.setLayout(new FlowLayout());
		//tamaño ancho, alto
		ventana.setSize(360,260);
		//la hacemos visible
		ventana.setVisible(true);
		//damos función a la ventana
		ventana.addWindowListener(this);
		//El usuario no puede modificar el tamaño de la ventana una vez ejecutada
		//ventana.setResizable(false);
		//para localizar la ventana en el centro de la pantalla
		ventana.setLocationRelativeTo(null);
		
		//mostramos todos lo labels y textfields
		ventana.add(lblNombre);
		ventana.add(txtNombre);
		ventana.add(lblApellidos);
		ventana.add(txtApellidos);
		ventana.add(lblDni);
		ventana.add(txtDni);
		ventana.add(lblTelefono);
		ventana.add(txtTelefono);
		ventana.add(lblDomicilio);
		ventana.add(txtDomicilio);
		ventana.add(lblEmail);
		ventana.add(txtEmail);
		//Listeners de botones
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);	
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		//Limpiar
		if(evento.getSource().equals(btnLimpiar))
		{
			Limpiar();
		}
		//Aceptar
		else if (evento.getSource().equals(btnAceptar))
		{

			//conectar la base de datos
			connection = bd.conectar();
			//Coger los datos del formulario:
			String nombre = txtNombre.getText();
			String apellidos = txtApellidos.getText();
			String dni = txtDni.getText();
			String domicilio = txtDomicilio.getText();
			String telefono = txtTelefono.getText();
			String email = txtEmail.getText();
			if(nombre.equals("")|| apellidos.equals("")|| dni.equals("")||domicilio.equals("")||telefono.equals(""))
			{
				lblMensaje.setText("Faltan datos");
				mostrarDialogo();
			}
			else
			{
				// Crear la sentencia del insert
				String sentencia = "INSERT INTO personas VALUES (null, '" ;
				sentencia+=  nombre + "', '" + apellidos + "', '" + dni 
						+"', '" + domicilio + "', '" + telefono + "', '" + email + "');";

				if((bd.AltaPersona(connection, sentencia))==0) 
				{
					//Si todo bien
					lblMensaje.setText("Alta de Persona correcta");
					dlgFeedback.setVisible(true);
					ventana.setVisible(false);
				}
				else 
				{
					// si no sale mensaje de error
					lblMensaje.setText("Error en Alta de Persona");
					mostrarDialogo();
				}
			}
			//Desconectamos base de datos
			bd.desconectar();
		}
	}

	private void mostrarDialogo()
	{
		// ventana del mensaje
		dlgFeedback.setTitle("Alta Cliente");
		dlgFeedback.setSize(200,150);
		dlgFeedback.setLayout(new FlowLayout());
		dlgFeedback.addWindowListener(this);
		dlgFeedback.add(lblMensaje);
		dlgFeedback.setLocationRelativeTo(null);
		dlgFeedback.setVisible(true);

	}

	private void Limpiar()
	{
		//funcionalidad del botón limpiar
		txtNombre.setText("");
		txtApellidos.setText(" ");
		txtDni.setText(" ");
		txtDomicilio.setText(" ");
		txtTelefono.setText(" ");
		txtEmail.setText(" ");
		txtNombre.requestFocus();
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
		if(dlgFeedback.isActive())
		{
			dlgFeedback.setVisible(false);
			Limpiar();
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
