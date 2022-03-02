package com.alifetvaci.ReadingIsGood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alifetvaci.ReadingIsGood.services.CustomerDetailsService;
import com.alifetvaci.ReadingIsGood.services.StatisticsService;

@Controller
@RequestMapping("/api")
public class StatisticsController {

	@Autowired
	private CustomerDetailsService customerDetailService;

	@Autowired
	private StatisticsService statisticService;

	@GetMapping("/statistics/order-count")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getTotalOrderCount(Authentication authentication) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);
		return ResponseEntity.ok(statisticService.getTotalOrderCount(authanticationCustomerId));

	}

	@GetMapping("/statistics/purchased-amount")
	@PreAuthorize("#authentication == authentication")
	public ResponseEntity<?> getPurchasedAmount(Authentication authentication) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		return ResponseEntity.ok(statisticService.getPurchasedAmount(authanticationCustomerId));

	}

	@GetMapping("/statistics/purchased-books")
	@PreAuthorize("#authentication == authentication")
	public String getPurchasedBooks(Authentication authentication, Model model) {
		String authanticationCustomerId = customerDetailService.getAuthanticationCustomerId(authentication);

		model.addAttribute("totalCountOfPurchasedBookList",
				statisticService.getPurchasedBooks(authanticationCustomerId));

		return "purchased-books";

	}

}
