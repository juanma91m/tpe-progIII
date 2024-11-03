package model;

public class Tarea implements Comparable<Tarea> {
	private String id, nombre;
	private Integer tiempoEjecucion, nivelPrioridad;
	private Boolean esCritica;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(Integer tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public Integer getNivelPrioridad() {
		return nivelPrioridad;
	}

	public void setNivelPrioridad(Integer nivelPrioridad) {
		this.nivelPrioridad = nivelPrioridad;
	}

	public Boolean getEsCritica() {
		return esCritica;
	}

	public void setEsCritica(Boolean esCritica) {
		this.esCritica = esCritica;
	}

	@Override
	public int compareTo(Tarea otraTarea) {
		return this.nivelPrioridad.compareTo(otraTarea.getNivelPrioridad());
	}
}