package model;

import java.util.*;

public class Solucion {
	//private Map<Procesador, List<Tarea>> mapSolucion;
	private HashSet<Procesador> mapSolucionNueva;
	private int tiempoFinal;
	private boolean haySolucion;

	public Solucion() {
		//mapSolucion = new HashMap<>();
		mapSolucionNueva = new HashSet<>();
		tiempoFinal = 0;
		haySolucion = false;
	}
	public boolean getHaySolucion(){return this.haySolucion;}
	public void setHaySolucion(boolean haySol){this.haySolucion = haySol;}
	public HashSet<Procesador> getMapSolucion() {
		return mapSolucionNueva;
	}

	public void addMapSolucion(Procesador p){this.mapSolucionNueva.add(p);}

	public void setMapSolucion(HashSet<Procesador> mapSolucion) {
		this.mapSolucionNueva = mapSolucion;
	}
	public void setTiempoFinal(int tFinal){this.tiempoFinal = tFinal;}
	public int getTiempoFinal(){return this.tiempoFinal;}


	@Override
	public Solucion clone() {
		Solucion s = new Solucion();
		for (Procesador p : this.mapSolucionNueva){
			s.mapSolucionNueva.add(new Procesador(p));
		}
		s.setTiempoFinal(this.tiempoFinal);
		return s;
	}
	@Override
	public String toString(){
		if(this.haySolucion){
			return this.getMapSolucion().toString();
		}else{
			return "NO HAY SOLUCION";
		}

	}
}