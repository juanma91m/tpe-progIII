package tpe;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("src/datasets/Procesadores.csv", "src/datasets/Tareas.csv");
		System.out.println(servicios.servicio1("T1"));
		System.out.println(servicios.servicio2(true));
		System.out.println(servicios.servicio3(10,109));
	}
}
