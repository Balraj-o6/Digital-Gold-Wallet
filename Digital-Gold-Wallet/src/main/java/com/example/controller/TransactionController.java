// Create: src/main/java/com/example/controller/TransactionController.java

package com.example.controller;

import com.example.dto.TransactionHistoryDTO;


import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.dto.TransactionHistoryDTO;
import com.example.service.IPhysicalGoldTransactionService;
import com.example.service.ITransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                          // Tells Spring: this class handles HTTP requests
@RequestMapping("/api/transactions")     // All URLs in this class start with /api/transactions
public class TransactionController {

    @Autowired                           // Spring automatically gives us the service
    private ITransactionHistoryService transactionHistoryService;

    @Autowired
    private IPhysicalGoldTransactionService physicalGoldTransactionService;

    // URL: GET /api/transactions/branch/3
    //      (the {branchId} part comes from the URL — so 3 becomes the branchId)
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionsByBranchId(
            @PathVariable Integer branchId) {   // @PathVariable reads {branchId} from the URL

        List<TransactionHistoryDTO> transactions =
                transactionHistoryService.getTransactionsByBranchId(branchId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }



    @GetMapping("/physical/branch/{branchId}")
    public ResponseEntity<List<PhysicalGoldTransactionDTO>> getPhysicalTransactionsByBranchId(
            @PathVariable Integer branchId) {

        List<PhysicalGoldTransactionDTO> transactions =
                physicalGoldTransactionService.getPhysicalTransactionsByBranchId(branchId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistoryByUserId(
            @PathVariable Integer userId) {

        List<TransactionHistoryDTO> transactions =
                transactionHistoryService.getTransactionHistoryByUserId(userId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<TransactionHistoryDTO>> getTransactionsByType(
            @RequestParam String type) {
        List<TransactionHistoryDTO> transactions =
                transactionHistoryService.getTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
