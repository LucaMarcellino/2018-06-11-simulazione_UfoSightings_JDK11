package it.polito.tdp.ufo.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		m.creaGrafo(new AnnoAvvistamento(1953,26));
		System.out.print(m.trovaRicorsivo("la"));
	}

}
