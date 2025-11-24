package hilos.hilos2;

public class E2_Taqueros implements Runnable {

    private static int ordenesDespachadas = 0;
    private static int tiempoTotal = 0;
    private final char orden;
    private final int cantidad;
    private final int numeroOrden;

    public E2_Taqueros(char producto, int cantidad) {
        this.orden = producto;
        this.cantidad = cantidad;
        this.numeroOrden = ++ordenesDespachadas;
        tiempoTotal += cantidad;
    }

    @Override
    public void run() {
        atenderOrden();
    }

    public void atenderOrden() {
        System.out.print("[" + this.numeroOrden + "]");
        try {
            for (int i = 1; i <= this.cantidad; i++) {
                //Descomente la siguiente linea si desea ver el avance parcial
                //System.out.print("\t "+ this.orden + "(" + i + ")");

                //Simulamos 1 segundo de tiempo, para cada producto comprendido en cada orden
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
        }
        System.out.print("\n\t===> Sale la Orden: " + this.numeroOrden + "\t( Producto: " + this.orden + "\t Cantidad: " + this.cantidad + " )");
    }

    public static void imprimirResumen() {
        System.out.println("\n\n#################################################");
        System.out.println("#\t\t Resumen de ventas \t\t#");
        System.out.println("#-----------------------------------------------#");
        System.out.println("# Ordenes Atendidas: \t\t" + ordenesDespachadas);
        System.out.println("# Tiempo serial (1 Taquero): \t" + tiempoTotal + " segundos");
    }

    public static int getTiempoTotal() {
        return tiempoTotal;
    }

    static {
        System.out.print("# Trabajando la orden: \t");
    }
}
