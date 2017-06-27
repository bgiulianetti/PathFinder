package mapa;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import app.Inicio;
import grafico.DibujoTDA;
import grafico.PoliLinea;
import grafico.Punto;

public class Camino {
	private Mapa mapa;
	private Nodo origen;
	private Nodo destino;
	private Comparator<Nodo> comparator = new Comparador();
	private PriorityQueue<Nodo> candidatos;
	private Nodo[][] matrizNodos;
	
	public Camino(Mapa mapa){
		this.mapa = mapa;
	}
	public Mapa getMapa() {
		return mapa;
	}
	public Nodo getOrigen() {
		return origen;
	}
	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}
	public void setOrigen(Punto punto) {
		this.origen = new Nodo(punto);
	}
	public Nodo getDestino() {
		return destino;
	}
	public void setDestino(Nodo destino) {
		this.destino = destino;
	}
	public void setDestino(Punto punto) {
		this.destino = new Nodo(punto);
	}
	
	// Muestra el camino de Nodos recorridos y el total de Nodos por pantalla.
	
	public DibujoTDA buscarCamino() throws Exception{
		List<Punto> cmc = new ArrayList<Punto>();
		Stack<Nodo> solucion = new Stack<Nodo>();
	
		inicializarMatriz();
		
		Nodo seleccionado = origen;
		seleccionado.setVisitado(true);
		seleccionado.setDistancia(calcularDistancia(destino));
		
		expandir(seleccionado);
		
		while ((seleccionado != destino) && (caminoPosible())) {
			seleccionado = candidatos.poll();
			expandir(seleccionado);
		}
		
		if (seleccionado == destino) {
			while (seleccionado.getPredecesor() != null) {
				cmc.add(seleccionado.getUbicacion());
				
				solucion.push(seleccionado);
				seleccionado = seleccionado.getPredecesor();
			}
			solucion.push(seleccionado);
		}
		
		mostrarSolucionPorConsola(solucion);
		
		return dibujarCamino(cmc);
	}

	// Inicializa "Lista de Candidatos"y "Matriz de Nodos".
	// Carga "Matriz de Nodos" con los dato provenientes del mapa.
	// Inicializa "Origen" y "Destino", y se cargan con las coordenadas del mapa.
	private void inicializarMatriz() {
		candidatos = new PriorityQueue<Nodo>(comparator);
		matrizNodos = new Nodo[DibujoTDA.LARGO][DibujoTDA.ALTO];
		
		for (int i = 0; i < DibujoTDA.ALTO; i++) 
			for (int j = 0; j < DibujoTDA.LARGO; j++) 
				matrizNodos[i][j] = new Nodo(new Punto(j, i), null, 0, 0, mapa.getDensidad(j, i), false);
		
		origen = matrizNodos[origen.getUbicacion().getY()][origen.getUbicacion().getX()];
		destino = matrizNodos[destino.getUbicacion().getY()][destino.getUbicacion().getX()];
	}
	
	// Calcula la distancia entre un Nodo y "Destino".
	private double calcularDistancia(Nodo seleccionado) {
		return (Math.abs(seleccionado.getUbicacion().getX()-destino.getUbicacion().getX())
				+Math.abs(seleccionado.getUbicacion().getY()-destino.getUbicacion().getY()));
	}
	
	
	private void expandir(Nodo seleccionado) throws Exception {
		try{
			Nodo predecesor = seleccionado;
			Nodo e;
			
			//Ortogonales
			if (seleccionado.getUbicacion().getX()+1 < mapa.getGrilla().length) {
				e = matrizNodos[seleccionado.getUbicacion().getY()][seleccionado.getUbicacion().getX()+1];
				if ((!e.isVisitado()) && (e.getDensidad()!=3)) 
					expandirNodo(e, predecesor, false);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+1 < e.getAcumulado()))
					expandirNodo(e, predecesor, false);
			}
			
			if (seleccionado.getUbicacion().getX()-1 >= 0) {
				e = matrizNodos[seleccionado.getUbicacion().getY()][seleccionado.getUbicacion().getX()-1];
				if ((!e.isVisitado()) && (e.getDensidad()!=3)) 
					expandirNodo(e, predecesor, false);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+1 < e.getAcumulado()))
					expandirNodo(e, predecesor, false);
			}
			
			if (seleccionado.getUbicacion().getY()+1 < mapa.getGrilla().length) {
				e = matrizNodos[seleccionado.getUbicacion().getY()+1][seleccionado.getUbicacion().getX()];
				if ((!e.isVisitado()) && (e.getDensidad()!=3)) 
					expandirNodo(e, predecesor, false);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+1 < e.getAcumulado()))
					expandirNodo(e, predecesor, false);
			}
			
			if (seleccionado.getUbicacion().getY()-1 >= 0) {
				e = matrizNodos[seleccionado.getUbicacion().getY()-1][seleccionado.getUbicacion().getX()];
				if ((!e.isVisitado()) && (e.getDensidad()!=3)) 
					expandirNodo(e, predecesor, false);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+1 < e.getAcumulado()))
					expandirNodo(e, predecesor, false);
			}
			
			//Diagonales
			if ((seleccionado.getUbicacion().getY()-1 >= 0) && (seleccionado.getUbicacion().getX()-1 >= 0)) {
				e = matrizNodos[seleccionado.getUbicacion().getY()-1][seleccionado.getUbicacion().getX()-1];
				if ((((!e.isVisitado()) && (e.getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()-1][seleccionado.getUbicacion().getX()].getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()][seleccionado.getUbicacion().getX()-1].getDensidad()!=3))
					expandirNodo(e, predecesor, true);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+Math.sqrt(2) < e.getAcumulado()))
					expandirNodo(e, predecesor, true);
			}
			
			if ((seleccionado.getUbicacion().getY()+1 < mapa.getGrilla().length) && (seleccionado.getUbicacion().getX()+1 < mapa.getGrilla().length)) {
				e = matrizNodos[seleccionado.getUbicacion().getY()+1][seleccionado.getUbicacion().getX()+1];
				if ((((!e.isVisitado()) && (e.getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()+1][seleccionado.getUbicacion().getX()].getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()][seleccionado.getUbicacion().getX()+1].getDensidad()!=3))
					expandirNodo(e, predecesor, true);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+Math.sqrt(2) < e.getAcumulado()))
					expandirNodo(e, predecesor, true);
			}
			
			if ((seleccionado.getUbicacion().getY()+1 < mapa.getGrilla().length) && (seleccionado.getUbicacion().getX()-1 >= 0)) {
				e = matrizNodos[seleccionado.getUbicacion().getY()+1][seleccionado.getUbicacion().getX()-1];
				if ((((!e.isVisitado()) && (e.getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()][seleccionado.getUbicacion().getX()-1].getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()+1][seleccionado.getUbicacion().getX()].getDensidad()!=3))
					expandirNodo(e, predecesor, true);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+Math.sqrt(2) < e.getAcumulado()))
					expandirNodo(e, predecesor, true);
			}
			
			if ((seleccionado.getUbicacion().getY()-1 >= 0) && (seleccionado.getUbicacion().getX()+1 < mapa.getGrilla().length)) {
				e = matrizNodos[seleccionado.getUbicacion().getY()-1][seleccionado.getUbicacion().getX()+1];
				if ((((!e.isVisitado()) && (e.getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()][seleccionado.getUbicacion().getX()+1].getDensidad()!=3)) 
						&& (matrizNodos[seleccionado.getUbicacion().getY()-1][seleccionado.getUbicacion().getX()].getDensidad()!=3))
					expandirNodo(e, predecesor, true);
				if ((e.isVisitado()) && (predecesor.getAcumulado()+e.getDensidad()+Math.sqrt(2) < e.getAcumulado()))
					expandirNodo(e, predecesor, true);
			}
		}
			catch(Exception e) {
				throw new Exception(e.getMessage());
			}
		
	}
	
	// Genera el Nodo con los atributos: Predecesor, Visitado, Acumulado y Distancia.
	private void expandirNodo(Nodo nodo, Nodo predecesor, boolean esDiagonal) {
		nodo.setPredecesor(predecesor);
		nodo.setVisitado(true);
		if (!esDiagonal)
			nodo.setAcumulado(predecesor.getAcumulado()+nodo.getDensidad()+1);
		else
			nodo.setAcumulado(predecesor.getAcumulado()+nodo.getDensidad()+Math.sqrt(2));
		nodo.setDistancia(calcularDistancia(nodo));
		candidatos.add(nodo);
	}
	
	private boolean caminoPosible() {
		if (candidatos.size()>0)
			return true;
		else
			return false;
	}
	
	// Imprime por pantalla el camino de "Origen" a "Destino" en formato de coordenadas (X,Y).
	// Calcula el total de Nodos recorridos desde "Origen" a "Destino".
	public void mostrarSolucionPorConsola(Stack<Nodo> solucion) {
		double acumulado = 0;
		
		System.out.println("PUNTOS QUE CONFORMAN LA SOLUCIÓN");
		System.out.println("---------------------------------------");
		
		while (solucion.size() > 0) {
			Nodo cabecera = solucion.pop();
			System.out.println("(" + cabecera.getUbicacion().getY() + "," + cabecera.getUbicacion().getX() + ")");
			acumulado = cabecera.getAcumulado();
		}
		
		System.out.println("---------------------------------------");
		System.out.println("TOTAL ACUMULADO: " + acumulado);
		System.out.println("***************************************");
		
	}
	
	private DibujoTDA dibujarCamino(List<Punto> cmc){
		int[][] xy = new int[2][cmc.size()];
		int index = 0;
		for(Punto p : cmc){
			xy[0][index] = p.getX();
			xy[1][index] = p.getY();
			index++;
		}
		Inicio.camino = new Camino(Inicio.mapa);
		return new PoliLinea(xy[0],xy[1],Color.red);
	}
}
