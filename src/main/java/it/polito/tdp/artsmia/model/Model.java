package it.polito.tdp.artsmia.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<Artista, DefaultWeightedEdge> grafo;
	private Map<Integer, Artista> idMap;
	private List<Adiacenza> adiacenze;
	private List<ArtistaWithWeight> percorso;
	private int costoMax;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
	}
	
	public List<String> getAllRoles() {
		return this.dao.getAllRoles();
	}
	
	public void creaGrafo(String role) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Vertici
		this.idMap = this.dao.getArtistsByRole(role);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//Archi
		this.adiacenze = this.dao.getEdges(role, idMap);
		for(Adiacenza a : adiacenze) {
			Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getNumEdges() {
		return this.grafo.edgeSet().size();
	}
	
	public String getArtistiConnessi() {
		String result = "";
		
		Collections.sort(adiacenze);
		
		for(Adiacenza a : adiacenze) {
			result += a.getA1().getArtist_id()+"_"+a.getA1()+" - "+a.getA2().getArtist_id()+"_"+a.getA2()+" -> "+a.getPeso()+"\n";
		}
		
		return result;
	}
	
	public String calcolaPercorso(int artist_id) {
		if(!idMap.containsKey(artist_id))
			return "L'identificativo non corrisponde a nessun artista nel grafo\n";
		
		String result = "";
		
		
		Artista a = this.idMap.get(artist_id);
		List<ArtistaWithWeight> parziale = new LinkedList<>();
		parziale.add(new ArtistaWithWeight(a, 0));
		this.percorso = null;
		this.costoMax = 0;
		this.cerca(parziale, 0);
		if(this.percorso==null)
			return "Ricerca fallita\n";
		int numExp = 0;
		for(ArtistaWithWeight aww : percorso) {
			result += aww.getA()+"\n";
			numExp = aww.getPeso();
		}
		result += "Il numero di esposizioni per cui il percorso risulta massimo è "+numExp+"\n";
		return result;
	}
	
	public void cerca(List<ArtistaWithWeight> parziale, int costo) {
		//Non c'è un caso terminale, ogni volta calcolo 
		//se migliora il costoMax e se si lo aggiorno ed aggiorno il percorso
		if(costo>this.costoMax) {
			this.costoMax = costo;
			this.percorso = new LinkedList<>(parziale);
			return;
		}
		
		
		for(Adiacenza a : this.adiacenze) {
			
			
			
			//Aggiungo a parziale solo se l'artista in esame (A1 o A2) è collegato con
			//quello precedente e se hanno lo stesso peso e se non ci sono già passato
			
			//Controllo che abbiano lo stesso peso oppure che si tratti del primo che aggiungo:
			int pesoPrec = parziale.get(parziale.size()-1).getPeso();
			if(a.getPeso()==pesoPrec || pesoPrec==0) {
				
				
				Artista a1 = a.getA1();
				Artista a2 = a.getA2();
				Artista prec = parziale.get(parziale.size()-1).getA();
				
				//CASO 1
				if(a1.equals(prec)){
					
					
						
					if(!parziale.contains(new ArtistaWithWeight(a2, a.getPeso()))) {
						
						
						parziale.add(new ArtistaWithWeight(a2, a.getPeso()));
						this.cerca(parziale, costo+a.getPeso());
						parziale.remove(parziale.size()-1);
					}
					
					
					
					
					
				//CASO 2
				}else { if(a2.equals(prec)) {
					
					
					
					if(!parziale.contains(new ArtistaWithWeight(a1, a.getPeso()))) {
						
						
						parziale.add(new ArtistaWithWeight(a1, a.getPeso()));
						this.cerca(parziale, costo+a.getPeso());
						parziale.remove(parziale.size()-1);
					}
					
					
				}}
				
				
			}
		}
		
		
		
	}

}
