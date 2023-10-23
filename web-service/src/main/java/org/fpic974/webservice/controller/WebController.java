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
import org.fpic974.webservice.service.RiskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/web/patient")
@RequiredArgsConstructor
@Slf4j
public class WebController {

    private final PatientService patientService;
    private final NoteService noteService;

    private final RiskService riskService;

    @GetMapping
    public String home(Model model, Principal principal) {
        log.info("Controller Call : GET /web/patient");

        return getAllPatients(model, principal);
    }

    @GetMapping("/list")
    public String getAllPatients(Model model, Principal principal) {
        log.debug("Controller Call : GET /web/patient/list");

        List<PatientResponse> patients = patientService.getAllPatients();

        model.addAttribute("user", principal.getName());
        model.addAttribute("patients", patients);

        return "patient/list";
    }

    @GetMapping("/details")
    public String getPatientDetails(@RequestParam Integer id, NoteRequest noteRequest, Model model) {
        log.debug("Controller Call : GET -- /web/patient/details?id=" + id);

        PatientResponse patient = patientService.getPatientById(id);
        List<NoteResponse> notes = noteService.getNotesByPatientId(id);
        String risk = riskService.getRiskAssessmentByPatientId(id);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("note", noteRequest);
        model.addAttribute("risk", risk);

        return "patient/details";
    }


    @GetMapping("/add")
    public String createPatient(PatientRequest patientRequest, Model model) {
        log.debug("Controller Call : GET -- /web/patient/patient/add : " + patientRequest);

        model.addAttribute("patientRequest", patientRequest);

        return "patient/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid PatientRequest patientRequest, BindingResult result, Model model) {
        log.debug("Controller Call : POST -- /web/patient/patient/validate - " + patientRequest);

        if (result.hasErrors()) {
            return "patient/add";
        }

        patientService.createPatient(patientRequest);

        model.addAttribute("patients", patientService.getAllPatients());

        return "redirect:/web/patient/list";
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam Integer id, Model model) {
        log.debug("Controller Call : GET -- /web/patient/delete - " + id);

        patientService.deletePatientById(id);
        noteService.deleteNotesByPatientId(id);

        model.addAttribute("patients", patientService.getAllPatients());

        return "redirect:/web/patient/list";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam Integer id, Model model) {
        log.debug("Controller Called : GET -- /web/patient/update - " + id);

        PatientResponse patientResponse = patientService.getPatientById(id);

        log.debug("Result : " + patientResponse);

        model.addAttribute("id", id);
        model.addAttribute("patientRequest", patientResponse);

        return "patient/update";
    }

    @PostMapping("/update")
    public String updatePatient(@RequestParam Integer id, @Valid PatientRequest patientRequest,
                            BindingResult result, Model model) {
        log.debug("Controller Called : POST -- /web/patient/update - {} - {}", id, patientRequest);

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "patient/update";
        }

        patientService.updatePatientById(id, patientRequest);
        model.addAttribute("patients", patientService.getAllPatients());

        return "redirect:/web/patient/list";
    }

    @PostMapping("/validateNote")
    public String addNote(@Valid NoteRequest noteRequest, Model model) {
        log.debug("Controller Call : POST -- /web/patient/validateNote - " + noteRequest);

        noteService.createNote(noteRequest);
        model.addAttribute("risk", riskService.getRiskAssessmentByPatientId(noteRequest.getPatientId()));

        return "redirect:/web/patient/details?id=" + noteRequest.getPatientId();
    }
}
