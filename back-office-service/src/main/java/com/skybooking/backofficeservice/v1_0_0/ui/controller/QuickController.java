package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.service.quick.QuickSV;
import com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick.QuickRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.quick.QuickRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1.0.0/quick")
public class QuickController {
    @Autowired
    private QuickSV quickSV;

    @GetMapping
    public ResponseEntity<List<QuickRS>> listing()
    {
        return ResponseEntity.status(HttpStatus.OK).body(quickSV.listing());
    }

    @PostMapping
    public ResponseEntity created(@Valid @RequestBody QuickRQ quickRQ)
    {
        quickSV.created(quickRQ);

        return new ResponseEntity<>("Success Created!", HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleted(@PathVariable Integer id)
    {
        var quick = quickSV.deleted(id);

        if(quick == false){
            return new ResponseEntity<>("Record not Match", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>( "Success Deleted", HttpStatus.OK);
    }
}

