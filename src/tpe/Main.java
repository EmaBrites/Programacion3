package ProgramacionIII.tpe;

import java.util.List;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("src/tpe/datasets/Procesadores.csv", "src/tpe/datasets/Tareas.csv");
		servicios.asginarTareasConBacktracking(50);
		servicios.reiniciarProcesadores();
		Solucion solucionGreedy = servicios.asignarTareasConGreedy(50);
		System.out.println(solucionGreedy);
	}
}
