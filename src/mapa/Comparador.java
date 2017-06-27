package mapa;

import java.util.Comparator;

public class Comparador implements Comparator<Nodo> {

	// Compara dos Nodos e indica cuál es el de menor Peso.
	public int compare(Nodo x, Nodo y) {
		if (x.getPeso() < y.getPeso())
            return -1;
        
        if (x.getPeso() > y.getPeso())
            return 1;
        
        return 0;
	}


}
