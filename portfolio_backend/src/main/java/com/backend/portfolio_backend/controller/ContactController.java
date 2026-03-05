package com.backend.portfolio_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.portfolio_backend.model.ContactMessage;
import com.backend.portfolio_backend.repository.ContactRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(
    origins = "*",
    allowedHeaders = "*",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    }
)
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    // ── POST /api/contact — Save a new message ──────────────────────────────
    @PostMapping
    public ResponseEntity<Map<String, String>> saveContact(@Valid @RequestBody ContactMessage msg) {
        contactRepository.save(msg);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Your message has been received! I'll get back to you soon.");
        return ResponseEntity.ok(response);
    }

    // ── GET /api/contact — Fetch all messages (admin use) ───────────────────
    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages(
            @RequestHeader(value = "X-Admin-Key", required = false) String adminKey) {

        // Simple key check — replace "Vrenjers001" with your own secret
        if (!"Vrenjers001".equals(adminKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(contactRepository.findAllByOrderBySentAtDesc());
    }

    // ── DELETE /api/contact/{id} — Delete a message (admin use) ────────────
   @DeleteMapping("/{id}")
public ResponseEntity<Map<String, String>> deleteMessage(
        @PathVariable Long id,
        @RequestParam(value = "key", required = false) String adminKey) {  // ← changed

    if (!"Vrenjers001".equals(adminKey)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    if (!contactRepository.existsById(id)) {
        Map<String, String> err = new HashMap<>();
        err.put("error", "Message not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    contactRepository.deleteById(id);
    Map<String, String> response = new HashMap<>();
    response.put("status", "deleted");
    return ResponseEntity.ok(response);
}

    // ── Validation error handler ─────────────────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg   = error.getDefaultMessage();
            errors.put(field, msg);
        });
        return errors;
    }
}