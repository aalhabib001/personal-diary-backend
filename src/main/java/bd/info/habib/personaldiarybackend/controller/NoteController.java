package bd.info.habib.personaldiarybackend.controller;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.ApiMessageResponse;
import bd.info.habib.personaldiarybackend.dto.request.NoteRequest;
import bd.info.habib.personaldiarybackend.dto.response.NoteResponse;
import bd.info.habib.personaldiarybackend.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    // Create Note
    @PostMapping
    public ResponseEntity<ApiMessageResponse> createNote(@RequestBody NoteRequest noteRequest) {
        return noteService.createNote(noteRequest);
    }

    // Get All Notes with multi field wise search
    @GetMapping
    public ResponseEntity<ApiDataResponse<List<NoteResponse>>> getAllNotes(@RequestParam(required = false) String query,
                                                                           @RequestParam(required = false, name = "fi") List<String> fields) {
        return noteService.getAllNotes(query, fields);
    }

    // Get a single Note by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<NoteResponse>> getNote(@PathVariable("id") Long id) {
        return noteService.getNote(id);
    }

    // Update a single Note by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> updateNote(@PathVariable("id") Long id, @RequestBody NoteRequest noteRequest) {
        return noteService.updateNote(id, noteRequest);
    }

    // Delete a single Note by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> deleteNote(@PathVariable("id") Long id) {
        return noteService.deleteNote(id);
    }

    // Get all notes with filter by fields, category
    @GetMapping("/-/categories/{categoryId}")
    public ResponseEntity<ApiDataResponse<List<NoteResponse>>> getAllNotesByCategory(@PathVariable("categoryId") Long categoryId,
                                                                                     @RequestParam(required = false) String query,
                                                                                     @RequestParam(required = false, name = "fi") List<String> fields) {
        return noteService.getAllNotesByCategory(categoryId, query, fields);
    }

}
