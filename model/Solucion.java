package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solucion {
	private Map<Procesador, List<Tarea>> mapSolucion;

	public Solucion() {
		mapSolucion = new HashMap<>();
	}

	public Map<Procesador, List<Tarea>> getMapSolucion() {
		return mapSolucion;
	}

	public void setMapSolucion(Map<Procesador, List<Tarea>> mapSolucion) {
		this.mapSolucion = mapSolucion;
	}

	public Integer getTiempoFinal() {
		Integer tiempoFinal = 0;
		for (Procesador p : mapSolucion.keySet()) {
			Integer tiempoParcial = 0;
			List<Tarea> tareas = mapSolucion.get(p);
			for (Tarea t : tareas) {
				tiempoParcial += t.getTiempoEjecucion();
			}

			tiempoFinal = tiempoFinal < tiempoParcial ? tiempoParcial : tiempoFinal;
		}

		return tiempoFinal;
	}
	@Override
	public Solucion clone() {
		Solucion s = new Solucion();
		for (Procesador p : this.getMapSolucion().keySet()){
			s.getMapSolucion().put(p, new LinkedList<Tarea>(this.getMapSolucion().get(p)));
		}

		return s;
	}
	@Override
	public String toString(){

		return this.getMapSolucion().toString();
	}
}