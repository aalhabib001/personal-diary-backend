package bd.info.habib.personaldiarybackend.service.impl;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.ApiMessageResponse;
import bd.info.habib.personaldiarybackend.dto.request.NoteRequest;
import bd.info.habib.personaldiarybackend.dto.response.CategoryResponse;
import bd.info.habib.personaldiarybackend.dto.response.NoteResponse;
import bd.info.habib.personaldiarybackend.model.CategoryModel;
import bd.info.habib.personaldiarybackend.model.NoteModel;
import bd.info.habib.personaldiarybackend.repository.CategoryRepository;
import bd.info.habib.personaldiarybackend.repository.NoteRepository;
import bd.info.habib.personaldiarybackend.repository.specification.NoteSpecification;
import bd.info.habib.personaldiarybackend.service.NoteService;
import bd.info.habib.personaldiarybackend.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<ApiMessageResponse> createNote(NoteRequest noteRequest) {
        CategoryModel categoryModel = categoryRepository.findById(noteRequest.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + noteRequest.getCategoryId()));

        NoteModel noteModel = NoteModel.builder()
                .title(noteRequest.getTitle())
                .content(noteRequest.getContent())
                .category(categoryModel)
                .build();

        noteRepository.save(noteModel);

        return new ResponseEntity<>(new ApiMessageResponse(201, "Note created successfully"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiDataResponse<List<NoteResponse>>> getAllNotes(String query, List<String> fields) {
        String userName = AuthUtils.getUserName();
//        List<NoteModel> noteModels = noteRepository.findAllByCreatedBy(userName);

        List<NoteModel> noteModels = noteRepository.findAll(NoteSpecification.search(query, fields, null));

        List<NoteResponse> noteResponses = noteModelToNoteResponse(noteModels);

        if (noteResponses.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No notes found");
        else
            return new ResponseEntity<>(new ApiDataResponse<>(200, "Notes fetched successfully", noteResponses), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ApiDataResponse<NoteResponse>> getNote(Long id) {
        NoteModel noteModel = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));

        String userName = AuthUtils.getUserName();
        if (!noteModel.getCreatedBy().equals(userName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this note");
        }

        NoteResponse noteResponse = new NoteResponse(noteModel.getId(), noteModel.getTitle(), noteModel.getContent(),
                new CategoryResponse(noteModel.getCategory().getId(), noteModel.getCategory().getCategoryName())
        );

        return new ResponseEntity<>(new ApiDataResponse<>(200, "Note fetched successfully", noteResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiMessageResponse> updateNote(Long id, NoteRequest noteRequest) {
        NoteModel noteModel = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
        String userName = AuthUtils.getUserName();
        if (!noteModel.getCreatedBy().equals(userName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to edit this note");
        }

        BeanUtils.copyProperties(noteRequest, noteModel);

        if (!noteModel.getCategory().getId().equals(noteRequest.getCategoryId())) {
            CategoryModel categoryModel = categoryRepository.findById(noteRequest.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + noteRequest.getCategoryId()));
            noteModel.setCategory(categoryModel);
        }

        noteRepository.save(noteModel);

        return new ResponseEntity<>(new ApiMessageResponse(200, "Note updated successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiMessageResponse> deleteNote(Long id) {
        NoteModel noteModel = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
        String userName = AuthUtils.getUserName();
        if (!noteModel.getCreatedBy().equals(userName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this note");
        }

        noteRepository.delete(noteModel);

        return new ResponseEntity<>(new ApiMessageResponse(200, "Note deleted successfully"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiDataResponse<List<NoteResponse>>> getAllNotesByCategory(Long categoryId, String query, List<String> fields) {
        String userName = AuthUtils.getUserName();
        CategoryModel categoryModel = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + categoryId));

//        List<NoteModel> noteModels = noteRepository.findAllByCategoryAndCreatedBy(categoryModel, userName);

        List<NoteModel> noteModels = noteRepository.findAll(NoteSpecification.search(query, fields, categoryModel));

        List<NoteResponse> noteResponses = noteModelToNoteResponse(noteModels);


        if (noteResponses.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No notes found");
        else
            return new ResponseEntity<>(new ApiDataResponse<>(200, "Notes fetched successfully", noteResponses), HttpStatus.OK);
    }


    private List<NoteResponse> noteModelToNoteResponse(List<NoteModel> noteModels) {

        return noteModels.stream()
                .map(noteModel -> new NoteResponse(noteModel.getId(), noteModel.getTitle(), noteModel.getContent(),
                        new CategoryResponse(noteModel.getCategory().getId(), noteModel.getCategory().getCategoryName())
                )).collect(java.util.stream.Collectors.toList());
    }


}
