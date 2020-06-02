package Programa;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Raiz.ClustersException;
import Raiz.ControlBD;
import DataTypes.Fichero;

public class Usuario implements Comparable<Usuario>{
	/* Generacion Auto */     private int id; 
	/* Obligatorio */         private String nombre;
	/* Obligatorio */         private String apellido1;
	/* Obligatorio */         private String apellido2;
	/* Obligatorio */         private String nick;
	/* Obligatorio */         private String correo;
	/* Posterior */           private byte[] fotoPerfil; //Datatype
	/* Posterior */           private String descripcion;
	/* Obligatorio */         private int carrera; //ID de la carrera
	/* Obligatorio */         private Date fechaNacimiento; //Datatype
	/* Posterior */           private List<Integer> asignaturasMatriculadas; //Tam max 20
	/* RelativoAMatriculadas*/private List<Integer> asignaturasCompletadas; //Tam max 40
	/* Voluntario */          private int numeroTelefono;
	/* Posterior */           private List<Integer> listaAmigos;
	/* Posterior */           private List<Integer> listaBloqueados;
	/* Posterior */           private List<Integer> listaEventos;
	/* Posterior */           private List<Integer> listaGrupos;
	
	public Usuario (String correo, String contrasenya) {
		ResultSet res = ControlBD.ConsultarUsuario(correo,contrasenya);
		ConstructorBD(res);
	}
	public Usuario (int telefono, String contrasenya) {
		ResultSet res = ControlBD.ConsultarUsuario(telefono,contrasenya);
		ConstructorBD(res);
	}
	public Usuario (int id) {
		ResultSet res = ControlBD.ConsultarUsuario(id);
		ConstructorBD(res);
	}
	private void ConstructorBD(ResultSet res) {
		try {
			if (res.getInt("ID") == 0)
				throw new ClustersException("Error al tratar los datos de la consulta");
			this.id = res.getInt("ID");
			
			if (res.getString("NOMBRE") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id + ":\n    ");
			this.nombre = res.getString("NOMBRE");
			
			if (res.getString("APELLIDO1") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id + ":\n    ");
			this.apellido1 = res.getString("APELLIDO1");
			
			if (res.getString("APELLIDO2") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id + ":\n    ");
			this.apellido2 = res.getString("APELLIDO2");
			
			if (res.getString("NICK") != null)
				this.nick = res.getString("NICK");
			
			if (res.getString("CORREO") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id + ":\n    ");
			this.correo = res.getString("CORREO");
			
			if (res.getBytes("FOTO") != null)
				this.fotoPerfil = res.getBytes("FOTO");
			
			if (res.getInt("CARRERA") == 0)
				throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id + ":\n    ");
			this.carrera = res.getInt("CARRERA");
			
			if (res.getDate("FECHA_NACIMIENTO") == null)
				throw new ClustersException("Error al tratar los datos de la consulta en el USUARIO con ID = " + id + ":\n    ");
			this.fechaNacimiento = res.getDate("FECHA_NACIMIENTO");
			
			if (res.getInt("TELEFONO") != 0)
				this.numeroTelefono = res.getInt("TELEFONO");
			
			if (res.getString("DESCRIPCION") != null)
				this.descripcion = res.getString("DESCRIPCION");
			
			IniciarMisAsignaturas();
			IniciarMisGrupos();
			IniciarMisAmigos();
			IniciarBloqueados();
		} catch (SQLException e) {
			throw new ClustersException("Error al tratar los datos de la consulta de usuario:\n    " + e);
		}
	}
	private void IniciarMisAsignaturas() throws SQLException {
		ResultSet res = ControlBD.ConsultarMatriculasUsuario(id);
		this.asignaturasMatriculadas = new ArrayList<>();
		this.asignaturasCompletadas = new ArrayList<>();
		while (res.next()) {
			this.asignaturasMatriculadas.add(res.getInt(1));
			if (res.getBoolean(2))
				this.asignaturasCompletadas.add(res.getInt(1));
		}
	}
	private void IniciarMisGrupos() throws SQLException {
		ResultSet res = ControlBD.ConsultarGruposUsuario(id);
		this.listaGrupos = new ArrayList<>();
		while (res.next()) {
			this.listaGrupos.add(res.getInt(1));
		}
	}
	private void IniciarMisAmigos() throws SQLException {
		ResultSet res = ControlBD.ConsultarAmigos(id);
		this.listaAmigos = new ArrayList<>();
		while (res.next()) {
			this.listaAmigos.add(res.getInt(1));
		}
	}
	private void IniciarBloqueados() throws SQLException {
		ResultSet res = ControlBD.ConsultarAmigos(id);
		this.listaBloqueados = new ArrayList<>();
		while (res.next()) {
			this.listaBloqueados.add(res.getInt(1));
		}
	}
	
	public List<Integer> getListaGrupos(){
		return this.listaGrupos;
	}
	public int getID() {
		return this.id;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getApellido1() {
		return this.apellido1;
	}
	public String getApellido2() {
		return this.apellido2;
	}
	public String getNick() {
		return this.nick;
	}
	public String getCorreo() {
		return this.correo;
	}
	public JLabel getFotoPerfil() {
		
		 try{
             BufferedImage image = null;
             InputStream in = new ByteArrayInputStream(this.fotoPerfil);
             image = ImageIO.read(in);
             ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
             return  new JLabel(imgi);
         }catch(Exception ex){
         	throw new ClustersException("No es una foto valida");
         }
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public int getCarrera() {
		return this.carrera;
	}
	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}
	public List<Integer> getAsignaturasMatriculadas() {
		return this.asignaturasMatriculadas;
	}
	public List<Integer> getAsignaturasAprobadas() {
		return this.asignaturasCompletadas;
	}
	public List<Integer> getListaEventos(){
		return this.listaEventos;
	}
	
	public void setNick(String nuevoNick) {
		if (Filtro.verificarNick(nuevoNick, getCorreo())) {
			ControlBD.ActualizarUsuarioNick(id, nuevoNick);
			this.nick=nuevoNick;
		}
		else {
			throw new ClustersException("El nick no cumple los resquisitos necesarios.");
		}
	}
	public void setContrasenya(String nuevaContrasenya) {
		if (Filtro.verificarContrasenya(nuevaContrasenya,correo,nombre,apellido1,apellido2)) 
			ControlBD.ActualizarUsuarioContrasenya(id, nuevaContrasenya);
		else {
			throw new ClustersException("La contrasenya no cumple los resquisitos necesarios.");
		}
	}
	/*	Este metodo comprueba que el archivo esta dentro de los formatos validos para
		una imagen. En caso afirmativo, sustituye el archivo por la foto de perfil del 
		usuario que ha solicitado el cambio */
	
	
//	public void setFotoPerfil(Fichero nuevaFoto) {
//		if (Archivos.EsImagen(nuevaFoto.getFormato())) {
//			this.fotoPerfil = nuevaFoto;
//		}
//		else {
//			throw new ClustersException("El archivo introducido no se corresponde con"
//					+ "los parametros esperados");
//		}
//	}
	public void setDescripcion(String nuevaDescripcion) {
		if (nuevaDescripcion != this.descripcion) {
			ControlBD.ActualizarUsuarioDescripcion(id, nuevaDescripcion);
			this.descripcion = nuevaDescripcion;
		}
	}
	public void insertarFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil=fotoPerfil;
	}
	
	public void setCarrera(int carrera) {
		if (carrera != this.carrera) {
			ControlBD.ActualizarUsuarioCarrera(id, carrera);
			this.carrera = carrera;
		}
	}

	/*Este metodo recibe una lista de asignaturas y las anyade a las asignaturas en las
	  que el usuario ha sido matriculado. Despues de esto, llama a ordenar y las guarda*/
	public void matricularAsignaturas (List<Integer> asignaturasNuevas) {
		if (asignaturasNuevas.isEmpty()) {
			throw new ClustersException ("La lista de asignaturas a matricular esta vacia");
		}
		for (int i=0;i<asignaturasNuevas.size();i++) {
			if (this.asignaturasMatriculadas.contains(asignaturasNuevas.get(i))||
					this.asignaturasCompletadas.contains(asignaturasNuevas.get(i))) {
				throw new ClustersException ("Ya se encuentra matriculado en alguna de estas asignaturas "
						+ "o ya la ha aprobado");
			}
		}
		for (int i=0;i< asignaturasNuevas.size();i++) {
			ControlBD.MatricularUsuario(id, asignaturasNuevas.get(i));
			asignaturasMatriculadas.add(asignaturasNuevas.get(i));
		}
		Collections.sort(asignaturasMatriculadas);
	}

	/*Este metodo recibe por parametro un array de asignaturas que han sido aprobadas
	 * y las anyade a la lista de asignaturas aprobadas. Ademas, las borra de asignaturas
	 * aprobadas.
	 */
	public void seisCreditos (List<Integer> asignaturasNuevas) {
		if (asignaturasNuevas.isEmpty()) {
			throw new ClustersException ("La lista de asignaturas aprobadas esta vacia");
		}
		for (int i=0;i<asignaturasNuevas.size();i++) {
			if (this.asignaturasCompletadas.contains(asignaturasNuevas.get(i))) {
				throw new ClustersException ("Ya ha aprobado anteriormente alguna de las "
						+ "asignaturas de la lista");
			}
			else if (!this.asignaturasMatriculadas.contains(asignaturasNuevas.get(i))) {
				throw new ClustersException ("No puede aprobar una asignatura en la que "
						+ "no estaba matriculado anteriormente");
			}
		}
		/*Primero comprobamos que las asignaturas aprobadas se encuentren entre las matriculadas
		en caso contrario, el programa lanzaria un error.
		*/
		for (int i=0;i< asignaturasNuevas.size();i++) {
			ControlBD.AprobarUsuario(id, asignaturasNuevas.get(i));
			asignaturasCompletadas.add(asignaturasNuevas.get(i));
		}
		Collections.sort(asignaturasCompletadas);
		//Las asignaturas se anyadiran una vez pasados los filtros anteriores.
	}
	
	public void AnadirAmigo(int usuario) {
		if (!listaAmigos.contains(usuario)) {
			ControlBD.Amistar(id, usuario);
			listaAmigos.add(usuario);
		}
	}
	
	public void EliminarAmigo(int usuario) {
		if (listaAmigos.contains(usuario)) {
			ControlBD.Enemistar(id, usuario);
			listaAmigos.remove(listaAmigos.indexOf(usuario));
		}
	}
	
	public void AnadirBloqueado(int usuario) {
		if (!listaBloqueados.contains(usuario)) {
			ControlBD.Bloquear(id, usuario);
			listaBloqueados.add(usuario);
		}
	}
	
	public void EliminarBloqueado(int usuario) {
		if (listaBloqueados.contains(usuario)) {
			ControlBD.Desbloquear(id, usuario);
			listaBloqueados.remove(listaBloqueados.indexOf(usuario));
		}
	}
	
	public void EntrarGrupo(int grupo) {
		if (!listaGrupos.contains(grupo)) {
			ControlBD.InsertarMiembroGrupo(this.id, grupo);
			this.listaGrupos.add(grupo);
		}
	}
	
	public void SalirGrupo(int grupo) {
		if (listaGrupos.contains(grupo)) {
			ControlBD.EliminarMiembroGrupo(this.id, grupo);
			this.listaGrupos.remove(listaGrupos.indexOf(grupo));
		}
	}
	
	public int getNumeroTelefono() {
		return this.numeroTelefono;
	}
	public List<Integer> getListaAmigos(){
		return this.listaAmigos;
	}
	public List<Integer> getListaBloqueados(){
		return this.listaBloqueados;
	}
	
	// Dos usuario son iguales siempre y cuando sus ID coincidan
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			Usuario usuario2 = (Usuario) obj;
			if (usuario2.id==this.id) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int compareTo(Usuario usuario2) {
	//Si el id de 2 usuarios es el mismo compareTo devolvera 0
		if (this.id==usuario2.id) {
			return 0;
		}
	//Si el id del primer usuario es mayor que el del segundo compareTo devolvera 1
		else if (this.id>usuario2.id) {
			return 1;
		}
	//Si el id del segundo usuario es mayor que el del primero compareTo devolvera -1.
		else  if (this.id<usuario2.id){
			return -1;
		}
	/*  Para cualquier otro caso no contemplado, se devolvera un error con el mensaje
		mostrado por argumento.	*/
		else {
			throw new ClustersException("Error en la id de los usuarios a comparar.");
		}
	}
	// Ahora mismo devuelve nick, nombre y apellidos. Terminar de ver quien queremos meter
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.nick);
		sb.append (" ");
		sb.append(this.nombre);
		sb.append (" ");
		sb.append (this.apellido1);
		sb.append (" ");
		sb.append (this.apellido2);
		return sb.toString();
	}
