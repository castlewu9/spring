package com.sung.demo.alignment;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
public class AlignmentController {
	
	AlignmentRepository alignmentRepository;
	
	public AlignmentController(AlignmentRepository alignmentRepository) {
		this.alignmentRepository = alignmentRepository;
	}
	
	@GetMapping
	public List<Alignment> getAll() {
		return alignmentRepository.findAll();
	}

}
