package agenda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Agenda {
	  
	  static String p=" ";
	 static SortedMap<String,String>agenda=new TreeMap<>();
	
	  
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
    BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
   
    String nombre=" ";
    String token=" ";
   String ruta=" ";
    String telefono=" ";
    
    boolean fin=false;
    do {
    	System.out.println(">");
    	Scanner sc=new Scanner(bf.readLine());
    	
    	//System.out.println(token);
    	
    	int estado=0;
    	while(estado!=8) {
    		switch(estado) {
    		case 0:
				try {
					token = sc.skip("fin|buscar:|borrar:|guardar:|cargar:|\\p{L}+(\\s+\\p{L}+)*").match().group();
					if (token.equals("fin")) {
						estado = 8;
						fin = true;
					}
					else if (token.equals("buscar:"))
						estado = 4;
					else if (token.equals("borrar:"))
						estado = 3;
					else if (token.equals("guardar:"))
						estado = 2;
					else if (token.equals("cargar:"))
						estado = 7;
					else {
						nombre = token;
						estado = 1;
					}
				} catch (NoSuchElementException e) {
					System.out.println("Se esperaba 'buscar: o guardar: o borrar: o cargar:' o 'fin' o un nombre");
					estado = 8;
				}
				break;
			case 1:
				try {
					sc.skip("-");
					estado = 5;
				}catch (NoSuchElementException e) {
					System.out.println("Se esperaba '-'");
					estado = 8;
				}
				break;
			case 2:
			
				try {
				//C:/Users/Ana/prueba/agenda
				
					
					ruta=sc.skip("C:/\\w+/\\w+/\\w+/\\w+").match().group();
					System.out.println(ruta);
					 escribir(ruta);
					 System.out.println("Se han guardado los datos correctamente");
					estado = 8;
				}catch (NoSuchElementException e) {
					System.out.println("Se esperaba 'ruta'");
					estado = 8;
				}
				
				
             break;
			case 3:
				try {
					nombre=sc.skip("\\p{L}+").match().group();
					if(agenda.containsKey(nombre)) {
						agenda.remove(nombre);
						System.out.println("se borro");
					}else {
						System.out.println("no existe");
					}
					estado = 8;
				}catch (NoSuchElementException e) {
					System.out.println("Se esperaba 'nombre borar'");
					estado = 8;
				}
             break;
			case 4:
				
				try {
					nombre=sc.skip("\\p{L}+").match().group();
				    //telefono=agenda.get(token);
					
					if(agenda.containsKey(nombre)) {//si contiene el nombre 
						telefono=agenda.get(nombre);
						System.out.println(nombre+"->"+telefono);//nos da el telefono
					}else {
						System.out.println("no esta en la agenda "+nombre);//no esta en la agenda
					}
					
					
					
					estado=8;
				}catch (NoSuchElementException e) {
					System.out.println("Se esperaba 'nombre'");
					estado = 8;
				}
             break;
			case 5:
				try {
				 telefono=sc.skip("\\d+").match().group();
				 if(!agenda.containsKey(nombre)) {
					 agenda.put(nombre, telefono);
				System.out.println("se inserto "+nombre+"->"+telefono);
					 
				 }else {
					 agenda.put(nombre, telefono);
						System.out.println("se actualizo "+nombre+"->"+telefono);
				 }
				 
				estado=8;
				}catch (NoSuchElementException e) {
					System.out.println("Se esperaba 'telefono'");
					estado = 8;
				}
             break;
			case 7:
				
					//C:/Users/Ana/prueba/agenda
					
						try {
						ruta=sc.skip("C:/\\w+/\\w+/\\w+/\\w+").match().group();
						System.out.println(ruta);
						 leer(ruta);
						 estado=8;
						}catch (NoSuchElementException e) {
							System.out.println("Se esperaba ruta");
							estado = 8;
						}
					
				break;
    		}
    		
    		
    		}
    	
    	}while(fin!=true);
    
     System.out.println("--------");
    
    
	}
	public static void escribir(String ruta)  {
		/*
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
		oos.writeObject(agenda);
		oos.close();
		*/
		
		FileWriter fw;
		try {
			fw = new FileWriter(ruta);
			BufferedWriter bw= new BufferedWriter(fw);
			PrintWriter pw=new PrintWriter(bw);
			
			Set<String> conjuntoClaves=agenda.keySet();
			Iterator<String> it=conjuntoClaves.iterator();
			
			while (it.hasNext()) {
				String clave=(String) it.next();
				String tf= agenda.get(clave);
				pw.println(clave+"-"+tf);
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Error al buscar el fichero para escribir");
		}
		
		
	}
	public static void leer(String ruta)  {
		/*
		ObjectInputStream llerFichero=new ObjectInputStream(new FileInputStream(ruta));
		
	    Map agenda2 =(Map<String,String>)llerFichero.readObject();
	    
	     llerFichero.close();
	     System.out.println("datos fichero :");
	     System.out.println(agenda2);
		
	}
	//https://jnjsite.com/java-serializando-objetos-para-guardar-y-recuperar-en-ficheros/
	*/
		File f=new File(ruta);
		try {
			FileReader fr=new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			String linea;
			while ((linea=br.readLine())!=null) {
				String [] datos=linea.split("-");
				agenda.put(datos[0], datos[1]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el fichero");
		} catch (IOException e) {
			System.out.println("No existe el fichero, inserte datos en la agenda y guárdelos");
		} 
		
	}
}


