package facec.prova.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import facec.prova.model.Aluno;
import facec.prova.model.repository.AlunoRepository;

@Controller
public class AlunoController {

	@Autowired
	AlunoRepository alunoRepository;

	@GetMapping("/formulario")
	public String cadastrar(Aluno aluno, Model model) {
		model.addAttribute("aluno", aluno);
		return "formulario";
	}

	@PostMapping("salvar")
	public String salvar(@Valid Aluno aluno,BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("erros", bindingResult.getAllErrors());
			return cadastrar(aluno, model);
			
		}
		alunoRepository.save(aluno);
		return "redirect:/listar";
	}

	@GetMapping("editar")
	public String editar(@PathParam(value = "id") Long id, Model model) {
		Aluno aluno = alunoRepository.getOne(id);
		model.addAttribute("aluno", aluno);
		return "formulario";

	}
	@PostMapping("editar/salvar")
	public String salvarEditar(@Valid Aluno aluno,BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("erros", bindingResult.getAllErrors());
			return cadastrar(aluno, model);
			
		}
		
		alunoRepository.save(aluno);
		return "redirect:../listar";
		
	}
	@GetMapping("deletar")
	public String deletar(@PathParam(value = "id")long id) {
		alunoRepository.deleteById(id);
		return "redirect:/listar";
		
	}
	@GetMapping("/listar")
	public String lista(Model model) {
		model.addAttribute("alunos", alunoRepository.findAll());
		return "lista";
		
	}
}
