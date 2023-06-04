package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true) 
public class KaKaoProfile {

	public Long id;
	public String connected_at;
	public Properties properties;
	public Kakao_Account kakao_account;
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown=true) 
	public class Properties {

		public String nickname;

	}
	@Data
	@JsonIgnoreProperties(ignoreUnknown=true) 
	public class Kakao_Account {

		public Boolean profile_nickname_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_Needs_Agreement;
		public Boolean is_Email_Valid;
		public Boolean is_Email_Verified;
		public String email;
		
		@Data
		@JsonIgnoreProperties(ignoreUnknown=true) 
		public class Profile {

			public String nickname;

		}

	}
}
