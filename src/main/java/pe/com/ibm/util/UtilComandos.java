package pe.com.ibm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * UtilComandos
 * @author cguerra
 **/
 public class UtilComandos{

	private static String SISTEMA_OPERATIVO = System.getProperty( "os.name" ).toLowerCase(); 
	private static int    MAX_TIMEOUT       = 300; 
	
   /**
    * main	
    * @param  argumentos
    * @throws Exception
    **/
	public static void main( String[] argumentos ) throws Exception{ 		
	        
		   UtilComandos x = null;
 
		   //COMANDOS: 
		   String NOMBRE_APP_DEPLOY = "proy-employee-service-crga-is";
		   String NOMBRE_NAMESPACE  = "cp4i-crga";
		   
		   String NOMBRE_IMAGEN     = "cp.icr.io/cp/appc/ace-server-prod";
		   String NOMBRE_ENT_KEY    = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJJQk0gTWFya2V0cGxhY2UiLCJpYXQiOjE2OTAzMjI2NDYsImp0aSI6IjhiMjg5MmQ0NTljZDQwYmRhMzJkNmJiNjc1MTRmMjVkIn0.dlpBDFU4Hv2mTNNe4Ma2QNo7AdBW-Oh75JoWa-6VjlM"; 
		   String NOMBRE_HOST       = "cp.icr.io";
		   		   
		   String vTemplate_01 = "oc get deployment proy-employee-service-crga-is -n cp4i-crga -o yaml | grep image: | awk \"{print $2}\"";
		   String vTemplate_02 = "docker login cp.icr.io -u cp -p NOMBRE_ENT_KEY";
		   String vTemplate_03 = "docker pull NOMBRE_IMAGEN";
		   String vTemplate_04 = "docker images | grep NOMBRE_HOST | awk \"{print $2}\"";
		   String vTemplate_05 = "docker rmi NOMBRE_IMAGEN"; 
		   
		   String vComando_01  = vTemplate_01.replace( "NOMBRE_APP_DEPLOY", NOMBRE_APP_DEPLOY ).replace( "NOMBRE_NAMESPACE", NOMBRE_NAMESPACE );
		   String vComando_02  = vTemplate_02.replace( "NOMBRE_ENT_KEY",    NOMBRE_ENT_KEY );
		   String vComando_03  = vTemplate_03.replace( "NOMBRE_IMAGEN",     NOMBRE_IMAGEN  );
		   String vComando_04  = vTemplate_04.replace( "NOMBRE_HOST",       NOMBRE_HOST    );
		   String vComando_05  = vTemplate_05.replace( "NOMBRE_IMAGEN",     NOMBRE_IMAGEN  );
		   
		   System.out.println( vComando_01 );
		   System.out.println( vComando_02 );  
		   System.out.println( vComando_03 ); 
		   System.out.println( vComando_04 ); 
		   System.out.println( vComando_05 ); 
		   System.out.println( "" );

		   try{
			   x = new UtilComandos();			
	           
			   x.procesarEjecucionComando( vComando_01 );
	           x.procesarEjecucionComando( vComando_02 );
	           //x.procesarEjecucionComando( vComando_03 );
	           //x.procesarEjecucionComando( vComando_04 );
	           //x.procesarEjecucionComando( vComando_05 );
	            
		   }
		   catch( Exception e ){
				  e.printStackTrace();
				  System.err.println( "ERROR al ejecutar el COMANDO: " + e.getMessage() ); 
		   } 
	}
 
   /**
    * procesarEjecucionComando
    * @param  vComandoParam	
    * @return String
    **/
	public String procesarEjecucionComando( String vComandoParam ){
 
		   ExecutorService objExecutorService = Executors.newSingleThreadExecutor();
		   Future<String>  objFuture          = null; 
		   String          vConsolaOutput     = null; 
			
		   try{
			   objFuture      = objExecutorService.submit(() -> ejecutarComandoNativo( vComandoParam ) );			
			   vConsolaOutput = objFuture.get( MAX_TIMEOUT, TimeUnit.SECONDS );  //Tiempo máximo de ejecución: 10 segundos 
				
			   System.out.println( "- COMANDO a EJECUTAR: [" + vComandoParam + "]" );
			   System.out.println( vConsolaOutput );
		   }
		   catch( Exception e ){
				  e.printStackTrace();
				  System.err.println( "ERROR al ejecutar el COMANDO: " + e.getMessage() );
				  objFuture.cancel( true );
		   }
		   finally{
				   objExecutorService.shutdown();
		   } 
		   
		   return vConsolaOutput;
	} 
	
   /**
    * ejecutarComandoNativo 	
    * @param  comandoParam
    * @return String
    * @throws Exception
    **/
	private static String ejecutarComandoNativo( String comandoParam ) throws Exception{
		
		Process        objProcess        = null;
		ProcessBuilder objProcessBuilder = null;
		String[]       arrayComando      = null;
		
		try{
			comandoParam      = "\"" + comandoParam + "\"";
			objProcessBuilder = new ProcessBuilder();		
			arrayComando      = new String[3];
			
			if( SISTEMA_OPERATIVO.contains( "windows" ) ){
				arrayComando[0] = "cmd.exe";
				arrayComando[1] = "/c";
				arrayComando[2] = comandoParam;
			}
			else{
			 	 arrayComando[0] = "/bin/bash";
				 arrayComando[1] = "-c";
				 arrayComando[2] = comandoParam;
			}
	 
			objProcessBuilder.command( arrayComando ); 
			objProcessBuilder.redirectErrorStream( true );
						
			objProcess = objProcessBuilder.start();		
			objProcess.waitFor( MAX_TIMEOUT, TimeUnit.SECONDS );
		}
		catch( Exception e ){
			   e.printStackTrace();
			   System.err.println( "ERROR al ejecutar el COMANDO: " + e.getMessage() );
		}
		
		try( BufferedReader objReader        = new BufferedReader( new InputStreamReader( objProcess.getInputStream() ) ); ){ 
			 StringBuilder  objConsolaOutput = new StringBuilder();
			 
			 String vLinea; 
			 while( (vLinea = objReader.readLine()) != null ){
			 	    objConsolaOutput.append( vLinea );
			 	    objConsolaOutput.append( "\n"  ); 
			 }
			 
			 return objConsolaOutput.toString();
		}
	 }	
	
  }
 
