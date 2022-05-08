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

public class ConsultaProductos implements WindowListener, ActionListener
{
	//Creamos los objetos necesarios para esta ventana
	Frame ventana = new Frame ("Consulta de Productos");
	MenuItem mniConsultaProductos = new MenuItem("Consulta");
	TextArea txtProductos = new TextArea(5,15);
	Label lblProductos = new Label("PRODUCTOS");
	Button btnPDF =  new Button ("Exportar a PDF");
	BaseDatos bd = new BaseDatos();

	public ConsultaProductos()
	{

		//Configuramos la pantalla y añadimos los Listeners
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250,200);//aplicamos ancho y altura
		ventana.addWindowListener(this);
		// Conectar con la BD
		bd.conectar();
		ventana.add(txtProductos);
		txtProductos.setText(bd.ConsultaProductos());
		btnPDF.addActionListener(this);
		ventana.add(btnPDF);
		bd.desconectar();

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Tercer trimestre

	}

	@Override
	public void windowOpened(WindowEvent e)
	{}

	@Override
	public void windowClosing(WindowEvent e)
	{
		//Cerranmos las ventanas
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
