package com.spring.jwt.BrandData;

import com.spring.jwt.BrandData.DTO.BrandDataDto;
import com.spring.jwt.BrandData.DTO.BrandResponseDto;
import com.spring.jwt.utils.ApiResponse;
import com.spring.jwt.utils.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@Slf4j
public class BrandDataController {

    @Autowired
    BrandDataService brandDataService;

    // API To Create Brand

    @PostMapping
    public ResponseEntity<ResponseDto> createBrand(@Valid @RequestBody BrandDataDto brandDataDto)
    {
            BrandDataDto addedBrand = brandDataService.createBrand(brandDataDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Success","Brand added Successfully"));
    }

    // API To Get Brand By Id

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponseDto> getBrandById(@RequestParam Integer brandId)
    {
            BrandDataDto brand = brandDataService.getBrandById(brandId);
            return ResponseEntity.ok(BrandResponseDto.success("Brand with given id is found", brand));
    }

    // API TO Update Brand

    @PatchMapping
    public ResponseEntity<BrandResponseDto> updateBrandById(@Valid @RequestBody BrandDataDto brandDataDto, @RequestParam Integer brandId)
    {
        try {
            BrandDataDto updatedBrand = brandDataService.updateBrandById(brandDataDto, brandId);
            return ResponseEntity.ok(BrandResponseDto.success("Brand Updated Successfully", updatedBrand));
        }
        catch(Exception ex)
        {
            log.error("Error Updating Brand", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BrandResponseDto.error("Failed To Update Brand at Given Id", ex.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@RequestParam Integer brandId)
    {
        brandDataService.deleteBrand(brandId);
        return ResponseEntity.ok(ApiResponse.success("BrandData deleted Successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<BrandResponseDto> getAllBrands
            (@RequestParam(defaultValue = "o") int size,
             @RequestParam(defaultValue = "10") int page) {

            List<BrandDataDto> response = brandDataService.getAllBrands(page, size);

            return ResponseEntity.ok(BrandResponseDto.success("Brands Retrieved Successfully", response));

    }


    @GetMapping
    public ResponseEntity<BrandResponseDto<List<String>>> getUniqueBrands()
    {
        List<String> uniqueBrands = brandDataService.getUniqueBrands();

        return ResponseEntity.ok(BrandResponseDto.success("List of Unique Brands ",uniqueBrands));
    }

    @GetMapping("/variants")
    public ResponseEntity<BrandResponseDto<List<String>>> getVariantByBrand(@RequestParam String brand)
    {
        List<String> variants = brandDataService.getVariantsByBrand(brand);

        return ResponseEntity.ok(BrandResponseDto.success("Fetched list of Variants By Brand ",variants));

    }

    @GetMapping("/sub_Variants")
    public ResponseEntity<BrandResponseDto<List<String>>> getSubVariantByBrand(@RequestParam String brand,

                                                                               @RequestParam String variant)
    {
        List<String> subVariants = brandDataService.getSubVariantsByBrandVariant(brand, variant);

        return ResponseEntity.ok(BrandResponseDto.success("Fetched SubVariants for given Brand and Variant",subVariants));
    }
}
