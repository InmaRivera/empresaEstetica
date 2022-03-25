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
public class ConsultaPersona implements WindowListener, ActionListener
{
	//Creamos los objetos necesarios para esta ventana
	Frame ventana = new Frame ("Consulta de Personas");
	MenuItem mniConsultaPersonas = new MenuItem("Consulta");
	TextArea txtPersonas = new TextArea(5,15);
	Label lblPersonas = new Label("PERSONAS");
	Button btnPDF =  new Button ("Exportar a PDF");
	BaseDatos bd = new BaseDatos();
	
	public ConsultaPersona()
	{
		//Configuramos la pantalla y añadimos los Listeners
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250,200);//aplicamos ancho y altura
		ventana.addWindowListener(this);
		// Conectar con la BD
		bd.conectar();
		ventana.add(txtPersonas);
		txtPersonas.setText(bd.ConsultaPersona());
		btnPDF.addActionListener(this);
		ventana.add(btnPDF);
		bd.desconectar();
		
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
