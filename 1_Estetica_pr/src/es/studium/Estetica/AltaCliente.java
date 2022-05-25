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

public class AltaCliente implements WindowListener, ActionListener
{
	//Creamos los objetos necesario para la clase Alta cliente
	Frame ventana = new Frame ("Alta de cliente");
	Dialog dlgFeedback = new Dialog (ventana, "Feedback", true);
	Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXX");

	TextField txtDescuento = new TextField(10);
	Label lblDescuento = new Label("Descuento cliente");
	Label lblCliente = new Label("Escoge un cliente");
	
	Button btnAceptar = new Button ("Aceptar");
	Button btnCancelar = new Button ("Cancelar");
	//choise para elegir a la persona
	Choice choPersonas = new Choice();
		
	BaseDatos bd = new BaseDatos();
	Connection connection = null;
	ResultSet rs = null;
	int tipoUsuario;
	public AltaCliente(int tipoUsuario)
	{
		this.tipoUsuario=tipoUsuario;
		//Configuración de la ventana y Listeners
		ventana.setLayout(new FlowLayout());
		ventana.setSize(320,200);
		ventana.setVisible(true);
		ventana.addWindowListener(this);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		//Mostramos en pantalla y botones necesarios
		ventana.add(lblDescuento);
		ventana.add(txtDescuento);
		ventana.add(lblCliente);
		ventana.add(choPersonas);
		
		//Método rellenar choice
		rellenarClienteA();
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);	
	}
	private void rellenarClienteA()
	{
		//Rellenar choice
		choPersonas.removeAll();//validación
		choPersonas.add("Seleccione un cliente...");
		//choice para seleccionar a la persona que buscamos
		rs=bd.rellenarPersonas(bd.conectar());
		try
		{
			while (rs.next())
			{
				choPersonas.add(rs.getInt("idPersona") + "-" +
						rs.getString("nombrePersona") + "-" +
						rs.getString("apellidosPersona")+"-" + 
						rs.getString("dniPersona"));
			}
		}
		catch(Exception e){}
		//desconectamos de la base
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
			//Coger los datos del formulario:
			String descuento = txtDescuento.getText();
			
			//Validación
			if(descuento.equals(""))
			{
				lblMensaje.setText("Debe aplicar un descuento");
				
			}
			else if (choPersonas.getSelectedItem().equals("Seleccione un cliente..."))
			{
				lblMensaje.setText("seleccione un cliente");
				
			}
			//Si todo está bien instrucción para insertar al cliente
			else
			{
				String[] seleccionado = choPersonas.getSelectedItem().split("-");
				int idPersonaFK = Integer.parseInt(seleccionado[0]);
				// Hacer el insert
				String sentencia = "INSERT INTO clientes VALUES (null, '" ;
				sentencia+=  descuento + "'," + "'" + idPersonaFK + "'" +");";
				bd.guardarLog(tipoUsuario, sentencia);
				System.out.println(sentencia);

				if((bd.AltaCliente(connection, sentencia))==0) 
				{
					// Todo bien
					lblMensaje.setText("Alta de cliente correcta");	
					ventana.setVisible(false);	
				}
				else 
				{
					// si no sale mensaje de error
					lblMensaje.setText("Error en Alta de Cliente");	
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
		dlgFeedback.setTitle("Alta Cliente");
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

