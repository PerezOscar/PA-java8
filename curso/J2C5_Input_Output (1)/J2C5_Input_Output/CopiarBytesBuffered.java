import java.io.*;
import java.io.IOException;


public final class CopiarBytesBuffered implements IArchivo {  

    private String nombreArchivoOrigen = null;
    private String nombreArchivoDestino = null;
    private File objetoFile_archivoOrigen = null;
    private File objetoFile_archivoDestino = null;
    private BufferedInputStream archivoOrigen = null;
    private BufferedOutputStream archivoDestino = null;
    private int bloqueBytes =0; 
    private long tiempoInicio, tiempoFin;
     
    @Override
    public void copiar(String origen){  	
	nombreArchivoOrigen = origen;
        nombreArchivoDestino = "copia_" + nombreArchivoOrigen; 

	objetoFile_archivoOrigen = new File(RUTA_DEFAULT, nombreArchivoOrigen);
	
	if(!verificarExistencia(objetoFile_archivoOrigen)){ 
	    System.out.printf(" %s \n", nombreArchivoOrigen);
	    return;
	}             
	objetoFile_archivoDestino = new File(RUTA_DEFAULT, nombreArchivoDestino); 
        
        try {            
            archivoOrigen = new BufferedInputStream(new FileInputStream(objetoFile_archivoOrigen));
            archivoDestino = new BufferedOutputStream(new FileOutputStream(objetoFile_archivoDestino));
            
            tiempoInicio = System.nanoTime();
            /* Lee el contenido del archivoOrigen  y lo escribe en archivoDestino */
            while ((bloqueBytes = archivoOrigen.read()) != -1) {
                archivoDestino.write(bloqueBytes);
            }
            tiempoFin = System.nanoTime();
            
            archivoOrigen.close();
            archivoDestino.close();            
            
        } catch (IOException e) {
            System.err.println(e);
        } finally {        
	    imprimirSalida(nombreArchivoOrigen, nombreArchivoDestino, tiempoInicio, tiempoFin);  
        } 
    }
  
  
    @Override
    public void copiar(String origen, String destino){   
	nombreArchivoOrigen = origen;
        nombreArchivoDestino = destino; 
        
	objetoFile_archivoOrigen = new File(RUTA_DEFAULT, nombreArchivoOrigen);
	    
	if(!verificarExistencia(objetoFile_archivoOrigen)){ 
	    System.out.printf(" %s \n", nombreArchivoOrigen);
	    return;
	}        
        
        objetoFile_archivoDestino = new File(RUTA_DEFAULT, nombreArchivoDestino);
        
	try {
            archivoOrigen = new BufferedInputStream(new FileInputStream(objetoFile_archivoOrigen));
            archivoDestino = new BufferedOutputStream(new FileOutputStream(objetoFile_archivoDestino));
            
            tiempoInicio = System.nanoTime();
            /* Lee el contenido del archivoOrigen  y lo escribe en archivoDestino */
            while ((bloqueBytes = archivoOrigen.read()) != -1) {
                archivoDestino.write(bloqueBytes);
            }
            tiempoFin = System.nanoTime();
            
            archivoOrigen.close();
            archivoDestino.close();
            
        } catch (IOException e) {
            System.err.println(e);
        } finally {        
	    imprimirSalida(nombreArchivoOrigen, nombreArchivoDestino, tiempoInicio, tiempoFin);  
        } 
    }
 
}