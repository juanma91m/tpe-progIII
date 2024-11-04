package tpe;

import java.lang.reflect.Array;
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
	private Integer contador = 0;
	private Solucion mejorSolucion;
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
		mejorSolucion = new Solucion();
		for (Procesador p : procesadores) {
			s.getMapSolucion().put(p, new LinkedList<Tarea>());
			mejorSolucion.getMapSolucion().put(p, new LinkedList<Tarea>());
		}


		backTracking(s,new ArrayList<>(this.tareas),tiempoMaxNoRefrigerados);
		System.out.println(mejorSolucion);
		return mejorSolucion;
	}

	private void backTracking(Solucion sParcial, List<Tarea> tRestantes, Integer tiempoMax) {
		if (tRestantes.isEmpty()) {
			if (mejorSolucion.getTiempoFinal() ==0 || sParcial.getTiempoFinal() < mejorSolucion.getTiempoFinal()) {
				mejorSolucion = sParcial.clone();
			}
			return;
		}
		Tarea actual = tRestantes.get(0);

		for (Procesador p : sParcial.getMapSolucion().keySet()) {
			List<Tarea> tareasActuales = sParcial.getMapSolucion().get(p);
			Integer cantCriticas = 0, tiempoActual = 0;
			for (Tarea t : tareasActuales){
				cantCriticas+= t.getEsCritica() ? 1 : 0;
				tiempoActual+= t.getTiempoEjecucion();
			}
			if((!actual.getEsCritica() || cantCriticas ==0)&&
					(p.getEsRefrigerado() || tiempoActual+actual.getTiempoEjecucion()<=tiempoMax)){
				sParcial.getMapSolucion().get(p).add(actual);
				List<Tarea> tRestantesSiguiente = new ArrayList<>(tRestantes.subList(1, tRestantes.size()));
				backTracking(sParcial, tRestantesSiguiente,tiempoMax);
				sParcial.getMapSolucion().get(p).remove(actual);
			}

		}

	}

	public Solucion greedy(Integer tiempoMaxNoRefrigerados) {

		return null;
	}
}