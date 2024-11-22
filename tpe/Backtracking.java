package tpe;

import model.Procesador;
import model.Solucion;
import model.Tarea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Backtracking {

    private Solucion mejorSolucion;
    private Servicios servicio;
    private int cantEstados;

    public Backtracking(Servicios s){
        this.servicio = s;
        cantEstados = 0;
    }
    /*
     * Esta solución genera un árbol con hasta P^T combinaciones, donde P es el número de procesadores y T es el número de tareas.
     *
     * En el primer nivel, tenemos P estados iniciales, en los cuales se asigna la primera tarea a cada procesador.
     * En el segundo nivel, para cada estado generado en el primer nivel, se recorre cada procesador y se asigna la segunda tarea.
     * Este proceso continúa de la misma forma hasta completar los T niveles, generando todas las combinaciones posibles de asignación.
     *
     * Además, se aplica una condición de poda basada en tareas críticas y en un tiempo máximo de ejecución para los procesadores refrigerados.
     */

    public Solucion backTracking(Integer tiempoMaxNoRefrigerados){
        mejorSolucion = new Solucion();
        for (Procesador p : servicio.getProcesadores()) {
            mejorSolucion.addMapSolucion(p);
        }

        backTracking(mejorSolucion.clone(),servicio.getTareas(),tiempoMaxNoRefrigerados);
        return mejorSolucion;
    }

    private void backTracking(Solucion sParcial, List<Tarea> tRestantes, Integer tiempoMax) {
        cantEstados++;
        if (tRestantes.isEmpty()) {
            if (mejorSolucion.getTiempoFinal() ==0 || sParcial.getTiempoFinal() < mejorSolucion.getTiempoFinal()) {
                mejorSolucion = sParcial.clone();
                mejorSolucion.setHaySolucion(true);
            }
            return;
        }
        Tarea actual = tRestantes.get(0);
        for (Procesador p : sParcial.getMapSolucion()) {
            if((!actual.getEsCritica() || p.getCantidadCriticas() <2)&&
                    (p.getEsRefrigerado() || p.getTiempoTotalAsignado()+actual.getTiempoEjecucion()<=tiempoMax)){
                p.addTarea(actual);
                int aux = 0;

                if(p.getTiempoTotalAsignado()>sParcial.getTiempoFinal()){
                    aux = sParcial.getTiempoFinal();
                    sParcial.setTiempoFinal(p.getTiempoTotalAsignado());
                }
                List<Tarea> tRestantesSiguiente = new ArrayList<>(tRestantes.subList(1, tRestantes.size()));
                backTracking(sParcial, tRestantesSiguiente,tiempoMax);
                if (aux !=0){
                    sParcial.setTiempoFinal(aux);
                }
                p.eliminarTarea(actual);
            }
        }

    }

    public int getCantEstados(){
        return cantEstados;
    }

    public void imprimirSolucion(){
        System.out.println("BackTracking:");
        System.out.println("Solución obtenida: " + mejorSolucion);
        System.out.println("Solución obtenida tiempo máximo de ejecución: " + mejorSolucion.getTiempoFinal());
        System.out.println("Cantidad de estados generados: " + cantEstados);

    }
}
