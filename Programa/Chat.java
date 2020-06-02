package com.example.clusters.Programa;

//Si listaParticipantes en la posicion 0 guarda el valor -1, significa que el grupo ha sido borrado.
//En caso contrario, esta posicion de la lista contendra el id del creador.
public class Chat {
	/*
	public List		  <Integer> listaParticipantes;		
	public List 	  <Boolean> listaCensura;				
	public LinkedList <Mensaje> registroChat;				//Guarda los mensajes en bruto, uno tras otro en orden
	public LinkedList <Mensaje> registroChatCensurado;		//Se actualiza cuando un usuario quiere usar el chat
															//censurado y es igual que el registro de mensajes pero censurado 
	public LinkedList <Integer> registroEscritura;			//Guarda la persona que escribe. Est� acorde al registro de chats.
	public int ultimoMensajeCensurado;						//Se usa para eficiencia del procesamiento (censura)
	public boolean elChatEsGrupal;
	
	//El creador debe crear el grupo que solo lo contenga a �l, a partir de ah� se podr� unir la gente
	//El creador siempre sera la primera persona de la lista (pos 0)
	public Chat(int creador, boolean censura) {

		this.listaParticipantes = new ArrayList<>();	
		this.listaCensura = new ArrayList<>();			
		this.registroChat = new LinkedList <>();				
		this.registroChatCensurado = new LinkedList <>();											
		this.registroEscritura = new LinkedList <>();
		
		this.listaParticipantes.add(creador);
		this.listaCensura.add(censura);
		this.ultimoMensajeCensurado = -1; 
		elChatEsGrupal = true;
	}
	
	public Chat(int us1,int us2, boolean censuraUs1, boolean censuraUs2) {
		
		this.listaParticipantes = new ArrayList<>();	
		this.listaCensura = new ArrayList<>();			
		this.registroChat = new LinkedList <>();				
		this.registroChatCensurado = new LinkedList <>();											
		this.registroEscritura = new LinkedList <>();
		
		this.listaParticipantes.add(us1);
		this.listaParticipantes.add(us1);
		this.listaCensura.add(censuraUs1);
		this.listaCensura.add(censuraUs2);
		this.ultimoMensajeCensurado = -1; 
		elChatEsGrupal = false;
	}
	
	
	//Recibe por parametro un string, lo convierte en mensaje sin archivo y lo guarda en la lista de mensajes.
	public void escribirNuevoMensaje(String s,int usuarioEscribe) {
		if (listaParticipantes.contains(usuarioEscribe)) {
			Mensaje msj = new Mensaje (s);
			registroChat.add(msj);
			registroEscritura.add(usuarioEscribe);
			if (listaCensura.contains(true)) {
				refreshCensurarChat();
			}
		}
		else {
			throw new ClustersException ("El usuario que ha escrito no se encuentra dentro de la lista"
					+ "de participantes del grupo");
		}

	}
	//Recibe por parametro un string, lo convierte en mensaje y un archivo y lo guarda en la lista de mensajes.
	public void escribirNuevoMensajeConFichero(String s,int usuarioEscribe,Fichero fich) {
		Mensaje msj = new Mensaje (fich,s);
		registroChat.add(msj);
		registroEscritura.add(usuarioEscribe);
		if (listaCensura.contains(true)) {
			refreshCensurarChat();
		}
	}
	//Actualiza el chat censurado para estar a la par de registroChat
	public void refreshCensurarChat (){
		while (registroChatCensurado.size()<registroChat.size()) {
			ultimoMensajeCensurado++;	
			Mensaje msj = new Mensaje (registroChat.get(ultimoMensajeCensurado));
			msj.sobreescribirMensaje(Filtro.censurar(registroChat.get(ultimoMensajeCensurado).getMensaje()));
			registroChatCensurado.add(msj);
		}
	}
	
	public void setCensura (Integer usuario) {
		if (listaCensura.get(listaParticipantes.indexOf(usuario))) {
			listaCensura.set(listaParticipantes.indexOf(usuario), true);
			refreshCensurarChat ();
		}
		else throw new ClustersException("El usuario no, ya tiene activa la censura");
	}
	
	public void eliminarCensura (Integer usuario) {
		if (!listaCensura.get(listaParticipantes.indexOf(usuario))) {
			listaCensura.set(listaParticipantes.indexOf(usuario), false);
		}
		else throw new ClustersException("El usuario no, no tiene activa la censura");
	}
	
	//Cuando se anyade una persona a un grupo, se anyade a la lista de participantes y el usuario, ha de decir
	//si quiere, previamente, censurar el grupo.
	public void anyadirParticipanteChat(Integer s,boolean b){
		if (!elChatEsGrupal) {
			throw new ClustersException ("No se pueden anyadir usuarios a un chat personal.");
		}
		listaParticipantes.add(s);
		if (b) {
			refreshCensurarChat();
		}
		listaCensura.add(b);
	}
	
	public void eliminarParticipanteChat(Integer s){
		if (!elChatEsGrupal) {
			throw new ClustersException ("No se pueden anyadir usuarios a un chat personal.");
		}
		listaCensura.remove(listaParticipantes.indexOf(s));
		listaParticipantes.remove(s);
	} 
	//Si el usuario que llama a este metodo es el creador, pondra en la posicion 0 del array el valor de -1,
	//esto indica que el grupo ha sido borrado y es innacesible pero sigue guardado en la base de datos.
	*/
}
