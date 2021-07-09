package cursospringboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cursospringboot.model.Pessoa;
import cursospringboot.model.Telefone;
import cursospringboot.repository.PessoaRepository;
import cursospringboot.repository.TelefoneRepository;

@Controller
public class ControllerPessoa {

	@Autowired
	public PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
	public String inicio() {
		return "cadastro/cadastroPessoa";
	}

	

	// ignora antes tudo que vem antes na url
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvarPessoa(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);
			modelAndView.addObject("pessoaObj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); // vem das anotaÃ§Ãµes @NotEmpty e outras
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaObj", new Pessoa());
			
		return andView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/lista")
	public ModelAndView lista() {
		ModelAndView modelview = new ModelAndView("/cadastro/lista");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelview.addObject("pessoas", pessoasIt);
		return modelview;
	}

	// mapea editarpessoa mais o parametro idpessoa
	@GetMapping("/editarPessoa/{idpessoa}")
	public ModelAndView editarPessoa(@PathVariable("idpessoa") Long idpessoa) {
		ModelAndView modelview = new ModelAndView("/cadastro/editar");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelview.addObject("pessoaObj", pessoa.get());
		return modelview;
	}

	@GetMapping("/removerPessoa/{idpessoa}")
	public ModelAndView removerPessoa(@PathVariable("idpessoa") Long idpessoa) {

		pessoaRepository.deleteById(idpessoa);
		ModelAndView modelview = new ModelAndView("/cadastro/lista");
		// carrega todas pessoas para pessoaObj, e em seguida resgata na lista
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelview.addObject("pessoas", pessoasIt);
		return modelview;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/buscarPessoa")
	public ModelAndView buscarPessoa(@RequestParam("nomePesquisa") String nomePesquisa) {

		ModelAndView mav = new ModelAndView("/cadastro/lista");
		mav.addObject("pessoas", pessoaRepository.buscarPessoaPorNome(nomePesquisa));
		mav.addObject("pessoaObj", new Pessoa());
		return mav;
	}

	// mapea editarpessoa mais o parametro idpessoa
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView ExibirPessoa(Telefone telefone, @PathVariable("idpessoa") Long idpessoa) {
		ModelAndView modelview = new ModelAndView("/cadastro/cadastroTelefone");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		modelview.addObject("pessoaObj", pessoa.get());
		// Telefone
		telefone.setPessoa(pessoa.get());
		modelview.addObject("telefones", telefoneRepository.buscaTelefones(idpessoa));

		return modelview;
	}

	@PostMapping("**/salvarTelefone/{pessoaid}")
	public ModelAndView salvarTelefonePesso(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		ModelAndView mav = new ModelAndView("/cadastro/cadastroTelefone");
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		mav.addObject("telefones", telefoneRepository.buscaTelefones(pessoaid));
		mav.addObject("pessoaObj", pessoa);
		return mav;
	}

	@GetMapping("/removerTelefone/{numeroTelefone}")
	public ModelAndView ExluirTelefone(@PathVariable("numeroTelefone") String numero) {
		telefoneRepository.DeletarTelefone(numero);
		ModelAndView mav = new ModelAndView("/cadastro/index");

		return mav;

	}

}
