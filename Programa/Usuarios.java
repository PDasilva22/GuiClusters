package Programa;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import DataTypes.Fichero;
import Raiz.ClustersException;
import Raiz.ControlBD;

public class Usuarios {
	
	private static Usuario manejador;
	private static HashMap <Integer,Usuario> listaAmigos 	 = new HashMap<Integer,Usuario>();
	private static HashMap <Integer,Usuario> listaBloqueados = new HashMap<Integer,Usuario>();
	public static HashMap <Integer,HashMap<Integer,Usuario>> listaListasMiembrosGrupo = new HashMap<Integer,HashMap<Integer,Usuario>>();
	
	public static void IniciarSesion(String correo, String contrasenya) {
		manejador = new Usuario(correo,contrasenya);
		conjListaAmigos();
		conjListaBloqueados();
		conjListaListaMiembrosGrupo();
	}
	public static void IniciarSesion(int telefono, String contrasenya) {
		manejador = new Usuario(telefono,contrasenya);
		conjListaAmigos();
		conjListaBloqueados();
		conjListaListaMiembrosGrupo();
	}
	public static void conjListaListaMiembrosGrupo() {
		for (int i : manejador.getListaGrupos()) {
			listaListasMiembrosGrupo.put(i,new HashMap<Integer,Usuario>());
			InicializarGrupo(i);
		}
	}
	public static void InicializarGrupo(int id) {
		ResultSet res = ControlBD.ConsultarMiembrosGrupo(id);
		try {
			while (res.next()) {
				listaListasMiembrosGrupo.get(id).put(res.getInt("USUARIO"), new Usuario(res.getInt("USUARIO")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void Registrar(String nombre, String apellido1, String apellido2, String correo, 
			String contrasenya, int carrera, Date fechaNacimiento, int telefono) {
			//if (Filtro.verificarNombre(nombre) && Filtro.verificarContrasenya(contrasenya,correo,nombre,apellido1,apellido2)
					//&& Filtro.verificarApellido(apellido1)&& Filtro.verificarApellido(apellido2)) {
				//throw new ClustersException ("Nombre o contrasenya no validos");
			//}
			ControlBD.InsertarUsuario(nombre, apellido1, apellido2, correo, contrasenya, carrera, fechaNacimiento, telefono);
	}
	
//	public static void Registrar(String nombre, String apellido1, String apellido2, String correo, 
//			String contrasenya, int carrera, Date fechaNacimiento) {
//			if (Filtro.verificarNombre(nombre) && verificarContrasenya(contrasenya)
//					&& verificarApellido(apellido1)&& verificarApellido(apellido2)) {
//				throw new ClustersException ("Nombre o contrasenya no validos");
//			}
//			ControlBD.InsertarUsuario(nombre, apellido1, apellido2, correo, contrasenya, carrera, fechaNacimiento);
//	}
	
	public static void conjListaAmigos() {
		for (Integer i:manejador.getListaAmigos()) {
			listaAmigos.put(i, new Usuario (i));
		}
	}
	
	public static Usuario getManejador() {
		return Usuarios.manejador;
	}
	
	public static void conjListaBloqueados() {
		for (Integer i:manejador.getListaBloqueados()) {
			listaBloqueados.put(i, new Usuario (i));
		}
	}
	
	public static HashMap<Integer,Usuario> getListaAmigos() {
		return Usuarios.listaAmigos;
	}
	
	public static HashMap <Integer,Usuario> getListaBloqueados(){
		return Usuarios.listaBloqueados;
	}
	
	public static HashMap <Integer,Usuario> getListaMiembrosGrupo(int id){
		return Usuarios.listaListasMiembrosGrupo.get(id);
	}
	
	public static Usuario getUsuario(int id) {
		return new Usuario(id);
	}
}
