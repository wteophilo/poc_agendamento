package br.com.wt.agendadoador.modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Protocolo {
	private MessageDigest message;
	
	public Protocolo() {
		try {
			this.message = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String gerarProtocolo(String valor){
		valor = valor.trim();
		String protocolo = "";
		message.update(valor.getBytes(), 0, valor.length());
		protocolo = new BigInteger(1, message.digest()).toString(16);
		return protocolo;
	}
}
