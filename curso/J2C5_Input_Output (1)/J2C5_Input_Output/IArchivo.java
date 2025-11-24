
import java.io.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;




interface IArchivo {

    String RUTA_DEFAULT = ".";
    
    String SEPARADOR_COLUMNA_DEFAULT = ",";
    
    String ATENCION = "¡¡¡ ATENCION !!! ";
    
    String NO_EXISTE = "No se encontró el archivo:";
    
    String FALTO_NOMBRE = "Faltó el nombre del archivo ";

    
    void copiar(String origen);
    
    void copiar(String origen, String destino);
    
    
    
    default void obtenerAtributos(File archivo) {        
        System.out.println("\t# Propiedades del archivo #");
        System.out.println("\t#-------------------------#");        
        System.out.println("\t length(): \t\t " + archivo.length());
        System.out.println("\t canRead(): \t\t " + archivo.canRead());
        System.out.println("\t canWrite(): \t\t " + archivo.canWrite());
        System.out.println("\t canExecute(): \t\t " + archivo.canExecute());
        System.out.println("\t isHidden(): \t\t " + archivo.isHidden());
        System.out.println("\t isDirectory(): \t " + archivo.isDirectory());
        System.out.println("\t isFile(): \t\t " + archivo.isFile());
        System.out.println("\t getAbsolutePath(): \t " + archivo.getAbsolutePath());
        System.out.println("\t getPath(): \t\t " + archivo.getPath());
        System.out.println("\t getName(): \t\t " + archivo.getName());        
        System.out.println("\t lastModified(): \t " + archivo.lastModified());
        System.out.println("\t lastModified(): \t " + new Date(archivo.lastModified()));
        System.out.println("\t valor md5: \t\t " + obtenerMD5(archivo));
    }    
    
    
    default void imprimirSalida(String origen, String destino, long tiempoInicio, long tiempoFin){
	double tiempoCPU;
	tiempoCPU = (tiempoFin - tiempoInicio) / 1E6;
	System.out.println("Salida:");
	System.out.printf("\t Archivo origen:\t %s \n",  origen ); 
	System.out.printf("\t Archivo destino:\t %s \n", destino ); 	
	System.out.printf("\t Tiempo CPU:\t\t %1.4f milisegundos \n", tiempoCPU); 
    }
    
    default boolean verificarExistencia(File objetoFile){
	boolean existeArchivo = true;
	if(!objetoFile.exists()){            
	    System.out.printf("\t\t %s \n", ATENCION);
	    System.out.printf("\t %s \t", NO_EXISTE);
	    existeArchivo = false;
	} 
	return existeArchivo;
    }
    
    
    
    default String obtenerMD5(File archivo){
	  StringBuffer cadenaMD5 = new StringBuffer();
	  FileInputStream archivoLectura;
	  MessageDigest instanciaMD5;
	  byte[] bloqueFirmado;
	  byte[] bloqueBytes = new byte[512];
	  int byteActual = 0;	  
	  
	  try {
		instanciaMD5 = MessageDigest.getInstance("MD5");
			    
		archivoLectura = new FileInputStream(archivo);
	
		while ((byteActual = archivoLectura.read(bloqueBytes)) != -1) {
		    instanciaMD5.update(bloqueBytes, 0, byteActual);
		}

		archivoLectura.close();
	
		bloqueFirmado = instanciaMD5.digest();	
		
		for (int i = 0; i < bloqueFirmado.length; i++) {
		  
		    cadenaMD5.append(Integer .toString((bloqueFirmado[i] & 0XFF) + 0x100, 16).substring(1));
		}
	  
	  } catch (NoSuchAlgorithmException | IOException e) {
		System.err.println(e);
	  }
	  return cadenaMD5.toString();	
    }
    
    
    static void faltoNombre(){
	  System.out.printf("\t\t %s \n", ATENCION);
	  System.out.printf("\t %s \n", FALTO_NOMBRE);
    } 
    
    static void descripcion(String clase1, String clase2){
	System.out.println("##################################################################################################");
	System.out.println("Descripcion:");
	System.out.printf("\t Se emplea la clase %s que a la vez, es una implementación de la clase %s  \n", clase1, clase2);
	System.out.println("\t -----------------------------------------------------------------------------------------\n");
    }   
        
    static void descripcion(String clase1, String clase2, String clase3){
	System.out.println("############################################################################################");
	System.out.println("Descripcion:");
	System.out.printf("\t Se emplea la clase %s que a la vez, es una implementación \n", clase1);
	System.out.printf("\t de las clases %s   y  %s \n", clase2, clase3);      
	System.out.println("\t --------------------------------------------------------------------------\n");
    }
    

}