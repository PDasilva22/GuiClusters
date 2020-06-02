package com.example.clusters.Raiz;

import com.example.clusters.DataTypes.Carrera;
import com.example.clusters.PerfilActivity;
import com.example.clusters.Programa.Asignaturas;
import com.example.clusters.Programa.Grupos;
import com.example.clusters.Programa.Usuarios;

import java.sql.Date;

public class ControlMenu {

	public static void IniciarSesion (String correo, String contrasenya) {
		System.out.println("Felicidades Crack! Linea 1 !");
		ControlBD.Conectar();
		System.out.println("Felicidades Crack! Linea 2 !");
		Usuarios.IniciarSesion(correo, contrasenya);
		System.out.println("Felicidades Crack! Linea 3 !");
		Asignaturas.IniciarSesion();
		System.out.println("Felicidades Crack! Linea 4 !");
		Grupos.IniciarSesion();
		System.out.println("Felicidades Crack! Linea 5 !");
	}
	public static void ConfirmarRegistro(String nombre, String apellido1, String apellido2, final String correo, String contrasena, int carrera, String fechaNacimiento, int numeroTelefono){
		/*this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.correo = correo;
		this.contrasenya = contrasena;
		this.carrera = carrera;
		this.fechaNacimiento = fechaNacimiento;
		this.numeroTelefono = numeroTelefono;*/
	}
	public static class Registrarse extends Thread {
		//String nombre, apellido1, apellido2, correo, contrasenya, fechaNacimiento;
		//int carrera, numeroTelefono;
		//Usuarios.Registrar(nombre, apellido1, apellido2, correo, contrasena, carrera, fechaNacimiento, numeroTelefono);
		//Para enviar un correo
		//try {
		public Registrarse(String msg) {
			super(msg);

		}

		@Override
		public void run() {
			ControlBD.Conectar();
			Asignaturas.IniciarSesion();
			String[] listaCarreras = new String[Asignaturas.getListaCarrerasBD().size()];
			int i = 0;
			for (Carrera c : Asignaturas.getListaCarrerasBD().values()) {
				listaCarreras[i] = c.getNombre();
				i++;
			}
			com.example.clusters.Registrarse.setCarreras(listaCarreras);
		}

	}						/*
						Properties props = new Properties();
						props.setProperty("mail.smtp.host", "smtp.gmail.com");
						props.setProperty("mail.smtp.starttls.enable", "true");
						props.setProperty("mail.smtp.port", "587");
						props.setProperty("mail.smtp.auth", "true");

						Session session = Session.getDefaultInstance(props);

						String correoRemitente = "clusteraplication@gmail.com"; // Correo que envia el correo
						String passwordRemitente = "un5son6@";
						String correoReceptor = correo; // Correo que recibe el correo
						String asunto = "Mi primero correo en Java"; // Asunto del Correo
						String mensaje = "Hola<br>Este es el contenido de mi primer correo desde <b>java</b><br><br>Por <b>Códigos de Programación</b>"; // Mensaje del correo en HTML

						MimeMessage message = new MimeMessage(session);
						message.setFrom(new InternetAddress(correoRemitente));

						message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
						message.setSubject(asunto);
						message.setText(mensaje, "ISO-8859-1", "html");

						Transport t = session.getTransport("smtp");
						t.connect(correoRemitente, passwordRemitente);
						t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
						t.close();

						//JOptionPane.showMessageDialog(null, "Correo Electronico Enviado");

					} catch (AddressException ex) {
						throw new ClustersException("Error en enviar el correo " + ex);
					} catch (MessagingException ex) {
						throw new ClustersException("Error al enviar el correo" + ex);
					}
	}*/


		public static void Perfil() {
		String aux = Usuarios.getManejador().getNombre();
		aux += " " + Usuarios.getManejador().getApellido1();
		aux += " " + Usuarios.getManejador().getApellido2();
			PerfilActivity.setNombre(aux);
		aux = Usuarios.getManejador().getNick();
			PerfilActivity.setApodo(aux);
		aux = Usuarios.getManejador().getCorreo();
			PerfilActivity.setCorreo(aux);
		Date aux2 = Usuarios.getManejador().getFechaNacimiento();

			PerfilActivity.setFechaNacimiento(aux2.toString());


		aux = Asignaturas.getCarrera(Usuarios.getManejador().getCarrera()).getNombre();
			PerfilActivity.setCarrera(aux);
		//aux = Usuarios.getManejador().getFotoPerfil(); REVISAR SIO SOIO/*************************************
			//PerfilActivity.setFoto(aux);
		String[] auxA = new String[Usuarios.getManejador().getAsignaturasMatriculadas().size()];
		int i = 0;
		for (int a : Usuarios.getManejador().getAsignaturasMatriculadas()){
			auxA[i] = Asignaturas.getAsignatura(a).getNombre();
			i++;
		}
			PerfilActivity.setAsignaturas(auxA);
	}
	
	public static void ExplorarGrupos() {
		
	}
	
	public static void AbrirCalendario() {
	
	}
	
	public static void CerrarSesion() {
		
	}
	
	public static void DarDeBaja(String contrasena) {
		
	}
}
