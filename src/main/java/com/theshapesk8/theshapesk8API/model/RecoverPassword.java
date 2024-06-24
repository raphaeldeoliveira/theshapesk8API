package com.theshapesk8.theshapesk8API.model;

import java.time.LocalDateTime;

public class RecoverPassword {

	private String codigo;
	private String email;
	private LocalDateTime horarioQueFoiGerado;
	
	public RecoverPassword() {
		
	}
	
	public RecoverPassword(String codigo, String email, LocalDateTime horarioQueFoiGerado) {
		this.codigo = codigo;
		this.email = email;
		this.horarioQueFoiGerado = horarioQueFoiGerado;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getHorarioQueFoiGerado() {
		return horarioQueFoiGerado;
	}
	public void setHorarioQueFoiGerado(LocalDateTime horarioQueFoiGerado) {
		this.horarioQueFoiGerado = horarioQueFoiGerado;
	}
	
}
