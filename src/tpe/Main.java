package tpe;

import java.util.List;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("../tpe/datasets/Procesadores.csv", "../tpe/datasets/Tareas.csv");
	servicios.printTareas();
	Tarea tareaId =  servicios.servicio1("T3");
	System.out.println("Tarea por id: " + tareaId.toString());

	List<Tarea> tareasCriticas =  servicios.servicio2(true);
		System.out.println("Tareas criticas:" + tareasCriticas.toString());
	List<Tarea> tareasNoCriticas =  servicios.servicio2(false);
		System.out.println("Tareas no criticas" + tareasNoCriticas.toString());
	List<Tarea> tareasPorNivel =  servicios.servicio3(1,65);
		System.out.println("Tareas entre niveles" + tareasPorNivel.toString());
	}
}
