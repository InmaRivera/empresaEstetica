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

public class ConsultaCompras  implements WindowListener, ActionListener
{
	//objetos necesarios para la ventana de consulta compras
	Frame ventana = new Frame ("Consulta compras");
	MenuItem mniConsultaCompras = new MenuItem("Consulta");
	TextArea txtCompras = new TextArea(5,15);
	Label lblCompras = new Label("  COMPRAS   ");
	Button btnPDF =  new Button ("Exportar a PDF");
	BaseDatos bd = new BaseDatos();
	public ConsultaCompras()
	{
		//Configuramos la ventana
		ventana.setLayout(new FlowLayout());
		ventana.setSize(210,200);
		ventana.add(lblCompras);
		ventana.addWindowListener(this);
		// Conectar con la BD
		bd.conectar();

		ventana.add(txtCompras);
		txtCompras.setText(bd.ConsultaCompras());
		btnPDF.addActionListener(this);
		ventana.add(btnPDF);
		bd.desconectar();

		//Mostramos la ventana
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub

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
