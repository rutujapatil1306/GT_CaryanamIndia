package com.spring.jwt.PremiumCarBrandData;

import com.spring.jwt.utils.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.ok;
import static org.springframework.web.servlet.function.ServerResponse.status;

@Slf4j
@RestController
@RequestMapping("/premiumbrands")
    public class PremiumCarBrandController {
    @Autowired
    PremiumCarBrandService premiumCarBrandService;



    @PostMapping("/add")
    public ResponseEntity<ResponseDto> create(@RequestBody PremiumBrandDTO Dto)
{
    PremiumBrandDTO addedBrand =premiumCarBrandService.save(Dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("success","PremiumCar Add Successfully"));

}
    @PatchMapping("/{brandId}")
    public ResponseEntity<ResponseDto> updateBrandById(
            @RequestBody PremiumBrandDTO dto,
            @PathVariable Integer brandId) {
        PremiumBrandDTO updatedBrand = premiumCarBrandService.updateBrandById(dto, brandId);
        return ResponseEntity.ok(new ResponseDto("success", "updated successfully"));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<ResponseDto> deletePremiumBrand(@PathVariable Integer brandId) {
        premiumCarBrandService.deletePremiumBrand(brandId);
     return ResponseEntity.ok(new ResponseDto("Success","Brand Deleted Successfully"));
}

    @GetMapping("/{brandId}")
    public ResponseEntity<AllBrandDTO> getPremiumBrandById(@PathVariable Integer brandId) {

        PremiumBrandDTO response = premiumCarBrandService.getPremiumBrandById(brandId);

        return ResponseEntity.ok(AllBrandDTO.success("brand found Suceessfully",response));


    }


    @GetMapping("/all")
    public ResponseEntity<AllBrandDTO> getAllPremiumBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<PremiumBrandDTO> response = premiumCarBrandService.getAllPremiumBrands(page, size);
        return ResponseEntity.ok(AllBrandDTO.success("Brand Retrived Successfully",response));
    }

    @GetMapping("/unique")
    public ResponseEntity<AllBrandDTO> getUniqueBrands()
    {
        List<String> uniqueBrands = premiumCarBrandService.getUniqueBrands();

        return ResponseEntity.ok(AllBrandDTO.success("List of Unique brands", uniqueBrands));
    }
    @GetMapping("/variants")
    public ResponseEntity<AllBrandDTO> getVariantsByBrand(@RequestParam String brandName) {
        List<String> variants = premiumCarBrandService.getVariantsByBrand(brandName);
        return ResponseEntity.ok(AllBrandDTO.success("Fetched list of Variants by brand",variants));
    }

    @GetMapping("/subvariants")
    public ResponseEntity<AllBrandDTO> getSubVariants(
            @RequestParam String brandName,
            @RequestParam String variantName) {

        List <String>  subVariants= premiumCarBrandService.getSubVariantsByBrandAndVariant(brandName,variantName);
        return ResponseEntity.ok(AllBrandDTO.success("Fetched subvarient fro given brand and variant",subVariants));

    }
}

