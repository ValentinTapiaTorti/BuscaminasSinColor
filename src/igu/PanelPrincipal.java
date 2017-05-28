package igu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelPrincipal extends JPanel {
	private Tablero tablero; //Tablero que contiene las casillas
	private BarraMenu barra; //Barra de menu
	private Cronometro crono; //Lienzo del reloj
	private Thread hilo_crono; //Hilo que maneja el reloj
	
	public PanelPrincipal(int alto, int ancho){ //Crea el Lienzo
		setLayout(new BorderLayout());
		
		barra = new BarraMenu(this);
		add(barra, BorderLayout.NORTH);
		
		crearMinas(alto, ancho);		
		add(tablero, BorderLayout.CENTER);	
		
		crono = new Cronometro();
		add(crono, BorderLayout.SOUTH);
	}
	public void startCrono(){ //Inicia el cronometro, y el hilo que cuenta el tiempo
		crono = new Cronometro();
		add(crono, BorderLayout.SOUTH);
		hilo_crono = new Thread(crono);
		hilo_crono.start();
	}
	public void killCrono(){//Mata el cronometro
		Thread hilo = new Thread(new Runnable(){ //Hilo que duerme 90 milesegundos, tiempo suficiente para que el JOptionPane salga
			public void run() { 				//Y luego se pare, de lo contrario La hora del reloj desaparece
				try {
					Thread.sleep(90);
					if(crono.getVivo()) 
						crono.kill();
					Thread.currentThread().join(); //El juego espere a que termine el hilo de detenido
				} catch (InterruptedException e) {}
			}
		});
		hilo.start();
	}
	public void pauseCrono(){ //Detiene el cronometro
		Thread hilo = new Thread(new Runnable(){
			public void run(){
				try {
					Thread.sleep(90);
					if(!crono.getPause())
						crono.pause();
					Thread.currentThread().join();
				} catch (InterruptedException e) {}
			}
		});
		hilo.start();
	}
	public void endPauseCrono(){ //Continua el funcionamiento del cronometro
		Thread hilo = new Thread(new Runnable(){
			public void run(){
				try{
					Thread.sleep(90);
					if(crono.getPause())
						crono.endPause();
					Thread.currentThread().join();
				}catch(InterruptedException e){}
			}
		});
		hilo.start();
	}
	public Tablero getTablero(){
		return tablero;
	}
	public Cronometro getCrono(){
		return crono;
	}
	public Thread getHiloCrono(){
		return hilo_crono;
	}
	private void crearMinas(int alto, int ancho){ //Crea el tablero  de juego
		tablero = new Tablero(alto, ancho); 		
	}
	public void reiniciarMinas(int alto, int ancho){ //Reinicia el tablero, sin crear otro marco, con lo poco eficiente que eso sería
		tablero = new Tablero(alto, ancho);
	}	
}












