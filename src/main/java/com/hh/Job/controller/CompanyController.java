package com.hh.Job.controller;


import com.hh.Job.domain.Company;
import com.hh.Job.domain.response.ResultPaginationDTO;
import com.hh.Job.service.CompanyService;
import com.hh.Job.util.annotation.APImessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(company));
    }


    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@Valid @PathVariable("id") Long id)
    {

        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok(null);

    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid  @RequestBody Company company) {
        Company updateCompany = this.companyService.handleUpdateCompany(company);
        return ResponseEntity.ok(updateCompany);
    }

    // phan trang vs pageable
//    @GetMapping("/companies")
//    public ResponseEntity<ResultPaginationDTO> getAllCompanies(
//            @RequestParam ("current") Optional<String> currentOptional,
//            @RequestParam("pageSize") Optional<String> pageSizeOptional
//    ){
//
//        String sCurrentPage = currentOptional.isPresent() ? currentOptional.get() : "";
//        String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
//
//        int crr = Integer.parseInt(sCurrentPage);
//        int page = Integer.parseInt(sPageSize);
//        Pageable pageable = PageRequest.of(crr - 1, page);
//        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.fetchAllCompany(pageable));
//    }



    // phan trang voi filter(Specification)
    @GetMapping("/companies")
    @APImessage("fetch all company")
    public ResponseEntity<ResultPaginationDTO> getAllCompanies(
            @Filter Specification<Company> spec,
            Pageable pageable
            ){

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.fetchAllCompany(spec,pageable));
    }
}
