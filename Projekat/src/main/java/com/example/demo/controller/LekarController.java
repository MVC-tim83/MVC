package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.TerminDTO;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Termin;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;

@RestController
@RequestMapping(value = "/api/lekari", produces = MediaType.APPLICATION_JSON_VALUE)
public class LekarController {

	@Autowired
	private LekarService lekarService;

	@Autowired
	private PacijentService pacijentiSevice;

	@Autowired
	private PregledService pregledService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable Long id) {

		Lekar lekar = lekarService.findOne(id);

		// studen must exist
		if (lekar == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getLekarByEmail")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE') or hasAuthority('LEKAR')")
	public ResponseEntity<?> findByEmail(Principal p){
		
		Lekar lekar = lekarService.findByEmail(p.getName());
		if (lekar == null) {
			System.out.println("Lekar nije pronadjen");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println("Lekar je pronadjen : "+ lekar);
		
		return ResponseEntity.ok(new LekarDTO(lekar));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<LekarDTO>> getAll() {

		List<Lekar> lekari = lekarService.findAll();

		// convert students to DTOs
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			lekarDTO.add(new LekarDTO(l));
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@PutMapping(path = "/update", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR') or hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<LekarDTO> updateLekar(@RequestBody LekarDTO lekarDTO) {

		// a student must exist
		System.out.println("LEKAR UPDRATE");
		Lekar lekar = lekarService.findByEmail(lekarDTO.getEmail());
//		System.out.println("Lekar update: " + lekar.getEmail());
//		if (lekar == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}

		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setTelefon(lekarDTO.getTelefon());

		lekar = lekarService.save(lekar);
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}

	// vrati mi listu svih paicjenata od prijavljenog lekara
	@GetMapping(value = "/listaPacijenataLekara")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PacijentDTO>> getPacijenataLekara(Principal pa) {
		System.out.println("//////////////////// LEKAR LISTA PACIJENATA /////////////////////////		");
		Lekar lekar = lekarService.findByEmail(pa.getName());

		List<Pacijent> listaSvihP = pacijentiSevice.findAll();
		for (Pacijent pp : listaSvihP) {
			System.out.println("!!!! " + pp);
		}
		System.out.println("Lista pacijenata od lekara: " + lekar.getEmail());

		List<PacijentDTO> lista = new ArrayList<>();
		for (Pacijent p : listaSvihP) {
			if (p.getOdobrenaRegistracija() == true) {

				System.out.println(p);
				PacijentDTO pDTO = new PacijentDTO(p);

				System.out.println("Pacijent dodat");
				lista.add(pDTO);
			}

		}
		System.out.println("*************");
		for (PacijentDTO pd : lista) {
			System.out.println(pd);
		}
		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping(value = "/listaPregledaPacijenta")
	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<List<PregledDTO>> getListaPregledaPacijenta(@RequestBody PacijentDTO pacijentDTO) {
		
		Pacijent pacijent = pacijentiSevice.findByEmail(pacijentDTO.getEmail());
		
		Set<Pregled> listaPregleda = pacijent.getListaPregleda();
		
		
		for (Pregled pp : listaPregleda) {
			System.out.println("Status pregleda " + pp.getStatus());
		}
		

		List<PregledDTO> lista = new ArrayList<>();
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	//VRACA LISTU PREGLEDA, ODMORA I ODSUSTVA
	@GetMapping(value = "/listaPregleda/{email}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<PregledDTO>> getListaPregleda(@PathVariable String email) {
		System.out.println("*************");
		Lekar lek = lekarService.findByEmail(email);
		
		Set<Pregled> listaRD = lek.getListaPregleda();
		
		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for(Pregled rd: listaRD) {
			System.out.println(rd.getDatum());
			System.out.println(rd.getTrajanje());
			lista.add(new PregledDTO(rd));
		}
		

		System.out.println("*************");
		return new ResponseEntity<>(lista, HttpStatus.OK);

		
	}
	
	@GetMapping(value = "/listaZauzetihTermina/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<TerminDTO>> getListaZauzetihTermina(@PathVariable Long id) {
		System.out.println("*************");

		Lekar lekar = lekarService.findOne(id);
		System.out.println(lekar.getIme());
		
		Set<Termin> listaTermina = lekar.getListaZauzetihTermina();
		System.out.println(listaTermina.size());
		
		List<TerminDTO> listaTerminaDTO = new ArrayList<TerminDTO>();
		for(Termin rd: listaTermina) {
			listaTerminaDTO.add(new TerminDTO(rd));
		}
		

		System.out.println("*************");
		return new ResponseEntity<>(listaTerminaDTO, HttpStatus.OK);

		
	}
	

	@PutMapping(path = "/oceni/{id}/{ocena}/{pregled_id}", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<LekarDTO> oceniLekara(@PathVariable Long id, @PathVariable int ocena,
			@PathVariable Long pregled_id) {

		Lekar lekar = lekarService.findById(id);
		int temp = lekar.getOcena();
		lekar.setOcena((temp + ocena) / 2);
		lekarService.save(lekar);
		Pregled pregled = pregledService.findById(pregled_id);
		if (pregled.getStatus() == 3) {
			pregled.setStatus(5);
			pregledService.save(pregled);
		} else if (pregled.getStatus() == 4) {
			pregled.setStatus(6);
			pregledService.save(pregled);
		}

		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.OK);
	}


}
