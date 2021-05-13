package com.fisa.ctm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fisa.ctm.enumerated.EnumResponse;

public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private T respuesta;
	private EnumResponse resultado;
	private List<String> mensajes;

	public Response() {
		super();
		this.resultado = EnumResponse.OK;
		this.mensajes = new ArrayList<>();
	}

	public Response(T respuesta) {
		super();
		this.respuesta = respuesta;
		this.resultado = EnumResponse.OK;
		this.mensajes = new ArrayList<>();
	}

	public Response(EnumResponse resultado, List<String> mensajes) {
		super();
		this.resultado = resultado;
		this.mensajes = mensajes;
	}

	public Response(T respuesta, EnumResponse resultado, List<String> mensajes) {
		super();
		this.respuesta = respuesta;
		this.resultado = resultado;
		this.mensajes = mensajes;
	}

	public T getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(T respuesta) {
		this.respuesta = respuesta;
	}

	public EnumResponse getResultado() {
		return resultado;
	}

	public void setResultado(EnumResponse resultado) {
		this.resultado = resultado;
	}

	public List<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

	public void setMensaje(String mensaje) {
		this.mensajes.add(mensaje);
	}

	@Override
	public String toString() {
		return "ResponseDto [respuesta=" + respuesta + ", resultado=" + resultado + ", mensajes=" + mensajes + "]";
	}
}
