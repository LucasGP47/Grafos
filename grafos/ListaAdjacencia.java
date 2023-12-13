package grafos;
import java.util.*;

class Aresta{
	int origem;
	int destino;
	int peso;
	
	public Aresta(int origem, int destino, int peso) {
		this.origem = origem;
		this.destino = destino;
		this.peso = peso;
	}
}

public class ListaAdjacencia {
	
	private int nVertices;
	private List<List<Aresta>>adjacencias;
	private boolean direcionando; 
	private int[] anterior;
	
	public ListaAdjacencia(int nVerticies, boolean direcionando) {
		this.nVertices = nVerticies;
		this.direcionando = direcionando;
		this.adjacencias = new ArrayList<>(nVertices);
		for (int i = 0; i < nVertices; i++) {
			adjacencias.add(new ArrayList<>()); 
		}
		 this.anterior = new int[nVertices];
	        Arrays.fill(anterior, -1);
	}
	
	private void adicionarAresta(int u, int v, int peso) {
		Aresta aresta = new Aresta(u, v, peso);
		adjacencias.get(u).add(aresta);
		if (!direcionando){
			Aresta arestaInvertida = new Aresta(v,u,peso);
			adjacencias.get(v).add(arestaInvertida);
		}
	}
	
	public void AdicionarAresta(int u, int v, int peso) {
		adicionarAresta(u,v, peso);
	}
	
	public void mostrarListaAdjacencias() {
		for (int i = 0; i < nVertices; i++) {
			System.out.print("Vértice " + i + ":");
			for (Aresta aresta : adjacencias.get(i)) {
				System.out.print("(" + aresta.destino + ", Peso: " + aresta.peso + ")");
			}
			System.out.println();
		}
	}
	
	public void removerAresta(int u, int v) {
		List<Aresta> arestasU = adjacencias.get(u);
		for (Aresta aresta : arestasU) {
			if (aresta.destino == v) {
				arestasU.remove(aresta);
				break;
			}
		}
		if (!direcionando) {
			List<Aresta> arestasV = adjacencias.get(v);
			for (Aresta aresta : arestasV) {
				if (aresta.destino == u) {
					arestasV.remove(aresta);
					break;
				}
			}
		}
	}
	
	public void removerVertice(int vertice) {	
		for (int i = 0; i < nVertices; i++) {
			if (i != vertice) {
				List<Aresta> arestas = adjacencias.get(i);
	            arestas.removeIf(aresta -> aresta.destino == vertice);
			}
		}		
		adjacencias.remove(vertice);
	    nVertices--;	    
	    for (int i = 0; i < nVertices; i++) {
	        List<Aresta> arestas = adjacencias.get(i);
	        for (Aresta aresta : arestas) {
	            if (aresta.destino > vertice) {
	                aresta.destino--;
	            }
	        }
	    }		
	}	
	
	public void AdjacenciasDoVertice(int vertice) {
		if (vertice < 0 || vertice >= nVertices) {
	        System.out.println("Vértice inválido");
	        return;
	    }

	    System.out.print("Vértice " + vertice + ":");
	    for (Aresta aresta : adjacencias.get(vertice)) {
	        System.out.print("(" + aresta.destino + ", Peso " + aresta.peso + ")");
	    }
	    System.out.println();
	}
	
	public void verificarConexo() {
	    boolean[] visitado = new boolean[nVertices];
	    int verticeInicial = 0; 
	    Arrays.fill(visitado, false);
	    verCon(verticeInicial, visitado);
	    for (boolean v : visitado) {
	        if (!v) {
	            System.out.println("Não é conexo."); 
	            return;
	        }
	    }	    
	    System.out.println("É conexo");
	}

	private void verCon(int vertice, boolean[] visitado) {
	    visitado[vertice] = true;
	    for (Aresta aresta : adjacencias.get(vertice)) {
	        int destino = aresta.destino;
	        if (!visitado[destino]) {
	        	verCon(destino, visitado);
	        }
	    }
	}
	
	public void verificarCompleto() {
	    int numArestasEsperado = nVertices * (nVertices - 1) / 2;
	    int numArestasAtual = 0;
	    for (int i = 0; i < nVertices; i++) {
	        for (int j = i + 1; j < nVertices; j++) {
	            for (Aresta aresta : adjacencias.get(i)) {
	                if (aresta.destino == j) {
	                    numArestasAtual++;
	                    break;
	                }
	            }
	        }
	    }
	    if (numArestasAtual == numArestasEsperado) {
	        System.out.println("É completo");
	    } else {
	        System.out.println("Não é completo");
	    }
	}

	
	public List<Integer> dijkstra(int origem, int destino) {
	    Queue<Integer> fila = new LinkedList<>();
	    int[] distancias = new int[nVertices];
	    Arrays.fill(distancias, Integer.MAX_VALUE);
	    distancias[origem] = 0;
	    fila.add(origem);
	    while (!fila.isEmpty()) {
	        int u = fila.poll();
	        for (Aresta aresta : adjacencias.get(u)) {
	            int v = aresta.destino;
	            int pesoUV = aresta.peso;
	            if (distancias[u] != Integer.MAX_VALUE && distancias[u] + pesoUV < distancias[v]) {
	                distancias[v] = distancias[u] + pesoUV;
	                fila.add(v);
	                anterior[v] = u;
	            }
	        }
	    }
	    List<Integer> caminhoMaisCurto = new ArrayList<>();
	    int atual = destino;
	    int pesoTotal = distancias[destino];
	    while (atual != -1) {
	        caminhoMaisCurto.add(atual);
	        atual = anterior[atual];
	    }
	    Collections.reverse(caminhoMaisCurto);	    
	    System.out.println("Peso Total: " + pesoTotal);
	    return caminhoMaisCurto;
	}
	
	public void verificarEuleriano() {
	    int grauImpar = 0;
	    for (int i = 0; i < nVertices; i++) {
	        int grau = adjacencias.get(i).size();
	        if (grau % 2 != 0) {
	            grauImpar++;
	        }
	    }
	    if (grauImpar == 0) {
	        System.out.println("O grafo é euleriano.");
	    } else if (grauImpar == 2) {
	        System.out.println("O grafo é semieuleriano.");
	    } else {
	        System.out.println("O grafo não é euleriano nem semieuleriano.");
	    }
	}
	
	public void verificarHamiltoniano() {
	    int n = nVertices;
	    for (int i = 0; i < n; i++) {
	        if (adjacencias.get(i).size() < 2) {
	            System.out.println("O grafo não é Hamiltoniano."); 
	            return;
	        }
	    }
	    boolean[] visitado = new boolean[n];
	    for (int i = 0; i < n; i++) {
	        Arrays.fill(visitado, false);
	        if (existeCicloHamiltoniano(i, visitado, 1, i)) {
	            System.out.println("O grafo é Hamiltoniano.");
	            return;
	        }
	    }
	    System.out.println("O grafo não é Hamiltoniano.");
	}

	private boolean existeCicloHamiltoniano(int atual, boolean[] visitado, int visitados, int origem) {
	    visitado[atual] = true;
	    for (Aresta aresta : adjacencias.get(atual)) {
	        int vizinho = aresta.destino;
	        if (!visitado[vizinho]) {
	            if (existeCicloHamiltoniano(vizinho, visitado, visitados + 1, origem)) {
	                return true;
	            }
	        } else if (vizinho == origem && visitados == nVertices) {
	            return true; 
	        }
	    }
	    visitado[atual] = false;
	    return false;
	}
}
