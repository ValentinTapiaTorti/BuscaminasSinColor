package igu;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
 /**
  * Main class that contains only the main method 
  * @author Vtt: linux
  * @version 1.0
  */
public class Main {
	/**
	 * Main method that creates the game frame and set the theme
	 * @param args
	 * @see Marco 
	 */
	public static void main(String[] args){
		MetalLookAndFeel.setCurrentTheme(new EstiloBuscaminas()); //Establecemos el estilo
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); //Agreagamos el estilo
		} catch (Exception ex) {
			System.out.println("Falló la carga del tema");
	      	System.out.println(ex);
	   	}
		JFrame.setDefaultLookAndFeelDecorated(true); //Permitimos que el frame y el dialog acepten un cambio de estilo
		JDialog.setDefaultLookAndFeelDecorated(true);
		new Marco(12, 12); //Creamos el marco con los parametros alto, ancho
	}
}
