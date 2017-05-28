package igu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class BarraMenu extends JPanel{ //Barra de Herramientas
	private JMenuBar barra;
	private JMenu menu_op;
	private JMenuItem nuevo, cambiar_tamanio, cerrar;
	private PanelPrincipal lienzo_principal; //Lienzo que contiene esta barra.
	
	public BarraMenu(PanelPrincipal lienzo){
		setLayout(new BorderLayout());
		
		this.lienzo_principal = lienzo;
		
		barra = new JMenuBar();
		menu_op = new JMenu("Opciones");
		
		nuevo = new JMenuItem("Nuevo Juego");
		cambiar_tamanio = new JMenuItem("Cambiar Tamaño");
		cerrar = new JMenuItem("Cerrar");
		
		nuevo.addActionListener(new ActionListener(){ //Nuevo juego mata el cronometro, lo coloca en 0 y reinicia solo el tablero
			public void actionPerformed(ActionEvent e){
				if(lienzo_principal.getCrono().getVivo()) //Mata el cronometro
					lienzo_principal.getCrono().kill();
				try { //Espera a que el cronometro termine de morir o de dar la ultima vuelta al bucle while(vivo)
					lienzo_principal.getHiloCrono().join();
				} catch (InterruptedException e1) {}
				lienzo_principal.getCrono().setInicio();//Luego establece el cronometro en 0:0:0:0
				lienzo_principal.getTablero().reiniciar(0); //0 means ok in the confirmDialog
			}
		});
		cambiar_tamanio.addActionListener(new ActionListener(){ //Cambia el tamaño del tablero
			public void actionPerformed(ActionEvent e){
				Marco.redimension(Marco.queMarco(lienzo_principal.getTablero()));
			}
		});
		cerrar.addActionListener(new ActionListener(){ //Cierra el marco y por conciguiente toda la app (EXIT_ON_CLOSE)
			public void actionPerformed(ActionEvent e){
				Marco.queMarco(lienzo_principal.getTablero()).dispose();
			}
		});
		
		menu_op.addSeparator();
		menu_op.add(nuevo);
		menu_op.add(cambiar_tamanio);
		menu_op.add(cerrar);
		
		barra.add(menu_op);
		add(barra, BorderLayout.NORTH);
	}
}
/*class CambiarTamanio extends JFrame{
	private JTextField ancho, largo, minas;
	private JButton aceptar, cancelar;
}*/
