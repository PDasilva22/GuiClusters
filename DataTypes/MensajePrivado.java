package com.example.clusters.DataTypes;

import com.example.clusters.Programa.Usuario;
import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class MensajePrivado {
	private int usuario;
	private int destinatario;
	private String mensaje;
	private Timestamp fecha;
	private boolean borrado;
	private String direccionCarpeta; //Hay que asignarle una direccion,esta ser� la direccion al directorio + id del usuario + id del usuario 2. 
	
	public MensajePrivado(int usuario, int destinatario, String mensaje) {
		this.fecha = ControlBD.InsertarMensajePrivado(usuario, destinatario, mensaje);
		this.usuario = usuario;
		this.destinatario = destinatario;
		this.mensaje = mensaje;
	}
	
	public MensajePrivado(ResultSet res) {
		try {
			if (res.getInt("USUARIO") == 0)
				throw new ClustersException();
			this.usuario = res.getInt("USUARIO");
			
			if (res.getInt("DESTINATARIO") == 0)
				throw new ClustersException();
			this.destinatario = res.getInt("DESTINATARIO");
			
			if (res.getString("MENSAJE") == null)
				throw new ClustersException();
			this.mensaje = res.getString("MENSAJE");
			
			if (res.getTimestamp("FECHA") == null)
				throw new ClustersException();
			this.fecha = res.getTimestamp("FECHA");
		} catch (SQLException e) {
			throw new ClustersException(e.getMessage());
		}
	}
	
	public MensajePrivado(int usuario, int destinatario, String mensaje, Date fecha,boolean borrado) {
		this.usuario=usuario;
		this.destinatario=destinatario;
		this.mensaje=mensaje;
		Timestamp f= new Timestamp(fecha.getTime());
		this.fecha=f;
		this.borrado= borrado;	
	}
	
	public int getUsuario() {
		return usuario;
	}

	public int getDestinatario() {
		return destinatario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public Timestamp getFecha() {
		return fecha;
	}
	public boolean isBorrado() {
		return borrado;
	}
	public  void setBorrado () {
		if (this.borrado==false) {
			this.borrado=true;
		}
		else {
			System.out.println("No se ha podido borrar un mensaje ya borrado anteriormente.");
		}
	}
	
	public void fromMessageToPrintWritter () {
		try {
			// Se le pasa file para otras pruebas File f = new File ("D:/Proyecto Git/prCluster/src/Prueba_PrintWritter.txt");
			PrintWriter pw = new PrintWriter (this.direccionCarpeta);
			StringBuilder sb = new StringBuilder ();	
			sb.append(getUsuario()).append(";.;").append(getDestinatario()).append(";.;").append(getFecha().getTime()).append(";.;").append(isBorrado()).append("\\FinMensajeCodificado//;.;");
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error en la busqueda del archivo para guardar.");
		}
	}
	
	public LinkedList<MensajePrivado> lectorPrintWritter () {
		LinkedList <MensajePrivado> listaBuffer = new LinkedList <MensajePrivado>();
		try{
			PrintWriter pw = new PrintWriter (this.direccionCarpeta);
			Scanner sc = new Scanner (pw.toString());
			pw.close();
			for (int i=0;sc.hasNextLine();i++) {
				listaBuffer.add(fromPrintWritterToMessage(i));
		}			
		}catch (FileNotFoundException e) {
			System.out.println("No se ha podido encontrar el archivo.");
		}
		return listaBuffer;
	}
	
	public MensajePrivado fromPrintWritterToMessage (int numeroLinea) {
		int usuario = 0;
		int destinatario =0;
		String mensaje = null;
		Timestamp fecha = null;
		boolean borrado=false;
		try {
			PrintWriter pw = new PrintWriter (this.direccionCarpeta);
			Scanner sc = new Scanner (pw.toString());
			pw.close();
			sc.useDelimiter(";.;");
			for (int i=0;i<numeroLinea;i++) {
				sc.nextLine();
			}
			boolean noSalir=true;
			int it = 0;
			while (sc.hasNext()&&noSalir) {
				String s = sc.next();
				if (s=="\\FinMensajeCodificado//;.;") {
					noSalir=false;
				}
				switch(it) {
				  case 0:
				    usuario=Integer.parseInt(s);
				    break;
				  case 1:
					  destinatario=Integer.parseInt(s);
				    break;
				  case 2:
					  mensaje=s;
					    break;
				  case 3:
					  	Timestamp f= new Timestamp((long) Double.parseDouble(s));
						this.fecha=f;
					    fecha=f;
					    break;
				  case 4:
					  if(s=="false") {
						  borrado = false;
					  }
					  else if (s=="true") {
						borrado = true;  
					  }else {
						  throw new ClustersException("Mensaje Borrado?");
					  }
					    break;
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Error en la lectura.");
		}
		MensajePrivado m = new MensajePrivado (usuario,destinatario,mensaje,fecha,borrado);
		return m;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder ();
		Usuario u2 = new Usuario (getUsuario());
		sb.append(u2.getNick()).append("\n");
		if (isBorrado()) {
			sb.append(this.getMensaje());
		}
		else {
			sb.append("El mensaje ha sido borrado.");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destinatario;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
		result = prime * result + usuario;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MensajePrivado) {
			MensajePrivado m = (MensajePrivado) obj;
			return usuario == m.usuario && destinatario == m.destinatario && mensaje == m.mensaje && fecha == m.fecha;
		}
		return false;
	}
	
	
	/*	COMO CO�O HACEMOS EL SISTEMA DE REFRESCO DE DATOS.
		CREAMOS UN PRINTWRITER QUE ESCRIBA LOS MENSAJES CON UNA SINTAXIS EN CONCRETO (USUARIO,FECHA,MENSAJE) EL ARCHIVO TIENE QUE TENER EL ID DEL USUARIO POR SI EN EL MISMO MOVIL SE INICIASE SESION CON OTRA CUENTA.
		CUANDO SE INICIA SESION EN LA APP ESTA INICIALIZA NO SOLO LOS DATOS DE LA BASE DE DATOS SINO LOS DEL ARCHIVO. Y CADA VEZ QUE SE ESCRIBE ALGO SE VA ESCRIBIENDO EN EL ARCHIVO SIMULTANEAMENTE.
		EL ARCHIVO TAMBIEN GUARDARA EL DATO DE LA ULTIMA FECHA EN LA QUE SE ACTUALIZO PARA PASARLA POR PARAMETRO EN LAS CONSULTAS (CONSULTAR TODOS LOS MENSAJES CON FECHA > ULTIMAFECHA).
		CHAVALES COMO HACEMOS ESTO. 
	*/
}