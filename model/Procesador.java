package model;

import java.util.LinkedList;
import java.util.List;

public class Procesador {
	private String id, codigo;
	private Boolean esRefrigerado;
	private Integer anioFuncionamiento;
	private List<Tarea> tareas;
	private int cantidadCriticas;
	private int tiempoTotalAsignado;

	public Procesador(){
		this.tareas = new LinkedList<>();
		this.cantidadCriticas = 0;
		this.tiempoTotalAsignado = 0;
	}
	public Procesador(Procesador p){
		this.id = p.getId();
		this.codigo = p.getCodigo();
		this.esRefrigerado = p.getEsRefrigerado();
		this.anioFuncionamiento = p.getAnioFuncionamiento();
		this.tareas = p.getTareas();
		this.cantidadCriticas = p.getCantidadCriticas();
		this.tiempoTotalAsignado = p.getTiempoTotalAsignado();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getEsRefrigerado() {
		return esRefrigerado;
	}

	public void setEsRefrigerado(Boolean esRefrigerado) {
		this.esRefrigerado = esRefrigerado;
	}

	public Integer getAnioFuncionamiento() {
		return anioFuncionamiento;
	}

	public void setAnioFuncionamiento(Integer anioFuncionamiento) {
		this.anioFuncionamiento = anioFuncionamiento;
	}

	public int getCantidadCriticas(){ return this.cantidadCriticas;}

	public void setCantidadCriticas(int cantCriticas){this.cantidadCriticas = cantCriticas;}

	public int getTiempoTotalAsignado(){ return this.tiempoTotalAsignado;}

	public void setTiempoTotalAsignado(int tiempoTotalAsignado){this.tiempoTotalAsignado = tiempoTotalAsignado;}

	public List<Tarea> getTareas(){return new LinkedList<Tarea>(this.tareas);}

	public void addTarea(Tarea t){
		this.tareas.add(t);
		this.tiempoTotalAsignado+=t.getTiempoEjecucion();
		if (t.getEsCritica()){
			this.cantidadCriticas++;
		}
	}

	public boolean eliminarTarea(Tarea t){
		this.tiempoTotalAsignado-=t.getTiempoEjecucion();
		if(t.getEsCritica()){
			this.cantidadCriticas--;
		}
		return this.tareas.remove(t);
	}

	public Procesador clone() {
		Procesador p = new Procesador();
		p.setId(this.id);
		p.setAnioFuncionamiento(this.anioFuncionamiento);
		p.setCodigo(this.codigo);
		p.setEsRefrigerado(this.esRefrigerado);
		
		return p;
	}

	@Override
	public String toString() {
		return "Procesador{" +
				"id=" + id +
				'}'+this.tareas;
	}
}