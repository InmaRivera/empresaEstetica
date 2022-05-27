package es.studium.Estetica;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class ConsultaCliente implements WindowListener, ActionListener
{
	//objetos necesarios para la ventana de consulta clientes
	Frame ventana = new Frame ("Consulta de Clientes");
	MenuItem mniConsultaClientes = new MenuItem("Consulta");
	TextArea txtClientes = new TextArea(5,24);
	Label lblClientes = new Label("CLIENTES");
	Button btnPDF =  new Button ("Exportar a PDF");
	BaseDatos bd = new BaseDatos();
	//Añadimos componentes necesarios para PDF
	public static final String DEST = "ConsultaCliente.pdf";
	PdfFont font;
	PdfWriter writer;
	//
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	int tipoUsuario;

	public ConsultaCliente(int tipoUsuario)
	{
		this.tipoUsuario=tipoUsuario;
		//Configuramos la ventana
		ventana.setLayout(new FlowLayout());
		ventana.setSize(250,210);
		ventana.add(lblClientes);
		ventana.addWindowListener(this);
		// Conectar con la BD
		connection = bd.conectar();

		ventana.add(txtClientes);
		txtClientes.setText(bd.ConsultaClientes(tipoUsuario));
		btnPDF.addActionListener(this);
		ventana.add(btnPDF);

		//Mostramos la ventana
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnPDF))
		{

			try
			{
				//Initialize PDF writer
				try
				{
					writer = new PdfWriter(DEST);
				} catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Initialize PDF document
				PdfDocument pdf = new PdfDocument(writer);
				// Initialize document
				Document document = new Document(pdf);
				//Add paragraph to the document
				document.add(new Paragraph("Clientes:"));
				// Create a PdfFont
				try
				{
					font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
				} catch (java.io.IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					String sentencia;
					statement = connection.createStatement();
					// Crear un objeto ResultSet para guardar lo obtenido 
					// y ejecutar la sentencia SQL 
					sentencia = "SELECT * FROM clientes join personas on idPersona =  idPersonaFK";
					resultSet = statement.executeQuery(sentencia);
					// Mostramos en choice los clientes de la empresa
					while(resultSet.next())
					{
						//Guardamos en documento PDF
						document.add(new Paragraph((resultSet.getInt("idCliente")+
								"-"+ resultSet.getString("nombrePersona")+
								"-"+ resultSet.getString("apellidosPersona")+
								"-"+ resultSet.getString("descuentoCliente")+
								"-"+ resultSet.getString("idPersonaFK")+"\n")));
					}
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Close document
				document.close();
				// Open the new PDF document just created
				try
				{
					Desktop.getDesktop().open(new File(DEST));
				} catch (java.io.IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch(IOException ioe) {}		
		}
		bd.desconectar();
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

