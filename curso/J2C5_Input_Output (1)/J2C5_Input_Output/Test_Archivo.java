
public final class Test_Archivo { 
  
    public static void main(String ArgumentosTeclado[]) { 
	
	if(ArgumentosTeclado.length > 0) { 
	
	    new Archivo().validarExistencia(ArgumentosTeclado[0]);
	
	} else {
		  IArchivo.faltoNombre();
		  return;
	}
	
	/*
	    Experimente con TODOS los metodos de la clase File	
	*/
	
    } 
    
    
    
    static {    
	IArchivo.descripcion("Archivo", "File");
    }
    
}