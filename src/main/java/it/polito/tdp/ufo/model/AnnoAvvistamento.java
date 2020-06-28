package it.polito.tdp.ufo.model;

public class AnnoAvvistamento implements Comparable<AnnoAvvistamento> {

	private int anno;
	private int tot;
	public AnnoAvvistamento(int anno, int tot) {
		super();
		this.anno = anno;
		this.tot = tot;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getTot() {
		return tot;
	}
	public void setTot(int tot) {
		this.tot = tot;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anno;
		result = prime * result + tot;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnoAvvistamento other = (AnnoAvvistamento) obj;
		if (anno != other.anno)
			return false;
		if (tot != other.tot)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return anno + " " + tot ;
	}
	@Override
	public int compareTo(AnnoAvvistamento arg0) {
		
		return this.anno-arg0.getAnno();
	}
	
	
	
}
