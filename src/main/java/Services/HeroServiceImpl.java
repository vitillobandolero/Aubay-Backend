package Services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prueba.tecnica.models.HeroEntity;
import com.prueba.tecnica.repositories.HeroRepository;

@Service
public class HeroServiceImpl implements HeroService{

	private static final Logger log = LoggerFactory.getLogger(HeroServiceImpl.class);

	@Autowired
	public HeroRepository repo;

	public Page<HeroEntity> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Cacheable(value = "heroes", key = "#id")
	public Optional<HeroEntity> findById(Long id) {
		log.error("------------------------------Retrieved from DB---------------------------------------");
		return repo.findById(id);
	}

	public HeroEntity create(HeroEntity hero) {
		return repo.save(hero);
	}

	@CacheEvict(value = "heroes", key = "#id")
	public HeroEntity update(Long id, HeroEntity hero) {
		Optional<HeroEntity> optional = this.findById(id);
		HeroEntity heroDTO = optional.get();
		
		heroDTO.setName(hero.getName());
		heroDTO.setVulnerability(hero.getVulnerability());
		heroDTO.setBirthDate(hero.getBirthDate());		
		
		return repo.save(heroDTO);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	
	public List<HeroEntity> search(String name) {
		return repo.findByNameContainingIgnoreCase(name);
	}

}
