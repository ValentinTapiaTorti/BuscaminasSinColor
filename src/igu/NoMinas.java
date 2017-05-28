package igu;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * 
 * @author Vtt: linux
 * @version 1.0
 * @see Casilla
 */
public class NoMinas extends Casilla{
	private String numero; //Show the number of bombs in the eight piece around
	
	/**
	 * Create a normarl piece
	 * @param fondo_arriba background color of the piece when it is face up
	 * @param fondo_abajo background color of the piece whem it is face down
	 */
	public NoMinas(Color fondo_arriba, Color fondo_abajo){
		super(fondo_arriba, fondo_abajo);
	}
	/**
	 * Change the numero value of the piece
	 * @param valor the new value of the numero variable
	 */
	public void cambiaNumero(String valor){ //Cambia el valor de una casilla
		if("12345678".contains(valor))
			numero = valor;
	}
	/**
	 * Return the value of the numero variable
	 * @return a string with the value of the numero variable
	 */
	public String getNumero(){
		return numero;
	}
	/**
	 * Count the number of bombs around the piece, for set the numero value
	 * @param x x position of the piece in the matrix
	 * @param y y position of the piece in the matrix
	 * @param tablero matrix that contains all the pieces of the game
	 */
	public void instanciarNumero(int x, int y, Casilla[][] tablero){ //Calcula el número de una casilla basandose en las minas que tiene alrededor
		int total = 0;
		
		if(x == 0 && y == 0){//Casilla esquina superior izquierda. (Solo tres casillas)
			if(tablero[y][x+1] instanceof Minas)
				total++;
			if(tablero[y+1][x] instanceof Minas)
				total++;
			if(tablero[y+1][x+1] instanceof Minas)
				total++;
		}
		else if(x == 0 && y == tablero.length-1){//Casilla esquina inferior izquierda. (Solo tres casillas)
			if(tablero[y][x+1] instanceof Minas)
				total++;
			if(tablero[y-1][x] instanceof Minas)
				total++;
			if(tablero[y-1][x+1] instanceof Minas)
				total++;
		}
		else if(x == tablero[0].length-1 && y == tablero.length-1){//Casilla esquina inferior derecha. (Solo tres casillas)
			if(tablero[y-1][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x] instanceof Minas)
				total++;
			if(tablero[y][x-1] instanceof Minas)
				total++;
		}
		else if(x == tablero[0].length-1 && y == 0){//Casilla esquina superior derecha. (Solo tres casillas)
			if(tablero[y][x-1] instanceof Minas)
				total++;
			if(tablero[y+1][x] instanceof Minas)
				total++;
			if(tablero[y+1][x-1] instanceof Minas)
				total++;
		}
		else if(x == 0){//Casillas del costado izquierdo. (Solo cinco casillas)
			if(tablero[y-1][x] instanceof Minas)
				total++;
			if(tablero[y-1][x+1] instanceof Minas)
				total++;
			if(tablero[y][x+1] instanceof Minas)
				total++;
			if(tablero[y+1][x+1] instanceof Minas)
				total++;
			if(tablero[y+1][x] instanceof Minas)
				total++;		
		}
		else if(y == tablero.length-1){//Casillas del costado inferior. (Solo cinco casillas)
			if(tablero[y][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x] instanceof Minas)
				total++;
			if(tablero[y-1][x+1] instanceof Minas)
				total++;
			if(tablero[y][x+1] instanceof Minas)
				total++;
		}
		else if(x == tablero[0].length-1){//Casillas del costado derecho. (Solo cinco casillas)
			if(tablero[y+1][x] instanceof Minas)
				total++;
			if(tablero[y+1][x-1] instanceof Minas)
				total++;
			if(tablero[y][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x] instanceof Minas)
				total++;
		}
		else if(y == 0){//Casillas del costado superior. (Solo cinco casillas)
			if(tablero[y][x-1] instanceof Minas)
				total++;
			if(tablero[y+1][x-1] instanceof Minas)
				total++;
			if(tablero[y+1][x] instanceof Minas)
				total++;
			if(tablero[y+1][x+1] instanceof Minas)
				total++;
			if(tablero[y][x+1] instanceof Minas)
				total++;
		}
		else if(x > 0 && x < tablero[0].length-1 && y > 0 && y < tablero.length-1){ //Casillas del CORAZON DEL TABLERO (8 casillas)
			if(tablero[y+1][x-1] instanceof Minas)
				total++;
			if(tablero[y][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x-1] instanceof Minas)
				total++;
			if(tablero[y-1][x] instanceof Minas)
				total++;
			if(tablero[y-1][x+1] instanceof Minas)
				total++;
			if(tablero[y][x+1] instanceof Minas)
				total++;
			if(tablero[y+1][x+1] instanceof Minas)
				total++;
			if(tablero[y+1][x] instanceof Minas)
				total++; 			
		}
		numero = String.valueOf(total);
		if(numero.contains("0")) numero = "";
	}
	/**
	 * Face up a piece, if its numero value is equal 0, it also face up the pieces around it
	 * @param y y position of the piece in the matrix
	 * @param x x position of the piece in the matrix
	 * @param tablero matrix that contains all the pieces of the game
	 */
	public void voltear(int y, int x, Casilla[][] tablero){
		if(banderita == true) return; //Si tiene la bandera no se voltea
		if(super.de_frente == true) return; //Si esta de frente no se coloca de frente
		setBackground(super.fondo_abajo);
		setText(numero);
		super.de_frente = true;
		if(numero.equals("")){//Si el numero es 0 empieza a voltear recursivamente los otros 0 y los alrededores de estos
			if(x == 0 && y == 0){//Casilla esquina superior izquierda.
				if(tablero[y][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x+1];
					no_minas.voltear(y, x+1, tablero);
				}									
				if(tablero[y+1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x];
					no_minas.voltear(y+1, x, tablero);
				}					
				if(tablero[y+1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
					no_minas.voltear(y+1, x+1, tablero);
				}
			}
			else if(x == 0 && y == tablero.length-1){//Casilla esquina inferior izquierda.
				if(tablero[y][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x+1];
					no_minas.voltear(y, x+1, tablero);
				}
				if(tablero[y-1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x];
					no_minas.voltear(y-1, x, tablero);
				}
				if(tablero[y-1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
					no_minas.voltear(y-1, x+1, tablero);
				}
			}
			else if(x == tablero[0].length-1 && y == tablero.length-1){//Casilla esquina inferior derecha.
				if(tablero[y-1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
					no_minas.voltear(y-1, x-1, tablero);
				}
				if(tablero[y-1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x];
					no_minas.voltear(y-1, x, tablero);
				}
				if(tablero[y][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x-1];
					no_minas.voltear(y, x-1, tablero);
				}
			}
			else if(x == tablero[0].length-1 && y == 0){//Casilla esquina superior derecha.
				if(tablero[y][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x-1];
					no_minas.voltear(y, x-1, tablero);
				}
				if(tablero[y+1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x];
					no_minas.voltear(y+1, x, tablero);
				}
				if(tablero[y+1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
					no_minas.voltear(y+1, x-1, tablero);
				}
			}
			else if(x == 0){//Casillas del costado izquierdo.
				if(tablero[y-1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x];
					no_minas.voltear(y-1, x, tablero);
				}
				if(tablero[y-1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
					no_minas.voltear(y-1, x+1, tablero);
				}
				if(tablero[y][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x+1];
					no_minas.voltear(y, x+1, tablero);
				}
				if(tablero[y+1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
					no_minas.voltear(y+1, x+1, tablero);
				}
				if(tablero[y+1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x];
					no_minas.voltear(y+1, x, tablero);
				}		
			}
			else if(y == tablero.length-1){//Casillas del costado inferior.
				if(tablero[y][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x-1];
					no_minas.voltear(y, x-1, tablero);
				}
				if(tablero[y-1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
					no_minas.voltear(y-1, x-1, tablero);
				}
				if(tablero[y-1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x];
					no_minas.voltear(y-1, x, tablero);
				}
				if(tablero[y-1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
					no_minas.voltear(y-1, x+1, tablero);
				}
				if(tablero[y][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x+1];
					no_minas.voltear(y, x+1, tablero);
				}
			}
			else if(x == tablero[0].length-1){//Casillas del costado derecho.
				if(tablero[y+1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x];
					no_minas.voltear(y+1, x, tablero);
				}
				if(tablero[y+1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
					no_minas.voltear(y+1, x-1, tablero);
				}
				if(tablero[y][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x-1];
					no_minas.voltear(y, x-1, tablero);
				}
				if(tablero[y-1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
					no_minas.voltear(y-1, x-1, tablero);
				}
				if(tablero[y-1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x];
					no_minas.voltear(y-1, x, tablero);
				}
			}
			else if(y == 0){//Casillas del costado superior.
				if(tablero[y][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x-1];
					no_minas.voltear(y, x-1, tablero);
				}
				if(tablero[y+1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
					no_minas.voltear(y+1, x-1, tablero);
				}
				if(tablero[y+1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x];
					no_minas.voltear(y+1, x, tablero);
				}
				if(tablero[y+1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
					no_minas.voltear(y+1, x+1, tablero);
				}
				if(tablero[y][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x+1];
					no_minas.voltear(y, x+1, tablero);
				}
			}
			else if(x > 0 && x < tablero[0].length-1 && y > 0 && y < tablero.length-1){
				if(tablero[y+1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
					no_minas.voltear(y+1, x-1, tablero);
				}
				if(tablero[y][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x-1];
					no_minas.voltear(y, x-1, tablero);
				}
				if(tablero[y-1][x-1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
					no_minas.voltear(y-1, x-1, tablero);
				}
				if(tablero[y-1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x];
					no_minas.voltear(y-1, x, tablero);
				}
				if(tablero[y-1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
					no_minas.voltear(y-1, x+1, tablero);
				}
				if(tablero[y][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y][x+1];
					no_minas.voltear(y, x+1, tablero);
				}
				if(tablero[y+1][x+1] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
					no_minas.voltear(y+1, x+1, tablero);
				}
				if(tablero[y+1][x] instanceof NoMinas){
					NoMinas no_minas = (NoMinas)tablero[y+1][x];
					no_minas.voltear(y+1, x, tablero);
				} 			
			}
		}
	}
	/**
	 * If a piece face up is double clicked it method compares if the number of flags over the eight pieces around
	 * is equals to the numero variable of the first piece
	 * @param y y position of the piece in the matrix
	 * @param x x position of the piece in the matrix
	 * @param tablero matrix that contains all the pieces of the game
	 * @return true if the flags around the piece is equals to the number variable, or false if it is not
	 */
	public boolean rodeadoBanderas(int y, int x, Casilla[][] tablero){//Si el numero de banderas alrededor de la casilla es igual al de la casilla true
		int total = 0;
		if(x == 0 && y == 0){//Casilla esquina superior izquierda.
			if(tablero[y][x+1].banderita)
				total++;
			if(tablero[y+1][x].banderita)
				total++;
			if(tablero[y+1][x+1].banderita)
				total++;
		}
		else if(x == 0 && y == tablero.length-1){//Casilla esquina inferior izquierda.
			if(tablero[y][x+1].banderita)
				total++;
			if(tablero[y-1][x].banderita)
				total++;
			if(tablero[y-1][x+1].banderita)
				total++;
		}
		else if(x == tablero[0].length-1 && y == tablero.length-1){//Casilla esquina inferior derecha.
			if(tablero[y-1][x-1].banderita)
				total++;
			if(tablero[y-1][x].banderita)
				total++;
			if(tablero[y][x-1].banderita)
				total++;
		}
		else if(x == tablero[0].length-1 && y == 0){//Casilla esquina superior derecha.
			if(tablero[y][x-1].banderita)
				total++;
			if(tablero[y+1][x].banderita)
				total++;
			if(tablero[y+1][x-1].banderita)
				total++;
		}
		else if(x == 0){//Casillas del costado izquierdo.
			if(tablero[y-1][x].banderita)
				total++;
			if(tablero[y-1][x+1].banderita)
				total++;
			if(tablero[y][x+1].banderita)
				total++;
			if(tablero[y+1][x+1].banderita)
				total++;
			if(tablero[y+1][x].banderita)
				total++;		
		}
		else if(y == tablero.length-1){//Casillas del costado inferior.
			if(tablero[y][x-1].banderita)
				total++;
			if(tablero[y-1][x-1].banderita)
				total++;
			if(tablero[y-1][x].banderita)
				total++;
			if(tablero[y-1][x+1].banderita)
				total++;
			if(tablero[y][x+1].banderita)
				total++;
		}
		else if(x == tablero[0].length-1){//Casillas del costado derecho.
			if(tablero[y+1][x].banderita)
				total++;
			if(tablero[y+1][x-1].banderita)
				total++;
			if(tablero[y][x-1].banderita)
				total++;
			if(tablero[y-1][x-1].banderita)
				total++;
			if(tablero[y-1][x].banderita)
				total++;
		}
		else if(y == 0){//Casillas del costado superior.
			if(tablero[y][x-1].banderita)
				total++;
			if(tablero[y+1][x-1].banderita)
				total++;
			if(tablero[y+1][x].banderita)
				total++;
			if(tablero[y+1][x+1].banderita)
				total++;
			if(tablero[y][x+1].banderita)
				total++;
		}
		else if(x > 0 && x < tablero[0].length-1 && y > 0 && y < tablero.length-1){
			if(tablero[y+1][x-1].banderita)
				total++;
			if(tablero[y][x-1].banderita)
				total++;
			if(tablero[y-1][x-1].banderita)
				total++;
			if(tablero[y-1][x].banderita)
				total++;
			if(tablero[y-1][x+1].banderita)
				total++;
			if(tablero[y][x+1].banderita)
				total++;
			if(tablero[y+1][x+1].banderita)
				total++;
			if(tablero[y+1][x].banderita)
				total++; 			
		}
		if(numero.equals(String.valueOf(total)))
			return true;
		return false;
	}
	/**
	 * If a piece face up is double clicked it method face up all the pieces around the first piece
	 * @param y y position of the piece in the matrix
	 * @param x x position of the piece in the matrix
	 * @param tablero matrix that contains all the pieces of the game
	 * @param imagen_b icon that should shows all the pieces that has not got a flag over itself
	 * @param imagen_bb icon that should all the pieces that has a flag
	 */
	public void voltearOcho(int y, int x, Casilla[][] tablero, Icon imagen_b, Icon imagen_bb){
		/*
		 * Revisa ficha por ficha alrededor de la  casilla[y][x] si es mina la explota, sino la voltea
		 */
		if(x == 0 && y == 0){//Casilla esquina superior izquierda.
			if(tablero[y][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x+1];
				no_minas.voltear(y, x+1, tablero);
			}
			else if(tablero[y][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x+1];
				minas.explocion(y, x+1, tablero, imagen_b, imagen_bb);
			}									
			if(tablero[y+1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x];
				no_minas.voltear(y+1, x, tablero);
			}
			else if(tablero[y+1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x];
				minas.explocion(y+1, x, tablero, imagen_b, imagen_bb);
			}					
			if(tablero[y+1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
				no_minas.voltear(y+1, x+1, tablero);
			}
			else if(tablero[y+1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x+1];
				minas.explocion(y+1, x+1, tablero, imagen_b, imagen_bb);
			}
		}
		else if(x == 0 && y == tablero.length-1){//Casilla esquina inferior izquierda.
			if(tablero[y][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x+1];
				no_minas.voltear(y, x+1, tablero);
			}
			else if(tablero[y][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x+1];
				minas.explocion(y, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x];
				no_minas.voltear(y-1, x, tablero);
			}
			else if(tablero[y-1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x];
				minas.explocion(y-1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
				no_minas.voltear(y-1, x+1, tablero);
			}
			else if(tablero[y-1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x+1];
				minas.explocion(y-1, x+1, tablero, imagen_b, imagen_bb);
			}
		}
		else if(x == tablero[0].length-1 && y == tablero.length-1){//Casilla esquina inferior derecha.
			if(tablero[y-1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
				no_minas.voltear(y-1, x-1, tablero);
			}
			else if(tablero[y-1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x-1];
				minas.explocion(y-1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x];
				no_minas.voltear(y-1, x, tablero);
			}
			else if(tablero[y-1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x];
				minas.explocion(y-1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x-1];
				no_minas.voltear(y, x-1, tablero);
			}
			else if(tablero[y][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x-1];
				minas.explocion(y, x-1, tablero, imagen_b, imagen_bb);
			}
		}
		else if(x == tablero[0].length-1 && y == 0){//Casilla esquina superior derecha.
			if(tablero[y][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x-1];
				no_minas.voltear(y, x-1, tablero);
			}
			else if(tablero[y][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x-1];
				minas.explocion(y, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x];
				no_minas.voltear(y+1, x, tablero);
			}
			else if(tablero[y+1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x];
				minas.explocion(y+1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
				no_minas.voltear(y+1, x-1, tablero);
			}
			else if(tablero[y+1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x-1];
				minas.explocion(y+1, x-1, tablero, imagen_b, imagen_bb);
			}
		}
		else if(x == 0){//Casillas del costado izquierdo.
			if(tablero[y-1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x];
				no_minas.voltear(y-1, x, tablero);
			}
			else if(tablero[y-1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x];
				minas.explocion(y-1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
				no_minas.voltear(y-1, x+1, tablero);
			}
			else if(tablero[y-1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x+1];
				minas.explocion(y-1, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x+1];
				no_minas.voltear(y, x+1, tablero);
			}
			else if(tablero[y][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x+1];
				minas.explocion(y, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
				no_minas.voltear(y+1, x+1, tablero);
			}
			else if(tablero[y+1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x+1];
				minas.explocion(y+1, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x];
				no_minas.voltear(y+1, x, tablero);
			}
			else if(tablero[y+1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x];
				minas.explocion(y+1, x, tablero, imagen_b, imagen_bb);
			}		
		}
		else if(y == tablero.length-1){//Casillas del costado inferior.
			if(tablero[y][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x-1];
				no_minas.voltear(y, x-1, tablero);
			}
			else if(tablero[y][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x-1];
				minas.explocion(y, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
				no_minas.voltear(y-1, x-1, tablero);
			}
			else if(tablero[y-1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x-1];
				minas.explocion(y-1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x];
				no_minas.voltear(y-1, x, tablero);
			}
			else if(tablero[y-1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x];
				minas.explocion(y-1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
				no_minas.voltear(y-1, x+1, tablero);
			}
			else if(tablero[y-1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x+1];
				minas.explocion(y-1, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x+1];
				no_minas.voltear(y, x+1, tablero);
			}
			else if(tablero[y][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x+1];
				minas.explocion(y, x+1, tablero, imagen_b, imagen_bb);
			}
		}
		else if(x == tablero[0].length-1){//Casillas del costado derecho.
			if(tablero[y+1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x];
				no_minas.voltear(y+1, x, tablero);
			}
			else if(tablero[y+1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x];
				minas.explocion(y+1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
				no_minas.voltear(y+1, x-1, tablero);
			}
			else if(tablero[y+1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x-1];
				minas.explocion(y+1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x-1];
				no_minas.voltear(y, x-1, tablero);
			}
			else if(tablero[y][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x-1];
				minas.explocion(y, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
				no_minas.voltear(y-1, x-1, tablero);
			}
			else if(tablero[y-1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x-1];
				minas.explocion(y-1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x];
				no_minas.voltear(y-1, x, tablero);
			}
			else if(tablero[y-1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x];
				minas.explocion(y-1, x, tablero, imagen_b, imagen_bb);
			}
		}
		else if(y == 0){//Casillas del costado superior.
			if(tablero[y][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x-1];
				no_minas.voltear(y, x-1, tablero);
			}
			else if(tablero[y][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x-1];
				minas.explocion(y, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
				no_minas.voltear(y+1, x-1, tablero);
			}
			else if(tablero[y+1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x-1];
				minas.explocion(y+1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x];
				no_minas.voltear(y+1, x, tablero);
			}
			else if(tablero[y+1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x];
				minas.explocion(y+1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
				no_minas.voltear(y+1, x+1, tablero);
			}
			else if(tablero[y+1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x+1];
				minas.explocion(y+1, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x+1];
				no_minas.voltear(y, x+1, tablero);
			}
			else if(tablero[y][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x+1];
				minas.explocion(y, x+1, tablero, imagen_b, imagen_bb);
			}
		}
		else if(x > 0 && x < tablero[0].length-1 && y > 0 && y < tablero.length-1){
			if(tablero[y+1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x-1];
				no_minas.voltear(y+1, x-1, tablero);
			}
			else if(tablero[y+1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x-1];
				minas.explocion(y+1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x-1];
				no_minas.voltear(y, x-1, tablero);
			}
			else if(tablero[y][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x-1];
				minas.explocion(y, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x-1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x-1];
				no_minas.voltear(y-1, x-1, tablero);
			}
			else if(tablero[y-1][x-1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x-1];
				minas.explocion(y-1, x-1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x];
				no_minas.voltear(y-1, x, tablero);
			}
			else if(tablero[y-1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x];
				minas.explocion(y-1, x, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y-1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y-1][x+1];
				no_minas.voltear(y-1, x+1, tablero);
			}
			else if(tablero[y-1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y-1][x+1];
				minas.explocion(y-1, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y][x+1];
				no_minas.voltear(y, x+1, tablero);
			}
			else if(tablero[y][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y][x+1];
				minas.explocion(y, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x+1] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x+1];
				no_minas.voltear(y+1, x+1, tablero);
			}
			else if(tablero[y+1][x+1] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x+1];
				minas.explocion(y+1, x+1, tablero, imagen_b, imagen_bb);
			}
			if(tablero[y+1][x] instanceof NoMinas){
				NoMinas no_minas = (NoMinas)tablero[y+1][x];
				no_minas.voltear(y+1, x, tablero);
			}
			else if(tablero[y+1][x] instanceof Minas){
				Minas minas = (Minas) tablero[y+1][x];
				minas.explocion(y+1, x, tablero, imagen_b, imagen_bb);
			} 			
		}
	}
}
