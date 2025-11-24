public final class Test_CopiarTextoBuffered { 
  /*
    Instrucciones:
		    -Compile y ejecute desde linea de comandos
		    -Se anexa un archivo llamado covid.csv para practicas
		    
		    Ejemplo:
		    # javac Test_CopiarTextoBuffered.java 
		    # java Test_CopiarTextoBuffered covid.csv
			      o
		    # java Test_CopiarTextoBuffered  covid.csv covidDepurado.csv     
  
  */
  
    public static void main(String ArgumentosTeclado[]) { 
	if(ArgumentosTeclado.length > 0) {
	    if(ArgumentosTeclado.length > 1) {	      
		  new CopiarTextoBuffered().copiar(ArgumentosTeclado[0], ArgumentosTeclado[1]);
	    }else {
		  new CopiarTextoBuffered().copiar(ArgumentosTeclado[0]);
	    }
	}else {
		  IArchivo.faltoNombre();
		  return;
	}
    } 
    
    
    
    static {    
	IArchivo.descripcion("CopiarTextoBuffered", "BufferedReader", "BufferedWriter");
    }
}