package com.spring.jwt.PremiumCarBrandData;

<<<<<<< HEAD
=======
import com.spring.jwt.utils.BaseResponseDTO;
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import com.spring.jwt.utils.ResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


<<<<<<< HEAD

=======
import java.time.LocalDateTime;
>>>>>>> f6478de2863350de09dee9e4d298974975739906
import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.ok;
import static org.springframework.web.servlet.function.ServerResponse.status;

@Slf4j
@RestController
@RequestMapping("/premiumbrands")
<<<<<<< HEAD
public class PremiumCarBrandController {
=======
    public class PremiumCarBrandController {
>>>>>>> f6478de2863350de09dee9e4d298974975739906
    @Autowired
    PremiumCarBrandService premiumCarBrandService;



<<<<<<< HEAD
@PostMapping("/add")
public ResponseEntity<com.spring.jwt.utils.ResponseDto> create( @RequestBody PremiumBrandDTO Dto)
{

    PremiumBrandDTO addedBrand =premiumCarBrandService.save(Dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Success","Brand added Successfully"));
}


    @PatchMapping("/{brandId}")
    public ResponseEntity<ResponseDto> updateBrandById(
            @RequestBody PremiumBrandDTO dto,
            @PathVariable Integer brandId) {
        PremiumBrandDTO updatedBrand = premiumCarBrandService.updateBrandById(dto, brandId);
        return ResponseEntity.ok(new ResponseDto("success", "updated successfully"));
    }

=======

    @PostMapping("/add")
    public ResponseEntity<PremiumBrandResponseDto<PremiumBrandDTO>> create(@Valid @RequestBody PremiumBrandDTO dto) {
        PremiumBrandDTO addedBrand = premiumCarBrandService.save(dto);

        PremiumBrandResponseDto<PremiumBrandDTO> response = new PremiumBrandResponseDto<>(
                "success",
                HttpStatus.CREATED.value(),
                "PremiumCar added successfully",
                null,
                LocalDateTime.now()

        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }





    @PatchMapping("/{brandId}")
    public ResponseEntity<PremiumBrandResponseDto> updateBrandById(
             @RequestBody PremiumBrandDTO dto,
            @PathVariable Integer brandId) {

        premiumCarBrandService.updateBrandById(dto, brandId);

        PremiumBrandResponseDto response = new PremiumBrandResponseDto(
                "success",
                HttpStatus.OK.value(),
                "Brand updated successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
>>>>>>> f6478de2863350de09dee9e4d298974975739906


    @DeleteMapping("/{brandId}")
    public ResponseEntity<ResponseDto> deletePremiumBrand(@PathVariable Integer brandId) {
        premiumCarBrandService.deletePremiumBrand(brandId);
     return ResponseEntity.ok(new ResponseDto("Success","Brand Deleted Successfully"));
}

    @GetMapping("/{brandId}")
<<<<<<< HEAD
    public ResponseEntity<ResponseDto> getPremiumBrandById(@PathVariable Integer brandId) {

        PremiumBrandDTO response = premiumCarBrandService.getPremiumBrandById(brandId);
     return ResponseEntity.ok(new ResponseDto("Success","Brand Found Successfully"));
=======
    public ResponseEntity<AllBrandDTO> getPremiumBrandById(@PathVariable Integer brandId) {

        PremiumBrandDTO response = premiumCarBrandService.getPremiumBrandById(brandId);

        return ResponseEntity.ok(AllBrandDTO.success("brand found Suceessfully",response));

>>>>>>> f6478de2863350de09dee9e4d298974975739906

    }


    @GetMapping("/all")
    public ResponseEntity<AllBrandDTO> getAllPremiumBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<PremiumBrandDTO> response = premiumCarBrandService.getAllPremiumBrands(page, size);
<<<<<<< HEAD
        return ResponseEntity.ok(new AllBrandDTO<>("List of Brand Data",response,null));
=======
        return ResponseEntity.ok(AllBrandDTO.success("Brand Retrived Successfully",response));
>>>>>>> f6478de2863350de09dee9e4d298974975739906
    }

    @GetMapping("/unique")
    public ResponseEntity<AllBrandDTO> getUniqueBrands()
    {
        List<String> uniqueBrands = premiumCarBrandService.getUniqueBrands();

<<<<<<< HEAD
        return ResponseEntity.ok(new AllBrandDTO<>("List of Unique Data",uniqueBrands,null));
    }

    @GetMapping("/variants")
    public ResponseEntity<AllBrandDTO> getVariantsByBrand(@RequestParam String brandName) {
        List<String> variants = premiumCarBrandService.getVariantsByBrand(brandName);
        return ResponseEntity.ok(new AllBrandDTO<>("List of Brand Data",variants,null));
    }

    @GetMapping("/subvariants")
    public ResponseEntity<AllBrandDTO> getSubVariants(
            @RequestParam String brandName,
            @RequestParam String variantName) {

   List <String>  subVariants= premiumCarBrandService.getSubVariantsByBrandAndVariant(brandName,variantName);
      return ResponseEntity.ok(new AllBrandDTO<>("List of Sub Variant Data",subVariants,null));
=======
        return ResponseEntity.ok(AllBrandDTO.success("List of Unique brands", uniqueBrands));
    }
    @GetMapping("/variants")
    public ResponseEntity<AllBrandDTO> getVariantsByBrand(@Valid @RequestParam String brandName) {
        List<String> variants = premiumCarBrandService.getVariantsByBrand(brandName);
        return ResponseEntity.ok(AllBrandDTO.success("Fetched list of Variants by brand",variants));
    }

    @GetMapping("/subvariants")
    public ResponseEntity<AllBrandDTO> getSubVariants(@Valid
            @RequestParam String brandName,
            @RequestParam String variantName) {

        List <String>  subVariants= premiumCarBrandService.getSubVariantsByBrandAndVariant(brandName,variantName);
        return ResponseEntity.ok(AllBrandDTO.success("Fetched subvarient fro given brand and variant",subVariants));
>>>>>>> f6478de2863350de09dee9e4d298974975739906

    }
}

