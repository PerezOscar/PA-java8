public final class Test_CopiarTextoBajoNivel { 
  
  /*
    Instrucciones:
		    -Compile y ejecute desde linea de comandos
		    -Se anexa un archivo llamado covid.csv para practicas
		    
		    Ejemplo:
		    # javac Test_CopiarTextoBajoNivel.java 
		    # java Test_CopiarTextoBajoNivel covid.csv
			      o
		    # java Test_CopiarTextoBajoNivel  covid.csv covidDepurado.csv     
  
  */
    
    public static void main(String ArgumentosTeclado[]) { 
	
	if(ArgumentosTeclado.length > 0) {
	    if(ArgumentosTeclado.length > 1) {	      
		  new CopiarTextoBajoNivel().copiar(ArgumentosTeclado[0], ArgumentosTeclado[1]);
	    }else {
		  new CopiarTextoBajoNivel().copiar(ArgumentosTeclado[0]);
	    }
	}else {
		  IArchivo.faltoNombre();
		  return;
	}
    } 
    
    
    
    static {    
	IArchivo.descripcion("CopiarTextoBajoNivel", "FileReader", "FileWriter");
    }
}