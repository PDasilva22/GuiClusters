package com.example.clusters.Programa;

import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Evento {

	private String nombre;
	private Timestamp fecha;
	private Timestamp fechaCreacion;
	private String descripcion;
	private int asignatura;
	private int id;
	private int grupo;
	private List<Integer> listaParticipantes;
	private List<Boolean> listaConfirmaciones;

	//"aaaa-mm-dd hh:mm:ss.fffffffff"
	public Evento(int grupo, String nombre, String ano, String mes, String dia, String horas, String minutos, String descripcion) {
		this.grupo = grupo;
		this.nombre = nombre;
		this.fecha = Timestamp.valueOf(ano+"-"+mes+"-"+dia+" "+horas+":"+minutos+":00.000000000");
		this.descripcion = descripcion;
		this.listaParticipantes.clear();
	}

	public Evento(ResultSet res) {
		
		ConstructorBD(res);
	}
	
	private void ConstructorBD(ResultSet res) {
		try {
			if (res.getInt("ID") == 0)
				throw new ClustersException();
			this.id = res.getInt("ID");
			
			if (res.getString("NOMBRE") == null)
				throw new ClustersException();
			this.nombre = res.getString("NOMBRE");
			
			if (res.getString("DESCRIPCION") != null)
				this.descripcion = res.getString("DESCRIPCION");
			
			if (res.getInt("ASINGATURA") != 0)
				this.asignatura = res.getInt("ASIGNATURA");
			
			if (res.getTimestamp("FECHA") == null)
				throw new ClustersException();
			this.fecha = res.getTimestamp("FECHA");
			
			if (res.getTimestamp("FECHA_CREACION") == null)
				throw new ClustersException();
			this.fechaCreacion = res.getTimestamp("FECHA_CREACION");
			
			if (res.getInt("GRUPO") == 0)
				throw new ClustersException();
			this.grupo = res.getInt("GRUPO");
			
			IniciarParticipantes();
		}catch(SQLException e) {
			throw new ClustersException();
		}
	}
	public void IniciarParticipantes() throws SQLException {
		ResultSet res = ControlBD.ConsultarParticipantesEvento(id);
		while (res.next()) {
			listaParticipantes.add(res.getInt(1));
			listaConfirmaciones.add(res.getBoolean(2));
		}
	}
	public int getGrupo() {
		return this.grupo;
	}
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		if (nombre != this.nombre) {
			ControlBD.ActualizarEventoNombre(id, nombre);
			this.nombre = nombre;
		}
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		if (fecha != this.fecha) {
			ControlBD.ActualizarEventoFecha(id, fecha);
			this.fecha = fecha;
		}
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		if (descripcion != this.descripcion) {
			ControlBD.ActualizarEventoDescripcion(id, descripcion);
			this.descripcion = descripcion;
		}
	}

	public List<Integer> getListaParticipantes() {
		return listaParticipantes;
	}

	public void AnadirParticipante(int participante, boolean confirmar) {
		if (listaParticipantes.contains(participante)) {
			if (listaConfirmaciones.get(listaParticipantes.indexOf(participante)) != confirmar) {
				ControlBD.ActualizarParticipanteConfirmacion(participante, id, confirmar);
				listaConfirmaciones.set(listaParticipantes.indexOf(participante), confirmar);
			}
		}else {
			ControlBD.InsertarParticipanteEvento(participante, id, confirmar);
			this.listaParticipantes.add(participante);
			this.listaConfirmaciones.add(confirmar);
		}
	}

	public int getId() {
		return id;
	}

}
