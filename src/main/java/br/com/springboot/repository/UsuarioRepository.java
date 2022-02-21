package br.com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
								/*usamos ?1 porque é só um parametro de pesquisa*/
	@Query(value = "select u from Usuario u where u.nome like %?1%")
	List<Usuario> buscarPorNome(String name);
}
