package pe.com.ibm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * UtilCapturaTexto
 * @author cguerra
 **/
 public class UtilCapturaTexto{
	
   /**
    * main	
    * @param argumentos
    **/
    public static void main( String[] argumentos ){
    	
    	   UtilCapturaTexto x = new UtilCapturaTexto();
    	   
    	   String rutaDriverParam = "C:\\CLIENT\\selenium\\chromedriver.exe";
    	   String paginaWebParam  = "https://www.ibm.com/docs/en/app-connect/containers_cd?topic=obtaining-app-connect-enterprise-server-image-from-cloud-container-registry";	   
    	   String idTablaParam    = "//table[@id='aceimages__table_aceimages']";
    			    	    	   
    	   String[] arrayCadena = x.obtenerValorTablaPaginaWeb( rutaDriverParam, paginaWebParam, idTablaParam );
    	   System.out.println( "- VERSIÓN: [" + arrayCadena[0] + "]" );
    	   System.out.println( "- RUTA: ["    + arrayCadena[1] + "]" );
    }
    
   /**
    * obtenerValorTablaPaginaWeb
    * @param  rutaDriverParam
    * @param  paginaWebParam
    * @param  idTablaParam
    * @return String[]
    **/    
    public String[] obtenerValorTablaPaginaWeb( String rutaDriverParam, String paginaWebParam, String idTablaParam ){
    	   
    	   ChromeOptions objDriverOptions = null;
    	   WebDriver     objDriver        = null; 
    	   WebElement    objWebElment     = null;
    	   String        vTextoObtenido   = null;   	   
           String        vVersionImagen   = null;
           String        vRutaImagen      = null;
    	   int           vFilaElegida     = 2;           
    	   String[]      arrayRespuesta   = new String[2];
    	   String[]      arrayTextoSaltoLinea = null;     	   
    	   String[]      arrayNewCadenaTexto  = null;
    	   String        vCadenaTexto         = null; 
    	   
	       try{
	    	   System.setProperty( "webdriver.chrome.driver", rutaDriverParam );
	    
	           objDriverOptions     = new ChromeOptions();
	           objDriverOptions.addArguments( "--headless" );    //Ejecutar en modo headless.
	           objDriverOptions.addArguments( "--disable-gpu" ); //Deshabilitar la aceleración de hardware.
	    	   
	    	   objDriver            = new ChromeDriver( objDriverOptions );
	           objDriver.get( paginaWebParam );
	           
	    	   objWebElment         = objDriver.findElement( By.xpath( idTablaParam ) );	
	    	   vTextoObtenido       = objWebElment.getText(); 
	    	   arrayTextoSaltoLinea = vTextoObtenido.split( "\\n" );

	           //IMPRIMIR TEXTO CON SALTO DE LINEA:
	           //for( String linea : arrayTextoSaltoLinea ){
	                //System.out.println( "===>" + linea);
	           //}	    	   	    	   
	    	   
	           //System.out.println( arrayTextoSaltoLinea.length );
	            
	           for( int i=0; i<arrayTextoSaltoLinea.length; i++ ){
	        	    vCadenaTexto = arrayTextoSaltoLinea[ i ];	        	    
	        	    
	        	    if( i == vFilaElegida ){  
	        	    	//System.out.println( vCadenaTexto );
	        	    	
	        	    	arrayNewCadenaTexto = vCadenaTexto.split( "\\s+" );
	        	    	vVersionImagen      = arrayNewCadenaTexto[ 0 ]; 
	        	    	vRutaImagen         = arrayNewCadenaTexto[ 1 ]; 
	        	    	
	        	    	//System.out.println( "->" +vVersionImagen );
	        	    	//System.out.println( "->" +vRutaImagen    );
	        	    	
	        	    	break;
	        	    }	        	    
	           }
    	   
	    	   arrayRespuesta[ 0 ] = vVersionImagen;
	    	   arrayRespuesta[ 1 ] = vRutaImagen;	 	   
	       }
	       catch( Exception e ){
				  e.printStackTrace();
	       }
	       catch( Throwable e ){
				  e.printStackTrace();
		   } 
	       finally{
	    	       objDriver.quit();	    	   
	       }
	       
	       return arrayRespuesta ;
     }  
    
    /**
     * compararVersionesACE12
     * @param  versionParam01
     * @param  versionParam02
     * @return String
     **/
     public String compararVersionesACE12 ( String versionParam01, String versionParam02 ){
	 
	        int    vResultado      = versionParam01.compareTo( versionParam02 );
	        String vResultadoTexto = null;
	         
	        if( vResultado < 0 ){
	        	vResultadoTexto = "La VERSIÓN (LOCAL): [" + versionParam01 + "] es MENOR que la VERSIÓN (DISPONIBLE): [" + versionParam02 + "],.... SI se requiere realizar una ACTUALIZACIÓN de IMAGE."; 
	        } 
	        else if( vResultado > 0 ){
	 	        	 vResultadoTexto = "La VERSIÓN (LOCAL): [" + versionParam01 + "] es MAYOR que la VERSIÓN (DISPONIBLE): [" + versionParam02 + "],.... NO se requiere realizar una ACTUALIZACIÓN de IMAGE."; 
	        }
	        else{
	        	 vResultadoTexto = "Ambas VERSIONES de IMAGE son IGUALES (NO requiere ACTUALIZAR).";
	        }
	        
	        return vResultadoTexto;
     }
     
    /**
     * obtenerValorEnCadena
     * @param  vNombreImageACELocalParam
     * @return String
     **/
     public String obtenerValorEnCadena( String vNombreImageACELocalParam ){
     
	        Pattern pattern = Pattern.compile( ":([^\\\\-]*-[^\\\\-]*)" );
	        Matcher matcher = pattern.matcher( vNombreImageACELocalParam.trim() + "" );

	        String valorDeseado = null;
	        
	        if( matcher.find() ){
	            valorDeseado = matcher.group( 1 );
	            //System.out.println( "Valor Deseado: " + valorDeseado);
	        } 
	        else{
	             System.out.println("No se encontró el patrón en la cadena.");
	        }
	        
	        return valorDeseado;
      }    
     
  }
