package com.example.clusters.DataTypes;

import com.example.clusters.Programa.Asignaturas;
import com.example.clusters.Raiz.ClustersException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Asignatura {
	
	private int id;
	private String nombre;
	private int carrera;
	
	public Asignatura (ResultSet res) {
		try {
			this.id = res.getInt("ID");
			this.nombre = res.getString("NOMBRE");
			this.carrera = res.getInt("CARRERA");
		} catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Asignatura [id=" + id + ", nombre=" + nombre + ", carrera=" + Asignaturas.getCarrera(carrera).getNombre() + "]";
	}
	
}
