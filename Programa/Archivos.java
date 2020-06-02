package com.example.clusters.Programa;

public class Archivos {
	private static String[] formatosImagen = {"jpg","png","jpeg","raw"};
	private static String[] formatosSonido = {"mp3","wav","flac"};
	private static String[] formatosVideo  = {"mp4","avi","mkv","flv","mov","wmv"};
	private static String[] formatosEspeciales; // Esta es la lista de formatos que se tratan de forma distinta

	public static int Especial(String formato) {
		int i = 0;
		while (i < formatosEspeciales.length) {
			if (formatosEspeciales[i] == formato)
				return i;
			else {
				++i;
			}
		} 
		return -1;
	}

	public static boolean EsVideo(String formato) {
		int i = 0;
		while (i < formatosVideo.length) {
			if (formatosVideo[i] == formato)
				return true;
			else {
				++i;
			}
		} 
		return false;
	}
	public static boolean EsSonido(String formato) {
		int i = 0;
		while (i < formatosSonido.length) {
			if (formatosSonido[i] == formato)
				return true;
			else {
				++i;
			}
		} 
		return false;
	}
	public static boolean EsImagen(String formato) {
		int i = 0;
		while (i < formatosImagen.length) {
			if (formatosImagen[i] == formato)
				return true;
			else {
				++i;
			}
		}
		return false;
	}
}
