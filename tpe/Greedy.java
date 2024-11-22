package tpe;
import model.Procesador;
import model.Solucion;
import model.Tarea;
import java.util.HashSet;
import java.util.List;

public class Greedy {
    private Servicios servicio;
    private int cantCandidatos;
    private Solucion mejorSolucion;

    public Greedy(Servicios s){
        servicio = s;
        cantCandidatos = 0;
        this.mejorSolucion = new Solucion();
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
        for (Procesador p : servicio.getProcesadores()) {
            mejorSolucion.addMapSolucion(p);
        }
        List<Tarea> tareas = servicio.getTareas();
        //Ordena las tareas por su tiempo de ejecucion.
        tareas.sort((o1, o2) -> {
            if (o1.getTiempoEjecucion()<o2.getTiempoEjecucion()){
                return 1;
            } else if (o1.getTiempoEjecucion()>o2.getTiempoEjecucion()) {
                return -1;
            }
            return 0;
        });
        mejorSolucion.setHaySolucion(true);
        for (Tarea tarea : tareas) {
            Procesador mejorProce = mejorProcesadorDisponible(mejorSolucion.getMapSolucion(),tarea,tiempoMaxNoRefrigerados);
            if (mejorProce != null){
                mejorProce.addTarea(tarea);
                if(mejorProce.getTiempoTotalAsignado()>mejorSolucion.getTiempoFinal()){
                    mejorSolucion.setTiempoFinal(mejorProce.getTiempoTotalAsignado());
                }
            }else{
                mejorSolucion.setHaySolucion(false);
            }
        }
        return mejorSolucion;
    }

    private Procesador mejorProcesadorDisponible(HashSet<Procesador> mapaActual, Tarea actual, Integer tiempoMax){
        Procesador mejorProce = null;
        int menorTiempo = 0;
        for (Procesador p : mapaActual) {
            cantCandidatos++;
            if((!actual.getEsCritica() || p.getCantidadCriticas() <2)&&
                    (p.getEsRefrigerado() || p.getTiempoTotalAsignado()+actual.getTiempoEjecucion()<=tiempoMax)){
                if (mejorProce==null || menorTiempo>p.getTiempoTotalAsignado()){
                    mejorProce = p;
                    menorTiempo = p.getTiempoTotalAsignado();

                }
            }
        }
        return mejorProce;
    }

    public void imprimirSolucion(){
        System.out.println("Greedy:");
        System.out.println("Solución obtenida: " + mejorSolucion);
        System.out.println("Solución obtenida tiempo máximo de ejecución: " + mejorSolucion.getTiempoFinal());
        System.out.println("Cantidad de candidatos: " + cantCandidatos);
    }
}