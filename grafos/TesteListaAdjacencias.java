package grafos;

public class TesteListaAdjacencias {
	
	public static void main(String[] args) {
		ListaAdjacencia grafo = new ListaAdjacencia(5,false);
		
	    grafo.AdicionarAresta(0, 1, 1);
	    grafo.AdicionarAresta(0, 3, 3);
	    grafo.AdicionarAresta(0, 4, 10);
	    grafo.AdicionarAresta(1, 2, 5);
	    grafo.AdicionarAresta(2, 4, 1);
	    grafo.AdicionarAresta(3, 2, 2);
	    grafo.AdicionarAresta(3, 4, 6);


		grafo.mostrarListaAdjacencias();		
		
		//System.out.println("*******************************************");
		
		//grafo.removerVertice(2);
		//System.out.println("Após a remoção do vértice 2: ");
		//grafo.mostrarListaAdjacencias();
		
		System.out.println("*******************************************");
		
		//grafo.AdjacenciasDoVertice(1);
		
		System.out.print("Caminho mais curto entre dois vértices: " + grafo.dijkstra(0, 4));
		System.out.println();
		
		//grafo.verificarConexo();
		//grafo.verificarCompleto();
		//grafo.verificarEuleriano();
		
		//grafo.verificarHamiltoniano();
		
		
	}
	

}
