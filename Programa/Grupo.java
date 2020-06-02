package com.example.clusters.Programa;

import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Grupo implements Comparable<Grupo>{
	protected int id;
	protected String nombre;
	protected String descripcion;
	protected int admin;
	protected List<Integer> asignaturas;
	protected Date fechaCreacion;
	protected byte[] foto;
	protected String historial;
	protected int privacidad;
	protected List<Integer> miembros;
	protected List<Integer> eventos;

	
	public Grupo(int id) {
		ResultSet res = ControlBD.ConsultarGrupo(id);
		ConstructorBD(res);
		
	}
	public Grupo(ResultSet res) {
		ConstructorBD(res);
	}
	public Grupo(String nombre, String descripcion, int privacidad) {
		ResultSet res = ControlBD.InsertarGrupo(nombre, Usuarios.getManejador().getID(), descripcion, privacidad);
		ConstructorBD(res);
	}
	private void ConstructorBD(ResultSet res) {
		try {
			if (res.getInt("ID") == 0)
				throw new ClustersException("Error al tratar los datos de la consulta de grupos:\\n    ");
			this.id = res.getInt("ID");
			
			if (res.getString("NOMBRE") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el GRUPO con ID = " + id + ":\n    ");
			this.nombre = res.getString("NOMBRE");
			
			if (res.getString("DESCRIPCION") != null)
				this.descripcion = res.getString("DESCRIPCION");
			
			if (res.getInt("ADMIN") == 0)
				throw new ClustersException("Error al tratar los datos de la consulta en el GRUPO con ID = " + id + ":\n    ");
			this.admin = res.getInt("ID");
			
			if (res.getDate("FECHA_CREACION") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el GRUPO con ID = " + id + ":\n    ");
			this.fechaCreacion = res.getDate("FECHA_CREACION");
			
			if (res.getBytes("FOTO") != null)
			this.foto = res.getBytes("FOTO");
			
			this.historial = "";
			
			IniciarAsignaturas();
			IniciarParticipantes();
		} catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta de grupos:\n    ");
		}
	}
	private void IniciarAsignaturas() throws SQLException {
		ResultSet res = ControlBD.ConsultarTagsGrupo(id);
		this.asignaturas = new ArrayList<>();
		while (res.next()) {
			this.asignaturas.add(res.getInt(1));
		}
	}
	private void IniciarParticipantes() throws SQLException {
		ResultSet res = ControlBD.ConsultarTagsGrupo(id);
		this.miembros = new ArrayList<>();
		this.miembros.add(this.admin);
		while (res.next()) {
			this.miembros.add(res.getInt(1));
		}
	}
	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	/*public Blob getFoto() {
		 try{

             BufferedImage image = null;
             InputStream in = new ByteArrayInputStream(this.foto);
             image = ImageIO.read(in);
             ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
             return  new JLabel(imgi);
         }catch(Exception ex){
        	 return new Blob("No imagen") {
		};
	}
	}*/
	
	public List<Integer> getMiembros(){
		return this.miembros;
	}

	public void setNombre(String nombre) {
		AnadirLog("Se ha cambiado el nombre del grupo de " + this.nombre + " a " + nombre); // Se deja grabado en el Log
																							// el cambio
		ControlBD.ActualizarGrupoNombre(id, nombre);
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		AnadirLog("Se ha cambiado la descripcion a: " + descripcion); // Se deja grabado en el Log el cambio
		ControlBD.ActualizarGrupoDescripcion(id, descripcion);
		this.descripcion = descripcion;
	}

	public void setAsignaturas(int[] asignaturasAnyadidas, int[] asignaturasEliminadas) {
		AnadirLog("Se han anyadido las asignaturas: "); // Se deja grabado en el Log el cambio
		for (int i = 0; i < asignaturasAnyadidas.length; i++) {
			ControlBD.TaggearGrupo(id, asignaturasAnyadidas[i]);
			this.asignaturas.add(asignaturasAnyadidas[i]);
			AnadirLog(Asignaturas.getAsignatura(asignaturasAnyadidas[i]).getNombre() + " "); // Se escriben todas las
																								// asignaturas anyadidas
		}
		for (int i = 0; i < asignaturasEliminadas.length; i++) {
			ControlBD.EliminarTagGrupo(id, asignaturasEliminadas[i]);
			this.asignaturas.remove(asignaturasEliminadas[i]);
			AnadirLog(Asignaturas.getAsignatura(asignaturasEliminadas[i]) + " "); // Y todas las eliminadas
		}
	}

//	public void setFoto(Fichero foto) {
//		if (Archivos.EsImagen(foto.getFormato())) {
//			this.foto = foto;
//			AnadirLog("Se ha cambiado la foto a: " + foto.getNombreCompleto()); // Se deja grabado en el Log el cambio
//		}else {
//			throw new ClustersException("El archivo no es una imagen");
//		}
//		
//	}

	private void AnadirLog(String m) {
		this.historial += "\n" + m;
	}

	
	@Override
	public int compareTo(Grupo o) {
		Grupo g = (Grupo) o;
		return this.id - g.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Grupo) {
			Grupo g = (Grupo) obj;
			return this.id == g.id;
		}
		throw new ClustersException("Se ha intentado comparar la instancia: " + this.toString() + " con un objeto no perteneciente a la clase Grupo: " + obj.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ID: ").append(id);
		sb.append(", NOMBRE:").append(nombre);
		sb.append(", DESCRIPCION: ").append(descripcion);
		sb.append(", FECHA DE CREACION: ").append(fechaCreacion).append("]");
		return  sb.toString();
	}

	
}
