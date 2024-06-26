package ProgramacionIII.tpe;

import ProgramacionIII.tpe.utils.CSVReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
    HashMap<String, Tarea> tareasCriticas = new HashMap<>();
    HashMap<String, Tarea> tareasNoCriticas = new HashMap<>();

    List<Procesador> procesadores = new ArrayList<>();
    List<Tarea> tareasAsignar = new ArrayList<>();

    /*
     * La complejidad temporal del constructor seria O(n) donde n es el maximo entre cantidad de tareas y
     * cantidad de procesadores n=Max(|T|,|P|).
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        ArrayList<Procesador> procesadores = reader.readProcessors(pathProcesadores);
        this.tareasAsignar = reader.readTasks(pathTareas);
        for (Tarea tarea : this.tareasAsignar) {
            if (tarea.getEsCritica()) {
                tareasCriticas.put(tarea.getId(), tarea);
            } else {
                tareasNoCriticas.put(tarea.getId(), tarea);
            }
        }
        this.procesadores.addAll(procesadores);
    }

    /*
     * * El servicio 1 tiene una complejidad de O(1) ya que la búsqueda en una tabla hash tiene un tiempo de acceso constante
     */
    public Tarea servicio1(String ID) {
        if (tareasCriticas.containsKey(ID)) {
            return tareasCriticas.get(ID);
        } else {
            return tareasNoCriticas.get(ID);
        }
    }

    /*
     * El servicio 2 tiene una complejidad de O(1) ya que al contar con los dos hashmap con las tareas dividas
     *  entre criticas y no criticas solo debemos retornar los valores del hashmap que esten buscando.
     */
    public List<Tarea> servicio2(boolean esCritica) {
        if (esCritica) {
            return new ArrayList<>(tareasCriticas.values());
        } else {
            return new ArrayList<>(tareasNoCriticas.values());
        }
    }

    /*
     * El servicio 3 tiene una complejidad de O(n) ya que para obtener las tareas entre dos niveles deberiamos
     * recorrer en el peor de los casos el total de las tareas tanto criticas como no criticas.
     */
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> tareas = new ArrayList<>();
        for (Tarea tarea : tareasCriticas.values()) {
            if (tarea.getNivelPrioridad() >= prioridadInferior && tarea.getNivelPrioridad() <= prioridadSuperior) {
                tareas.add(tarea);
            }
        }
        for (Tarea tarea : tareasNoCriticas.values()) {
            if (tarea.getNivelPrioridad() >= prioridadInferior && tarea.getNivelPrioridad() <= prioridadSuperior) {
                tareas.add(tarea);
            }
        }
        return tareas;
    }

    public void printTareas() {
        for (Tarea tarea : tareasCriticas.values()) {
            System.out.println(tarea.toString());
        }
        for (Tarea tarea : tareasNoCriticas.values()) {
            System.out.println(tarea.toString());
        }
    }

    public Solucion asginarTareasConBacktracking(Integer maxTiempoNoRefrigerados) {
        this.procesadores.stream().filter(p -> !p.getRefrigerado()).collect(Collectors.toList()).forEach(p -> p.setTiempoMaximo(maxTiempoNoRefrigerados));
        tareasAsignar.sort(comparadorTiempoEjecucion);
        System.out.println(tareasAsignar);
        Backtracking backtracking = new Backtracking(tareasAsignar, this.procesadores);
        return backtracking.iniciarBacktracking();
    }

    public void printProcesadores() {
        System.out.println(this.procesadores.toString());
    }

    public void reiniciarProcesadores() {
        for (Procesador p : this.procesadores) {
            p.reiniciar();
        }
    }

    public Solucion asignarTareasConGreedy(Integer maxTiempoNoRefrigerados) {
        this.procesadores.stream().filter(p -> !p.getRefrigerado()).collect(Collectors.toList()).forEach(p -> p.setTiempoMaximo(maxTiempoNoRefrigerados));
        tareasAsignar.sort(comparadorTiempoEjecucion);
        Greedy greedy = new Greedy(tareasAsignar, procesadores);
        return greedy.ejecutarGreedy();

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


}