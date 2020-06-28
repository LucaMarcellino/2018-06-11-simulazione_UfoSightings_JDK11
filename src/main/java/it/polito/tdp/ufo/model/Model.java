package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;


import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	private SightingsDAO dao;
	private List<AnnoAvvistamento> annoAvvistamento;
	private Graph<String,DefaultEdge> grafo;
	private List<String> vertici;
	private List<String> parziale;
	private List<String> best;
	
	public Model() {
		this.dao= new SightingsDAO();
		this.annoAvvistamento= new ArrayList<AnnoAvvistamento>(dao.getAnnoAvvistamento());
	}
	 
	public List<AnnoAvvistamento> getAnnoAvvistamento(){
		Collections.sort(dao.getAnnoAvvistamento());
		return annoAvvistamento ;
	}
	
	public void creaGrafo(AnnoAvvistamento aa) {
		this.grafo= new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertex(aa));
		
		for(Arco a : dao.getEdge(aa))
			grafo.addEdge(a.getV1(), a.getV2());
		
	}
	
	public List<String> getVertex(){
		this.vertici= new ArrayList<String>(grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	public int getEdge() {
		return grafo.edgeSet().size();
	}
	
	public List<String> getSuccessivi(String partenza){
		return Graphs.successorListOf(grafo, partenza);
	}
	
	public List<String> getPrecedenti(String partenza){
		return Graphs.predecessorListOf(grafo, partenza);
	}
	
	public List<String> getVisita(String partenza){
		final Map<String	,String> albero = new HashMap<>();
		List<String> result = new ArrayList<String>();

		
		BreadthFirstIterator<String,DefaultEdge> it =new BreadthFirstIterator<>(this.grafo,partenza);
		albero.put(partenza,null);
		
		it.addTraversalListener(new TraversalListener<String, DefaultEdge>() {
			
			@Override
			public void vertexTraversed(VertexTraversalEvent<String> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<String> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				String sorgente= grafo.getEdgeSource(e.getEdge());
				String destinazione= grafo.getEdgeTarget(e.getEdge());
				if( !albero.containsKey(destinazione) && albero.containsKey(sorgente)) {
					albero.put(destinazione, sorgente);
				}else if( !albero.containsKey(sorgente) && albero.containsKey(destinazione)) {
					albero.put(sorgente, destinazione);
				}
				
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

			
			
	
		while(it.hasNext()) {
			it.next();
		}
		
		for(String a:albero.keySet()) {
			result.add(a);
		}
		result.remove(partenza);
		
		Collections.sort(result);


		return result;
	}	
	
	public List<String> trovaRicorsivo(String partenza) {
		this.parziale= new ArrayList<String>();
		this.best= new ArrayList<String>();
		parziale.add(partenza);//livello 0
		int livello=1;
		ricorsiovo(livello,parziale);
		return best;
	}

	private void ricorsiovo(int livello, List<String> parziale) {
		
		for(String s : Graphs.successorListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				ricorsiovo(livello+1, parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
		if(best.size()<parziale.size())
			best=new ArrayList<String>(parziale);
		
		
	}
	
	
}
