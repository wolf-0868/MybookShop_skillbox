package com.example.bookshop.controllers.admin;

import com.example.bookshop.data.dto.ReviewDTO;
import com.example.bookshop.services.admin.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminReviewPageController {

    private final AdminReviewService adminReviewService;

    @GetMapping(value = "/admin/reviews")
    public String adminReviewsPage(
            Model aModel,
            @RequestParam(required = false, name = "keyword") String aKeyword,
            @RequestParam(defaultValue = "1", name = "page") int aPage,
            @RequestParam(defaultValue = "10", name = "size") int aSize) {
        Page<ReviewDTO> pageReviews = null;
        Pageable paging = PageRequest.of(aPage - 1, aSize, Sort.by("id"));

        try {
            if (aKeyword == null) {
                pageReviews = adminReviewService.getPageReviews(paging);
            } else {
                pageReviews = adminReviewService.getPageOfSearchResultReviews(aKeyword, paging);
                aModel.addAttribute("keyword", aKeyword);
            }
            aModel.addAttribute("reviews", pageReviews.getContent());
            aModel.addAttribute("currentPage", pageReviews.getNumber() + 1);
            aModel.addAttribute("totalItems", pageReviews.getTotalElements());
            aModel.addAttribute("totalPages", pageReviews.getTotalPages());
            aModel.addAttribute("pageSize", pageReviews.getSize());

        } catch (Exception e) {
            aModel.addAttribute("message", e.getMessage());
        }
        return "/admin/reviews";
    }

    @GetMapping(value = "/admin/reviews/delete/{id}")
    public String deleteReviews(@PathVariable("id") Long aId, RedirectAttributes aRedirectAttributes) {
        try {
            adminReviewService.deleteReviewById(aId);
            aRedirectAttributes.addFlashAttribute("message", "The Review with id=" + aId + " has been deleted successfully!");
        } catch (Exception e) {
            aRedirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/reviews";
    }

}
