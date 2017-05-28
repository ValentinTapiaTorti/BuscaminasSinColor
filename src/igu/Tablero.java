package igu;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel { //Tablero que contiene las casillas o botones
	private Icon im_bandera, im_bomba, im_bomba_bandera; //Imagenes de la bandera, de la bomba, y de las bombas que fueron adivinadas
	private int alto, ancho, minas; // Tamaño del tablero mas la cantidad de minas en este
	private int cant_banderas;	//Cantidad de banderas maximo = minas y minimo = 0
	private int primer_click = -1; //Posicion (alto, ancho) donde se realizo el primer click, tendría que ser ancho alto
	private int[] primer_click_alrededor = new int[9]; //Las ocho posibles posiciones alrededor del primer click y este incluido
	private Casilla[][] tablero, tablero_aux; //Tablero sin minas inicial, para evitar perder al inicio, y el tablero del resto del juego
	
	public Tablero(int alto, int ancho){
		setLayout(new GridLayout(alto, ancho));
		
		im_bandera = new ImageIcon(Tablero.class.getResource("/Bandera.gif"));
		im_bomba = new ImageIcon(Tablero.class.getResource("/Bomba.gif"));
		im_bomba_bandera = new ImageIcon(Tablero.class.getResource("/BombaBandera.gif"));
		
		tablero_aux = new Casilla[alto][ancho];
		tablero = new Casilla[alto][ancho];		
		cant_banderas = 0;		
		
		iniciarTableroAux();
		calcularMinas(alto, ancho);
	}
	private void iniciarTableroAux(){ //Inicia el tablero con casillas, que es la clase padre de minas y noMinas
		for(int i = 0; i < tablero_aux.length; i++){
			for(int j = 0; j < tablero_aux[i].length; j++){
				tablero_aux[i][j] = new Casilla(new Color(59, 89, 182), new Color(241, 94, 23));
				tablero_aux[i][j].addMouseListener(new AccionInicio(i, j)); //Listener
				add(tablero_aux[i][j]);
			}
		}
	}
	private void calcularMinas(int alto, int ancho){ //Las minas son un 15% del total de casillas
		this.alto = alto;
		this.ancho = ancho;
		minas = Integer.valueOf((15*alto*ancho) / 100); //Las minas son el 15% del total de casillas
	}
	private void crearMinas(int cant_minas){ //Crea las minas controlando que no se repitan minas en la misma posicion
		int[]posiciones = new int[cant_minas];
		//Al inicio las posiciones estan dadas por un número entre 0(arriba a la izquierda) y (alto*ancho)-1 (abajo a la derecha)
		for(int i = 0; i < cant_minas; i++){//Genera cant_minas posiciones donde se localizaran minas.
			int aux_pos = (int)(Math.random() * (alto*ancho));//Genera un número alazar entre 0 y la cantidad de casillas
			if(revisarArray(posiciones, aux_pos)) //Si el valor asignado no esta repetido en el array lo agrega
				posiciones[i] = aux_pos;
			else i--; //Sino vuelve a intentar reduciendo i en uno
		}
		int x, y;		
		for(int i = 0; i < cant_minas; i++){ //Asigna las posiciones en "Cordenada" (alto, ancho)
			if(posiciones[i] == 0){ //Si la posicion fue 0 va arriba a la izquierda
				y = 0;
				x = 0;
			}
			else if(posiciones[i] < ancho){ //Si la posicion es menor al ancho va en la primer fila
				y = 0;
				x = posiciones[i];
			}
			else{ //Sino la Mina se coloca en las cordenadas y,x 0 alto, ancho
				y = posiciones[i] / ancho;
				x = posiciones[i] % ancho;
			}
			tablero[y][x] = new Minas(new Color(59, 89, 182), new Color(241, 94, 23));//Se crea la mina con las coordenadas dadas
		}
	}
	private boolean revisarArray(int[] array, int valor){//Revisa que el valor no este en el array.
		for(int i = 0; i < primer_click_alrededor.length; i++)
			if(primer_click_alrededor[i] == valor) return false; 
		for(int i = 0; i < array.length; i++){
			if(array[i] == valor)
				return false;
		}
		return true;
	}
	private void crearNoMinas(){//Crea las noMinas en el resto del Tablero
		for(int i = 0; i < tablero.length; i++){
			for(int j = 0; j < tablero[i].length; j++){
				if(tablero[i][j]  == null)
					tablero[i][j] = new NoMinas(new Color(59, 89, 182), new Color(241, 94, 23));
			}
		}
	}
	private void agregarCasillas(){//Agrega todas las casillas ya minas al tablero
		for(int i = 0; i < tablero.length; i++){
			for(int j = 0; j < tablero[i].length; j++){
				add(tablero[i][j]);
				tablero[i][j].addMouseListener(new AccionJuego(i, j));
				if(tablero[i][j] instanceof NoMinas){
					NoMinas no_mina = (NoMinas) tablero[i][j];
					no_mina.instanciarNumero(j, i, tablero);
				}		
			}
		}
	}
	private void iniciarJuego(int y, int x){ //Inicia el tablero luego de haberce efectuado el primer click
		Marco.queMarco(Tablero.this).getPanel().startCrono();//Inicia el cronometro
		
		removeAll();//Remueve las fichas anteriores
		tablero_aux = null;//Deseñala el tablero auxiliar para que lo recoga el garabageCollection
		primer_click = (y * ancho) + x; //Nueve posiciones que no pueden tener minas(primer click y alrededores)
		primer_click_alrededor[0] = primer_click - 1 - ancho;
		primer_click_alrededor[1] = primer_click - ancho;
		primer_click_alrededor[2] = primer_click + 1 - ancho;
		primer_click_alrededor[3] = primer_click - 1;
		primer_click_alrededor[4] = primer_click;
		primer_click_alrededor[5] = primer_click + 1;
		primer_click_alrededor[6] = primer_click - 1 + ancho;
		primer_click_alrededor[7] = primer_click + ancho;
		primer_click_alrededor[8] = primer_click + 1 + ancho;		
		
		crearMinas(minas);
		crearNoMinas();
		agregarCasillas();
		//Voltea la casilla del primer Click
		NoMinas no_mina = (NoMinas)tablero[y][x];
		no_mina.voltear(y, x, tablero);
		updateUI();//actualiza el lienzo
		//paint(getGraphics()); //Le da un efecto que me gusta
		fin(tablero);
	}
	private void fin(Casilla[][] tablero){ //Controla si el juego termino por derrota o por victoria, o si no
		for(int i = 0; i < tablero.length; i++){//Si se cliqueo una mina
			for(int j = 0; j < tablero[i].length; j++){
				if(tablero[i][j] instanceof Minas){
					if(tablero[i][j].de_frente) {
						Marco.queMarco(Tablero.this).getPanel().killCrono();//Parar el reloj		
						reiniciar(JOptionPane.showConfirmDialog(null, "Perdiste ;(\nQuieres empezar de nuevo?", "Derrota", 0, 1, null));
						return;
					}
				}
			}
		}
		for(int i = 0; i < tablero.length; i++){//Si se cliquearon todas las noMinas
			for(int j = 0; j < tablero[i].length; j++){
				if(tablero[i][j] instanceof NoMinas){
					if(!tablero[i][j].de_frente)
						return;
				}
			}
		}
		Marco.queMarco(Tablero.this).getPanel().killCrono();//Parar el reloj	
		reiniciar(JOptionPane.showConfirmDialog(null, "Felicidades Ganaste!!!!\nQuieres empezar de nuevo?", "Victoria", 0, 1, null));
	}
	public void reiniciar(int op){//0 es igual a reiniciar el tablero y un oa salir		
		if(op == 0){
			removeAll();
			for(int i = 0; i < tablero.length; i++){
				for(int j = 0; j < tablero[i].length; j++)
					tablero[i][j] = null;
			}				
			tablero_aux = new Casilla[alto][ancho];
			tablero = new Casilla[alto][ancho];		
			cant_banderas = 0;		
			
			iniciarTableroAux();
			calcularMinas(alto, ancho);
			updateUI();
		}
		else System.exit(0);//Marco.queMarco(Tablero.this).dispose(); //Se puede usar Marco.marcos.get(0).dispose();
	}
	private class Accion extends MouseAdapter{//Clase padre oyente
		int x, y;
		public Accion(int y, int x){  //Recibe la posicion del boton
			this.x = x;
			this.y = y;
		}
	}
	private class AccionInicio extends Accion{//Accion inicio es simple cualquier click lleva a iniciar juego
		public AccionInicio(int y, int x) {
			super(y, x);
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == 3) return;
			iniciarJuego(y,x);
		}		
	}
	private class AccionJuego extends Accion {//Es el cerebro del juego
		public AccionJuego(int y, int x) {
			super(y, x);
		}
		public void mouseClicked(MouseEvent e) {		
			if(tablero[y][x].de_frente){//Si se clickea una ficha de frente 
				NoMinas no_minas = (NoMinas)tablero[y][x];				
				if(no_minas.rodeadoBanderas(y, x, tablero)){//Si se colocaron las banderas correspondientes alrededor de la ficha
					if(e.getClickCount() == 2 || e.getButton() == 2)//Si se hizo doble click o clik medio se desbloquean las 3, 5, 8 fichas circundantes
						no_minas.voltearOcho(y, x, tablero, im_bomba, im_bomba_bandera);
						//String aux = no_minas.getNumero();no_minas.cambiaNumero("");no_minas.cambiaNumero(aux);
				}
				fin(tablero);//Comprueba si se acabo el juego
				return;
			}
			
			if(tablero[y][x].banderita) return; //Si la ficha clickeada tiene bandera no hace nada
			if(e.getButton() == 3) return; //Si el click es el derecho no hace nada
			
			if(tablero[y][x] instanceof Minas){//Si es una mina explota
				Minas mina = (Minas) tablero[y][x];
				mina.explocionFinal(tablero, im_bomba, im_bomba_bandera);
				fin(tablero);
			}
			
			else if(tablero[y][x] instanceof NoMinas){//Si no es una mina la voltea
				NoMinas no_mina = (NoMinas) tablero[y][x];
				no_mina.voltear(y, x, tablero);
				fin(tablero);
			}
		}	
		public void mousePressed(MouseEvent e) {
			if(tablero[y][x] instanceof Minas) System.out.println("Es Mina");
			if(e.getButton() == 3){//Si el click fue derecho 
				if(tablero[y][x].de_frente) return; //Si esta defrente nada
				if(!tablero[y][x].banderita && cant_banderas < minas){ //Si se puede colocar una bandera mas
					tablero[y][x].setIcon(im_bandera);
					tablero[y][x].banderita = true;
					cant_banderas ++;
				}
				else if(tablero[y][x].banderita && cant_banderas > 0){ //Si tiene una bandera, entonces se saca
					tablero[y][x].setIcon(null);
					tablero[y][x].banderita = false;
					cant_banderas --;
				}
				else if(cant_banderas == minas) //Si no se pueden agregar mas banderas
					JOptionPane.showMessageDialog(null, "Lo siento ya marcaste todas las banderas");
				return ;
			}
		}	
	}
}


















