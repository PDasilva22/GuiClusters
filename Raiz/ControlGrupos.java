package com.example.clusters.Raiz;

import com.example.clusters.Programa.Grupo;
import com.example.clusters.Programa.Grupos;

public class ControlGrupos {
	public static void CrearGrupo() {
		
	}
	
	public static void UnirseAGrupo() {
		
	}
	
	public static void MostarMisGrupos() {
		String[] grupos = new String[Grupos.getListaGrupos().size()];
		int i = 0;
		for (Grupo g : Grupos.getListaGrupos().values()) {
			grupos[i] = g.getNombre();
			i++;
		}
		//ListaGruposActivity.setListaGrupos(grupos);
	}
	
	public static void PedirEntrar() {
		
	}
	
	public static void InvitarAGrupo() {
		
	}
	
	public static void Vetar() {
		
	}
	
	public static void Readmitir() {
		
	}
	
	public static void AceptarMiembro() {
		
	}
	
	public static void RechazarMiembro() {
		
	}
	
	public static void FiltrarGruposPorAsignaturas() {
		
	}
	
	public static void FiltrarGruposPorPrivacidad() {
		
	}
	
//	public static void FiltrarGruposPorZonas() {
//		
//	}
	
	public static void OrdenarGruposPorAntiguedad() {
		
	}
	
	public static void OrdenarGruposPorAsignatura() {
		
	}
	
	public static void OrdenarGruposPorTamano() {
		
	}
	
	public static void OrdenarGruposPorCercania() {
		
	}
	
	public static void NombrarModerador() {
		
	}
	
	public static void CederAdministrador() {
		
	}
	
	public static void RevocarModerador() {
		
	}
	
	public static void CrearEvento() {
		
	}
	
	public static void CancelarEvento() {
		
	}
	
	public static void Participar() {
		
	}
	
	public static void QuererParticipar() {
		
	}
	
	public static void TerminarEvento() {
		
	}
	
	public static void EditarGrupo() {
		
	}
	
	public static void EditarEvento() {
		
	}
}
