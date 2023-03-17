package com.alok91340.ecommerceapi;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.alok91340.ecommerceapi.entities.Role;
import com.alok91340.ecommerceapi.repository.RoleRepository;
import com.alok91340.ecommerceapi.utils.Constant;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EcommerceapiApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try {

			Role admin_role = new Role();
			admin_role.setId(Constant.ADMIN_USER);
			admin_role.setName("ROLE_ADMIN");

			Role user_role = new Role();
			user_role.setId(Constant.NORMAL_USER);
			user_role.setName("ROLE_USER");

			List<Role> roles = new ArrayList<>();

			roles.add(user_role);
			roles.add(admin_role);
			this.roleRepository.saveAll(roles);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
