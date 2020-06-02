package com.example.clusters.Programa;

import com.example.clusters.Raiz.ClustersException;

import java.util.HashMap;


public class Calendario {
	private static HashMap <Integer,Evento> listaEventos = new HashMap<Integer,Evento>();
	private static HashMap <Integer,Tarea> listaTareas = new HashMap<Integer, Tarea>();
	
	public static void conjListaEventos() {
		for (Integer i: Usuarios.getManejador().getListaEventos()) {
			//listaEventos.put(i, new Evento (i));
		}
	}
	
	public static HashMap<Integer,Evento> getListaEventos() {
		return Calendario.listaEventos;
	}
	
	public static String getNombreEvento(int codigo) {
		if (listaEventos.containsKey(codigo)) {
			return listaEventos.get(codigo).getNombre();
		}
		else {
			throw new ClustersException("El evento no ha podido ser encontrado.");
		}
	}
}