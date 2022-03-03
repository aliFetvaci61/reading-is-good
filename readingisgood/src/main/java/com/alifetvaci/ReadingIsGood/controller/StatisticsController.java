package com.alifetvaci.ReadingIsGood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alifetvaci.ReadingIsGood.services.IAuthenticationFacadeService;
import com.alifetvaci.ReadingIsGood.services.StatisticsService;

@Controller
@RequestMapping("/api")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticService;

	@Autowired
	private IAuthenticationFacadeService iAuthenticationFacadeService;

	@GetMapping("/statistics/order-count")
	public ResponseEntity<?> getTotalOrderCount() {
		return ResponseEntity
				.ok(statisticService.getTotalOrderCount(iAuthenticationFacadeService.getAuthanticatedCustomerId()));

	}

	@GetMapping("/statistics/purchased-amount")
	public ResponseEntity<?> getPurchasedAmount() {

		return ResponseEntity
				.ok(statisticService.getPurchasedAmount(iAuthenticationFacadeService.getAuthanticatedCustomerId()));

	}

	@GetMapping("/statistics/purchased-books")
	public String getPurchasedBooks(Model model) {

		model.addAttribute("totalCountOfPurchasedBookList",
				statisticService.getPurchasedBooks(iAuthenticationFacadeService.getAuthanticatedCustomerId()));

		return "purchased-books";

	}

}
