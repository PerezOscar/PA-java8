import java.io.*;
import java.io.IOException;


public final class Archivo implements IArchivo { 


    private File archivoOrigen = null;
    private File[] contenidoDirectorio = null;
    
  
    public void validarExistencia(String nombreArchivo){
    /*
    Descripcion:   Si nombreArchivo EXISTE y es de tipo Directorio, entonces
		   lee el directorio e imprime su contenido en forma de lista
		   pero sin un orden especifico.
		   
		   Si nombreArchivo EXISTE y es de tipo Archivo, entonces
		   lee las propiedades del archivo y las imprime en forma de lista y
		   finalmente imprime el valor MD5 del archivo, el cual se obtiene 
		   mediante la funcion obtenerMD5() de la interfaz IArchivo
    
		   Si nombreArchivo NO EXISTE, entonces crea un archivo con ese nombre
		   y en la ruta local, es decir; donde se encuentra esta clase Archivo
		   
		   El objetivo de este metodo es la familiarizacion con los metodos 
		   de la clase File del paquete java.io
    */
	archivoOrigen = new File(RUTA_DEFAULT, nombreArchivo);
	
	if(archivoOrigen.exists()){  
	
	    if(archivoOrigen.isDirectory()){
		  contenidoDirectorio = archivoOrigen.listFiles();
		  listarContenido(contenidoDirectorio);
	    }else if(archivoOrigen.isFile()){
		  obtenerAtributos(archivoOrigen);
	    }
	} else {
	    crearArchivo(archivoOrigen);
	}
    }
    
    
    public void crearArchivo(File archivo){
	
        try {
            /*  Creamos un archivo  */
            if (archivo.createNewFile()) {
                System.out.printf("\t Se creo con exito el archivo: \t %s \n", archivo.getName());
            } 
        } catch (IOException e) {
            System.err.println(e);
        }
    }	
	
	
    
    
    public void listarContenido(File[] directorio) {        
        System.out.println("# Listar archivos de Directorios #");
        System.out.println("#--------------------------------#");
        for (File archivoActual : directorio) {
            if (archivoActual.isFile()) {
                System.out.println("\t - " + archivoActual.getName());                  
            } else {
                System.out.println("\t" + archivoActual.getName() + "/ (Directorio)");
            }
        }
    }

    @Override
    public void copiar(String origen){  }

    @Override
    public void copiar(String origen, String destino){  }
  
}