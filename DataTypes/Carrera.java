package com.example.clusters.DataTypes;

import com.example.clusters.Raiz.ClustersException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Carrera {
	private int id;
	private String nombre;
	private String facultad;
	public Carrera(ResultSet res) {
		try {
			this.id = res.getInt("ID");
			this.nombre = res.getString("NOMBRE");
			this.facultad = res.getString("FACULTAD");
		} catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta en la CARRERA con ID = " + id);
		}
	}
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getFacultad() {
		return facultad;
	}
	
	
	
}
