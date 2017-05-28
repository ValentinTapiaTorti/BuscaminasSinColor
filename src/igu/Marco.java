package igu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class his in charge to create the JFrame and contains the game's board container
 * @author Vtt: linux
 * @version 1.0
 * @see JFrame
 */
public class Marco extends JFrame {
	private PanelPrincipal lienzo; //Lienzo que contiene casillas, cronometro, barra de herramientas, etc.
	public static ArrayList<Marco> marcos = new ArrayList<Marco>(); //Array Que contiene todos los marcos creados y me permite
																	//Acceder al que esta ahora justo en juego 
	/**
	 * Create the JFrame and initializes the board container.
	 * @param alto defines the height of the board
	 * @param ancho defines the width of the board
	 * @see PanelPrincipal
	 */
	public Marco(int alto, int ancho){
		setTitle("Buscaminas");
		//setSize();
		
		lienzo = new PanelPrincipal(alto, ancho);
		add(lienzo);
		setMinimumSize(new Dimension(400, 200)); //Tamaño minimo para que el cronometro este en pantalla
		pack();
		//setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);
		//_______CENTRA LA VENTANA A LA MITAD DE LA PANTALLA
		Dimension ventana_dim = getSize();
		Dimension pantalla_dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(pantalla_dim.width/2-ventana_dim.width/2, pantalla_dim.height/2-ventana_dim.height/2);
		
		marcos.add(this);
	}
	/**
	 * Return the object that contain the board
	 * @return the board's container object
	 * @see PanelPrincipal
	 */
	public PanelPrincipal getPanel(){ //Retorna el lienzo que contiene casillas, cronometro, barra de herramientas, etc.
		return lienzo;
	}
	/**
	 * Return the Marco object by finding the Tablero object that is a variable of this object
	 * @param tablero object that belong to Marco found
	 * @return The Marco object that contains the tablero object passed as parameter, or null if there is
	 * no object with the tablero variable
	 * @see Tablero
	 */
	public static Marco queMarco(Tablero tablero){ //Busca un marco especifico dentro de todos los ya creados
		Iterator<Marco> iterador = marcos.iterator();
		while(iterador.hasNext()){
			Marco marco = iterador.next();
			if(marco.lienzo.getTablero().equals(tablero)) return marco;
		}
		return null;
	}
	/**
	 * Close the Marco object and replace it for other with a different size
	 * @param marco Marco object that will be replaced
	 */
	public static void redimension(Marco marco){//Redimensiona el tablero
		marco.getPanel().pauseCrono();		
		String alto = JOptionPane.showInputDialog("Introduce alto: ");
		if(alto == null || alto.equals("")){ //Si se presiono enter, aceptar vacio o cancelar no se redimensiona
			marco.getPanel().endPauseCrono();
			return;
		} 
		for(int i = 0; i < alto.length(); i++){ //Si no es un número ingresado no se redimensiona
			marco.getPanel().endPauseCrono();
			if(!Character.isDigit(alto.charAt(i))) return;
		}
		
		String ancho = JOptionPane.showInputDialog("Introduce ancho: ");
		if(ancho == null || ancho.equals("")){ //Si se presiono enter, aceptar vacio o cancelar no se redimensiona
			marco.getPanel().endPauseCrono();
			return;
		}
		for(int i = 0; i < ancho.length(); i++){ //Si no es un número ingresado no se redimensiona
			marco.getPanel().endPauseCrono();
			if(!Character.isDigit(ancho.charAt(i))) return;
		}
		
		marco.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		marco.dispose();//Cierra el marco pero no el programa entero
		marcos.clear();//Borra todos los marcos del array salvo el que agrega en la siguiente linea
		
		//Establece limites para el tamaño maximo y minimo del tablero
		if(Integer.valueOf(alto) > 20) alto = "20";
		else if(Integer.valueOf(alto) < 4) alto = "4";
		if(Integer.valueOf(ancho) > 35) ancho = "35";
		else if(Integer.valueOf(ancho) < 4) ancho = "4";
		marco = new Marco(Integer.valueOf(alto), Integer.valueOf(ancho)); //Crea un nuevo marco de juego
	}
}
