package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import model.Procesador;
import model.Tarea;
import tpe.utils.CSVReader;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos. Sólo se podrá
 * adaptar el nombre de la clase "Tarea" según sus decisiones de implementación.
 */
public class Servicios {

	private List<Procesador> procesadores;
	private Map<String, Tarea> mapTareasId;
	private Map<Boolean, List<Tarea>> mapTareasEsCritica;
	private List<Tarea> tareas;

	/*
	 * Expresar la complejidad temporal del constructor.
	 */
	public Servicios(String pathProcesadores, String pathTareas) {
		CSVReader reader = new CSVReader();
		procesadores = reader.readProcessors(pathProcesadores);
		tareas = reader.readTasks(pathTareas);

		mapTareasId = new HashMap<>();
		mapTareasEsCritica = new HashMap<>();
		mapTareasEsCritica.put(true, new LinkedList<Tarea>());
		mapTareasEsCritica.put(false, new LinkedList<Tarea>());

		for (Tarea t : tareas) {
			mapTareasId.put(t.getId(), t);
			mapTareasEsCritica.get(t.getEsCritica()).add(t);
		}
	}

	/*
	 * Expresar la complejidad temporal del servicio 1. O(1)
	 */
	public Tarea servicio1(String ID) {
		return mapTareasId.get(ID);
	}

	/*
	 * Expresar la complejidad temporal del servicio 2. O(1)
	 */
	public List<Tarea> servicio2(boolean esCritica) {
		return mapTareasEsCritica.get(esCritica);
	}

	/*
	 * Expresar la complejidad temporal del servicio 3.
	 */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		return new ArrayList<>();
	}
}