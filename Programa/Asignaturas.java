package com.example.clusters.Programa;

import com.example.clusters.DataTypes.Asignatura;
import com.example.clusters.DataTypes.Carrera;
import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Asignaturas {
	private static HashMap <Integer,Asignatura> asignaturasBD;
	private static HashMap <Integer,Carrera> carrerasBD;
	
	public static void IniciarSesion() {
		asignaturasBD = new HashMap<>();
		ResultSet res = ControlBD.ConsultarListadoAsignaturas();
		try {
			asignaturasBD.put(res.getInt("ID"), new Asignatura(res));
			while (res.next()) {
				asignaturasBD.put(res.getInt("ID"), new Asignatura(res));
			}
		} catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta de ASIGNATURA");
		}
		carrerasBD = new HashMap<>();
		res = ControlBD.ConsultarListadoCarreras();
		try {
			carrerasBD.put(res.getInt("ID"), new Carrera(res));
			while (res.next()) {
				carrerasBD.put(res.getInt("ID"), new Carrera(res));
			}
		} catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta de CARRERA");
		}
	}
	
	
	
	public static Asignatura getAsignatura(int codigo) {
		if (asignaturasBD.containsKey(codigo)&&
				asignaturasBD.get(codigo)!=null) {
			return asignaturasBD.get(codigo);
		}
		else {
			throw new ClustersException("La asignatura no ha podido ser encontrada");
		}
	}
	
	
	public static HashMap <Integer,Asignatura> getListaAsignaturas() {
		return Asignaturas.asignaturasBD;
	}

	public static Carrera getCarrera(int carrera) {
		
		return carrerasBD.get(carrera);
	}

	public static HashMap<Integer, Carrera> getListaCarrerasBD() {
		return carrerasBD;
	}
	
	
}