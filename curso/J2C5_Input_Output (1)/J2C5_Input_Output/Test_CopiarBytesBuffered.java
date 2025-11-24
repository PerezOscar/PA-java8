

public final class Test_CopiarBytesBuffered { 
  
   
  /*
    Instrucciones:
		    -Compile y ejecute desde linea de comandos
		    -Se anexa un archivo llamado covid.csv para practicas
		     pero usted puede probar con el archivo que desee (mp3, mp4, wav, pdf, etc.)
		     
		    Ejemplo:
		    # javac Test_CopiarBytesBuffered.java 
		    # java Test_CopiarBytesBuffered covid.csv
			      o
		    # java Test_CopiarBytesBuffered  covid.csv covidDepurado.csv     
  
  */
      
   
    public static void main(String ArgumentosTeclado[]) { 
	
	if(ArgumentosTeclado.length > 0) {
	    if(ArgumentosTeclado.length > 1) {	      
		  new CopiarBytesBuffered().copiar(ArgumentosTeclado[0], ArgumentosTeclado[1]);
	    }else {
		  new CopiarBytesBuffered().copiar(ArgumentosTeclado[0]);
	    }
	}else {
		  IArchivo.faltoNombre();
		  return;
	}
    } 
    
    
    
    static {    
	IArchivo.descripcion("CopiarBytesBuffered", "BufferedInputStream", "BufferedOutputStream");
    }
}