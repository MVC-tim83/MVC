package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PregledDTO;


import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;


import com.example.demo.dto.SalaDTO;
import com.example.demo.model.Sala;



import com.example.demo.service.PregledService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "/api/pregledi", produces = MediaType.APPLICATION_JSON_VALUE)
public class PregledController {
	@Autowired
	private PregledService pregledService;
	@Autowired
	private KlinikaService klinikaService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private TipPregledaService tipPregledaService;
	

	@PostMapping(path="/new", consumes = "application/json")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<PregledDTO> noviPregled(@RequestBody PregledDTO pregledDTO) {
		System.out.println("dodavanje novog pregleda");
		System.out.println(pregledDTO);
		Pregled pregled = new Pregled();
		pregled.setCena(pregledDTO.getCena());
		pregled.setDatum(pregledDTO.getDatum());
		Klinika klinika = klinikaService.findById(pregledDTO.getKlinikaID());
		pregled.setKlinika(klinika);
		Lekar lekar = lekarService.findOne(pregledDTO.getLekarID());
		pregled.setLekar(lekar);
		Pacijent pacijent = pacijentService.findByEmail(pregledDTO.getPacijentEmail());
		pregled.setPacijent(pacijent);
		pregled.setStatus(false);
		TipPregleda tp = tipPregledaService.findOne(pregledDTO.getTipPregledaID());
		pregled.setTipPregleda(tp);
		

		pregled = pregledService.save(pregled);
		

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PregledDTO> getLekar(@PathVariable Long id) {

		Pregled pregled = pregledService.findOne(id);

		// studen must exist
		if (pregled == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PregledDTO>> getAll() {

		List<Pregled> pregledi = pregledService.findAll();

		// convert students to DTOs
		List<PregledDTO> pregledDTO = new ArrayList<>();
		for (Pregled p : pregledi) {
			pregledDTO.add(new PregledDTO(p));
		}

		return new ResponseEntity<>(pregledDTO, HttpStatus.OK);
	}

	

	@GetMapping(value = "preuzmiPregledeKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> getPreglediKlinike(@PathVariable Long id) {

		Klinika klinika = klinikaService.findOne(id);
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> lista = new ArrayList<PregledDTO>();
		for(Pregled s : pregledi) {
			if(s.getKlinika().getId()==klinika.getId()) {
				PregledDTO pregledDTO = new PregledDTO(s);
				lista.add(pregledDTO);
			}
		}
		
		System.out.println("Lista pregleda u klinici:" + klinika.getNaziv() + " ID: " + id);
		for(PregledDTO ss: lista) {
			System.out.println(ss.getCena());
		}
		
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	

}
