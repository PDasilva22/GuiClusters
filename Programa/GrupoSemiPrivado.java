package com.example.clusters.Programa;

import com.example.clusters.Raiz.ClustersException;

import java.util.ArrayList;
import java.util.List;

public class GrupoSemiPrivado extends Grupo {
	
	private List<Integer> listaPendientes;
	
	public GrupoSemiPrivado(int id/*, String nombre, String descripcion, Set<Integer> asignaturas, Date fechaCreacion, File foto, String historial, List<Integer> participantes*/) {
		//RECIBIR DATOS DE LA BASE DE DATOS
		super(id/*, nombre, descripcion, asignaturas, fechaCreacion, foto, historial, participantes*/);
		this.privacidad = 0;
		listaPendientes = new ArrayList<>();
	}
	public GrupoSemiPrivado(String nombre, String descripcion) {
		super(nombre, descripcion, 0);
	}
	public List<Integer> getListaPendientes(){
		return listaPendientes;
	}
	public void addListaPendientes(int i) {
		if (!listaPendientes.contains(i))
			listaPendientes.add(i);
		else
			throw new ClustersException("Este usuario con id: " + id + "ya estaba en la lista de pendientes");
	}
	public void RechazarMiembro(int i) {
		if (listaPendientes.contains(i))
			listaPendientes.remove(i);
		else
			throw new ClustersException("Este usuario con id " + id + "no estaba en la lsita de pendientes");
	}
	public void AceptarMiembro(int i) {
		if (listaPendientes.contains(i))
			listaPendientes.remove(i);
		else
			throw new ClustersException("Este usuario con id " + id + "no estaba en la lsita de pendientes");
	}
}
