package com.apress.prospring6.fourteen.boot.controllers;

import com.apress.prospring6.fourteen.boot.entities.AbstractEntity;
import com.apress.prospring6.fourteen.boot.entities.Singer;
import com.apress.prospring6.fourteen.boot.problem.InvalidCriteriaException;
import com.apress.prospring6.fourteen.boot.services.SingerService;
import com.apress.prospring6.fourteen.boot.util.CriteriaDto;
import com.apress.prospring6.fourteen.boot.util.SingerForm;
import com.apress.prospring6.fourteen.boot.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static com.apress.prospring6.fourteen.boot.controllers.OneSingerController.setPhoto;

@Controller
@RequestMapping("/singers")
public class SingersController {
    public static Comparator<AbstractEntity> COMPARATOR_BY_ID = Comparator.comparing(AbstractEntity::getId);

    private final Logger LOGGER = LoggerFactory.getLogger(SingersController.class);

    private final SingerService singerService;
    private final MessageSource messageSource;

    public SingersController(SingerService singerService, MessageSource messageSource) {
        this.singerService = singerService;
        this.messageSource = messageSource;
    }

    @GetMapping(path = "/list")
    //@RequestMapping(path = "/list",method = RequestMethod.GET)
    public String list(Model uiModel) {
        List<Singer> singers = singerService.findAll();
        singers.sort(COMPARATOR_BY_ID);
        uiModel.addAttribute("singers", singers);

        return "singers/list";
    }

    //--------------------create ------------------
    @GetMapping(value = "/create")
    public String create(Model uiModel) {
        uiModel.addAttribute("singer", new SingerForm());
        return "singers/create";
    }

    //@RequestMapping(params = "form",method = RequestMethod.POST)
    @PostMapping
    public String create(@Valid SingerForm singerForm, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest,
                         Locale locale) throws IOException {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", messageSource.getMessage("singer.save.fail", new Object[]{}, locale));
            uiModel.addAttribute("singer", singerForm);
            return "singers/create";
        }
        uiModel.asMap().clear();

        var singer = new Singer();
        singer.setFirstName(singerForm.getFirstName());
        singer.setLastName(singerForm.getLastName());
        singer.setBirthDate(singerForm.getBirthDate());

        if (!singerForm.getFile().isEmpty()) {
            setPhoto(singer, singerForm.getFile());
        }

        var created = singerService.save(singer);
        return "redirect:/singer/" + UrlUtil.encodeUrlPathSegment(created.getId().toString(),
                httpServletRequest);

    }
    // -------------------search---------------
    @GetMapping(value = "/search")
    public String showSearchform(CriteriaDto criteria) {
        return "singers/search";
    }

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
