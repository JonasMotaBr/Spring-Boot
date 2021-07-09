package cursospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cursospringboot.repository.TelefoneRepository;


@Controller
public class ControlleTelefone {
	
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	
	
	

}
