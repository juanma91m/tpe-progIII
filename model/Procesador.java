package model;

public class Procesador {
	private String id, codigo;
	private Boolean esRefrigerado;
	private Integer anioFuncionamiento;

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
	
	public Procesador clone() {
		Procesador p = new Procesador();
		p.setId(this.id);
		p.setAnioFuncionamiento(this.anioFuncionamiento);
		p.setCodigo(this.codigo);
		p.setEsRefrigerado(this.esRefrigerado);
		
		return p;
	}
}