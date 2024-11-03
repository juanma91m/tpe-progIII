package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.Procesador;
import model.Solucion;
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
	 * Expresar la complejidad temporal del servicio 3. O(Log N + K) N: cantidad de
	 * elementos K: cantidad de elementos que cumplen la condición. En el peor de
	 * los casos es N.
	 */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		int inicio = buscarIndice(tareas, prioridadInferior, true);
		int fin = buscarIndice(tareas, prioridadSuperior, false);

		if (inicio < tareas.size() && fin >= 0 && inicio <= fin) {
			return tareas.subList(inicio, fin + 1);
		}
		return new ArrayList<>();
	}

	// Busqueda binaria O(Log N)
	private int buscarIndice(List<Tarea> tareas, int prioridad, boolean buscarInicio) {
		int left = 0, right = tareas.size() - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			int midPrioridad = tareas.get(mid).getNivelPrioridad();

			if (midPrioridad < prioridad) {
				// Buscar en la parte derecha
				left = mid + 1;
			} else if (midPrioridad > prioridad) {
				// Buscar en la parte izquierda
				right = mid - 1;
			} else {
				// Encontrado, buscar el índice extremo
				if (buscarInicio) {
					// Buscar el primer índice igual
					right = mid - 1;
				} else {
					// Buscar el último índice igual
					left = mid + 1;
				}
			}
		}

		return buscarInicio ? left : right;
	}

	public Solucion backTracking(Integer tiempoMaxNoRefrigerados) {
		Solucion s = new Solucion();
		for (Procesador p : procesadores) {
			s.getMapSolucion().put(p, new LinkedList<Tarea>());
		}

		return s;
	}

	private void backTracking(Solucion sFinal, Solucion sParcial, List<Tarea> tRestantes) {
		if (tRestantes.isEmpty()) {
			if (sParcial.getTiempoFinal() < sFinal.getTiempoFinal()) {
				sFinal = sParcial.clone();
			}
		} else {

		}
	}

	public Solucion greedy(Integer tiempoMaxNoRefrigerados) {

		return null;
	}
}