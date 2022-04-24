package es.studium.Estetica;
import java.awt.Button;
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

public class AltaProductos implements WindowListener, ActionListener
{
	//Creamos los objetos de la ventana alta cliente
		Frame ventana = new Frame ("Nuevo Producto");
		Dialog dlgFeedback = new Dialog (ventana, "Feedback", true);
		Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXX");
		//Sus componentes
		TextField txtTipo = new TextField(15);
		Label lblTipo = new Label("Tipo Producto         ");
		TextField txtCantidad = new TextField(15);
		Label lblCantidad = new Label("Cantidad          ");
		TextField txtIVA = new TextField(15);
		Label lblIVA = new Label("IVA                  ");
		TextField txtPrecioVenta = new TextField(15);
		Label lblPrecioVenta = new Label("Precio de  Venta");
		TextField txtCompra= new TextField(15);
		Label lblCompra = new Label("Precio de compra");
		Button btnAceptar = new Button ("Aceptar");
		Button btnLimpiar = new Button ("Limpiar");
		//para conectar con la clase base de datos
		BaseDatos bd = new BaseDatos();
		Connection connection = null;
		public AltaProductos()
		{
			//Configuración de la ventana
			ventana.setLayout(new FlowLayout());
			//tamaño ancho, alto
			ventana.setSize(330,230);
			//la hacemos visible
			ventana.setVisible(true);
			//damos función a la ventana
			ventana.addWindowListener(this);
			//El usuario no puede modificar el tamaño de la ventana una vez ejecutada
			//ventana.setResizable(false);
			//para localizar la ventana en el centro de la pantalla
			ventana.setLocationRelativeTo(null);
			
			//mostramos todos lo labels y textfields
			ventana.add(lblTipo);
			ventana.add(txtTipo);
			ventana.add(lblCantidad);
			ventana.add(txtCantidad);
			ventana.add(lblIVA);
			ventana.add(txtIVA);
			ventana.add(lblPrecioVenta);
			ventana.add(txtPrecioVenta);
			ventana.add(lblCompra);
			ventana.add(txtCompra);
			
			//Listeners de botones
			btnAceptar.addActionListener(this);
			btnLimpiar.addActionListener(this);
			ventana.add(btnAceptar);
			ventana.add(btnLimpiar);	
		}
		@Override
		public void actionPerformed(ActionEvent evento)
		{
			//Limpiar
			if(evento.getSource().equals(btnLimpiar))
			{
				Limpiar();
			}
			//Aceptar
			else if (evento.getSource().equals(btnAceptar))
			{

				//conectar la base de datos
				connection = bd.conectar();
				//Coger los datos del formulario:
				String tipo = txtTipo.getText();
				String cantidad = txtCantidad.getText();
				String iva = txtIVA.getText();
				String venta = txtPrecioVenta.getText();
				String compra = txtCompra.getText();
				
				if(tipo.equals("")|| cantidad.equals("")|| iva.equals("")||venta.equals("")||compra.equals(""))
				{
					lblMensaje.setText("Faltan datos");
					mostrarDialogo();
				}
				else
				{
					// Crear la sentencia del insert
					String sentencia = "INSERT INTO productos VALUES (null, '" ;
					sentencia+=  tipo + "', '" + cantidad + "', '" + iva 
							+"', '" + venta + "', '" + compra + "');";
					System.out.println(sentencia);

					if((bd.AltaProducto(connection, sentencia))==0) 
					{
						//Si todo bien
						lblMensaje.setText("Alta de Producto correcta");
						
					}
					else 
					{
						// si no sale mensaje de error
						lblMensaje.setText("Error en Alta de Producto");
						
					}
				}
				//Desconectamos base de datos
				bd.desconectar();
				mostrarDialogo();
			}
			
		}
		private void mostrarDialogo()
		{
			// ventana del mensaje
			dlgFeedback.setTitle("Nuevo Producto");
			dlgFeedback.setSize(200,150);
			dlgFeedback.setLayout(new FlowLayout());
			dlgFeedback.addWindowListener(this);
			dlgFeedback.add(lblMensaje);
			dlgFeedback.setLocationRelativeTo(null);
			dlgFeedback.setVisible(true);

		}
		private void Limpiar()
		{
			//funcionalidad del botón limpiar
			txtTipo.setText("");
			txtTipo.setText(" ");
			txtCantidad.setText(" ");
			txtIVA.setText(" ");
			txtPrecioVenta.setText(" ");
			txtCompra.setText(" ");
			txtTipo.requestFocus();
		}
		@Override
		public void windowOpened(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosing(WindowEvent e)
		{
			//Cerramos las ventanas
			if(dlgFeedback.isActive())
			{
				dlgFeedback.setVisible(false);
				Limpiar();
			}
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
