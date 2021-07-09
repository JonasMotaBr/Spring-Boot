package cursospringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cursospringboot.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {

	
	
	@Query("select t from Telefone t where t.pessoa.id=?1")
	public List<Telefone>  buscaTelefones(Long pessoaid);
	
	@Modifying
	@Query("delete from Telefone t where t.numero = ?1")
	void DeletarTelefone(String numero);
}