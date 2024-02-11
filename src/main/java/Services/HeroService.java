package Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prueba.tecnica.models.HeroEntity;


public interface HeroService {

	public Page<HeroEntity> findAll(Pageable pageable);
	public Optional<HeroEntity> findById(Long id);
	public HeroEntity create(HeroEntity hero);
	public HeroEntity update(Long id, HeroEntity hero);
	public void delete(Long id);	
	public List<HeroEntity> search(String keyword);
	
}
