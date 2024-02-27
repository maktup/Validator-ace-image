package pe.com.ibm.test;
 
import pe.com.ibm.util.UtilCapturaTexto;
import pe.com.ibm.util.UtilComandos;

/**
 * TestAceImageVersion clase JAVA que VALIDAR las versiones LOCAL & REMOTO de la IMAGE de ACE, para saber si se tiene al día y/o se requiere una ACTUALIZACIÓN de IMAGE. 
 * @author cguerra
 **/
 public class TestAceImageVersion{
	
	   /**
	    * main	
	    * @param  argumentos
	    * @throws Exception
	    **/
		public static void main( String[] argumentos ) throws Exception{ 		
		        
			   UtilComandos     objUtilEc  = null;
			   UtilCapturaTexto objUtilCtt = null;
			   
			   //COMANDOS: 
			   String vNombreAppDeploy = "proy-employee-service-crga-is";
			   String vNombreNameSpace = "cp4i-crga"; 
    
			   String vComandoKube_01  = "oc login --token=sha256~1xyUcG5Ss9MpXWNA3M62M0vI8Ule2ByQN0x8hIiFHP0 --server=https://api.tarkin.coc-ibm.com:6443";  //ACTUALIZAR CONSTANTEMENTE. 
			   
			   String vTemplateKube_02 = "kubectl get deployment " + vNombreAppDeploy + " -n " + vNombreNameSpace + " -o yaml | grep image: | awk \"{print $2}\""; 
			   String vComandoKube_02  = vTemplateKube_02.replace( "NOMBRE_APP_DEPLOY", vNombreAppDeploy  ).replace( "NOMBRE_NAMESPACE", vNombreNameSpace ); 
	  	   
	    	   String vRutaLocalDriver = "C:\\CLIENT\\selenium\\chromedriver.exe";  //IMPORTANTE
	    	   String vLinkPaginaWeb   = "https://www.ibm.com/docs/en/app-connect/containers_cd?topic=obtaining-app-connect-enterprise-server-image-from-cloud-container-registry";	   
	    	   String vIdTabla         = "//table[@id='aceimages__table_aceimages']";
			   
	    	   String vNombreImageACELocal     = null;
	    	   String vNombreImageACELocalTrim = null;
	    	   String vVersionImagenLocal      = null;
	    	   String vVersionImagenDisponible = null;
	    	   String vRutaImagenDisponible    = null;
	    	   
			   try{
				   objUtilEc  = new UtilComandos();			
				   objUtilCtt = new UtilCapturaTexto();
				   
				   System.out.println( "" );
				   vNombreImageACELocal     = objUtilEc.procesarEjecucionComando( vComandoKube_01  );
				   
				   System.out.println( "" );
				   vNombreImageACELocal     = objUtilEc.procesarEjecucionComando( vComandoKube_02  );
				   
				   System.out.println( "" );
				   System.out.println( "---------- [REPORTE COMPARATIVO DE: 'ACE IMAGE'] ----------" );
				   System.out.println( "" );
				   
				   vNombreImageACELocalTrim = (vNombreImageACELocal.trim() + "");
				   System.out.println( "- RUTA ACE (LOCAL): [" + vNombreImageACELocalTrim + "]" );
                   
				   vVersionImagenLocal = objUtilCtt.obtenerValorEnCadena( vNombreImageACELocalTrim );
				   System.out.println( "- IMAGE: ACE (LOCAL): [" + vVersionImagenLocal + "]" );
				   System.out.println( "" );
				   
		    	   String[] arrayCadena = objUtilCtt.obtenerValorTablaPaginaWeb( vRutaLocalDriver, vLinkPaginaWeb, vIdTabla );
		    	   vVersionImagenDisponible = arrayCadena[ 0 ];
		    	   vRutaImagenDisponible    = arrayCadena[ 1 ];
		    	   		    	   
		    	   System.out.println( "- RUTA: ACE (DISPONIBLE): [" + vRutaImagenDisponible    + "]" );
		    	   System.out.println( "- IMAGE ACE (DISPONIBLE): [" + vVersionImagenDisponible + "]" );
		    	   System.out.println( "" );
		    	   
		    	   String vMensajeFinal = objUtilCtt.compararVersionesACE12( vVersionImagenLocal, vVersionImagenDisponible );
		    	   System.out.println( "===>: " + vMensajeFinal );
		    	   
		    	   System.out.println( "" );
		    	   System.out.println( "---------- [REPORTE COMPARATIVO DE: 'ACE IMAGE'] ----------" );		       
		    	   System.out.println( "" );
			   }
			   catch( Exception e ){
					  e.printStackTrace();
					  System.err.println( "ERROR al ejecutar el COMANDO: " + e.getMessage() ); 
			   } 
		}	 

   }
