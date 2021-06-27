package it.polito.tdp.artsmia.model;

public class ArtistaWithWeight {
	
	private Artista a;
	private int peso;
	
	public ArtistaWithWeight(Artista a, int peso) {
		super();
		this.a = a;
		this.peso = peso;
	}

	public Artista getA() {
		return a;
	}

	public void setA(Artista a) {
		this.a = a;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public boolean equals(Object obj) {
		ArtistaWithWeight other = (ArtistaWithWeight)obj;
		return this.a.equals(other.getA());
	}
	
	
	
	
	
	

}
