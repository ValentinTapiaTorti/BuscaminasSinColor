package igu;

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.awt.font.*;

public class Cronometro extends JPanel implements Runnable{ //Lienzo que contiene la hora y se encarga de iniciar, pausar, matar el cronometro
	private byte h, m, s, ms; //Representan horas, minutos, segundos, centena de milesegundos
	private JLabel hora; //Contiene la hora, se uso un JLabel xq es mas eficiente que un printString()
	private DecimalFormat formato; //Le da formato de hora rellenando con 0s
	private boolean vivo, pause; //El cronometro esta funcionando mientras vivo sea verdad, pause detiene el cronometro, pero no finaliza el run()
	
	public Cronometro(){
		setLayout(new BorderLayout()); // BorderLayout para agregar el contador de minas en el futuro
		setPreferredSize(new Dimension(50, 50));
		
		reiniciar(); //Reiniciar inicia las variables del tiempo en 0
		vivo = true; //Permite iniciar el cronometro cuando se llame al metodo run
		pause = false; 
		formato = new DecimalFormat("00");
		hora = new JLabel(formato.format(h) + ":" + formato.format(m) + ":" + formato.format(s) + ":" + ms);
		hora.setHorizontalAlignment(JLabel.CENTER); //Centra el texto del cronometro
		hora.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 35));
		add(hora, BorderLayout.CENTER);
	}
	public void reiniciar(){
		h = 0;
		m = 0;
		s = 0; 
		ms = 0;
	}
	/*public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString((formato.format(h) + ":" + formato.format(m) + ":" + formato.format(s) + ":" + ms), 10, 20);
	}*/
	public void setInicio(){
		hora.setText(formato.format(0) + ":" + formato.format(0) + ":" + formato.format(0) + ":" + 0);
		paint(getGraphics());
	}
	public boolean getVivo(){
		return vivo;
	}
	public void kill(){
		vivo = false;
	}
	public void pause(){
		pause = true;
	}
	public void endPause(){
		pause = false;
	}
	public boolean getPause(){
		return pause;
	}
	public void run(){
		while(vivo){//Suma una ms cada 100 milesimas
			ms += 1;
			if(h >= 99 && m >= 59 && s >= 59 && ms >= 9){//Si el reloj llega a este maximo el tiempo se para
				try {
					Thread.sleep(100);
					hora.setText(formato.format(h) + ":" + formato.format(m) + ":" + formato.format(s) + ":" + ms);
				} catch (InterruptedException e) {}
				vivo = false;
				break;
			}
			if(ms > 9){
				ms = 0;
				s += 1;
			}
			if(s > 59){
				s = 0;
				m += 1;
			}
			if(m > 59){
				m = 0;
				h += 1;
			}
			try {
				Thread.sleep(100);
				hora.setText(formato.format(h) + ":" + formato.format(m) + ":" + formato.format(s) + ":" + ms);
			} catch (InterruptedException e) {}
			while(pause){//Si el reloj esta pausado se mantiene el tiempo pero no sale del bucle while(vivo)
				System.out.print("");;
			}
		}
	}
}
