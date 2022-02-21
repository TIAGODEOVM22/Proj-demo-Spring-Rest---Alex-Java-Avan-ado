package br.com.springboot.controllers;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.model.Usuario;
import br.com.springboot.repository.UsuarioRepository;

@RestController
public class testeController {

	@Autowired /*injeção de dependencia*/
	private UsuarioRepository usuarioRepository;
	
	///mostrarnome é a url, {nome} é a variavel "nome" da classe Usuario Model
	@RequestMapping(value = "/mostrarnome/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String teste(@PathVariable String nome) {
		
		Usuario usuario = new Usuario(); //cria um objeto usuario
		usuario.setNome(nome);//seta um nome 
		usuarioRepository.save(usuario); //essa interface salva o usuario 
		
		return "CURSO SPRINGBOOT API: " + nome;
	}
	
	@GetMapping(value = "listaTodos")
	@ResponseBody/*retorna os dados para o corpo da resposta*/
	public ResponseEntity<List<Usuario>> listaUsuario(){
		
		List<Usuario> usuarios = usuarioRepository.findAll();//busca no BD e retorna uma lista de usuarios
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK );/*retorna a lista em JSON*/
	}
	
	@PostMapping(value="salvar")/*mapeia a url*/
	@ResponseBody /*descrição da resposta*/
	public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){/*PARAMETRO Recebe os dados para salvar*/
		
		Usuario user = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED) ;
		
	}
	
	//ATUALIZAR - USAMOS @PUTMAPPING
	@PutMapping(value="atualizar")/*mapeia a url*/
	@ResponseBody
	//METODO GENERICO USANDO "?" POSSO RETORNAR UMA STRING E ATUALIZAR UM OBJETO
	public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){/*PARAMETRO Recebe os dados para salvar*/
		
		if(usuario.getId() ==null) {
			return new ResponseEntity<String>("id não foi informado", HttpStatus.OK);
		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="deletar")/*mapeia a url*/
	@ResponseBody /*descrição da resposta*/
public ResponseEntity<String> deletar (@RequestParam Long iduser){/*PARAMETRO tem que ser o id*/
		
		/*quando deleta não tem retorno*/
		usuarioRepository.deleteById(iduser);/*metodo deletar do spring data faz o exclusão*/
		
		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
		
	}
	
	//URL MAPEADA
	@GetMapping(value="buscaruserid")/*getmapping para recuperar dados*/
	//RESPOSTA PRA QUEM FEZ A REQUISIÇÃO
	@ResponseBody
	//RECEBO O USUARIO COMO PARAMETRO
	//O NOME DO METODO TEM QUE SER IGUAL AO MAPEADO NA URL
public ResponseEntity<Usuario> buscaruserid (@RequestParam("iduser") Long iduser){
											/*passar essa string com o nome do parametro para facilitar a busca*/				
		//PESQUISA NO BD
		Usuario usuario = usuarioRepository.findById(iduser).get();/*retorna o usuario pelo id*/
	
		/*RETORNO PARA A TELA*/
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
		
	}
	@GetMapping(value="buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name="name") String name){
		/*passar essa string com o nome do parametro para facilitar a busca*/				
		//PESQUISA NO BD
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim());/*retorna o usuario pelo id*/

		/*RETORNO PARA A TELA*/
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);

}
}
