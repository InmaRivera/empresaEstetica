package es.studium.Estetica;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public class MenuPrincipal extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	MenuBar barraMenu = new MenuBar();

	Menu mnuPersonas = new Menu ("Personas");
	MenuItem mniConsultaPersonas = new MenuItem("Listado de personas");
	MenuItem mniAltaPersonas = new MenuItem("Nueva persona");
	MenuItem mniBajaPersonas = new MenuItem("Eliminar persona");
	MenuItem mniModificacionPersonas = new MenuItem("Modificar Persona");

	Menu mnuProductos = new Menu ("Productos");
	MenuItem mniConsultaProductos = new MenuItem("Listado de productos");
	MenuItem mniAltaProductos = new MenuItem("Nuevo producto");
	MenuItem mniBajaProductos = new MenuItem("Eliminar producto");
	MenuItem mniModificacionProductos = new MenuItem("Modificar producto");

	Menu mnuClientes = new Menu ("Clientes");
	MenuItem mniConsultaClientes = new MenuItem("Lista de clientes");
	MenuItem mniAltaClientes = new MenuItem("Nuevo cliente");
	MenuItem mniBajaClientes = new MenuItem("Eliminar cliente");
	MenuItem mniModificacionClientes= new MenuItem("Modificar cliente");

	Menu mnuCompras = new Menu ("Compras");
	MenuItem mniConsultaCompras = new MenuItem("Listado de compras");
	MenuItem mniAltaCompras = new MenuItem("Nueva compra");
	MenuItem mniBajaCompras = new MenuItem("Eliminar compra");
	MenuItem mniModificacionCompras= new MenuItem("Modificar compra");
	
	Image imagen;
	// Declarar el objeto Toolkit para manejo de imágenes
	Toolkit herramienta;
	int tipoUsuario;
	public MenuPrincipal(int tipo)
	{
		this.tipoUsuario = tipo;
		//Configuración Pantalla
		addWindowListener(this);
		setSize(300, 220); // Tamaño: Ancho x Alto
		setResizable(false); // No permitir redimensión
		setLayout(new FlowLayout());
		
		//Listener y menús
		if(tipo == 1)//Si se accede con usuario administrador se muestra estos ítems
		{
			mniBajaCompras.addActionListener(this);
			mnuCompras.add(mniBajaCompras);
			mniModificacionCompras.addActionListener(this);
			mnuCompras.add(mniModificacionCompras);
		}
		mniAltaCompras.addActionListener(this);
		mnuCompras.add(mniAltaCompras);
		mniConsultaCompras.addActionListener(this);
		mnuCompras.add(mniConsultaCompras);
		barraMenu.add(mnuCompras);

		if(tipo == 1)
		{
			mniBajaProductos.addActionListener(this);
			mnuProductos.add(mniBajaProductos);
			mniModificacionProductos.addActionListener(this);
			mnuProductos.add(mniModificacionProductos);
		}
		mniAltaProductos.addActionListener(this);
		mnuProductos.add(mniAltaProductos);
		mniConsultaProductos.addActionListener(this);
		mnuProductos.add(mniConsultaProductos);
		barraMenu.add(mnuProductos);

		if(tipo == 1)
		{
			mniBajaClientes.addActionListener(this);
			mnuClientes.add(mniBajaClientes);
			mniModificacionClientes.addActionListener(this);
			mnuClientes.add(mniModificacionClientes);
		}
		mniAltaClientes.addActionListener(this);
		mnuClientes.add(mniAltaClientes);
		mniConsultaClientes.addActionListener(this);
		mnuClientes.add(mniConsultaClientes);
		barraMenu.add(mnuClientes);

		if(tipo == 1)
		{
			mniBajaPersonas.addActionListener(this);
			mnuPersonas.add(mniBajaPersonas);
			mniModificacionPersonas.addActionListener(this);
			mnuPersonas.add(mniModificacionPersonas);
		}
		mniAltaPersonas.addActionListener(this);
		mnuPersonas.add(mniAltaPersonas);
		mniConsultaPersonas.addActionListener(this);
		mnuPersonas.add(mniConsultaPersonas);
		barraMenu.add(mnuPersonas);
		
		setVisible(true);
		herramienta = getToolkit();
		// Especificar la ruta de la imagen
		imagen = herramienta.getImage("img\\CentroEstetiCA.jpg");
		setSize(299, 246); // En pixeles Width, Height. Ir probando y cambiando anchura y altura
		setMenuBar(barraMenu);
		setLocationRelativeTo(null); // Centrar
			
	}
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		//Funcionalidad de los ítems
		if(evento.getSource().equals(mniConsultaPersonas))
		{
			new ConsultaPersona();
		}
		else if(evento.getSource().equals(mniAltaPersonas))
		{
			new AltaPersona(tipoUsuario);
		}
		else if(evento.getSource().equals(mniBajaPersonas))
		{
			new BajaPersona();
		}
		else if(evento.getSource().equals(mniModificacionPersonas))
		{
			new ModificacionPersona();
		}
		else if(evento.getSource().equals(mniConsultaClientes))
		{
		
			new ConsultaCliente(tipoUsuario);
		}
		else if(evento.getSource().equals(mniAltaClientes))
		{
			new AltaCliente(tipoUsuario);
		}
		else if(evento.getSource().equals(mniBajaClientes))
		{
			new BajaCliente(tipoUsuario);
		}
		else if(evento.getSource().equals(mniModificacionClientes))
		{
			new ModificacionClientes();
		}
		else if(evento.getSource().equals(mniAltaProductos))
		{
			new AltaProductos();
		}
		else if(evento.getSource().equals(mniConsultaProductos))
		{
			new ConsultaProductos();
		}
		else if(evento.getSource().equals(mniBajaProductos))
		{
			new BajaProductos();
		}
		else if(evento.getSource().equals(mniModificacionProductos))
		{
			new ModificacionProductos();
		}
		else if(evento.getSource().equals(mniConsultaCompras))
		{
			new ConsultaCompras();
		}
		else if(evento.getSource().equals(mniAltaCompras))
		{
			new AltaCompras();
		}
		else if(evento.getSource().equals(mniBajaCompras))
		{
			new BajaCompras();
		}
		else if (evento.getSource().equals(mniModificacionCompras))
		{
			new ModificacionCompras();
		}
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
		BaseDatos bd = new BaseDatos();
		bd.guardarLog(tipoUsuario, "logOut");
		System.exit(0);		

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
