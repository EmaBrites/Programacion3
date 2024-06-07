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
        this.resulTiempoFinalEjecucion = Integer.MAX_VALUE;
        this.estadosGenerados = 0;
        this.tareas = new ArrayList<>(tareas);
        this.procesadores = new ArrayList<>(procesadores);
        this.procesadoresListos = new ArrayList<>();
    }

    public Solucion iniciarBacktracking() {
        this.ejecutarBacktracking(tareas, this.procesadores);
        return new Solucion(this.procesadoresListos, this.resulTiempoFinalEjecucion, this.estadosGenerados);
    }

    private void ejecutarBacktracking(List<Tarea> tareas, List<Procesador> procesadores) {
        this.estadosGenerados++;
        if (tareas.isEmpty()) {
            ArrayList<Procesador> solucionActual = new ArrayList<>();
            int mayorTiempo = 0;
            for (Procesador procesador : procesadores) {
                if (mayorTiempo < procesador.getTiempoEjecucion()) {
                    mayorTiempo = procesador.getTiempoEjecucion();
                }
                solucionActual.add(new Procesador(procesador.getId(),
                        procesador.getCodigoProcesador(),
                        procesador.getAnioFuncionamiento(),
                        procesador.getRefrigerado(),
                        procesador.getTiempoEjecucion(),
                        procesador.getTiempoMaximo(),
                        procesador.getTareasAsignadas()));
            }
            if (mayorTiempo < this.resulTiempoFinalEjecucion) {
                procesadoresListos.clear();
                procesadoresListos.addAll(solucionActual);
                this.resulTiempoFinalEjecucion = mayorTiempo;
            }
        } else {
            while (!tareas.isEmpty()) {
                Tarea tActual = tareas.get(0);
                for (Procesador pActual : procesadores) {
                    if (pActual.puedeAgregarTarea(tActual)) {
                        pActual.addTarea(tActual);
                        tareas.remove(tActual);
                        if (pActual.getTiempoEjecucion() < this.resulTiempoFinalEjecucion) {
                            ejecutarBacktracking(tareas, procesadores);
                        }
                        pActual.removeTarea(tActual);
                        tareas.add(tActual);
                    }else {
                        return;
                    }
                }
            }
        }
    }
}


