package es.studium.Estetica;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public class ConsultaCliente implements WindowListener, ActionListener
{
	//objetos necesarios para la ventana de consulta clientes
	Frame ventana = new Frame ("Consulta de Clientes");
	MenuItem mniConsultaClientes = new MenuItem("Consulta");
	TextArea txtClientes = new TextArea(5,15);
	Label lblClientes = new Label("CLIENTES");
	Button btnPDF =  new Button ("Exportar a PDF");
	BaseDatos bd = new BaseDatos();

	public ConsultaCliente()
	{
		//Configuramos la ventana
		ventana.setLayout(new FlowLayout());
		ventana.setSize(210,200);
		ventana.add(lblClientes);
		ventana.addWindowListener(this);
		// Conectar con la BD
		bd.conectar();
		
		ventana.add(txtClientes);
		txtClientes.setText(bd.ConsultaClientes());
		btnPDF.addActionListener(this);
		ventana.add(btnPDF);
		bd.desconectar();

		//Mostramos la ventana
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		// Tercer trimestre
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		ventana.setVisible(false);	

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

