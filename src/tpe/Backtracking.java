package ProgramacionIII.tpe;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    private Integer resulTiempoFinalEjecucion;
    private List<Tarea> tareas;
    private List<Procesador> procesadores;
    private Integer estadosGenerados;
    private List<Procesador> procesadoresListos;


    public Backtracking(List<Tarea> tareas, List<Procesador> procesadores) {
        this.resulTiempoFinalEjecucion = 0;
        this.estadosGenerados = 0;
        this.tareas = new ArrayList<>(tareas);
        this.procesadores = new ArrayList<>(procesadores);
        this.procesadoresListos = new ArrayList<>();
    }

    /*
     * La complejidad temporal del backtracking seria O(n^m) donde n son las tareas y m los procesadores.
     */

    public Solucion iniciarBacktracking() {
        this.resulTiempoFinalEjecucion = Integer.MAX_VALUE;
        Integer mejorTiempoActual = Integer.MIN_VALUE;
        this.ejecutarBacktracking(mejorTiempoActual, new ArrayList<>(), this.procesadores);
        if (this.procesadoresListos.isEmpty()) {
            throw new RuntimeException("No existe solucion posible.");
        }
        return new Solucion(this.procesadoresListos, this.resulTiempoFinalEjecucion, this.estadosGenerados);
    }

    /*
    Lo primero es verificar la condicion de corte que seria haber asignado todas las tareas para luego calcular
    el mejor tiempo actual y una vez calculado comparamos con lo que tenemos como posible resultado y elegimos
    el de menos tiempo.
    Sino se asignaron todas las tareas seguimos probando posibles soluciones con backtracking y podando casos en los que
    no se llegarian a una mejor solucion de la ya encontrada.
     */

    private void ejecutarBacktracking(Integer mejorTiempoActual, List<Tarea> tareasAsignadas, List<Procesador> procesadores) {
        this.estadosGenerados++;

        if (tareasAsignadas.size() == this.tareas.size()) {
            for (Procesador procesador : procesadores) {
                int tiempoProcesador = procesador.getTiempoEjecucion();
                mejorTiempoActual = Math.max(mejorTiempoActual, tiempoProcesador);
            }
            if (mejorTiempoActual < this.resulTiempoFinalEjecucion) {
                this.procesadoresListos.clear();
                this.resulTiempoFinalEjecucion = mejorTiempoActual;
                for (Procesador procesador : procesadores) {
                    this.procesadoresListos.add(new Procesador(procesador.getId(), procesador.getCodigoProcesador(), procesador.getAnioFuncionamiento(), procesador.getRefrigerado(), procesador.getTiempoEjecucion(), procesador.getTiempoMaximo(), procesador.getTareasAsignadas()));
                }
            }
        } else {
            if (mejorTiempoActual >= this.resulTiempoFinalEjecucion) {
                return;
            }
            for (Tarea tActual : this.tareas) {
                for (Procesador pActual : procesadores) {
                    if (!tareasAsignadas.contains(tActual)) {
                        if (pActual.puedeAgregarTarea(tActual)) {
                            pActual.addTarea(tActual);
                            tareasAsignadas.add(tActual);
                            if (mejorTiempoActual < this.resulTiempoFinalEjecucion) {
                                ejecutarBacktracking(mejorTiempoActual, tareasAsignadas, procesadores);
                            }
                            tareasAsignadas.remove(tActual);
                            pActual.removeTarea(tActual);
                        }
                    }
                }
            }
        }
    }
}



