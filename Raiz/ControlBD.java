package com.example.clusters.Raiz;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class ControlBD extends AsyncTask {

	private static Connection conn;
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String user = "cluster";
	private static final String password = "";
	private static final String url = "jdbc:mysql://79.146.102.236:3306/clustersbd";
	private static Timestamp ultimoRefresco;

	public static void Conectar() { //TESTEADO

		//new Thread(new Runnable() {    // TODAS LAS PUTAS CONSULTAS CON ESTO
			//@Override
			//public void run() {

				conn = null;
				System.out.println("Felicidades MAQUINA! Linea 1 !");
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) {
					throw new ClustersException(e.getMessage());
				}
				System.out.println("Felicidades MAQUINA! Linea 2 !");
				try {
					conn = DriverManager.getConnection(url,user,password);
				} catch (SQLException e) {
					throw new ClustersException(e.getMessage());
				}

				System.out.println("Felicidades MAQUINA! Linea 3 !");
				if (conn != null) {
					System.out.println("Felicidades MAQUINA! Linea 4 !");
					System.out.println("Conexion establecida.");
					System.out.println("Felicidades MAQUINA! Linea 6 !");
				}
			//}
		//}).start();
	}

	public static ResultSet ConsultarUsuario(int id){ //TESTEADO        DEVUELVE UN USUARIO CON LA ID QUE PASES POR PARAMETRO
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT ID, NOMBRE, APELLIDO1, APELLIDO2, NICK, CORREO, FOTO, CARRERA, FECHA_NACIMIENTO, TELEFONO, DESCRIPCION FROM USUARIO WHERE ID = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de un USUARIO con ID = " + id);
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarUsuario(String correo,String contrasenya){ //TESTEADO     DEVUELVE UN USUARIO CON EL CORREO Y CONTRASENYA QUE PASES POR PARAMETRO
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT ID, NOMBRE, APELLIDO1, APELLIDO2, NICK, CORREO, FOTO, CARRERA, FECHA_NACIMIENTO, TELEFONO, DESCRIPCION FROM USUARIO WHERE CORREO = ? AND CONTRASENYA = ?");
			ps.setString(1, correo);
			ps.setString(2, contrasenya);
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de un USUARIO con CORREO = " + correo);
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static boolean ConsultarUsuario(String correo){ //TESTEADO
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT ID FROM USUARIO WHERE CORREO = ?");
			ps.setString(1, correo);
			res = ps.executeQuery();
			if (res.next()) {
				return true;
			}
			else {
				return false;
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarUsuario(String nombre, String apellido1, String apellido2, String correo,              //METE A UN USUARIO NO VERIFICADO EN LA BASE DE DATOS (SIN TELEFONO)
			String contrasenya, int carrera, Date fechaNacimiento) { //TESTEADO
		if (ConsultarUsuario(correo)) {
			throw new ClustersException("Ya existe un USUARIO con CORREO = " + correo);
		}
		PreparedStatement ps;
		try {

			ps = conn.prepareStatement("INSERT INTO USUARIO_NO_VERIFICADO (NOMBRE, APELLIDO1, APELLIDO2, CORREO, CONTRASENYA, CARRERA, FECHA_NACIMIENTO) VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, nombre);
			ps.setString(2, apellido1);
			ps.setString(3, apellido2);
			ps.setString(4, correo);
			ps.setString(5, contrasenya);
			ps.setInt(6, carrera);
			ps.setDate(7, fechaNacimiento);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha creado el USUARIO " + correo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarUsuario(String nombre, String apellido1, String apellido2, String correo,            //METE A UN USUARIO NO VERIFICADO EN LA BASE DE DATOS (CON TELEFONO)
			String contrasenya, int carrera, Date fechaNacimiento, int telefono) { //TESTEADO
		if (ConsultarUsuario(correo)) {
			throw new ClustersException("Ya existe un USUARIO con CORREO = " + correo);
		}
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO USUARIO_NO_VERIFICADO (NOMBRE, APELLIDO1, APELLIDO2, CORREO, CONTRASENYA, CARRERA, FECHA_NACIMIENTO, TELEFONO) VALUES(?,?,?,?,?,?,?,?)");
			ps.setString(1, nombre);
			ps.setString(2, apellido1);
			ps.setString(3, apellido2);
			ps.setString(4, correo);
			ps.setString(5, contrasenya);
			ps.setInt(6, carrera);
			ps.setDate(7, fechaNacimiento);
			ps.setInt(8, telefono);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha creado el USUARIO " + correo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarUsuarioNick(int id, String nick) { //TESTEADO
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE USUARIO SET NICK = ? WHERE ID = ?");
			ps.setString(1, nick);
			ps.setInt(2, id);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado el NICK del USUARIO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarUsuarioContrasenya(int id, String contrasenya) { //TESTEADO
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE USUARIO SET CONTRASENYA = ? WHERE ID = ?");
			ps.setString(1, contrasenya);
			ps.setInt(2, id);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la CONTRASENYA del USUARIO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarUsuarioCarrera(int id, int carrera) { //TESTEADO
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE USUARIO SET CARRERA = ? WHERE ID = ?");
			ps.setInt(1, carrera);
			ps.setInt(2, id);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la CARRERA del USUARIO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarUsuarioDescripcion(int id, String descripcion) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE USUARIO SET DESCRIPCION = ? WHERE ID = ?");
			ps.setString(1, descripcion);
			ps.setInt(2, id);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la CARRERA del USUARIO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarUsuario(int id) { //TESTEADO
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM USUARIO WHERE ID = ?");
			ps.setInt(1, id);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha eliminado el USUARIO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarMatriculasUsuario(int usuario) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("SELECT ASIGNATURA, ESTADO FROM MATRICULAR WHERE USUARIO = ?");
			ps.setInt(1, usuario);
			ResultSet res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarMatriculasAsignatura(int asignatura) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("SELECT USUARIO, ESTADO FROM MATRICULAR WHERE ASIGNATURA = ?");
			ps.setInt(1, asignatura);
			ResultSet res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void MatricularUsuario(int usuario, int asignatura) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO MATRICULAR (USUARIO, ASIGNATURA) VALUES(?,?)");
			ps.setInt(1, usuario);
			ps.setInt(2, asignatura);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha matriculado el USUARIO con ID = " + usuario + " en la asignatura " + asignatura + "base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void AprobarUsuario(int usuario, int asignatura) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE MATRICULAR SET ESTADO = ? WHERE USUARIO = ? AND ASIGNATURA = ?");
			ps.setInt(1, usuario);
			ps.setInt(2, asignatura);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado el ESTADO del la MATRICULA del USUARIO con ID = " + usuario + " en la asignatura " + asignatura + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarAmigos(int usuario) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("SELECT USUARIO2 FROM AMIGOS WHERE USUARIO1 = ?");
			ps.setInt(1, usuario);
			ResultSet res = ps.executeQuery(); 
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void Amistar(int usuario1, int usuario2) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO AMIGOS (USUARIO1, USUARIO2) VALUES(?,?)");
			ps.setInt(1, usuario1);
			ps.setInt(2, usuario2);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se han amistado el USUARIO con ID = " + usuario1 + " y el USUARIO con ID = " + usuario2 + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void Enemistar(int usuario1, int usuario2) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM AMIGOS WHERE USUARIO1 = ? AND USUARIO2 = ?");
			ps.setInt(1, usuario1);
			ps.setInt(2, usuario2);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se han enemistado el USUARIO con ID = " + usuario1 + " y el USUARIO con ID = " + usuario2 + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarBloqueados(int id) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("SELECT BLOQUEADO FROM BLOQUEADO WHERE USUARIO = ?");
			ps.setInt(1, id);
			ResultSet res = ps.executeQuery(); 
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void Bloquear(int usuario, int bloqueado) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO BLOQUEADO (USUARIO, BLOQUEADO) VALUES(?,?)");
			ps.setInt(1, usuario);
			ps.setInt(2, bloqueado);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("El USUARIO con ID = " + usuario + " no ha bloqueado al USUARIO con ID = " + bloqueado + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void Desbloquear(int usuario, int bloqueado) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM BLOQUEADO WHERE USUARIO = ? AND BLOQUEADO = ?");
			ps.setInt(1, usuario);
			ps.setInt(2, bloqueado);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("El USUARIO con ID = " + usuario + " no ha desbloqueado al USUARIO con ID = " + bloqueado + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarGrupo(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM GRUPO WHERE ID = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de un GRUPO con ID = " + id);
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarGruposPorAsignatura(int asignatura) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT G.ID, G.NOMBRE, G.ADMIN, G.PRIVACIDAD, G.FOTO FROM GRUPO G JOIN TAG_GRUPO T ON G.ID = T.GRUPO WHERE T.ASIGNATURA = ? AND G.PRIVACIDAD BETWEEN 1 AND 2");
			ps.setInt(1, asignatura);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarGruposPorPrivacidad(int privacidad) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT ID, NOMBRE, ADMIN, PRIVACIDAD, FOTO FROM GRUPO WHERE G.PRIVACIDAD = ?");
			ps.setInt(1, privacidad);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarGrupos() {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT ID, NOMBRE, ADMIN, PRIVACIDAD, FOTO FROM GRUPO WHERE G.PRIVACIDAD BETWEEN 1 AND 2");
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarGrupo(String nombre,int admin) { //TESTEADO
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM GRUPO WHERE NOMBRE = ? AND ADMIN = ?");
			ps.setString(1, nombre);
			ps.setInt(2, admin);
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de un GRUPO con NOMBRE = " + nombre + " y ADMIN = " + admin);
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet InsertarGrupo(String nombre, int admin, String descripcion, int privacidad) { //TESTEADO
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO GRUPO (NOMBRE, ADMIN, DESCRIPCION, PRIVACIDAD) VALUES(?,?,?,?)");
			ps.setString(1, nombre);
			ps.setInt(2, admin);
			ps.setString(3, descripcion);
			ps.setInt(4, privacidad);
			int r = ps.executeUpdate(); 
			if (r > 0) {
				ResultSet res = ConsultarGrupo(nombre, admin);
				int id = res.getInt("ID");
				InsertarMiembroGrupo(admin, id);
				return res;
			}
			else {
				throw new ClustersException("No se ha creado el GRUPO " + nombre + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarGrupoNombre(int id, String nombre) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE GRUPO SET NOMBRE = ? WHERE ID = ?");
			ps.setString(1, nombre);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado el NOMBRE del GRUPO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarGrupoDescripcion(int id, String descripcion) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE GRUPO SET DESCRIPCION = ? WHERE ID = ?");
			ps.setString(1, descripcion);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la DESCRIPCION del GRUPO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarGrupoFoto(int id, String foto) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE GRUPO SET FOTO = ? WHERE ID = ?");
			ps.setString(1, foto);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la FOTO del GRUPO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarGrupo(int id) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM GRUPO WHERE ID = ?");
			ps.setInt(1, id);
			int r = ps.executeUpdate();
			if (r <= 0) {
				throw new ClustersException("No se ha eliminado el GRUPO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarTagsGrupo(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT ASIGNATURA FROM TAG_GRUPO WHERE GRUPO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void TaggearGrupo(int grupo, int asignatura) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO TAG_GRUPO (GRUPO, ASIGNATURA) VALUES(?,?)");
			ps.setInt(1, grupo);
			ps.setInt(2, asignatura);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha taggeado el GRUPO " + grupo + " con la asignatura " + asignatura + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarTagGrupo(int grupo, int asignatura) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM TAG_GRUPO WHERE GRUPO = ? AND ASIGNATURA = ?");
			ps.setInt(1, grupo);
			ps.setInt(2, asignatura);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha destaggeado el GRUPO " + grupo + " con la asignatura " + asignatura + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarMiembrosGrupo(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT USUARIO, MODERADOR FROM MIEMBRO WHERE GRUPO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarGruposUsuario(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT GRUPO FROM MIEMBRO WHERE USUARIO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarMiembroGrupo(int usuario, int grupo) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO MIEMBRO (USUARIO, GRUPO, MODERADOR) VALUES(?,?,?)");
			ps.setInt(1, usuario);
			ps.setInt(2, grupo);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha insertado el USUARIO " + usuario + " en el grupo " + grupo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarMiembroGrupo(int usuario, int grupo) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM MIEMBRO WHERE USUARIO = ? AND GRUPO = ?");
			ps.setInt(1, usuario);
			ps.setInt(2, grupo);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha eliminado el USUARIO " + usuario + " del grupo " + grupo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void NombrarModeradorGrupo(int usuario, int grupo) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE GRUPO SET ESTADO = 1 WHERE USUARIO = ? AND GRUPO = ?");
			ps.setInt(1, usuario);
			ps.setInt(2, grupo);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha nombrado moderador al USUARIO " + usuario + " en el grupo " + grupo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void RevocarModeradorGrupo(int usuario, int grupo) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE GRUPO SET ESTADO = 0 WHERE USUARIO = ? AND GRUPO = ?");
			ps.setInt(1, usuario);
			ps.setInt(2, grupo);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha revocado de moderador al USUARIO " + usuario + " del grupo " + grupo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarListadoAsignaturas() {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM ASIGNATURA");
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de ninguna ASIGNATURA");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarListadoCarreras() {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM CARRERA");
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de ninguna CARRERA");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarEvento(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM EVENTO WHERE ID = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			if (res.next()) {
				return res;
			}
			else {
				throw new ClustersException("Lagunas en la base de datos, no hay registro de ninguna EVENTO con ID = " + id);
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarEvento(int grupo, String nombre, Timestamp fecha, String descripcion) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO EVENTO (NOMBRE, FECHA, DESCRIPCION, GRUPO) VALUES(?,?,?,?)");
			ps.setString(1, nombre);
			ps.setTimestamp(2, fecha);
			ps.setString(3, descripcion);
			ps.setInt(4, grupo);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha creado el EVENTO " + nombre + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarEventoNombre(int id, String nombre) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE EVENTO SET NOMBRE = ? WHERE ID = ?");
			ps.setString(1, nombre);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado el NOMBRE del EVENTO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarEventoFecha(int id, Timestamp fecha) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE EVENTO SET FECHA = ? WHERE ID = ?");
			ps.setTimestamp(1, fecha);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la FECHA del EVENTO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarEventoDescripcion(int id, String descripcion) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE EVENTO SET DESCRIPCION = ? WHERE ID = ?");
			ps.setString(1, descripcion);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la DESCRIPCION del EVENTO con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarEvento(int id) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM EVENTO WHERE ID = ?");
			ps.setInt(1, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha eliminado el EVENTO " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarEventosGrupo(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM EVENTO WHERE GRUPO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarParticipantesEvento(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT USUARIO, CONFIRMAR, FECHA_INSCRIPCION FROM PARTICIPANTE WHERE GRUPO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarParticipanteEvento(int participante, int evento, boolean confirmar) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO PARTICIPANTE (USUARIO, EVENTO, CONFIRMAR) VALUES(?,?,?)");
			ps.setInt(1, participante);
			ps.setInt(2, evento);
			ps.setBoolean(3, confirmar);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha insertado el participante con IND = " + participante + " en el EVENTO " + evento + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarParticipanteConfirmacion(int participante, int evento, boolean confirmar) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE PARTICIPANTE SET CONFIRMAR = ? WHERE USUARIO = ? AND EVENTO = ?");
			ps.setBoolean(1, confirmar);
			ps.setInt(2, participante);
			ps.setInt(3, evento);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la CONFIRMACION del PARTICIPANTE con ID = " + participante + " en el EVENTO con ID = " + evento + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarTareasUsuario(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM TAREA WHERE USUARIO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarTarea(int usuario, Timestamp fecha) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM TAREA WHERE USUARIO = ? AND FECHA = ?");
			ps.setInt(1, usuario);
			ps.setTimestamp(2, fecha);
			res = ps.executeQuery();
			if (res.next())
				return res;
			else
				throw new ClustersException("No se ha encontrado la TAREA del USUARIO con ID = " + usuario);
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet InsertarTarea(int usuario, String nombre, Timestamp fecha, String descripcion) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO TAREA (NOMBRE, FECHA, DESCRIPCION, USUARIO) VALUES(?,?,?,?)");
			ps.setString(1, nombre);
			ps.setTimestamp(2, fecha);
			ps.setString(3, descripcion);
			ps.setInt(4, usuario);
			int r = ps.executeUpdate(); 
			if (r > 0) {
				return ConsultarTarea(usuario, fecha);
			}else {
				throw new ClustersException("No se ha creado el EVENTO " + nombre + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarTareaNombre(int id, String nombre) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE TAREA SET NOMBRE = ? WHERE ID = ?");
			ps.setString(1, nombre);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado el NOMBRE de la TAREA con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarTareaFecha(int id, Timestamp fecha) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE TAREA SET FECHA = ? WHERE ID = ?");
			ps.setTimestamp(1, fecha);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la FECHA de la TAREA con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarTareaDescripcion(int id, String descripcion) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE TAREA SET DESCRIPCION = ? WHERE ID = ?");
			ps.setString(1, descripcion);
			ps.setInt(2, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la DESCRIPCION de la TAREA con ID = " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarTarea(int id) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM TAREA WHERE ID = ?");
			ps.setInt(1, id);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha eliminado la TAREA " + id + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarMensajesPrivadosEnviados(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM MENSAJE_PRIVADO WHERE USUARIO1 = ? AND FECHA >= ?");
			ps.setInt(1, id);
			ps.setTimestamp(2, ultimoRefresco);
			ultimoRefresco = ObtenerFecha();
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarMensajesPrivadosRecibidos(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM MENSAJE_PRIVADO WHERE USUARIO2 = ? AND FECHA >= ?");
			ps.setInt(1, id);
			ps.setTimestamp(2, ultimoRefresco);
			ultimoRefresco = ObtenerFecha();
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static Timestamp InsertarMensajePrivado(int id, int destinatario,String mensaje) {
		PreparedStatement ps;
		try {
			Timestamp fecha = ObtenerFecha();
			ps = conn.prepareStatement("INSERT INTO MENSAJE_PRIVADO (USUARIO1, USUARIO2, MENSAJE, FECHA) VALUES(?,?,?,?)");
			ps.setInt(1, id);
			ps.setInt(2, destinatario);
			ps.setString(3, mensaje);
			ps.setTimestamp(4, fecha);
			int r = ps.executeUpdate(); 
			if (r > 0) {
				return fecha;
			}else {
				throw new ClustersException("No se ha creado el MENSAJE del USUARIO con ID = " + id + " para el USUARIO con ID = " + destinatario + " que decia: " + mensaje + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarMensajesGrupo(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT * FROM MENSAJE_GRUPO WHERE GRUPO = ? AND FECHA >= ?");
			ps.setInt(1, id);
			ps.setTimestamp(2, ultimoRefresco);
			ultimoRefresco = ObtenerFecha();
			res = ps.executeQuery();
			res.next();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static Timestamp InsertarMensajeGrupo(int usuario, int grupo, String mensaje) {
		PreparedStatement ps;
		try {
			Timestamp fecha = ObtenerFecha();
			ps = conn.prepareStatement("INSERT INTO MENSAJE_GRUPO (GRUPO, USUARIO, MENSAJE, FECHA) VALUES(?,?,?,?)");
			ps.setInt(1, grupo);
			ps.setInt(2, usuario);
			ps.setString(3, mensaje);
			ps.setTimestamp(4, fecha);
			int r = ps.executeUpdate(); 
			if (r > 0) {
				return fecha;
			}else {
				throw new ClustersException("No se ha creado el MENSAJE del USUARIO con ID = " + usuario + " para el GRUPO con ID = " + grupo + " que decia: " + mensaje + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarListaPendientesGrupo(int id) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT USUARIO FROM LISTA_PENDIENTES WHERE GRUPO = ?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			return res;
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarUsuarioListaPendientesGrupo(int usuario, int grupo) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO LISTA_PENDIENTES (USUARIO, GRUPO) VALUES(?,?)");
			ps.setInt(1, usuario);
			ps.setInt(2, grupo);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha metido el USUARIO con ID = " + usuario + " en la lista de pendientes del GRUPO con ID = " + grupo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarUsuarioListaPendientesGrupo(int usuario, int grupo, boolean aceptado) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM TAREA WHERE ID = ?");
			ps.setInt(1, usuario);
			ps.setInt(2, grupo);
			int r = ps.executeUpdate(); 
			if (r > 0) {
				if (aceptado)
					InsertarMiembroGrupo(usuario, grupo);
			}else {
				throw new ClustersException("No se ha eliminado el USUARIO " + usuario + " de la lista de pendientes del GRUPO con ID = " + grupo + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static ResultSet ConsultarZona(int evento) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = conn.prepareStatement("SELECT NOMBRE, LONGITUD, LATITUD FROM ZONA WHERE EVENTO = ?");
			ps.setInt(1, evento);
			res = ps.executeQuery();
			if (res.next())
				return res;
			else
				throw new ClustersException("No se ha encontrado la ZONA del EVENTO con ID = " + evento);
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void InsertarZona(int evento, String nombre, float longitud, float latitud) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO ZONA (EVENTO, NOMBRE, LONGITUD, LATITUD) VALUES(?,?,?,?)");
			ps.setInt(1, evento);
			ps.setString(2, nombre);
			ps.setFloat(3, longitud);
			ps.setFloat(4, latitud);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha creado la ZONA del EVENTO con ID = " + evento + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarZonaNombre(int evento, String nombre) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE ZONA SET NOMBRE = ? WHERE EVENTO = ?");
			ps.setString(1, nombre);
			ps.setInt(2, evento);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la ZONA del EVENTO con ID = " + evento + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void ActualizarZonaCoordenadas(int evento, float longitud, float latitud) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("UPDATE ZONA SET LONGITUD = ?, LATITUD = ? WHERE EVENTO = ?");
			ps.setFloat(1, longitud);
			ps.setFloat(2, latitud);
			ps.setInt(3, evento);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha actualizado la ZONA del EVENTO con ID = " + evento + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static void EliminarZona(int evento) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE FROM ZONA WHERE EVENTO = ?");
			ps.setInt(1, evento);
			int r = ps.executeUpdate(); 
			if (r <= 0) {
				throw new ClustersException("No se ha eliminado la ZONA del EVENTO con ID = " + evento + " en la base de datos");
			}
		}catch(SQLException e) {
			throw new ClustersException("No se ha podido realizar la consulta: " + e);
		}
	}
	public static Timestamp ObtenerFecha() {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("SELECT CURRENT_TIMESTAMP");
			ResultSet res = ps.executeQuery();
			if (res.next())
				return res.getTimestamp(1);
			else
				throw new ClustersException("No se consigue acceder a la hora del servidor");
		}catch(SQLException e) {
			throw new ClustersException("No se consigue acceder a la hora del servidor: " + e);
		}
		
	}
	public static void Refrescar() {
		ultimoRefresco = ObtenerFecha();
	}
	public static void Desconectar() {
		conn = null ;
		System.out.println("Conexion terminada");
	}

	@Override
	protected Object doInBackground(Object[] objects) {
		Conectar();

		return null;
	}
}
