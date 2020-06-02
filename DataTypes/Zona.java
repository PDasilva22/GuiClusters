package com.example.clusters.DataTypes;


import com.example.clusters.Programa.Filtro;
import com.example.clusters.Raiz.ClustersException;
import com.example.clusters.Raiz.ControlBD;
public class Zona {

	private int id;
	private String nombre;
	private String url;
	private float coordenadaNorte;
	private float coordenadaOeste;
	private Fichero foto;
	
	public Zona (String nombre) {
		this.nombre = nombre;
	}//Contructor de zona cuando solo se tiene el nombre y no el link.
	
	
	public Zona (String url, String nombreZona) {
	//	this.id = Buscar id en la base de datos.  
		try {
			descomponerUrl (url);
			this.nombre=nombreZona;
			generarUrlGoogleMaps();
		}catch (ClustersException e) {
			System.err.println(e);
		}

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		ControlBD.ActualizarZonaNombre(id, nombre);
		this.nombre = nombre;
	}

	public double getCoordenadaNorte() {
		return this.coordenadaNorte;
	}

	public void setCoordenas(float coordenadaLongitud, float coordenadaLatitud) {
		ControlBD.ActualizarZonaCoordenadas(id, coordenadaLongitud, coordenadaLatitud);
		this.coordenadaNorte = coordenadaLongitud;
		this.coordenadaOeste = coordenadaLatitud;
		generarUrlGoogleMaps();
	}

	public double getCoordenadaOeste() {
		return this.coordenadaOeste;
	}

	public int getId() {
		return this.id;
	}

	public Fichero getFoto() {
		return this.foto;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void descomponerUrl (String url){
		if (Filtro.urlGoogleValida(url)) {
			boolean hayEspacios = true;
			StringBuilder sb = new StringBuilder (url);
			while (hayEspacios) {
				if (sb.charAt(0)==' ') {
					sb.deleteCharAt(0);
				}
				else {
					hayEspacios = false;
				}
				url = sb.toString();
			}
			int it = 0;
			while (it<url.length()) {
				sb.append(url.charAt(it));
				it++;
			}
			it = 1;
			boolean esNegativo=false;
			if(url.contains("%")) {
				char c1=url.charAt(0);
				char c2=url.charAt(1);
				try {
					boolean noCondicion=false;
					while (!noCondicion) {//&&c2!='@'
						if (c1=='/'&&c2=='@') {
							noCondicion = true;
						}
						c1=url.charAt(it);
						c2=url.charAt(it+1);
						it++;
					}
				}catch(Exception e) {
					throw new ClustersException ("Los datos introducidos no son validos para la URL");
				}
			}
			else{
				while (!Filtro.caracterNumero(url.charAt(it))) {
					it++;
				}
			}
			if (url.charAt(it-1)=='-') {
				esNegativo=true;
			}
			double parteEntera=0.0;
			double parteDecimal=0.0;
			StringBuilder sb3 = new StringBuilder ();
			while (Filtro.caracterNumero(url.charAt(it))) {
				sb3.append(url.charAt(it));
				it++;
			}
			parteEntera=Double.parseDouble(sb3.toString()) + parteEntera;
			it++;
			StringBuilder sb4 = new StringBuilder ("0.");
			while (Filtro.caracterNumero(url.charAt(it))) {
				sb4.append(url.charAt(it));
				it++;
			}
			parteDecimal=Double.parseDouble(sb4.toString());
			if  (esNegativo) {
				this.coordenadaNorte=(float)-(parteEntera+parteDecimal);
			}
			else {
				this.coordenadaNorte=(float)(parteEntera+parteDecimal);
			}
			it++;
			esNegativo=false;
			StringBuilder sb5 = new StringBuilder ();
			parteEntera=0.0;
			parteDecimal=0.0;
			if(url.charAt(it)=='-') {
				esNegativo = true;
				it++;
			}
			else if (url.charAt(it)=='.') {
				throw new ClustersException ("Error, formato invalido");
			}
			while(Filtro.caracterNumero(url.charAt(it))) {
				sb5.append(url.charAt(it));
				it++;		
			}
			parteEntera=Double.parseDouble(sb5.toString());
			if(url.charAt(it)=='.') {
				it++;
				StringBuilder sb6 = new StringBuilder ("0.");
				while(it < url.length() && Filtro.caracterNumero(url.charAt(it))) {
					sb6.append(url.charAt(it));
					it++;
				}
				parteDecimal=Double.parseDouble(sb6.toString());
				if  (esNegativo) {
					this.coordenadaOeste=(float)-(parteEntera+parteDecimal);
				}
				else {
					this.coordenadaOeste=(float)(parteEntera+parteDecimal);
				}
			}
		}else {
			throw new ClustersException ("Fallo en la lectura del link");
		}
	}
	
	public void generarUrlGoogleMaps () {
		StringBuilder sb = new StringBuilder();
		sb.append("https://maps.google.com/?q=").append(this.coordenadaNorte).append(",").append(this.coordenadaOeste);
		this.url =  sb.toString();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Zona: ").append(this.getNombre()).append("-> ").append("[ ").append(this.getCoordenadaNorte()).append(", ").append(this.getCoordenadaOeste()).append(" ]");
		return sb.toString();
	}
}