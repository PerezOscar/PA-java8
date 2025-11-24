package hilos.hilos2;


public class E4_Productor implements Runnable {

    E4_MesaBuffet mesaBuffet;

    public E4_Productor(E4_MesaBuffet mesaBuffet) {
        this.mesaBuffet = mesaBuffet;
    }

    @Override
    public void run() {
        int i = 1;
        //System.out.println("Cocinero cocina: " );
        while (true) {
            //System.out.println("Cocinero cocina: " + i);
            System.out.println("Cocinero produce: " + i);
            // Simulamos la produccion de un producto
            mesaBuffet.producir(i++);
            try {
                /* Lo dormimos un tiempo a leatoria en cada ciclo*/
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException ex) {
                /* La logica que cada quien quiera....*/
            }
        }
    }
}
