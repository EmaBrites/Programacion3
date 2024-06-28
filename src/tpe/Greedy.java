package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Greedy {
    private List<Tarea> tareas;
    private List<Procesador> procesadores;
    private Integer resulTiempoFinalEjecucion;
    private Integer estadosGenerados;


    public Greedy(List<Tarea> tareas, List<Procesador> procesadores) {
        this.tareas = new ArrayList<>(tareas);
        this.procesadores = new ArrayList<>(procesadores);
        this.estadosGenerados = 0;
        this.resulTiempoFinalEjecucion = 0;
        this.tareas.sort(comparadorTiempoEjecucion);
    }

    /*
    Nosotros consideramos que era una buena estrategia ordenar por criticas y tiempo de ejecucion(mayor a menor)
    para encontrar los casos de procesadores saturados mas rapido y asi descartar estados innecesario.
     */
    Comparator<Tarea> comparadorTiempoEjecucion = new Comparator<Tarea>() {
        @Override
        public int compare(Tarea tarea1, Tarea tarea2) {
            // Si las tareas son críticas, pone la crítica primero
            if (tarea1.getEsCritica() && !tarea2.getEsCritica()) {
                return -1;
            } else if (!tarea1.getEsCritica() && tarea2.getEsCritica()) {
                return 1;
            }
            // Si ambas tareas son críticas o no críticas, ordena por tiempo de ejecucion de mayor a menor
            return Integer.compare(tarea2.getTiempoEjecucion(), tarea1.getTiempoEjecucion());
        }
    };

    /*
    Este metodo es nuestro criterio para seleccionar la mejor opcion posible, en este caso el procesador
     con menos tiempo de ejecucion actual es la mejor opcion para asignar una tarea
     */
    private Procesador obtenerProcesadorOptimo(Tarea tarea) {
        int mejorTiempo = Integer.MAX_VALUE;
        Procesador pOptimo = null;
        for (Procesador procesador : this.procesadores) {
            this.estadosGenerados++;
            if (procesador.puedeAgregarTarea(tarea) && procesador.getTiempoEjecucion() < mejorTiempo) {
                pOptimo = procesador;
                mejorTiempo = procesador.getTiempoEjecucion();
            }
        }
        return pOptimo;
    }

    public Solucion ejecutarGreedy() {
        while (!this.tareas.isEmpty()) {
            Tarea tareaAsignar = this.tareas.get(0);
            Procesador procesador = this.obtenerProcesadorOptimo(tareaAsignar);
            if (procesador != null) {
                procesador.addTarea(tareaAsignar);
                this.tareas.remove(tareaAsignar);
                if (resulTiempoFinalEjecucion < procesador.getTiempoEjecucion()) {
                    resulTiempoFinalEjecucion = procesador.getTiempoEjecucion();
                }
            } else {
                throw new RuntimeException("No se encontro una solucion.");
            }
        }
        return new Solucion(this.procesadores, this.resulTiempoFinalEjecucion, this.estadosGenerados);
    }
}


