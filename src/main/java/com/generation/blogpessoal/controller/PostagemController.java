		package com.generation.blogpessoal.controller;
		import com.generation.blogpessoal.repository.TemaRepository;
		
		import java.util.List;
		import java.util.Optional;
		
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.data.repository.query.Param;
		import org.springframework.http.HttpStatus;
		import org.springframework.http.ResponseEntity;
		import org.springframework.web.bind.annotation.CrossOrigin;
		import org.springframework.web.bind.annotation.DeleteMapping;
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.PathVariable;
		import org.springframework.web.bind.annotation.PostMapping;
		import org.springframework.web.bind.annotation.PutMapping;
		import org.springframework.web.bind.annotation.RequestBody;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.ResponseStatus;
		import org.springframework.web.bind.annotation.RestController;
		import org.springframework.web.server.ResponseStatusException;
		
		import com.generation.blogpessoal.model.Postagem;
		import com.generation.blogpessoal.repository.PostagemRepository;
		import com.generation.blogpessoal.repository.TemaRepository;
		
		import jakarta.validation.Valid;
		
		@RestController 
		@RequestMapping("/postagens")
		@CrossOrigin(origins = "*" , allowedHeaders = "*")
		
		public class PostagemController {
			
		@Autowired
		private PostagemRepository postagemRepository;
		@Autowired
		private TemaRepository temaRepository;
		
		@GetMapping
		public ResponseEntity <List<Postagem>> getAll(){
			
				return ResponseEntity.ok(postagemRepository.findAll());
		}
		
		//definindo o metodo https get, e no endpoint dele enviando a variavel de caminho(que é a variavel q vai guardar o valor do id pesquisado) 
		@GetMapping("/{id}")
		//criando um metodo do tipo Resposta de Requisicao do obj Postagem chamado get by id e ele recebe a variavel de camingo do tipo long id 
		public ResponseEntity<Postagem> getByid(@PathVariable Long id){
			//pega o metodo do crud da postagem e procura com id 
			return postagemRepository.findById(id)
					//uso o optinal para evitar nullpointerexeption e o map para validar se e'nulo ou nao 
					//mapeia se o valor do id é null ou nao e se a resposta for ok, ele manda a resposta para o respoEntitiy 
				.map(resposta -> ResponseEntity.ok(resposta))
				//se nao ele seta o sattus da aquisicao como nao encontrado e manda contruir a mensagem do http
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
			
		}
		
		@GetMapping("/titulo/{titulo}")
		public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
			return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
					
		
		}
		
		
		@PostMapping()
		public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
			if(temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.CREATED)
					.body(postagemRepository.save(postagem));
			
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!" , null); 
		}
		
		@PutMapping()
		public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
			if(postagemRepository.existsById(postagem.getId())) {
				
				if(temaRepository.existsById(postagem.getTema().getId()))
						return  ResponseEntity.status(HttpStatus.OK)
							.body(postagemRepository.save(postagem));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!" , null); 
			
			}	
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
		
			@ResponseStatus(HttpStatus.NO_CONTENT)
			@DeleteMapping("/{id}")
			
			public void delete (@PathVariable Long id) {
				Optional<Postagem> postagem = postagemRepository.findById(id);
				
				if(postagem.isEmpty()) 	
					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
				
				postagemRepository.deleteById(id);
					
			}
				
			
			}