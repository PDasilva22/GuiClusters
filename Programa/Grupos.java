package com.example.clusters.Programa;
import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
public class Grupos {
	private static Grupo grupoActual;
	private static List<Integer> propiedades;
	private static HashMap <Integer,Grupo> listaGrupos;
	private static List<Grupo> exploradorGrupos;
	
	public static void IniciarSesion() {
		listaGrupos = new HashMap<> ();
		System.out.println(Usuarios.getManejador().getListaGrupos());
		for (int i : Usuarios.getManejador().getListaGrupos()) {
			listaGrupos.put(i, new Grupo(i));
		}
		
	}
	//OrdenarGruposPorAntiguedad
	//OrdenarGruposPorAsignatura
	//OrdenarGruposPorTama�o
	
	//Filtro int asignatura
	public static void ExplorarGrupos(String s, int filtro) {
		switch (filtro) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		default:
			
			break;
		}
		ResultSet res = ControlBD.ConsultarGrupos();
		exploradorGrupos.add(new Grupo(res));
		try {
			while (res.next()) {
				exploradorGrupos.add(new Grupo(res));
			}
		}catch(SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta de grupos");
		}
	}
	
	public static HashMap<Integer,Grupo> getListaGrupos(){
		return listaGrupos;
	}
	
	public static void CrearGrupo(String nombre, String descripcion, int[] participantes, int privacidad) {
		Creable(nombre);
		Grupo g = new Grupo(nombre, descripcion, privacidad);
		listaGrupos.put(g.getId(), g);
		propiedades.add(g.getId());
	}
	private static void Creable(String nombre) {
		if (propiedades.size() >= 5)
			throw new ClustersException("Ya posee 5 grupos");
		int i = 0;
		while (i < propiedades.size()) {
			if (listaGrupos.get(i).getNombre() == nombre)
				throw new ClustersException("Ya posee un grupo con el nombre " + nombre);
			i++;
		}
	}
	public static void IniciarGrupo(int id) {
		grupoActual = listaGrupos.get(id);
		Usuarios.conjGrupActual(grupoActual.getMiembros());
	}
}
