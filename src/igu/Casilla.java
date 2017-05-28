package igu;

import java.awt.*;
import javax.swing.*;
/**
 * Father class that represent a piece of the board 
 * @author Vtt: Windows
 * @version 1.0
 */
public class Casilla extends JButton{ //Molde de un casilla mina o no mina
	public boolean de_frente = false; //Si la casilla ha sido ya volteada
	public boolean banderita = false; //Si la casilla no ha sido volteada pero si marcada
	public Color fondo_arriba, fondo_abajo; // El color de frente de la casilla y el color de la espalda de la casilla
	/**
	 * creates a piece that is neither a bomb nor a non-bomb
	 * @param fondo_arriba background color of the piece face up
	 * @param fondo_abajo background color of the piece face down
	 */
	public Casilla(Color fondo_arriba, Color fondo_abajo){
		super();
		setFont(new Font("", Font.BOLD, 10));
		setFocusPainted(false); //No colorea el contorno del boton si esta seleccionado  
		setBackground(fondo_arriba);
		setForeground(Color.WHITE);
		this.fondo_abajo = fondo_abajo;
		this.fondo_arriba = fondo_arriba;
		setPreferredSize(new Dimension(45, 45));
	}
}
