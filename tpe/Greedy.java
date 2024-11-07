package tpe;

import model.Procesador;
import model.Solucion;
import model.Tarea;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Greedy {
    private Servicios servicio;
    private int cantCandidatos;
    private Solucion mejorSolucion;

    public Greedy(Servicios s){
        servicio = s;
        cantCandidatos = 0;
    }

    /*
     * Comenzamos con un arreglo de Tareas ordenado de forma descendente por tiempo de ejecución.
     * Para cada tarea a asignar, buscamos los procesadores posibles, es decir,
     * aquellos que cumplan con las condiciones de tareas criticas y tiempo máximo
     * (en el caso de procesadores refrigerados).
     * Entre estos procesadores válidos, se elige el candidato con el menor tiempo
     * de ejecución acumulado en sus tareas asignadas.
     */
    public Solucion greedy (Integer tiempoMaxNoRefrigerados){
        Solucion s = new Solucion();
        for (Procesador p : servicio.getProcesadores()) {
            s.getMapSolucion().put(p, new LinkedList<Tarea>());
        }
        List<Tarea> tareas = servicio.getTareas();
        /*
        * Ordena las tareas por su tiempo de ejecucion.
        * */
        tareas.sort((o1, o2) -> {
            if (o1.getTiempoEjecucion()<o2.getTiempoEjecucion()){
                return 1;
            } else if (o1.getTiempoEjecucion()>o2.getTiempoEjecucion()) {
                return -1;
            }
            return 0;
        });

        for (Tarea tarea : tareas) {
            Procesador mejorProce = mejorProcesadorDisponible(s.getMapSolucion(),tarea,tiempoMaxNoRefrigerados);
            if (mejorProce != null){
                s.getMapSolucion().get(mejorProce).add(tarea);
            }

        }
        mejorSolucion = s;
        return s;
    }
    private Procesador mejorProcesadorDisponible(Map<Procesador, List<Tarea>> mapaActual, Tarea actual, Integer tiempoMax){
        Procesador mejorProce = null;
        int menorTiempo = 0;
        for (Procesador p : mapaActual.keySet()) {
            List<Tarea> tareas = mapaActual.get(p);
            int cantCriticas=0;
            int tiempoActual=0;
            for (Tarea tarea : tareas) {
                if (tarea.getEsCritica()){
                    cantCriticas++;
                }
                tiempoActual+=tarea.getTiempoEjecucion();
            }
            if((!actual.getEsCritica() || cantCriticas <2)&&
                    (p.getEsRefrigerado() || tiempoActual+actual.getTiempoEjecucion()<=tiempoMax)){
                if (mejorProce==null || menorTiempo>tiempoActual){
                    mejorProce = p;
                    menorTiempo = tiempoActual;
                    cantCandidatos++;
                }
            }
        }
        return mejorProce;
    }

    public int getCantCandidatos() {
        return cantCandidatos;
    }

    public void imprimirSolucion(){
        System.out.println("Greedy:");
        System.out.println("Solución obtenida: " + mejorSolucion);
        System.out.println("Solución obtenida tiempo máximo de ejecución: " + mejorSolucion.getTiempoFinal());
        System.out.println("Cantidad de candidatos: " + cantCandidatos);

    }
}
