package app;

import javax.swing.*;

import vista.PanelCentral;
import vista.PanelOeste;
import mapa.Camino;
import mapa.Mapa;
import java.awt.*;

public class Inicio {
	private static JFrame frame;
	public static PanelCentral panelCentral;
	public static PanelOeste panelOeste;
	public static Mapa mapa = new Mapa();
	public static Camino camino = new Camino(mapa);
		
	private static void show() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
	    
	private static void components() {
		Container c = frame.getContentPane();
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		c.add(new PanelCentral(), BorderLayout.CENTER);
		c.add(new PanelOeste(), BorderLayout.WEST);
	} 
	
	public static void cerrarVentana() {
		frame.dispose();
		System.exit(0);
	}

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	frame = new JFrame("Progra III");
            	components();
                show();
            }
        });
    }
}
