package es.studium.Estetica;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Login implements WindowListener, ActionListener
{
	//creamos la ventana para el login
	Frame ventana = new Frame();
	Dialog dlgFeedback = new Dialog (ventana, "Mensaje", true);
	Label lblFeedback = new Label ("XXXXXXXXXXXXXXXXXXXXXXX");

	//objetos para indicar usuario y clave del login
	Label lblUsuario = new Label("Usuario");
	Label lblClave = new Label("Clave");
	TextField txtUsuario = new TextField(10);	
	TextField txtClave = new TextField(10);	
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");

	Panel pnlPanel = new Panel();
	BaseDatos bd = new BaseDatos();

	Login ()
	{
		//Configuración ventana
		ventana.setTitle("Login");// Título
		ventana.addWindowListener(this);
		ventana.setSize(230, 150); // Tamaño: Ancho x Alto
		ventana.setResizable(false); // No permitir redimensión
		ventana.setLayout(new FlowLayout()); // Distribución - Diseño
		pnlPanel.setLayout(new FlowLayout());
		pnlPanel.setSize(250,200);
		dlgFeedback.addWindowListener(this);

		ventana.add(lblUsuario);
		//txtUsuario.addActionListener(this);
		ventana.add(txtUsuario);
		ventana.add(lblClave);
		txtClave.setEchoChar('*');
		//txtClave.addActionListener(this);
		ventana.add(txtClave);

		//pnlPanel.add(lblOlvide);
		ventana.add(pnlPanel);
		btnAceptar.addActionListener(this);
		ventana.add(btnAceptar);
		btnCancelar.addActionListener(this);
		ventana.add(btnCancelar);
		//ventana.add(btnRegistro);

		ventana.setLocationRelativeTo(null); // Centrar
		ventana.setVisible(true); // Mostrarla

	}
	public static void main(String[] args)
	{
		new Login();
	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnCancelar))
		{
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		}

		else if (evento.getSource().equals(btnAceptar))
		{
			bd.conectar();
			//conectar con la BD
			//Hacer un select
			//SELECT * FROM  usuarios WHERE
			String usuario =txtUsuario.getText();
			String clave = txtClave.getText();
			String sentencia = "SELECT * FROM usuarios WHERE " 
					+ "nombreUsuario = '"
					+ usuario 
					+ "' AND claveUsuario = SHA2('" + clave +  "', 256);";
			int resultado = bd.consultar(sentencia);

			//si credenciales incorrectas ---> mensaje de error 
			if(resultado == -1)
			{
				dlgFeedback.setSize(200,100);
				dlgFeedback.setLayout(new FlowLayout());
				dlgFeedback.setResizable(false);
				lblFeedback.setText("Credenciales incorrectas");
				dlgFeedback.add(lblFeedback);
				dlgFeedback.setLocationRelativeTo(null);
				dlgFeedback.setVisible(true);
			}
			//Si credenciales correctas ---> mostrar menu principal
			else
			{
				new MenuPrincipal(resultado);
				ventana.setVisible(false);//para que no aparezca la ventana de login cuando aparece Menu Principal
			}
		}
		//Desconectar BD
		bd.desconectar();
	}

	void mostrarDialogo()
	{
		dlgFeedback.setLayout(new FlowLayout());
		dlgFeedback.add(lblFeedback);

		dlgFeedback.setSize(200,150);

		dlgFeedback.setResizable(false);
		dlgFeedback.addWindowListener(this);
		dlgFeedback.setLocationRelativeTo(null);

		dlgFeedback.setVisible(true);
	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgFeedback.isActive())
		{
			dlgFeedback.setVisible(false);
		}
		else
		{
			System.exit(0);
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
