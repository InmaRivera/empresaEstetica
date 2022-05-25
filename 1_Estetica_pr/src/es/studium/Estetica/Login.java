package es.studium.Estetica;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public class Login extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	//creamos la ventana para el login
	
	Dialog dlgFeedback = new Dialog (this, "Mensaje", true);
	Label lblFeedback = new Label ("XXXXXXXXXXXXXXXXXXXXXXX");

	//objetos para indicar usuario y clave del login
	Label lblUsuario = new Label("Usuario");
	Label lblClave = new Label("Clave   ");
	TextField txtUsuario = new TextField("administrador");	
	TextField txtClave = new TextField("Studium");	
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");

	BaseDatos bd = new BaseDatos();
	Image imagen;
	// Declarar el objeto Toolkit para manejo de im�genes
	Toolkit herramienta;
	Login ()
	{
		//Configuraci�n ventana
		setTitle("Login");// T�tulo
		addWindowListener(this);
		setSize(210, 180); // Tama�o: Ancho x Alto
		//setResizable(false); // No permitir redimensi�n
		setLayout(new FlowLayout()); // Distribuci�n - Dise�o
		dlgFeedback.addWindowListener(this);

		add(lblUsuario);
		add(txtUsuario);
		add(lblClave);
		txtClave.setEchoChar('*');
		add(txtClave);

		btnAceptar.addActionListener(this);
		add(btnAceptar);
		btnCancelar.addActionListener(this);
		add(btnCancelar);
		
		//herramienta toolkit
		herramienta = getToolkit();
		// Especificar la ruta de la imagen
		imagen = herramienta.getImage("img\\seguridad.jpg");
		setLocationRelativeTo(null); // Centrar
		setVisible(true); // Mostrarla
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
				bd.guardarLog(resultado, "login");
				new MenuPrincipal(resultado);
				setVisible(false);//para que no aparezca la ventana de login cuando aparece Menu Principal
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
	public void paint(Graphics g)
	{
		// Dibujar la imagen
		g.drawImage(imagen,4,23,this);
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
