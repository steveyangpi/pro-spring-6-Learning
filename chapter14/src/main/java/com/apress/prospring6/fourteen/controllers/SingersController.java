package com.apress.prospring6.fourteen.controllers;

import com.apress.prospring6.fourteen.entities.AbstractEntity;
import com.apress.prospring6.fourteen.entities.Singer;
import com.apress.prospring6.fourteen.problem.InvalidCriteriaException;
import com.apress.prospring6.fourteen.services.SingerService;
import com.apress.prospring6.fourteen.util.CriteriaDto;
import com.apress.prospring6.fourteen.util.SingerForm;
import com.apress.prospring6.fourteen.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static com.apress.prospring6.fourteen.controllers.OneSingerController.setPhoto;

@Controller
@RequestMapping("/singers")
public class SingersController {
    public static Comparator<AbstractEntity> COMPARATOR_BY_ID = Comparator.comparing(AbstractEntity::getId);

    private final SingerService singerService;
    private final MessageSource messageSource;

    public SingersController(SingerService singerService, MessageSource messageSource) {
        this.singerService = singerService;
        this.messageSource = messageSource;
    }

    @GetMapping
    //@RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        List<Singer> singers = singerService.findAll();
        singers.sort(COMPARATOR_BY_ID);
        uiModel.addAttribute("singers",singers);

        return "singers/list";
    }

    //--------------- create ------------------
    @GetMapping(value = "/create")
    public String showCreateForm(Model uiModel){
        uiModel.addAttribute("singerForm",new SingerForm());
        return "singers/create";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@Valid SingerForm singerForm, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest,
                         Locale locale) throws IOException {
        if(bindingResult.hasErrors()) {
            uiModel.addAttribute("message",messageSource.getMessage("singer.save.fail",new Object[]{},locale));
            uiModel.addAttribute("singerForm",singerForm);
            return "singers/create";
        }else{
            uiModel.asMap().clear();

            var singer = new Singer();
            singer.setFirstName(singerForm.getFirstName());
            singer.setLastName(singerForm.getLastName());
            singer.setBirthDate(singerForm.getBirthDate());

            if(!singerForm.getFile().isEmpty()){
                setPhoto(singer,singerForm.getFile());
            }
            var created = singerService.save(singer);
            return "redirect:/singer" + UrlUtil.encodeUrlPathSegment(created.getId().toString(),
                    httpServletRequest);
        }
    }

    //---------------search-----------------------
    @GetMapping(value = "/search")
    public String showSearchform(CriteriaDto criteriaDto){return "singers/search";}

    @GetMapping(value = "/go")
    public String processSubmit(@Valid @ModelAttribute("criteriaDto") CriteriaDto criteria,
                                BindingResult result, Model model, Locale locale) {
        if (result.hasErrors()) {
            return "singers/search";
        }
        try {
            List<Singer> singers = singerService.getByCriteriaDto(criteria);
            if (singers.size() == 0) {
                result.addError(new FieldError("criteriaDto", "noResults", messageSource.getMessage("NotEmpty.criteriaDto.noResults", null, locale)));
                return "singers/search";
            } else if (singers.size() == 1) {
                return "redirect:/singer/" + singers.get(0).getId();
            } else {
                model.addAttribute("singers", singers);
                return "singers/list";
            }
        } catch (InvalidCriteriaException ice) {
            result.addError(new FieldError("criteriaDto", ice.getFieldName(),
                    messageSource.getMessage(ice.getMessageKey(), null, locale)));
            return "singers/search";
        }
    }
}