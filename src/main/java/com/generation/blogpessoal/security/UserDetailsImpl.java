package com.generation.blogpessoal.security;
	import java.util.Collection;
	import java.util.List;
	 
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.core.userdetails.UserDetails;
	 
	import com.generation.blogpessoal.model.Usuario;
	 
	public class UserDetailsImpl implements UserDetails {
		
		private static final long serialVersionUID = 1L;
		//controle de versão para essa classe
	 
		private String userName;//email
		private String password;//senha
		private List<GrantedAuthority> authorities;
		//autorizações que usuario tem
	 
		//metodo construtor
		public UserDetailsImpl(Usuario user) {
			this.userName = user.getUsuario();
			this.password = user.getSenha();
		}
	 
		public UserDetailsImpl() {	}
	 
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
	 
			return authorities;
		}
	 
		//retornar a senha
		@Override
		public String getPassword() {
	 
			return password;
		}
	 
		//retorna email -> usuario
		@Override
		public String getUsername() {
	 
			return userName;
		}
		
	 
		//ajudar a verificar se o acesso ja expirou
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}
	 
		//auxiliar a validação se usuario esta bloqueado
		@Override
		public boolean isAccountNonLocked() {
			return true;
		}
	 
		//auxiliar validar se a credencial expira
		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}
	 
		//validar se o user esta ativo
		@Override
		public boolean isEnabled() {
			return true;
		}
	 
	}
	 
