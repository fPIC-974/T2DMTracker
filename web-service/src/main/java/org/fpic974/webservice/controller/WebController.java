package org.fpic974.webservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.dto.NoteRequest;
import org.fpic974.webservice.dto.NoteResponse;
import org.fpic974.webservice.dto.PatientRequest;
import org.fpic974.webservice.dto.PatientResponse;
import org.fpic974.webservice.service.NoteService;
import org.fpic974.webservice.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/patient")
@RequiredArgsConstructor
@Slf4j
public class WebController {

    private final PatientService patientService;
    private final NoteService noteService;

    @GetMapping
    public String home(Model model) {
        log.info("Controller Call : GET /");
        return getAllPatients(model);
    }

    @GetMapping("/list")
    public String getAllPatients(Model model) {
        log.debug("Controller Call : GET /list");

        List<PatientResponse> patients = patientService.getAllPatients();

        log.debug("Result : " + patients);

        model.addAttribute("patients", patients);

        return "patient/list";
    }

    @GetMapping("/details")
    public String getPatientDetails(@RequestParam Integer id, NoteRequest noteRequest, Model model) {
        PatientResponse patient = patientService.getPatientById(id);
        List<NoteResponse> notes = noteService.getNotesByPatientId(id);
//        String riskAssessment = riskAssessmentService.getRiskAssessmentByPatientId(id);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("note", noteRequest);
//        model.addAttribute("risk", riskAssessment);

        return "patient/details";
    }


    @GetMapping("/add")
    public String createPatient(PatientRequest patientRequest) {
        log.debug("Controller Call : GET -- /patient/add : " + patientRequest);

        return "patient/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid PatientRequest patientRequest, BindingResult result, Model model) {
        log.debug("Controller Call : POST -- /patient/validate - " + patientRequest);

        if (result.hasErrors()) {
            return "patient/add";
        }

        patientService.createPatient(patientRequest);

        model.addAttribute("patients", patientService.getAllPatients());

        return "redirect:http://localhost:8080/web/patient/list";
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam Integer id, Model model) {

        patientService.deletePatientById(id);
        noteService.deleteNotesByPatientId(id);

        model.addAttribute("patients", patientService.getAllPatients());

        return "redirect:http://localhost:8080/web/patient/list";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam Integer id, Model model) {
        log.debug("Controller Called : GET -- /patient/update - " + id);

        PatientResponse patientResponse = patientService.getPatientById(id);

        model.addAttribute("patient", patientResponse);

        return "patient/update";
    }

    @PostMapping("/update")
    public String updatePatient(@RequestParam Integer id, @Valid PatientRequest patientRequest,
                            BindingResult result, Model model) {
        log.debug("Controller Called : POST -- /patient/update - {} - {}", id, patientRequest);

        if (result.hasErrors()) {
            return "patient/update";
        }

        patientService.updatePatientById(id, patientRequest);
        model.addAttribute("patients", patientService.getAllPatients());

        return "redirect:http://localhost:8080/web/patient/list";
    }

    @PostMapping("/validateNote")
    public String addNote(@Valid NoteRequest noteRequest, BindingResult result, Model model) {
        log.debug("POST -- /patient/validateNote - " + noteRequest);

        noteService.createNote(noteRequest);

        return "redirect:http://localhost:8080/web/patient/details?id=" + noteRequest.getPatientId();
    }
}
