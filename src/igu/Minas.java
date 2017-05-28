package igu;
import javax.swing.*;
import java.awt.*;

/**
 * Piece that simbolizes a bomb
 * @author Vtt: linux
 * @version 1.0
 * @see Casilla
 */
public class Minas extends Casilla{
	/**
	 * Create a Minas object 
	 * @param fondo_arriba the color of the piece when it is face up
	 * @param fondo_abajo the color of the piece when it is face down. The color should be the same for all the pieces
	 */
	public Minas(Color fondo_arriba, Color fondo_abajo){
		super(fondo_arriba, fondo_abajo);
	}
	/**
	 * Check if the piece clicked has a flag over or if the game should end for call the explosionFinal method
	 * @param y y position of the piece
	 * @param x x position of the piece
	 * @param tablero matrix with all the pieces, bombs and no bombs
	 * @param imagen_b icon that should shows all the pieces that has not got a flag over itself
	 * @param imagen_bb icon that should all the pieces that has a flag
	 */
	public void explocion(int y, int x, Casilla[][] tablero, Icon imagen_b, Icon imagen_bb){
		if(banderita == true)return;
		else explocionFinal(tablero, imagen_b, imagen_bb);
	}
	/**
	 * Call the bum method for each bomb in the board
	 * @param tablero matrix that contains all the pieces of the game
	 * @param imagen_b icon that should shows all the pieces that has not got a flag over itself
	 * @param imagen_bb icon that should shows all the pieces that has got a flag over itself
	 */
	public void explocionFinal(Casilla[][] tablero, Icon imagen_b, Icon imagen_bb){
		for(int i = 0; i < tablero.length; i++){
			for(int j = 0; j < tablero[i].length; j++){
				if(tablero[i][j] instanceof Minas){
					Minas mina = (Minas) tablero[i][j];
					mina.bum(imagen_b, imagen_bb);
				}
			}
		}
	}
	/**
	 * Change the icon an background color of the bomb piece
	 * @param imagen_b icon that should shows all the pieces that has not got a flag over itself
	 * @param imagen_bb icon that should shows all the pieces that has got a flag over itself
	 */
	public void bum(Icon imagen_b, Icon imagen_bb){
		Toolkit.getDefaultToolkit().beep();
		System.out.println("BOOOM!!!");
		de_frente = true;
		if(super.banderita)
			setIcon(imagen_bb);
		else 
			setIcon(imagen_b);
	}
}
