package com.example.clusters.DataTypes;

import com.example.clusters.Raiz.ClustersException;
public class Fichero{
	private String nombre;
	private String formato;
	private int tamano;
	private String direccion;// Rellenar despues
	
	// Constructor Temporal (no contiene un archivo)
	public Fichero(String nombreArchivo, String direccion) { 			
		boolean puntoEncontrado = false;
		int posicionPunto = -1;
		for (int i=nombreArchivo.length()-1; i>0 ;i--) {
			if (!puntoEncontrado&&nombreArchivo.charAt(i)=='.') {
				puntoEncontrado=true;
				posicionPunto=i;
			}
		}
		if (posicionPunto ==-1) {
			throw new ClustersException("No se ha podido encontrar el punto en el nombre. Error en la instancia del fichero.");
		}
		else {
			StringBuilder sb  = new StringBuilder ();
			for (int i=posicionPunto;i< nombreArchivo.length();i++) {
				sb.append(nombreArchivo.charAt(i));
			}
			this.formato= sb.toString();
		}
		StringBuilder sb  = new StringBuilder ();
		for (int i=0; i<posicionPunto ;i++) {
			sb.append(nombreArchivo.charAt(i));
		}
		this.nombre = sb.toString(); 
		this.direccion= direccion;
	}
	
	//BUSCAR COMO ASIGNAR LA DIRECCION PARA EL ARCHIVO EN LA BBDD
	/*public Fichero(String nombreArchivo) { 			
		boolean puntoEncontrado = false;
		int posicionPunto = -1;
		for (int i=nombreArchivo.length(); i>0 ;i--) {
			if (!puntoEncontrado&&nombreArchivo.charAt(i)=='.') {
				puntoEncontrado=true;
				posicionPunto=i;
			}
		}
		if (posicionPunto ==-1) {
			throw new ClustersException("No se ha podido encontrar el punto en el nombre. Error en la instancia del fichero.");
		}
		else {
			StringBuilder sb  = new StringBuilder ();
			for (int i=posicionPunto;i< nombreArchivo.length();i++) {
				sb.append(nombreArchivo.charAt(i));
			}
			this.formato= sb.toString();
		}
		StringBuilder sb  = new StringBuilder ();
		for (int i=0; i<posicionPunto ;i++) {
			sb.append(nombreArchivo.charAt(i));
		}
		this.nombre = sb.toString(); 
		//GENERAR DIRECCION AQUI
	}
	*/

	public String getFormato() {
		return this.formato;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getNombreCompleto() {
		return this.nombre + "." + this.formato;
	}

	public int getTamano() {
		return this.tamano;
	}

}
