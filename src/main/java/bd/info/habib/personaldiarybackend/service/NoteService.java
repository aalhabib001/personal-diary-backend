package bd.info.habib.personaldiarybackend.service;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.ApiMessageResponse;
import bd.info.habib.personaldiarybackend.dto.request.NoteRequest;
import bd.info.habib.personaldiarybackend.dto.response.NoteResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NoteService {
    ResponseEntity<ApiMessageResponse> createNote(NoteRequest noteRequest);

    ResponseEntity<ApiDataResponse<List<NoteResponse>>> getAllNotes(String query, List<String> fi);

    ResponseEntity<ApiDataResponse<NoteResponse>> getNote(Long id);

    ResponseEntity<ApiMessageResponse> updateNote(Long id, NoteRequest noteRequest);

    ResponseEntity<ApiMessageResponse> deleteNote(Long id);

    ResponseEntity<ApiDataResponse<List<NoteResponse>>> getAllNotesByCategory(Long categoryId, String query, List<String> fields);
}
