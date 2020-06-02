package Programa;

import java.io.File;
import java.util.Date;

public abstract class GrupoAbstracto {
	// Atributos
	private int id;
	private String nombre;
	private String descripcion;
	private int[] asignaturas;
	private Date fechaCreacion;
	private File foto;
	private File[] historial;

	// Metodos

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public File getFoto() {
		return foto;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setAsignaturas(int[] asignaturas) {
		this.asignaturas = asignaturas;
	}

	public void setFoto(File foto) {
		this.foto = foto;
	}
}