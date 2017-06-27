package mapa;

import grafico.Punto;

public class Nodo {
	private Punto ubicacion;	// Coordenadas (X,Y) del Nodo
	private Nodo predecesor;	// Nodo Previo
	private double acumulado;	// Cantidad del Nodos al "Origen" (incluyendo Nodo)
	private double distancia;	// Distancia del Nodo al "Destino".
	private double densidad;	// Valor de la Superficie que ocupa el Nodo != 3
	private boolean visitado;	// Nodo ha sido utilizado
	
	public Nodo(Punto ubicacion){
		this.ubicacion = ubicacion;
	}
	
	public Nodo(Punto ubicacion, Nodo predecesor, double acumulado, double distancia, int densidad, boolean visitado){
		this.ubicacion = ubicacion;
		this.predecesor = predecesor;
		this.acumulado = acumulado;
		this.distancia = distancia;
		this.densidad = densidad;
		this.visitado = visitado;
	}

	// Devuelve las Coordenadas (X,Y) del Nodo
	public Punto getUbicacion() {
		return ubicacion;
	}

	// Devuelve el Nodo Predecesor de un Nodo.
	public Nodo getPredecesor() {
		return predecesor;
	}
	
	// Establece el Nodo Predecesor de un Nodo.
	public void setPredecesor(Nodo predecesor) {
		this.predecesor = predecesor;
	}
	
	// Devuelve la Cantidad del Nodos al "Origen" (incluyendo Nodo).
	public double getAcumulado() {
		return acumulado;
	}
	
	// Establece la Cantidad del Nodos al "Origen" (incluyendo Nodo).
	public void setAcumulado(double acumulado) {
		this.acumulado = acumulado;
	}
	
	// Devuelve la Distancia del Nodo al "Destino".
	public double getDistancia() {
		return distancia;
	}

	// Establece la Distancia del Nodo al "Destino".
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	// Devuelve Verdadero si el Nodo fué Visitado.
	public boolean isVisitado() {
		return visitado;
	}
	
	// Establece que el Nodo fué visitado.
	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}
	
	// Devuelve el la cantidad real de Nodos recorridos + la cantidad teorica de Nodos por Recorrer (Peso)
	public double getPeso() {
		return acumulado+distancia;
	}

	// Obtiene la Densidad de un Nodo
	public double getDensidad() {
		return densidad;
	}

	// Establece la Densidad de un Nodo
	public void setDensidad(double densidad) {
		this.densidad = densidad;
	}
}

