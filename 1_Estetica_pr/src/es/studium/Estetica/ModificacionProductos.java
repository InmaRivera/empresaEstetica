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

public class ModificacionProductos implements WindowListener, ActionListener
{
	//ventana seleccion
	Frame ventana = new Frame ("Modificar producto");
	Label lblCabecera = new Label ("Elegir producto");
	Choice choProducto = new Choice();
	Button btnEditar = new Button("Editar");

	Dialog dlgMensaje = new Dialog(ventana,"Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXX");

	//Ventana Modificacion
	Dialog dlgModificar = new Dialog(ventana,"Modificación", true);
	Label lblModificar = new Label("Modificar los datos de: ");
	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	TextField txtTipo = new TextField(10);
	Label lblTipo = new Label("Nombre producto    ");
	TextField txtCantidad = new TextField(10);
	Label lblCantidad = new Label("Cantidad            ");
	TextField txtIVA = new TextField(10);
	Label lblIVA = new Label("IVA                ");
	TextField txtPrecioVenta = new TextField(10);
	Label lblPrecioVenta = new Label("Precio de venta ");
	TextField txtCompra = new TextField(10);
	Label lblCompra = new Label("Precio de compra");

	BaseDatos bd = new BaseDatos();
	int idProducto = 0;
	Connection connection = null;
	ResultSet rs = null;
	int tipoUsuario;
	public ModificacionProductos(int tipoUsuario)
	{
		this.tipoUsuario=tipoUsuario;
		//Listener
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);

		//Pantalla
		ventana.setLayout(new FlowLayout());
		//tamaño pantalla ancho y alto
		ventana.setSize(300,200);
		//usuario no puede cambiar el tamaño de la ventana ejecutada
		ventana.setResizable(false);
		ventana.add(lblCabecera);

		//rellenamos el choice
		rellenarChoiceProductos();
		ventana.add(choProducto);
		ventana.add(btnEditar);
		//mostrar ventana
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	private void rellenarChoiceProductos()
	{
		// Rellenar el Choice
		choProducto.removeAll();//validación
		choProducto.add("Seleccionar una producto...");
		// Conectar BD
		bd.conectar();
		//Sacar  los productos de la tabla 
		rs=bd.rellenarProducto(bd.conectar());
		try
		{
			while (rs.next())
			{
				choProducto.add(rs.getInt("idProducto") + "-" +
						rs.getString("tipoProducto") + "-" +
						rs.getString("cantidadProducto")+"-" + 
						rs.getString("ivaProducto"));

			}
		}
		catch(Exception e){}
		//Desconectar la base de datos
		bd.desconectar();
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnEditar))
		{
			// Validación
			if ((choProducto.getSelectedItem().equals("Seleccionar una producto...")))
			{
				//mensaje de error si intentas seleccionar producto
				lblMensaje.setText("Debes seleccionar una producto");
				mostrarDialogo();
			}

			else
			{
				mostrarModificar();
			}
		}

		else if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		else if(evento.getSource().equals(btnModificar))
		{
			bd.conectar(); 
			// Coger los datos
			//Coger los datos del formulario:
			String tipo = txtTipo.getText();
			String cantidad = txtCantidad.getText();
			String iva = txtIVA.getText();
			String venta = txtPrecioVenta.getText();
			String compra = txtCompra.getText();
			//Ejecutamos comando modificar "Update"
			String sentencia = "UPDATE estetica_pr.productos SET tipoProducto = '"+ tipo + 
					"', cantidadProducto = '" + cantidad +
					"', ivaProducto = '" + iva +
					"', precioVentaProducto= '" + venta +
					"', precioCompraProducto= '" + compra + 
					"' WHERE idProducto = " + idProducto;
			bd.guardarLog(tipoUsuario, sentencia);
			if ((bd.ModificacionPersona(sentencia)==0)) 
			{
				// Todo bien
				lblMensaje.setText("Modificación correcta");
				mostrarDialogo();
				dlgModificar.setVisible(false);

			}
			else
			{
				lblMensaje.setText("Modificación errónea");
				dlgModificar.setVisible(false);
				mostrarDialogo();
			}
			//Desconectar la base
			bd.desconectar();
			rellenarChoiceProductos();

		}
	}
	private void mostrarModificar()
	{
		//Montar la ventana Modificacion
		dlgModificar.setLayout(new FlowLayout());
		//damos tamaño al diálogo
		dlgModificar.setSize(270,240); //ancho , alto		
		//Crear una sentencia
		String[] seleccionado = choProducto.getSelectedItem().split("-");
		idProducto = Integer.parseInt(seleccionado[0]);
		try
		{
			//rellena el choice seleccionar
			rs = bd.buscarProducto(bd.conectar(), "SELECT * FROM productos WHERE idProducto = " + idProducto);		
			rs.next();
			//mostrar el producto seleccionado a modificar
			dlgModificar.add(lblModificar);
			dlgModificar.add(lblTipo);
			txtTipo.setText(rs.getString("tipoProducto"));
			dlgModificar.add(txtTipo);
			dlgModificar.add(lblCantidad);
			txtCantidad.setText(rs.getString("cantidadProducto"));
			dlgModificar.add(txtCantidad);
			dlgModificar.add(lblIVA);
			txtIVA.setText(rs.getString("ivaProducto"));
			dlgModificar.add(txtIVA);
			dlgModificar.add(lblPrecioVenta);
			txtPrecioVenta.setText(rs.getString("precioVentaProducto"));
			dlgModificar.add(txtPrecioVenta);
			dlgModificar.add(lblCompra);
			txtCompra.setText(rs.getString("precioCompraProducto"));
			dlgModificar.add(txtCompra);
			//Mostrar botones
			dlgModificar.add(btnModificar);
			dlgModificar.add(btnCancelar);
		}
		catch(Exception e){	}
		//Listener
		dlgModificar.addWindowListener(this);
		btnModificar.addActionListener(this);
		btnCancelar.addActionListener(this);

		//mostrar en pantalla
		dlgModificar.setResizable(false);
		dlgModificar.setLocationRelativeTo(null);
		dlgModificar.setVisible(true);
	}
	private void mostrarDialogo()
	{
		//Creamos el diálogo
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(240,160);
		dlgMensaje.addWindowListener(this);
		dlgMensaje.add(lblMensaje);

		//Mostramos el diálogo
		dlgMensaje.setResizable(false);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		//Indicamos que si los diáologos están abiertos se cierren primero
		if(dlgModificar.isActive())
		{
			dlgModificar.setVisible(false);
		}
		else if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		//Sino cerramos ventana principal
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
