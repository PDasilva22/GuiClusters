package com.example.clusters.Programa;

import com.example.clusters.Raiz.ClustersException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Filtro {
	
	public static boolean verificarNombre(String s) {
		if (s.length()>0 && s.length()<30 && !malsonante(s) && caracterLetra(s)) {
			return true;
		}
		return false;
	}
	
	public static boolean verificarApellido(String s) {
		if (s.length()>0 && s.length()<20 && !malsonante (s) && caracterLetra(s)) {
			return true;
		}
		return false;
	}
	
	public static boolean verificarContrasenya(String s,String correo,String nombre,String apellido1,
											  String apellido2) {
		if (s.length()>8 && s.length()<20 && !s.contains(correoSinArroba(correo)) && !s.contains (nombre)) {
			return true;
		}
		return false;
	}
	
	public static boolean verificarNick(String s,String correo) {
		if (s.length()>0 && s.length()<20 && !malsonante (s)&& s.contains((correoSinArroba(correo)))) {
			return true;
		}
		return false;
	}
	
	public static boolean caracterNumero (char c) {
		if ((int)c>47&&(int)c<58) {
			return true;
		}
		return false;
	}
	
	public static boolean caracterLetra (String s) {
		for (char c: s.toCharArray()) {
			int x = (int) c;
			if (x<65||x>122||(x>90&&x<97)) {
				return false;
			}
		}
		return true;
	}
	public static boolean noEsTelefono (int tel){
		if (tel > 100000000 && tel <999999999) {
			return false;
		}
		return true;
	}

	public static boolean malsonante (String s){
		try {
			Scanner sc = new Scanner (new File ("Palabras_malsonantes_2.txt"));
			while (sc.hasNext()) {
				if (s.contains(sc.next())) {
					return true;
				}
			}
			sc.close();
			return false;
		}
		catch(FileNotFoundException e) {
			throw new ClustersException ("No se ha podido acceder al filtro de palabras.");
		}
	}
	
	public static String censurarFiltro(String s){
		String [] arraySpliteado;
		StringBuilder sb = new StringBuilder();
			arraySpliteado = s.split(" ");
			for (int i=0; i< arraySpliteado.length ;i++) {
				sb.append(censurar(arraySpliteado[i]));
				sb.append(" ");
			}

		return sb.toString();
	}
	
	public static String censurar(String s) {
		if (malsonante(s)) {
			StringBuilder sb = new StringBuilder();
			for (int i=0;i<s.length();i++) {
				sb.append('*');
			}
			return sb.toString();
		}
		return s;
	}
	
	
	public static String correoSinArroba (String s) {
		StringBuilder sb = new StringBuilder();
		boolean arrobaNoEncontrada=true;
		int contador = 0;
		try {
			while (arrobaNoEncontrada) {
				if (s.charAt(contador)!='@') {
					sb.append (s.charAt(contador));
					contador++;
				}
				else {
					arrobaNoEncontrada=false;
				}
			}
			
		}
		catch (IndexOutOfBoundsException e) {
			throw new ClustersException("El correo introducido no contiene @");
		}
		return sb.toString();
	}
	
	public static boolean urlGoogleValida(String s) {

		if (s.contains("maps.google.com/?")||(s.contains("google.com/maps/"))&&s.contains("/@")||s.contains("maps.google.es/?")||(s.contains("google.es/maps/"))&&s.contains("/@")) {
			return true;
		}
		return false; 
	}
}