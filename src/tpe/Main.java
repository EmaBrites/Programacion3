package ProgramacionIII.tpe;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		Servicios servicios = new Servicios("src/tpe/datasets/Procesadores.csv", "src/tpe/datasets/Tareas.csv");
		Solucion solucionBacktracking = servicios.asginarTareasConBacktracking(50);
		System.out.println(solucionBacktracking);
		servicios.reiniciarProcesadores();
		Solucion solucionGreedy = servicios.asignarTareasConGreedy(50);
		System.out.println(solucionGreedy);
	}
}
