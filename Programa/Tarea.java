package com.example.clusters.Programa;

import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Tarea {

	private String nombre;
	private Timestamp fecha;
	private Timestamp fechaCreacion;
	private int asignatura;
	private String descripcion;
	private int id;

	public Tarea(String nombre, String ano, String mes, String dia, String horas, String minutos, String segundos, String milisegundos, String descripcion) {
		ConstructorBD(ControlBD.InsertarTarea(Usuarios.getManejador().getID(), nombre, Timestamp.valueOf(ano+"-"+mes+"-"+dia+" "+horas+":"+minutos+":00.000000000"), descripcion));
	}
	public Tarea(ResultSet res) {
		ConstructorBD(res);
	}
	private void ConstructorBD(ResultSet res) {
		try {
			if (res.getInt("ID") == 0)
				throw new ClustersException("Error al tratar los datos de la consulta en la TAREA");
			this.id = res.getInt("ID");
			
			if (res.getString("NOMBRE") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en la TAREA con ID = " + id);
			this.nombre = res.getString("NOMBRE");
			
			if (res.getString("DESCRIPCION") != null)
				this.descripcion = res.getString("DESCRIPCION");
			
			if (res.getInt("ASIGNATURA") != 0)
				this.asignatura = res.getInt("ASIGNATURA");
			
			if (res.getTimestamp("FECHA") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en la TAREA con ID = " + id);
			this.fecha = res.getTimestamp("FECHA");
			
			if (res.getTimestamp("FECHA_CREACION") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en la TAREA con ID = " + id);
			this.fechaCreacion = res.getTimestamp("FECHA_CREACION");
		}catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta de TAREA :\n    " + e);
		}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		if (nombre != this.nombre) {
			ControlBD.ActualizarTareaNombre(id, nombre);
			this.nombre = nombre;
		}
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		if (fecha != this.fecha) {
			ControlBD.ActualizarTareaFecha(id, fecha);
			this.fecha = fecha;
		}
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		if (descripcion != this.descripcion) {
			ControlBD.ActualizarTareaDescripcion(id, descripcion);
			this.descripcion = descripcion;
		}
	}
	public int getId() {
		return id;
	}
}
